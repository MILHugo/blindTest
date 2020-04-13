package com.example.blindtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThemeActivity extends AppCompatActivity {

    Button btnBegin;

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
        btnBegin = findViewById(R.id.BTN_commencer);
        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SingleplayerActivity.class);
                startActivity(intent);
            }
        });
    }
}
