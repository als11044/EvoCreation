package sawyer.alex.evocreation;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.graphics.SurfaceTexture;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class GameActivity extends Activity
        implements TextureView.SurfaceTextureListener {
    private TextureView mTextureView;
    private RelativeLayout layout_joystick;
    private ImageView image_joystick, image_border;
    private TextView timeView;
    private JoyStickClass js;
    private GameActivity.RenderingThread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        timeView = (TextView) findViewById(R.id.timeView);
        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);
        mTextureView = (TextureView) findViewById(R.id.textureView);
        mTextureView.setSurfaceTextureListener(this);

        js = new JoyStickClass(getApplicationContext()
                , layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        layout_joystick.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                   /* textView1.setText("X : " + String.valueOf(js.getX()));
                    textView2.setText("Y : " + String.valueOf(js.getY()));
                    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
                    textView4.setText("Distance : " + String.valueOf(js.getDistance()));*/

                    int direction = js.get8Direction();
                    if (direction == JoyStickClass.STICK_UP) {
                       /* textView5.setText("Direction : Up");*/
                    } else if (direction == JoyStickClass.STICK_UPRIGHT) {
                       /* textView5.setText("Direction : Up Right");*/
                    } else if (direction == JoyStickClass.STICK_RIGHT) {
                        /*textView5.setText("Direction : Right");*/
                    } else if (direction == JoyStickClass.STICK_DOWNRIGHT) {
                       /* textView5.setText("Direction : Down Right");*/
                    } else if (direction == JoyStickClass.STICK_DOWN) {
                       /* textView5.setText("Direction : Down");*/
                    } else if (direction == JoyStickClass.STICK_DOWNLEFT) {
/*                        textView5.setText("Direction : Down Left");*/
                    } else if (direction == JoyStickClass.STICK_LEFT) {
/*                        textView5.setText("Direction : Left");*/
                    } else if (direction == JoyStickClass.STICK_UPLEFT) {
/*                        textView5.setText("Direction : Up Left");*/
                    } else if (direction == JoyStickClass.STICK_NONE) {
/*                        textView5.setText("Direction : Center");*/
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
/*                    textView1.setText("X :");
                    textView2.setText("Y :");
                    textView3.setText("Angle :");
                    textView4.setText("Distance :");
                    textView5.setText("Direction :");*/
                }
                return true;
            }
        });
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mThread = new RenderingThread(mTextureView);
        mThread.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mThread != null) mThread.stopRendering();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Ignored
    }

    private class RenderingThread extends Thread {
        private final TextureView mSurface;
        private volatile boolean mRunning = true;

        Random rnd = new Random();
        int r = rnd.nextInt(256);
        int g = rnd.nextInt(256);
        int b = rnd.nextInt(256);
        int color = Color.rgb(r, g, b);

        int invColor = Color.rgb((255 - r), (255 - g), (255 - b));

        int r1 = rnd.nextInt(256);
        int g1 = rnd.nextInt(256);
        int b1 = rnd.nextInt(256);
        int color1 = Color.rgb(r1, g1, b1);


        public RenderingThread(TextureView surface) {
            mSurface = surface;
        }

        @Override
        public void run() {
            int w = mSurface.getWidth();
            int h = mSurface.getHeight();
            int timeLast = 0;
            int deaths = 0;
            int births = 0;

            List<Organism> organisms = new ArrayList<Organism>();
            Time time = new Time();

            float locX = 0;
            float locY = 0;
            float x = 0;
            float y = 0;
            double speedX = 5.;
            double speedY = 3.;
            float outerRadius = 30.0f;
            float innerRadius = 20.0f;
            int speedR = 5;
            double angle = 3.14 / 4;

            organisms.add(0, new Organism(r1, g1, b1, 1, x, y, speedX, speedY,
                    innerRadius, outerRadius, r, g, b, speedR, angle, h, w));

            while (mRunning && !Thread.interrupted()) {
                final Canvas canvas = mSurface.lockCanvas(null);
                int arraySize = organisms.size();
                int timeNow = time.month;
                try {
                    locX = locX + (js.getX())/10;
                    locY = locY - (js.getY())/10;
                    canvas.drawColor(color);
                    for (int i = arraySize - 1; i >= 0; i--) {
                        organisms.get(i).draw(canvas, locX, locY);
                        organisms.get(i).move();
                    }

                    if (timeNow != timeLast) {
                        timeLast = timeNow;
                        for (int i = arraySize - 1; i >= 0; i--) {
                            String update = organisms.get(i).update(arraySize);
                            if (update.equals("dead")) {
                                organisms.remove(i);
                                deaths = deaths + 1;
                            } else if (update.equals("split")) {
                                r1 = organisms.get(i).getrnew();
                                g1 = organisms.get(i).getgnew();
                                b1 = organisms.get(i).getbnew();
                                x = organisms.get(i).getx();
                                y = organisms.get(i).gety();
                                speedX = organisms.get(i).getspeedX();
                                speedY = organisms.get(i).getspeedY();
                                speedR = organisms.get(i).getspeedR();
                                angle = organisms.get(i).getangle();
                                organisms.add(new Organism(r1, g1, b1, 1, x, y, speedX, speedY,
                                        innerRadius, outerRadius, r, g, b, speedR, angle, h, w));
                                births = births + 1;
                            }
                        }
                    }
                } finally {
                    mSurface.unlockCanvasAndPost(canvas);
                }
                try {
                    Thread.sleep(15);
                    time.update(timeView, arraySize, births, deaths);
                } catch (InterruptedException e) {
                    // Interrupted
                }
            }
        }

        void stopRendering() {
            interrupt();
            mRunning = false;
        }
    }
}