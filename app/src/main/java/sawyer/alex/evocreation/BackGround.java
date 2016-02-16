package sawyer.alex.evocreation;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by Alexander on 10/20/2015.
 */
public class BackGround extends Application{
    int w;
    int h;
    Paint p;
    Bitmap b;
    float locX;
    float locY;
    float x;
    float y;
    String displayText;
    TextView positionView;
    int xMax;
    int yMax;
    int xMin;
    int yMin;
    BackGround(Paint setp, Bitmap setb, float setX, float setY, int setw, int seth,int setxMax,int setyMax,int setxMin,int setyMin){
        p = setp;
        b = setb;
        y = setY;
        x = setX;
        w = setw;
        h = seth;
        xMax = setxMax;
        yMax = setyMax;
        xMin = setxMin;
        yMin = setyMin;
//        w = setw;
//        h = seth;
//        tileW = 750;
//        tileH = 750;
//        sizeW = (int) Math.ceil(w/tileW) + 1;
//        sizeH = (int) Math.ceil(h/tileH)+1;
//        int i;
//        for (i = 0; i < sizeW * sizeH; i++) {
//            map[i] = 1;
//        }
//        sizeHdim = sizeH * tileH;
//        sizeWdim = sizeW * tileW;
    }
    public Bitmap getb(){
        return b;
    }
    public Paint getp(){
        return p;
    }
    public void draw(TextView view, Canvas Canvas,float setlocX, float setlocY){
        positionView = view;
        locX = setlocX;
        locY = setlocY;
        float screenX = ((w / 2) + (x - locX));
        float screenY = ((h / 2) - (y - locY));
        // Improve by only drawing tiles necessary
        Canvas.drawBitmap(b, screenX - b.getWidth()/2, screenY - b.getHeight()/2, p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 + b.getWidth(), screenY - b.getHeight()/2, p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 + 2*b.getWidth(), screenY - b.getHeight()/2, p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 - b.getWidth(), screenY - b.getHeight()/2, p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 - 2*b.getWidth(), screenY - b.getHeight()/2, p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2, screenY - b.getHeight()/2 + b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 + b.getWidth(), screenY - b.getHeight()/2 + b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 + 2*b.getWidth(), screenY - b.getHeight()/2 + b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 - b.getWidth(), screenY - b.getHeight()/2 + b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 - 2*b.getWidth(), screenY - b.getHeight()/2 + b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2, screenY - b.getHeight()/2 + 2*b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 + b.getWidth(), screenY - b.getHeight()/2 + 2*b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 + 2*b.getWidth(), screenY - b.getHeight()/2 + 2*b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 - b.getWidth(), screenY - b.getHeight()/2 + 2*b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 - 2*b.getWidth(), screenY - b.getHeight()/2 + 2*b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2, screenY - b.getHeight()/2 - b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 + b.getWidth(), screenY - b.getHeight()/2 - b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 + 2*b.getWidth(), screenY - b.getHeight()/2 - b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 - b.getWidth(), screenY - b.getHeight()/2 - b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 - 2*b.getWidth(), screenY - b.getHeight()/2 - b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2, screenY - b.getHeight()/2 - 2*b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 + b.getWidth(), screenY - b.getHeight()/2 - 2*b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 + 2*b.getWidth(), screenY - b.getHeight()/2 - 2*b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 - b.getWidth(), screenY - b.getHeight()/2 - 2*b.getHeight(), p);
        Canvas.drawBitmap(b, screenX - b.getWidth()/2 - 2*b.getWidth(), screenY - b.getHeight()/2 - 2*b.getHeight(), p);
//        blocX = -b.getWidth()/2 + locX * -500/10000;
//        blocY = -b.getHeight()/2 + locY * 500/10000;
//        Canvas.drawBitmap(b, blocX, blocY, p);
        displayText = "(X:" + locX + ",Y :" + locY + ")";
        positionView.post(new Runnable() {
            public void run() {
                positionView.setText(displayText);
            }
        });
    }
}
