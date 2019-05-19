package com.ufrn.angele.apotheca.bd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ufrn.angele.apotheca.dominio.Discente;

@Database(entities = { Discente.class},version = 1, exportSchema = false)
public abstract class DiscenteDB extends RoomDatabase {
    private static DiscenteDB INSTANCE;
    public abstract DiscenteDAO discenteDAO();

    public static DiscenteDB getInstance (Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context,DiscenteDB.class,"discente").build();
        }
        return INSTANCE;
    }
}
