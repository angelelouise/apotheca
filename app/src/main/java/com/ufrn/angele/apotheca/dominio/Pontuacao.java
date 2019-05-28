package com.ufrn.angele.apotheca.dominio;

public class Pontuacao {
    private int id;
    private int id_usuario;
    private int pontos;
    private int tipo_movimentacao;
    private String data_cadastro;

    public Pontuacao(int id, int id_usuario, int pontos, int tipo_movimentacao, String data_cadastro) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.pontos = pontos;
        this.tipo_movimentacao = tipo_movimentacao;
        this.data_cadastro = data_cadastro;
    }

    @Override
    public String toString() {
        return "Pontuacao{" +
                "id=" + id +
                ", id_usuario=" + id_usuario +
                ", pontos=" + pontos +
                ", tipo_movimentacao=" + tipo_movimentacao +
                ", data_cadastro='" + data_cadastro + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getTipo_movimentacao() {
        return tipo_movimentacao;
    }

    public void setTipo_movimentacao(int tipo_movimentacao) {
        this.tipo_movimentacao = tipo_movimentacao;
    }

    public String getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(String data_cadastro) {
        this.data_cadastro = data_cadastro;
    }
}
