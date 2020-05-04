package com.example.blindtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Database {
    private SQLiteDatabase sqlDB;

    private ArrayList<Music> musics = null;
    private ArrayList<Music> songs = null;
    private ArrayList<Music> animes = null;
    private ArrayList<Music> movies = null;


    public Database(Context context){
        System.out.println("Lien de la BDD : "+context.getFilesDir().getPath());
        sqlDB = openOrCreateDatabase(context.getFilesDir().getPath() + "blindtest", null);

        //\/\/\/ A docommenter si vous voulez remettre la bdd a neuf \/\/\/
        //sqlDB.execSQL("DROP TABLE MUSIC");

        sqlDB.execSQL("CREATE TABLE IF NOT EXISTS MUSIC (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "path_extrait VARCHAR," +
                "category VARCHAR" +
                ")"
        );
        //sqlDB.close();
    }


    //Ajoute les tuples dans la bdd, correspondant aux chansons (action assigné au bouton fill  du home screen)
    public void fillDB(){
        ContentValues values = new ContentValues();
        values.put("name", "Billie Jean");
        values.put("path_extrait", "billiejean");
        values.put("category", "Music");
        sqlDB.insert("MUSIC", null, values);

        values = new ContentValues();
        values.put("name", "Hey ya!");
        values.put("path_extrait", "heyya");
        values.put("category", "Music");
        sqlDB.insert("MUSIC", null, values);

        values = new ContentValues();
        values.put("name", "Stayin'alive");
        values.put("path_extrait", "stayinalive");
        values.put("category", "Music");
        sqlDB.insert("MUSIC", null, values);

        values = new ContentValues();
        values.put("name", "Wake me up");
        values.put("path_extrait", "wakemeup");
        values.put("category", "Music");
        sqlDB.insert("MUSIC", null, values);

//        String insert = "INSERT INTO MUSIC(id, name, path_extrait, category) " +
//                "VALUES (null, 'primary', 'music/extrait/primary.mp3', 'Films');";
//        this.sqlDB.execSQL(insert);

    }


    //recupere les musiques de la BDD et les mets dans musics (variable de classes)
    public ArrayList<Music> getAllMusic(){
        if (musics == null) {
            Cursor cursor;
            int id_temp;
            String name_temp, path_extrait_temp;
            Category category_temp = null;
            ArrayList<Music> musics_temp = new ArrayList<>();

            cursor = sqlDB.rawQuery("SELECT * FROM MUSIC;", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    id_temp = cursor.getInt(0);
                    name_temp = cursor.getString(1);
                    path_extrait_temp = cursor.getString(2);
                    switch (cursor.getString(3)){
                        case "Music":
                            category_temp = Category.SONG;
                            break;
                        case "Film":
                            category_temp = Category.FILM;
                            break;
                        case "Anime":
                            category_temp = Category.ANIME;
                            break;
                        default:
                            Log.e("DATABASE", "Une erreur s'est produite lors de la gestion des categories");
                            System.exit(-1);
                    }
                    Music music = new Music(id_temp, name_temp, path_extrait_temp, category_temp);
                    switch (music.getCategory()){
                        case SONG:
                            songs.add(music);
                            break;
                        case FILM:
                            movies.add(music);
                            break;
                        case ANIME:
                            animes.add(music);
                            break;
                    }
//                    System.out.println("BDD : id_temp : " + id_temp);
//                    System.out.println("BDD : name_temp : " + name_temp);
//                    System.out.println("BDD : path_extrait_temp : " + path_extrait_temp);
//                    System.out.println("BDD : category_temp : " + category_temp);
                    musics_temp.add(music);
                    cursor.moveToNext();
                }
            }

            this.musics = musics_temp;
            return musics_temp;
        }
        return this.musics;
    }

    //Recupere n chansons et les mets dans une liste(ar). le premier element de cette liste est la reponse attendue et donc la musique jouée
    public ArrayList<Music> getRand(int n){
        ArrayList<Music> ar = new ArrayList();
        ar.add(getOneMusicRand());

        ArrayList<Integer> indexalreadychoosen = new ArrayList();
        indexalreadychoosen.add( ar.get(0).getId() );

        for(int i =0 ; i<n-1; i ++){
            Music m = getOtherRand(indexalreadychoosen);
            ar.add(m);
            indexalreadychoosen.add(m.getId());
        }
        return ar;
    }

    //Recupere une musique aleatoirement et la renvoie. Elle sera considérée comme la reponse juste
    private Music getOneMusicRand(){
        Random r = new Random();
        int i = r.nextInt(getAllMusic().size());
        return musics.get(i);
    }

    //Recupere des musique aleatoirement et les renvoie sous forme de listes. Elle constitueront les réponses fausses
    private Music getOtherRand(ArrayList<Integer> idsToDodge){
        Music m;
        while (true) {
            m = getOneMusicRand();
            if ( !idsToDodge.contains(m.getId()) )  return m;
        }
    }

    //Recupere n chansons et les mets dans une liste(ar). le premier element de cette liste est la reponse attendue et donc la musique jouée
    public ArrayList<Music> getByCategory(int n, ArrayList<Category> categories){
        ArrayList<Music> ar = new ArrayList();
        ar.add(getOneMusicRandByCategory(categories));

        ArrayList<Integer> indexalreadychoosen = new ArrayList();
        indexalreadychoosen.add( ar.get(0).getId() );

        for(int i =0 ; i<n-1; i ++){
            Music m = getOtherRandByCategory(indexalreadychoosen, categories);
            ar.add(m);
            indexalreadychoosen.add(m.getId());
        }
        return ar;
    }

    //Recupere une musique aleatoirement correspondant a la categorie et la renvoie. Elle sera considérée comme la reponse juste
    private Music getOneMusicRandByCategory(ArrayList<Category> categories){
        Random r = new Random();
        ArrayList<Music> total = new ArrayList<>();
        for (int j = 0; j < categories.size() ; j++){
            switch (categories.get(j)){
                case SONG:
                    total.addAll(songs);
                    break;
                case FILM:
                    total.addAll(movies);
                    break;
                case ANIME:
                    total.addAll(animes);
                    break;
            }
        }
        int i = r.nextInt(total.size());
        return total.get(i);
    }

    //Recupere des musique aleatoirement des categories passees en parametre et les renvoie sous forme de listes. Elle constitueront les réponses fausses
    private Music getOtherRandByCategory(ArrayList<Integer> idsToDodge, ArrayList<Category> categories){
        Music m;
        while (true) {
            m = getOneMusicRandByCategory(categories);
            if ( !idsToDodge.contains(m.getId()) )  return m;
        }
    }

}
