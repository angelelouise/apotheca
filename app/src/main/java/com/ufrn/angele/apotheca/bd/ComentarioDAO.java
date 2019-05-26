package com.ufrn.angele.apotheca.bd;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ufrn.angele.apotheca.dominio.Comentario;

import java.util.List;

@Dao
public interface ComentarioDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void inserir(Comentario comentario);

    @Update
    public void atualizar(Comentario comentario);

    @Delete
    public void deletar (Comentario comentario);

    @Query("SELECT * FROM comentario WHERE id_postagem = :id")
    public LiveData<List<Comentario>> findById(int id);

    @Query("SELECT * FROM comentario WHERE id_autor = :id_autor")
    public LiveData<List<Comentario>> findByUsuario(Long id_autor);
}
