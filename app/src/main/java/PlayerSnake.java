package uk.ac.reading.cs2ja16;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Anna on 3/25/2018.
 * Player controlled snake
 */

public class PlayerSnake extends Snake {

    /**
     * Initially snake consists of head and empty body, is placed at the bottom of the screen and moves up.
     * @param canvasX x coordinate of the starting position
     * @param canvasY y coordinate of the starting position
     */
    @Override
    public void initialiseSnakePos(int canvasX, int canvasY) {
       snakeHead.initialisePosition(canvasX, canvasY);
       snakeHead.posX = canvasX/2;  //in the middle of screen width
       snakeHead.posY = canvasY - 3* snakeHead.radius; //at the bottom of the screen
       snakeBody = new ArrayList<>();
        dir = Direction.UP;  //move up
        p.setColor(Color.YELLOW); //player snake will be yellow
    }

    /**
     * Draw the snake on the canvas
     * @param c canvas
     */
    @Override
    public void drawSnake(Canvas c) {
        Paint a = new Paint();
        a.setColor(Color.BLUE); //set colour
        c.drawCircle(snakeHead.posX, snakeHead.posY, snakeHead.radius, a); //draw the head of the snake
        if (snakeLength >0){  //if snake has a body
            for (Token t: snakeBody){
            c.drawCircle(t.posX, t.posY, t.radius, p);} //draw each segment of the body
    }}

    /**
     * On losing the game player starts the level from the beginning.
     */
    public void loseGame(Level l, TheGame g){
        snakeLength = 0;
        snakeBody = new ArrayList<>(); //new snake
        //new enemies
        for (EnemySnake e: l.enemies){
            e.newEnemySnake(g.mCanvasWidth, g.mCanvasHeight);
        }
        for (SmartEnemy s: l.smartEnemies){
            s.newEnemySnake(g.mCanvasWidth, g.mCanvasHeight);
        }
    }


}
