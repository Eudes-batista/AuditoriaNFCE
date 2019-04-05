package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.Configuracao;
import br.com.auditoria.nfce.modelo.Produto;
import java.sql.SQLException;

public class ControleProduto {

    private final ConectaBanco conecta;
    private final GerenciaArquivo gerenciaArquivo;

    public ControleProduto() {
        this.gerenciaArquivo = new GerenciaArquivo();
        Configuracao configuracao = this.gerenciaArquivo.buscarConfiguracao();
        this.conecta = new ConectaBanco(configuracao);
    }

    public Produto buscarProduto(String referencia) throws SQLException {
        Produto produto = new Produto();
        if (conecta.conectar()) {
            String sql = "select \n"
                    + "  prcodigo as referencia\n"
                    + "	,prcodbar as codigo_barras\n"
                    + "	,prdescri as descricao\n"
                    + "	,prunidad as unidade\n"
                    + "	,prncmcod as ncm\n"
                    + "	,prcdcest as cest\n"
                    + "	,prcstcod as cst\n"
                    + "	,prcsttip as tributacao\n"
                    + "	,pricmsde as icms\n"
                    + "	,prperiss as iss\n"
                    + "	,prtippis as pis\n"
                    + "	,prtcofins as cofins\n"
                    + "	,prcfopde as cfop_dentro_estado\n"
                    + "	,prcfopfo as cfop_dentro_fora\n"
                    + "from\n"
                    + "   produto\n"
                    + "where \n"
                    + "   prcodigo ='" + referencia + "' or prcodbar='" + referencia + "'";
            if (conecta.executarSQL(sql)) {
                if (conecta.getRs().first()) {
                    produto.setReferencia(conecta.getRs().getString("referencia"));
                    produto.setCodigoBarra(conecta.getRs().getString("codigo_barras"));
                    produto.setDescricao(conecta.getRs().getString("descricao"));
                    produto.setUnidade(conecta.getRs().getString("unidade"));
                    produto.setNcm(conecta.getRs().getString("ncm"));
                    produto.setCest(conecta.getRs().getString("cest"));
                    produto.setCst(conecta.getRs().getString("cst"));
                    produto.setTributacao(conecta.getRs().getString("tributacao"));
                    produto.setIcms(conecta.getRs().getDouble("icms"));
                    produto.setIss(conecta.getRs().getDouble("iss"));
                    produto.setPis(conecta.getRs().getDouble("pis"));
                    produto.setCofins(conecta.getRs().getDouble("cofins"));
                    produto.setCfopSaidaDentroEstado(conecta.getRs().getString("cfop_dentro_estado"));
                    produto.setCfopSaidaForaEstado(conecta.getRs().getString("cfop_dentro_fora"));
                }
            }
        }
        return produto;
    }

}
