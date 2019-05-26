package com.ufrn.angele.apotheca.bd;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.ufrn.angele.apotheca.dominio.Usuario;

public class UsuarioRepository {
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private ConnectivityManager cm;

    public UsuarioRepository(Application app) {
        usuarioDAO = UsuarioDB.getInstance(app).usuarioDAO();
        cm =(ConnectivityManager)app
                .getSystemService(app.CONNECTIVITY_SERVICE);
    }
    public void inserir (Usuario usuario){
        //if(netOn) > insere usuário no serviço
        new InsertASync(usuarioDAO).execute(usuario);
    }

    public Usuario findByCPF(Long cpf){

        //if(netOn) > consulta usuário no serviço
        usuario = usuarioDAO.findByCPF(cpf);
        return usuario;
    }
    public Usuario findById(int id_usuario){
        //if(netOn) > consulta usuário no serviço
        usuario = usuarioDAO.findByIdUsuario(id_usuario);
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
