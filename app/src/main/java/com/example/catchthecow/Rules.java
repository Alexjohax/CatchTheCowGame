package com.example.catchthecow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Rules extends AppCompatActivity {

    TextView rules_header;
    TextView rules_text;
    Button btn_reset;
    Resources res;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        //Skapar de variabler och layouts för att kunna rita bitmap nedan
        ConstraintLayout constraintLayout =(ConstraintLayout)findViewById(R.id.cl);
        MyView mv = new MyView(this); //Skapar en ny class/view för att kunna måla bitmap på denna activity
        constraintLayout.addView(mv);
        res = getResources();

        String header = "RULES";
        String rules_txt = "When the button turns green, try to catch the cow as fast as you can!" +"\n\n" + "The delay is different for every level."
                + "\n\n" + "Easy: 2 seconds." + "\n\n" + "Medium: 0.5 - 5 seconds delay." + "\n\n" + "Hard: 0.05 - 2.5 seconds delay." + "\n\n" + "GOOD LUCK";

        rules_header = findViewById(R.id.rules_header);
        rules_text = findViewById(R.id.rules_text);
        btn_reset = findViewById(R.id.btn_reset);

        rules_header.setText(header);
        rules_text.setText(rules_txt);
        btn_reset.setText("RESET HIGHSCORE");

        //Rita bitmap här

        //Kod för att resetta highscore när man klickar på btn_reset.
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            resetScore();


            }


        });









    }

    //Skapar klassen MyView som extendar över activityns view för att visa bitmapbilden genom metoden onDraw som tar canvas som argument.
    class MyView extends View {
        public MyView(Context context) {
            super(context);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.cow_small), 100, 800, null);

        }
    }
    public void resetScore() {
        //Återställer highscore genom att rensa sharedprefs.
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}