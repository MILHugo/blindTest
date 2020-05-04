package com.example.blindtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SingleplayerActivity extends AppCompatActivity {

    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    ArrayList<RadioButton> radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences=getSharedPreferences("com.example.blindtest", 0);
        if (preferences.getBoolean("DarkMode", false))
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        init();
    }

    private void init(){
        rb1 = findViewById(R.id.BTN_rep1);
        rb2 = findViewById(R.id.BTN_rep2);
        rb3 = findViewById(R.id.BTN_rep3);
        rb4 = findViewById(R.id.BTN_rep4);

        radioGroup = new ArrayList<>();
        radioGroup.add(rb1);
        radioGroup.add(rb2);
        radioGroup.add(rb3);
        radioGroup.add(rb4);

        //to uncheck the selected radiobutton
        for (final RadioButton rb : radioGroup){
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (RadioButton otherRB : radioGroup)
                        otherRB.setChecked(false);
                    rb.setChecked(true);
                }
            });
        }

        //recupere 4 chansons de la bdd et les mets dans une liste
        ArrayList<Music> musics;
        if (ThemeActivity.listCategories.isEmpty()) {
            musics = MainActivity.DB.getRand(4);
        } else {
            musics = MainActivity.DB.getByCategory(4, ThemeActivity.listCategories);
        }

        //Liste qui contiendra les index des chansons deja ajoutés aux propositions
        ArrayList<Integer> indexalreadychoosen = new ArrayList();
        int i = 0;

        //on veut 4 propositions
        while (i < 4) {
            Random r = new Random();
            int k = r.nextInt(musics.size());
            if( !indexalreadychoosen.contains(k) ){
                radioGroup.get(i).setText(musics.get(k).getName());
                indexalreadychoosen.add(k);
                i++;
            }
        }

        //recupere l'id du .mp3 a partir de son nom
        int musicid = getResources().getIdentifier(musics.get(0).getPath_extrait(),"raw", getPackageName());

        //prepare la music et attend une sec avant de la jouer
        final MediaPlayer mp = MediaPlayer.create(this, musicid);
        TimerTask task = new TimerTask() {
            public void run() {
                mp.start();
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        timer.schedule(task, delay);

        //affiche les musics choisis en propositions
        for (Music m: musics) {
            System.out.println("BDD: " + m);
        }




    }
}
