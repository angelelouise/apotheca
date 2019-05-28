package com.ufrn.angele.apotheca.dominio;

public class Tag {
    private int id;
    private int id_postagem;
    private String data_cadastro;
    private String descricao;

    public Tag(int id, int id_postagem, String data_cadastro, String descricao) {
        this.id = id;
        this.id_postagem = id_postagem;
        this.data_cadastro = data_cadastro;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", id_postagem=" + id_postagem +
                ", data_cadastro='" + data_cadastro + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_postagem() {
        return id_postagem;
    }

    public void setId_postagem(int id_postagem) {
        this.id_postagem = id_postagem;
    }

    public String getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(String data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
