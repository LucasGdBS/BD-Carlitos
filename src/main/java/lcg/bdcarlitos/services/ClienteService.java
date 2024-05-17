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
        if (cliente.getTelefone().isBlank()) { throw new RuntimeException("Campo Telefone vazio");}
        if (cliente.getComplemento().isBlank()) { throw new RuntimeException("Campo Cidade vazio"); }
        if (cliente.getRua().isBlank()) { throw new RuntimeException("Campo Rua vazio"); }
        if (cliente.getBairro().isBlank()) { throw new RuntimeException("Campo Bairro vazio"); }
        if (cliente.getNumero() == 0) { throw new RuntimeException("Campo Numero vazio"); }
        if (cliente.getCep().isBlank()) { throw new RuntimeException("Campo CEP vazio"); }
        return clienteRepository.create(cliente);
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
                if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()){cliente.setTelefone(clienteExistente.getTelefone());}
                if (cliente.getRua() == null || cliente.getRua().isBlank()) { cliente.setRua(clienteExistente.getRua()); }
                if (cliente.getBairro() == null || cliente.getBairro().isBlank()) {cliente.setBairro(clienteExistente.getBairro());}
                if (cliente.getComplemento() == null || cliente.getComplemento().isBlank()) { cliente.setComplemento(clienteExistente.getComplemento()); }
                if (cliente.getNumero() == 0){cliente.setNumero(clienteExistente.getNumero());}
                if (cliente.getCep() == null || cliente.getCep().isBlank()) { cliente.setCep(clienteExistente.getCep()); }
                return clienteRepository.updateByPhone(phone, cliente);
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void delete(String phone){
        Cliente clienteExistente = clienteRepository.findByPhone(phone);
        if (clienteExistente == null){
            throw new RuntimeException("Cliente não encontrado");
        }
        clienteRepository.deleteCliente(phone);
    }

}