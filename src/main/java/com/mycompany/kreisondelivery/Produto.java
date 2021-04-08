
package com.mycompany.kreisondelivery;

public class Produto {

    private int id_produto;
    private String nome;
    private float preco;
    private int quantidade;
    private int venda;


    public Produto(){}
    public Produto(float price){
        this.preco = price;
    }

    public Produto(String nome,float preco,int quantidade) {
        this.setNome(nome);
        this.setPreco(preco);
        this.setQuantidade(quantidade);
    }

    public Produto(float preco , String nome,int quantidade, int idProduto) {
        this.setNome(nome);
        this.setPreco(preco);
        this.setQuantidade(quantidade);
        this.setId_produto(idProduto);
    }

    public Produto(String nome, float preco, int quantidade, int venda) {
        this.setNome(nome);
        this.setPreco(preco);
        this.setQuantidade(quantidade);
        this.setVenda(venda);
    }

    public Produto(int quantidade) {
        this.setQuantidade(quantidade);
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the preco
     */
    public float getPreco() {
        return preco;
    }

    /**
     * @param preco the preco to set
     */
    public void setPreco(float preco) {
        this.preco = preco;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getVenda() {
        return venda;
    }

    public void setVenda(int venda) {
        this.venda = venda;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }
}
