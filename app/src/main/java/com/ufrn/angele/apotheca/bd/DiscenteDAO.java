package com.ufrn.angele.apotheca.bd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ufrn.angele.apotheca.dominio.Discente;

import java.util.List;

@Dao
public interface DiscenteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void inserir(Discente discente);

    @Update
    public void atualizar(Discente discente);

    @Delete
    public void deletar(Discente discente);

    @Query("SELECT * FROM discente WHERE cpf = :cpf LIMIT 1")
    public List<Discente> findByCPF(String cpf);

    @Query("SELECT * FROM discente ORDER BY id ASC")
    public List<Discente> buscarTodos();

    @Query("SELECT * FROM discente WHERE id_usuario = :id_usuario LIMIT 1")
    public List<Discente> findByIdUsuario(int id_usuario);

}
