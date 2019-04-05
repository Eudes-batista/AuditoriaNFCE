
package br.com.auditoria.nfce.modelo;

import java.util.Objects;


public class Produto {
    private String referencia;
    private String codigoBarra;
    private String descricao;
    private String unidade;
    private String ncm;
    private String cest;
    private String cst;
    private String tributacao;
    private double icms;
    private double iss;
    private double pis;
    private double cofins;
    private String cfopSaidaDentroEstado;
    private String cfopSaidaForaEstado;

    public Produto() {
    }

    public Produto(String referencia, String codigoBarra, String descricao, String unidade, String ncm, String cest, String cst, String tributacao, double icms, double iss, double pis, double cofins, String cfopSaidaDentroEstado, String cfopSaidaForaEstado) {
        this.referencia = referencia;
        this.codigoBarra = codigoBarra;
        this.descricao = descricao;
        this.unidade = unidade;
        this.ncm = ncm;
        this.cest = cest;
        this.cst = cst;
        this.tributacao = tributacao;
        this.icms = icms;
        this.iss = iss;
        this.pis = pis;
        this.cofins = cofins;
        this.cfopSaidaDentroEstado = cfopSaidaDentroEstado;
        this.cfopSaidaForaEstado = cfopSaidaForaEstado;
    }

    public Produto(String referencia) {
        this.referencia = referencia;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getCest() {
        return cest;
    }

    public void setCest(String cest) {
        this.cest = cest;
    }

    public String getCst() {
        return cst;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    public String getTributacao() {
        return tributacao;
    }

    public void setTributacao(String tributacao) {
        this.tributacao = tributacao;
    }

    public double getIcms() {
        return icms;
    }

    public void setIcms(double icms) {
        this.icms = icms;
    }

    public double getIss() {
        return iss;
    }

    public void setIss(double iss) {
        this.iss = iss;
    }

    public double getPis() {
        return pis;
    }

    public void setPis(double pis) {
        this.pis = pis;
    }

    public double getCofins() {
        return cofins;
    }

    public void setCofins(double cofins) {
        this.cofins = cofins;
    }

    public String getCfopSaidaDentroEstado() {
        return cfopSaidaDentroEstado;
    }

    public void setCfopSaidaDentroEstado(String cfopSaidaDentroEstado) {
        this.cfopSaidaDentroEstado = cfopSaidaDentroEstado;
    }

    public String getCfopSaidaForaEstado() {
        return cfopSaidaForaEstado;
    }

    public void setCfopSaidaForaEstado(String cfopSaidaForaEstado) {
        this.cfopSaidaForaEstado = cfopSaidaForaEstado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.referencia);
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
        final Produto other = (Produto) obj;
        return Objects.equals(this.referencia, other.referencia);
    }

    @Override
    public String toString() {
        return "Produto{" + "referencia=" + referencia + ", codigoBarra=" + codigoBarra + ", descricao=" + descricao + ", unidade=" + unidade + ", ncm=" + ncm + ", cest=" + cest + ", cst=" + cst + ", tributacao=" + tributacao + ", icms=" + icms + ", iss=" + iss + ", pis=" + pis + ", cofins=" + cofins + ", cfopSaidaDentroEstado=" + cfopSaidaDentroEstado + ", cfopSaidaForaEstado=" + cfopSaidaForaEstado + '}';
    }
    
    
    
}
