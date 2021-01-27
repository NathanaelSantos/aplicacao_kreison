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

    public String nome;
    private float preco;
    public int quantidade;
    private int venda;
    public String especificacaoProduto;

    public Produto(String nome,float preco , int quantidade) {
        this.nome = nome;
        this.setPreco(preco);
        this.quantidade = quantidade;
    }

    public Produto(String nome, float preco, int quantidade, int venda) {
        this.nome = nome;
        this.setPreco(preco);
        this.quantidade = quantidade;
        this.setVenda(venda);
    }

    public Produto(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto(String nome, float preco, int quantidade, String especificacaoProduto) {
        this.nome = nome;
        this.setPreco(preco);
        this.quantidade = quantidade;
        this.especificacaoProduto = especificacaoProduto;
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

    public String getTipoProduto() {
        return especificacaoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.especificacaoProduto = tipoProduto;
    }

    public int getVenda() {
        return venda;
    }

    public void setVenda(int venda) {
        this.venda = venda;
    }
}
