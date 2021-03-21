package model;

import com.mycompany.kreisondelivery.Pessoa;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class Cliente extends Pessoa {
    private String endereco;
    private String bairro;
    private String cep;
    private Integer numero;

    public Cliente(String cpf, String nome, String dataNascimento) {
        super(cpf,nome,dataNascimento);
    }

    public Cliente() {
    }


    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
