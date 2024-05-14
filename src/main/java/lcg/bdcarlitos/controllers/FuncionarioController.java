package lcg.bdcarlitos.controllers;


import lcg.bdcarlitos.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/")
    public ResponseEntity<?> getAllFuncionarios(){
        try{
            return new ResponseEntity<>(funcionarioService.getAll(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar Clientes " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
