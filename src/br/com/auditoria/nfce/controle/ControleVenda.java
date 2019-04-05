package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.Configuracao;
import br.com.auditoria.nfce.modelo.Venda;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControleVenda {

    private final ConectaBanco conecta;
    private final GerenciaArquivo gerenciaArquivo;

    public ControleVenda() {
        this.gerenciaArquivo = new GerenciaArquivo();
        Configuracao configuracao = this.gerenciaArquivo.buscarConfiguracao();
        this.conecta = new ConectaBanco(configuracao);
    }

    public List<Venda> listarVendas(String dataInicial, String dataFinal) throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        if (conecta.conectar()) {
            String sql = "select \n"
                    + "   hvnumvda as numeroVenda\n"
                    + "  ,hvnomxml as chave \n"
                    + "	,substring(hvnomxml,26,9) as cupom\n"
                    + "	,hvsessao as protocolo \n"
                    + "	, hvdigval as digval\n"
                    + "	,hvdatven as data_venda\n"
                    + "	,hvtotbru as valor_bruto\n"
                    + "	,hvtotliq as valor_liquido\n"
                    + "	,hvvldesc as desconto\n"
                    + "	,hvvlacre as acrescimo\n"
                    + "	,hvcpfcli as cpf_cliente\n"
                    + "	,hvnomcli as nome_cliente\n"
                    + " from\n"
                    + "  hvenda\n"
                    + " where \n"
                    + "      hvnomxml is not null and hvnomxml <> '' and hvstatus <> 'C' \n"
                    + "  and hvdatven between '" + dataInicial + " 00:00:00' and '" + dataFinal + " 23:59:59' \n"
                    + " order by \n"
                    + "  cupom \n"
                    + "	  desc";
            if (conecta.executarSQL(sql)) {
                if (conecta.getRs().first()) {
                    do {
                        vendas.add(new Venda(
                                conecta.getRs().getString("numeroVenda"),
                                conecta.getRs().getString("chave"),
                                conecta.getRs().getString("cupom"),
                                conecta.getRs().getString("protocolo"),
                                conecta.getRs().getString("digval"),
                                conecta.getRs().getString("data_venda"),
                                conecta.getRs().getDouble("valor_bruto"),
                                conecta.getRs().getDouble("valor_liquido"),
                                conecta.getRs().getDouble("desconto"),
                                conecta.getRs().getDouble("acrescimo"),
                                conecta.getRs().getString("cpf_cliente"),
                                conecta.getRs().getString("nome_cliente")
                        ));
                    } while (conecta.getRs().next());
                }
            }
            this.conecta.desconectar();
        }
        return vendas;
    }

    public Venda buscarVenda(Venda venda) throws SQLException {
        Venda vendaBuscada = null;
        if (conecta.conectar()) {
            String sql = "select \n"
                    + "   hvnumvda as numeroVenda\n"
                    + "  ,hvnomxml as chave \n"
                    + "	,substring(hvnomxml,26,9) as cupom\n"
                    + "	,hvsessao as protocolo \n"
                    + "	, hvdigval as digval\n"
                    + "	,hvdatven as data_venda\n"
                    + "	,hvtotbru as valor_bruto\n"
                    + "	,hvtotliq as valor_liquido\n"
                    + "	,hvvldesc as desconto\n"
                    + "	,hvvlacre as acrescimo\n"
                    + "	,hvcpfcli as cpf_cliente\n"
                    + "	,hvnomcli as nome_cliente\n"
                    + " from\n"
                    + "  hvenda\n"
                    + " where \n"
                    + "  hvnomxml is not null and hvnomxml <> '' and hvstatus <> 'C' and hvnumvda='" + venda.getNumeroVenda() + "' \n"
                    + " order by \n"
                    + "  cupom \n"
                    + "	  desc";
            if (conecta.executarSQL(sql)) {
                if (conecta.getRs().first()) {
                    do {
                        vendaBuscada = new Venda(
                                conecta.getRs().getString("numeroVenda"),
                                conecta.getRs().getString("chave"),
                                conecta.getRs().getString("cupom"),
                                conecta.getRs().getString("protocolo"),
                                conecta.getRs().getString("digval"),
                                conecta.getRs().getString("data_venda"),
                                conecta.getRs().getDouble("valor_bruto"),
                                conecta.getRs().getDouble("valor_liquido"),
                                conecta.getRs().getDouble("desconto"),
                                conecta.getRs().getDouble("acrescimo"),
                                conecta.getRs().getString("cpf_cliente"),
                                conecta.getRs().getString("nome_cliente")
                        );
                    } while (conecta.getRs().next());
                }
            }
            this.conecta.desconectar();
        }
        return vendaBuscada;
    }

    public Venda buscarVenda(String chave) throws SQLException {
        Venda vendaBuscada = null;
        if (conecta.conectar()) {
            String sql = "select \n"
                    + "   hvnumvda as numeroVenda\n"
                    + "  ,hvnomxml as chave \n"
                    + "	,substring(hvnomxml,26,9) as cupom\n"
                    + "	,hvsessao as protocolo \n"
                    + "	, hvdigval as digval\n"
                    + "	,hvdatven as data_venda\n"
                    + "	,hvtotbru as valor_bruto\n"
                    + "	,hvtotliq as valor_liquido\n"
                    + "	,hvvldesc as desconto\n"
                    + "	,hvvlacre as acrescimo\n"
                    + "	,hvcpfcli as cpf_cliente\n"
                    + "	,hvnomcli as nome_cliente\n"
                    + " from\n"
                    + "  hvenda\n"
                    + " where \n"
                    + "  hvnomxml ='" + chave + "' and hvstatus <> 'C' \n"
                    + " order by \n"
                    + "  cupom \n"
                    + "	  desc";
            if (conecta.executarSQL(sql)) {
                if (conecta.getRs().first()) {
                    do {
                        vendaBuscada = new Venda(
                                conecta.getRs().getString("numeroVenda"),
                                conecta.getRs().getString("chave"),
                                conecta.getRs().getString("cupom"),
                                conecta.getRs().getString("protocolo"),
                                conecta.getRs().getString("digval"),
                                conecta.getRs().getString("data_venda"),
                                conecta.getRs().getDouble("valor_bruto"),
                                conecta.getRs().getDouble("valor_liquido"),
                                conecta.getRs().getDouble("desconto"),
                                conecta.getRs().getDouble("acrescimo"),
                                conecta.getRs().getString("cpf_cliente"),
                                conecta.getRs().getString("nome_cliente")
                        );
                    } while (conecta.getRs().next());
                }
            }
            this.conecta.desconectar();
        }
        return vendaBuscada;
    }

    public Venda buscarUltimoCupom() throws SQLException {
        Venda vendaBuscada = null;
        if (conecta.conectar()) {
            String sql = "select \n"
                    + "   hvnumvda as numeroVenda\n"
                    + "  ,hvnomxml as chave \n"
                    + "	,substring(hvnomxml,26,9) as cupom\n"
                    + "	,hvsessao as protocolo \n"
                    + "	, hvdigval as digval\n"
                    + "	,hvdatven as data_venda\n"
                    + "	,hvtotbru as valor_bruto\n"
                    + "	,hvtotliq as valor_liquido\n"
                    + "	,hvvldesc as desconto\n"
                    + "	,hvvlacre as acrescimo\n"
                    + "	,hvcpfcli as cpf_cliente\n"
                    + "	,hvnomcli as nome_cliente\n"
                    + " from\n"
                    + "  hvenda\n"
                    + " where \n"
                    + "  hvnomxml is not null and hvnomxml <> '' and hvstatus <> 'C' \n"
                    + "  and DATE_FORMAT(hvdatven,'%Y-%m-%d') = '" + LocalDate.now().toString() + "' \n"
                    + " order by \n"
                    + "  cupom \n"
                    + "	  desc limit 1";
            if (conecta.executarSQL(sql)) {
                if (conecta.getRs().first()) {
                    do {
                        vendaBuscada = new Venda(
                                conecta.getRs().getString("numeroVenda"),
                                conecta.getRs().getString("chave"),
                                conecta.getRs().getString("cupom"),
                                conecta.getRs().getString("protocolo"),
                                conecta.getRs().getString("digval"),
                                conecta.getRs().getString("data_venda"),
                                conecta.getRs().getDouble("valor_bruto"),
                                conecta.getRs().getDouble("valor_liquido"),
                                conecta.getRs().getDouble("desconto"),
                                conecta.getRs().getDouble("acrescimo"),
                                conecta.getRs().getString("cpf_cliente"),
                                conecta.getRs().getString("nome_cliente")
                        );
                    } while (conecta.getRs().next());
                }
            }
            this.conecta.desconectar();
        }
        return vendaBuscada;
    }

}
