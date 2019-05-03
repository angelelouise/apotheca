package com.ufrn.angele.apotheca.bd;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.ufrn.angele.apotheca.dominio.Discente;

import java.util.List;

public class DiscenteRepository {
    private List<Discente> discentes;
    private DiscenteDAO discenteDAO;

    private ConnectivityManager cm;

    public void inserir (Discente discente){
        new InsertASync(discenteDAO).execute(discente);
    }

    public List<Discente> findByCPF(String cpf){

        //if(netOn) > consulta usuário no serviço
        discentes = discenteDAO.findByCPF(cpf);
        return discentes;
    }

    public List<Discente> findByIdUsuario(int id_usuario){

        //if(netOn) > consulta usuário no serviço
        discentes = discenteDAO.findByIdUsuario(id_usuario);
        return discentes;
    }
    private class InsertASync extends AsyncTask<Discente, Void, Void> {
        private DiscenteDAO discenteDAO;

        public InsertASync (DiscenteDAO discenteDAO){
            this.discenteDAO= discenteDAO;
        }
        @Override
        protected Void doInBackground(Discente... discente){
            Discente p = discente[0];
            try{
                discenteDAO.inserir(p);
            }catch (Exception e){
                Log.e(DiscenteRepository.class.getName(), "ERRO", e);
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
