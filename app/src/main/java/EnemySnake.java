package uk.ac.reading.cs2ja16;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anna on 3/29/2018.
 * Enemy snake which moves around the game area changing direction at intervals.
 */

public class EnemySnake extends Snake {

    private int timer; //to know when to change the direction

    /**
     * Initialise the enemy snake. Starts in a random position in the top quarter of the screen.
     *
     * @param canvasX width of canvas
     * @param canvasY height of canvas
     */
    public void initialiseSnakePos(int canvasX, int canvasY) {
        snakeHead.initialisePosition(canvasX, canvasY); //initialise snake head
        Random r = new Random();
        int x = r.nextInt(canvasX - 2 * snakeHead.radius) + snakeHead.radius;
        int y = r.nextInt(canvasY / 4) + snakeHead.radius; //get random coordinates
        snakeHead.posX = x; //place the head in the position
        snakeHead.posY = y;
        snakeBody = new ArrayList<>(); //create empty body
        dir = Direction.DOWN;  //move down
        timer = 20; //set timer
        p.setColor(Color.BLACK); //set colour
    }

    /**
     * Draw the snake on the canvas.
     *
     * @param c
     */
    public void drawSnake(Canvas c) {
        c.drawCircle(snakeHead.posX, snakeHead.posY, snakeHead.radius, p); //draw the head of the snake
        if (snakeLength > 0) {  //if snake has a body
            for (Token t : snakeBody) {
                c.drawCircle(t.posX, t.posY, t.radius, p);
            } //draw each segment of the body
        }
    }

    /**
     * updateTimer counts down to the next direction change.
     *
     * @return
     */
    public boolean updateTimer() {
        if (timer > 0) {
            timer--;
            return false;
        }
        if (timer == 0)
            timer = 20;
        return true;
    }

    /**
     * Create a new snake.
     *
     * @param canvasX canvas width
     * @param canvasY canvas height
     */
    public void newEnemySnake(int canvasX, int canvasY) {
        this.snakeLength = 0; //make an empty snake
        this.snakeBody = new ArrayList<>();
        this.initialiseSnakePos(canvasX, canvasY); //initialise

    }
}
