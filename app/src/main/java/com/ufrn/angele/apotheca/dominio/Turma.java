package com.ufrn.angele.apotheca.dominio;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "turma")
public class Turma implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private int id_discente;
    @NonNull
    private int ano;
    @NonNull
    private int id_turma;
    @NonNull
    private int id_componente;
    @NonNull
    private String codigo_componente;
    @NonNull
    private String nome_componente;
    private String chave_usuario;
    @Override
    public String toString() {
//        return "Turma{" +
//                "id=" + id +
//                ", id_discente=" + id_discente +
//                ", ano=" + ano +
//                ", id_turma=" + id_turma +
//                ", id_componente=" + id_componente +
//                ", codigo_componente='" + codigo_componente + '\'' +
//                ", nome_componente='" + nome_componente + '\'' +
//                '}';
        return nome_componente;
    }

    public String getChave_usuario() {
        return chave_usuario;
    }

    public void setChave_usuario(String chave_usuario) {
        this.chave_usuario = chave_usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_discente() {
        return id_discente;
    }

    public void setId_discente(int id_discente) {
        this.id_discente = id_discente;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getId_turma() {
        return id_turma;
    }

    public void setId_turma(int id_turma) {
        this.id_turma = id_turma;
    }

    public int getId_componente() {
        return id_componente;
    }

    public void setId_componente(int id_componente) {
        this.id_componente = id_componente;
    }

    public String getCodigo_componente() {
        return codigo_componente;
    }

    public void setCodigo_componente(String codigo_componente) {
        this.codigo_componente = codigo_componente;
    }

    public String getNome_componente() {
        return nome_componente;
    }

    public void setNome_componente(String nome_componente) {
        this.nome_componente = nome_componente;
    }
}
