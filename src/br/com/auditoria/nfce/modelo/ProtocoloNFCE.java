
package br.com.auditoria.nfce.modelo;

import java.util.Objects;


public class ProtocoloNFCE {

    private String id;
    private String ambiente;
    private String versao;
    private String chave;
    private String recibo;
    private String protocolo;
    private String digival;
    private String cStat;
    private String motivo;

    public ProtocoloNFCE() {
    }

    public ProtocoloNFCE(String id, String ambiente, String versao, String chave, String recibo, String protocolo, String digival, String cStat, String motivo) {
        this.id = id;
        this.ambiente = ambiente;
        this.versao = versao;
        this.chave = chave;
        this.recibo = recibo;
        this.protocolo = protocolo;
        this.digival = digival;
        this.cStat = cStat;
        this.motivo = motivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getDigival() {
        return digival;
    }

    public void setDigival(String digival) {
        this.digival = digival;
    }

    public String getcStat() {
        return cStat;
    }

    public void setcStat(String cStat) {
        this.cStat = cStat;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final ProtocoloNFCE other = (ProtocoloNFCE) obj;
        return Objects.equals(this.protocolo, other.protocolo);
    }

    @Override
    public String toString() {
        return "ProtocoloNFCE{" + "id=" + id + ", ambiente=" + ambiente + ", versao=" + versao + ", chave=" + chave + ", recibo=" + recibo + ", protocolo=" + protocolo + ", digival=" + digival + ", cStat=" + cStat + ", motivo=" + motivo + '}';
    }
    
    
}
