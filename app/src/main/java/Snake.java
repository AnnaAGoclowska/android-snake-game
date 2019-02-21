package uk.ac.reading.cs2ja16;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Anna on 3/25/2018.
 * Snake that moves around the game area.
 */

public abstract class Snake {
    protected ArrayList<Token> snakeBody; //tokens making snake body
    protected ArrayList<Direction> snakeDirections; //directions of body segments
    protected Token snakeHead;  //token to represent snake head
    protected int snakeLength;  //length of snake body
    protected enum Direction {UP, RIGHT, DOWN, LEFT}; //directions
    protected Direction dir;  //direction of snake head
    protected ArrayList<SnakeBend> bend; //points where snake turns
    protected ArrayList<Integer> counter; //counters for segments
    protected Paint p = new Paint(); // colour of the snake

    /**
     * Constructor for a starting snake
     */
    Snake(){
        snakeLength = 0; //start with snake of length 0
        snakeHead = new Token();
        snakeDirections = new ArrayList<>();
        bend = new ArrayList<>();
        counter = new ArrayList<>();
    }

    /**
     * Move the snake around game area
     */
    public void moveSnake(){
        if (snakeLength >0 && bend.size()>0){ //if snake has a body and changes direction
            for (int i = 0; i< snakeLength; i++){
                for (SnakeBend b: bend){ //at every point where snake changes direction
                    changeSegmentDir(i, b);} //change direction of segment if needed
                moveSegment(snakeBody.get(i), snakeDirections.get(i)); //move the segments in appropriate direction
            }}
        moveSegment(snakeHead,dir); //move the head
    }

    /**
     * Snake collects a token
     * @param t token
     * @return
     */
    public boolean eatToken(Token t){
        //if snake's head touches the token
        if ((snakeHead.posX -t.posX)*(snakeHead.posX - t.posX) + (snakeHead.posY-t.posY)*(snakeHead.posY-t.posY)<=(t.radius + snakeHead.radius)*(t.radius + snakeHead.radius)){
            return true;
        }
        return false;

    }
    /**
     * Snake increases its length
     * @param canvasX width of the canvas
     * @param canvasY height of the canvas
     */
    public void increaseLength(int canvasX, int canvasY){
        snakeBody.add(snakeLength, new Token()); //add new token to the snake
        snakeBody.get(snakeLength).initialisePosition(canvasX, canvasY); //initialise the new token
        if (snakeLength ==0){ //if snake has no body yet
            switch (dir){ //connect the new token to snake's head
                case UP:  //if the snake is moving up, connect the token below the head
                    snakeBody.get(snakeLength).posX = snakeHead.posX;
                    snakeBody.get(snakeLength).posY = snakeHead.posY + snakeHead.radius*2;
                    snakeDirections.add(snakeLength, Direction.UP);
                    break;
                case DOWN: //if the snake is moving down connect the token above the head
                    snakeBody.get(snakeLength).posX = snakeHead.posX;
                    snakeBody.get(snakeLength).posY = snakeHead.posY - snakeHead.radius*2;
                    snakeDirections.add(snakeLength, Direction.DOWN);
                    break;
                case LEFT: //if the snake is moving left, connect the token on the right of the head
                    snakeBody.get(snakeLength).posX = snakeHead.posX + snakeHead.radius*2;
                    snakeBody.get(snakeLength).posY = snakeHead.posY;
                    snakeDirections.add(snakeLength, Direction.LEFT);
                    break;
                case RIGHT: //if the snake is moving right, connect the token on the left of the head
                    snakeBody.get(snakeLength).posX = snakeHead.posX - snakeHead.radius*2;
                    snakeBody.get(snakeLength).posY = snakeHead.posY;
                    snakeDirections.add(snakeLength, Direction.RIGHT);
                    break;
            }}
        else { //if snake already has a body
            int x = snakeBody.get(snakeLength -1).posX; //coordinates of the last token in the body
            int y = snakeBody.get(snakeLength -1).posY;
            int z = 2*(snakeBody.get(snakeLength -1).radius); //diameter of the token
            Direction direction = snakeDirections.get(snakeLength -1);
            switch (direction){ //connect the new token to the last token in the body
                //depending on the direction of the last token in the body, connect the new one from the correct side
                case UP: //connect at the bottom if the token is moving up
                    snakeBody.get(snakeLength).posX = x;
                    snakeBody.get(snakeLength).posY = y+z;
                    snakeDirections.add(snakeLength, Direction.UP);
                    break;
                case DOWN: //connect at the top if the token is moving up
                    snakeBody.get(snakeLength).posX = x;
                    snakeBody.get(snakeLength).posY = y-z;
                    snakeDirections.add(snakeLength, Direction.DOWN);
                    break;
                case LEFT: //connect on the right if the token is moving left
                    snakeBody.get(snakeLength).posX = x+z;
                    snakeBody.get(snakeLength).posY = y;
                    snakeDirections.add(snakeLength, Direction.LEFT);
                    break;
                case RIGHT: //connect on the left if the token is moving right
                    snakeBody.get(snakeLength).posX = x-z;
                    snakeBody.get(snakeLength).posY = y;
                    snakeDirections.add(snakeLength, Direction.RIGHT);
                    break;
            }
        }
        snakeLength++; //increase the length
        for (SnakeBend b: bend){
            if (b.counter>0){
            b.counter++;}} //increment counters to accommodate for the new segment
    }
    /**
     * Change direction of snake's segment
     * @param i number of the segment
     * @param b bend in the snake body
     */
    protected void changeSegmentDir(int i, SnakeBend b){
        //if snake reaches the coordinates of the bend
        if ( snakeBody.get(i).posX == b.x && snakeBody.get(i).posY ==b.y && b.counter>0){
            Direction direction = Direction.UP;
            if (b.dir == 1) direction = Direction.UP; //adjust direction according of the value in the bend
            if (b.dir ==2) direction = Direction.LEFT;
            if (b.dir ==3) direction = Direction.DOWN;
            if (b.dir ==4) direction = Direction.RIGHT;
            snakeDirections.set(i, direction);//set new direction for the segment
            b.counter--; //decrement counter as the segment passes the point of direction change
        }
    }

