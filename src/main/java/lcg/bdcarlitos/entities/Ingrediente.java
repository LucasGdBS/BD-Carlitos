package lcg.bdcarlitos.entities;

import java.time.LocalDate;

public class Ingrediente {
    private String nome;
    private String dtValidade;
    private int quantidade;
    private int codigo;
    private String tipoAlimento;

    public Ingrediente() {
    }
    public Ingrediente(String nome, String dtValidade, int quantidade, int codigo, String tipoAlimento) {
        this.nome = nome;
        this.dtValidade = dtValidade;
        this.quantidade = quantidade;
        this.codigo = codigo;
        this.tipoAlimento = tipoAlimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDtValidade() {
        return dtValidade;
    }

    public void setDtValidade(String dtValidade) {
        this.dtValidade = dtValidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipoAlimento() {
        return tipoAlimento;
    }

    public void setTipoAlimento(String tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
    }
}
