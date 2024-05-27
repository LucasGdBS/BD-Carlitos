package lcg.bdcarlitos.entities;

public class Pedido {
    private int codigoNotalFiscal;
    private int numeroPedido; // PK
    private int idCliente; // PK FK
    private String nomeCLiente;
    private int produtoId; // PK FK
    private String nomeProduto;
    private String atendenteCpf; // FK
    private float valorTotal; // Valor do produto * qntProduto
    private float valorSemDesconto;
    private float valorDesconto;
    private String dtPedido;
    private String formaPagamento;
    private float taxaEntrega;
    private float desconto;
    private int qntProduto;

    public float getValorSemDesconto() {
        return valorSemDesconto;
    }

    public void setValorSemDesconto(float valorSemDesconto) {
        this.valorSemDesconto = valorSemDesconto;
    }

    public float getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(float valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Pedido() {
    }

    public int getCodigoNotalFiscal() {
        return codigoNotalFiscal;
    }

    public void setCodigoNotalFiscal(int codigoNotalFiscal) {
        this.codigoNotalFiscal = codigoNotalFiscal;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCLiente() {
        return nomeCLiente;
    }

    public void setNomeCLiente(String nomeCLiente) {
        this.nomeCLiente = nomeCLiente;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getAtendenteCpf() {
        return atendenteCpf;
    }

    public void setAtendenteCpf(String atendenteCpf) {
        this.atendenteCpf = atendenteCpf;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDtPedido() {
        return dtPedido;
    }

    public void setDtPedido(String dtPedido) {
        this.dtPedido = dtPedido;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public float getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(float taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    public int getQntProduto() {
        return qntProduto;
    }

    public void setQntProduto(int qntProduto) {
        this.qntProduto = qntProduto;
    }
}
