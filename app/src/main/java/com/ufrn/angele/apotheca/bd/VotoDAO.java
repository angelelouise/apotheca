package com.ufrn.angele.apotheca.bd;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ufrn.angele.apotheca.dominio.Voto;

import java.util.List;
@Dao
public interface VotoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void inserir(Voto voto);

    @Update
    public void atualizar(Voto voto);

    @Delete
    public void deletar (Voto voto);

    @Query("SELECT COUNT(id) FROM voto WHERE id_postagem = :id_postagem and id_comentario is null and voto is not null")
    public LiveData<Integer> countVotosPostagem(int id_postagem);

    @Query("SELECT COUNT(id) FROM voto WHERE id_postagem = :id_postagem and id_comentario is null and negativacao is not null")
    public LiveData<Integer> countNegativacoesPostagem(int id_postagem);

    @Query("SELECT COUNT(id) FROM voto WHERE id_postagem = :id_postagem and id_comentario = :id_comentario and voto is not null")
    public LiveData<Integer> countVotosComentario(int id_postagem, int id_comentario);

    @Query("SELECT COUNT(id) FROM voto WHERE id_postagem = :id_postagem and id_comentario = :id_comentario and negativacao is not null")
    public LiveData<Integer> countNegativacoesComentario(int id_postagem, int id_comentario);
}
