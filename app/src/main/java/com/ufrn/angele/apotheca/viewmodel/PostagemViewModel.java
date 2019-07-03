package com.ufrn.angele.apotheca.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ufrn.angele.apotheca.bd.PostagemRepository;
import com.ufrn.angele.apotheca.dominio.Postagem;

import java.util.List;

public class PostagemViewModel extends AndroidViewModel {
    private PostagemRepository postagemRepository;
    private LiveData<List<Postagem>> listaPostagem;
    private LiveData<Postagem> postagem;

    public PostagemViewModel(@NonNull Application application) {
        super(application);
        postagemRepository = new PostagemRepository(application);
        //listaPostagem = postagemRepository.buscarTodas();
    }
    public void inserir (Postagem postagem){
        postagemRepository.inserir(postagem);
    }
    public void atualizar (Postagem postagem){
        postagemRepository.atualizar(postagem);
    }
    public void excluir (Postagem postagem){
        postagemRepository.excluir(postagem);
    }
    public void findById(Long id){
        postagem = postagemRepository.findById(id);
    }
//    public void findByNome (String nome){
//        listaPostagem = postagemRepository.findByAutor(nome);
//    }
    public void findByTurma (Long id){
        listaPostagem = postagemRepository.findByTurma(id);
    }
    public void findByUsuario (Long id){
        listaPostagem = postagemRepository.findByUsuario(id);
    }
    public LiveData<List<Postagem>> getListaPostagem(List<Integer> ids) {
        listaPostagem = postagemRepository.buscarTodas(ids);
        return listaPostagem;
    }

    public LiveData<Postagem> getPostagem() {
        return postagem;
    }
}
