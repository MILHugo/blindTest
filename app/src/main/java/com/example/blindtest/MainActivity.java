package com.example.blindtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnSingleplayer;
    Button btnMultiplayer;
    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences=getSharedPreferences("com.example.blindtest", 0);
        if (preferences.getBoolean("DarkMode", false))
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        btnSingleplayer = findViewById(R.id.id_singleplayer);
        btnSingleplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ThemeActivity.class);
                startActivity(intent);
            }
        });

        btnMultiplayer = findViewById(R.id.id_multiplayer);
        btnMultiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : implementer le mode multijoueur
            }
        });

        btnSettings = findViewById(R.id.id_setting);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences preferences = getSharedPreferences("com.example.blindtest", 0);
        Log.d("DEBUG", "Theme changed : "+preferences.getBoolean("ThemeChanged", false));
        if (preferences.getBoolean("ThemeChanged", false)){
            SharedPreferences.Editor editor = preferences.edit();
            Log.d("DEBUG", "Recreate main");
            editor.putBoolean("ThemeChanged", false);
            editor.apply();
            this.recreate();
        }
    }
}
