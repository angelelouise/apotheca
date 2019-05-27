package com.ufrn.angele.apotheca.bd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ufrn.angele.apotheca.dominio.Voto;
@Dao
public interface VotoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void inserir(Voto voto);

    @Update
    public void atualizar(Voto voto);

    @Delete
    public void deletar (Voto voto);

    @Query("SELECT COUNT(id) FROM voto WHERE id_postagem = :id_postagem and id_comentario is null and voto = 1")
    public int countVotosPostagem(int id_postagem);

    @Query("SELECT COUNT(id) FROM voto WHERE id_postagem = :id_postagem and id_comentario is null and negativacao = 1")
    public int countNegativacoesPostagem(int id_postagem);

    @Query("SELECT COUNT(*) FROM voto WHERE id_postagem = :id_postagem and id_comentario = :id_comentario and voto = 1 and negativacao =0")
    public int countVotosComentario(int id_postagem, int id_comentario);

    @Query("SELECT COUNT(*) FROM voto WHERE id_postagem = :id_postagem and id_comentario = :id_comentario and negativacao = 1 and voto=0")
    public int countNegativacoesComentario(int id_postagem, int id_comentario);
}
