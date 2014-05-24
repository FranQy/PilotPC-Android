package com.example.socketclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;







/* Import ZBar Class files */


public class MainActivity extends Activity implements AsyncResponse {


    public static ObjectOutputStream oos;
    public static TextView blad;
    public static PowerManager pm;
    public static PowerManager.WakeLock wl;
    public static boolean screenManager = true;
    public static long wlTimeout = 60000;
    static SteeringSocketService steeringSocketService = null;
    static Intent service;

    static boolean polaczony = false;
    static Context kontext;


    static Context baseKontext;
    static ImageView PadL, PadR;
    static TextView stanPolaczenia;
    static Pilot pilot;
    static Keyboard keyboarda;
    static touchPad touchp;
    static menu men;

    /**
    *Notifications
    */
    static NotificationCompat.Builder builder;
    static NotificationManager manager;
    WifiManager mainWifi;
    String servername = "";
    static fileOperation file;
    ViewFlipper vf;
    /**
     * input from connecting class
     */
    boolean costamif = false;

    /**
     * viewFlipper's animations
     *
     * @return
     */
    public static Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        inFromRight.setDuration(350);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    public static Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        outtoLeft.setDuration(350);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    // for the next movement
    public static Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        inFromLeft.setDuration(350);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    public static Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        outtoRight.setDuration(350);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    public static void lacz(String servname) {

      /*  myAsync.closeSocket();
        myAsync = new connecting();


        myAsync.delegate = new MainActivity();

        myAsync.execute(servname);*/


        steeringSocketService.newSock(servname);


    }

    public static void rozlacz() {
      /*  myAsync.closeSocket();*/


        steeringSocketService.closeSocket();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.pilot_layout);

        kontext = getApplicationContext();
        baseKontext = getBaseContext();
        men = new menu(this, getApplicationContext());
        file = new fileOperation();


        service = new Intent(this, connection.class);
        startService(service);

        steeringSocketService = new connection();


/**
 *
 * Notification build
 *
 */


        RemoteViews notyficationView = new RemoteViews(getPackageName(), R.layout.notyfication);

