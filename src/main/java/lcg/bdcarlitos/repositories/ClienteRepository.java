package lcg.bdcarlitos.repositories;

import lcg.bdcarlitos.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Cliente> rowMapper = (ResultSet rs, int rowNum) -> {
        Cliente cliente = new Cliente();
        cliente.setNome(rs.getString("nome"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setComplemento(rs.getString("complemento"));
        cliente.setRua(rs.getString("rua"));
        cliente.setBairro(rs.getString("bairro"));
        cliente.setNumero(rs.getInt("numero"));
        cliente.setCep(rs.getString("cep"));
        return cliente;
    };

    public List<Cliente> getAll() {
        try {
            String sql = "select * from Clientes";
            return jdbcTemplate.query(sql, rowMapper);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public Cliente create(Cliente cliente) {
        try {
            String sql = "INSERT INTO Cliente(nome, telefone, complemento, rua, bairro, numero, cep) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    cliente.getNome(),
                    cliente.getTelefone(),
                    cliente.getComplemento(),
                    cliente.getRua(),
                    cliente.getBairro(),
                    cliente.getNumero(),
                    cliente.getCep()
            );
            return cliente;
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public List<Cliente> findByName(String name) {
        try {
            String sql = "select * from Clientes where nome like ?";
            return jdbcTemplate.query(sql, rowMapper, "%" + name + "%");
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public Cliente findByPhone(String Phone) {
        try {
            String sql = "select * from Clientes where telefone like ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, "%" + Phone + "%");
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public Cliente updateByPhone(String telefone, Cliente cliente) {
        try {
            String sql = "UPDATE Clientes SET nome = ?, complemento = ?, rua = ?, bairro = ?, numero = ?, cep = ? WHERE telefone = ?";
            jdbcTemplate.update(sql,
                    cliente.getNome(),
                    telefone,
                    cliente.getComplemento(),
                    cliente.getRua(),
                    cliente.getBairro(),
                    cliente.getNumero(),
                    cliente.getCep()
            );
            return cliente;
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public void deleteCliente(String telefone) {
        String sql = "delete from Clientes where telefone = ?";
        jdbcTemplate.update(sql, telefone);
    }

}