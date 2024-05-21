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

    public List<Motoqueiro> findByName(String name){ return motoqueiroRepository.findByName(name);}

    public Motoqueiro findByCpf(String cpf){ return motoqueiroRepository.findByCpf(cpf); }

    public void create(Motoqueiro motoqueiro){
        if (motoqueiro.getCpf().isBlank()){throw new RuntimeException("Campo CPF vazio");}
        motoqueiroRepository.create(motoqueiro);
    }

    public void delete(String cpf){
        Motoqueiro motoqueiroExistente = motoqueiroRepository.findByCpf(cpf);
        if (motoqueiroExistente == null){
            throw new RuntimeException("Motoqueiro não encontrado");
        }
        motoqueiroRepository.delete(cpf);
    }

    public Motoqueiro update(String cpf, Motoqueiro motoqueiro){
        try{
            Motoqueiro motoqueiroExistente = motoqueiroRepository.findByCpf(cpf);
            if (motoqueiroExistente != null){
                return motoqueiroRepository.updateBycpf(cpf, motoqueiroExistente);
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
    }
}
