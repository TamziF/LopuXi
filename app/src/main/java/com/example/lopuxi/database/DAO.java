package com.example.lopuxi.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface DAO {

    @Query("SELECT * FROM postentity")
    List<PostEntity> getAll();

    @Insert
    Completable add(PostEntity post);

}
