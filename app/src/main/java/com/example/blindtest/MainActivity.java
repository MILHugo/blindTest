package com.example.blindtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public static Database DB;

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

        DB = new Database(getApplicationContext());

        init();
    }

    private void init(){
        btnSingleplayer = findViewById(R.id.BTN_singleplayer);
        btnSingleplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ThemeActivity.class);
                startActivity(intent);
            }
        });

        btnMultiplayer = findViewById(R.id.BTN_multiplayer);
        btnMultiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : implementer le mode multijoueur
                Snackbar.make(v, getString(R.string.in_development), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnSettings = findViewById(R.id.BTN_setting);
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
        if (preferences.getBoolean("ThemeChanged", false)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("ThemeChanged", false);
            editor.apply();
            this.recreate();
        }
    }
}