        builder = new NotificationCompat.Builder(this).setAutoCancel(true)
                .setContentIntent(PendingIntent.getBroadcast(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("PilotPC")
                .setContentText("Połączony")
                .setOngoing(true)
                .setContent(notyficationView);



        Intent backIntent = new Intent(this, MainActivity.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, 0);
        builder.setContentIntent(pendingIntent);


        // Add as notification
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //   manager.notify(1, builder.build());


        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");


        wl.acquire();
        //screen will stay on during this section...
        // wl.release();

        /**
         * inicjacja poszczegolnych ustawien
         */

        menuOptionsInitiate();


     /*   Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        Paint paint = new Paint();
//paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));
        c.drawCircle(50, 50, 30, paint);

        ImageView img = (ImageView) findViewById(R.id.testDraw);
*/
//        img.setBackground(new BitmapDrawable(bitmap));


        keyboarda = new Keyboard(this, this.getApplicationContext());
        touchp = new touchPad(this);
        pilot = new Pilot(this);


        /**
         * viewFlipper construct and show pilotlay2.xml
         */
        vf = (ViewFlipper) findViewById(R.id.viewFlippermoj);
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
 * 1 - pilotlay2.xml
 * 2 - keyboardlay.xml
 * 3 - mouselay.xml
 */

        gamepad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men.close();
                if (vf.getDisplayedChild() != 0) {
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
                if (vf.getDisplayedChild() == 0) {
                    vf.setInAnimation(inFromRightAnimation());
                    vf.setOutAnimation(outToLeftAnimation());
                    vf.setDisplayedChild(1);
                } else if (vf.getDisplayedChild() > 1) {
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
                if (vf.getDisplayedChild() < 2) {


                    vf.setInAnimation(inFromRightAnimation());
                    vf.setOutAnimation(outToLeftAnimation());
                    vf.setDisplayedChild(2);

                } else if (vf.getDisplayedChild() > 2) {
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
                if (vf.getDisplayedChild() < 3) {
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


        blad = (TextView) findViewById(R.id.textView1);

     /*   PadL = (ImageView) findViewById(R.id.padL);

      final ImageView PadLBack = (ImageView) findViewById(R.id.PadLBack);


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
*/


    }

   /* void showToast(final String toast, final int time) {


        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(kontext, toast, time).show();
                men.rozlaczono();
            }
        });
    }*/

    public void processFinish(ObjectOutputStream output, OutputStream os, InputStream is, final Socket sock) {
        //this you will received result fired from async class of onPostExecute(result) method.

        men.closeMENUall();
        men.close();
        /**
         *
         * Tu masz ten ping
         *
         */
       /* try {
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
*/


        //  PrintWriter out;
        //  blad.setText("połączono");
        Log.d("async", "polaczono");
        polaczony = true;
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

        keyboarda.active = true;
        keyboarda.giveOutputStream(oos);


        // pilot.przyciski1.givePrintWriter(out);
        // pilot.przyciski1.giveOutputStream(oos);
        pilot.przyciski1.klawisze = true;
        // pilot.blad.setText("asdads");
        Toast.makeText(kontext, "połączono", Toast.LENGTH_SHORT).show();
        // stanPolaczenia.setText("-Stan:  połączono");




       /* new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("ping", "start");

                Ping ping = new Ping();

                ping.liczba = 1111;

                while (true)
                {

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                try {
                    oos.writeObject(ping);

                    oos.flush();
                    oos.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }


               *//* try {
                    ping = (Ping) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*//*

                Log.d("ping", "odebral");


                }




            }
        }).start();*/





     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                boolean run = true;




                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while(run)
                {

                    if(sock != null)
                    {
                    try {
                        InputStream test = null;
                        Thread.sleep(1000);


                        test = sock.getInputStream();




                        if( test.read() == -1)
                        {


                            Log.d("async", "rozlaczono");
                            polaczony=false;
                            pilot.przyciski1.klawisze=false;
                            touchp.active = false;


                            showToast("rozłączono", 2000);
                            run = false;
                            break;
                        }

                        test.close();


                        } catch (IOException e) {
                        run = false;
                        Log.d("async", "rozlaczono");
                        polaczony=false;
                        pilot.przyciski1.klawisze=false;
                        touchp.active = false;



                        showToast("rozłączono", 2000);
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }



                }

            }
        }).start();*/
    }

    public void processFinish(int output) {
        //this you will received result fired from async class of onPostExecute(result) method.

        polaczony = false;
        men.closeMENUall();
        men.close();
//        blad.setText("rozlaczony");
        switch (output) {
            case -1: {
                Toast.makeText(kontext, "rozlaczono", 5).show();
                men.rozlaczono();
                //  blad.setText("blad polaczenia");
                break;
            }
            case 0: {
                men.rozlaczono();
                builder.setContentText("Rozlaczony");
                manager.notify(1, builder.build());
                //blad.setText("wlacz program-server");
                break;
            }
            case 1: {
                lacz(file.read("server"));

                // blad.setText("wlacz PC");


                //  pilot.blad.setText("asdads");
                break;
            }


        }

//klawisze=false;
        touchp.active = false;
        pilot.przyciski1.klawisze = false;

        oos = null;


        Log.d("async", "end2");
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

       return mWifi.isConnected();


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
*/

    void loading(final boolean show) {

        final RelativeLayout loadingLay = (RelativeLayout) findViewById(R.id.loadingLay);

        runOnUiThread(new Runnable() {
            public void run() {
                if (show) {
                    loadingLay.setVisibility(View.VISIBLE);
                } else {
                    loadingLay.setVisibility(View.GONE);
                    lacz(file.read("server"));
                }
            }
        });
    }

    public void onPause() {
        super.onPause();
        if (wl.isHeld())
            wl.release();


    }

    @Override
    protected void onStart() {
        super.onStart();

        manager.cancel(1);
        steeringSocketService.mainActivitySleep(false);
    }

    protected void onStop() {
        super.onStop();

        // rozlacz();


        if (polaczony == true) {
            builder.setContentText("Polaczony");
            manager.notify(1, builder.build());
            steeringSocketService.mainActivitySleep(true);
        }


    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            if (!men.closeMENU()) {
                rozlacz();
                stopService(service);
                finish();
            }

            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {

            pilot.przyciski1.volume(true, false);


            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            pilot.przyciski1.volume(false, false);

            return true;
        }

        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            pilot.przyciski1.volume(true, true);

            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            pilot.przyciski1.volume(false, true);

            return true;
        }

        super.onKeyDown(keyCode, event);
        return true;
    }

    @Override
    public void onBackPressed() {
        System.gc();

        if (!men.closeMENU()) {
            if (polaczony) {
                Toast.makeText(kontext, "przytrzymaj dluzej aby zakończyć", 2000).show();
                this.moveTaskToBack(true);
            } else {
                rozlacz();
                stopService(service);
                finish();
            }
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_MENU) {
            men.openclose();
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        manager.cancel(1);
        rozlacz();
    }

    public void onResume() {

        super.onResume();
        if (!polaczony) {
            connect();
        }

        if (screenManager)
            wl.acquire(wlTimeout);

    }




    /*class ClientThread implements Runnable {



        @Override
        public void run()
        {




    }
}*/

    private void connect() {
        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);


        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {

            if (mainWifi.getWifiState() == 1) {
                DialogFragment wifi = new WiFiDialog();
                wifi.show(getFragmentManager(), "asd");
            } else if (mainWifi.getConnectionInfo().getSupplicantState() != SupplicantState.COMPLETED) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                //finish();
            } else {
                lacz(file.read("server"));
            }

        } else {
            lacz(file.read("server"));
        }
    }

    private void menuOptionsInitiate() {
        MenuOptions menuoptions = new MenuOptions(this);

        menuoptions.motywInitiate();

        menuoptions.wygaszaczInitiate();

    }

    /**
     * end of viewFlipper's animations
     */


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
                    .setNegativeButton("Dalej", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    })
                    .setPositiveButton("Włącz", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mainWifi.setWifiEnabled(true);

                            while (!mainWifi.isWifiEnabled()) {
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

                            if (mainWifi.getConnectionInfo().getSupplicantState() != SupplicantState.COMPLETED) {
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                // finish();
                            }


                            Thread t = new Thread() {
                                @Override
                                public void run() {

                                    loading(true);
                                    try {
                                        //check if connected!
                                        while (!isConnected(MainActivity.this)) {
                                            //Wait to connect
                                            Thread.sleep(1000);
                                        }
                                        Log.d("loading", "loaded");


                                        loading(false);


                                    } catch (Exception e) {
                                    }
                                }
                            };
                            t.start();


                        }
                    });
            return builder.create();
        }

    }


}







