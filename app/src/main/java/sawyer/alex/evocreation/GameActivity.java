package sawyer.alex.evocreation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private TextView positionView;
    private JoyStickClass js;
    private GameActivity.RenderingThread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        timeView = (TextView) findViewById(R.id.timeView);
        positionView = (TextView) findViewById(R.id.positionView);
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
            float locX = 0;
            float locY = 0;

            Paint pit = new Paint();
            Bitmap bit= BitmapFactory.decodeResource(getResources(), R.drawable.water1);
            BackGround background = new BackGround(pit,bit,locX,locY,w,h);

            List<Organism> organisms = new ArrayList<Organism>();
            Time time = new Time();

            float x = 0;
            float y = 0;
            double speedX = 5.;
            double speedY = 3.;
            float outerRadius = 60.0f;
            float innerRadius = 55.0f;
            int speedR = 5;
            double angle = 3.14 / 4;

            organisms.add(0, new Organism(r1, g1, b1, 1, x, y, speedX, speedY,
                    innerRadius, outerRadius, r, g, b, speedR, angle, h, w));

            while (mRunning && !Thread.interrupted()) {
                final Canvas canvas = mSurface.lockCanvas(null);
                int arraySize = organisms.size();
                int timeNow = time.month;
                try {
                    int joyX = (js.getX())/10;
                    int joyY = (js.getY())/10;
                    if ( locX + (w/2) + joyX <= 5000 && locX - (w/2) + joyX >= -5000){
                        locX = locX + joyX;
                    }
                    if ( locY + (h/2) - joyY <= 5000 && locY - (h/2) - joyY >= -5000){
                        locY = locY - joyY;
                    }
//                    canvas.drawBitmap(bit, 0, 0, pit);
                    background.draw(positionView, canvas, locX, locY);
                    for (int i = arraySize - 1; i >= 0; i--) {
                        organisms.get(i).draw(canvas, locX, locY);
                        organisms.get(i).move();
                    }
                    for (int i = 0; i < arraySize-1; i++) {
                        Organism organism1 = organisms.get(i);
                        for (int j = i + 1; j < arraySize; j++) {
                            Organism organism2 = organisms.get(j);
                            double d = Math.sqrt(((organism1.getx() - organism2.getx()) * (organism1.getx() - organism2.getx()))
                                    + ((organism1.gety() - organism2.gety()) * (organism1.gety() - organism2.gety())));
                            if (d <= (organism1.getouterRadius()+organism2.getouterRadius())) {
                                organism1.collision(organism2.getx(),organism2.gety());
                                organism2.collision(organism1.getx(),organism1.gety());
                            }
                        }
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