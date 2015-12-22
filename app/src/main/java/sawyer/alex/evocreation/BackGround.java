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
    int tileW;
    int tileH;
    int map[];
    int sizeW;
    int sizeH;
    double sizeHdim;
    double sizeWdim;
    int left;
    int top;
    int right;
    Paint p;
    Bitmap b;
    float locX;
    float locY;
    float blocX;
    float blocY;
    String displayText;
    TextView positionView;
    BackGround(Paint setp, Bitmap setb, float setlocX, float setlocY, int setw, int seth){
        p = setp;
        b = setb;
        locX = setlocX;
        locY = setlocY;
        w = setw;
        h = seth;
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
        blocX = -200 + locX * -500/10000;
        blocY = -300 + locY * 500/10000;
        Canvas.drawBitmap(b, blocX, blocY, p);
        displayText = "(X:" + locX + ",Y :" + locY + ")";
        positionView.post(new Runnable() {
            public void run() {
                positionView.setText(displayText);
            }
        });
    }
}
