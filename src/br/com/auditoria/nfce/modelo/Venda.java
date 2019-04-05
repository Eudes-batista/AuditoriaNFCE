
package br.com.auditoria.nfce.modelo;

import java.util.Objects;


public class Venda {

    private String numeroVenda;
    private String chave;
    private String cupom;
    private String protocolo;
    private String digVal;
    private String dataVenda;
    private double valorTotalBruto;
    private double valorTotalLiquido;
    private double desconto;
    private double acrescimo;
    private String cpfCliente;
    private String nomeCliente;

    public Venda() {
    }

    public Venda(String numeroVenda, String chave, String cupom, String protocolo, String digVal, String dataVenda, double valorTotalBruto, double valorTotalLiquido, double desconto, double acrescimo, String cpfCliente, String nomeCliente) {
        this.numeroVenda = numeroVenda;
        this.chave = chave;
        this.cupom = cupom;
        this.protocolo = protocolo;
        this.digVal = digVal;
        this.dataVenda = dataVenda;
        this.valorTotalBruto = valorTotalBruto;
        this.valorTotalLiquido = valorTotalLiquido;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.cpfCliente = cpfCliente;
        this.nomeCliente = nomeCliente;
    }

    public Venda(String cupom) {
        this.cupom = cupom;
    }

    
    public String getNumeroVenda() {
        return numeroVenda;
    }

    public void setNumeroVenda(String numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getCupom() {
        return cupom;
    }

    public void setCupom(String cupom) {
        this.cupom = cupom;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getDigVal() {
        return digVal;
    }

    public void setDigVal(String digVal) {
        this.digVal = digVal;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.cupom);
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
        final Venda other = (Venda) obj;
        return Objects.equals(this.cupom, other.cupom);
    }

    @Override
    public String toString() {
        return "Venda{" + "numeroVenda=" + numeroVenda + ", chave=" + chave + ", cupom=" + cupom + ", protocolo=" + protocolo + ", digVal=" + digVal + ", dataVenda=" + dataVenda + ", valorTotalBruto=" + valorTotalBruto + ", valorTotalLiquido=" + valorTotalLiquido + ", desconto=" + desconto + ", acrescimo=" + acrescimo + ", cpfCliente=" + cpfCliente + ", nomeCliente=" + nomeCliente + '}';
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    
    
}
