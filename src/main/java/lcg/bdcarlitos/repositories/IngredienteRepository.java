package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Ingrediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class IngredienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Ingrediente> rowMapper = (ResultSet rs, int rowNum) ->{
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(rs.getString("nome"));
        ingrediente.setDtValidade(rs.getString("dt_validade"));
        ingrediente.setQuantidade(rs.getInt("quantidade"));
        ingrediente.setCodigo(rs.getInt("codigo"));
        ingrediente.setTipoAlimento(rs.getString("tipo_alimento"));
        return ingrediente;
    };

    public List<Ingrediente> getAll(){
        try{
            String sql = "select * from ingredientes";
            return jdbcTemplate.query(sql, rowMapper);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Ingrediente create(Ingrediente ingrediente) {
        try {
            String sql = "INSERT INTO ingredientes(nome, dt_validade, quantidade, tipo_alimento)" +
                    " VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, ingrediente.getNome(), ingrediente.getDtValidade(), ingrediente.getQuantidade(),
                    ingrediente.getTipoAlimento());
            return ingrediente;
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public List<Ingrediente> findByName(String name){
        try{
            String sql = "select * from ingredientes where nome like ?";
            return jdbcTemplate.query(sql, rowMapper, "%"+name+"%");
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Ingrediente findByCodigo(int codigo) {
        try {
            String sql = "SELECT * FROM ingredientes WHERE codigo = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, codigo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Ingrediente updateById(int id, Ingrediente ingrediente){
        try{
            String sql = "UPDATE ingredientes SET nome = ?, dt_validade = ?, quantidade = ?, tipo_alimento = ? " +
                    "WHERE codigo = ?";
            jdbcTemplate.update(sql, ingrediente.getNome(), ingrediente.getDtValidade(), ingrediente.getQuantidade(),
                    ingrediente.getTipoAlimento(), id);
            return ingrediente;
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void deleteIngrediente(int id){
        String sql = "delete from ingredientes where codigo = ?";
        jdbcTemplate.update(sql, id);
    }

}
