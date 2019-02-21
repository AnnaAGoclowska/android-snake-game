package uk.ac.reading.cs2ja16;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Highscore extends Activity {
    TextView score1;
    TextView score2;
    TextView score3;
    TextView score4;
    TextView score5;
    Button toMenu;
    int r1, r2, r3, r4, r5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        SetUp();
    }

    public void SetUp() {
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        SharedPreferences s = getSharedPreferences(MainActivity.HIGHSCORE, 0);
        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);
        score3 = (TextView) findViewById(R.id.score3);
        score4 = (TextView) findViewById(R.id.score4);
        score5 = (TextView) findViewById(R.id.score5);
        toMenu = (Button)findViewById(R.id.toMenu);
        toMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Highscore.this, MenuActivity.class);
                startActivity(i);
            }
        });
        CheckScores(s);

    }
    private void CheckScores (SharedPreferences sp){
        if (sp!=null){
        r1 = sp.getInt(getResources().getString(R.string.score1),-1);
         r2 = sp.getInt(getResources().getString(R.string.score2),-1);
         r3 = sp.getInt(getResources().getString(R.string.score3),-1);
         r4 = sp.getInt(getResources().getString(R.string.score4),-1);
         r5 = sp.getInt(getResources().getString(R.string.score5),-1);}
        score1.setText(Integer.toString(r1));
        score2.setText(Integer.toString(r2));
        score3.setText(Integer.toString(r3));
        score4.setText(Integer.toString(r4));
        score5.setText(Integer.toString(r5));



    }

}

