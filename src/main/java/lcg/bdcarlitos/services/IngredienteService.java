package lcg.bdcarlitos.services;

import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.entities.Ingrediente;
import lcg.bdcarlitos.repositories.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Ingrediente> getAll(){
        return ingredienteRepository.getAll();
    }

    public Ingrediente create(Ingrediente ingrediente){
        if (ingrediente.getNome().isBlank()){throw new RuntimeException("Campo nome vazio");}
        if (ingrediente.getDtValidade().isBlank()){throw new RuntimeException("Campo Validade Vazio");}
        if (ingrediente.getTipoAlimento().isBlank()){throw new RuntimeException("Campo Tipo aimento Vazio");}
        return ingredienteRepository.create(ingrediente);
    }

    public List<Ingrediente> findByName(String name){
        return ingredienteRepository.findByName(name);
    }

    public Ingrediente findByCodigo(int codigo){ return ingredienteRepository.findByCodigo(codigo); }

    public Ingrediente update(int codigo, Ingrediente ingrediente){
        try{
            Ingrediente ingredienteExistente = ingredienteRepository.findByCodigo(codigo);
            if (ingrediente != null){
                if (ingrediente.getNome() == null || ingrediente.getNome().isBlank())
                    ingrediente.setNome(ingredienteExistente.getNome());
                if (ingrediente.getDtValidade() == null || ingrediente.getDtValidade().isBlank())
                    ingrediente.setDtValidade(ingredienteExistente.getDtValidade());
                if (ingrediente.getQuantidade() == 0){ingrediente.setDtValidade(ingredienteExistente.getDtValidade());}
                ingrediente.setCodigo(ingredienteExistente.getCodigo());
                return ingredienteRepository.updateById(codigo, ingrediente);
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
    }

    public void delete(int id){
        Ingrediente ingredienteExistente = ingredienteRepository.findByCodigo(id);
        if (ingredienteExistente == null){
            throw new RuntimeException("Ingrediente não encontrado");
        }
        ingredienteRepository.deleteIngrediente(id);
    }


}
