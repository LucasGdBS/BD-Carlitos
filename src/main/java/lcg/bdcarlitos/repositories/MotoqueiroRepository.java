package lcg.bdcarlitos.repositories;



import lcg.bdcarlitos.entities.Motoqueiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public class MotoqueiroRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Motoqueiro> rowMapper = (ResultSet rs, int rowNum) -> {
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

    public List<Motoqueiro> findByName(String name){
        try{
            String sql = "select m.cpf, f.nome, f.salario, m.gerente_motoqueiro, " +
                    "f2.nome as nome_gerente from motoqueiro m join funcionario f on m.cpf = f.cpf " +
                    "left join funcionario f2 on m.gerente_motoqueiro = f2.cpf " +
                    "where f.nome like ?";
            return jdbcTemplate.query(sql, rowMapper, "%"+name+"%");
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public Motoqueiro findByCpf(String cpf) {
        try {
            String sql = "select m.cpf, f.nome, f.salario, m.gerente_motoqueiro, " +
                    "f2.nome as nome_gerente from motoqueiro m join funcionario f on m.cpf = f.cpf " +
                    "left join funcionario f2 on m.gerente_motoqueiro = f2.cpf " +
                    "where m.cpf = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, cpf);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void delete(String cpf){
        String sql = "delete from motoqueiro where cpf = ?";
        jdbcTemplate.update(sql, cpf);
    }

    public Motoqueiro updateBycpf(String cpf, Motoqueiro motoqueiro){
        try{
            String sql = "UPDATE motoqueiro set gerente_motoqueiro = ? where cpf = ?";
            jdbcTemplate.update(sql, motoqueiro.getGerenteMotorqueiro_cpf(), cpf);
            return motoqueiro;
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }


}
