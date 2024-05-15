package lcg.bdcarlitos.services;

import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> getAll(){
        return funcionarioRepository.getAll();
    }

    public Funcionario create(Funcionario funcionario) {
        if (funcionario.getCpf().isBlank()){throw new RuntimeException("Campo CPF vazio");}
        if (funcionario.getNome().isBlank()){throw new RuntimeException("Campo Nome vazio");}
        if (funcionario.getSalario() == 0.0){throw new RuntimeException("Campo Salario vazio");}
        return funcionarioRepository.create(funcionario);
    }

    public List<Funcionario> findByName(String name){
        return funcionarioRepository.findByName(name);
    }

    public Funcionario findByCpf(String cpf){ return funcionarioRepository.findByCpf(cpf); }

    public Funcionario update(String cpf, Funcionario funcionario){
        try{
            Funcionario funcExistente = funcionarioRepository.findByCpf(cpf);
            if (funcExistente != null){
                if (funcionario.getNome() == null || funcionario.getNome().isBlank()){funcionario.setNome(funcExistente.getNome());}
                if (funcionario.getSalario() == 0.0){funcionario.setSalario(funcExistente.getSalario());}
                if (funcionario.getCpf() == null || funcionario.getCpf().isBlank()){funcionario.setCpf(funcExistente.getCpf());}
                return funcionarioRepository.updateByCpf(cpf, funcionario);
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void delete(String cpf){
        Funcionario funcionarioExistente = funcionarioRepository.findByCpf(cpf);
        if (funcionarioExistente == null){
            throw new RuntimeException("Funcionario não encontrado");
        }
        funcionarioRepository.deleteFuncionario(cpf);
    }
}
