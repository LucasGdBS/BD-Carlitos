package lcg.bdcarlitos.controllers;


import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/")
    public List<Funcionario> listarFuncionarios(){
        return funcionarioRepository.listarFuncionarios();
    }
}
