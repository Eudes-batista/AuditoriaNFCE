package br.com.auditoria.nfce.visao;

import br.com.auditoria.nfce.controle.ControleArquivoXML;
import br.com.auditoria.nfce.controle.ControleEnvioXML;
import br.com.auditoria.nfce.controle.GerenciaArquivo;
import br.com.auditoria.nfce.modelo.ConfiguracaoEnvio;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class SystemTrayAuditoriaNFCE {

    private final ConfiguracoesNfe config;

    public SystemTrayAuditoriaNFCE(ConfiguracoesNfe iniciaConfiguracoes) {
        this.config = iniciaConfiguracoes;
    }
    private boolean enviou;

    public void iniciarVerificacao() {

        final TrayIcon trayIcon; // declarando uma constante do tipo TrayIcon

        // Aqui vamos testar se o recurso é suportado
        if (SystemTray.isSupported()) {

            //declarando uma variavel  do tipo SystemTray
            SystemTray tray = SystemTray.getSystemTray();
            //declarando uma variavel  do tipo Image que contera a imagem tray.gif
            Image image = Toolkit.getDefaultToolkit().getImage(getImage());

            ActionListener sair = e -> System.exit(0);
            ActionListener configurarBanco = e -> new FrmConectaBanco().setVisible(true);
            ActionListener configurarEnvio = e -> new FrmConfigurarEnvio().setConfig(config).setVisible(true);
            ActionListener envioManual = e -> new FrmEnvio().setVisible(true);

            PopupMenu popu = new PopupMenu();

            MenuItem itemSair = new MenuItem("Sair");
            MenuItem itemConfigurarBanco = new MenuItem("Configurar Banco");
            MenuItem itemConfigurarEnvio = new MenuItem("Configurar Envio");
            MenuItem itemEnvio = new MenuItem("Envio Manual");

            itemSair.addActionListener(sair);

            itemConfigurarBanco.addActionListener(configurarBanco);

            itemConfigurarEnvio.addActionListener(configurarEnvio);

            itemEnvio.addActionListener(envioManual);

            popu.add(itemConfigurarBanco);
            popu.add(itemConfigurarEnvio);
            popu.add(itemEnvio);
            popu.add(itemSair);

            //criando um objeto do tipo TrayIcon
            trayIcon = new TrayIcon(image, "Enviar Notas", popu);

            //Na linha a seguir a imagem a ser utilizada como icone sera redimensionada
            trayIcon.setImageAutoSize(true);

            //Seguida adicionamos os actions listeners
            trayIcon.addActionListener(sair);
            trayIcon.addActionListener(configurarBanco);
            trayIcon.addActionListener(configurarEnvio);
            //Tratamento de erros
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("Erro, TrayIcon não sera adicionado.");
            }

            Runnable runnableDiario = () -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        if (!verificarDiaDeEnvio() || this.enviou) {
                            ControleArquivoXML controleArquivoXML = new ControleArquivoXML(this.config);
                            controleArquivoXML.realizarConsultaDiaria();
                        }
                    } catch (InterruptedException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao inciar o tempo");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Na leitura do arquivo");
                    } catch (CertificadoException ex) {
                        JOptionPane.showMessageDialog(null, "Erro no certificado digital");
                    } catch (Exception ex) {
                        
                    }
                }
            };

            Runnable runnableMensal = () -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        if (verificarDiaDeEnvio() && !this.enviou) {
                            ControleArquivoXML controleArquivoXML = new ControleArquivoXML(this.config);
                            controleArquivoXML.realizarConsultaMensal();
                            this.enviou = new ControleEnvioXML().enviar(controleArquivoXML.getData());
                        }
                    } catch (InterruptedException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao inciar o tempo");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Na leitura do arquivo");
                    } catch (CertificadoException ex) {
                        JOptionPane.showMessageDialog(null, "Erro no certificado digital");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };

            Thread thread = new Thread(runnableDiario);
            Thread threadEnvio = new Thread(runnableMensal);
            thread.start();
            threadEnvio.start();
        } else {
            JOptionPane.showMessageDialog(null, "recurso ainda não esta disponível pra o seu sistema");
        }
    }

    private URL getImage() {
        return getClass().getResource("/br/com/auditoria/nfce/image/send.png");
    }

    private boolean verificarDiaDeEnvio() {
        GerenciaArquivo gerenciaArquivo = new GerenciaArquivo();
        ConfiguracaoEnvio configuracaoDeEnvio = gerenciaArquivo.buscarConfiguracaoDeEnvio();
        int diaDoMes = LocalDate.now().getDayOfMonth();
        return configuracaoDeEnvio.getDiaEnvio().equals(String.valueOf(diaDoMes));
    }

}
