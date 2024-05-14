package lcg.bdcarlitos.controllers;


import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>("Erro ao buscar Funcionario " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createFuncionario(@RequestBody Funcionario funcionario){
        try{
            return new ResponseEntity<>(funcionarioService.create(funcionario), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao criar Funcionario: " + e.getCause(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
