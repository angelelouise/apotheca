package com.ufrn.angele.apotheca.dominio;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "voto")
public class Voto {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String id_postagem;
    private String id_comentario;
    private boolean voto;
    private boolean negativacao;
    private int id_autor;
    private String data_cadastro;

    public Voto(int id, String id_postagem, String id_comentario, boolean voto, boolean negativacao, int id_autor, String data_cadastro) {
        this.id = id;
        this.id_postagem = id_postagem;
        this.id_comentario = id_comentario;
        this.voto = voto;
        this.negativacao = negativacao;
        this.id_autor = id_autor;
        this.data_cadastro = data_cadastro;
    }

    @Override
    public String toString() {
        return "Voto{" +
                "id=" + id +
                ", id_postagem=" + id_postagem +
                ", id_comentario=" + id_comentario +
                ", voto=" + voto +
                ", negativacao=" + negativacao +
                ", id_autor=" + id_autor +
                ", data_cadastro='" + data_cadastro + '\'' +
                '}';
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

    public String getId_comentario() {
        return id_comentario;
    }

    public void setId_comentario(String id_comentario) {
        this.id_comentario = id_comentario;
    }

    public boolean isVoto() {
        return voto;
    }

    public void setVoto(boolean voto) {
        this.voto = voto;
    }

    public boolean isNegativacao() {
        return negativacao;
    }

    public void setNegativacao(boolean negativacao) {
        this.negativacao = negativacao;
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
}
