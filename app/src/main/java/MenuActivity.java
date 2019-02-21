package uk.ac.reading.cs2ja16;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Starting game screen.
 */
public class MenuActivity extends Activity {
    Button newGame; //buttons
    Button highScores;
    Button help;
    Button customLevel;

    /**
     * Run when the activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
       newGame = (Button)findViewById(R.id.newGame); //set buttons
       newGame.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(MenuActivity.this, MainActivity.class);
               startActivity(i);
           }
       });
       highScores = (Button)findViewById(R.id.highScore);
       highScores.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(MenuActivity.this, Highscore.class);
               startActivity(i);

           }
       });
       help = (Button)findViewById(R.id.help);
       help.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(MenuActivity.this, InstructionsActivity.class);
               startActivity(i);
           }
       });
       customLevel = (Button)findViewById(R.id.customLevel);
        customLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, LevelCreation.class);
                startActivity(i);
            }
        });
    }
}
