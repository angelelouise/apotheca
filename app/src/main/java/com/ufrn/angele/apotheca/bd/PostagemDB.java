package com.ufrn.angele.apotheca.bd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ufrn.angele.apotheca.dominio.Postagem;

/*Necessario para implementar o livedata e viewmodel
 * 1- Cria o DAO
 * 2- Cria o BD
 * 3- Cria o repositorio
 * 4- Cria a view*/
@Database(entities = {Postagem.class},version = 1, exportSchema = false)
public abstract class PostagemDB extends RoomDatabase {
    private static PostagemDB INSTANCE;

    public abstract PostagemDAO postagemDAO();

    public static PostagemDB getInstance (Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context,PostagemDB.class,"postagem_bd").build();
        }
        return INSTANCE;
    }

}