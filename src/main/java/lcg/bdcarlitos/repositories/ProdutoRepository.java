package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Ingrediente;
import lcg.bdcarlitos.entities.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProdutoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Produto> rowMapper = (ResultSet rs, int rowNum) -> {
        Produto produto = new Produto();
        produto.setId_produto(rs.getInt("id_produto"));
        produto.setNome(rs.getString("nome"));
        produto.setPreco(rs.getDouble("preco"));

        try{
            String ingredientesString = rs.getString("ingredientes");

            if (ingredientesString != null) {
                String[] ingredientesArrayString = ingredientesString.split(", ");
                int[] ingredientesArrayInt = new int[ingredientesArrayString.length];
                for (int i = 0; i < ingredientesArrayString.length; i++) {
                    ingredientesArrayInt[i] = Integer.parseInt(ingredientesArrayString[i]);
                }
                produto.setIngredientes(ingredientesArrayInt);
            } else {
                produto.setIngredientes(new int[0]);
            }
        }catch (SQLException e){
            produto.setIngredientes(new int[0]);
        }
        return produto;
    };

    private final RowMapper<Ingrediente> rowMapperIngrediente = (ResultSet rs, int rowNum) -> {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(rs.getString("nome"));
        ingrediente.setDtValidade(rs.getString("dt_validade"));
        ingrediente.setQuantidade(rs.getInt("quantidade"));
        ingrediente.setCodigo(rs.getInt("codigo"));
        ingrediente.setTipoAlimento(rs.getString("tipo_alimento"));
        ingrediente.setQuantidadeNoProduto(rs.getInt("quantidade_no_produto"));

        return ingrediente;
    };

    public List<Produto> getAll(){
        try{
            String sql = "SELECT p.*, GROUP_CONCAT(i.codigo SEPARATOR ', ') AS ingredientes " +
                    "FROM produto p " +
                    "LEFT JOIN ingredientes_produto ip ON p.id_produto = ip.produto_id " +
                    "LEFT JOIN ingredientes i ON ip.codigo_ingrediente  = i.codigo " +
                    "GROUP BY p.id_produto " +
                    "ORDER BY p.id_produto ";
            return jdbcTemplate.query(sql, rowMapper);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Produto create(Produto produto) {
        try {
            String sql = "INSERT INTO Produto(nome, preco) VALUES (?, ?)";
            jdbcTemplate.update(sql, produto.getNome(), produto.getPreco());
            return produto;
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public List<Produto> findByName(String name){
        try{
            String sql = "SELECT p.*, GROUP_CONCAT(i.codigo SEPARATOR ', ') AS ingredientes " +
                    "FROM produto p " +
                    "LEFT JOIN ingredientes_produto ip ON p.id_produto = ip.produto_id " +
                    "LEFT JOIN ingredientes i ON ip.codigo_ingrediente  = i.codigo " +
                    "where p.nome like ? " +
                    "GROUP BY p.id_produto " +
                    "ORDER BY p.id_produto";
            return jdbcTemplate.query(sql, rowMapper, "%"+name+"%");
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Produto findById(int id) {
        try {
            String sql = "SELECT p.*, GROUP_CONCAT(i.codigo SEPARATOR ', ') AS ingredientes " +
                    "FROM produto p " +
                    "LEFT JOIN ingredientes_produto ip ON p.id_produto = ip.produto_id " +
                    "LEFT JOIN ingredientes i ON ip.codigo_ingrediente  = i.codigo " +
                    "where p.id_produto = ? " +
                    "GROUP BY p.id_produto " +
                    "ORDER BY p.id_produto";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Produto updateById(int id, Produto produto){
        try{
            String sql = "UPDATE produto SET nome = ?, preco = ? WHERE id_produto = ?";
            jdbcTemplate.update(sql, produto.getNome(), produto.getPreco(), id);
            return produto;
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void deleteProduto(int id){
        String sql = "delete from produto where id_produto = ?";
        jdbcTemplate.update(sql, id);
    }

    public void defineIngredientes(int idProduto ,int[] ingredientes, int[] quantidades){
        String sql = "INSERT INTO ingredientes_produto(produto_id, codigo_ingrediente, quantidade) values " +
                "(?, ?, ?)";
        int i = 0;
        for (int ingrediente: ingredientes){
            jdbcTemplate.update(sql, idProduto, ingrediente, quantidades[i]);
            i++;
        }
    }

    public List<Ingrediente> getIngredientesByProduto(int idProduto){
        String sql = "select i.*, ip.quantidade as quantidade_no_produto from ingredientes_produto ip " +
                "join produto p on p.id_produto = ip.produto_id " +
                "join ingredientes i on i.codigo = ip.codigo_ingrediente " +
                "where p.id_produto =  ?";
        return jdbcTemplate.query(sql, rowMapperIngrediente, idProduto);
    }
}
