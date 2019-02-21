package uk.ac.reading.cs2ja16;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Anna on 3/25/2018.
 * A token that the user collects to gain points and to increase the snake length.
 */

public class Token extends GameObject {
    private Paint colour;

    /**
     * Constructor
     */
    Token(){
        radius = 5;
        posX = -100;
        posY = -100;
        colour = new Paint();
        colour.setColor(Color.RED); //tokens will be red
    }

    /**
     * Initialise the token.
     * @param canvasX canvas width
     * @param canvasY canvas height
     */
  public void initialisePosition(int canvasX, int canvasY){
       Random r = new Random();
       this.posX = r.nextInt(canvasX-3*radius)+ radius; //puts the token at random position
       this.posY = r.nextInt(canvasY-3*radius)+ radius;
       radius = canvasY/70; //set size

   }

    /**
     * Draw the token on canvas.
     * @param c
     */
    @Override
    public void draw(Canvas c){
        c.drawCircle(posX, posY, radius,colour); //draw a circle in the correct place
    }
}
