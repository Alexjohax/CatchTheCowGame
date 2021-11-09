package com.example.catchthecow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {



    //Initiera variablar för knappar
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    long MAIN_EASY_HIGHSCORE;
    long MAIN_MEDIUM_HIGHSCORE;
    long MAIN_HARD_HIGHSCORE;

    TextView textEasy;
    TextView textMedium;
    TextView textHard;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textEasy = findViewById(R.id.text_easy_highscore);
        textMedium = findViewById(R.id.text_med_highscore);
        textHard = findViewById(R.id.text_hard_highscore);


        //Initiera Shared preferences för att komma åt highscore.
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);

        MAIN_EASY_HIGHSCORE = sharedPreferences.getLong("EASY_HIGHSCORE", 0);
        MAIN_MEDIUM_HIGHSCORE = sharedPreferences.getLong("MEDIUM_HIGHSCORE", 0);
        MAIN_HARD_HIGHSCORE = sharedPreferences.getLong("HARD_HIGHSCORE", 0);





        textEasy.setText("Easy High Score: " + MAIN_EASY_HIGHSCORE + " ms");
        textMedium.setText("Medium High Score: " + MAIN_MEDIUM_HIGHSCORE + " ms");
        textHard.setText("Hard High Score: " + MAIN_HARD_HIGHSCORE + " ms");





        //Initiera mediaspelare
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cow_startup);
        //Spela upp ljud när appen startas
        mp.start();

        //Aktivera knapparna
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        //Sätter en onClickListener på varje knapp och vad de skall göra
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v1) {
                easyLvl();
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v2) {
                medLvl();
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v3) {
                hardLvl();
            }
        });

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v4) {
                rules();
            }
        });









    }

    @Override
    protected void onResume() {
        super.onResume();

        update();
    }



    //De metoder som används vid klick på respektive knapp. Startar ett Intent med resp. class.
    public void easyLvl() {
        Intent intent = new Intent(this, easyLevel.class);
        //startar activityn
        startActivity(intent);
    }
    public void medLvl() {
        Intent intent = new Intent(this, medLevel.class);
        startActivity(intent);
    }
    public void hardLvl() {
        Intent intent = new Intent(this, hardLevel.class);
        startActivity(intent);
    }
    public void rules() {
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }
    //Metod för att uppdatera highscore vid bakåtklick
    public void update() {

        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        MAIN_EASY_HIGHSCORE = sharedPreferences.getLong("EASY_HIGHSCORE", 0);
        MAIN_MEDIUM_HIGHSCORE = sharedPreferences.getLong("MEDIUM_HIGHSCORE", 0);
        MAIN_HARD_HIGHSCORE = sharedPreferences.getLong("HARD_HIGHSCORE", 0);

        textEasy = findViewById(R.id.text_easy_highscore);
        textMedium = findViewById(R.id.text_med_highscore);
        textHard = findViewById(R.id.text_hard_highscore);

        textEasy.setText("Easy High Score: " + MAIN_EASY_HIGHSCORE + " ms");
        textMedium.setText("Medium High Score: " + MAIN_MEDIUM_HIGHSCORE + " ms");
        textHard.setText("Hard High Score: " + MAIN_HARD_HIGHSCORE + " ms");
    }




}
