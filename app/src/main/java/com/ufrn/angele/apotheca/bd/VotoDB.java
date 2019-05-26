package com.ufrn.angele.apotheca.bd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ufrn.angele.apotheca.dominio.Voto;

@Database(entities = {Voto.class},version = 1, exportSchema = false)
public abstract class VotoDB extends RoomDatabase {
    private static VotoDB INSTANCE;
    public abstract VotoDAO votoDAO();

    public static VotoDB getInstance (Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context,VotoDB.class,"voto_bd").build();
        }
        return INSTANCE;
    }
}