package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Motoqueiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public class MotoqueiroRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Motoqueiro> rowMapper = (ResultSet rs, int rowNum) -> {
        Motoqueiro motoqueiro = new Motoqueiro();
        motoqueiro.setCpf(rs.getString("cpf"));
        motoqueiro.setNome(rs.getString("nome"));
        motoqueiro.setSalario(rs.getFloat("salario"));
        motoqueiro.setGerenteMotorqueiro_cpf(rs.getString("gerente_motoqueiro"));
        motoqueiro.setGerenteMotoqueiro_nome(rs.getString("nome_gerente"));
        return motoqueiro;
    };

    public List<Motoqueiro> getAll(){
        try{
            String sql = "select m.cpf, f.nome, f.salario, m.gerente_motoqueiro, " +
                    "f2.nome as nome_gerente from motoqueiro m join funcionario f on m.cpf = f.cpf " +
                    "left join funcionario f2 on m.gerente_motoqueiro = f2.cpf";
            return jdbcTemplate.query(sql, rowMapper);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void create(Motoqueiro motoqueiro){
        try{
            String sql = "insert into Motoqueiro(cpf, gerente_motoqueiro) values " +
                    "(?, ?)";
            jdbcTemplate.update(sql, motoqueiro.getCpf(), motoqueiro.getGerenteMotorqueiro_cpf());
        }catch (DataAccessException e){
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException){
                throw new RuntimeException("Motoqueiro ja existente ou funcionario não cadastrado");
            }
            throw new RuntimeException(e.getCause());
        }
    }
}
