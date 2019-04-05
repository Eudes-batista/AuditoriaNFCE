
package br.com.auditoria.nfce.modelo;

import java.util.Objects;


public class Configuracao {

    private String host;
    private String banco;
    private String porta;
    private String usuario;
    private String senha;

    public Configuracao() {
    }

    public Configuracao(String host, String banco, String porta, String usuario, String senha) {
        this.host = host;
        this.banco = banco;
        this.porta = porta;
        this.usuario = usuario;
        this.senha = senha;
    }

    
    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.banco);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Configuracao other = (Configuracao) obj;
        return Objects.equals(this.banco, other.banco);
    }

    @Override
    public String toString() {
        return "Configuracao{" + "host=" + host + ", banco=" + banco + ", porta=" + porta + ", usuario=" + usuario + ", senha=" + senha + '}';
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    
}
