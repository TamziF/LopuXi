package com.example.lopuxi.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PostEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int pId;

    @ColumnInfo(name = "image_url")
    public String imageUrl;

    @ColumnInfo(name = "description_text")
    public String postText;

    public PostEntity(String imageUrl, String postText) {
        this.imageUrl = imageUrl;
        this.postText = postText;
    }
}
