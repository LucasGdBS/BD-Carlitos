package lcg.bdcarlitos.entities;

import lcg.bdcarlitos.repositories.GerenteRepository;

public class Atendente extends Funcionario{

    private String turno;
    private String cpf_gerente;
    private String nome_gerente;

    public Atendente() {
    }

    public Atendente(String cpf, String nome, float salario, String turno, String cpf_gerente, String nome_gerente) {
        super(cpf, nome, salario);
        this.turno = turno;
        this.cpf_gerente = cpf_gerente;
        this.nome_gerente = nome_gerente;
    }


    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCpf_gerente() {
        return cpf_gerente;
    }

    public void setCpf_gerente(String cpf_gerente) {
        this.cpf_gerente = cpf_gerente;
    }

    public String getNome_gerente() {
        return nome_gerente;
    }

    public void setNome_gerente(String nome_gerente) {
        this.nome_gerente = nome_gerente;
    }
}
