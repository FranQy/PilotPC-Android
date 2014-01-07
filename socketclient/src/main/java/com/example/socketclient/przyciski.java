package com.example.socketclient;




import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;

import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageView;


import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;


/**
 * Created by franqy on 05.10.13.
 * przyciski v0.3
 */
public class przyciski extends pilot{
  //  public Activity activity;
    boolean klawisze = false;
    ImageView przyciskia, infoImage, hand;
    ImageView onoff, mute, music, photo, movie, quieter, louder, perv, next, playpause, stop;


   // PrintWriter out;
  // ObjectOutputStream out;
    static public  TCP_Data data  = new TCP_Data();

    public przyciski(){

super();

       // out = null;

        blad = (TextView) this.activity.findViewById(R.id.textView1);

przyciskia = (ImageView)this.activity.findViewById(R.id.przyciskiBack);

        przyciskia.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                float X, Y;
                X = motionEvent.getX();
                Y = motionEvent.getY();

                float[] XY = new float[]{X, Y};


                Matrix invertMatrix = new Matrix();
                //TODO: check Null pointer bla bla
                przyciskia.getImageMatrix().invert(invertMatrix);


                invertMatrix.mapPoints(XY);

                int x = (int) XY[0];
                int y = (int) XY[1];

                Drawable imgDrawable = przyciskia.getDrawable();
                assert imgDrawable != null;
                Bitmap bitmap = ((BitmapDrawable)  imgDrawable).getBitmap();



                  // blad.setText(String.valueOf(x));


                //if(x>0 && x<500)
                ktoryPrzycisk(bitmap.getPixel(x, y));



                return false;
            }


        });

    }

void ktoryPrzycisk(int touchedRGB)
{
blad.setText(String.valueOf(touchedRGB));
    if (klawisze) {

    switch (touchedRGB) {

        case -7024087: {
            click(TCP_Data.pilotButton.VOLUP);
            break;
        }
        case -7023945: {
            click(TCP_Data.pilotButton.VOLDOWN);
            break;
        }
        case -15404256: {
            click(TCP_Data.pilotButton.MUTE);

            break;
        }
        case -789740: {
            //click("p/p");
            click(TCP_Data.pilotButton.PLAYPAUSE);
            break;
        }
        case -798025: {
            click(TCP_Data.pilotButton.PERV);
            break;
        }
        case -821484: {
            click(TCP_Data.pilotButton.NEXT);
            break;
        }
        case -846828: {
            click(TCP_Data.pilotButton.OFF);
            break;
        }

        case -15404070: {
            //click("stop");
            click(TCP_Data.pilotButton.STOP);
            break;
        }




        case -15443725: {
           // click("OK");
            click(TCP_Data.pilotButton.RETTURN);
            break;
        }
        case -16734011: {
            click(TCP_Data.pilotButton.UP);
            break;
        }
        case -2381627: {
            click(TCP_Data.pilotButton.DOWN);
            break;
        }
        case -16777019: {
            click(TCP_Data.pilotButton.RIGHT);
            break;
        }
        case -16777216: {
            click(TCP_Data.pilotButton.LEFT);
            break;
        }
        case -846617: {
            click(TCP_Data.pilotButton.MULTIMEDIA);
            break;
        }
        case -846735:{
            click(TCP_Data.pilotButton.MUSIC);
            break;
        }
        default: {
            break;
        }
        }
    }
}









    void click(TCP_Data.pilotButton button)
    {
        data.type=TCP_Data.typ.PILOT;
        data.button=button;
         if(klawisze)
         {
             try {
                   MainActivity.oos.writeObject(data);
                     MainActivity.oos.reset();
                  MainActivity.oos.flush();
             } catch (IOException e) {
               e.printStackTrace();
             }
        }
        click();
    }
    public void click(){
        Vibrator v = (Vibrator) pilot.activity.getSystemService(Context.VIBRATOR_SERVICE);
        int VIBRATION_TIME = 50;
        v.vibrate(VIBRATION_TIME);

    }













}
