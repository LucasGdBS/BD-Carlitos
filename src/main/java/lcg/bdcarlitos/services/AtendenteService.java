package lcg.bdcarlitos.services;

import lcg.bdcarlitos.entities.Atendente;
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

    public Atendente create(Atendente atendente){
        atendenteRepository.create(atendente);
        return atendenteRepository.findByCpf(atendente.getCpf());
    }
}
