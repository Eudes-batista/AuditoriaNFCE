package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.CabecalhoNota;
import br.com.auditoria.nfce.modelo.Empresa;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.util.ChaveUtil;
import java.time.format.DateTimeFormatter;

public class ChaveNotaControle {

    private final ConfiguracoesNfe config;
    private final CabecalhoNota cabecalhoNota;
    private final Empresa empresa;
    private ChaveUtil chaveUtil;
    private String chave;
    private final int digitoVerificador;

    public ChaveNotaControle(ConfiguracoesNfe config, CabecalhoNota cabecalhoNota, Empresa empresa) {
        this.config = config;
        this.cabecalhoNota = cabecalhoNota;
        this.empresa = empresa;
        String anoMes = cabecalhoNota.getDataEmissao().format(DateTimeFormatter.ofPattern("yyMM"));
        String modelo = String.format("%02d", cabecalhoNota.getModelo());
        String serie = String.format("%03d", cabecalhoNota.getSerie());
        String numeroNfe = String.format("%09d", cabecalhoNota.getNumeroNFCe());
        String tipoEmissao = String.valueOf(cabecalhoNota.getTipoDaNFe());
        String numeroVenda = String.valueOf(cabecalhoNota.getNumeroVenda()).substring(0, 8);
        chave = config.getEstado().getCodigoUF() + anoMes + empresa.getCnpj() + modelo + serie + numeroNfe + tipoEmissao + numeroVenda;
        digitoVerificador = modulo11(chave);
        chave += digitoVerificador;
    }

    public String gerarChaveDeAcesso() {
        return chave;
    }

    public String gerarChaveComDigitoVerificador() {
        return String.valueOf(digitoVerificador);
    }

    private int modulo11(String chave) {
        int total = 0;
        int peso = 2;

        for (int i = 0; i < chave.length(); i++) {
            total += (chave.charAt((chave.length() - 1) - i) - '0') * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }
        int resto = total % 11;
        return (resto == 0 || resto == 1) ? 0 : (11 - resto);
    }

}
