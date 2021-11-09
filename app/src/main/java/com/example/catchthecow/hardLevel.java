package com.example.catchthecow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class hardLevel extends AppCompatActivity {

    Button b_start;
    ImageButton b_main;
    long startTime, endTime, currentTime;
    TextView t_hardScore;
    TextView hard_HScore;
    long hardHighScore;
    int delayMin = 50;
    int delayMax = 2500;













    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_level);

        b_start = (Button) findViewById(R.id.b_h_start);
        b_main = (ImageButton) findViewById(R.id.b_h_main);
        t_hardScore = (TextView) findViewById(R.id.t_hardScore);
        hard_HScore = (TextView) findViewById(R.id.hardHs);

        b_main.setBackgroundColor(getResources().getColor(R.color.red));

        //Initierar SharedPreferences som används för att memorera & ändra High Score.
        SharedPreferences prefs = this.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);



        //Hämtar värdet för nivåns Highscore
        hardHighScore = prefs.getLong("HARD_HIGHSCORE", 0);

        hard_HScore.setText("High Score: " + hardHighScore);
        Log.d("CurrentHighscoreHARD", "Current Highscore: " + hardHighScore);

        //Fångar upp displayens mått
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        //Displayens width o höjd
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        Log.d("scr sizze", "X: " +w + "Y: " + h);

        //Begränsningar för spelområdet
        int b_X_min = 1;
        int b_X_max = w-(w/4);
        double b_Y_min = h-(h*(0.8));
        int b_Y_max = h-(h/2);

        //Initiera mediaspelare
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cow_correct);



        b_start = (Button) findViewById(R.id.b_h_start);
        b_main = (ImageButton) findViewById(R.id.b_h_main);
        t_hardScore = (TextView) findViewById(R.id.t_hardScore);
        hard_HScore = (TextView) findViewById(R.id.hardHs);

        b_main.setBackgroundColor(getResources().getColor(R.color.red));



        Random buttonPlace = new Random();

        b_start.setEnabled(true);
        b_main.setEnabled(false);

        b_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Skapar en int-variabel för att slumpa fram en fördröjning när man trycker start
                int delay_int = (int)Math.floor(Math.random()*(delayMax-delayMin+1)+delayMin);
                Log.d("Delay:", "delay = " + delay_int);
                int buttonX = (int)Math.floor(Math.random()*(b_X_max-b_X_min+1)+b_X_min);
                int buttonY = (int)Math.floor(Math.random()*(b_Y_max-b_Y_min+1)+b_Y_min);


                b_start.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Tid då knappen trycktes på
                        startTime = System.currentTimeMillis();
                        //Ändrar knappen till grön när delay_int räknat ner och gör så att knappen kan användas
                        b_main.setBackgroundColor(getResources().getColor(R.color.green));
                        b_main.setEnabled(true);


                        b_main.setX(buttonX);
                        b_main.setY(buttonY);
                    }
                }, delay_int);
            }
        });

        b_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                //Tid då spelaren tryckte på kon
                endTime = System.currentTimeMillis();
                //Diff mellan dessa
                currentTime = endTime - startTime;
                //Sätt bakgrundsfärgen till röd och disabla knappen, visa score
                b_main.setBackgroundColor(getResources().getColor(R.color.red));
                t_hardScore.setText(currentTime + " ms");
                b_start.setEnabled(true);
                b_main.setEnabled(false);

                if (hardHighScore == 0) {
                    updateScore();
                } else if (currentTime < hardHighScore) {
                    Log.d("Else if", "Vi är i else if statement");

                    updateScore();
                    mp.start();
                }
                hard_HScore.setText("High Score: " + hardHighScore);

            }
        });


    }
    private void updateScore(){

        //Initierar SharedPreferences som används för att memorera & ändra High Score.
        SharedPreferences prefs = this.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //Genom editor sparas det nya highscoret i variabeln hardHighscore.
        editor.putLong("HARD_HIGHSCORE", currentTime);
        editor.commit();
        hardHighScore = prefs.getLong("HARD_HIGHSCORE", 0);

    }
}
