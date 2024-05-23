package lcg.bdcarlitos.services;

import lcg.bdcarlitos.entities.Cliente;
import lcg.bdcarlitos.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAll(){
        return clienteRepository.getAll();
    }

    public Cliente create(Cliente cliente) {
        if (cliente.getNome().isBlank()) { throw new RuntimeException("Campo Nome vazio"); }
        if (cliente.getTelefone_1().isBlank()) { throw new RuntimeException("Campo Telefone 1 vazio");}
        if (cliente.getTelefone_2().isBlank()) { throw new RuntimeException("Campo Telefone 1 vazio");}
        if (cliente.getComplemento().isBlank()) { throw new RuntimeException("Campo Cidade vazio"); }
        if (cliente.getRua().isBlank()) { throw new RuntimeException("Campo Rua vazio"); }
        if (cliente.getBairro().isBlank()) { throw new RuntimeException("Campo Bairro vazio"); }
        if (cliente.getNumero() == 0) { throw new RuntimeException("Campo Numero vazio"); }
        if (cliente.getCep().isBlank()) { throw new RuntimeException("Campo CEP vazio"); }
        Cliente clienteCriado = clienteRepository.create(cliente);
        clienteCriado.setId_cliente(getAll().getLast().getId_cliente());

        return clienteCriado;
    }

    public List<Cliente> findByName(String name){
        return clienteRepository.findByName(name);
    }

    public Cliente findByPhone(String phone){ return clienteRepository.findByPhone(phone); }

    public Cliente update(String phone, Cliente cliente){
        try{
            Cliente clienteExistente = clienteRepository.findByPhone(phone);
            if (clienteExistente != null){
                if (cliente.getNome() == null || cliente.getNome().isBlank()){cliente.setNome(clienteExistente.getNome());}
                if (cliente.getTelefone_1() == null || cliente.getTelefone_1().isBlank()){cliente.setTelefone_1(clienteExistente.getTelefone_1());}
                if (cliente.getTelefone_2() == null || cliente.getTelefone_2().isBlank()){cliente.setTelefone_2(clienteExistente.getTelefone_1());}
                if (cliente.getRua() == null || cliente.getRua().isBlank()) { cliente.setRua(clienteExistente.getRua()); }
                if (cliente.getBairro() == null || cliente.getBairro().isBlank()) {cliente.setBairro(clienteExistente.getBairro());}
                if (cliente.getComplemento() == null || cliente.getComplemento().isBlank()) { cliente.setComplemento(clienteExistente.getComplemento()); }
                if (cliente.getNumero() == 0){cliente.setNumero(clienteExistente.getNumero());}
                if (cliente.getCep() == null || cliente.getCep().isBlank()) { cliente.setCep(clienteExistente.getCep()); }
                cliente.setId_cliente(clienteExistente.getId_cliente());
                return clienteRepository.updateByPhone(phone, cliente);
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void delete(int id){
        Cliente clienteExistente = clienteRepository.findById(id);
        if (clienteExistente == null){
            throw new RuntimeException("Cliente não encontrado");
        }
        clienteRepository.deleteCliente(id);
    }

}
