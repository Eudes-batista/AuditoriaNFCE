package br.com.auditoria.nfce.visao;

import br.com.auditoria.nfce.ConfiguracaoNFCE;
import br.com.auditoria.nfce.controle.GerenciaArquivo;
import br.com.auditoria.nfce.modelo.ConfiguracaoEnvio;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrmConfigurarEnvio extends javax.swing.JFrame {

    private GerenciaArquivo gerenciaArquivo;
    private ConfiguracoesNfe config;
    private boolean editar = false;

    public FrmConfigurarEnvio() {
        gerenciaArquivo = new GerenciaArquivo();
        initComponents();
        this.gerenciaArquivo.crirarArquivo();
        ConfiguracaoEnvio configuracaoDeEnvio = this.gerenciaArquivo.buscarConfiguracaoDeEnvio();
        if (configuracaoDeEnvio != null) {
            editCaminhoXML.setText(configuracaoDeEnvio.getCaminhoXML());
            editEmailContador.setText(configuracaoDeEnvio.getEmailContador());
            editDias.setText(configuracaoDeEnvio.getDiaEnvio());
            editCertificadoA1.setText(configuracaoDeEnvio.getCaminhoCertificado());
            editar = true;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        editCaminhoXML = new javax.swing.JTextField();
        jButtonBuscarCaminho = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        editEmailContador = new javax.swing.JTextField();
        jButtonSalvar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        editCertificadoA1 = new javax.swing.JTextField();
        jButtonBuscarCaminho1 = new javax.swing.JButton();
        editDias = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Caminho dos XMLS");

        editCaminhoXML.setText("C:\\SigmaSat\\exe\\xml\\Sig\\cnpj\\nfce");
        editCaminhoXML.setToolTipText("C:\\SigmaSat\\exe\\xml\\Sig\\cnpj\\nfce");

        jButtonBuscarCaminho.setText("...");
        jButtonBuscarCaminho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarCaminhoActionPerformed(evt);
            }
        });

        jLabel3.setText("Email do Contador");

        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });

        jLabel5.setText("Dia");

        jCheckBox1.setText("Certificado A3");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Caminho do Certificado");

        jButtonBuscarCaminho1.setText("...");
        jButtonBuscarCaminho1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarCaminho1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jCheckBox1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSair, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(editDias, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(editCaminhoXML, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBuscarCaminho))
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(editCertificadoA1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editEmailContador, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBuscarCaminho1)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editCaminhoXML, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscarCaminho, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editEmailContador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editCertificadoA1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscarCaminho1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSalvar)
                    .addComponent(jButtonSair))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Configuração de Envio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(473, 402));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuscarCaminhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarCaminhoActionPerformed
        JFileChooser jFileChooser = new JFileChooser("C:\\SIGMASAT\\EXE\\XML\\");
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retorno = jFileChooser.showOpenDialog(new JDialog());
        if (retorno == JFileChooser.APPROVE_OPTION) {
            String caminhoXML = jFileChooser.getSelectedFile().getAbsolutePath();
            editCaminhoXML.setText(caminhoXML);
        }
    }//GEN-LAST:event_jButtonBuscarCaminhoActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonSairActionPerformed

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        ConfiguracaoEnvio configuracaoDeEnvio = new ConfiguracaoEnvio();
        configuracaoDeEnvio.setCaminhoXML(editCaminhoXML.getText());
        configuracaoDeEnvio.setEmailContador(editEmailContador.getText());
        configuracaoDeEnvio.setDiaEnvio(String.valueOf(editDias.getText()));
        configuracaoDeEnvio.setHorario("08:00");
        configuracaoDeEnvio.setCertificadoA3(jCheckBox1.isSelected());
        configuracaoDeEnvio.setCaminhoCertificado(editCertificadoA1.getText());
        gerenciaArquivo.salvarConfiguracaoEnvio(configuracaoDeEnvio);
        if (this.config == null) {
            try {
                this.config = ConfiguracaoNFCE.iniciaConfiguracoes();
                Nfe.statusServico(config, DocumentoEnum.NFCE);
            } catch (CertificadoException ex) {
                JOptionPane.showMessageDialog(null, ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        if (!this.editar) {
            SystemTrayAuditoriaNFCE systemTrayAuditoriaNFCE = new SystemTrayAuditoriaNFCE(this.config);
            systemTrayAuditoriaNFCE.iniciarVerificacao();
        }
        this.editar = false;
        dispose();
    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        editCertificadoA1.setEnabled(true);
        if (jCheckBox1.isSelected()) {
            editCertificadoA1.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButtonBuscarCaminho1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarCaminho1ActionPerformed
        JFileChooser jFileChooser = new JFileChooser("C:\\SIGMASAT\\EXE\\XML\\");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("*.pfx,*.p12", "pfx", "p12"));
        int retorno = jFileChooser.showOpenDialog(new JDialog());
        if (retorno == JFileChooser.APPROVE_OPTION) {
            String caminhoXML = jFileChooser.getSelectedFile().getAbsolutePath();
            editCertificadoA1.setText(caminhoXML);
        }
    }//GEN-LAST:event_jButtonBuscarCaminho1ActionPerformed

    public FrmConfigurarEnvio setConfig(ConfiguracoesNfe config) {
        this.config = config;
        return this;
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmConfigurarEnvio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new FrmConfigurarEnvio().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField editCaminhoXML;
    private javax.swing.JTextField editCertificadoA1;
    private javax.swing.JTextField editDias;
    private javax.swing.JTextField editEmailContador;
    private javax.swing.JButton jButtonBuscarCaminho;
    private javax.swing.JButton jButtonBuscarCaminho1;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
