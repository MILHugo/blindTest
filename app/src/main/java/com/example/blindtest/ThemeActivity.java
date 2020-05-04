package com.example.blindtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class ThemeActivity extends AppCompatActivity {

    Button btnBegin;

    CheckBox chkSongs;
    CheckBox chkMovies;
    CheckBox chkAnime;
    CheckBox chkAll;

    ArrayList<Category> listCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences=getSharedPreferences("com.example.blindtest", 0);
        if (preferences.getBoolean("DarkMode", false))
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        init();
    }

    private void init(){
        listCategories = new ArrayList<>();

        btnBegin = findViewById(R.id.BTN_commencer);
        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chkAll.isChecked()){//si toutes les categories sont selectionees, la liste est vide
                    if (chkAnime.isChecked())
                        listCategories.add(Category.ANIME);
                    if (chkMovies.isChecked())
                        listCategories.add(Category.FILM);
                    if (chkSongs.isChecked())
                        listCategories.add(Category.SONG);
                }
                Intent intent = new Intent(getBaseContext(), SingleplayerActivity.class);
                startActivity(intent);
            }
        });

        chkAll = findViewById(R.id.CHK_ThemeAll);
        chkAnime = findViewById(R.id.CHK_anime);
        chkMovies = findViewById(R.id.CHK_movies);
        chkSongs = findViewById(R.id.CHK_song);

        chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){//si on coche le bouton all, tous les autres boutons sont coches
                    chkAnime.setChecked(true);
                    chkMovies.setChecked(true);
                    chkSongs.setChecked(true);
                }
                else {//Si on decoche le bouton all alors que tous les autres sont coches, on les decoches tous
                    if (chkSongs.isChecked() && chkMovies.isChecked() && chkAnime.isChecked()) {
                        chkAnime.setChecked(false);
                        chkMovies.setChecked(false);
                        chkSongs.setChecked(false);
                    }
                }
            }
        });

        CompoundButton.OnCheckedChangeListener boxesListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkSongs.isChecked() && chkMovies.isChecked() && chkAnime.isChecked())//si tous les boutons sont coches, ont coche all
                    chkAll.setChecked(true);
                if (!chkSongs.isChecked() || !chkMovies.isChecked() || !chkAnime.isChecked())//si un des boutons est decoches, on decoche all
                    chkAll.setChecked(false);
            }
        };

        chkSongs.setOnCheckedChangeListener(boxesListener);
        chkAnime.setOnCheckedChangeListener(boxesListener);
        chkMovies.setOnCheckedChangeListener(boxesListener);
    }


}
