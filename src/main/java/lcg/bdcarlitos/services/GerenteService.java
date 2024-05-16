package lcg.bdcarlitos.services;

import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.repositories.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;

    public List<Funcionario> getAll() { return gerenteRepository.getAll(); }

    public List<Funcionario> findByName(String name){
        return gerenteRepository.findByName(name);
    }

    public Funcionario findByCpf(String cpf){ return gerenteRepository.findByCpf(cpf); }

    public void delete(String cpf){
        Funcionario funcionarioExistente = gerenteRepository.findByCpf(cpf);
        if (funcionarioExistente == null){
            throw new RuntimeException("Funcionario não encontrado");
        }
        gerenteRepository.deleteFuncionario(cpf);
    }
}
