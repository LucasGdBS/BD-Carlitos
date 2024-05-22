package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.entities.Ingrediente;
import lcg.bdcarlitos.services.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping("/")
    public ResponseEntity<?> getAllIngredientes(){
        try{
            return new ResponseEntity<>(ingredienteService.getAll(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar Ingredientes " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createIngrediente(@RequestBody Ingrediente ingrediente){
        try{
            return new ResponseEntity<>(ingredienteService.create(ingrediente), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao criar ingrediente: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> findIngredientesByName(@RequestParam String nome){
        try{
            return new ResponseEntity<>(ingredienteService.findByName(nome), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar ingrediente " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<?> findFuncionariosByCpf(@RequestParam int id){
        try{
            Ingrediente ingrediente = ingredienteService.findByCodigo(id);
            if (ingrediente != null){
                return new ResponseEntity<>(ingrediente, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar ingrediente " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editar-por-id/{id}")
    public ResponseEntity<?> editFuncionario(@PathVariable int id, @RequestBody Ingrediente ingrediente){
        try{
            Ingrediente ingredienteExistente = ingredienteService.update(id, ingrediente);
            return new ResponseEntity<>(ingredienteExistente, HttpStatus.OK);
        }catch (Exception e){
            if (e.getCause() == null){
                return new ResponseEntity<>("Ingrediente não encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Erro ao editar Ingrediente " + e.getCause(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFuncionario(@PathVariable int id){
        try{
            ingredienteService.delete(id);
            return new ResponseEntity<>("Ingrediente deletado", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
