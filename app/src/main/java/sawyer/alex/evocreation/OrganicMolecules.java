package sawyer.alex.evocreation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alexander on 12/26/2015.
 */
public class OrganicMolecules {
    float x;
    float y;
    int h;
    int w;
    Paint p;
    Bitmap b;
    float rndX;
    float rndY;
    double speedX;
    double speedY;
    int speedR;
    int xMax;
    int yMax;
    int xMin;
    int yMin;
    double angle;

    OrganicMolecules(Paint setp, Bitmap setb, int seth, int setw,int setxMax,int setyMax, int setxMin,int setyMin,int setspeedR, double setspeedX, double setspeedY) {
        Random rnd = new Random();
        double leftLimit = 0.00;
        double rightLimit = 6.28;
        angle = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
        h = seth;
        w = setw;
        p = setp;
        b = setb;
        speedX = setspeedX;
        speedY = setspeedY;
        speedR =setspeedR;
        xMax = setxMax-b.getWidth();
        yMax = setyMax-b.getHeight();
        xMin = setxMin+b.getWidth();
        yMin = setyMin+b.getHeight();
        x = ThreadLocalRandom.current().nextInt(xMin, xMax + 1);
        y = ThreadLocalRandom.current().nextInt(yMin, yMax + 1);
    }
    public float getouterRadius(){
        return (b.getWidth()/2);
    }
    public float getx(){
        return x;
    }
    public float gety(){
        return y;
    }
    public void draw(Canvas Canvas, float locX, float locY) {
        float screenX = ((w / 2) + (x - locX));
        float screenY = ((h / 2) - (y - locY));
        Canvas.drawBitmap(b, screenX - b.getWidth()/2, screenY - b.getHeight()/2, p);
    }
    public void move() {
        if (x + b.getWidth()/2 + speedX >= xMax || x - b.getWidth()/2 + speedX <= xMin) {
            angle = 3.14 - angle;
        }
        if (y + b.getHeight() + speedY >= yMax || y - b.getHeight() + speedY <= yMin) {
            angle = 6.28 - angle;
        }
        speedX = speedR * Math.cos(angle);
        speedY = speedR * Math.sin(angle);
        x += speedX;
        y += speedY;
    }
}
