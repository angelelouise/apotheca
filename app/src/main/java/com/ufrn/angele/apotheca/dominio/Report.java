package com.ufrn.angele.apotheca.dominio;


public class Report {
    private int id;
    private int id_postagem;
    private int id_autor;
    private String data_cadastro;
    private String motivo;

    public Report(int id, int id_postagem, int id_autor, String data_cadastro, String motivo) {
        this.id = id;
        this.id_postagem = id_postagem;
        this.id_autor = id_autor;
        this.data_cadastro = data_cadastro;
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", id_postagem=" + id_postagem +
                ", id_autor=" + id_autor +
                ", data_cadastro='" + data_cadastro + '\'' +
                ", motivo='" + motivo + '\'' +
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
