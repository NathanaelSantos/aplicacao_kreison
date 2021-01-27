package model;

import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.time.LocalDate;

public class Usuario extends Cliente{
    public int Senha;
    public int tipoUsuario;

    public Usuario(String nome, int senha, String cpf, String dataNascimento, int tipoUsuario) {
        super(cpf,nome,dataNascimento);
        this.setSenha(senha);
        this.setTipoUsuario(tipoUsuario);
    }

    public Integer getSenha() {
        return Senha;
    }

    public void setSenha(Integer senha) {
        Senha = senha;
    }


    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
