package uk.ac.reading.cs2ja16;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anna on 3/30/2018.
 * Game level. All objects used in the game are stored inside a level.
 */

public class Level {
    PlayerSnake player; //player character
    ArrayList<EnemySnake> enemies; //arraylist for regular enemies
    private ArrayList<Obstacle> obstacles; //arraylist for obstacles
    ArrayList<SmartEnemy> smartEnemies; //arraylist for smart enemies
    Token token; //token to be collected
    private int scoreToWin; //score needed to win

    /**
     * Constructor. Create the level using parameters provided.
     * @param enemy_num number of regular enemies
     * @param smart_enemy_num number of smart enemies
     * @param obstacle_num number of obstacles
     * @param score score to win
     */
    Level(int enemy_num, int smart_enemy_num, int obstacle_num, int score){
        scoreToWin = score; //set the score needed
        player = new PlayerSnake(); //create the things needed
        enemies = new ArrayList<>();
        obstacles = new ArrayList<>();
        smartEnemies = new ArrayList<>();
        if (enemy_num>0){
        for (int i =0; i<enemy_num; i++){
            enemies.add(i, new EnemySnake());}}
        if (smart_enemy_num>0){
            for (int i = 0; i<smart_enemy_num;i++){
                smartEnemies.add(i, new SmartEnemy());
            }
        }
            if (obstacle_num>0){
        for (int i =0 ; i<obstacle_num; i++){
            obstacles.add(i, new Obstacle());
        }}
        token = new Token();
    }

    /**
     * Initialises the level. Puts all the objects in starting positions.
     * @param canvasX canvas width
     * @param canvasY canvas height
     */
    public void initialiseLevel(int canvasX, int canvasY){
        player.initialiseSnakePos(canvasX, canvasY); //initialise the player
        for (EnemySnake e: enemies){
            e.initialiseSnakePos(canvasX, canvasY); //initialise all enemies
        }
        for (SmartEnemy s: smartEnemies){
            s.initialiseSnakePos(canvasX, canvasY);
        }
        for (Obstacle o: obstacles){   //initialise obstacles
            o.initialisePosition(canvasX, canvasY);
        }
        token.initialisePosition(canvasX, canvasY); //initialise token
    }

    /**
     * Draw the content of the level on canvas.
     * @param c canvas
     */
    public void drawLevel(Canvas c){
        Paint wall = new Paint();
        wall.setColor(Color.BLACK);
        wall.setStrokeWidth(5);
        c.drawLine(0,0, c.getHeight(), 0, wall); //draw the borders around game area
        c.drawLine(c.getWidth(),0, c.getWidth(), c.getHeight(), wall);
        c.drawLine( c.getWidth(),c.getHeight(), 0, c.getHeight(), wall);
        c.drawLine(0, c.getHeight(), 0, 0, wall);
        player.drawSnake(c); //draw the player
        token.draw(c); //draw the token
        for (EnemySnake e: enemies){ //draw enemies
            e.drawSnake(c);
        }
        for (SmartEnemy s: smartEnemies){
            s.drawSnake(c);
        }
        for (Obstacle o: obstacles){ //draw obstacles
            o.draw(c);
        }
    }

    /**
     * Update the game. All game actions happen here.
     * @param g
     */
    public void play(TheGame g){
        for (EnemySnake e: enemies){ //move each enemy
            e.moveSnake();
            e.moveSnake();
            e.moveSnake();
            e.moveSnake();
            if (e.updateTimer()){
                Random r = new Random(); //regular enemies change direction at random
                int a = r.nextInt(4)+1;
                e.changeDirection(a);
            }
            if (e.runIntoWall(g) || e.runIntoItself()){ //if enemy runs into a wall or itself, it starts from beginning again.
                e.newEnemySnake(g.mCanvasWidth, g.mCanvasHeight);
            }
            for (Obstacle o: obstacles){ //if enemy runs into obstacle it also starts from the beginning
                if (e.runIntoObstacle(o)){
                    e.newEnemySnake(g.mCanvasWidth, g.mCanvasHeight);
                }
            }
            if (e.eatToken(token)){ //if enemy collects a token
                token.initialisePosition(g.mCanvasWidth,g.mCanvasHeight); //make new token
                e.increaseLength(g.mCanvasWidth, g.mCanvasHeight); //enemy grows
                g.updateScore(-1); //decrease player's score
            }
        }
        for (SmartEnemy s: smartEnemies){ //smart enemies do everything in the same way as regular enemies but don't move randomly
            s.moveSnake(this);
            s.moveSnake(this);
            s.moveSnake(this);

            if (s.runIntoWall(g) || s.runIntoItself()){
                s.newEnemySnake(g.mCanvasWidth, g.mCanvasHeight);
            }
            for (Obstacle o: obstacles){
                if (s.runIntoObstacle(o)){
                    s.newEnemySnake(g.mCanvasWidth, g.mCanvasHeight);
                }
            }
            if (s.eatToken(token)){
                token.initialisePosition(g.mCanvasWidth,g.mCanvasHeight);
                s.increaseLength(g.mCanvasWidth, g.mCanvasHeight);
                g.updateScore(-1);
            }
        }
        player.moveSnake();
        player.moveSnake();
        player.moveSnake();
        player.moveSnake(); //move the player
        player.moveSnake();
        if (player.eatToken(token)){ //if player collects token
            token.initialisePosition(g.mCanvasWidth, g.mCanvasHeight); //new token appears
            g.updateScore(1); //player gains point
            player.increaseLength(g.mCanvasWidth, g.mCanvasHeight); //snake length increases
        }

    }

    /**
     * Player wins the level if player's score is equal to the target score.
     * @param g TheGame
     * @return
     */
    public boolean levelWon (TheGame g){
        if (g.score == scoreToWin){
            return true;
        }
        else return false;
    }

    /**
     * Player loses the level.
     * @param g
     * @return
     */
    public boolean levelLost (TheGame g) {
        if (player.runIntoWall(g)){ //level lost if player runs into the wall
            return true;
        }
        for (EnemySnake e : enemies) { //or is hit by another snake
            if (player.hitByOtherSnake(e)) {
                return true;
            }
        }
        for (SmartEnemy s: smartEnemies){
            if (player.hitByOtherSnake(s)){
                return true;
            }
        }
        for (Obstacle o: obstacles){ //or runs into obstacle
            if (player.runIntoObstacle(o)){
                return true;
            }
        }
        if (player.runIntoItself()){ //or runs into itself
            return true;
        }
        return false;
    }

    }

