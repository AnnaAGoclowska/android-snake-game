package uk.ac.reading.cs2ja16;

//Other parts of the android libraries that we use
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;

import java.util.ArrayList;

public class TheGame extends GameThread{
    private Context m_context; //used to change activity
    private Level level1 = new Level(0,0,0, 5); //game levels
    private Level level2 = new Level(3,0,5, 5);
    private Level level3 = new Level(0,2, 2,5);
    private ArrayList<Level> l = new ArrayList<>(); //arraylist for storing levels
    private Level currentLevel = new Level(0,0,0,0); //current playing level


    /**
     *This is run before anything else, so we can prepare things here
     */
    public TheGame(GameView gameView, Context context) {
        //House keeping
        super(gameView);
        m_context = context; //get context
    }
    /**
     *This is run before anything else, so we can prepare things here
     */
    public TheGame(GameView gameView) {
        //House keeping
        super(gameView);
    }

    /**
     *Set up before the game begins.
     */
    @Override
    public void setupBeginning() {
        l.add(0, level1);
        l.add(1, level2);
        l.add(2, level3); //add levels to the array
        if (customLevel ==false){  //if player plays original levels
        currentLevel = l.get(levelPlayed -1); //choose the correct level according to player's progress
        }
        if (customLevel ==true){ //if palyer plays custom level
            prepareUserGame();
            currentLevel = createLevel(custScore, custObstacle, custEnemyB, custEnemyP); //create level with user's input
            levelPlayed = 3;
        }
        currentLevel.initialiseLevel(mCanvasWidth, mCanvasHeight); //initialise current level
    }

    /**
     * Draw game area on the canvas
     * @param canvas
     */
    @Override
    protected void doDraw(Canvas canvas) {
        //If there isn't a canvas to do nothing
        //It is ok not understanding what is happening here
        if(canvas == null) return;
        //House keeping
        super.doDraw(canvas);
        currentLevel.drawLevel(canvas); //draw the content of the current level
    }

    /**
     * This is run whenever the phone is touched by the user
     */
    @Override
    protected void actionOnTouch(float x, float y) {
        if (mMode==STATE_RUNNING){  //if game is playing
            int direction = 0; //direction player moves
            if (x>=mCanvasWidth/3 && x<=mCanvasWidth*2/3){ //player touches the phone in the middle of the width
                if (y<=mCanvasHeight/3){  //at the top
                    direction =1;
                }
                if (y>=mCanvasHeight*2/3){ //at the bottom
                    direction = 3;
                }
            }
            if (x<mCanvasWidth/3 && y>mCanvasHeight/3 && y<mCanvasHeight*2/3){ //player touches left part of the screen
                direction = 2;
            }
            if (x>mCanvasWidth*2/3 && y>mCanvasHeight/3 && y<mCanvasHeight*2/3){ //player touches right part of the screen
                direction = 4;
            }
            currentLevel.player.changeDirection(direction); //change player's direction accordingly
        }

        if (mMode==STATE_WIN && customLevel ==true){ //if player has won the custom level
                Intent i = new Intent(m_context, MenuActivity.class);
                m_context.startActivity(i);  //go back to main menu


        }
        if (mMode==STATE_WIN && levelPlayed ==4){ //if player has won the game
            SaveScore();
            Intent i = new Intent(m_context, MenuActivity.class);
            m_context.startActivity(i);// go back to main menu
        }
    }

    /**
     *  This is run just before the game "scenario" is printed on the screen
     */
    @Override
    protected void updateGame(float secondsElapsed) {
        if (mMode == GameThread.STATE_RUNNING){ //if the game is running
            currentLevel.play(this); //update the game
        }

        if (currentLevel.levelLost(this)){ //if the player loses the level
            currentLevel.player.loseGame(currentLevel, this); //reset the level
            totalScore--; //decrease score
            setState(GameThread.STATE_LOSE);
        }
        if (currentLevel.levelWon(this )){ //if the player wins the level
            if (levelPlayed <4){ //if player has not completed all levels
            levelPlayed++; //progress to next level
            totalScore = totalScore +(int)score; //update total score
            }
            setState(GameThread.STATE_WIN);
        }

    }

