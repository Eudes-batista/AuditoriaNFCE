package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.CabecalhoNota;
import br.com.auditoria.nfce.modelo.Empresa;
import br.com.auditoria.nfce.modelo.FormaPagamento;
import br.com.auditoria.nfce.modelo.Item;
import br.com.auditoria.nfce.modelo.Venda;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.*;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.*;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.COFINS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.PIS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Ide;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import br.com.swconsultoria.nfe.util.*;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class ControleNFCE {

    private final ConfiguracoesNfe config;
    private final List<Double> valoresIcms = new ArrayList<>();
    private final DecimalFormat df = new DecimalFormat("###,##0.00");

    public ControleNFCE(ConfiguracoesNfe config) {
        this.config = config;
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        this.df.setDecimalFormatSymbols(decimalFormatSymbols);
    }

    public String emitiNFCE(String chave) throws CertificadoException, Exception {
        Empresa empresa = new EmpresaControle().buscarEmpresa();
        Venda venda = new ControleVenda().buscarVenda(chave);
        List<Item> itens = new ControleItem().listarItensPorVenda(venda);
        List<FormaPagamento> formarPagamentos = new ControleFormaPagamento().listarFormaPagamentoPorVenda(venda);
        empresa.setCnpj(empresa.getCnpj().replaceAll("\\D", ""));

        //Informe o idToken
        String idToken = empresa.getToken();
        //Informe o CSC da NFCe
        String csc = empresa.getCSC();

        CabecalhoNota cabecalhoNota = preencherCabecalho(venda, empresa);
        ChaveNotaControle chaveNotaControle = new ChaveNotaControle(this.config, cabecalhoNota, empresa);
        cabecalhoNota.setDigitoVerificador(Integer.parseInt(chaveNotaControle.gerarChaveComDigitoVerificador()));

        TNFe.InfNFe infNFe = new TNFe.InfNFe();
        infNFe.setId("NFe" + chaveNotaControle.gerarChaveDeAcesso());
        infNFe.setVersao(ConstantesUtil.VERSAO.NFE);

        infNFe.setIde(preencheIde(cabecalhoNota));
        infNFe.setEmit(preencheEmitente(empresa));
        if (venda.getCpfCliente() != null && !venda.getCpfCliente().isEmpty()) {
            infNFe.setDest(preencheDestinatario(venda));
        }

        infNFe.getDet().addAll(preencheDet(itens, empresa));

        infNFe.setTotal(preencheTotal(itens, empresa));

        infNFe.setTransp(preencheTransporte());

        infNFe.setPag(preenchePag(formarPagamentos));

        TNFe nfe = new TNFe();
        nfe.setInfNFe(infNFe);

        // Monta EnviNfe
        TEnviNFe enviNFe = new TEnviNFe();
        enviNFe.setIdLote("1");
        enviNFe.setIndSinc("1");
        enviNFe.setVersao(ConstantesUtil.VERSAO.NFE);
        enviNFe.getNFe().add(nfe);

        //Monta QRCode
        String qrCode = preencheQRCode(enviNFe, idToken, csc, chaveNotaControle.gerarChaveDeAcesso());

        TNFe.InfNFeSupl infNFeSupl = new TNFe.InfNFeSupl();
        infNFeSupl.setQrCode(qrCode);
        infNFeSupl.setUrlChave(WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_CONSULTANFCE));
        enviNFe.getNFe().get(0).setInfNFeSupl(infNFeSupl);

        // Monta e Assina o XML
        enviNFe = Nfe.montaNfe(config, enviNFe, true);

        TRetConsSitNFe consultaXml = Nfe.consultaXml(config, venda.getChave(), DocumentoEnum.NFCE);

        String xml = XmlNfeUtil.criaNfeProc(enviNFe, consultaXml.getProtNFe());
        return xml;
    }

    private CabecalhoNota preencherCabecalho(Venda venda, Empresa empresa) {
        CabecalhoNota cabecalhoNota = new CabecalhoNota();
        cabecalhoNota.setNumeroNFCe(Integer.parseInt(venda.getCupom()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date data = simpleDateFormat.parse(venda.getDataVenda());
            LocalDateTime localDateTime = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            cabecalhoNota.setDataEmissao(localDateTime);
        } catch (ParseException ex) {
            Logger.getLogger(ControleNFCE.class.getName()).log(Level.SEVERE, null, ex);
        }
        cabecalhoNota.setModelo(Integer.parseInt(DocumentoEnum.NFCE.getModelo()));
        cabecalhoNota.setSerie(Integer.parseInt(empresa.getNumeroSerie()));
        cabecalhoNota.setTipoDaNFe(1);
        cabecalhoNota.setEstado(Integer.parseInt(this.config.getEstado().getCodigoUF()));
        cabecalhoNota.setNumeroVenda(Integer.parseInt(venda.getNumeroVenda()));
        cabecalhoNota.setNaturezaOperacao("VENDA");
        cabecalhoNota.setIndicadorLocalDestino(1);
        cabecalhoNota.setCodigoDoMunicipio(empresa.getCodigoCidade());
        cabecalhoNota.setTipoAmbiente(Integer.parseInt(this.config.getAmbiente().getCodigo()));
        cabecalhoNota.setFinalidadeEmissao(1);
        cabecalhoNota.setFormatoDeImpressao(4);
        cabecalhoNota.setConsumidorFinal(1);
        cabecalhoNota.setIndicadorDePresencaConsumidor(1);
        cabecalhoNota.setIndicadorDeProcessoDeEmissao(0);
        cabecalhoNota.setVersaoProcessoEmissao("1.0");
        return cabecalhoNota;
    }

    private TNFe.InfNFe.Ide preencheIde(CabecalhoNota cabecalhoNota) throws NfeException {
        Ide ide = new Ide();
        ide.setCUF(config.getEstado().getCodigoUF());
        ide.setCNF(String.valueOf(cabecalhoNota.getNumeroVenda()).substring(0, 8));
        ide.setNatOp("VENDA");
        ide.setMod("65");
        ide.setSerie(String.valueOf(cabecalhoNota.getSerie()));
        ide.setNNF(String.valueOf(cabecalhoNota.getNumeroNFCe()));
        ide.setDhEmi(XmlNfeUtil.dataNfe(cabecalhoNota.getDataEmissao()));
        ide.setTpNF("1");
        ide.setIdDest("1");
        ide.setCMunFG(cabecalhoNota.getCodigoDoMunicipio());
        ide.setTpImp("4");
        ide.setTpEmis("1");
        ide.setCDV(String.valueOf(cabecalhoNota.getDigitoVerificador()));
        ide.setTpAmb(config.getAmbiente().getCodigo());
        ide.setFinNFe("1");
        ide.setIndFinal("1");
        ide.setIndPres("1");
        ide.setProcEmi("0");
        ide.setVerProc("1.0");

        return ide;
    }

    private TNFe.InfNFe.Emit preencheEmitente(Empresa empresa) {
        TNFe.InfNFe.Emit emit = new TNFe.InfNFe.Emit();
        emit.setCNPJ(empresa.getCnpj());
        emit.setXNome(empresa.getRazaoSocial());
        emit.setXFant(empresa.getNomeFantasia());
        TEnderEmi enderEmit = new TEnderEmi();
        enderEmit.setXLgr(empresa.getLogradouro());
        enderEmit.setNro(empresa.getNumero());
        if (!"".equals(empresa.getComplemento())) {
            enderEmit.setXCpl(empresa.getComplemento());
        }
        enderEmit.setXBairro(empresa.getBairro());
        enderEmit.setCMun(empresa.getCodigoCidade());
        enderEmit.setXMun(empresa.getCidade());
        enderEmit.setUF(TUfEmi.valueOf(config.getEstado().toString()));
        enderEmit.setCEP(empresa.getCep());
        enderEmit.setCPais("1058");
        enderEmit.setXPais("Brasil");
        if (empresa.getTelefone() != null && !"".equals(empresa.getTelefone())) {
            enderEmit.setFone(empresa.getTelefone());
        }
        emit.setEnderEmit(enderEmit);
        emit.setIE(empresa.getInscricaoEstadual());
        emit.setCRT("1");
        return emit;
    }

    private TNFe.InfNFe.Dest preencheDestinatario(Venda venda) {
        TNFe.InfNFe.Dest dest = new TNFe.InfNFe.Dest();
        dest.setCPF(venda.getCpfCliente());
        dest.setXNome(venda.getNomeCliente());
        return dest;
    }

    private List<TNFe.InfNFe.Det> preencheDet(List<Item> itens, Empresa empresa) {
        valoresIcms.clear();
        List<TNFe.InfNFe.Det> dets = new ArrayList<>();
        itens.forEach(item -> {
            TNFe.InfNFe.Det det = new TNFe.InfNFe.Det();
            det.setNItem(String.valueOf(Integer.parseInt(item.getItemSeguencia())));
            det.setProd(preencheProduto(item));
            det.setImposto(preencheImposto(item, empresa));
            dets.add(det);
        });
        return dets;
    }

    private TNFe.InfNFe.Det.Prod preencheProduto(Item item) {
        TNFe.InfNFe.Det.Prod prod = new TNFe.InfNFe.Det.Prod();
        prod.setCProd(item.getReferencia());
        String codigoBarra = item.getCodigoBarras();
        if (codigoBarra != null && !codigoBarra.isEmpty()) {
            codigoBarra = codigoBarra.startsWith("789") ? codigoBarra : "SEM GTIN";
        }
        prod.setXProd(item.getDescricao().trim());
        prod.setNCM(item.getNcm());
        if (!"".equals(item.getCest())) {
            prod.setCEST(item.getCest());
            prod.setIndEscala("S");
        }
        prod.setCFOP(item.getCfopSaidaDentroEstado().replace(".", ""));
        prod.setUCom(item.getUnidadeVenda());
        prod.setQCom(df.format(item.getQuantidade()).replaceFirst(Pattern.quote("."), ""));
        prod.setVUnCom(df.format(item.getValorUnitario()));
        prod.setVProd(df.format(item.getValorTotalLiquido()));
        prod.setCEAN(codigoBarra);
        prod.setCEANTrib(codigoBarra);
        prod.setUTrib(item.getUnidadeVenda());
        prod.setQTrib(df.format(item.getQuantidade()).replaceFirst(Pattern.quote("."), ""));
        prod.setVUnTrib(String.valueOf(item.getValorTotalLiquido()));
        if (item.getDesconto() != 0) {
            prod.setVDesc(df.format(item.getDesconto()));
        }
        if (item.getAcrescimo() != 0) {
            prod.setVOutro(df.format(item.getAcrescimo()));
        }
        prod.setIndTot("1");
        return prod;
    }

    private TNFe.InfNFe.Det.Imposto preencheImposto(Item item, Empresa empresa) {
        Imposto imposto = new Imposto();

        ICMS icms = new ICMS();

        icms.setICMSSN102(preencherICMSSimplesNacional());
        if ("1".equals(empresa.getRegimeTributario())) {
            icms.setICMS00(preencherICMSRegimeNormal(item));
        }

        PIS pis = new PIS();
        pis.setPISNT(preencherPISSimpleNacional());
        if ("1".equals(empresa.getRegimeTributario())) {
            pis.setPISAliq(preencherPISRegimeNormal(item));
        }

        COFINS cofins = new COFINS();
        cofins.setCOFINSNT(preencherCOFINSSimpleNacional());
        if ("1".equals(empresa.getRegimeTributario())) {
            cofins.setCOFINSAliq(preencherCOFINSRegimeNormal(item));
        }

        JAXBElement<ICMS> icmsElement = new JAXBElement<>(new QName("ICMS"), ICMS.class, icms);
        imposto.getContent().add(icmsElement);

        JAXBElement<PIS> pisElement = new JAXBElement<>(new QName("PIS"), PIS.class, pis);
        imposto.getContent().add(pisElement);

        JAXBElement<COFINS> cofinsElement = new JAXBElement<>(new QName("COFINS"), COFINS.class, cofins);
        imposto.getContent().add(cofinsElement);
        return imposto;
    }

    private TNFe.InfNFe.Det.Imposto.ICMS.ICMS00 preencherICMSRegimeNormal(Item item) {
        TNFe.InfNFe.Det.Imposto.ICMS.ICMS00 icms00 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS00();
        icms00.setOrig("0");
        icms00.setCST("00");
        icms00.setModBC("0");
        icms00.setVBC(String.valueOf(item.getValorTotalLiquido()));
        icms00.setPICMS(String.valueOf(item.getIcms()));
        double valorIcms = item.getValorTotalLiquido() * (item.getIcms() / 100);
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
        decimalFormat.setRoundingMode(RoundingMode.UP);
        this.valoresIcms.add(valorIcms);
        icms00.setVICMS(decimalFormat.format(valorIcms).replace(",", "."));
        return icms00;
    }

    private ICMS.ICMSSN102 preencherICMSSimplesNacional() {
        ICMS.ICMSSN102 icms102 = new ICMS.ICMSSN102();
        icms102.setOrig("0");
        icms102.setCSOSN("102");
        return icms102;
    }

    private TNFe.InfNFe.Det.Imposto.PIS.PISAliq preencherPISRegimeNormal(Item item) {
        TNFe.InfNFe.Det.Imposto.PIS.PISAliq pisAliq = new TNFe.InfNFe.Det.Imposto.PIS.PISAliq();
        pisAliq.setCST(String.valueOf(item.getPis()));
        pisAliq.setVBC(String.valueOf(item.getValorTotalLiquido()));
        pisAliq.setPPIS("0.00");
        pisAliq.setVPIS("0.00");
        return pisAliq;
    }

    private PIS.PISNT preencherPISSimpleNacional() {
        PIS.PISNT pisnt = new PIS.PISNT();
        pisnt.setCST("07");
        return pisnt;
    }

    private COFINS.COFINSAliq preencherCOFINSRegimeNormal(Item item) {
        COFINS.COFINSAliq cofinsAliq = new COFINS.COFINSAliq();
        cofinsAliq.setCST(String.valueOf(item.getCofins()));
        cofinsAliq.setVBC(String.valueOf(item.getValorTotalLiquido()));
        cofinsAliq.setPCOFINS("0.00");
        cofinsAliq.setVCOFINS("0.00");
        return cofinsAliq;
    }

    private COFINS.COFINSNT preencherCOFINSSimpleNacional() {
        COFINS.COFINSNT cofins = new COFINS.COFINSNT();
        cofins.setCST("07");
        return cofins;
    }

    private TNFe.InfNFe.Pag preenchePag(List<FormaPagamento> formaPagamentos) {
        TNFe.InfNFe.Pag pag = new TNFe.InfNFe.Pag();
        formaPagamentos.forEach(f -> {
            TNFe.InfNFe.Pag.DetPag detPag = new TNFe.InfNFe.Pag.DetPag();
            if (f.getTipoPagamento().equals("DINHEIRO")) {
                detPag.setTPag("01");
                detPag.setVPag(df.format(f.getValorPagamentoLiquido()));
            }
            if (f.getTipoPagamento().equals("CARTAO")) {
                detPag.setTPag(f.getDescricaoPagamento().contains("DEBITO") ? "04" : "03");
                detPag.setVPag(df.format(f.getValorPagamentoLiquido()));
                TNFe.InfNFe.Pag.DetPag.Card card = new TNFe.InfNFe.Pag.DetPag.Card();
                card.setTpIntegra("1");
                card.setCNPJ("99999999999999");
                card.setTBand("99");
                card.setCAut(f.getNsutef());
                detPag.setCard(card);
            }
            pag.getDetPag().add(detPag);
        });
        return pag;
    }

    private TNFe.InfNFe.Total preencheTotal(List<Item> itens, Empresa empresa) {
        TNFe.InfNFe.Total total = new TNFe.InfNFe.Total();
        TNFe.InfNFe.Total.ICMSTot icmstot = new TNFe.InfNFe.Total.ICMSTot();

        double valorTotalItens = itens.stream().mapToDouble(Item::getValorTotalLiquido).sum();
        double valorAcrescimo = itens.stream().mapToDouble(Item::getAcrescimo).sum();
        double valorTotalIcms = this.valoresIcms.stream().mapToDouble(Double::doubleValue).sum();
        double valorTotalDesconto = itens.stream().mapToDouble(Item::getDesconto).sum();

        icmstot.setVBC("1".equals(empresa.getRegimeTributario()) ? df.format(valorTotalItens) : "0.00");
        icmstot.setVICMS("1".equals(empresa.getRegimeTributario()) ? df.format(valorTotalIcms) : "0.00");
        icmstot.setVICMSDeson("0.00");
        icmstot.setVFCP("0.00");
        icmstot.setVFCPST("0.00");
        icmstot.setVFCPSTRet("0.00");
        icmstot.setVBCST("0.00");
        icmstot.setVST("0.00");
        icmstot.setVProd(df.format(valorTotalItens));
        icmstot.setVFrete("0.00");
        icmstot.setVSeg("0.00");
        icmstot.setVDesc(df.format(valorTotalDesconto));
        icmstot.setVII("0.00");
        icmstot.setVIPI("0.00");
        icmstot.setVIPIDevol("0.00");
        icmstot.setVPIS("0.00");
        icmstot.setVCOFINS("0.00");
        icmstot.setVOutro(df.format(valorAcrescimo));
        icmstot.setVNF(df.format(valorTotalItens));
        total.setICMSTot(icmstot);

        return total;
    }

    private TNFe.InfNFe.Transp preencheTransporte() {
        TNFe.InfNFe.Transp transp = new TNFe.InfNFe.Transp();
        transp.setModFrete("9");
        return transp;
    }

    private String preencheQRCode(TEnviNFe enviNFe, String idToken, String csc, String chave) throws NfeException, NoSuchAlgorithmException {
        return NFCeUtil.getCodeQRCode(
                chave,
                config.getAmbiente().getCodigo(),
                idToken,
                csc,
                WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_QRCODE));
    }

}
