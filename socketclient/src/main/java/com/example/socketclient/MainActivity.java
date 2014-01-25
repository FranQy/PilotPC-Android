package com.example.socketclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.wifi.WifiManager;
import android.os.Bundle;


import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import android.view.KeyEvent;


import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import android.view.animation.TranslateAnimation;


import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.Toast;
import android.widget.ViewFlipper;







import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;


/* Import ZBar Class files */
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Button;

@SuppressWarnings("ALL")
public class MainActivity extends Activity implements AsyncResponse {



  // boolean klawisze = false, odswierzPrzycisk = false;
   // Thread mojThread;
    //Thread pilotThread, pilotThread1;
   // ImageView odswierz;
   static boolean polaczony = false;
 public static ObjectOutputStream oos;
    static Context kontext;
   public static TextView blad;

    static ImageView PadL, PadR;
    WifiManager mainWifi;

 static TextView stanPolaczenia;

   //public fileOperation nameFile;



    //AlertDialog alertDialog;
 //   InetSocketAddress asddd;

    pilot pilot;
   static touchPad touchp;


   static menu men;
    String servername= "";

    Socket sock;

    /*
    Notifications
     */
    static Notification.Builder builder;
    static NotificationManager manager;


ViewFlipper vf;

  public  static  PowerManager pm;
   public static PowerManager.WakeLock wl;
  public static boolean screenManager = true;
   public static long wlTimeout = 60000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.pilot_layout);

       // this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


/**
 * Notification build
 */
        builder = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("GodFinger")
                        .setContentText("Połączony")
                        .setPriority(1)
                        .setOngoing(true);


         Intent backIntent = new Intent(this, MainActivity.class);
    backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

      Intent intent = new Intent(this, MainActivity.class);
    intent.setAction(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);

    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
            intent, 0);
    builder.setContentIntent(pendingIntent);


       /* Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);*/

        // Add as notification
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
     //   manager.notify(1, builder.build());












        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

         wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");


        wl.acquire();
        //screen will stay on during this section...
       // wl.release();


        kontext = getApplicationContext();




        touchp = new touchPad(this);
        men = new menu(this, getApplicationContext());

        /**
         * viewFlipper construct and show pilotlay.xml
         */
       vf=(ViewFlipper)findViewById(R.id.viewFlippermoj);
       vf.setDisplayedChild(1);
/**
 * menuBar buttons constructors
 */
        ImageButton gamepad = (ImageButton) findViewById(R.id.buttonGamepad);
        ImageButton pilotButton = (ImageButton) findViewById(R.id.buttonPilot);
        ImageButton keyboard = (ImageButton) findViewById(R.id.buttonKeyboard);
        ImageButton mouse = (ImageButton) findViewById(R.id.buttonMouse);

