package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Atendente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class AtendenteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    public void create(Atendente atendente){
        try{
            String sql = "insert into atendentes (cpf, gerente, turno) values " +
                    "(?, ?, ?)";
            jdbcTemplate.update(sql, atendente.getCpf(), atendente.getCpf_gerente(), atendente.getTurno());
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }
    public Atendente findByCpf(String cpf){
        try{
            String sql = "select a.cpf, f.nome, a.turno, f.salario, f2.nome as nome_gerente, g.cpf as cpf_gerente " +
                    "from atendentes a\n" +
                    "join funcionario f on a.cpf = f.cpf " +
                    "join gerente g on a.gerente = g.cpf " +
                    "join funcionario f2 on g.cpf = f2.cpf where a.cpf = ?";;
            return jdbcTemplate.queryForObject(sql, rowMapper, cpf);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Atendente> findByName(String name){
        try{
            String sql = "select a.cpf, f.nome, a.turno, f.salario, f2.nome as nome_gerente, g.cpf as cpf_gerente " +
                    "from atendentes a\n" +
                    "join funcionario f on a.cpf = f.cpf " +
                    "join gerente g on a.gerente = g.cpf " +
                    "join funcionario f2 on g.cpf = f2.cpf where f.nome like ?";
            return jdbcTemplate.query(sql, rowMapper, "%"+name+"%");
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public List<Atendente> findByGerenteCpf(String cpf){
        try{
            String sql = "select a.cpf, f.nome, a.turno, f.salario, f2.nome as nome_gerente, g.cpf as cpf_gerente " +
                    "from atendentes a " +
                    "join funcionario f on a.cpf = f.cpf " +
                    "join gerente g on a.gerente = g.cpf " +
                    "join funcionario f2 on g.cpf = f2.cpf where a.gerente = ?";
            return jdbcTemplate.query(sql, rowMapper, cpf);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }


    public Atendente updateByCpf(String cpf, Atendente atendente){
        try{
            String sql = "UPDATE atendentes SET gerente = ?, turno = ? WHERE cpf = ?";
            jdbcTemplate.update(sql, atendente.getCpf_gerente(), atendente.getTurno(), cpf);
            return atendente;
        }catch (DataAccessException e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void deleteAtendente(String cpf){
        String sql = "delete from atendentes where cpf = ?";
        jdbcTemplate.update(sql, cpf);
    }


}
