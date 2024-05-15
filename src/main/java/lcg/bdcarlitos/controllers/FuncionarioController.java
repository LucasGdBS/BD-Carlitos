package lcg.bdcarlitos.controllers;


import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.repositories.FuncionarioRepository;
import lcg.bdcarlitos.services.FuncionarioService;
import org.apache.tomcat.util.http.parser.HttpParser;
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

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> findFuncionariosByName(@RequestParam String nome){
        try{
            return new ResponseEntity<>(funcionarioService.findByName(nome), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar funcionario " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-por-cpf")
    public ResponseEntity<?> findFuncionariosByCpf(@RequestParam String cpf){
        try{
            return new ResponseEntity<>(funcionarioService.findByCpf(cpf), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar funcionario " + e.getMessage(), HttpStatus.NOT_FOUND);
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

    @PutMapping("/editar-por-cpf/{cpf}")
    public ResponseEntity<?> editFuncionario(@PathVariable String cpf, @RequestBody Funcionario funcionario){
        try{
            Funcionario funcionarioExistente = funcionarioService.update(cpf, funcionario);
            return new ResponseEntity<>(funcionarioExistente, HttpStatus.OK);
        }catch (Exception e){
            if (e.getCause() == null){
                return new ResponseEntity<>("Funcionario não encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Erro ao editar Funcionario " + e.getCause(), HttpStatus.BAD_REQUEST);
        }

    }
}
