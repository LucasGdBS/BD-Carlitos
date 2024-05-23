package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.entities.Produto;
import lcg.bdcarlitos.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/")
    public ResponseEntity<?> getAllProdutos(){
        try{
            return new ResponseEntity<>(produtoService.getAll(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao buscar Produtos " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> findProdutosByName(@RequestParam String nome){
        try{
            return new ResponseEntity<>(produtoService.findByName(nome), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar produtos " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<?> findFuncionariosByCpf(@RequestParam int id){
        try{
            Produto produto = produtoService.findById(id);
            if (produto != null){
                return new ResponseEntity<>(produto, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Erro ao encontrar produto " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createProduto(@RequestBody Produto produto){
        try{
            System.out.println(Arrays.toString(produto.getIngredientes()));
            return new ResponseEntity<>(produtoService.create(produto, produto.getIngredientes()), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao criar Produto: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/editar-por-id/{id}")
    public ResponseEntity<?> editProduto(@PathVariable int id, @RequestBody Produto produto){
        try{
            Produto produtoExistente = produtoService.update(id, produto);
            return new ResponseEntity<>(produtoExistente, HttpStatus.OK);
        }catch (Exception e){
            if (e.getCause() == null){
                return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Erro ao editar Produto " + e.getCause(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFuncionario(@PathVariable int id){
        try{
            produtoService.delete(id);
            return new ResponseEntity<>("Produto deletado", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-ingredientes-produto")
    public ResponseEntity<?> findIngredienteByProdutoId(@RequestParam int id){
        try{
            if(produtoService.findById(id) == null){ return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);}
            return new ResponseEntity<>(produtoService.getIngredientesByProduto(id),
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
