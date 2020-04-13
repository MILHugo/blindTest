package com.example.blindtest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Database {
    private SQLiteDatabase sqlDB;

    private ArrayList<Music> musics = null;

    public Database(Context context){
        sqlDB = openOrCreateDatabase(context.getFilesDir().getPath() + "blindtest", null);

        sqlDB.execSQL("CREATE TABLE IF NOT EXISTS MUSIC (" +
                "id INT PRIMARY KEY," +
                "name VARCHAR," +
                "path_extrait VARCHAR," +
                "category VARCHAR" +
                ")"
        );
    }


    public ArrayList<Music> getAllMusic(){
        if (musics != null) {
            Cursor c;
            int id_temp;
            String name_temp, path_extrait_temp, category_temp;
            ArrayList<Music> musics_temp = new ArrayList<>();

            c = sqlDB.rawQuery("SELECT * FROM MUSIC;", null);
            c.moveToFirst();

            for (int i = 0; c.moveToPosition(i); i++) {
                id_temp = c.getInt(0);
                name_temp = c.getString(1);
                path_extrait_temp = c.getString(2);
                category_temp = c.getString(3);
                musics_temp.add(new Music(id_temp, name_temp, path_extrait_temp, category_temp));
            }
            c.close();
            this.musics = musics_temp;
            return musics_temp;
        }
        return this.musics;
    }


    public Music getOneMusicRand(){
        Random r = new Random();
        int i = r.nextInt(getAllMusic().size());
        return musics.get(i);
    }
}
