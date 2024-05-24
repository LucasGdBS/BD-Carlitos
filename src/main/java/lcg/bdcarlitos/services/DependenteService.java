package lcg.bdcarlitos.services;

import lcg.bdcarlitos.entities.Dependente;
import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.repositories.DependenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DependenteService {

    @Autowired
    private DependenteRepository dependenteRepository;

    public List<Dependente> getAll() { return dependenteRepository.getAll(); }

    public Dependente create(Dependente dependente) {
        if (dependente.getData_nacimento().isBlank()){throw new RuntimeException("Campo data de nascimento vazio");}
        if (dependente.getNome().isBlank()){throw new RuntimeException("Campo nome vazio");}
        if (dependente.getCpf_funcionario().isBlank()){throw new RuntimeException("Campo Cpf do titular do vazio");}
        if (dependente.getRelacao().isBlank()){throw new RuntimeException("Campo da relação do vazio");}
        return dependenteRepository.create(dependente);
    }

    public List<Dependente> findByCpfDono(String cpfDono) { return dependenteRepository.findByCpfDono(cpfDono);}

    public void delete(int id){
        Dependente dependenteExistente = dependenteRepository.findById(id);
        if (dependenteExistente == null){
            throw new RuntimeException("Dependente não encontrado");
        }
        dependenteRepository.deleteDependentes(id);
    }



}
