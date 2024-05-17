package lcg.bdcarlitos.controllers;

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
}
