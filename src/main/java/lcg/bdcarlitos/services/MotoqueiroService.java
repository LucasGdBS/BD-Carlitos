package lcg.bdcarlitos.services;

import lcg.bdcarlitos.entities.Motoqueiro;
import lcg.bdcarlitos.repositories.MotoqueiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoqueiroService {

    @Autowired
    private MotoqueiroRepository motoqueiroRepository;

    public List<Motoqueiro> getAll(){ return motoqueiroRepository.getAll();}

    public void create(Motoqueiro motoqueiro){
        if (motoqueiro.getCpf().isBlank()){throw new RuntimeException("Campo CPF vazio");}
        if (motoqueiro.getGerenteMotorqueiro_cpf().isBlank()){throw new RuntimeException("Campo gerenteCpf vazio");}
        motoqueiroRepository.create(motoqueiro);
    }
}
