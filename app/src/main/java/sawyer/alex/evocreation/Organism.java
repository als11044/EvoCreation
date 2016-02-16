package sawyer.alex.evocreation;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by Alexander on 9/15/2015.
 */
public class Organism {
    int ia;
    int ir;
    int ig;
    int ib;
    int name;
    int age;
    int icolor;
    int ocolor;
    int xMax;
    int yMax;
    int xMin;
    int yMin;
    int mutationChance;
    double deathChance;
    int splitChance;
    double deathRate;
    int arraySize;
    int splitRate;
    String status;
    int rnew;
    int bnew;
    int gnew;
    int or;
    int og;
    int ob;
    int oa;
    int speedR;
    double angle;
    int h;
    int w;
    float birthSize;
    float x;
    float y;
    double speedX;
    double speedY;
    float innerRadius;
    float outerRadius;
    float size;
    int growth;
    int reproduction;
    int longevity;
    int survivability;
    int energyCapture;
    int mutationRate;
    int energyEfficiency;
    int complexity;
    int maturity;
    double energy;
    float wallThickness;
    /*Genes*/
    float maxSize;



    Organism(int setia, int setir, int setig, int setib, int setoa, int setor,
             int setog, int setob, float setx,
             float sety, double setspeedX, double setspeedY, float setmaxSize, float setwallThickness, float setbirthSize,int setspeedR,
             double setangle, int seth, int setw, double setenergy, int setenergyEfficiency,
             int setgrowth, int setreproduction, int setlongevity, int setsurvivability,
             int setenergyCapture, int setmutationRate, int setcomplexity, int setmaturity) {
        ia = setia;
        ir = setir;
        ig = setig;
        ib = setib;
        oa = setoa;
        or = setor;
        og = setog;
        ob = setob;
        x = setx;
        y = sety;
        energyEfficiency = setenergyEfficiency;
        speedX = setspeedX;
        speedY = setspeedY;
        wallThickness = setwallThickness;
        innerRadius = setbirthSize - setwallThickness;
        outerRadius = setbirthSize;
        birthSize = setbirthSize;
        speedR = setspeedR;
        angle = setangle;
        h = seth;
        w = setw;
        energy = setenergy;
        size = setbirthSize;
        maxSize = setmaxSize;
        growth = setgrowth;
        reproduction= setreproduction;
        longevity = setlongevity;
        survivability = setsurvivability;
        energyCapture = setenergyCapture;
        mutationRate = setmutationRate;
        complexity = setcomplexity;
        maturity = setmaturity;

        xMax = 5000;
        yMax = 5000;
        xMin = -5000;
        yMin = -5000;
        icolor = Color.argb(ia,ir,ig,ib);
        ocolor = Color.argb(oa,or,og,ob);
        survivability = 40;

    }
    public int getrnew(){
        return rnew;
    }
    public int getbnew(){
        return bnew;
    }
    public int getgnew(){
        return gnew;
    }
    public float getx(){
        return x;
    }
    public float gety(){
        return y;
    }
    public int getspeedR() {
            return speedR;
    }
    public double getspeedX(){
            return speedX;
    }
    public double getspeedY(){
        return speedY;
    }
    public float getsize(){
        return size;
    }
    public float getbirthSize(){
        return size;
    }
    public float getwallThickness(){
        return wallThickness;
    }
    public void draw(Canvas Canvas, float locX, float locY){
        float screenX = ((w/2) + (x - locX));
        float screenY = ((h/2) - (y - locY));
        if (screenX - outerRadius < w && screenX + outerRadius > 0 && screenY - outerRadius < h && screenY + outerRadius > 0) {
            Paint circlePaint = new Paint();
            Paint innerCirclePaint = new Paint();

            innerCirclePaint.setColor(icolor);
            circlePaint.setColor(ocolor);

            circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            innerCirclePaint.setStyle(Paint.Style.STROKE);
            innerCirclePaint.setStrokeWidth(wallThickness);
            Canvas.drawCircle(screenX, screenY, outerRadius, circlePaint);
            Canvas.drawCircle(screenX, screenY, innerRadius, innerCirclePaint);
        }
    }
    public void move() {
        if (x + outerRadius + speedX >= xMax || x - outerRadius + speedX <= xMin) {
            angle = 3.14 - angle;
        }
        if (y + outerRadius + speedY >= yMax || y - outerRadius + speedY <= yMin) {
            angle = 6.28 - angle;
        }
        speedX = speedR*Math.cos(angle);
        speedY = speedR*Math.sin(angle);
        x += speedX;
        y += speedY;
    }
    public void collision(double colx, double coly){
        double dx = x - colx;
        double dy = y - coly;
        if ( dx <= 0 ){
            angle = Math.atan(dy/dx)+ 3.14;
        }else {
            angle = Math.atan(dy/dx);
        }
    }
    public void molecule(){
        updateSize(size + 20.0f);
        energy = energy + 20;
    }

    public String update(int setarraySize) {
        energy  = energy - 1*energyEfficiency;
        status = "alive";
        arraySize = setarraySize;
        splitRate = 20000;
        Random rnd = new Random();
        age = age + 1;
        energy = energy - 2;
        updateSize(size-2.0f);
        splitChance = (survivability-age)*(rnd.nextInt(100) + 1);
        if (energy<= 0) {
            status = "dead";
            return status;
        }
        else if (size >= 120.0f) {
            status = "split";
            updateSize(size-birthSize);
            return status;
        }
        return status;
    }
    private void updateSize(float newSize){
        size = newSize;
        outerRadius = size;
        innerRadius = size - wallThickness;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){

            // Do what you want
            return true;
        }
        return false;
    }
}
