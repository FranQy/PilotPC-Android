package com.example.socketclient;

import android.app.Activity;

import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ViewFlipper;


/**
 * Created by franqy on 12.10.13.
 * MENU
 */
public class menu {

    ViewGroup mContainerView, infoContainerView;

     ViewGroup nameView, infoView;

    ClickableSlidingDrawer sliding;

    ViewFlipper connectFlipper;



    public static Activity activity;

    public menu(Activity activity)
    {
        this.activity=activity;
        Button nameButton = (Button) this.activity.findViewById(R.id.buttonNameChange);



        sliding = (ClickableSlidingDrawer) this.activity.findViewById(R.id.slidingDrawer);

        mContainerView = (ViewGroup) this.activity.findViewById(R.id.container2);
        infoContainerView = (ViewGroup) this.activity.findViewById(R.id.infoContainer);

         nameView = (ViewGroup) LayoutInflater.from(this.activity).inflate(
                R.layout.list_item_example2, mContainerView, false);

       infoView = (ViewGroup) LayoutInflater.from(this.activity).inflate(
                R.layout.authors, infoContainerView, false);


        sliding.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                closeMENUall();

            }
        });









         final String TAG = "FragmentTabs";
         final String TAB_WORDS = "words";
         final String TAB_NUMBERS = "numbers";



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

        mContainerView.addView(nameView, 0);


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

    private void closeMENUall()
    {
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
}


