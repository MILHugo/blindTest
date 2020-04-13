package com.example.blindtest;

import android.os.Build;

public class Music {

    private int id;
    private String name;
    private String path_extrait;
    private String category;


    public Music(int _id, String _name, String _path_extrait, String _category){
        this.id = _id;
        this.name = _name;
        this.path_extrait = _path_extrait;
        this.category = _category;
    }
}
