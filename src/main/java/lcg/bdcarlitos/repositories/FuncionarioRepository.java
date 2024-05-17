package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class FuncionarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Funcionario> rowMapper = (ResultSet rs, int rowNum) -> {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(rs.getString("cpf"));
        funcionario.setNome(rs.getString("nome"));
        funcionario.setSalario(rs.getFloat("salario"));
        return funcionario;
    };

    public List<Funcionario> getAll(){
        try{
            String sql = "select * from Funcionario";
            return jdbcTemplate.query(sql, rowMapper);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Funcionario create(Funcionario funcionario) {
        try {
            String sql = "INSERT INTO Funcionario(cpf, nome, salario) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, funcionario.getCpf(), funcionario.getNome(), funcionario.getSalario());
            return funcionario;
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public List<Funcionario> findByName(String name){
        try{
            String sql = "select * from Funcionario where nome like ?";
            return jdbcTemplate.query(sql, rowMapper, "%"+name+"%");
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Funcionario findByCpf(String cpf) {
        try {
            String sql = "SELECT * FROM Funcionario WHERE cpf = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, cpf);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Funcionario updateByCpf(String cpf, Funcionario funcionario){
        try{
            String sql = "UPDATE Funcionario SET nome = ?, salario = ? WHERE cpf = ?";
            jdbcTemplate.update(sql, funcionario.getNome(), funcionario.getSalario(), cpf);
            return funcionario;
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void deleteFuncionario(String cpf){
        String sql = "delete from Funcionario where cpf = ?";
        jdbcTemplate.update(sql, cpf);
    }
}
