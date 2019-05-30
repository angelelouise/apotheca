package com.ufrn.angele.apotheca.bd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.ufrn.angele.apotheca.dominio.Comentario;

import java.util.List;

public class ComentarioRepository {
    private LiveData<List<Comentario>> listaComentario;
    private LiveData<Comentario> comentario;
    private ComentarioDAO comentarioDAO;

    private ConnectivityManager cm;
    private boolean hasNet;

    public ComentarioRepository(Application app) {
        comentarioDAO = ComentarioDB.getInstance(app).comentarioDAO();
        //postagemDAOFirestore = new PostagemDAOFirestore();
        cm =(ConnectivityManager)app
                .getSystemService(app.CONNECTIVITY_SERVICE);
    }

    public LiveData<List<Comentario>> findById(String id) {
        if (listaComentario==null){
            listaComentario = comentarioDAO.findById(id);
        }
        return listaComentario;
    }
    public LiveData<List<Comentario>> findByUsuario(Long id) {
        if (listaComentario==null){
            listaComentario = comentarioDAO.findByUsuario(id);
        }
        return listaComentario;
    }

    public void inserir (Comentario comentario){
        new ComentarioRepository.InsertASync(comentarioDAO).execute(comentario);
        //postagemDAOFirestore.inserir(postagem);
    }

    public void atualizar (Comentario comentario){
        new ComentarioRepository.InsertASync(comentarioDAO).execute(comentario);
        //postagemDAOFirestore.atualizar(postagem);
    }

    private class InsertASync extends AsyncTask<Comentario, Void, Void> {
        private ComentarioDAO comentarioDAO;

        public InsertASync (ComentarioDAO comentarioDAO){
            this.comentarioDAO= comentarioDAO;
        }
        @Override
        protected Void doInBackground(Comentario... comentarios){
            try{
                comentarioDAO.inserir(comentarios[0]);
            }catch (Exception e){
                Log.e(ComentarioRepository.class.getName(), "ERRO", e);
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