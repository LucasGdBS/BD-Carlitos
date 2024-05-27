package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Pedido;
import lcg.bdcarlitos.entities.ResumoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class PedidoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Pedido> rowMapper = (ResultSet rs, int rowNum) -> {
        Pedido pedido = new Pedido();
        pedido.setCodigoNotalFiscal(rs.getInt("codigo_nota_fiscal"));
        pedido.setDtPedido(rs.getString("dt_pedido"));
        pedido.setFormaPagamento(rs.getString("forma_pagamento"));
        pedido.setTaxaEntrega(rs.getFloat("taxa_entrega"));
        pedido.setDesconto(rs.getFloat("desconto"));
        pedido.setQntProduto(rs.getInt("qnt_produto"));
        pedido.setNumeroPedido(rs.getInt("num_pedido"));
        pedido.setIdCliente(rs.getInt("id_cliente"));
        pedido.setProdutoId(rs.getInt("produto_id"));
        pedido.setAtendenteCpf(rs.getString("atendente_cpf"));

        pedido.setNomeCLiente(rs.getString("nome_cliente"));
        pedido.setNomeProduto(rs.getString("nome_produto"));
        pedido.setValorTotal(rs.getFloat("valor_total")); // valorProduto * qntProduto
        pedido.setValorDesconto(rs.getFloat("valor_desconto"));
        pedido.setValorSemDesconto(rs.getFloat("valor_sem_desconto"));
        return pedido;
    };

    private final RowMapper<ResumoPedido> rowMapperResumoPedido = (ResultSet rs, int rowNum) -> {
        ResumoPedido resumo = new ResumoPedido();
        resumo.setNumPedido(rs.getInt("num_pedido"));
        resumo.setProdutos(rs.getString("produtos"));
        resumo.setNomeCliente(rs.getString("nome_cliente"));
        resumo.setValorTotal(rs.getFloat("valor_total"));
        return resumo;
    };

    public List<Pedido> getAll(){
        try{
            String sql = "select * from view_detalhes_pedido";
            return jdbcTemplate.query(sql, rowMapper);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Pedido create(Pedido pedido) {
        try {
            String sql = "INSERT INTO pedido (codigo_nota_fiscal, num_pedido, dt_pedido, forma_pagamento, taxa_entrega, desconto, " +
                    "qnt_produto, id_cliente, produto_id, atendente_cpf) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, pedido.getCodigoNotalFiscal(), pedido.getNumeroPedido(), pedido.getDtPedido(), pedido.getFormaPagamento(),
                    pedido.getTaxaEntrega(), pedido.getDesconto(), pedido.getQntProduto(), pedido.getIdCliente(),
                    pedido.getProdutoId(), pedido.getAtendenteCpf());
            return pedido;
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public Pedido[] create(Pedido[] pedidos){
        try{
            String sql = "INSERT INTO pedido (codigo_nota_fiscal, num_pedido, dt_pedido, forma_pagamento, taxa_entrega, desconto, " +
                    "qnt_produto, id_cliente, produto_id, atendente_cpf) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            for (Pedido pedido : pedidos){
                jdbcTemplate.update(sql, pedido.getCodigoNotalFiscal(), pedido.getNumeroPedido(), pedido.getDtPedido(), pedido.getFormaPagamento(),
                        pedido.getTaxaEntrega(), pedido.getDesconto(), pedido.getQntProduto(), pedido.getIdCliente(),
                        pedido.getProdutoId(), pedido.getAtendenteCpf());
            }
            return pedidos;
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public List<Pedido> findByNumPedido(int numPedido) {
        try{
            String sql = "select * from view_detalhes_pedido where num_pedido = ?";
            return jdbcTemplate.query(sql, rowMapper, numPedido);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public List<Pedido> findByNome(String nome) {
        try{
            String sql = "select * from view_detalhes_pedido where nome_cliente like ?";
            return jdbcTemplate.query(sql, rowMapper, "%"+nome+"%");
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public ResumoPedido resumoPedido(int numPedido) {
        try{
            String sql = "select num_pedido, nome_cliente, " +
                    "group_concat(concat(qnt_produto, \" x \", nome_produto ) separator ', ') as produtos, " +
                    "sum(valor_total) as valor_total from view_detalhes_pedido " +
                    "where num_pedido = ? " +
                    "group by num_pedido, nome_cliente";
            return jdbcTemplate.queryForObject(sql, rowMapperResumoPedido, numPedido);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public List<ResumoPedido> resumoPedidoByName(String nome) {
        try{
            String sql = "select num_pedido, nome_cliente, " +
                    "group_concat(concat(qnt_produto, \" x \", nome_produto ) separator ', ') as produtos, " +
                    "sum(valor_total) as valor_total from view_detalhes_pedido " +
                    "where nome_cliente like ? " +
                    "group by num_pedido, nome_cliente " +
                    "order by nome_cliente";
            return jdbcTemplate.query(sql, rowMapperResumoPedido, "%"+nome+"%");
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void deletePedido(int id){
        String sql = "delete from pedido where num_pedido = ?";
        jdbcTemplate.update(sql, id);
    }
}
