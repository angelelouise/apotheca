package com.ufrn.angele.apotheca.dominio;

public class Usuario {
    private int id;
    private Long cpf_cnpj;
    private String email;
    private int id_usuario;
    private String login;
    private String url_foto;
    private String nome;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", cpf_cnpj='" + cpf_cnpj + '\'' +
                ", email='" + email + '\'' +
                ", id_usuario=" + id_usuario +
                ", login='" + login + '\'' +
                ", url_foto='" + url_foto + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(Long cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }
}
