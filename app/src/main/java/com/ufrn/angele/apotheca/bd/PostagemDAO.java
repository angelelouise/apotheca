package com.ufrn.angele.apotheca.bd;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ufrn.angele.apotheca.dominio.Postagem;

import java.util.List;

@Dao
public interface PostagemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void inserir(Postagem postagem);

    @Update
    public void atualizar(Postagem postagem);

    @Delete
    public void deletar (Postagem postagem);

    @Query("SELECT * FROM postagem WHERE id_postagem = :id LIMIT 1")
    public LiveData<Postagem> findById(Long id);

    @Query("SELECT * FROM postagem WHERE id_autor = :id_autor")
    public LiveData<List<Postagem>> findByUsuario(Long id_autor);

//    @Query("SELECT * FROM postagem INNER JOIN usuario ON (usuario.id = postagem.id_usuario)WHERE usuario.usuario_nome = :nome")
//    public LiveData<List<Postagem>> findByAutor(String nome);

    @Query("SELECT * FROM postagem WHERE id_turma = :id_turma")
    public LiveData<List<Postagem>> findByTurma(Long id_turma);

    @Query("SELECT * FROM postagem ORDER BY id_postagem ASC")
    public LiveData<List<Postagem>> buscarTodas();
}
