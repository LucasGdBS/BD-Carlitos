package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public class GerenteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private FuncionarioRepository funcionarioRepository;

    private RowMapper<Funcionario> rowMapper = (ResultSet rs, int rowNum) -> {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(rs.getString("cpf"));
        funcionario.setNome(rs.getString("nome"));
        funcionario.setSalario(rs.getFloat("salario"));
        return funcionario;
    };

    public List<Funcionario> getAll(){
        try{
            String sql = "SELECT f.cpf, f.nome, f.salario FROM Gerente g " +
                    "JOIN Funcionario f ON f.cpf = g.cpf";
            return jdbcTemplate.query(sql, rowMapper);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void create(String cpf){
        try {
            String sql = "INSERT INTO Gerente(cpf) VALUES (?)";
            System.out.println(cpf);
            jdbcTemplate.update(sql, cpf);
        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException){
                throw new RuntimeException("Gerente ja existente ou funcionario não cadastrado", e);
            }
            throw new RuntimeException(e.getCause());
        }
    }

    public List<Funcionario> findByName(String name){
        try{
            String sql = "SELECT f.cpf, f.nome, f.salario FROM Gerente g " +
                    "JOIN Funcionario f ON f.cpf = g.cpf " +
                    "where f.nome like ?";
            return jdbcTemplate.query(sql, rowMapper, "%"+name+"%");
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Funcionario findByCpf(String cpf) {
        try {
            String sql = "SELECT f.cpf, f.nome, f.salario FROM Gerente g " +
                    "JOIN Funcionario f ON f.cpf = g.cpf " +
                    "where f.cpf = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, cpf);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteFuncionario(String cpf){
        String sql = "delete from Gerente where cpf = ?";
        jdbcTemplate.update(sql, cpf);
    }
}
