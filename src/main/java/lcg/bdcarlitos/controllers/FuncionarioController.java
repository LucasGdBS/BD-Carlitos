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
            Funcionario funcionario = funcionarioService.findByCpf(cpf);
            if (funcionario != null){
                return new ResponseEntity<>(funcionario, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>("Erro ao criar Funcionario: " + e.getMessage(),
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

    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deleteFuncionario(@PathVariable String cpf){
        try{
            funcionarioService.delete(cpf);
            return new ResponseEntity<>("Funcionario deletado", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
