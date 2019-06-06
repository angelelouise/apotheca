package com.ufrn.angele.apotheca.bd;


import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.ufrn.angele.apotheca.bd.firestore.TurmaDAOFirestore;
import com.ufrn.angele.apotheca.dominio.Turma;

import java.util.List;

public class TurmaRepository {
    private List<Turma> turmas;
    private TurmaDAO turmaDAO;
    private ConnectivityManager cm;
    private TurmaDAOFirestore turmaDAOFirestore;

    public  TurmaRepository(Application app){
        turmaDAO = TurmaDB.getInstance(app).turmaDAO();
        turmaDAOFirestore = new TurmaDAOFirestore();
        //curricularDAOFirestore = new CCurricularDAOFirestore();
        cm =(ConnectivityManager)app
                .getSystemService(app.CONNECTIVITY_SERVICE);
    }

    public void inserir (Turma turma){
        //new InsertASync(turmaDAO).execute(turma);
        turmaDAOFirestore.inserir(turma);
    }
    public List<Turma> findByIdUsuario(int id_usuario){

        //if(netOn) > consulta usuário no serviço
        turmas = turmaDAO.findByIdDiscente(id_usuario);
        return turmas;
    }
    private class InsertASync extends AsyncTask<Turma, Void, Void> {
        private TurmaDAO turmaDAO;

        public InsertASync (TurmaDAO turmaDAO){
            this.turmaDAO= turmaDAO;
        }
        @Override
        protected Void doInBackground(Turma... turma){
            Turma p = turma[0];
            try{
                turmaDAO.inserir(p);
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
