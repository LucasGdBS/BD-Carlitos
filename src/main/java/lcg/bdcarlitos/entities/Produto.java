package lcg.bdcarlitos.entities;

public class Produto {
    private int id_produto;
    private String nome;
    private double preco;
    private int[] ingredientes;

    private int[] quantidadeIngredientes;

    public int[] getQuantidadeIngredientes() {
        return quantidadeIngredientes;
    }

    public void setQuantidadeIngredientes(int[] quantidadeIngredientes) {
        this.quantidadeIngredientes = quantidadeIngredientes;
    }

    public int[] getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(int[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Produto() {
    }
    public Produto(int id_produto, String nome, double preco) {
        this.id_produto = id_produto;
        this.nome = nome;
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
