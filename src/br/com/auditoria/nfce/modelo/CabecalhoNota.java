package br.com.auditoria.nfce.modelo;

import java.time.LocalDateTime;

public class CabecalhoNota {

    private int estado;
    private int numeroVenda;
    private String naturezaOperacao;
    private int modelo;
    private int serie;
    private int numeroNFCe;
    private LocalDateTime dataEmissao;
    private int tipoDaNFe;
    private int indicadorLocalDestino;
    private String codigoDoMunicipio;
    private int formatoDeImpressao;
    private int digitoVerificador;
    private int tipoAmbiente;
    private int finalidadeEmissao;
    private int consumidorFinal;
    private int indicadorDePresencaConsumidor;
    private int indicadorDeProcessoDeEmissao;
    private String versaoProcessoEmissao;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.modelo;
        hash = 61 * hash + this.serie;
        hash = 61 * hash + this.numeroNFCe;
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
        final CabecalhoNota other = (CabecalhoNota) obj;
        if (this.modelo != other.modelo) {
            return false;
        }
        if (this.serie != other.serie) {
            return false;
        }
        return this.numeroNFCe == other.numeroNFCe;
    }

    public CabecalhoNota() {
    }

    public CabecalhoNota(int estado, int numeroVenda, String naturezaOperacao, int modelo, int serie, int numeroNFCe, LocalDateTime dataEmissao, int tipoDaNFe, int indicadorLocalDestino, String codigoDoMunicipio, int formatoDeImpressao, int digitoVerificador, int tipoAmbiente, int finalidadeEmissao, int consumidorFinal, int indicadorDePresencaConsumidor, int indicadorDeProcessoDeEmissao, String versaoProcessoEmissao) {
        this.estado = estado;
        this.numeroVenda = numeroVenda;
        this.naturezaOperacao = naturezaOperacao;
        this.modelo = modelo;
        this.serie = serie;
        this.numeroNFCe = numeroNFCe;
        this.dataEmissao = dataEmissao;
        this.tipoDaNFe = tipoDaNFe;
        this.indicadorLocalDestino = indicadorLocalDestino;
        this.codigoDoMunicipio = codigoDoMunicipio;
        this.formatoDeImpressao = formatoDeImpressao;
        this.digitoVerificador = digitoVerificador;
        this.tipoAmbiente = tipoAmbiente;
        this.finalidadeEmissao = finalidadeEmissao;
        this.consumidorFinal = consumidorFinal;
        this.indicadorDePresencaConsumidor = indicadorDePresencaConsumidor;
        this.indicadorDeProcessoDeEmissao = indicadorDeProcessoDeEmissao;
        this.versaoProcessoEmissao = versaoProcessoEmissao;
    }

    

    public CabecalhoNota(int modelo, int serie, int numeroNFCe) {
        this.modelo = modelo;
        this.serie = serie;
        this.numeroNFCe = numeroNFCe;
    }

    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getNumeroVenda() {
        return numeroVenda;
    }

    public void setNumeroVenda(int numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    public String getNaturezaOperacao() {
        return naturezaOperacao;
    }

    public void setNaturezaOperacao(String naturezaOperacao) {
        this.naturezaOperacao = naturezaOperacao;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getNumeroNFCe() {
        return numeroNFCe;
    }

    public void setNumeroNFCe(int numeroNFCe) {
        this.numeroNFCe = numeroNFCe;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public int getTipoDaNFe() {
        return tipoDaNFe;
    }

    public void setTipoDaNFe(int tipoDaNFe) {
        this.tipoDaNFe = tipoDaNFe;
    }

    public int getIndicadorLocalDestino() {
        return indicadorLocalDestino;
    }

    public void setIndicadorLocalDestino(int indicadorLocalDestino) {
        this.indicadorLocalDestino = indicadorLocalDestino;
    }

    public String getCodigoDoMunicipio() {
        return codigoDoMunicipio;
    }

    public void setCodigoDoMunicipio(String codigoDoMunicipio) {
        this.codigoDoMunicipio = codigoDoMunicipio;
    }

    public int getFormatoDeImpressao() {
        return formatoDeImpressao;
    }

    public void setFormatoDeImpressao(int formatoDeImpressao) {
        this.formatoDeImpressao = formatoDeImpressao;
    }

    public int getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(int digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

    public int getTipoAmbiente() {
        return tipoAmbiente;
    }

    public void setTipoAmbiente(int tipoAmbiente) {
        this.tipoAmbiente = tipoAmbiente;
    }

    public int getFinalidadeEmissao() {
        return finalidadeEmissao;
    }

    public void setFinalidadeEmissao(int finalidadeEmissao) {
        this.finalidadeEmissao = finalidadeEmissao;
    }

    public int getConsumidorFinal() {
        return consumidorFinal;
    }

    public void setConsumidorFinal(int consumidorFinal) {
        this.consumidorFinal = consumidorFinal;
    }

    public int getIndicadorDePresencaConsumidor() {
        return indicadorDePresencaConsumidor;
    }

    public void setIndicadorDePresencaConsumidor(int indicadorDePresencaConsumidor) {
        this.indicadorDePresencaConsumidor = indicadorDePresencaConsumidor;
    }

    public int getIndicadorDeProcessoDeEmissao() {
        return indicadorDeProcessoDeEmissao;
    }

    public void setIndicadorDeProcessoDeEmissao(int indicadorDeProcessoDeEmissao) {
        this.indicadorDeProcessoDeEmissao = indicadorDeProcessoDeEmissao;
    }

    public String getVersaoProcessoEmissao() {
        return versaoProcessoEmissao;
    }

    public void setVersaoProcessoEmissao(String versaoProcessoEmissao) {
        this.versaoProcessoEmissao = versaoProcessoEmissao;
    }
    
    
    
}
