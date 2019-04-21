package com.ufrn.angele.apotheca.dominio;

import java.util.Date;

public class Postagem {

    private String titulo;
    private String turma;
    private Date data_cadastro;
    private int avatar;

    @Override
    public String toString() {
        return "Postagem{" +
                "titulo='" + titulo + '\'' +
                ", turma='" + turma + '\'' +
                ", data_cadastro=" + data_cadastro +
                ", avatar=" + avatar +
                '}';
    }

    public Postagem(String titulo, String turma, Date data_cadastro, int avatar) {
        this.titulo = titulo;
        this.turma = turma;
        this.data_cadastro = data_cadastro;
        this.avatar = avatar;
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

    public Date getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
