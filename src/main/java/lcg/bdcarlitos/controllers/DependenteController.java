package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.entities.Dependente;
import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.services.DependenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dependentes")
public class DependenteController {

    @Autowired
    private DependenteService dependenteService;

    @GetMapping("/")
    public ResponseEntity<?> getAllDependente(){
        try{
            return new ResponseEntity<>(dependenteService.getAll(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar dependente " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createDependente(@RequestBody Dependente dependente){
        try{
            return new ResponseEntity<>(dependenteService.create(dependente), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao criar Dependente: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/buscar-por-cpf-titular")
    public ResponseEntity<?> findByCpfTitular(@RequestParam String cpf){
        try{
            return new ResponseEntity<>(dependenteService.findByCpfDono(cpf), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar dependentes desse funcionario " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDependente(@PathVariable int id){
        try{
            dependenteService.delete(id);
            return new ResponseEntity<>("Dependente deletado", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
