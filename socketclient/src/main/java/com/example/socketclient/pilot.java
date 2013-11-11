package com.example.socketclient;

import android.app.Activity;

import android.widget.TextView;



/**
 * Created by franqy on 03.10.13.
 * pilot v0.2
 */
public class pilot  {
    public static Activity activity;

    public static TextView blad;




    static przyciski przyciski1;

    static fileOperation file;




    public pilot(Activity activity)
   {


       this.activity=activity;
       file = new fileOperation();
       file.read();

       przyciski1 = new przyciski();




    }

  /*  public void showcos()
    {
        DialogFragment dialasd = new hostNameDialog();
        dialasd.show(pilot.activity.getFragmentManager(), "asd");
    }
*/
    public pilot() {

    }


}
