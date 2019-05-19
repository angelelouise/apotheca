package com.ufrn.angele.apotheca.dominio;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "discente")
public class Discente implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String cpf;
    @NonNull
    private int id_usuario;
    @NonNull
    private int id_discente;
    private int matricula;
    @NonNull
    private int id_curso;
    @NonNull
    private String nome_curso;
    private int id_tipo_discente;
    private String tipo_vinculo;

    @Override
    public String toString() {
        return "Discente{" +
                "id=" + id +
                ", cpf=" + cpf +
                ", id_usuario=" + id_usuario +
                ", id_discente=" + id_discente +
                ", matricula=" + matricula +
                ", id_curso=" + id_curso +
                ", nome_curso='" + nome_curso + '\'' +
                ", id_tipo_discente=" + id_tipo_discente +
                ", tipo_vinculo='" + tipo_vinculo + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_discente() {
        return id_discente;
    }

    public void setId_discente(int id_discente) {
        this.id_discente = id_discente;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public String getNome_curso() {
        return nome_curso;
    }

    public void setNome_curso(String nome_curso) {
        this.nome_curso = nome_curso;
    }

    public int getId_tipo_discente() {
        return id_tipo_discente;
    }

    public void setId_tipo_discente(int id_tipo_discente) {
        this.id_tipo_discente = id_tipo_discente;
    }

    public String getTipo_vinculo() {
        return tipo_vinculo;
    }

    public void setTipo_vinculo(String tipo_vinculo) {
        this.tipo_vinculo = tipo_vinculo;
    }
}
