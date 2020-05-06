package com.example.blindtest;

import android.os.Build;

public class Music {

    private int id;
    private String name;
    private String path_extrait;
    private Category category;


    public Music(int _id, String _name, String _path_extrait, Category _category){
        this.id = _id;
        this.name = _name;
        this.path_extrait = _path_extrait;
        this.category = _category;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath_extrait() {
        return path_extrait;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path_extrait='" + path_extrait + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
