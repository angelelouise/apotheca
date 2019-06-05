package com.ufrn.angele.apotheca.bd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ufrn.angele.apotheca.dominio.Usuario;

@Dao
public interface UsuarioDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void inserir(Usuario usuario);

    @Update
    public void atualizar(Usuario usuario);

    @Delete
    public void deletar (Usuario usuario);

    @Query("SELECT * FROM usuario WHERE cpf_cnpj = :cpf LIMIT 1")
    public Usuario findByCPF(Long cpf);

    @Query("SELECT * FROM usuario WHERE id_usuario = :id_usuario LIMIT 1")
    public Usuario findByIdUsuario(int id_usuario);

    @Query("SELECT * FROM usuario WHERE login = :login LIMIT 1")
    public Usuario findByLogin(String login);

}
