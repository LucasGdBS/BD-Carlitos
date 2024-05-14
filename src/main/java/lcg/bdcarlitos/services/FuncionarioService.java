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
}
