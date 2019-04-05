
package br.com.auditoria.nfce.modelo;

import java.util.Objects;


public class FormaPagamento {

    private String numeroVenda;
    private String tipoPagamento;
    private String descricaoPagamento;
    private double valorPagamentoBruto;
    private double valorPagamentoLiquido;
    private double desconto;
    private String nsutef;

    public FormaPagamento() {
    }

    public FormaPagamento(String numeroVenda, String tipoPagamento, String descricaoPagamento, double valorPagamentoBruto, double valorPagamentoLiquido, double desconto, String nsutef) {
        this.numeroVenda = numeroVenda;
        this.tipoPagamento = tipoPagamento;
        this.descricaoPagamento = descricaoPagamento;
        this.valorPagamentoBruto = valorPagamentoBruto;
        this.valorPagamentoLiquido = valorPagamentoLiquido;
        this.desconto = desconto;
        this.nsutef = nsutef;
    }

    public FormaPagamento(String numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    
    
    public String getNumeroVenda() {
        return numeroVenda;
    }

    public void setNumeroVenda(String numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getDescricaoPagamento() {
        return descricaoPagamento;
    }

    public void setDescricaoPagamento(String descricaoPagamento) {
        this.descricaoPagamento = descricaoPagamento;
    }

    public double getValorPagamentoBruto() {
        return valorPagamentoBruto;
    }

    public void setValorPagamentoBruto(double valorPagamentoBruto) {
        this.valorPagamentoBruto = valorPagamentoBruto;
    }

    public double getValorPagamentoLiquido() {
        return valorPagamentoLiquido;
    }

    public void setValorPagamentoLiquido(double valorPagamentoLiquido) {
        this.valorPagamentoLiquido = valorPagamentoLiquido;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public String getNsutef() {
        return nsutef;
    }

    public void setNsutef(String nsutef) {
        this.nsutef = nsutef;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final FormaPagamento other = (FormaPagamento) obj;
        return Objects.equals(this.numeroVenda, other.numeroVenda);
    }

    @Override
    public String toString() {
        return "FormaPagamento{" + "numeroVenda=" + numeroVenda + ", tipoPagamento=" + tipoPagamento + ", descricaoPagamento=" + descricaoPagamento + ", valorPagamentoBruto=" + valorPagamentoBruto + ", valorPagamentoLiquido=" + valorPagamentoLiquido + ", desconto=" + desconto + ", nsutef=" + nsutef + '}';
    }
    
    
}
