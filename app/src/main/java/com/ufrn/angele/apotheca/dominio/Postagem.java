package com.ufrn.angele.apotheca.dominio;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "postagem")
public class Postagem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String id_postagem;
    private int id_autor;
//    private int tipo_postagem;
    private String titulo;
    private int id_componente;
    private String componente;
    private String data_cadastro;
    private boolean ativo;
    private String descricao;
    private String url_autor;



    @Override
    public String toString() {
        return "Postagem{" +
                "id_postagem=" + id_postagem +
                ", id_autor=" + id_autor +
                ", tipo_postagem=" +
                ", titulo='" + titulo + '\'' +
                ", id_componente=" + id_componente +
                ", componente='" + componente + '\'' +
                ", data_cadastro=" + data_cadastro +
                ", avatar=" +
                ", ativo=" + ativo +
                '}';
    }

    public Postagem(String titulo, String componente, String data_cadastro) {
        this.titulo = titulo;
        this.componente = componente;
        this.data_cadastro = data_cadastro;
    }

    public String getUrl_autor() {
        return url_autor;
    }

    public void setUrl_autor(String url_autor) {
        this.url_autor = url_autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

//    public int getTipo_postagem() {
//        return tipo_postagem;
//    }
//
//    public void setTipo_postagem(int tipo_postagem) {
//        this.tipo_postagem = tipo_postagem;
//    }

    public int getId_componente() {
        return id_componente;
    }

    public void setId_componente(int id_componente) {
        this.id_componente = id_componente;
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

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(String data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
