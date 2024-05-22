package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.entities.Cliente;
import lcg.bdcarlitos.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public ResponseEntity<?> getAllCliente() {
        try {
            return new ResponseEntity<>(clienteService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar cliente " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> findClientessByName(@RequestParam String nome) {
        try {
            return new ResponseEntity<>(clienteService.findByName(nome), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao encontrar cliente " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-por-telefone")
    public ResponseEntity<?> findClienteByPhone(@RequestParam String phone) {
        try {
            Cliente cliente = clienteService.findByPhone(phone);
            if (cliente != null) {
                return new ResponseEntity<>(cliente, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao encontrar cliente " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente){
        try{
            return new ResponseEntity<>(clienteService.create(cliente), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao criar Cliente: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/editar-por-telefone/{telefone}")
    public ResponseEntity<?> editCliente(@PathVariable String telefone, @RequestBody Cliente cliente){
        try{
            Cliente clienteExistente = clienteService.update(telefone, cliente);
            return new ResponseEntity<>(clienteExistente, HttpStatus.OK);
        }catch (Exception e){
            if (e.getCause() == null){
                return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Erro ao editar Cliente " + e.getCause(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{telefone}")
    public ResponseEntity<?> deleteCliente(@PathVariable String telefone){
        try{
            clienteService.delete(telefone);
            return new ResponseEntity<>("Cliente deletado", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
