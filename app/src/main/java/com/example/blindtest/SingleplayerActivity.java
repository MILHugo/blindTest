package com.example.blindtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;

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
    }
}
