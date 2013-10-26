package com.example.socketclient;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;


/**
 * Created by franqy on 12.10.13.
 * MENU
 */
public class menu {

    ViewGroup mContainerView, infoContainerView;

     ViewGroup nameView, infoView;

    ClickableSlidingDrawer sliding;

    public static Activity activity;

    public menu(Activity activity)
    {
        this.activity=activity;

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


       Button nameButton = (Button) this.activity.findViewById(R.id.buttonNameChange);
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mContainerView.getChildCount()==0){
                addItem();
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






    private void addItem() {

        nameView.findViewById(R.id.buttondel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mContainerView.removeView(nameView);
            }
        });

        nameView.findViewById(R.id.saveName)  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//TODO: check Null pointer...

                pilot.file.write(((EditText) nameView.findViewById(R.id.servName)).getText().toString());
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

}


