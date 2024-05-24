package lcg.bdcarlitos.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DashboardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getClientesPorBairro() {
        String sql = "SELECT bairro, COUNT(*) AS quantidade_clientes FROM clientes c GROUP BY bairro ORDER BY quantidade_clientes DESC";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getProdutosMaisVendidos() {
        String sql = "SELECT p2.nome AS produto, SUM(p.qnt_produto) AS quantidade_vendida FROM pedido p JOIN produto p2 ON p.produto_id = p2.id_produto GROUP BY p2.nome ORDER BY quantidade_vendida DESC";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getFaturamentoDiario() {
        String sql = "SELECT dt_pedido AS data, SUM(valor_total) AS faturamento_diario FROM view_detalhes_pedido vdp GROUP BY dt_pedido ORDER BY dt_pedido";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getFaturamentoMensal() {
        String sql = "SELECT DATE_FORMAT(dt_pedido, '%Y-%m') AS mes, SUM(valor_total) AS faturamento_mensal FROM view_detalhes_pedido vdp GROUP BY DATE_FORMAT(dt_pedido, '%Y-%m') ORDER BY mes";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getFaturamentoAnual() {
        String sql = "SELECT YEAR(dt_pedido) AS ano, SUM(valor_total) AS faturamento_anual FROM view_detalhes_pedido vdp GROUP BY YEAR(dt_pedido) ORDER BY ano";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getFormaPagamentoMaisUtilizadas() {
        String sql = "SELECT p.forma_pagamento, COUNT(*) AS quantidade_utilizacao FROM pedido p GROUP BY forma_pagamento ORDER BY quantidade_utilizacao DESC";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getAtendentesComMaisVendas() {
        String sql = "SELECT a.cpf, f.nome, COUNT(p.num_pedido) AS numero_de_vendas FROM pedido p JOIN atendentes a ON p.atendente_cpf = a.cpf JOIN funcionario f ON a.cpf = f.cpf GROUP BY a.cpf, f.nome ORDER BY numero_de_vendas DESC";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getPedidosPorTurno() {
        String sql = "SELECT a.turno, COUNT(p.num_pedido) AS quantidade_pedidos FROM pedido p JOIN atendentes a ON p.atendente_cpf = a.cpf GROUP BY a.turno";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getFuncionariosComSalariosAcimaDaMedia() {
        String sql = "SELECT nome, salario FROM funcionario WHERE salario > (SELECT AVG(salario) FROM funcionario)";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getProdutosComMaisDeTresIngredientes() {
        String sql = "SELECT nome FROM produto WHERE id_produto IN (SELECT produto_id FROM ingredientes_produto GROUP BY produto_id HAVING COUNT(*) > 3)";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getIngredientesProximosAoVencimento() {
        String sql = "SELECT nome, dt_validade FROM ingredientes WHERE DATEDIFF(dt_validade, CURDATE()) < 7";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getIngredientesMaisUtilizadosNosProdutosMaisVendidos() {
        String sql = "SELECT i.nome AS ingrediente, COUNT(*) AS quantidade_utilizacao " +
                "FROM ingredientes_produto ip " +
                "JOIN ingredientes i ON ip.codigo_ingrediente = i.codigo " +
                "WHERE ip.produto_id IN ( " +
                "    SELECT produto_id " +
                "    FROM ( " +
                "        SELECT produto_id, SUM(qnt_produto) AS total_vendido " +
                "        FROM pedido " +
                "        GROUP BY produto_id " +
                "        ORDER BY total_vendido DESC " +
                "        LIMIT 5 " +
                "    ) AS produtos_mais_vendidos " +
                ") " +
                "GROUP BY i.nome " +
                "ORDER BY quantidade_utilizacao DESC";
        return jdbcTemplate.queryForList(sql);
    }
}