/**
 * menuBar buttons onClickListeners
 * show layouts
 * 0 - gamepadlay.xml
 * 1 - pilotlay.xml
 * 2 - keyboardlay.xml
 * 3 - mouselay.xml
 */

        gamepad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men.close();
                if(vf.getDisplayedChild()!=0){
                    vf.setInAnimation(inFromLeftAnimation());
                    vf.setOutAnimation(outToRightAnimation());
               vf.setDisplayedChild(0);
                }

            }
        });



        pilotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men.close();
                if(vf.getDisplayedChild()==0)
                {
                    vf.setInAnimation(inFromRightAnimation());
                    vf.setOutAnimation(outToLeftAnimation());
                    vf.setDisplayedChild(1);
                }

                else if(vf.getDisplayedChild()>1){
                    vf.setInAnimation(inFromLeftAnimation());
                    vf.setOutAnimation(outToRightAnimation());
                vf.setDisplayedChild(1);
                }
            }
        });





        keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men.close();
                if(vf.getDisplayedChild()<2)
                {




                    vf.setInAnimation(inFromRightAnimation());
                    vf.setOutAnimation(outToLeftAnimation());
                    vf.setDisplayedChild(2);

                }

                else if(vf.getDisplayedChild()>2){
                    vf.setInAnimation(inFromLeftAnimation());
                    vf.setOutAnimation(outToRightAnimation());
                    vf.setDisplayedChild(2);
                }
            }
        });

        mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men.close();
                if(vf.getDisplayedChild()<3)
                {
                    vf.setInAnimation(inFromRightAnimation());
                    vf.setOutAnimation(outToLeftAnimation());
                    vf.setDisplayedChild(3);
                }

            }
        });

        /**
         * end of menu Bar buttons listeners
         */
























      //  PadL.scrollTo(-30, -50);


        //FrameLayout mojanim = (FrameLayout) findViewById(R.id)
     //  Button mojanim = (Button) findViewById(R.id.buttonAim);
    //    LinearLayout mojanim2 = (LinearLayout) findViewById(R.id.container);

       /* AnimatorSet set = new AnimatorSet();


       ObjectAnimator animation = ObjectAnimator.ofFloat(mojanim, "translationY", 0f, -1000f);
        animation.setDuration(2000);
        set.play(animation);
        set.start();*/




      // Animation logoMoveAnimation3 = AnimationUtils.loadAnimation(this, R.anim.animlay);
      //  mojanim2.startAnimation(logoMoveAnimation3);


       // Animation logoMoveAnimation = AnimationUtils.loadAnimation(this, R.anim.animlay);
      //  mojanim.startAnimation(logoMoveAnimation);



       // View newView = (View) findViewById(R.id)





       pilot = new pilot(this);







        /*buta.setOnClickLireturn false;stener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mContainerView.getChildCount()==0)
                {
              //  addItem();
                }
                else
                {
                    mContainerView.removeAllViews();
                }
            }
        });*/

        /*mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);




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
*/
        PadL = (ImageView) findViewById(R.id.padL);

      final ImageView PadLBack = (ImageView) findViewById(R.id.PadLBack);
        blad = (TextView) findViewById(R.id.textView1);

        PadLBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                float x = motionEvent.getX();

                float y = motionEvent.getY();
                float x1, y1;









                int actionMask = motionEvent.getActionMasked();

                if(actionMask == MotionEvent.ACTION_MOVE)
                {
                    x-=90;
                    y-=90;
                    blad.setText(String.valueOf(y));
                    if(Math.sqrt(x*x+y*y)>79)
                    {
                        y1 = (float) ((y/Math.sqrt(x*x+y*y))*85);
                        x1 = (float) ((x/Math.sqrt(x*x+y*y))*85);
                    }
                    else
                    {
                        x1=x;
                        y1=y;
                    }
                    blad.setText(String.valueOf(Math.sqrt(x1*x1+y1*y1)));
                    PadL.setX(x1+PadLBack.getLeft());
                    PadL.setY(y1+653+90);






                    return true;
                }
                else if(actionMask == MotionEvent.ACTION_UP)
                {
                    PadL.setX(PadLBack.getLeft());
                    PadL.setY(743);

                    x-=90;
                    y-=90;
                   // blad.setText(String.valueOf(Math.sqrt(x*x+y*y)));
                }
                //blad.setText(String.valueOf(x-90)+"  "+String.valueOf(y-90));
                return true;
            }
        });



        PadR = (ImageView) findViewById(R.id.PadR);

        final ImageView PadRBack = (ImageView) findViewById(R.id.PadRBack);


        PadRBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                float x = motionEvent.getX();
                float y = motionEvent.getY();
                float x1, y1;









                int actionMask = motionEvent.getActionMasked();

                if(actionMask == MotionEvent.ACTION_MOVE)
                {
                    x-=90;
                    y-=90;

                    if(Math.sqrt(x*x+y*y)>79)
                    {
                        y1 = (float) ((y/Math.sqrt(x*x+y*y))*85);
                        x1 = (float) ((x/Math.sqrt(x*x+y*y))*85);
                    }
                    else
                    {
                        x1=x;
                        y1=y;
                    }
                    blad.setText(String.valueOf(Math.sqrt(x*x+y*y)));
                   // PadR.setX();
                   // PadR.offsetLeftAndRight((int)x1+PadRBack.getLeft());
                    PadR.layout(400, 400, 400, 400 );
                   // PadR.setY(y1+653+90);






                    return true;
                }
                else if(actionMask == MotionEvent.ACTION_UP)
                {
                    PadR.setX(PadRBack.getLeft());
                    PadR.setY(743);

                    x-=90;
                    y-=90;
                    // blad.setText(String.valueOf(Math.sqrt(x*x+y*y)));
                }
                //blad.setText(String.valueOf(x-90)+"  "+String.valueOf(y-90));
                return true;
            }
        });




    }

    /**
     *
     * input from connecting class
     */