    /**
     * Remember coordinates at which the snake bends
     * @param x x coordinate
     * @param y y coordinate
     * @param direction direction after change
     */
    public void addBend(int x, int y, int direction) {
        SnakeBend s = new SnakeBend(x, y, direction, snakeLength);
        bend.add(s);
    }

    /**
     * Detect if snake runs into itself.
     * @return
     */
    public boolean runIntoItself(){
        if (snakeLength >2){ //only possible if snake is at least 3 segments long
        for (int i = 2; i< snakeLength; i++){ //the first 2 tokens of body are allowed to touch the head
            Token t = snakeBody.get(i);

            if (snakeHead.detectCollision(t)){ //if snake heads touches the body segment
                return true;
            }
            }}
        return false;

    }
    /**
     * Moves segment of the snake in the given direction
     * @param t token
     * @param d direction of movement
     */
    protected void moveSegment (Token t, Direction d){
        switch (d){ //move according to the direction
            case UP:
                t.posY--;
                break;
            case DOWN:
                t.posY++;
                break;
            case LEFT:
                t.posX--;
                break;
            case RIGHT:
                t.posX++;
        }
    }
    /**
     * Change direction of the snake
     * @param direction new direction
     */
    public void changeDirection(int direction){
        int x = snakeHead.posX;
        int y = snakeHead.posY; //coordinates at which the direction changes
        int d = 0;
        switch (direction){ //direction can only be changed 90 degrees at a time
            case 1:
                if (dir == Direction.LEFT || dir == Direction.RIGHT){
                    dir = Direction.UP;
                    d= 1;
                }
                break;
            case 2:
                if (dir == Direction.UP || dir == Direction.DOWN){
                    dir = Direction.LEFT;
                    d =2;
                }
                break;
            case 3:
                if (dir == Direction.LEFT || dir == Direction.RIGHT){
                    dir = Direction.DOWN;
                    d = 3;
                }
                break;
            case 4:
                if (dir == Direction.UP || dir == Direction.DOWN){
                    dir = Direction.RIGHT;
                    d =4;
                }
                break;
        }
        if (d!=0){ //if direction was changed
            addBend(x,y,d);} //save the point at which direction changes
    }

    /**
     * Detect if the snake collides with another snake.
     * @param s another snake
     * @return
     */
    public boolean hitByOtherSnake(Snake s){
     if (collisionDetected(s.snakeHead)){ //if both snakes hit with each other with the heads
         return true;
     }
     for (Token t: s.snakeBody){ //if the snake runs into the other snake's body
         if (collisionDetected(t)){
             return true;
         }
     }
     for (Token t: this.snakeBody){ //if the other snake runs into this snake's body
         if (s.collisionDetected(t)){
             return true;
         }
     }
     return false; //otherwise
    }

    /**
     * Detect if the snake has run into an obstacle
     * @param o obstacle
     * @return
     */
    public boolean runIntoObstacle (Obstacle o){
        if (collisionDetected(o)){ //if snake hits the obstacle with its head
            return true;
        }
        else return false;
    }

    /**
     * Determines starting position on the canvas. Player and enemy snakes start in different positions
     * @param canvasX width of canvas
     * @param canvasY height of canvas
     */
    public abstract void initialiseSnakePos(int canvasX, int canvasY);

    /**
     * Draw the snake. Different snakes are drawn in different way.
     * @param c
     */
    public abstract void drawSnake(Canvas c);

    /**
     * Detects a collision with another object.
     * @param g
     * @return
     */
    private boolean collisionDetected (GameObject g){
        if ((snakeHead.posX -g.posX)*(snakeHead.posX - g.posX) + (snakeHead.posY-g.posY)*(snakeHead.posY-g.posY)<=(g.radius + snakeHead.radius)*(g.radius + snakeHead.radius)){
            return true; //2 circles collide if the distance between their middles is < sum of their radiuses
        }
        return false;
    }

    /**
     * Detects if the snake hits the edge of the game area.
     * @param g
     * @return
     */
    public boolean runIntoWall (TheGame g){
        if (snakeHead.posY >= g.mCanvasHeight-snakeHead.radius || snakeHead.posY <= snakeHead.radius || snakeHead.posX >= g.mCanvasWidth-snakeHead.radius || snakeHead.posX <=snakeHead.radius){
            return true;
        }
        else return false;
    }


}
