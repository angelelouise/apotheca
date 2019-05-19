package com.ufrn.angele.apotheca.bd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ufrn.angele.apotheca.dominio.Turma;

import java.util.List;

@Dao
public interface TurmaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void inserir(Turma tuma);

    @Update
    public void atualizar(Turma turma);

    @Delete
    public void deletar(Turma turma);

    @Query("SELECT * FROM turma WHERE id_discente = :id_discente LIMIT 1")
    public List<Turma> findByIdDiscente(int id_discente);
}
