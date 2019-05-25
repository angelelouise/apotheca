package com.ufrn.angele.apotheca.dominio;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "postagem")
public class Postagem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id_postagem;
    private int id_autor;
    private int tipo_postagem;
    private String titulo;
    private int id_turma;
    private String turma;
    private String data_cadastro;
    private boolean ativo;
    private String descricao;

    @Override
    public String toString() {
        return "Postagem{" +
                "id_postagem=" + id_postagem +
                ", id_autor=" + id_autor +
                ", tipo_postagem=" + tipo_postagem +
                ", titulo='" + titulo + '\'' +
                ", id_turma=" + id_turma +
                ", turma='" + turma + '\'' +
                ", data_cadastro=" + data_cadastro +
                ", avatar=" +
                ", ativo=" + ativo +
                '}';
    }

    public Postagem(String titulo, String turma, String data_cadastro) {
        this.titulo = titulo;
        this.turma = turma;
        this.data_cadastro = data_cadastro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_postagem() {
        return id_postagem;
    }

    public void setId_postagem(int id_postagem) {
        this.id_postagem = id_postagem;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public int getTipo_postagem() {
        return tipo_postagem;
    }

    public void setTipo_postagem(int tipo_postagem) {
        this.tipo_postagem = tipo_postagem;
    }

    public int getId_turma() {
        return id_turma;
    }

    public void setId_turma(int id_turma) {
        this.id_turma = id_turma;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(String data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

}
