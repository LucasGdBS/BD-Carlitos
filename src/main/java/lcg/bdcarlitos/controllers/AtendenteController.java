package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.services.AtendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atentendes")
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
}
