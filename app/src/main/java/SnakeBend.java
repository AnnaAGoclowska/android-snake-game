package uk.ac.reading.cs2ja16;

/**
 * Created by Anna on 3/26/2018.
 * Used to store the points at which the snake changes direction.
 */

class SnakeBend {
    int x;
    int y;
    int dir;
    int counter;

    /**
     * Constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param z direction
     * @param a counter for segments to pass
     */
    SnakeBend(int x, int y, int z, int a){
        this.x = x;
        this.y = y;
        this.dir = z;
        this.counter = a;
    }
}
