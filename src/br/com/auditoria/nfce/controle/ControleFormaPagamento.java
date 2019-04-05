package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.Configuracao;
import br.com.auditoria.nfce.modelo.FormaPagamento;
import br.com.auditoria.nfce.modelo.Venda;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControleFormaPagamento {

    private final ConectaBanco conecta;
    private final GerenciaArquivo gerenciaArquivo;

    public ControleFormaPagamento() {
        this.gerenciaArquivo = new GerenciaArquivo();
        Configuracao configuracao = this.gerenciaArquivo.buscarConfiguracao();
        this.conecta = new ConectaBanco(configuracao);
    }

    public List<FormaPagamento> listarFormaPagamentoPorVenda(Venda venda) throws SQLException {
        List<FormaPagamento> formarDePagamentos = new ArrayList<>();
        if (conecta.conectar()) {
            String sql = "select \n"
                    + "  pvnumvda as numeroVenda\n"
                    + "	,pvtippag as tipo_pagamento \n"
                    + "	,pvdescri as descricao_pagamento\n"
                    + "	,pvvalpag as valor_bruto\n"
                    + "	,pvvalliq as valor_liquido\n"
                    + "	,pvdescpg as desconto\n"
                    + "	,pvnsutef as nsu\n"
                    + "from\n"
                    + "   pvenda\n"
                    + "where \n"
                    + "    pvstatus <> 'C'\n"
                    + "and pvnumvda ='" + venda.getNumeroVenda() + "' ";
            if (conecta.executarSQL(sql)) {
                if (conecta.getRs().first()) {
                    do {
                        formarDePagamentos.add(new FormaPagamento(
                                conecta.getRs().getString("numeroVenda"),
                                conecta.getRs().getString("tipo_pagamento"),
                                conecta.getRs().getString("descricao_pagamento"),
                                conecta.getRs().getDouble("valor_bruto"),
                                conecta.getRs().getDouble("valor_liquido"),
                                conecta.getRs().getDouble("desconto"),
                                conecta.getRs().getString("nsu")
                        ));
                    } while (conecta.getRs().next());
                }
            }
        }
        return formarDePagamentos;
    }

}
