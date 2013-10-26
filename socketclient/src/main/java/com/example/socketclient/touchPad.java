package com.example.socketclient;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by franqy on 24.10.13.
 */
public class touchPad extends Activity {
    Activity activity;
TextView blad;
    PrintWriter out;
   public boolean active = false;

    public touchPad(Activity activity)
    {
        this.activity = activity;
        out = null;
        blad = (TextView) this.activity.findViewById(R.id.textView1);
        blad.setText("touchpad");

        ImageView mysz = (ImageView) this.activity.findViewById(R.id.imageMysz);


        mysz.setOnTouchListener(new View.OnTouchListener() {
            float mPreviousX=0;
            float mPreviousY=0;
            float oldX =0;
            float oldY=0;
            private Timer longpressTimer;
            private boolean longClicked = false;
            private  boolean returnState = true;
            long startTime=0;


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


                if(active)
                {

                int actionMask = motionEvent.getActionMasked();

                switch (actionMask)
                {


                    case MotionEvent.ACTION_DOWN:
                    {
                        returnState = true;
                        startTime = System.currentTimeMillis();

                        oldX= x;
                        oldY= y;

                        longClicked = false;

                        longpressTimer = new Timer();


                        longpressTimer.schedule(new TimerTask(){


                            @Override
                            public void run() {
                                longClicked = true;
                                pilot.przyciski1.click();
                            }
                        }, 1000);
                        break;

                    }
                    case MotionEvent.ACTION_POINTER_1_UP:
                    {
                        prawy = true;
                       blad.setText("prawy");
                        longpressTimer.cancel();

                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        longpressTimer.cancel();
                        returnState = false;
                        if((int) Math.sqrt(dx*dx + dy*dy)<=2 && System.currentTimeMillis()-startTime<100 && !prawy){

                            out.println("click");
                            blad.setText("click");
                        }
                        prawy =  false;
                        break;


                    }
                    case MotionEvent.ACTION_MOVE:
                    {
                        int s = (int) Math.sqrt(dx*dx + dy*dy);
                        boolean isActuallyMoving = s >= 1;

                        if(isActuallyMoving){

                            longpressTimer.cancel();
                        }


                        if(longClicked)
                        {
                            blad.setText("long  "+String.valueOf(dx));


                        }
                        else if(System.currentTimeMillis()-startTime>50)
                        {
                            if(dx<0)
                            {
                                dx-=1;
                            }
                            else if(dx>0)
                            {
                                dx+=1;
                            }
                            out.println((int)dx);

                            blad.setText(String.valueOf(dx)+" \r"+String.valueOf(dy));

                        }
                        /*?
                        a = MouseInfo.getPointerInfo();
			b = a.getLocation();
			 x = (int) b.getX();
		 y = (int) b.getY();

		robot.mouseMove(x, y-1);
                         */

                        break;
                    }
                }
                }

                mPreviousX = x;
                mPreviousY = y;


                return returnState;

            }


        });

    }

public void givePrintWriter(PrintWriter cos)
{
    out = cos;
}
}
