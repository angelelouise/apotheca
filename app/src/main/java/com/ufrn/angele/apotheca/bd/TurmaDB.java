package com.ufrn.angele.apotheca.bd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ufrn.angele.apotheca.dominio.Turma;

@Database(entities = {Turma.class}, version = 1, exportSchema = false)
public abstract class TurmaDB extends RoomDatabase {

    private static TurmaDB INSTANCE;

    public abstract TurmaDAO turmaDAO();

    public static TurmaDB getInstance (Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context,TurmaDB.class,"turma").build();
        }
        return INSTANCE;
    }
}
