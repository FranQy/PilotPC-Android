package com.example.socketclient;

import android.app.Activity;

import android.app.FragmentManager;
import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ViewFlipper;


/**
 * Created by franqy on 12.10.13.
 * MENU
 */
public class menu {

    ViewGroup mContainerView, infoContainerView, wyswietlanieContainerView;

    public static ViewGroup nameView;
     static ViewGroup infoView, wyswietlanieView;

    Spinner wyswietlanieCzasSpinner, wyswietlanieTypSpinner;

    ClickableSlidingDrawer sliding;

    ViewFlipper connectFlipper;

 static QrReader Qr;

    public static Activity activity;
    public static Context kontext;
    public menu(Activity activity, Context kontext)
    {
        this.activity=activity;
        this.kontext=kontext;






        Button nameButton = (Button) this.activity.findViewById(R.id.buttonNameChange);



        sliding = (ClickableSlidingDrawer) this.activity.findViewById(R.id.slidingDrawer);

        mContainerView = (ViewGroup) this.activity.findViewById(R.id.container2);
        infoContainerView = (ViewGroup) this.activity.findViewById(R.id.infoContainer);
        wyswietlanieContainerView = (ViewGroup) this.activity.findViewById(R.id.wyswietlanieContainer);

         nameView = (ViewGroup) LayoutInflater.from(this.activity).inflate(
                R.layout.list_item_example2, mContainerView, false);

       infoView = (ViewGroup) LayoutInflater.from(this.activity).inflate(
                R.layout.authors, infoContainerView, false);


        wyswietlanieView = (ViewGroup) LayoutInflater.from(this.activity).inflate(R.layout.wyswietlanie, wyswietlanieContainerView, false);

        Qr = new QrReader(kontext, activity);


        sliding.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                closeMENUall();

            }
        });







        wyswietlanieCzasSpinner = (Spinner) wyswietlanieView.findViewById(R.id.wyswietlanieCzasSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterWyswietlanieCzas = ArrayAdapter.createFromResource(this.activity,
                R.array.wyswietlanieArrayCzas, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterWyswietlanieCzas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        wyswietlanieCzasSpinner.setAdapter(adapterWyswietlanieCzas);
        wyswietlanieCzasSpinner.setEnabled(false);


        wyswietlanieTypSpinner = (Spinner) wyswietlanieView.findViewById(R.id.wyswietlanieTypSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterWyswietlanieTyp = ArrayAdapter.createFromResource(this.activity,
                R.array.wyswietlanieArrayTyp, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterWyswietlanieTyp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

        wyswietlanieTypSpinner.setAdapter(adapterWyswietlanieTyp);
        wyswietlanieTypSpinner.setSelection(1);




      wyswietlanieCzasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              MainActivity.blad.setText(String.valueOf(i));




          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });


        wyswietlanieTypSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(MainActivity.wl.isHeld())
                     MainActivity.wl.release();

                switch(i)
                {
                    case 0:
                    {

                      //  MainActivity.wl.release();
                        MainActivity.screenManager = false;
                        MainActivity.blad.setText("normall");
                        break;
                    }
                    case 1:
                    {
                       // MainActivity.wl.release();
                        MainActivity.screenManager = true;
                        MainActivity.wl = MainActivity.pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "wygas ekran");
                        MainActivity.wl.acquire();
                        MainActivity.blad.setText("dim");
                        break;
                    }
                    case 2:
                    {

                        MainActivity.screenManager = true;
                        MainActivity.wl = MainActivity.pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "full wake lock");
                        MainActivity.wl.acquire(MainActivity.wlTimeout);
                        MainActivity.blad.setText("always on");
                        break;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        TabHost tabHost;

        tabHost=(TabHost)nameView.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec1=tabHost.newTabSpec("TAB 1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Search");


        TabHost.TabSpec spec2=tabHost.newTabSpec("TAB 2");
        spec2.setIndicator("Write ");
        spec2.setContent(R.id.tab2);


        TabHost.TabSpec spec3=tabHost.newTabSpec("TAB 3");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("Qr ");

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);


















        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mContainerView.getChildCount()==0){
                addConnect();
                }
                else
                {
                    mContainerView.removeView(nameView);
                }
            }
        });


        Button wyswietlanieButton = (Button) this.activity.findViewById(R.id.buttonWyswietlanie);
        wyswietlanieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wyswietlanieContainerView.getChildCount()==0){
                    addWyswietlanie();
                }
                else
                {
                    wyswietlanieContainerView.removeView(wyswietlanieView);
                }
            }
        });


       Button infoButton = (Button) this.activity.findViewById(R.id.buttonInfo);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(infoContainerView.getChildCount()==0){
                    addInfo();
                }
                else
                {
                    infoContainerView.removeView(infoView);
                }
            }
        });

    }







    private void addConnect() {

        nameView.findViewById(R.id.buttondel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mContainerView.removeView(nameView);
               /* if(Qr!=null)
                Qr.releaseCamera();
                Qr=null;*/
            }
        });

        nameView.findViewById(R.id.saveName)  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  pilot Pilot = new pilot();
                pilot.file.write(((EditText) nameView.findViewById(R.id.servName)).getText().toString());

                InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

                closeMENUall();
                close();
//TODO: check Null pointer...


            }
        });


       // Qr.ad();
        Qr.getIn();
        mContainerView.addView(nameView, 0);





    }

    private void addWyswietlanie()
    {


        wyswietlanieView.findViewById(R.id.closeWyswietlanie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wyswietlanieContainerView.removeView(wyswietlanieView);
            }
        });

        wyswietlanieContainerView.addView(wyswietlanieView, 0);

    }


    private void addInfo() {

        infoView.findViewById(R.id.closeInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoContainerView.removeView(infoView);
            }
        });
        infoContainerView.addView(infoView, 0);


    }

    public void closeMENUall()
    {
        Qr.releaseCamera();
       /* if(Qr!=null)
        Qr.releaseCamera();
        Qr=null;*/

        if(mContainerView.getChildCount()==1)
        {
            mContainerView.removeView(nameView);

        }
        if(infoContainerView.getChildCount()==1)
        {
            infoContainerView.removeView(infoView);

        }


    }

    public boolean closeMENU()
    {
        Qr.releaseCamera();
       /* if(Qr!=null)
        Qr.releaseCamera();
        Qr=null;*/
        if(infoContainerView.getChildCount()==1)
        {
            infoContainerView.removeView(infoView);
            return true;
        }
        else if(mContainerView.getChildCount()==1)
        {
            mContainerView.removeView(nameView);
            return true;
        }
        else if(sliding.isOpened())
        {
            sliding.animateClose();
            return true;
        }
       return false;
    }



    public  void openclose()
    {
        sliding.animateToggle();
    }

    public  void close()
    {
        if(sliding.isOpened())
            sliding.animateClose();
    }


   static public void polaczono(String Host)
    {
        ((TextView)infoView.findViewById(R.id.stanPolaczenia)).setText("-Stan: połączono");
        ((TextView)infoView.findViewById(R.id.hostTxt)).setText("-Host: "+Host);
    }

    public menu()
    {

    }
}


