package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.ConfiguracaoEnvio;
import br.com.auditoria.nfce.modelo.Venda;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TEnviNFe;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;

public class ControleArquivoXML {

    private final ConfiguracoesNfe config;
    private String data;

    public ControleArquivoXML(ConfiguracoesNfe config) {
        this.config = config;
    }

    public void realizarConsultaMensal() throws SQLException, IOException, CertificadoException, Exception {

        ControleVenda controleVenda = new ControleVenda();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        int dia = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        data = ano + "-" + String.format("%02d", mes) + "-" + String.format("%02d", dia);

        List<Venda> vendasBanco = controleVenda.listarVendas(ano + "-" + mes + "-01", data);
        if (verificarSeJaFoiFeitoAuditoria(ano + String.format("%02d", mes), vendasBanco.size())) {
            return;
        }
        List<String> vendasChave = vendasBanco.stream().map(venda -> venda.getChave() + ".xml").collect(Collectors.toList());

        ConfiguracaoEnvio configuracaoDeEnvio = new GerenciaArquivo().buscarConfiguracaoDeEnvio();

        String caminhoXML = configuracaoDeEnvio.getCaminhoXML();

        File file = new File(caminhoXML + "\\" + ano + String.format("%02d", mes) + "\\NFCe\\");

        String[] files = file.list();
        List<String> asList = Arrays.asList(files);

        List<String> xmls = asList.stream()
                .map(xml -> xml.replace("-nfe", ""))
                .collect(Collectors.toList());

        xmls.removeAll(vendasChave);
        apagarXmlQueNaoContemVenda(xmls, file);
        adicionarVendaExistente(vendasChave, caminhoXML, data.split("-")[0] + data.split("-")[1]);
    }

    public void realizarConsultaDiaria() throws SQLException, IOException, CertificadoException, Exception {

        ControleVenda controleVenda = new ControleVenda();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        data = ano + "-" + String.format("%02d", mes);

        Venda ultimaVenda = controleVenda.buscarUltimoCupom();
        if (ultimaVenda == null) {
            return;
        }
        String chaveUltimaVenda = ultimaVenda.getChave();

        ConfiguracaoEnvio configuracaoDeEnvio = new GerenciaArquivo().buscarConfiguracaoDeEnvio();

        String caminhoXML = configuracaoDeEnvio.getCaminhoXML();

        caminhoXML += "\\" + data.split("-")[0] + data.split("-")[1] + "\\NFCe\\";

        adicionarVendaExistente(chaveUltimaVenda, caminhoXML, data.split("-")[0] + data.split("-")[1]);
    }

    private void apagarXmlQueNaoContemVenda(List<String> xmls, File file) throws IOException {
        if (!xmls.isEmpty()) {
            for (String nota : xmls) {
                String xml = nota.replace(".xml", "-nfe.xml");
                boolean deleteIfExists = Files.deleteIfExists(file.toPath().resolve(xml));
                System.out.println("deleteIfExists = " + deleteIfExists);
            }
        }
    }

    private void adicionarVendaExistente(List<String> vendaChave, String caminhoXML, String data) throws IOException, NfeException, JAXBException, Exception {
        String xml, caminho;
        for (String nota : vendaChave) {
            xml = nota.replace(".xml", "-nfe.xml");
            caminho = caminhoXML + "\\" + data + "\\NFCe\\" + xml;
            adicionarProtocolo(caminho, nota, data);
        }
    }

    private void adicionarVendaExistente(String ultimaVenda, String caminhoXML, String data) throws IOException, NfeException, JAXBException, Exception {
        String xml, caminho;
        xml = ultimaVenda + "-nfe.xml";
        caminho = caminhoXML + xml;
        if (Files.exists(Paths.get(caminho))) {
            adicionarProtocolo(caminho, ultimaVenda, data);
        }
    }

    private void adicionarProtocolo(String caminho, String xml, String data) throws IOException, NfeException, JAXBException, Exception {
        try (FileReader fileReader = new FileReader(caminho); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.ready()) {
                String linhas = bufferedReader.readLine();
                String chave = xml.replace(".xml", "");
                if (!linhas.contains("nProt")) {
                    String xmlFinal;
                    if (!"Signature".contains(linhas)) {
                        ControleNFCE controleNFCE = new ControleNFCE(config);
                        xmlFinal = controleNFCE.emitiNFCE(chave);
                    } else {
                        String leXml = XmlNfeUtil.leXml(caminho);
                        TEnviNFe tEnviNFe = XmlNfeUtil.xmlToObject(leXml, TEnviNFe.class);
                        TRetConsSitNFe consultaXml = Nfe.consultaXml(config, chave, DocumentoEnum.NFCE);
                        xmlFinal = XmlNfeUtil.criaNfeProc(tEnviNFe, consultaXml.getProtNFe());
                    }
                    salvarXML(xmlFinal, chave, data);
                } else {
                    salvarXML(linhas, chave, data);
                }
            }
        }
    }

    private void salvarXML(String xml, String chave, String data) throws FileNotFoundException, IOException {
        File file = new File("xmls/" + data + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
        try (PrintWriter pw = new PrintWriter(file.getAbsolutePath() + "\\" + chave + ".xml")) {
            pw.println(xml.trim());
            pw.flush();
        }
    }

    private boolean verificarSeJaFoiFeitoAuditoria(String data, long quantidadeVenda) {
        File file = new File("xmls/" + data);
        return file.exists() && file.list().length == quantidadeVenda;
    }

    public String getData() {
        return data;
    }

}
