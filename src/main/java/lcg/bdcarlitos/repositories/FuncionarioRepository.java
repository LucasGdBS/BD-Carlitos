package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FuncionarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Funcionario> listarFuncionarios(){
        String sql = "select * from Funcionario";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Funcionario funcionario = new Funcionario();
            funcionario.setCpf(resultSet.getString("cpf"));
            funcionario.setNome(resultSet.getString("nome"));
            funcionario.setSalario(resultSet.getFloat("salario"));
            return funcionario;
        });
    }
}
