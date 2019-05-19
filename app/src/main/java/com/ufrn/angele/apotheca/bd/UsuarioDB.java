package com.ufrn.angele.apotheca.bd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ufrn.angele.apotheca.dominio.Usuario;


@Database(entities = { Usuario.class},version = 1, exportSchema = false)
public abstract class UsuarioDB extends RoomDatabase {

    private static UsuarioDB INSTANCE;

    public abstract UsuarioDAO usuarioDAO();

    public static UsuarioDB getInstance (Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context,UsuarioDB.class,"usuario").build();
        }
        return INSTANCE;
    }
}
