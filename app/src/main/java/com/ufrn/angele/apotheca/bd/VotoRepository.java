package com.ufrn.angele.apotheca.bd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.ufrn.angele.apotheca.bd.firestore.VotoDAOFirestore;
import com.ufrn.angele.apotheca.dominio.Voto;

import java.util.List;

public class VotoRepository {
    private LiveData<List<Integer>> listaComentario;
    private LiveData<Integer> comentario;
    private VotoDAO votoDAO;
    private int countVotosComentarios;
    private int countNegativacoesComentarios;
    private int countVotosPostagem;
    private int countNegativacoesPostagem;
    private ConnectivityManager cm;
    private boolean hasNet;
    private VotoDAOFirestore votoDAOFirestore;

    public VotoRepository(Application app) {
        votoDAO = VotoDB.getInstance(app).votoDAO();
        votoDAOFirestore = new VotoDAOFirestore();
        cm =(ConnectivityManager)app
                .getSystemService(app.CONNECTIVITY_SERVICE);
    }


    public void inserir (Voto voto){
        //new VotoRepository.InsertASync(votoDAO).execute(voto);
        votoDAOFirestore.inserir(voto);
    }
    public void inserirPostagem (Voto voto){
        //new VotoRepository.InsertASync(votoDAO).execute(voto);
        votoDAOFirestore.inserirPostagem(voto);
    }

    public void atualizar (Voto voto){
        //new VotoRepository.InsertASync(votoDAO).execute(voto);
        //postagemDAOFirestore.atualizar(postagem);
    }

    public int getCountVotosComentario(String id_postagem, String id_comentario) {
        //countVotosComentarios = votoDAO.countVotosComentario(id_postagem,id_comentario);
        if (netOn()){
            countVotosComentarios = votoDAOFirestore.countVotosComentario(id_postagem,id_comentario);

        }else{
            countVotosComentarios = votoDAO.countVotosComentario(id_postagem,id_comentario);
        }
        return countVotosComentarios;
    }
    public int getCountNegativacoesComentarios(String id_postagem, String id_comentario) {
        if (netOn()){
            countNegativacoesComentarios = votoDAOFirestore.countNegativacoesComentario(id_postagem,id_comentario);

        }else{
            countNegativacoesComentarios = votoDAO.countNegativacoesComentario(id_postagem,id_comentario);
        }

        return countNegativacoesComentarios;
    }
    public int getCountVotosPostagem(String id_postagem) {

        if (netOn()){
            countVotosPostagem = votoDAOFirestore.countVotosPostagem(id_postagem);

        }else{
            countVotosPostagem = votoDAO.countVotosPostagem(id_postagem);
        }
        return countVotosPostagem;
    }
    public int getCountNegativacoesPostagem(String id_postagem) {

        if (netOn()){
            countNegativacoesPostagem = votoDAOFirestore.countNegativacoesPostagem(id_postagem);

        }else{
            countNegativacoesPostagem = votoDAO.countNegativacoesPostagem(id_postagem);
        }
        return countNegativacoesPostagem;
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