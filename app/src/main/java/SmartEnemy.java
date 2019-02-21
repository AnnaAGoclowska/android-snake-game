package uk.ac.reading.cs2ja16;

import android.graphics.Color;

/**
 * Created by Anna on 4/9/2018.
 * Enemy snake which follows the tokens.
 */

public class SmartEnemy extends EnemySnake {


    public void initialiseSnakePos(int canvasX, int canvasY) {
        super.initialiseSnakePos(canvasX, canvasY);
        p.setColor(Color.MAGENTA); //set colour to pink
    }

    /**
     * This snake changes direction depending on where the token is relative to its head.
     * @param l
     */
    private void changeDirection(Level l){
        int x = snakeHead.posX;
        int y = snakeHead.posY;
        int d = 0;
        if (l.token.posX < snakeHead.posX && l.token.posY < snakeHead.posY) { //token in upper left quadrant
            if (dir == Direction.RIGHT){ //if the snake is moving right, changes direction to up
                dir = Direction.UP;
                d= 1;
            }
            if (dir ==Direction.DOWN){ //if the snake was moving down, changes direction to left
                dir = Direction.LEFT;
                d =2;
            }

        }
        if (l.token.posX > snakeHead.posX && l.token.posY < snakeHead.posY){ //token in upper right quadrant
            if (dir == Direction.DOWN){
                dir = Direction.RIGHT; //if snake was moving down, changes direction to right
                d =4;
            }
            if (dir == Direction.LEFT){ //if snake was moving left, changes direction to up
                dir = Direction.UP;
                d =1;
            }

        }
        if (l.token.posX > snakeHead.posX && l.token.posY > snakeHead.posY){ //token in lower right quadrant
            if (dir == Direction.UP){
                dir = Direction.RIGHT; //if snake was moving up, changes direction to right
                d=4;
            }
            if (dir == Direction.LEFT){ //if snake was moving left, changes direction to down
                dir = Direction.DOWN;
                d=3;
            }
        }
        if (l.token.posX < snakeHead.posX && l.token.posY > snakeHead.posY){ //token in lower left quadrant
            if (dir == Direction.UP){
                dir = Direction.LEFT; //if snake was moving up, changes direction to left
                d=2;
            }
            if (dir == Direction.RIGHT){ //if snake was moving right, changes direction to down
                dir = Direction.DOWN;
                d=3;
            }
        }
        if (Math.abs(l.token.posX - snakeHead.posX)<5 && l.token.posY < snakeHead.posY){ //token directly above
            if (dir == Direction.RIGHT || dir == Direction.LEFT){
                dir = Direction.UP; // change direction to up from right or left
                d=1;
            }
        }
        if (Math.abs(l.token.posX - snakeHead.posX)<5 && l.token.posY > snakeHead.posY){ //token directly below
            if (dir == Direction.RIGHT || dir == Direction.LEFT){
                dir = Direction.DOWN; // change direction to down from right or left
                d=3;
            }
        }
        if (l.token.posX > snakeHead.posX && Math.abs(l.token.posY - snakeHead.posY)<5){ //token directly to the right
            if (dir == Direction.UP || dir == Direction.DOWN){
                dir = Direction.RIGHT; //change direction to right from up or down
                d=4;
            }
        }
        if (l.token.posX == snakeHead.posX && Math.abs(l.token.posY - snakeHead.posY)<5){ //token directly to the left
            if (dir == Direction.UP || dir == Direction.DOWN){
                dir = Direction.LEFT; //change direction to left from up or down
                d=2;
            }
        }
        if (d!=0){ //if direction was changed
            addBend(x,y,d); //add new bend
        }

    }

    /**
     * Moves the snake changing direction if needed.
     * @param l game level
     */
    public void moveSnake(Level l) {
        changeDirection(l);
        moveSnake();
    }

    }

