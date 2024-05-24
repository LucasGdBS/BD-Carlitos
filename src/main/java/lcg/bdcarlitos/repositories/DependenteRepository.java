package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Dependente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class DependenteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Dependente> rowMapper = (ResultSet rs, int rowNum) -> {
        Dependente dependente = new Dependente();
        dependente.setId_dependente(rs.getInt("id_dependente"));
        dependente.setNome(rs.getString("nome"));
        dependente.setData_nacimento(rs.getString("data_nascimento"));
        dependente.setRelacao(rs.getString("relacao"));
        dependente.setCpf_funcionario(rs.getString("cpf_funcionario"));
        return dependente;
    };

    public List<Dependente> getAll(){
        try{
            String sql = "select * from dependentes";
            return jdbcTemplate.query(sql, rowMapper);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Dependente create(Dependente dependentes) {
        try {
            String sql = "INSERT INTO dependentes (nome, data_nascimento, relacao, cpf_funcionario) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, dependentes.getNome(), dependentes.getData_nacimento(), dependentes.getRelacao(), dependentes.getCpf_funcionario());
            return dependentes;
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public List<Dependente> findByCpfDono(String cpf) {
        try {
            String sql = "SELECT * FROM Dependentes WHERE cpf_funcionario = ?";
            return jdbcTemplate.query(sql, rowMapper, cpf);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Dependente findById(int id) {
        try {
            String sql = "SELECT * FROM Dependentes WHERE id_dependente = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteDependentes(int id){
        String sql = "delete from dependentes where id_dependente = ?";
        jdbcTemplate.update(sql, id);
    }
}
