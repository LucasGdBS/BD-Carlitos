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
        try {
            return funcionarioRepository.create(funcionario);
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public List<Funcionario> findByName(String name){
        return funcionarioRepository.findByName(name);
    }

    public Funcionario findByCpf(String cpf){
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf);
        if (funcionario != null){
            System.out.println(true);
            return funcionario;
        }
        System.out.println(false);
        return null;
    }

    public Funcionario update(String cpf, Funcionario funcionario){
        try{
            Funcionario funcExistente = funcionarioRepository.findByCpf(cpf);
            if (funcExistente != null){
                if (funcionario.getNome() == null){funcionario.setNome(funcExistente.getNome());}
                if (funcionario.getSalario() == 0.0){funcionario.setSalario(funcExistente.getSalario());}
                if (funcionario.getCpf() == null){funcionario.setCpf(funcExistente.getCpf());}
                return funcionarioRepository.updateByCpf(cpf, funcionario);
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
    }
}
