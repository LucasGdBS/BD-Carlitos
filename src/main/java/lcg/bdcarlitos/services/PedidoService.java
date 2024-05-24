package lcg.bdcarlitos.services;

import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.entities.Pedido;
import lcg.bdcarlitos.entities.ResumoPedido;
import lcg.bdcarlitos.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> getAll(){
        return pedidoRepository.getAll();
    }
    public List<Pedido> findById(int id) { return pedidoRepository.findByNumPedido(id);}

    public List<Pedido> findByName(String nome) { return pedidoRepository.findByNome(nome);}

    public ResumoPedido resumoPedido(int id) { return pedidoRepository.resumoPedido(id);}

    public List<ResumoPedido> resumoPedidoByName(String nome) { return pedidoRepository.resumoPedidoByName(nome);}


    public Pedido create(Pedido pedido){
        if (pedido.getDtPedido().isBlank()){throw new RuntimeException("Data do pedido em branco");}
        if (pedido.getQntProduto() <= 0){throw new RuntimeException("Quantidade do produto inválida");}
        return pedidoRepository.create(pedido);
    }

    public void delete(int id){
        ResumoPedido resumoPedido = pedidoRepository.resumoPedido(id);
        if (resumoPedido == null){
            throw new RuntimeException("Pedido não encontrado");
        }
        pedidoRepository.deletePedido(id);
    }
}
