package br.com.auditoria.nfce.modelo;

import java.util.Objects;

public class Item {

    private String numeroVenda;
    private String referencia;
    private String itemSeguencia;
    private String dataVenda;
    private String descricao;
    private String tipo;
    private String unidadeVenda;
    private String cst;
    private String tributacao;
    private double aliquotaIcms;
    private double valorUnitario;
    private double quantidade;
    private double desconto;
    private double acrescimo;
    private double valorTotalBruto;
    private double valorTotalLiquido;
    private String codigoBarras;
    private String ncm;
    private String cest;
    private double icms;
    private double iss;
    private double pis;
    private double cofins;
    private String cfopSaidaDentroEstado;
    private String cfopSaidaForaDoEstado;
    
    public Item() {
    }

    public Item(String numeroVenda, String referencia, String itemSeguencia, String dataVenda, String descricao, String tipo, String unidadeVenda, String cst, String tributacao, double aliquotaIcms, double valorUnitario, double quantidade, double desconto, double acrescimo, double valorTotalBruto, double valorTotalLiquido, String codigoBarras, String ncm, String cest, double icms, double iss, double pis, double cofins, String cfopSaidaDentroEstado, String cfopSaidaForaDoEstado) {
        this.numeroVenda = numeroVenda;
        this.referencia = referencia;
        this.itemSeguencia = itemSeguencia;
        this.dataVenda = dataVenda;
        this.descricao = descricao;
        this.tipo = tipo;
        this.unidadeVenda = unidadeVenda;
        this.cst = cst;
        this.tributacao = tributacao;
        this.aliquotaIcms = aliquotaIcms;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.valorTotalBruto = valorTotalBruto;
        this.valorTotalLiquido = valorTotalLiquido;
        this.codigoBarras = codigoBarras;
        this.ncm = ncm;
        this.cest = cest;
        this.icms = icms;
        this.iss = iss;
        this.pis = pis;
        this.cofins = cofins;
        this.cfopSaidaDentroEstado = cfopSaidaDentroEstado;
        this.cfopSaidaForaDoEstado = cfopSaidaForaDoEstado;
    }

    public Item(String numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    
    
    public String getNumeroVenda() {
        return numeroVenda;
    }

    public void setNumeroVenda(String numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getItemSeguencia() {
        return itemSeguencia;
    }

    public void setItemSeguencia(String itemSeguencia) {
        this.itemSeguencia = itemSeguencia;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidadeVenda() {
        return unidadeVenda;
    }

    public void setUnidadeVenda(String unidadeVenda) {
        this.unidadeVenda = unidadeVenda;
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

    public double getAliquotaIcms() {
        return aliquotaIcms;
    }

    public void setAliquotaIcms(double aliquotaIcms) {
        this.aliquotaIcms = aliquotaIcms;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(double acrescimo) {
        this.acrescimo = acrescimo;
    }

    public double getValorTotalBruto() {
        return valorTotalBruto;
    }

    public void setValorTotalBruto(double valorTotalBruto) {
        this.valorTotalBruto = valorTotalBruto;
    }

    public double getValorTotalLiquido() {
        return valorTotalLiquido;
    }

    public void setValorTotalLiquido(double valorTotalLiquido) {
        this.valorTotalLiquido = valorTotalLiquido;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.numeroVenda);
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
        final Item other = (Item) obj;
        return Objects.equals(this.numeroVenda, other.numeroVenda);
    }

    @Override
    public String toString() {
        return "Item{" + "numeroVenda=" + numeroVenda + ", referencia=" + referencia + ", itemSeguencia=" + itemSeguencia + ", dataVenda=" + dataVenda + ", descricao=" + descricao + ", tipo=" + tipo + ", unidadeVenda=" + unidadeVenda + ", cst=" + cst + ", tributacao=" + tributacao + ", aliquotaIcms=" + aliquotaIcms + ", valorUnitario=" + valorUnitario + ", quantidade=" + quantidade + ", desconto=" + desconto + ", acrescimo=" + acrescimo + ", valorTotalBruto=" + valorTotalBruto + ", valorTotalLiquido=" + valorTotalLiquido + ", codigoBarras=" + codigoBarras + ", ncm=" + ncm + ", cest=" + cest + ", icms=" + icms + ", iss=" + iss + ", pis=" + pis + ", cofins=" + cofins + ", cfopSaidaDentroEstado=" + cfopSaidaDentroEstado + ", cfopSaidaForaDoEstado=" + cfopSaidaForaDoEstado + '}';
    }

    

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
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

    public String getCfopSaidaForaDoEstado() {
        return cfopSaidaForaDoEstado;
    }

    public void setCfopSaidaForaDoEstado(String cfopSaidaForaDoEstado) {
        this.cfopSaidaForaDoEstado = cfopSaidaForaDoEstado;
    }

}
