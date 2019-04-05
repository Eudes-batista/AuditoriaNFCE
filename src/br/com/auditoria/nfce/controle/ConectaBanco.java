package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.Configuracao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JOptionPane;

public class ConectaBanco {

    private Connection conn;
    private ResultSet rs;
    private Statement stm;
    private final String driver = "com.mysql.jdbc.Driver";
    private final String caminho = "jdbc:mysql://";
    private final Configuracao configuracao;

    public ConectaBanco(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public boolean conectar() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro na comunicação com o driver do banco de dados");
        }
        try {
            String banco = configuracao.getBanco(), porta = configuracao.getPorta(), usuario = configuracao.getUsuario(), senha = configuracao.getSenha();
            String url = caminho + configuracao.getHost() + ":" + porta + "/" + banco;
            String Path = url + "?user=" + usuario + "&password=" + senha;
            Properties properties = new Properties();
            properties.put("connectTimeout", "1000");
            conn = DriverManager.getConnection(Path, properties);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "não conseguiu comunicar com o banco de dados\npode está sem comunicação de rede ou usuario do mysq não está autorizado para comunicação externas ");
        }
        return false;
    }

    public void desconectar() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao desconectar no banco de dados\n detalhe do erro: " + ex.getMessage());
            }
        }
    }

    public boolean executarSQL(String sql) {
        if (conn != null) {
            try {
                stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stm.execute(sql);
                rs = stm.getResultSet();
                return true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro na execução do sql\n detralhe do sql: " + sql);
            }
        }
        return false;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

}
