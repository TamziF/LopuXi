package com.example.lopuxi.network;


import java.sql.Timestamp;
import java.util.ArrayList;

public class Post {

    public Integer id;

    public String author;

    public Timestamp time;

    public String text;

    public ArrayList<String> photos;

    /*public Post(String userName, Timestamp time, String text){
        id = 1;
        this.userName = userName;
        this.time = time;
        this.text = text;
        this.numberOfFiles = 3;
        this.photos = new ArrayList<>();
    }*/
}