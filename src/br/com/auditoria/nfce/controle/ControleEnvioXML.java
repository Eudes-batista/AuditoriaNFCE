package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.ConfiguracaoEnvio;
import br.com.auditoria.nfce.modelo.Empresa;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class ControleEnvioXML {

    public boolean enviar(String data) throws IOException {
        if (compactarArquivos(data)) {
            try {
                ConfiguracaoEnvio configuracao = new GerenciaArquivo().buscarConfiguracaoDeEnvio();
                EmailAttachment attachment = new EmailAttachment();
                attachment.setPath("xmls.zip");
                attachment.setDisposition(EmailAttachment.ATTACHMENT);

                EmailAttachment attachment2 = new EmailAttachment();
                attachment2.setPath("FATURAMENTO.pdf");
                attachment2.setDisposition(EmailAttachment.ATTACHMENT);

                MultiPartEmail email = new MultiPartEmail();
                email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
                email.setSmtpPort(587);
                email.setAuthentication("zoomtecnologiawlc@gmail.com", "wz30551404");
                email.addTo(configuracao.getEmailContador(), ""); //destinatário
                String dataFormatada[] = data.split(Pattern.quote("-"));
                email.setSubject("Faturamento do mês " + dataFormatada[0] + dataFormatada[1]); // assunto do e-mail
                Empresa empresa = new EmpresaControle().buscarEmpresa();
                String cnpj = empresa.getCnpj();
                String razaoSocial = empresa.getRazaoSocial();
                email.setFrom("zoomtecnologiawlc@gmail.com", razaoSocial + " - " + cnpj); // remetente
                email.setMsg(razaoSocial + " - " + cnpj + " Caixa: " + empresa.getNumeroSerie()); //conteudo do e-mail
                email.setTLS(true);
                email.attach(attachment);
                email.attach(attachment2);
                email.send(); //envia o e-mail
                return true;
            } catch (EmailException ex) {
                new GerenciaArquivo().salvarInformacoesLog("Erro ao enviar Arquivo xml: detalhes " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Não conseguiu enviar as vendas para o email do contador\n verifique a conexão com a internet");
            }
        }

        return false;
    }

    private boolean compactarArquivos(String data) {
        try (FileOutputStream fos = new FileOutputStream("xmls.zip")) {
            String caminho = "";
            String datas[] = null;
            datas = data.split(Pattern.quote("-"));
            String sistemaBara = !System.getProperty("os.name").toUpperCase().contains("Windows".toUpperCase()) ? "/" : "\\";
            caminho = "xmls" + sistemaBara + datas[0] + datas[1] + sistemaBara;
            File file = new File(caminho);
            if (file.exists()) {
                File[] list = file.listFiles();
                try (ZipOutputStream zipOut = new ZipOutputStream(fos)) {
                    for (File fileName : list) {
                        if (fileName.getName().split(Pattern.quote("."))[1].toLowerCase().equals("xml")) {
                            String linha = null;
                            BufferedReader bufferedReader = Files.newBufferedReader(fileName.toPath());
                            while (bufferedReader.ready()) {
                                linha = bufferedReader.readLine();
                            }
                            zipOut.putNextEntry(new ZipEntry(fileName.getName()));
                            zipOut.write(linha.getBytes());
                            zipOut.flush();
                            zipOut.closeEntry();
                        }
                    }
                    new GeraFaturamento().gerarFaturamento(caminho, "Faturamento do mês - " + data);
                    return true;
                } catch (Exception ex) {
                    new GerenciaArquivo().salvarInformacoesLog("Erro ao compactar arquivos xml: detalhes " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Erro ao compactar arquivos xml.");
                }
            }

        } catch (IOException ex) {
            try {
                new GerenciaArquivo().salvarInformacoesLog("Erro ao compactar arquivos xml: detalhes " + ex.getMessage());
            } catch (IOException ex1) {
                JOptionPane.showMessageDialog(null, "Erro ao escrever log");
            }
            JOptionPane.showMessageDialog(null, "Erro ao compactar arquivos xml.");
        }
        return false;
    }

}
