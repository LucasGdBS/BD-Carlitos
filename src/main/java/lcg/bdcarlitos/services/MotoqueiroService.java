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
}
