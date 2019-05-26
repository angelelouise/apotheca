package com.ufrn.angele.apotheca.bd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ufrn.angele.apotheca.dominio.Comentario;

@Database(entities = {Comentario.class},version = 1, exportSchema = false)
public abstract class ComentarioDB extends RoomDatabase{
    private static ComentarioDB INSTANCE;
    public abstract ComentarioDAO comentarioDAO();

    public static ComentarioDB getInstance (Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context,ComentarioDB.class,"comentario_bd").build();
        }
        return INSTANCE;
    }
}

