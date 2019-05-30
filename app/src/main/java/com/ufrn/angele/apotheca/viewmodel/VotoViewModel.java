package com.ufrn.angele.apotheca.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.ufrn.angele.apotheca.bd.VotoRepository;
import com.ufrn.angele.apotheca.dominio.Comentario;
import com.ufrn.angele.apotheca.dominio.Voto;

import java.util.HashMap;

public class VotoViewModel extends AndroidViewModel {
    private VotoRepository votoRepository;
    private int countVotosComentarios;
    private int countNegativacoesComentarios;
    private int countVotosPostagem;
    private int countNegativacoesPostagem;
    HashMap<Comentario, Integer> mapVotosComentarios;
    HashMap<Comentario, Integer> mapNegativacoesComentarios;
    public VotoViewModel(@NonNull Application application) {
        super(application);
        votoRepository = new VotoRepository(application);
        //listaComentarios = comentarioRepository.findById();
    }

    public void inserir (Voto voto){
        votoRepository.inserir(voto);
    }
    public void atualizar (Voto voto){
        votoRepository.atualizar(voto);
    }

    public int getCountVotosComentarios(String id_postagem, int id_comentario) {
            countVotosComentarios = votoRepository.getCountVotosComentario(id_postagem,id_comentario);
        return countVotosComentarios;
    }

    public int getCountNegativacoesComentarios(String id_postagem, int id_comentario) {

        countNegativacoesComentarios = votoRepository.getCountNegativacoesComentarios(id_postagem,id_comentario);
        return countNegativacoesComentarios;
    }

    public int getCountVotosPostagem(String id_postagem) {
        countVotosPostagem = votoRepository.getCountVotosPostagem(id_postagem);
        return countVotosPostagem;
    }

    public int getCountNegativacoesPostagem(String id_postagem) {
        countNegativacoesPostagem = votoRepository.getCountNegativacoesPostagem(id_postagem);
        return countNegativacoesPostagem;
    }

}