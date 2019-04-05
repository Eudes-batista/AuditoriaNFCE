package br.com.auditoria.nfce;

import br.com.auditoria.nfce.controle.ConectaBanco;
import br.com.auditoria.nfce.controle.GerenciaArquivo;
import br.com.auditoria.nfce.modelo.Configuracao;
import br.com.auditoria.nfce.modelo.ConfiguracaoEnvio;
import br.com.auditoria.nfce.visao.FrmConectaBanco;
import br.com.auditoria.nfce.visao.FrmConfigurarEnvio;
import br.com.auditoria.nfce.visao.SystemTrayAuditoriaNFCE;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import javax.swing.JOptionPane;

public class Principal {

    public static void main(String[] args) {
        Configuracao configuracao = buscarConfiguracoes();
        ConectaBanco conecta = new ConectaBanco(configuracao);
        if (!conecta.conectar()) {
            FrmConectaBanco frmConectaBanco = new FrmConectaBanco();
            frmConectaBanco.setVisible(true);
            return;
        }
        conecta.desconectar();
        ConfiguracoesNfe config = null;
        try {
            config = ConfiguracaoNFCE.iniciaConfiguracoes();
            Nfe.statusServico(config, DocumentoEnum.NFCE);
        } catch (CertificadoException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        ConfiguracaoEnvio configuracoesEnivo = buscarConfiguracoesEnivo();
        if (configuracoesEnivo == null) {
            FrmConfigurarEnvio frmConfigurarEnvio = new FrmConfigurarEnvio();
            frmConfigurarEnvio.setConfig(config).setVisible(true);
        }
        SystemTrayAuditoriaNFCE systemTrayAuditoriaNFCE = new SystemTrayAuditoriaNFCE(config);
        systemTrayAuditoriaNFCE.iniciarVerificacao();
    }

    public static Configuracao buscarConfiguracoes() {
        GerenciaArquivo gerenciaArquivo = new GerenciaArquivo();
        gerenciaArquivo.crirarArquivo();
        return gerenciaArquivo.buscarConfiguracao();
    }

    public static ConfiguracaoEnvio buscarConfiguracoesEnivo() {
        GerenciaArquivo gerenciaArquivo = new GerenciaArquivo();
        gerenciaArquivo.crirarArquivo();
        return gerenciaArquivo.buscarConfiguracaoDeEnvio();
    }
}
