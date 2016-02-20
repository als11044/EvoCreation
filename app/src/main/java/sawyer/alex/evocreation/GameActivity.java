package sawyer.alex.evocreation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.graphics.SurfaceTexture;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;



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
    protected void onPause(){
        super.onPause();
        mThread.stopRendering();
    }


    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Ignored
    }

    private class RenderingThread extends Thread {
        private final TextureView mSurface;
        private volatile boolean mRunning = true;
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
            float x = 0;
            float y = 0;
            int xMax=5000;
            int yMax=5000;
            int xMin=-5000;
            int yMin=-5000;

            Paint pit = new Paint();
            Bitmap bit= BitmapFactory.decodeResource(getResources(), R.drawable.water1);
            BackGround background = new BackGround(pit,bit,x,y,w,h,xMax,yMax,xMin,yMin);

            List<Organism> organisms = new ArrayList<Organism>();
            List<OrganicMolecules> molecules = new ArrayList<OrganicMolecules>();
            Time time = new Time();

            Paint pit2 = new Paint();
            Bitmap bit2= BitmapFactory.decodeResource(getResources(), R.drawable.atom_nb);
            Bitmap bit2s = Bitmap.createScaledBitmap(bit2, 80, 80, false);

            double speedX = 0.;
            double speedY = 0.;
            float birthSize = 60.0f;
            float wallThickness = 5.0f;
            int speedR = 1;
            float maxSize = 100.0f;
            double leftLimit = 0.00;
            double rightLimit = 6.28;
            double angle = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
            int ir = 243;
            int ig = 231;
            int ib = 203;
            int ia = 40;
            int or = 243;
            int og = 231;
            int ob = 203;
            int oa = 80;
            double energy = 20.00;
            int energyEfficiency = 1;
            int growth = 1;
            int reproductivity = 1;
            int longevity = 100;
            int survivability = 1;
            int energyCapture = 1;
            int mutationRate = 1;
            int complexity = 1;
            int maturity = 1;
            float outerRadius;
            float innerRadius;


            molecules.add(0, new OrganicMolecules(pit2,bit2s,h,w,xMax,yMax,xMin,yMin,speedR,speedX,speedY));
            organisms.add(0, new Organism(ia, ir, ig, ib, oa, or, og, ob, x, y, speedX, speedY,
                    maxSize, wallThickness, birthSize, speedR, angle, h, w, energy, energyEfficiency,
                    growth, reproductivity,longevity,survivability,energyCapture,mutationRate,complexity,maturity));

            int n = 0;
            while (mRunning && !Thread.interrupted()) {
                final Canvas canvas = mSurface.lockCanvas(null);
                int arraySize = organisms.size();
                int arraySizeM = molecules.size();
                int timeNow = time.month;

                try {
                    float joyX = (js.getX())/10;
                    float joyY = (js.getY())/10;
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
                    for (int i = arraySizeM - 1; i >= 0; i--) {
                        molecules.get(i).draw(canvas, locX, locY);
                        molecules.get(i).move();
                    }

                    for (int i = 0; i < arraySize-1; i++) {
                        Organism organism1 = organisms.get(i);
                        for (int j = i + 1; j < arraySize; j++) {
                            Organism organism2 = organisms.get(j);
                            double d = Math.sqrt(((organism1.getx() - organism2.getx()) * (organism1.getx() - organism2.getx()))
                                    + ((organism1.gety() - organism2.gety()) * (organism1.gety() - organism2.gety())));
                            if (d <= (organism1.getsize()+organism2.getsize())) {
                                organism1.collision(organism2.getx(),organism2.gety());
                                organism2.collision(organism1.getx(),organism1.gety());
                            }
                        }
                    }
                    for (int i = 0; i < arraySizeM-1; i++) {
                            OrganicMolecules molecule = molecules.get(i);
                            for (int j = 0; j < arraySize; j++) {
                                Organism organism = organisms.get(j);
                                double d = Math.sqrt(((molecule.getx() - organism.getx()) * (molecule.getx() - organism.getx()))
                                        + ((molecule.gety() - organism.gety()) * (molecule.gety() - organism.gety())));
                                if (d <= (molecule.getouterRadius() + organism.getsize())) {
                                    molecules.remove(i);
                                    i = i -1;
                                    arraySizeM = arraySizeM - 1;
                                    organism.molecule();
                                    break;
                                }
                            }
                    }
                    n = n + 1;
                    if (arraySizeM <= 500 && n == 2) {
                        molecules.add(new OrganicMolecules(pit2, bit2s, h, w, xMax, yMax, xMin, yMin, speedR, speedX, speedY));
                        n = 0;
                    }

                    if (timeNow != timeLast) {
                        timeLast = timeNow;
                        for (int i = arraySize - 1; i >= 0; i--) {
                            String update = organisms.get(i).update(arraySize);
                            if (update.equals("dead")) {
                                organisms.remove(i);
                                deaths = deaths + 1;
                            } else if (update.equals("split")) {
                                leftLimit = 0.00;
                                rightLimit = 6.28;
                                angle = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);

                                speedX = organisms.get(i).getspeedX();
                                speedY = organisms.get(i).getspeedY();
                                speedR = organisms.get(i).getspeedR();
                                birthSize = organisms.get(i).getbirthSize();
                                wallThickness = organisms.get(i).getwallThickness();
                                double addXD = 2*birthSize*Math.cos(angle);
                                double addYD = 2*birthSize*Math.sin(angle);
                                float addX = (float)addXD;
                                float addY = (float)addYD;
                                x = organisms.get(i).getx()+ addX;
                                y = organisms.get(i).gety()+ addY;
                                organisms.add(new Organism(ia, ir, ig, ib, oa, or, og, ob, x, y, speedX, speedY,
                                        maxSize, wallThickness, birthSize, speedR, angle, h, w, energy, energyEfficiency,
                                        growth, reproductivity,longevity,survivability,energyCapture,mutationRate,complexity,maturity));
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
                    mThread.stopRendering();
                }
            }
        }

        void stopRendering() {
            interrupt();
            mRunning = false;
        }
    }
}