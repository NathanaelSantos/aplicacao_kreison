package com.mycompany.kreisondelivery;

public class Pessoa {
    private String cpf;
    private String nome;
    private int id_usuario;
    private String data_nasc;


    public Pessoa() { }
    public Pessoa(String nome) {
        this.setNome(nome);
    }

    public Pessoa(String cpf, String nome, String data_nasc) {
        this.setCpf(cpf);
        this.setNome(nome);
        this.setData_nasc(data_nasc);
    }

    public Pessoa(String cpf, String nome, int id_usuario) {
        this.setCpf(cpf);
        this.setNome(nome);
        this.setId_usuario(id_usuario);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
