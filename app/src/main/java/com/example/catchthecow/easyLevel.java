package com.example.catchthecow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class easyLevel extends AppCompatActivity {

    Button b_start;
    ImageButton b_main;
    long startTime, endTime, currentTime;
    TextView t_eScore;
    TextView e_HScore;
    long eHighScore;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);

        //Initierar SharedPreferences som används för att memorera High Score.
        SharedPreferences prefs = this.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);

        //Hämtar värdet för nivåns Highscore
        eHighScore = prefs.getLong("EASY_HIGHSCORE", 0);



        b_start = (Button) findViewById(R.id.b_start);
        b_main = (ImageButton) findViewById(R.id.b_main);
        t_eScore = (TextView) findViewById(R.id.t_eScore);
        e_HScore = (TextView) findViewById(R.id.easyHs);

        b_main.setBackgroundColor(getResources().getColor(R.color.red));


        e_HScore.setText("High Score: " + eHighScore);
        Log.d("CurrentHighscoreEAsy", "Current Highscore: " + eHighScore);







        //Initiera mediaspelare
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cow_correct);

        b_start.setEnabled(true);
        b_main.setEnabled(false);

        b_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b_start.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startTime = System.currentTimeMillis();
                        b_main.setBackgroundColor(getResources().getColor(R.color.green));
                        b_main.setEnabled(true);
                    }
                }, 2000);
            }
        });

        b_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                endTime = System.currentTimeMillis();
                currentTime = endTime - startTime;
                b_main.setBackgroundColor(getResources().getColor(R.color.red));
                t_eScore.setText(currentTime + " ms");
                b_start.setEnabled(true);
                b_main.setEnabled(false);

                //Säkerhet för att säkerställa att highscore inte är 0 vid start
                if (eHighScore == 0) {
                    updateScore();
                }
                //Om highscore är över 0 så sätts det till den nya tiden.
                else if (currentTime < eHighScore) {

                    updateScore();
                    //Spelar upp ljud om man slår highscore
                    mp.start();

                }
                e_HScore.setText("High Score: " + eHighScore);


                Log.d("Easy Highscore: ","Easy HighScore: " + eHighScore);
            }

        });

    }
    private void updateScore(){

        //Initierar SharedPreferences som används för att memorera & ändra High Score.
        SharedPreferences prefs = this.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("EASY_HIGHSCORE", currentTime);
        editor.commit();
        eHighScore = prefs.getLong("EASY_HIGHSCORE", 0);

    }
}