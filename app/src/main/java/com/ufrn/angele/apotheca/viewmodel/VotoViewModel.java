package com.ufrn.angele.apotheca.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.ufrn.angele.apotheca.bd.VotoRepository;
import com.ufrn.angele.apotheca.dominio.Comentario;
import com.ufrn.angele.apotheca.dominio.Voto;

import java.util.HashMap;
import java.util.List;

public class VotoViewModel extends AndroidViewModel {
    private VotoRepository votoRepository;
    private LiveData<Integer> countVotosComentarios;
    private LiveData<Integer> countNegativacoesComentarios;
    private LiveData<Integer> countVotosPostagem;
    private LiveData<Integer> countNegativacoesPostagem;
    HashMap<Comentario, LiveData<Integer>> mapVotosComentarios;
    HashMap<Comentario, LiveData<Integer>> mapNegativacoesComentarios;
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

    public LiveData<Integer> getCountVotosComentarios(int id_postagem, int id_comentario) {
            countVotosComentarios = votoRepository.getCountVotosComentario(id_postagem,id_comentario);
        return countVotosComentarios;
    }

    public LiveData<Integer> getCountNegativacoesComentarios(int id_postagem, int id_comentario) {

        countNegativacoesPostagem = votoRepository.getCountNegativacoesComentarios(id_postagem,id_comentario);
        return countNegativacoesPostagem;
    }

    public LiveData<Integer> getCountVotosPostagem(int id_postagem) {
        countVotosPostagem = votoRepository.getCountVotosPostagem(id_postagem);
        return countVotosPostagem;
    }

    public LiveData<Integer> getCountNegativacoesPostagem(int id_postagem) {
        countNegativacoesPostagem = votoRepository.getCountNegativacoesPostagem(id_postagem);
        return countNegativacoesPostagem;
    }

}