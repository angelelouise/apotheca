package com.ufrn.angele.apotheca.dominio;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "comentario")

public class Comentario {

    @PrimaryKey(autoGenerate = true)
    private int id_comentario;
    private String id;
    private String id_postagem;
    private int id_autor;
    private String data_cadastro;
    private boolean escolhido;
    private String titulo;

    public Comentario(String id, String id_postagem, int id_autor, String data_cadastro, boolean escolhido, String titulo) {
        this.id = id;
        this.id_postagem = id_postagem;
        this.id_autor = id_autor;
        this.data_cadastro = data_cadastro;
        this.escolhido = escolhido;
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", id_postagem=" + id_postagem +
                ", id_resposta=" +
                ", id_autor=" + id_autor +
                ", data_cadastro='" + data_cadastro + '\'' +
                ", resposta="  +
                ", escolhido=" + escolhido +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    public int getId_comentario() {
        return id_comentario;
    }

    public void setId_comentario(int id_comentario) {
        this.id_comentario = id_comentario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_postagem() {
        return id_postagem;
    }

    public void setId_postagem(String id_postagem) {
        this.id_postagem = id_postagem;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public String getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(String data_cadastro) {
        this.data_cadastro = data_cadastro;
    }


    public boolean isEscolhido() {
        return escolhido;
    }

    public void setEscolhido(boolean escolhido) {
        this.escolhido = escolhido;
    }
}
