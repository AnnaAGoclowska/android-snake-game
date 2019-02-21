package uk.ac.reading.cs2ja16;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which allows the player to create a custom level
 */
public class LevelCreation extends Activity {
    EditText obstaclesNum; //number of obstacles
    EditText pointsToWin; //points needed to win
    EditText blackEnemies; //numbers of enemies of each type
    EditText pinkEnemies;
    Button ready; //button to create level
    Button clear; //button to clear the fields
    Button back; //button to go back to main menu
    TextView instruction; //instruction on how to create a level
    int targetScore=0, obstacles=0, enemiesB=0, enemiesP=0; //values entered by the user will be stored here

    /**
     * Run when the activity is started.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_creation);
        setUp();
    }

    /**
     * Function to set up the buttons and edit texts used.
     */
    private void setUp(){
        pointsToWin = (EditText)findViewById(R.id.score);
        obstaclesNum = (EditText)findViewById(R.id.obstacles);
        blackEnemies = (EditText)findViewById(R.id.black_enemies);
        pinkEnemies = (EditText)findViewById(R.id.pink_enemies); //set up the edit texts

        ready = (Button)findViewById(R.id.ready); //set up ready button
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //on pressing the button
                getValues(); //user values are stored into the variables
                Intent i = new Intent (LevelCreation.this, MainActivity.class);
                i.putExtra("custom game", true); //send the data inside the intent
                i.putExtra("obstacles", obstacles);
                i.putExtra("score", targetScore);
                i.putExtra("blackEnemies", enemiesB);
                i.putExtra("pinkEnemies", enemiesP);
                startActivity(i); //go to the main activity
            }
        });
        clear = (Button)findViewById(R.id.clear_all); //set up clear button
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointsToWin.setText(""); //clear all the fields
                obstaclesNum.setText("");
                blackEnemies.setText("");
                pinkEnemies.setText("");
            }
        });
        back = (Button)findViewById(R.id.menu);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LevelCreation.this, MenuActivity.class);
                startActivity(i);

            }
        });
        instruction = (TextView)findViewById(R.id.instruction);

    }

    /**
     * Store the user entered values in variables.
     */
    private void getValues(){
        if (!TextUtils.isEmpty(pointsToWin.getText()))
        {
            targetScore = Integer.parseInt(pointsToWin.getText().toString());
        }
        if (!TextUtils.isEmpty(obstaclesNum.getText()))
        {
            obstacles = Integer.parseInt(obstaclesNum.getText().toString());
        }
        if (!TextUtils.isEmpty(blackEnemies.getText())) {
            enemiesB = Integer.parseInt(blackEnemies.getText().toString());
        }
        if (!TextUtils.isEmpty(pinkEnemies.getText())) {
            enemiesP = Integer.parseInt(pinkEnemies.getText().toString());
        }
    }

}

