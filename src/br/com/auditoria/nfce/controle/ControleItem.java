package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.Configuracao;
import br.com.auditoria.nfce.modelo.Item;
import br.com.auditoria.nfce.modelo.Venda;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControleItem {

    private final ConectaBanco conecta;
    private final GerenciaArquivo gerenciaArquivo;

    public ControleItem() {
        this.gerenciaArquivo = new GerenciaArquivo();
        Configuracao configuracao = this.gerenciaArquivo.buscarConfiguracao();
        this.conecta = new ConectaBanco(configuracao);
    }

    public List<Item> listarItensPorVenda(Venda venda) throws SQLException {
        List<Item> itens = new ArrayList<>();
        if (conecta.conectar()) {
            String sql = "select \n"
                    + "     ivnumvda as numeroVenda\n"
                    + "    ,ivcodpro as referencia \n"
                    + "	   ,ivnumite as item_seguencia\n"
                    + "    ,ivdatven as data_venda\n"
                    + "	   ,ivdespro as descricao\n"
                    + "	   ,ivtippro as tipo\n"
                    + "    ,ivunipro as unidade\n"
                    + "    ,ivcstpro as cst\n"
                    + "    ,ivtiptri as tributacao\n"
                    + "    ,ivalicms as aliquota_icms\n"
                    + "    ,ivvlunit as valor_unitario\n"
                    + "    ,ivqtdite as quantidade\n"
                    + "    ,ivvaldes as desconto\n"
                    + "    ,ivvalacr as acrescimo\n"
                    + "    ,ivtotbru as valor_bruto\n"
                    + "    ,ivtotliq as valor_liquido\n"
                    + "    ,prcodbar as codigo_barras\n"
                    + "    ,prncmcod as ncm\n"
                    + "    ,prcdcest as cest\n"
                    + "    ,pricmsde as icms\n"
                    + "    ,prperiss as iss\n"
                    + "    ,prtippis as pis\n"
                    + "    ,prtcofins as cofins\n"
                    + "    ,prcfopde as cfop_dentro_estado\n"
                    + "    ,prcfopfo as cfop_dentro_fora\n"
                    + "from\n"
                    + "   ivenda\n"
                    + "inner join \n"
                    + "   produtos on(prcodigo=ivcodpro)\n"
                    + "where\n"
                    + "    ivstatus <> 'C'\n"
                    + "and ivnumvda='" + venda.getNumeroVenda() + "'\n"
                    + "order by\n"
                    + "  item_seguencia";
            if (conecta.executarSQL(sql)) {
                if (conecta.getRs().first()) {
                    do {
                        itens.add(new Item(
                                conecta.getRs().getString("numeroVenda"),
                                conecta.getRs().getString("referencia"),
                                conecta.getRs().getString("item_seguencia"),
                                conecta.getRs().getString("data_venda"),
                                conecta.getRs().getString("descricao"),
                                conecta.getRs().getString("tipo"),
                                conecta.getRs().getString("unidade"),
                                conecta.getRs().getString("cst"),
                                conecta.getRs().getString("tributacao"),
                                conecta.getRs().getDouble("aliquota_icms"),
                                conecta.getRs().getDouble("valor_unitario"),
                                conecta.getRs().getDouble("quantidade"),
                                conecta.getRs().getDouble("desconto"),
                                conecta.getRs().getDouble("acrescimo"),
                                conecta.getRs().getDouble("valor_bruto"),
                                conecta.getRs().getDouble("valor_liquido"),
                                conecta.getRs().getString("codigo_barras"),
                                conecta.getRs().getString("ncm"),
                                conecta.getRs().getString("cest"),
                                conecta.getRs().getDouble("icms"),
                                conecta.getRs().getDouble("iss"),
                                conecta.getRs().getDouble("pis"),
                                conecta.getRs().getDouble("cofins"),
                                conecta.getRs().getString("cfop_dentro_estado"),
                                conecta.getRs().getString("cfop_dentro_fora")
                        ));
                    } while (conecta.getRs().next());
                }
            }
        }
        return itens;
    }

}
