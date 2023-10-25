package com.example.lopuxi.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {PostEntity.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract DAO Dao();

    private static Database instance = null;

    public static synchronized Database getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "database").build();
        }
        return instance;
    }

}