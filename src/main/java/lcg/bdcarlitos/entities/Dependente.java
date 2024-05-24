package lcg.bdcarlitos.entities;

public class Dependente {
    private int id_dependente;
    private String nome;
    private String data_nacimento;
    private String relacao;
    private String cpf_funcionario;


    public Dependente() {
    }

    public Dependente(int id_dependente, String nome, String data_nacimento, String relacao, String cpf_funcionario) {
        this.id_dependente = id_dependente;
        this.nome = nome;
        this.data_nacimento = data_nacimento;
        this.relacao = relacao;
        this.cpf_funcionario = cpf_funcionario;
    }

    public int getId_dependente() {
        return id_dependente;
    }

    public void setId_dependente(int id_dependente) {
        this.id_dependente = id_dependente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData_nacimento() {
        return data_nacimento;
    }

    public void setData_nacimento(String data_nacimento) {
        this.data_nacimento = data_nacimento;
    }

    public String getRelacao() {
        return relacao;
    }

    public void setRelacao(String relacao) {
        this.relacao = relacao;
    }

    public String getCpf_funcionario() {
        return cpf_funcionario;
    }

    public void setCpf_funcionario(String cpf_funcionario) {
        this.cpf_funcionario = cpf_funcionario;
    }
}
