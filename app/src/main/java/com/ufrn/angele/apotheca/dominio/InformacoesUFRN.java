package com.ufrn.angele.apotheca.dominio;

import java.util.ArrayList;

public class InformacoesUFRN {
    private Usuario usuario;
    private ArrayList<Discente> discentes;
    private ArrayList<Turma> turmas;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Discente> getDiscentes() {
        return discentes;
    }

    public void setDiscentes(ArrayList<Discente> discentes) {
        this.discentes = discentes;
    }

    public ArrayList<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(ArrayList<Turma> turmas) {
        this.turmas = turmas;
    }
}
