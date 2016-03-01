package sawyer.alex.evocreation;

/**
 * Created bjdfgkjdfgbsdfy Alexander on 2/23/2016.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BackGround {

    private Bitmap image;
    private int x, y, dx;

    public BackGround(Bitmap res)
    {
        image = res;
    }
    public void update()
    {
        x+=dx;
        if(x<-GamePanel.WIDTH){
            x=0;
        }
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y,null);
        if(x<0)
        {
            canvas.drawBitmap(image, x+GamePanel.WIDTH, y, null);
        }
    }
    public void setVector(int dx)
    {
        this.dx = dx;
    }
}

