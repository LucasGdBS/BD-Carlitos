package lcg.bdcarlitos.services;

import lcg.bdcarlitos.entities.Atendente;
import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.repositories.AtendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtendenteService {

    @Autowired
    private AtendenteRepository atendenteRepository;

    public List<Atendente> getAll() {return atendenteRepository.getAll(); }
    public Atendente findByCpf(String cpf) { return atendenteRepository.findByCpf(cpf);}

    public List<Atendente> findByName(String name){return atendenteRepository.findByName(name);}

    public List<Atendente> findByGerente(String cpf){return atendenteRepository.findByGerenteCpf(cpf);}

    public Atendente create(Atendente atendente){
        atendenteRepository.create(atendente);
        return atendenteRepository.findByCpf(atendente.getCpf());
    }

    public Atendente update(String cpf, Atendente atendente){
        try{
            Atendente atendenteExistente = atendenteRepository.findByCpf(cpf);
            if (atendenteExistente != null){
                 if (atendente.getCpf_gerente() == null || atendente.getCpf_gerente().isBlank())
                    atendente.setCpf_gerente(atendenteExistente.getCpf_gerente());
                 if (atendente.getTurno() == null || atendente.getTurno().isBlank())
                    atendente.setTurno(atendenteExistente.getTurno());
                 atendente.setCpf(cpf);
                 atendente.setNome(atendenteExistente.getNome());
                 atendente.setSalario(atendenteExistente.getSalario());
                 atendente.setNome_gerente(atendenteExistente.getNome_gerente());
                return atendenteRepository.updateByCpf(cpf, atendente);
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void delete(String cpf){
        Funcionario funcionarioExistente = atendenteRepository.findByCpf(cpf);
        if (funcionarioExistente == null){
            throw new RuntimeException("Atendente não encontrado");
        }
        atendenteRepository.deleteAtendente(cpf);
    }
}
