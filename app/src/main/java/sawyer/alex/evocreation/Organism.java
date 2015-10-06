package sawyer.alex.evocreation;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Alexander on 9/15/2015.
 */
public class Organism {
    /* color */
    int ir;
    int ig;
    int ib;
    int name;
    float x;
    float y;
    float speedX;
    float speedY;
    float innerRadius;
    float outerRadius;
    float age;
    int icolor;
    int ocolor;
    int w;
    int h;
    int mutationChance;
    double deathChance;
    int splitChance;
    double deathRate;
    int survivability;
    int arraySize;


    Organism(int setir, int setig, int setib, int setname, float setx,
             float sety, float setspeedX, float setspeedY,
             float setinnerRadius, float setouterRadius,
             int setw, int seth) {
        ir = setir;
        ib = setig;
        ig = setib;
        name = setname;
        x = setx;
        y = sety;
        speedX = setspeedX;
        speedY = setspeedY;
        innerRadius = setinnerRadius;
        outerRadius = setouterRadius;
        w = setw;
        h = seth;
        icolor = Color.rgb(ir, ig, ib);
        ocolor = Color.rgb((255-ir),(255-ig),(255-ib));

    }

    public void setx(float setx){
        x = setx;
    }
    public void sety(float sety){
        y = sety;
    }
    public void setspeedX (float setspeedX) {
        speedX = setspeedX;
    }
    public void setspeedY (float setspeedY) {
        speedY = setspeedY;
    }
    public void setinnerRadius (float setinnerRadius) {
        innerRadius = setinnerRadius;
    }
    public void setouterRadius (float setouterRadius) {
        outerRadius = setouterRadius;
    }
    public void draw(Canvas Canvas){
        Paint innerCirclePaint = new Paint();
        innerCirclePaint.setColor(icolor);
        Paint outerCirclePaint = new Paint();
        outerCirclePaint.setColor(ocolor);
        Canvas.drawCircle(x, y, outerRadius, outerCirclePaint);
        Canvas.drawCircle(x, y, innerRadius, innerCirclePaint);
    }
    public void move() {
        if (x + outerRadius + speedX >= w || x - outerRadius + speedX <= 0.0f) {
            speedX = -speedX;
        }
        if (y + outerRadius + speedY >= h || y - outerRadius + speedY <= 0.0f) {
            speedY = -speedY;
        }

        x += speedX;
        y += speedY;
    }
    public void update(int setarraySize) {
        arraySize = setarraySize;
        deathRate = (3*arraySize)/(Math.sqrt(16*(arraySize)^2+100000*arraySize));
        Random rnd = new Random();
        age = age +1;
        deathChance = (rnd.nextInt(100))/100;
        if (deathChance < deathRate) {

        }
    }
}
