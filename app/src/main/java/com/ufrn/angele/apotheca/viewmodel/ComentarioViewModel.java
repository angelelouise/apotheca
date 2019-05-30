package com.ufrn.angele.apotheca.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ufrn.angele.apotheca.bd.ComentarioRepository;
import com.ufrn.angele.apotheca.dominio.Comentario;

import java.util.List;

public class ComentarioViewModel extends AndroidViewModel{
    private ComentarioRepository comentarioRepository;
    private LiveData<List<Comentario>> listaComentarios;
    private LiveData<Comentario> comentario;

    public ComentarioViewModel(@NonNull Application application) {
        super(application);
        comentarioRepository = new ComentarioRepository(application);
        //listaComentarios = comentarioRepository.findById();
    }

    public void inserir (Comentario comentario){
        comentarioRepository.inserir(comentario);
    }
    public void atualizar (Comentario comentario){
        comentarioRepository.atualizar(comentario);
    }
    public void findById(String id){
        listaComentarios = comentarioRepository.findById(id);
    }
    public void findByUsuario (Long id){
        listaComentarios = comentarioRepository.findByUsuario(id);
    }

    public LiveData<List<Comentario>> getListaComentarios(String id_postagem) {
        listaComentarios = comentarioRepository.findById(id_postagem);
        return listaComentarios;
    }

    public LiveData<Comentario> getPostagem() {
        return comentario;
    }
}
