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
import android.view.TextureView;
import android.widget.FrameLayout;

@SuppressWarnings({"UnusedDeclaration"})
public class GameActivity extends Activity
        implements TextureView.SurfaceTextureListener {
    private TextureView mTextureView;
    private GameActivity.RenderingThread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout content = new FrameLayout(this);

        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);
        mTextureView.setOpaque(false);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        content.addView(mTextureView, new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
        setContentView(content);
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

    private static class RenderingThread extends Thread {
        private final TextureView mSurface;
        private volatile boolean mRunning = true;

        Random rnd = new Random();
        int r = rnd.nextInt(256);
        int g = rnd.nextInt(256);
        int b = rnd.nextInt(256);
        int color = Color.rgb(r, g, b);

        int invColor = Color.rgb((255-r),(255-g),(255-b));

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

            List<Organism> organisms = new ArrayList<Organism>();
            organisms.add(new Organism());

            float x = (w/2);
            float y = (h/2);
            float speedX = 5.0f;
            float speedY = 3.0f;
            float outerRadius = 60.f;
            float innerRadius = 50.0f;

            organisms.add(new Organism(r1,g1,b1,1,x,y,speedX,speedY,
                    outerRadius, innerRadius,w,h));

            while (mRunning && !Thread.interrupted()) {
                final Canvas canvas = mSurface.lockCanvas(null);
                try {
                    canvas.drawColor(color);
                    for (Organism organism : organisms) {
                        organism.draw(canvas);
                    }
                } finally {
                    mSurface.unlockCanvasAndPost(canvas);
                }

                for (Organism organism : organisms) {
                    organism.move();
                }
                try {
                    Thread.sleep(15);
                }

                catch (InterruptedException e) {
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