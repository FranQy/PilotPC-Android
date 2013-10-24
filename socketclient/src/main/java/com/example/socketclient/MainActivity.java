package com.example.socketclient;

import java.io.PrintWriter;



import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Bundle;


import android.util.Log;
import android.view.KeyEvent;


import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import android.view.animation.TranslateAnimation;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;


@SuppressWarnings("ALL")
public class MainActivity extends Activity implements AsyncResponse {


  // boolean klawisze = false, odswierzPrzycisk = false;
   // Thread mojThread;
    //Thread pilotThread, pilotThread1;
   // ImageView odswierz;

    Context kontext;
   // TextView blad;

  //  ImageView przyciski;
    WifiManager mainWifi;

   //public fileOperation nameFile;



    AlertDialog alertDialog;
 //   InetSocketAddress asddd;
    PrintWriter out;
    pilot pilot;
menu men;
    String servername= "";

ViewFlipper vf;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.pilot_layout);
        men = new menu(this);
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
pilot.blad.setText("asdasd");

        final float[] mPreviousX = {0};
        final float[] mPreviousY = {0};

        ImageView mysz = (ImageView) findViewById(R.id.imageMysz);

        mysz.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

               /* int pointerCount = motionEvent.getPointerCount();
float fingerOneX=0;
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

                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                    {

                        float dx = x - mPreviousX[0];
                        float dy = y - mPreviousY[0];

                        pilot.blad.setText(String.valueOf(dx)+" \r"+String.valueOf(dy));

                    }



                }

                mPreviousX[0] = x;
                mPreviousY[0] = y;


                return true;

            }
        });

        /*buta.setOnClickListener(new View.OnClickListener() {
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


        //odswierz = (ImageView) findViewById(R.id.imageView2);

       /* odswierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameFile.read();

            }
        });*/


    }

    /**
     *
     * input from connecting class
     */

    public void processFinish(PrintWriter output){
        //this you will received result fired from async class of onPostExecute(result) method.

      //  blad.setText("połączono");
        Log.d("async", "polaczono");
        out = null;
        out = output;



        pilot.przyciski1.givePrintWriter(out);
        pilot.przyciski1.klawisze=true;
     //   pilot.blad.setText("asdads");
     //   myAsync.cancel();
    }

    public void processFinish(int output){
        //this you will received result fired from async class of onPostExecute(result) method.

switch (output)
{
    case 1:
    {
       // blad.setText("blad polaczenia");
        break;
    }
    case 2:
    {
      //  blad.setText("wlacz program-server");
        break;
    }
    case 3:
    {
       // blad.setText("wlacz PC");



      //  pilot.blad.setText("asdads");
        break;
    }


}
//klawisze=false;

        pilot.przyciski1.klawisze=false;

        out = null;


        Log.d("async", "end2");
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
    }*/

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
  @Override
    public void onBackPressed()
    {

        //super.onBackPressed();


        if(!men.closeMENU())
        {
pilot.file.rozlacz();
            finish();
        }

       // pilot.file.rozlacz();
       // finish();
      //myAsync.closeSocket();



    }


    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //pilot.przyciski1.sliding.animateOpen();
            men.openclose();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }





    /*class ClientThread implements Runnable {



        @Override
        public void run()
        {




    }
}*/












}







