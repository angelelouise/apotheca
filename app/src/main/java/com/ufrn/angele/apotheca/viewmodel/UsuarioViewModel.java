package com.ufrn.angele.apotheca.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.ufrn.angele.apotheca.bd.UsuarioRepository;
import com.ufrn.angele.apotheca.dominio.Usuario;

import java.util.List;

public class UsuarioViewModel extends AndroidViewModel {
    private UsuarioRepository usuarioRepository;
    private Usuario usuario;
    private List<Usuario> usuarios;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
    }
    public void inserir (Usuario usuario){
        usuarioRepository.inserir(usuario);
    }

    public Usuario findByCPF(Long cpf){
        usuario = usuarioRepository.findByCPF(cpf);
        return usuario;
    }

    public Usuario findByIdUsuario(int id_usuario){
        usuario = usuarioRepository.findByIdUsuario(id_usuario);
        return usuario;
    }
    public Usuario findByLogin(String login){
        usuario = usuarioRepository.findByLogin(login);
        return usuario;
    }
    public Usuario getUsuario() {
        return usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}