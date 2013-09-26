package com.example.socketclient;




import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;

import java.net.InetSocketAddress;
import java.net.Socket;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.wifi.SupplicantState;

import android.net.wifi.WifiManager;

import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;


@SuppressWarnings("ALL")
public class MainActivity extends Activity implements AsyncResponse {

    private Socket socket;
    Thread mojThread;
    Thread pilotThread;
    Button   menu;

    Context kontext;
    TextView blad;

    WifiManager mainWifi;

   public fileOperation nameFile;

    connecting myAsync = new connecting();


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

        myAsync.delegate = this;

nameFile = new fileOperation();
        socket = new Socket();
        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);



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




       /* pOdswierz.setText("sproboj jeszcze raz");

        pOdswierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                System.exit(0);




            }


        });*/





         mojThread = new Thread(new ClientThread());
       pilotThread = new Thread(new Pilot());


       // pilotThread.start();
        nameFile.read();


blad.setText("ad");
    }


    public void processFinish(PrintWriter output){
        //this you will received result fired from async class of onPostExecute(result) method.

        blad.setText("wygrałeś internety :D");
        Log.d("async", "end1");
    }

    public void processFinish(int output){
        //this you will received result fired from async class of onPostExecute(result) method.
switch (output)
{
    case 1:
    {
        blad.setText("wlacz sprogram na pc");
    }
    case 2:
    {
        blad.setText("wlacz pc");
    }
}

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

      myAsync.cancel();

    }






    class ClientThread implements Runnable {



        @Override
        public void run()
        {




    }
}




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
            myAsync.execute(servername);
            Log.d("reasd", "Myasync start");
           /* try {
                if(myAsync.execute(servername).get() == 1)
                {
                    pilotThread.start();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/


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







