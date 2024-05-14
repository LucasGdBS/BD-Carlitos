package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class FuncionarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Funcionario> rowMapper = (ResultSet rs, int rowNum) -> {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(rs.getString("cpf"));
        funcionario.setNome(rs.getString("nome"));
        funcionario.setSalario(rs.getFloat("salario"));
        return funcionario;
    };

    public List<Funcionario> getAll(){
        String sql = "select * from Funcionario";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
