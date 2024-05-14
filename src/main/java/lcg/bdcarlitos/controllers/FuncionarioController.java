package lcg.bdcarlitos.controllers;


import lcg.bdcarlitos.entities.Funcionario;
import lcg.bdcarlitos.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/")
    public List<Funcionario> getAllFuncionarios(){
        return funcionarioService.getAll();
    }
}
