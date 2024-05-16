package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.entities.Gerente;
import lcg.bdcarlitos.services.GerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gerentes")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @GetMapping("")
    public ResponseEntity<?> getAllGerentes(){
        try{
            return new ResponseEntity<>(gerenteService.getAll(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar gerentes " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> findGerenteByName(@RequestParam String nome){
        try{
            return new ResponseEntity<>(gerenteService.findByName(nome), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar gerente " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-por-cpf")
    public ResponseEntity<?> findGerenteByCpf(@RequestParam String cpf){
        try{
            Funcionario funcionario = gerenteService.findByCpf(cpf);
            if (funcionario != null){
                return new ResponseEntity<>(funcionario, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar gerente " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deleteGerente(@PathVariable String cpf){
        try{
            gerenteService.delete(cpf);
            return new ResponseEntity<>("Cargo de gerente retirado", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createGerente(@RequestBody Gerente gerente){
        try{
            gerenteService.create(gerente.getCpf());
            return new ResponseEntity<>("Cargo de gerente atribuido com sucesso", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao atribuir cargo de gerente: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
