package lcg.bdcarlitos.services;

import lcg.bdcarlitos.entities.Produto;
import lcg.bdcarlitos.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> getAll() { return produtoRepository.getAll(); }

    public Produto create(Produto produto){
        if (produto.getNome().isBlank()){throw new RuntimeException("Campo nome vazio");}
        Produto produtoCriado = produtoRepository.create(produto);
        produto.setId_produto(getAll().size()+1);
        produtoCriado.setId_produto(getAll().getLast().getId_produto());
        return produtoCriado;
    }

    public List<Produto> findByName(String name){ return produtoRepository.findByName(name);}

    public Produto findById(int id){return produtoRepository.findById(id);}

    public Produto update(int id, Produto produto){
        try{
            Produto produtoExistente = produtoRepository.findById(id);
            if (produtoExistente != null){
                if (produto.getId_produto() == 0){produto.setId_produto(produtoExistente.getId_produto());}
                return produtoRepository.updateById(id, produto);
            }else{ return null; }
        }catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void delete(int id){
        Produto produtoExistente = produtoRepository.findById(id);
        if (produtoExistente == null){
            throw new RuntimeException("Funcionario não encontrado");
        }
        produtoRepository.deleteProduto(id);
    }
}
