package lcg.bdcarlitos.controllers;



import lcg.bdcarlitos.entities.Cliente;
import lcg.bdcarlitos.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            return new ResponseEntity<>("Erro ao encontrar funcionario " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}
