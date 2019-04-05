
package br.com.auditoria.nfce.modelo;


public class ConfiguracaoEnvio {

    private String caminhoXML;
    private String emailContador;
    private String diaEnvio;
    private String horario;
    private boolean certificadoA3;
    private String caminhoCertificado;

    public String getCaminhoXML() {
        return caminhoXML;
    }

    public void setCaminhoXML(String caminhoXML) {
        this.caminhoXML = caminhoXML;
    }

    public String getEmailContador() {
        return emailContador;
    }

    public void setEmailContador(String emailContador) {
        this.emailContador = emailContador;
    }

    public String getDiaEnvio() {
        return diaEnvio;
    }

    public void setDiaEnvio(String diaEnvio) {
        this.diaEnvio = diaEnvio;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isCertificadoA3() {
        return certificadoA3;
    }

    public void setCertificadoA3(boolean certificadoA3) {
        this.certificadoA3 = certificadoA3;
    }

    public String getCaminhoCertificado() {
        return caminhoCertificado;
    }

    public void setCaminhoCertificado(String caminhoCertificado) {
        this.caminhoCertificado = caminhoCertificado;
    }

    @Override
    public String toString() {
        return "ConfiguracaoEnvio{" + "caminhoXML=" + caminhoXML + ", emailContador=" + emailContador + ", diaEnvio=" + diaEnvio + ", horario=" + horario + ", certificadoA3=" + certificadoA3 + ", caminhoCertificado=" + caminhoCertificado + '}';
    }

    
       
}