boolean costamif = false;



    public void processFinish( ObjectOutputStream  output, OutputStream os, InputStream is, final Socket sock){
        //this you will received result fired from async class of onPostExecute(result) method.

       this.sock = sock;


        /**
         *
         * Tu masz ten ping
         *
         */
        try {
            os = sock.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] buffo = new byte[11];
        for(int i = 0; i<11; i++)
        {
         buffo[i]=11;
        }

        try {
            os.write(buffo);
        } catch (IOException e) {
            e.printStackTrace();
        }






        //  PrintWriter out;
      //  blad.setText("połączono");
        Log.d("async", "polaczono");
        polaczony=true;
       // out = null;
       // out = output;

//sendcos wyslij = new sendcos(out);
       // wyslij.senda();


//        touch.givePrintWriter(out);

//boolean so = touchp.active;

        oos = output;
       // touchp.blad.setText("tou");
        touchp.active = true;
       touchp.giveOutputStream(oos);
       // pilot.przyciski1.givePrintWriter(out);
       // pilot.przyciski1.giveOutputStream(oos);
        pilot.przyciski1.klawisze=true;
       // pilot.blad.setText("asdads");
        Toast.makeText(kontext, "połączono", 2000).show();
       // stanPolaczenia.setText("-Stan:  połączono");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {

                    try {
                        if(sock.getInputStream().read() == -1)
                        {


                            Log.d("async", "rozlaczono");
                            polaczony=false;
                            pilot.przyciski1.klawisze=false;
                            touchp.active = false;
                            men.rozlaczono();

                          //  Toast.makeText(kontext, "rozłączono", 2000).show();
                            break;

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();



        //   myAsync.cancel();
    }




    public void processFinish(int output){
        //this you will received result fired from async class of onPostExecute(result) method.

        polaczony=false;
        blad.setText("rozlaczony");
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



      //  pilot.blad.setText("asdads");
        break;
    }


}

//klawisze=false;
touchp.active = false;
        pilot.przyciski1.klawisze=false;

        oos = null;


        Log.d("async", "end2");
    }


    void foo()
    {
        stanPolaczenia.setText("-Stan:  rozłączonyaaa");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        pilot.blad.setText("landscape");
        // Checks the orientation of the screen

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
          pilot.blad.setText("landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            pilot.blad.setText("portrait");
        }
    }




    /**
     * viewFlipper's animations
     * @return
     */
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
    }


    /**
     * end of viewFlipper's animations
     */








 /*   public class WiFiDialog extends DialogFragment {
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
    }*/














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
*/





    public void onPause()
    {
          super.onPause();
         if(wl.isHeld())
             wl.release();



        }

    @Override
    protected void onStart() {
        super.onStart();

        manager.cancel(1);
    }

    protected void onStop() {
        super.onStop();

        if(polaczony==true)
        {
        manager.notify(1, builder.build());
        }



    }


     @Override
    public void onBackPressed()
    {

        //super.onBackPressed();
        if(polaczony)
        {
        Toast.makeText(kontext, "przytrzymaj dluzej aby zakończyć", 2000).show();
        this.moveTaskToBack(true);
        }
        else
        {

            if(!men.closeMENU())
            {
                pilot.file.rozlacz();
                finish();
            }
        }

    }



        @Override
        public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {



            if(!men.closeMENU())
            {
                pilot.file.rozlacz();
                finish();
            }
            // do your stuff here
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }







    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //pilot.przyciski1.sliding.animateOpen();
            men.openclose();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();



        manager.cancel(1);
        pilot.file.rozlacz();
    }


    public void onResume()
    {

        super.onResume();
        if(screenManager)
            wl.acquire(wlTimeout);

    }







    /*class ClientThread implements Runnable {



        @Override
        public void run()
        {




    }
}*/










}







