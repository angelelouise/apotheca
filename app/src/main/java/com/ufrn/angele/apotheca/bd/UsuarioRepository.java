package com.ufrn.angele.apotheca.bd;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.ufrn.angele.apotheca.bd.firestore.UsuarioDAOFirestore;
import com.ufrn.angele.apotheca.dominio.Usuario;

public class UsuarioRepository {
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private ConnectivityManager cm;
    private UsuarioDAOFirestore usuarioDAOFirestore;
    public UsuarioRepository(Application app) {
        usuarioDAO = UsuarioDB.getInstance(app).usuarioDAO();
        usuarioDAOFirestore = new UsuarioDAOFirestore();
        cm =(ConnectivityManager)app
                .getSystemService(app.CONNECTIVITY_SERVICE);
    }
    public void inserir (Usuario usuario){
        //if(netOn) > insere usuário no serviço
        new InsertASync(usuarioDAO).execute(usuario);
        usuarioDAOFirestore.inserir(usuario);
    }

    public Usuario findByCPF(Long cpf){

        //if(netOn) > consulta usuário no serviço
        if (netOn()){
            usuario = usuarioDAOFirestore.findByCPF(cpf);

        }else{
            usuario = usuarioDAO.findByCPF(cpf);
        }
        return usuario;
    }
    public Usuario findByIdUsuario(int id_usuario){
        //if(netOn) > consulta usuário no serviço
        if (netOn()){
            usuario = usuarioDAOFirestore.findByIdUsuario(id_usuario);

        }else{
            usuario = usuarioDAO.findByIdUsuario(id_usuario);
        }
        return usuario;
    }
    public Usuario findByLogin(String login){
        //if(netOn) > consulta usuário no serviço
        if (netOn()){
            usuario = usuarioDAOFirestore.findByLogin(login);

        }else{
            usuario = usuarioDAO.findByLogin(login);
        }
        return usuario;
    }
    private class InsertASync extends AsyncTask<Usuario, Void, Void> {
        private UsuarioDAO usuarioDAO;


        public InsertASync (UsuarioDAO usuarioDAO){
            this.usuarioDAO= usuarioDAO;
        }
        @Override
        protected Void doInBackground(Usuario... usuarios){
//            Usuario p = usuarios[0];
////            if(p.getId_usuario() == 0)
////                usuarioDAO.inserir(p);
////            else
////                usuarioDAO.atualizar(p);
            try {
                usuarioDAO.inserir(usuarios[0]);
            }catch(Exception e){
                Log.e(UsuarioRepository.class.getName(), "ERRO", e);
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
