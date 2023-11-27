package com.example.lopuxi.network;

import java.util.ArrayList;

public class CreatePostForm {
    public String author;
    public String text;
    public ArrayList<String> photosID;

    public CreatePostForm(String author, String text, ArrayList<String> photosID){
        this.author = author;
        this. text = text;
        this.photosID = photosID;
    }
}