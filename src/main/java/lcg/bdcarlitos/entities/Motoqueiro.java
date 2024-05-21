package lcg.bdcarlitos.entities;

public class Motoqueiro extends Funcionario {

    private String gerenteMotoqueiro_cpf;
    private String gerenteMotoqueiro_nome;

    public Motoqueiro() {
    }

    public Motoqueiro(String gerenteMotorqueiro_cpf, String cpf) {
        super(cpf, null, 0.0f);
        this.gerenteMotoqueiro_cpf = gerenteMotorqueiro_cpf;
    }

    public Motoqueiro(String cpf, String nome, float salario, String gerenteMotorqueiro_cpf, String gerenteMotoqueiro_nome) {
        super(cpf, nome, salario);
        this.gerenteMotoqueiro_cpf = gerenteMotorqueiro_cpf;
        this.gerenteMotoqueiro_nome = gerenteMotoqueiro_nome;
    }

    public String getGerenteMotoqueiro_cpf() {
        return gerenteMotoqueiro_cpf;
    }

    public void setGerenteMotoqueiro_cpf(String gerenteMotoqueiro_cpf) {
        this.gerenteMotoqueiro_cpf = gerenteMotoqueiro_cpf;
    }

    public String getGerenteMotoqueiro_nome() {
        return gerenteMotoqueiro_nome;
    }

    public void setGerenteMotoqueiro_nome(String gerenteMotoqueiro_nome) {
        this.gerenteMotoqueiro_nome = gerenteMotoqueiro_nome;
    }
}
