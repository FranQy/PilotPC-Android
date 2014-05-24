package com.example.socketclient;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by franqy on 24.10.13.
 */
public class touchPad extends Activity {
    Activity activity;
    TextView blad;
    //PrintWriter out;
    ObjectOutputStream out;
    OutputStream os;
    public boolean active = false;
    public static TCP_Data data;

    boolean wysylaj = true;

    public touchPad(Activity activity) {
        this.activity = activity;
        out = null;
        data = new TCP_Data();

        blad = (TextView) this.activity.findViewById(R.id.textView1);
        blad.setText("touchpad");

        //ImageView mysz = (ImageView) this.activity.findViewById(R.id.imageMysz);

        LinearLayout touchArea = (LinearLayout) this.activity.findViewById(R.id.touchArea);
        LinearLayout scrollArea = (LinearLayout) this.activity.findViewById(R.id.scrollArea);

        touchArea.setOnTouchListener(new View.OnTouchListener() {
            float mPreviousX = 0;


            float mPreviousY = 0;
            float oldX = 0;
            float oldY = 0;
            private Timer longpressTimer;
            private boolean longClicked = false;
            private boolean returnState = true;
            long startTime = 0;


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int pointerCount = motionEvent.getPointerCount();
/*float fingerOneX=0;
                float fingerTwoX=0;

                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                    {
                        for(int i = 0; i < pointerCount; ++i)
                        {
                            int pointerIndex = i;
                            int pointerId = motionEvent.getPointerId(pointerIndex);

                            if(pointerId == 0)
                            {

                                fingerOneX = motionEvent.getX(pointerIndex);
                                //fingerOneY = event.getY(pointerIndex);
                            }
                            if(pointerId == 1)
                            {

                                fingerTwoX = motionEvent.getX(pointerIndex);
                               // fingerTwoY = event.getY(pointerIndex);
                            }
                    }
                }
                }
                    pilot.blad.setText(String.valueOf(fingerOneX)+" \r"+String.valueOf(fingerTwoX));*/


                float x = motionEvent.getX();
                float y = motionEvent.getY();

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                boolean prawy = false;


                if (active) {

                    int actionMask = motionEvent.getActionMasked();


                    switch (actionMask) {


                        case MotionEvent.ACTION_DOWN: {
                            returnState = true;
                            startTime = System.currentTimeMillis();

                            oldX = x;
                            oldY = y;

                            longClicked = false;

                            longpressTimer = new Timer();

                            longpressTimer.schedule(new TimerTask() {


                                @Override
                                public void run() {
                                    longClicked = true;
                                    Pilot.przyciski1.click();
                                }
                            }, 500);

                            break;

                        }

                        case MotionEvent.ACTION_POINTER_1_UP: {

                            prawy = true;

                            longpressTimer.cancel();
                            data.clean();
                            data.mouse = TCP_Data.touchedTYPE.PPM;
                            send();
                            blad.setText("prawy");
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            longpressTimer.cancel();
                            returnState = false;
                            if ((int) Math.sqrt(dx * dx + dy * dy) <= 1 && System.currentTimeMillis() - startTime < 100 && !prawy) {

                                // out.println("click");
                                data.clean();
                                data.mouse = TCP_Data.touchedTYPE.LPM;

                                send();
                                blad.setText("click");
                            } else if (longClicked) {
                                data.mouse = TCP_Data.touchedTYPE.UP;
                                send();
                            }
                            prawy = false;
                            break;


                        }
                        case MotionEvent.ACTION_MOVE: {
                            int s = (int) Math.sqrt(dx * dx + dy * dy);
                            boolean isActuallyMoving = s >= 2;

                            if (isActuallyMoving) {

                                longpressTimer.cancel();
                            }


                            if (longClicked) {
                                blad.setText("long  " + String.valueOf(dx));
                                data.mouse = TCP_Data.touchedTYPE.LONG;
                                data.touchpadX = (int) dx;
                                data.touchpadY = (int) dy;
                                send();

                            } else if (System.currentTimeMillis() - startTime > 50) {
                                blad.setText(String.valueOf(dx) + " \r" + String.valueOf(dy));
                                if (dx < -0.5) {
                                    dx -= 1;
                                } else if (dx > 0.5) {
                                    dx += 1;
                                }
                                if (dy < -0.5) {
                                    dy -= 1;
                                } else if (dy > 0.5) {
                                    dy += 1;
                                }


                                data.mouse = TCP_Data.touchedTYPE.NORMAL;
                                data.touchpadX = (int) dx;
                                data.touchpadY = (int) dy;


                                send();

                            }


                            break;
                        }

                    }


                }

                mPreviousX = x;
                mPreviousY = y;


                return returnState;

            }


        });


        scrollArea.setOnTouchListener(new View.OnTouchListener() {


            float mPreviousY = 0;


            int r = 0;
            private boolean returnState = true;


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int pointerCount = motionEvent.getPointerCount();


                float y = motionEvent.getY();


                float dy = y - mPreviousY;

                boolean prawy = false;


                if (active) {

                    int actionMask = motionEvent.getActionMasked();


                    switch (actionMask) {


                        case MotionEvent.ACTION_DOWN: {
                            returnState = true;


                            break;

                        }


                        case MotionEvent.ACTION_UP: {

                            returnState = false;


                            break;


                        }
                        case MotionEvent.ACTION_MOVE: {


                            if (dy < -0.5) {
                                dy -= 1;
                            } else if (dy > 0.5) {
                                dy += 1;
                            }
                            //  out.println((int)dx);
                            if (r == 1) {
                                blad.setText(String.valueOf(dy));

                                data.mouse = TCP_Data.touchedTYPE.SCROLL;

                                data.touchpadY = (int) dy;


                                send();


                            } else if (r == 6) {
                                r = 0;
                            }
                            r++;


                            break;
                        }
                    }

                }


                mPreviousY = y;


                return returnState;

            }
        });


    }

    public void giveOutputStream(ObjectOutputStream cos) {
        out = cos;
    }


    void send() {
        data.type = TCP_Data.typ.TOUCHPAD;
        try {

            out.writeObject(data);
            out.reset();
            //out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
