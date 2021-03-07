/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;


/**
 *
 * @author nathan
 */

public class Produto {

    private int id_produto;
    public String nome;
    private float preco;
    public int quantidade;
    private int venda;


    public Produto(){}

    public Produto(String nome,float preco,int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Produto(float preco , String nome,int quantidade, int idProduto) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.id_produto = idProduto;
    }

    public Produto(String nome, float preco, int quantidade, int venda) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.setVenda(venda);
    }

    public Produto(int quantidade) {
        this.quantidade = quantidade;
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