    /**
     * Create a level using user input.
     * @param score target score
     * @param obstacles number of obstacles in the level
     * @param enemies1 number of standard enemies
     * @param enemies2 number of smart enemies
     * @return
     */
    public Level createLevel(int score, int obstacles, int enemies1, int enemies2){
        Level l = new Level(enemies1, enemies2,obstacles,score); //create level
        return l;
    }

    /**
     * Check the user input for level creation is within the permissible range.
     */
    public void prepareUserGame(){
       //target score not allowed to be less than 1 or more than 100
        if (custScore >100){
            custScore = 100;
        }
        if (custScore <1){
            custScore = 1;
        }
        //obstacle number not allowed negative or >30
        if (custObstacle <0){
            custObstacle = 0;
        }
        if (custObstacle >30){
            custObstacle = 30;
        }
        //for each enemy type not allowed negative value or >10
        if (custEnemyB <0){
            custEnemyB = 0;
        }
        if (custEnemyB >10){
            custEnemyB = 10;
        }
        if (custEnemyP <0){
            custEnemyP = 0;
        }
        if (custEnemyP >10){
            custEnemyP = 10;
        }
    }

    /**
     * Compare the score with the highscore list, save it if it's higher than a current highscore value.
     */
    public void SaveScore(){
        SharedPreferences s = m_context.getSharedPreferences(MainActivity.HIGHSCORE, 0);
        SharedPreferences.Editor editor = s.edit();
        int r1 = s.getInt(m_context.getResources().getString(R.string.score1),-1);  //get the scores from shared preferences
        int r2 = s.getInt(m_context.getResources().getString(R.string.score2),-1);
        int r3 = s.getInt(m_context.getResources().getString(R.string.score3),-1);
        int r4 = s.getInt(m_context.getResources().getString(R.string.score4),-1);
        int r5 = s.getInt(m_context.getResources().getString(R.string.score5),-1);
        int choice =-1;
        if(r1==0){  //if there's an empty slot in highscores
            choice=1;
        }else if(r2==-0){
            choice=2;
        }else if(r3==0){
            choice=3;
        }else if (r4==0){
            choice = 4;}
        else if (r5 ==0){
            choice = 5;}
        else{
           if (totalScore>r1){ //new score higher than the top
               choice = 1;
           }
           else if (totalScore>r2){ //new score higher than second top
               choice = r2;
           }
           else if (totalScore>r3){ //higher than third
               choice = r3;
           }
           else if (totalScore>r4){ //higher than fourth
               choice = r4;
           }
           else if (totalScore>r5){ //higher than fifth
               choice = r5;
           }

        }
        switch(choice){ //add the score in the correct position, move down the scores below it
            case 1:
                editor.putInt(m_context.getResources().getString(R.string.score3),r2); //new high score at top, the others move one space down
                editor.putInt(m_context.getResources().getString(R.string.score4),r3);
                editor.putInt(m_context.getResources().getString(R.string.score5),r4);
                editor.putInt(m_context.getResources().getString(R.string.score2),r1);
                editor.putInt(m_context.getResources().getString(R.string.score1),totalScore);
                break;
            case 2:
                editor.putInt(m_context.getResources().getString(R.string.score5),r4);
                editor.putInt(m_context.getResources().getString(R.string.score4),r3);
                editor.putInt(m_context.getResources().getString(R.string.score3),r2);
                editor.putInt(m_context.getResources().getString(R.string.score2),totalScore);
                break;
            case 3:
                editor.putInt(m_context.getResources().getString(R.string.score5),r4);
                editor.putInt(m_context.getResources().getString(R.string.score4),r3);
                editor.putInt(m_context.getResources().getString(R.string.score3),totalScore);
                break;
            case 4:
                editor.putInt(m_context.getResources().getString(R.string.score5),r4);
                editor.putInt(m_context.getResources().getString(R.string.score4),totalScore);
                break;
            case 5:
                editor.putInt(m_context.getResources().getString(R.string.score5),totalScore);
                break;}
        editor.commit();


    }

}

// This file is part of the course "Begin Programming: Build your first mobile game" from futurelearn.com
// Copyright: University of Reading and Karsten Lundqvist
// It is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// It is is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// 
// You should have received a copy of the GNU General Public License
// along with it.  If not, see <http://www.gnu.org/licenses/>.