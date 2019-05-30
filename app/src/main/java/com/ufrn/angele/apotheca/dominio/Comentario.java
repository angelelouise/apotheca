package com.ufrn.angele.apotheca.dominio;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "comentario")

public class Comentario {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String id_postagem;
    private int id_resposta;
    private int id_autor;
    private String data_cadastro;
    private boolean resposta;
    private boolean escolhido;
    private String titulo;

    public Comentario(int id, String id_postagem, int id_resposta, int id_autor, String data_cadastro, boolean resposta, boolean escolhido, String titulo) {
        this.id = id;
        this.id_postagem = id_postagem;
        this.id_resposta = id_resposta;
        this.id_autor = id_autor;
        this.data_cadastro = data_cadastro;
        this.resposta = resposta;
        this.escolhido = escolhido;
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", id_postagem=" + id_postagem +
                ", id_resposta=" + id_resposta +
                ", id_autor=" + id_autor +
                ", data_cadastro='" + data_cadastro + '\'' +
                ", resposta=" + resposta +
                ", escolhido=" + escolhido +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_postagem() {
        return id_postagem;
    }

    public void setId_postagem(String id_postagem) {
        this.id_postagem = id_postagem;
    }

    public int getId_resposta() {
        return id_resposta;
    }

    public void setId_resposta(int id_resposta) {
        this.id_resposta = id_resposta;
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

    public boolean isResposta() {
        return resposta;
    }

    public void setResposta(boolean resposta) {
        this.resposta = resposta;
    }

    public boolean isEscolhido() {
        return escolhido;
    }

    public void setEscolhido(boolean escolhido) {
        this.escolhido = escolhido;
    }
}
