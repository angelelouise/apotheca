package com.ufrn.angele.apotheca.bd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.ufrn.angele.apotheca.bd.firestore.PostagemDAOFirestore;
import com.ufrn.angele.apotheca.dominio.Postagem;

import java.util.List;

public class PostagemRepository {
    private LiveData<List<Postagem>> listaPostagem;
    private LiveData<Postagem> postagem;
    private PostagemDAO postagemDAO;
    private PostagemDAOFirestore postagemDAOFirestore;

    private ConnectivityManager cm;
    private boolean hasNet;

    public PostagemRepository(Application app) {
        postagemDAO = PostagemDB.getInstance(app).postagemDAO();
        postagemDAOFirestore = new PostagemDAOFirestore();
        cm =(ConnectivityManager)app
                .getSystemService(app.CONNECTIVITY_SERVICE);
    }

    public LiveData<List<Postagem>> buscarTodas(int ids) {
        if (netOn()){
            listaPostagem = postagemDAOFirestore.buscarTodas(ids);

        }else{
            listaPostagem = postagemDAO.buscarTodas(ids);
        }
        //listaPostagem = postagemDAO.buscarTodas();
        return listaPostagem;
    }

    public LiveData<Postagem> findById(Long id) {
        if (postagem==null){
            postagem = postagemDAO.findById(id);
        }
        return postagem;
    }
    public LiveData<List<Postagem>> findByUsuario(Long id) {
        if (listaPostagem==null){
            listaPostagem = postagemDAO.findByUsuario(id);
        }
        return listaPostagem;
    }
    public LiveData<List<Postagem>> findByTurma(Long id) {
        if (listaPostagem==null){
            listaPostagem = postagemDAO.findByTurma(id);
        }
        return listaPostagem;
    }
//    public LiveData<List<Postagem>> findByAutor(String nome) {
//        if (listaPostagem==null){
//            listaPostagem = postagemDAO.findByAutor(nome);
//        }
//        return listaPostagem;
//    }

    public void inserir (Postagem postagem){
        new InsertASync(postagemDAO).execute(postagem);
        postagemDAOFirestore.inserir(postagem);
    }

    public void atualizar (Postagem postagem){
        new InsertASync(postagemDAO).execute(postagem);
        postagemDAOFirestore.atualizar(postagem);
    }

    private class InsertASync extends AsyncTask<Postagem, Void, Void> {
        private PostagemDAO postagemDAO;

        public InsertASync (PostagemDAO postagemDAO){
            this.postagemDAO= postagemDAO;
        }
        @Override
        protected Void doInBackground(Postagem... postagens){
            try{
                postagemDAO.inserir(postagens[0]);
            }catch (Exception e){
                Log.e(PostagemRepository.class.getName(), "ERRO", e);
            }

            return null;
            //Postagem p = postagens[0];
            //if(p.getId_postagem() == 0)
            //postagemDAO.inserir(p);
            //else
            //postagemDAO.atualizar(p);
            //return null;
        }
    }
    private boolean netOn(){
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}

