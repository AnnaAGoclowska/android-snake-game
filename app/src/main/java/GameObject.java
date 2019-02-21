package uk.ac.reading.cs2ja16;

import android.graphics.Canvas;

/**
 * Created by Anna on 3/25/2018.
 * Abstract class for various game objects.
 */

public abstract class GameObject {
    int posX, posY; //coordinates
    int radius; //size

    /**
     * Constructor. Initially places object in position (-100, -100).
     */
    GameObject() {
        radius = 5;
        posX = -100;
        posY = -100;
    }

    /**
     * Abstract method for drawing the object.
     * @param c canvas
     */
    public abstract void draw(Canvas c);

    /**
     * Detect collision between 2 game objects.
     * @param o
     * @return
     */
    public boolean detectCollision(GameObject o) {
        if ((this.posX - o.posX) * (this.posX - o.posX) + (this.posY - o.posY) * (this.posY - o.posY) <= (o.radius + this.radius) * (o.radius + this.radius)) {
            return true; //objects collide if the distance between their middles is smaller than the sum of their radiuses
        }
        return false;


    }

    /**
     * Initialises the position in the game area
     * @param canvasX width of the game area
     * @param canvasY height of the game area
     */
    public abstract void initialisePosition(int canvasX, int canvasY);

}