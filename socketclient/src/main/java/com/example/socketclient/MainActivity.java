package com.example.socketclient;




import java.io.FileReader;
import java.io.FileWriter;


import java.io.PrintWriter;

import java.net.InetSocketAddress;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.wifi.SupplicantState;

import android.net.wifi.WifiManager;

import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;


@SuppressWarnings("ALL")
public class MainActivity extends Activity implements AsyncResponse {


    Thread mojThread;
    Thread pilotThread;
    Button   menu;
    ImageView odswierz;

    Context kontext;
    TextView blad;

    ImageView przyciski;
    WifiManager mainWifi;

   public fileOperation nameFile;

    connecting myAsync;


   // private ViewFlipper vf;

    //private float oldTouchValue;



    AlertDialog alertDialog;
    InetSocketAddress asddd;
    PrintWriter out;


    String servername= "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilot_layout);
       //vf=(ViewFlipper)findViewById(R.id.ViewFlipper01);

      test1();

nameFile = new fileOperation();


        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);


        /*
        Tworzenie przyciskow z imageView
         */

        przyciski = (ImageView)  findViewById(R.id.imagePrzyciski);

      przyciski.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent motionEvent) {
              float X, Y;
             X=motionEvent.getX();
              Y=motionEvent.getY();

              float[] XY = new float[] {X, Y};


              Matrix invertMatrix = new Matrix();
              ((ImageView)view).getImageMatrix().invert(invertMatrix);

              invertMatrix.mapPoints(XY);

              int x = Integer.valueOf((int)XY[0]);
              int y = Integer.valueOf((int)XY[1]);

              Drawable imgDrawable = ((ImageView)view).getDrawable();
              Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();



              int touchedRGB = bitmap.getPixel(x, y);
              blad.setText(String.valueOf(touchedRGB));



              switch(touchedRGB)
              {
                  case -39424:
                  {
                      blad.setText("radio");
                      break;
                  }
                  case -256:
                  {
                      blad.setText("glosniej");
                      break;
                  }
                  case -16733697:
                  {
                      blad.setText("ciszej");
                      break;
                  }
                  case -5636268:
                  {
                      blad.setText("wycisz");
                      break;
                  }
                  case -13312:
                  {
                      blad.setText("P/P");
                      break;
                  }
                  case -11189248:
                  {
                      blad.setText("perw");
                      break;
                  }
                  case -6528:
                  {
                      blad.setText("next");
                      break;
                  }
                  case -65536:
                  {
                      blad.setText("off");
                      break;
                  }
                  case -13142:
                  {
                      blad.setText("tv");
                      break;
                  }
                  case -32982:
                  {
                      blad.setText("movie");
                      break;
                  }
                  case -11522794:
                  {
                      blad.setText("rec");
                      break;
                  }
                  case -2912929:
                  {
                      blad.setText("stop");
                      break;
                  }
                  case -16744448:
                  {
                      blad.setText("red");
                      break;
                  }
                  case -65281:
                  {
                      blad.setText("green");
                      break;
                  }
                  case -11206656:
                  {
                      blad.setText("yellow");
                      break;
                  }
                  case -2883584:
                  {
                      blad.setText("blue");
                      break;
                  }
                  case -43691:
                  {
                      blad.setText("DVR");
                      break;
                  }
                  case -32640:
                  {
                      blad.setText("GUIDE");
                      break;
                  }
                  case -21846:
                  {
                      blad.setText("info");
                      break;
                  }
                  case -5467245:
                  {
                      blad.setText("exit");
                      break;
                  }
                  case -14149877:
                  {
                      blad.setText("menu");
                      break;
                  }
                  case -58368:
                  {
                      blad.setText("ch+");
                      break;
                  }
                  case -11206743:
                  {
                      blad.setText("ch-");
                      break;
                  }
                  case -51200:
                  {
                      blad.setText("return");
                      break;
                  }
                  case -16711681:
                  {
                      blad.setText("OK");
                      break;
                  }
                  case -1900772:
                  {
                      blad.setText("up");
                      break;
                  }
                  case -16777089:
                  {
                      blad.setText("down");
                      break;
                  }
                  case -16744449:
                  {
                      blad.setText("right");
                      break;
                  }
                  case -16776961:
                  {
                      blad.setText("left");
                      break;
                  }

                  case -1:
                  {
                      break;
                  }
              }


            // blad.setText(String.valueOf(touchedRGB));



              return false;
          }
      });


        blad = (TextView) findViewById(R.id.textView1);

        menu = (Button) findViewById(R.id.buttonMenu);
         alertDialog = new AlertDialog.Builder(this).create();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialasd = new hostNameDialog();
                dialasd.show(getFragmentManager(), "asd");

            }
        });






       if(mainWifi.getWifiState()==1)
       {
           DialogFragment wifi = new WiFiDialog();
            wifi.show(getFragmentManager(), "asd");
       }
      else  if(mainWifi.getConnectionInfo().getSupplicantState() != SupplicantState.COMPLETED)
       {
           startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
           finish();
       }


     //   DialogFragment dialasd = new FireMissilesDialogFragment();
       // dialasd.show(getFragmentManager(), "asd");





        odswierz = (ImageView) findViewById(R.id.imageView2);

        odswierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameFile.read();

            }
        });




       //  mojThread = new Thread(new ClientThread());


        pilotThread = new Thread(new Pilot());
       // pilotThread.start();
        nameFile.read();


