package com.example.socketclient;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;


/**
 * Created by franqy on 12.10.13.
 * trololo
 */
public class menu extends Activity {

    ViewGroup mContainerView;

     ViewGroup nameView;



    public static Activity activity;

    public menu(Activity activity)
    {
        this.activity=activity;

        mContainerView = (ViewGroup) this.activity.findViewById(R.id.container2);

         nameView = (ViewGroup) LayoutInflater.from(this.activity).inflate(
                R.layout.list_item_example2, mContainerView, false);

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

}


