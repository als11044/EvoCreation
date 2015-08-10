package sawyer.alex.evocreation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import java.util.Random;


public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicsView(this));
    }
        public class GraphicsView extends View {
            private Path circlefill;
            private Path circlestroke;
            private Paint cPaint;
            private Paint tPaint;

            Random rnd = new Random();
            int r = rnd.nextInt(256);
            int g = rnd.nextInt(256);
            int b = rnd.nextInt(256);
            int color = Color.rgb(r, g, b);

            int invcolor = Color.rgb((255-r),(255-g),(255-b));

            int r1 = rnd.nextInt(256);
            int g1 = rnd.nextInt(256);
            int b1 = rnd.nextInt(256);
            int color1 = Color.rgb(r1, g1, b1);

            public GraphicsView(Context context) {
                super(context);

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;

                circlestroke = new Path();
                circlestroke.addCircle((width/2), (height/2), 100, Path.Direction.CW);
                cPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                cPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                cPaint.setColor(invcolor);
                cPaint.setStrokeWidth(25);

                circlefill = new Path();
                circlefill.addCircle((width/2), (height/2), 100, Path.Direction.CW);
                tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                tPaint.setStyle(Paint.Style.FILL);
                tPaint.setColor(color1);
                tPaint.setStrokeWidth(25);

                setBackgroundColor(color);
            }

            @Override
            protected void onDraw(Canvas canvas) {
                canvas.drawPath(circlestroke, cPaint);
                canvas.drawPath(circlefill, tPaint);
            }
        }

}