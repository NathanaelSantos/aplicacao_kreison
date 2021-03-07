package model;

import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.time.LocalDate;

public class Usuario extends Cliente{
    public String Senha;
    public int tipoUsuario;

    public Usuario(String nome, String senha, String cpf, String dataNascimento, int tipoUsuario) {
        super(cpf,nome,dataNascimento);
        this.setSenha(senha);
        this.setTipoUsuario(tipoUsuario);
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }


    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
