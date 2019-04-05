package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.Configuracao;
import br.com.auditoria.nfce.modelo.ConfiguracaoEnvio;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class GerenciaArquivo {

    private Configuracao configuracao;
    private File file;
    private File fileEnvio;
    private File fileLog;

    public GerenciaArquivo() {
    }

    public GerenciaArquivo(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public void crirarArquivo() {
        file = new File("config.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao crirar o Arquivo \n detalhe do erro: " + ex.getMessage());
            }
        }
        fileEnvio = new File("configEnvio.txt");
        if (!fileEnvio.exists()) {
            try {
                fileEnvio.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao crirar o Arquivo \n detalhe do erro: " + ex.getMessage());
            }
        }
        fileLog = new File("configLog.txt");
        if (!fileLog.exists()) {
            try {
                fileLog.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao criar arquivo de log");
            }
        }
    }

    public void salvar() {
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println(configuracao.getHost());
            pw.println(configuracao.getBanco());
            pw.println(configuracao.getPorta());
            pw.println(configuracao.getUsuario());
            pw.println(configuracao.getSenha());
            pw.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar as configurações");
        }
    }

    public Configuracao buscarConfiguracao() {
        crirarArquivo();
        try {
            List<String> configuracoes = Files.lines(file.toPath()).collect(Collectors.toList());
            if (configuracao == null) {
                configuracao = new Configuracao();
            }
            if (configuracoes != null && !configuracoes.isEmpty()) {
                configuracao.setHost(configuracoes.get(0));
                configuracao.setBanco(configuracoes.get(1));
                configuracao.setPorta(configuracoes.get(2));
                configuracao.setUsuario(configuracoes.get(3));
                configuracao.setSenha(configuracoes.get(4));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler as configurações");
        }
        return configuracao;
    }

    public void salvarConfiguracaoEnvio(ConfiguracaoEnvio configuracaoEnvio) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileEnvio))) {
            printWriter.println(configuracaoEnvio.getCaminhoXML());
            printWriter.println(configuracaoEnvio.getEmailContador());
            printWriter.println(configuracaoEnvio.getDiaEnvio());
            printWriter.println(configuracaoEnvio.getHorario());
            printWriter.println(configuracaoEnvio.isCertificadoA3());
            printWriter.println(configuracaoEnvio.getCaminhoCertificado());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public ConfiguracaoEnvio buscarConfiguracaoDeEnvio() {
        ConfiguracaoEnvio configuracaoEnvio = null;
        this.crirarArquivo();
        try {
            List<String> linhas = Files.readAllLines(fileEnvio.toPath());
            if (linhas != null && !linhas.isEmpty()) {
                configuracaoEnvio = new ConfiguracaoEnvio();
                configuracaoEnvio.setCaminhoXML(linhas.get(0));
                configuracaoEnvio.setEmailContador(linhas.get(1));
                configuracaoEnvio.setDiaEnvio(linhas.get(2));
                configuracaoEnvio.setHorario(linhas.get(3));
                configuracaoEnvio.setCertificadoA3(linhas.get(4).equals("true"));
                configuracaoEnvio.setCaminhoCertificado(linhas.get(5));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return configuracaoEnvio;
    }

    public void salvarInformacoesLog(String log) throws FileNotFoundException, IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileLog, true))) {
            printWriter.println();
            printWriter.println(log);
            printWriter.flush();
        }
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

}
