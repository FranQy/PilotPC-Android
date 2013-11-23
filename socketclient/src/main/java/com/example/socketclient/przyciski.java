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
   // ImageView przyciskia, infoImage, hand;
    ImageView onoff, mute, music, photo, movie, quieter, louder, perv, next, playpause, stop;


   // PrintWriter out;
  // ObjectOutputStream out;
    static public  TCP_Data data  = new TCP_Data();

    public przyciski(){

super();

       // out = null;

        blad = (TextView) this.activity.findViewById(R.id.textView1);
blad.setText("asd");


       onoff = (ImageView) this.activity.findViewById(R.id.onoff);
       mute = (ImageView) this.activity.findViewById(R.id.mute);
       music = (ImageView) this.activity.findViewById(R.id.music);
       photo = (ImageView) this.activity.findViewById(R.id.photo);
       movie = (ImageView) this.activity.findViewById(R.id.movie);
       quieter = (ImageView) this.activity.findViewById(R.id.quieter);
       louder = (ImageView) this.activity.findViewById(R.id.louder);
       perv = (ImageView) this.activity.findViewById(R.id.perv);
       next = (ImageView) this.activity.findViewById(R.id.next);
       playpause = (ImageView) this.activity.findViewById(R.id.playpause);
       stop = (ImageView) this.activity.findViewById(R.id.stop);



        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(TCP_Data.pilotButton.OFF);
            }
        });


        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(TCP_Data.pilotButton.MUTE);
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(TCP_Data.pilotButton.MUSIC);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click(TCP_Data.pilotButton.);
            }
        });

        movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(TCP_Data.pilotButton.MULTIMEDIA);
            }
        });

        quieter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(TCP_Data.pilotButton.VOLDOWN);
            }
        });

        louder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(TCP_Data.pilotButton.VOLUP);
            }
        });

        perv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(TCP_Data.pilotButton.PERV);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(TCP_Data.pilotButton.NEXT);
            }
        });

        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(TCP_Data.pilotButton.PLAYPAUSE);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(TCP_Data.pilotButton.STOP);
            }
        });








        /**
         * STARA WERSJA
         */
        /**
        przyciskia = (ImageView) this.activity.findViewById(R.id.imagePrzyciski);
        infoImage =(ImageView) this.activity.findViewById(R.id.imageInfo);

       // connectingSlidingMenu = (SlidingDrawer) this.activity.findViewById(R.id.slidingDrawer2);
      // PilotButton = (ImageView) this.activity.findViewById(R.id.pilotButton);

        hand = (ImageView)this.activity.findViewById(R.id.handleImag);
//testLay = (LinearLayout) this.activity.findViewById(R.id.linearLayout4);



        infoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    infoImage.setVisibility(View.INVISIBLE);
                click();

            }
        });

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


                if(x>0 && x<500)
                ktoryPrzycisk(bitmap.getPixel(x, y));



                return false;
            }


        });

    }

void ktoryPrzycisk(int touchedRGB)
{

    if(touchedRGB == -14149877)
    {
        blad.setText("menu masz na dole");
       // click();



    }
    else if(touchedRGB == -32640)
    {
       click();
        infoImage.setVisibility(View.VISIBLE);
    }

    if (klawisze) {

    switch (touchedRGB) {
        case -39424: {
            //click("radio");
            break;
        }
        case -256: {
            click(TCP_Data.pilotButton.VOLUP);
            break;
        }
        case -16733697: {
            click(TCP_Data.pilotButton.VOLDOWN);
            break;
        }
        case -5636268: {
            click(TCP_Data.pilotButton.MUTE);
            break;
        }
        case -13312: {
            //click("p/p");
            click(TCP_Data.pilotButton.PLAYPAUSE);
            break;
        }
        case -11189248: {
            click(TCP_Data.pilotButton.PERV);
            break;
        }
        case -6528: {
            click(TCP_Data.pilotButton.NEXT);
            break;
        }
        case -65536: {
            click(TCP_Data.pilotButton.OFF);
            break;
        }
        case -13142: {
            //click("tv");
            break;
        }
        case -32982: {
            //click("movie");
            break;
        }
        case -11522794: {
           // click("rec");
            break;
        }
        case -2912929: {
            //click("stop");
            click(TCP_Data.pilotButton.STOP);
            break;
        }
        case -16744448: {
            //click("red");
            break;
        }
        case -65281: {
            //click("green");
            break;
        }
        case -11206656: {
            //click("yellow");
            break;
        }
        case -2883584: {
           // click("blue");
            break;
        }
        case -43691: {
           // click("DVR");
            break;
        }

        case -21846: {
          //  click("info");


            break;
        }
        case -5467245: {
            click(TCP_Data.pilotButton.EXIT);
            break;
        }
        case -58368: {
            //click("ch+");
            break;
        }
        case -11206743: {
            //click("ch-");
            break;
        }
        case -51200: {
            //back
            break;
        }
        case -16711681: {
           // click("OK");
            click(TCP_Data.pilotButton.RETTURN);
            break;
        }
        case -1900772: {
            click(TCP_Data.pilotButton.UP);
            break;
        }
        case -16777089: {
            click(TCP_Data.pilotButton.DOWN);
            break;
        }
        case -16744449: {
            click(TCP_Data.pilotButton.RIGHT);
            break;
        }
        case -16776961: {
            click(TCP_Data.pilotButton.LEFT);
            break;
        }

        default: {
            break;
        }
        }
    }
**/





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
