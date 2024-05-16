package lcg.bdcarlitos.entities;

public class Motoqueiro extends Funcionario {

    private String gerenteMotorqueiro_cpf;
    private String gerenteMotoqueiro_nome;

    public Motoqueiro() {
    }

    public Motoqueiro(String cpf, String nome, float salario, String gerenteMotorqueiro_cpf, String gerenteMotoqueiro_nome) {
        super(cpf, nome, salario);
        this.gerenteMotorqueiro_cpf = gerenteMotorqueiro_cpf;
        this.gerenteMotoqueiro_nome = gerenteMotoqueiro_nome;
    }

    public String getGerenteMotorqueiro_cpf() {
        return gerenteMotorqueiro_cpf;
    }

    public void setGerenteMotorqueiro_cpf(String gerenteMotorqueiro_cpf) {
        this.gerenteMotorqueiro_cpf = gerenteMotorqueiro_cpf;
    }

    public String getGerenteMotoqueiro_nome() {
        return gerenteMotoqueiro_nome;
    }

    public void setGerenteMotoqueiro_nome(String gerenteMotoqueiro_nome) {
        this.gerenteMotoqueiro_nome = gerenteMotoqueiro_nome;
    }
}
