package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Atendente;
import lcg.bdcarlitos.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class AtendenteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FuncionarioService funcionarioService;

    private final RowMapper<Atendente> rowMapper = (ResultSet rs, int rowNum) -> {
        Atendente atendente = new Atendente();
        atendente.setCpf(rs.getString("cpf"));
        atendente.setNome(rs.getString("nome"));
        atendente.setSalario(rs.getFloat("salario"));
        atendente.setTurno(rs.getString("turno"));
        atendente.setNome_gerente(rs.getString("nome_gerente"));
        atendente.setCpf_gerente(rs.getString("cpf_gerente"));

        return atendente;
    };

    public List<Atendente> getAll(){
        try{
            String sql = "select a.cpf, f.nome, a.turno, f.salario, f2.nome as nome_gerente, g.cpf as cpf_gerente " +
                    "from atendentes a\n" +
                    "join funcionario f on a.cpf = f.cpf " +
                    "join gerente g on a.gerente = g.cpf " +
                    "join funcionario f2 on g.cpf = f2.cpf";
            return jdbcTemplate.query(sql, rowMapper);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }
}