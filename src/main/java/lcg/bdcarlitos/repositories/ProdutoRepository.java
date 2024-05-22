package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
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
        return produto;
    };

    public List<Produto> getAll(){
        try{
            String sql = "select * from produto order by id_produto";
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
            String sql = "select * from produto where nome like ?";
            return jdbcTemplate.query(sql, rowMapper, "%"+name+"%");
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Produto findById(int id) {
        try {
            String sql = "SELECT * FROM produto WHERE id_produto = ?";
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
}
