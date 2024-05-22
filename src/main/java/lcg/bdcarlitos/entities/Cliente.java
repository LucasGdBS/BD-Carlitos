package lcg.bdcarlitos.entities;

public class Cliente {

    private int id_cliente;
    private String nome;
    private String telefone_1;
    private String telefone_2;
    private String complemento;
    private String rua;
    private String bairro;
    private int numero;
    private String cep;


    public Cliente() {
    }

    public Cliente(int id_cliente, String nome, String telefone_1, String telefone_2, String complemento, String rua, String bairro, int numero, String cep) {
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.telefone_1 = telefone_1;
        this.telefone_2 = telefone_2;
        this.complemento = complemento;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.cep = cep;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone_1() {
        return telefone_1;
    }

    public void setTelefone_1(String telefone_1) {
        this.telefone_1 = telefone_1;
    }

    public String getTelefone_2() {
        return telefone_2;
    }

    public void setTelefone_2(String telefone_2) {
        this.telefone_2 = telefone_2;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}