package uk.ac.reading.cs2ja16;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Anna on 3/29/2018.
 * Obstacle which the player must avoid
 */

public class Obstacle extends GameObject {

    /**
     * Draw the obstacle on the canvas
     * @param c
     */
    public void draw(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.GRAY); //obstacles will be multicolored
        c.drawCircle(posX, posY, radius,p);
        p.setColor(Color.BLACK);
        c.drawCircle(posX, posY, radius*3/4, p);
        p.setColor(Color.WHITE);
        c.drawCircle(posX, posY, radius/2, p);

    }

    /**
     * Place the obstacle in a random point in game area.
     * @param canvasX
     * @param canvasY
     */
    public void initialisePosition(int canvasX, int canvasY) {
        Random r = new Random();
        this.posX = r.nextInt(canvasX-2*radius);
        this.posY = r.nextInt(canvasY-2*radius);
        radius = r.nextInt(canvasY/30) + 10; //random size
    }
}
