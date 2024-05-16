package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.services.MotoqueiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