blad.setText("ad");
    }

    public void test1()
    {
        myAsync = new connecting();

        myAsync.delegate = this;


    }


    public void processFinish(PrintWriter output){
        //this you will received result fired from async class of onPostExecute(result) method.

        blad.setText("połączono");
        Log.d("async", "polaczono");
out = null;
        out = output;

        if(pilotThread.isAlive())
        {
            pilotThread.interrupt();
        }
        pilotThread = new Thread(new Pilot());
        pilotThread.start();

     //   myAsync.cancel();
    }

    public void processFinish(int output){
        //this you will received result fired from async class of onPostExecute(result) method.
switch (output)
{
    case 1:
    {
        blad.setText("blad polaczenia");
        break;
    }
    case 2:
    {
        blad.setText("wlacz program-server");
        break;
    }
    case 3:
    {
        blad.setText("wlacz PC");
        break;
    }


}

    if(pilotThread.isAlive())
    {
        pilotThread.interrupt();
    }
    myAsync.closeSocket();

        out = null;


        Log.d("async", "end2");
    }



  /*  @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                oldTouchValue = touchevent.getX();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                //if(this.searchOk==false) return false;
                float currentX = touchevent.getX();
                if (oldTouchValue < currentX)
                {
                    vf.setInAnimation(inFromLeftAnimation());
                    vf.setOutAnimation(outToRightAnimation());
                    vf.showNext();
                }
                if (oldTouchValue > currentX)
                {
                    vf.setInAnimation(inFromRightAnimation());
                    vf.setOutAnimation(outToLeftAnimation());
                    vf.showPrevious();

                }
                break;
            }
        }
        return false;
    }

    //for the previous movement
    public static Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
        );
        inFromRight.setDuration(350);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }
    public static Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
        );
        outtoLeft.setDuration(350);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }
    // for the next movement
    public static Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
        );
        inFromLeft.setDuration(350);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }
    public static Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
        );
        outtoRight.setDuration(350);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }*/








    public class hostNameDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Log.d("MyThread", "bla");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            final View v = inflater.inflate(R.layout.activity_main, null);
            builder.setView(v)
                    // Add action buttons

                    .setNegativeButton("anuluj", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // LoginDialogFragment.this.getDialog().cancel();
                        }
                    })
            .setPositiveButton("potwierdź", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Log.d("MyThread", "bla");

                    nameFile.write(((EditText) v.findViewById(R.id.kurwa)).getText().toString());
                    Log.d("M strt", "adasd bal");
                    nameFile.read();
                   // servername = ((EditText) v.findViewById(R.id.kurwa)).getText().toString();



                }
            });
            return builder.create();
        }
        }



    public class WiFiDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            final View v = inflater.inflate(R.layout.wifidialog, null);
            builder.setView(v)
                    // Add action buttons
                    .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            finish();

                        }
                    })
                    .setPositiveButton("Włącz", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mainWifi.setWifiEnabled(true);

                            while(!mainWifi.isWifiEnabled())
                            {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            }

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if(mainWifi.getConnectionInfo().getSupplicantState() != SupplicantState.COMPLETED)
                            {
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                finish();
                            }




                        }
                    });
            return builder.create();
        }
    }














  /*  public void onResume()
    {
        super.onResume();


        if(socket==null)
        {


            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
             i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }

    }

    public void onPause()
    {
        super.onPause();
        if(socket!=null)
        {
        try {

            socket.getOutputStream().close();
            socket.getInputStream().close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            socket = null;




        }


    }
*/
    public void onBackPressed()
    {

        super.onBackPressed();

      myAsync.closeSocket();

    }






    /*class ClientThread implements Runnable {



        @Override
        public void run()
        {




    }
}*/




  class Pilot implements Runnable {


        Button glosniej;
        Button ciszej;
        Button wycisz;
        Button radio;
        Button pp;
        Button next;
        Button perv;
        Button cancel;
        Button stop;
        Button XBMC;
        Button music;
        Button up, down, left, right, OK;

        Bundle extras;

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        int VIBRATION_TIME = 80;


        @Override
        public void run() {


            if(getIntent().getExtras()!= null)

            {
                Bundle extras = getIntent().getExtras();
                String value1 = extras.getString("yt");

                out.println(value1);
            }






            glosniej = (Button) findViewById(R.id.buttonGlosniej);
                    ciszej = (Button) findViewById(R.id.buttonCiszej);
            wycisz = (Button) findViewById(R.id.buttonWycisz);
            radio = (Button) findViewById(R.id.buttonRadio);
            pp = (Button) findViewById(R.id.buttonPlay);
            next = (Button) findViewById(R.id.buttonNext);
            perv = (Button) findViewById(R.id.buttonPerv);




                    glosniej.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                          /* if(socket.isOutputShutdown())
                            {
                                blad.setText("rozlaczony1");
                            }*/

                            out.println("glosniej");
                            v.vibrate(VIBRATION_TIME);
                        }
                    });

                    ciszej.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            out.println("ciszej");
                            v.vibrate(VIBRATION_TIME);
                        }
                    });

            wycisz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    out.println("wycisz");
                    v.vibrate(VIBRATION_TIME);
                }
            });

            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    out.println("radio");
                    v.vibrate(VIBRATION_TIME);
                }
            });

            pp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    out.println("p/p");
                    v.vibrate(VIBRATION_TIME);
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    out.println("next");
                    v.vibrate(VIBRATION_TIME);
                }
            });

            perv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    out.println("perv");
                    v.vibrate(VIBRATION_TIME);
                }
            });



                }
    }



    public class fileOperation
    {
        public void read()
        {
            Log.d("M strt", "reading 0");
            servername = "";

            char buf[] = new char[512];

            FileReader rdr;

            try {
                rdr = new FileReader("/sdcard/PilotTV/serverName.ptv");
                int s = rdr.read(buf);
                for(int k = 0; k < s; k++){
                    servername+=buf[k];
                    buf[k]='\0';
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




                Log.d("FILE", servername);




            //if(pilotThread.isAlive())
               // pilotThread.destroy();
            Log.d("reasd", "pilotThreadStop");

           // pilotThread = new Thread(new Pilot());
            Log.d("reasd", "pilotThreadMake");

test1();

            Log.d("reasd", "Myasync start");
            myAsync.execute(servername);



        }


        public void write(String text)
        {

            FileWriter fWriter;
            try{
                fWriter = new FileWriter("/sdcard/PilotTV/serverName.ptv");
                fWriter.write(text);
                fWriter.flush();
                fWriter.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        }


    }


}







