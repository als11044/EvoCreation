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
    double speedX;
    double speedY;
    float innerRadius;
    float outerRadius;
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
    int survivability;
    int arraySize;
    int splitRate;
    String status;
    int mutationRate;
    int rnew;
    int bnew;
    int gnew;
    int r;
    int g;
    int b;
    int speedR;
    double angle;
    int h;
    int w;


    Organism(int setir, int setig, int setib, int setname, float setx,
             float sety, double setspeedX, double setspeedY,
             float setinnerRadius, float setouterRadius, int setr,
             int setg, int setb, int setspeedR, double setangle, int seth, int setw) {
        ir = setir;
        ig = setig;
        ib = setib;
        name = setname;
        x = setx;
        y = sety;
        speedX = setspeedX;
        speedY = setspeedY;
        innerRadius = setinnerRadius;
        outerRadius = setouterRadius;
        r = setr;
        g = setg;
        b = setb;
        speedR = setspeedR;
        angle = setangle;
        h = seth;
        w = setw;

        xMax = 5000;
        yMax = 5000;
        xMin = -5000;
        yMin = -5000;
        icolor = Color.rgb(ir, ig, ib);
        ocolor = Color.rgb((255-ir),(255-ig),(255-ib));
        survivability = Math.abs(r-ir)+Math.abs(g-ig)+Math.abs(b-ib);

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
        Random rnd = new Random();
        int num0 = rnd.nextInt(2);
        if (num0 == 0 && speedX > 1){
            return speedR+1;
        }
        else if( num0 == 1 && speedR < 10){
            return speedR+1;
        }else{
            return speedR;
        }
    }
    public double getspeedX(){
            return speedX;
    }
    public double getspeedY(){
        return speedY;
    }
    public double getangle(){
        Random rnd = new Random();
        int num0 = rnd.nextInt(2);
        if (num0 == 0){
            return angle-(3.14/6);
        }
        else if( num0 == 1){
            return angle+(3.14/6);
        }else{
            return speedR;
        }
    }
    public float getouterRadius(){
        return outerRadius;
    }

     public void draw(Canvas Canvas, float locX, float locY){
        Paint innerCirclePaint = new Paint();
        innerCirclePaint.setColor(icolor);
        Paint outerCirclePaint = new Paint();
        outerCirclePaint.setColor(ocolor);

         float screenX = ((w/2) + (x - locX));
         float screenY = ((h/2) - (y - locY));
        Canvas.drawCircle(screenX, screenY, outerRadius, outerCirclePaint);
        Canvas.drawCircle(screenX, screenY, innerRadius, innerCirclePaint);
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
    public String update(int setarraySize) {
        status = "alive";
        arraySize = setarraySize;
        deathRate = 350000;
        splitRate = 20000;
        Random rnd = new Random();
        age = age + 1;
        deathChance = (arraySize * (rnd.nextInt(100) + 1) * (survivability+1) * (age));
        splitChance = (survivability-age)*(rnd.nextInt(100) + 1);
        if (deathChance > deathRate) {
            status = "dead";
            return status;
        }
        else if (splitChance < splitRate) {
            status = "split";
            mutationRate = 110;
            mutationChance = (rnd.nextInt(100));
            if (mutationChance < mutationRate){
                int num0 = rnd.nextInt(100);
                int num1 = rnd.nextInt(100);
                int num2 = rnd.nextInt(100);
                if  (num0 > 66) {
                    if ((ir-10) >= 0){
                        rnew = ir - 10;
                    }else {
                        rnew = ir;
                    }
                }else if (num0 < 33) {
                    if ((ir+10) <= 255){
                        rnew = ir + 10;
                    }else {
                        rnew = ir;
                    }
                }else {
                    rnew = ir;
                }
                if  (num1 > 66) {
                    if ((ig-10) >= 0){
                        gnew = ig - 10;
                    }else {
                        gnew = ig;
                    }
                }else if (num1 < 33) {
                    if ((ig+10) <= 255){
                        gnew = ig + 10;
                    }else {
                        gnew = ig;
                    }
                }else {
                    gnew = ig;
                }
                if  (num2 > 66) {
                    if ((ib-10) >= 0){
                        bnew = ib - 10;
                    }else {
                        bnew = ib;
                    }
                }else if (num2 < 33) {
                    if ((ib+10) <= 255){
                        bnew = ib + 10;
                    }else {
                        bnew = ib;
                    }
                }else {
                    bnew = ib;
                }
            }
            return status;
        }
        return status;
    }
}
