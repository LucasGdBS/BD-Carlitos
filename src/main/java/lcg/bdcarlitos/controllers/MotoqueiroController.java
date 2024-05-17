package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.entities.Motoqueiro;
import lcg.bdcarlitos.services.MotoqueiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("motoqueiros")
public class MotoqueiroController {

    @Autowired
    private MotoqueiroService motoqueiroService;

    @GetMapping("/")
    public ResponseEntity<?> getAllMotoqueiros(){
        try{
            return new ResponseEntity<>(motoqueiroService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar Motoqueiros " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createMotoqueiro(@RequestBody Motoqueiro motoqueiro){
        // Só precisa colocar CPF e o CPF do gerente
        try{
            motoqueiroService.create(motoqueiro);
            return new ResponseEntity<>("Cargo de motoqueiro atribuido com sucesso", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao atribuir cargo ao gerente: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> findMotoqueiroByName(@RequestParam String nome){
        try{
            return new ResponseEntity<>(motoqueiroService.findByName(nome), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar motoqueiro " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-por-cpf")
    public ResponseEntity<?> findMotoqueiroByCpf(@RequestParam String cpf){
        try{
            Motoqueiro motoqueiro = motoqueiroService.findByCpf(cpf);
            if (motoqueiro != null){
                return new ResponseEntity<>(motoqueiro, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar motoqueiro " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deleteGerente(@PathVariable String cpf){
        try{
            motoqueiroService.delete(cpf);
            return new ResponseEntity<>("Cargo de motoqueiro retirado", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
