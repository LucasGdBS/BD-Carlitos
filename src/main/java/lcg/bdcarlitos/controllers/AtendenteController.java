package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.entities.Atendente;
import lcg.bdcarlitos.services.AtendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atendentes")
public class AtendenteController {

    @Autowired
    private AtendenteService atendenteService;

    @GetMapping("/")
    public ResponseEntity<?> getAllAtendentes(){
        try{
            return new ResponseEntity<>(atendenteService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar atendentes" + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar-por-cpf")
    public ResponseEntity<?> findAtendentesByCpf(@RequestParam String cpf){
        try{
            Atendente atendente = atendenteService.findByCpf(cpf);
            if (atendente != null){
                return new ResponseEntity<>(atendente, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar atendente " + e.getCause(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createAtendente(@RequestBody Atendente atendente){
        try{
            return new ResponseEntity<>(atendenteService.create(atendente), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao criar atendente "+ e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> findAtendentesByName(@RequestParam String nome){
        try{
            return new ResponseEntity<>(atendenteService.findByName(nome), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar atendentes " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-por-gerente")
    public ResponseEntity<?> findAtendentesByGerente(@RequestParam String cpf){
        try{
            return new ResponseEntity<>(atendenteService.findByGerente(cpf), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar atendentes " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
