package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.entities.Pedido;
import lcg.bdcarlitos.entities.ResumoPedido;
import lcg.bdcarlitos.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/")
    public ResponseEntity<?> getAllPedidos(){
        try{
            return new ResponseEntity<>(pedidoService.getAll(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar Pedidos " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<?> findPedidosById(@RequestParam int id){
        try{
            return new ResponseEntity<>(pedidoService.findById(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar Pedidos " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> findPedidosByNome(@RequestParam String nome){
        try{
            return new ResponseEntity<>(pedidoService.findByName(nome), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar Pedidos " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/resumo/{id}")
    public ResponseEntity<?> resumoPedido(@PathVariable int id){
        try{
            ResumoPedido resumoPedido = pedidoService.resumoPedido(id);
            if (resumoPedido != null){
                return new ResponseEntity<>(resumoPedido, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar pedido " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/resumo")
    public ResponseEntity<?> findResumosByName(@RequestParam String nome){
        try{
            return new ResponseEntity<>(pedidoService.resumoPedidoByName(nome), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar Pedidos " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/")
    public ResponseEntity<?> createPedido(@RequestBody Pedido pedido){
        try{
            return new ResponseEntity<>(pedidoService.create(pedido), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao criar Pedido: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFuncionario(@PathVariable int id) {
        try {
            pedidoService.delete(id);
            return new ResponseEntity<>("Pedido deletado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
