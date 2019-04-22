package com.ufrn.angele.apotheca.dominio;

import java.util.Date;

public class Notificacao {

    private int id;
    private int id_autor;
    private int id_postagem;
    private int id_notificado;
    private Date data_cadastro;
    private int tipo_notificacao;
    private boolean visualizada;
    private int avatar;



    @Override
    public String toString() {
        return "Notificacao{" +
                "id=" + id +
                ", id_autor=" + id_autor +
                ", id_postagem=" + id_postagem +
                ", id_notificado=" + id_notificado +
                ", data_cadastro=" + data_cadastro +
                ", tipo_notificacao=" + tipo_notificacao +
                ", visualizada=" + visualizada +
                '}';
    }

    public Notificacao(int id, int id_autor, int id_postagem, int id_notificado, Date data_cadastro, int tipo_notificacao, boolean visualizada) {
        this.id = id;
        this.id_autor = id_autor;
        this.id_postagem = id_postagem;
        this.id_notificado = id_notificado;
        this.data_cadastro = data_cadastro;
        this.tipo_notificacao = tipo_notificacao;
        this.visualizada = visualizada;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public int getId_postagem() {
        return id_postagem;
    }

    public void setId_postagem(int id_postagem) {
        this.id_postagem = id_postagem;
    }

    public int getId_notificado() {
        return id_notificado;
    }

    public void setId_notificado(int id_notificado) {
        this.id_notificado = id_notificado;
    }

    public Date getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public int getTipo_notificacao() {
        return tipo_notificacao;
    }

    public void setTipo_notificacao(int tipo_notificacao) {
        this.tipo_notificacao = tipo_notificacao;
    }

    public boolean isVisualizada() {
        return visualizada;
    }

    public void setVisualizada(boolean visualizada) {
        this.visualizada = visualizada;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
