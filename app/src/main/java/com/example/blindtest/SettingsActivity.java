package com.example.blindtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    Switch switchTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences=getSharedPreferences("com.example.blindtest", 0);
        if (preferences.getBoolean("DarkMode", false))
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
    }

    private void init(){
        final SharedPreferences preferences = getSharedPreferences("com.example.blindtest", 0);

        switchTheme = findViewById(R.id.SWT_sombre);
        switchTheme.setChecked(preferences.getBoolean("DarkMode", false));
        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                if (isChecked) {
                    editor.putBoolean("DarkMode", true);
                }
                else {
                    editor.putBoolean("DarkMode", false);
                }
                editor.putBoolean("ThemeChanged", true);
                editor.apply();
                recreate();
            }
        });
    }
}
