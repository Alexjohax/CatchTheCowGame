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

public class medLevel extends AppCompatActivity {

    Button b_start;
    ImageButton b_main;
    long startTime, endTime, currentTime;
    TextView t_mScore;
    TextView m_HScore;
    long mHighScore;
    int delayMin = 500;
    int delayMax = 5000;












    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_level);

        b_start = (Button) findViewById(R.id.b_m_start);
        b_main = (ImageButton) findViewById(R.id.b_m_main);
        t_mScore = (TextView) findViewById(R.id.t_mScore);
        m_HScore = (TextView) findViewById(R.id.medHs);

        b_main.setBackgroundColor(getResources().getColor(R.color.red));

        //Initierar SharedPreferences som används för att memorera High Score.
        SharedPreferences prefs = this.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);



        //Hämtar värdet för nivåns Highscore
        mHighScore = prefs.getLong("MEDIUM_HIGHSCORE", 0);

        m_HScore.setText("High Score: " + mHighScore);
        Log.d("CurrentHighscoreMed", "Current Highscore: " + mHighScore);

        //Fångar upp displayens mått
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        //Displayens width o höjd
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        Log.d("scr sizze", "X: " +w + "Y: " + h);
        //Begränsningar för var knappen kan hamna
        int b_X_min = 1;
        int b_X_max = w-(w/4);
        double b_Y_min = h-(h*(0.8));
        int b_Y_max = h-(h/2);



        //Initiera mediaspelare
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cow_correct);






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
                Log.d("ButtonX", "Button X: " + buttonX);
                Log.d("Buttony", "Button Y: " + buttonY);


                b_start.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startTime = System.currentTimeMillis();
                        b_main.setBackgroundColor(getResources().getColor(R.color.green));
                        b_main.setEnabled(true);

                        //Efter delay_int räknat ner ändras knappen b_mains position

                        b_main.setX(buttonX);
                        b_main.setY(buttonY);
                    }
                }, delay_int);
            }
        });

        b_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                endTime = System.currentTimeMillis();
                currentTime = endTime - startTime;
                b_main.setBackgroundColor(getResources().getColor(R.color.red));
                t_mScore.setText(currentTime + " ms");
                b_start.setEnabled(true);
                b_main.setEnabled(false);

                if (mHighScore == 0) {
                    updateScore();
                } else if (currentTime < mHighScore) {
                    //Uppdaterar Highscore genom metoden nedan
                    updateScore();
                    //Spelar upp ljud om man slog highscore
                    mp.start();
                }
                m_HScore.setText("High Score: " + mHighScore);

            }
        });
    }
    private void updateScore(){

        //Initierar SharedPreferences som används för att memorera & ändra High Score.
        SharedPreferences prefs = this.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("MEDIUM_HIGHSCORE", currentTime);
        editor.commit();
        mHighScore = prefs.getLong("MEDIUM_HIGHSCORE", 0);

    }
}