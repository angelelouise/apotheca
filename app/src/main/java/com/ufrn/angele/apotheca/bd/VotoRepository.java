package com.ufrn.angele.apotheca.bd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.ufrn.angele.apotheca.dominio.Voto;

import java.util.List;

public class VotoRepository {
    private LiveData<List<Integer>> listaComentario;
    private LiveData<Integer> comentario;
    private VotoDAO votoDAO;

    private ConnectivityManager cm;
    private boolean hasNet;

    public VotoRepository(Application app) {
        votoDAO = VotoDB.getInstance(app).votoDAO();
        //postagemDAOFirestore = new PostagemDAOFirestore();
        cm =(ConnectivityManager)app
                .getSystemService(app.CONNECTIVITY_SERVICE);
    }


    public void inserir (Voto voto){
        new VotoRepository.InsertASync(votoDAO).execute(voto);
        //postagemDAOFirestore.inserir(postagem);
    }

    public void atualizar (Voto voto){
        new VotoRepository.InsertASync(votoDAO).execute(voto);
        //postagemDAOFirestore.atualizar(postagem);
    }

    private class InsertASync extends AsyncTask<Voto, Void, Void> {
        private VotoDAO votoDAO;

        public InsertASync (VotoDAO votoDAO){
            this.votoDAO= votoDAO;
        }
        @Override
        protected Void doInBackground(Voto... comentarios){
            try{
                votoDAO.inserir(comentarios[0]);
            }catch (Exception e){
                Log.e(ComentarioRepository.class.getName(), "ERRO", e);
            }

            return null;
        }
    }
    private boolean netOn(){
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}