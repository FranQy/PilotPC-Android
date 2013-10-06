package com.example.socketclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



/**
 * Created by franqy on 03.10.13.
 * pilot v0.2
 */
public class pilot   {
    public static Activity activity;

    public TextView blad;




    static przyciski przyciski1;

    fileOperation file = new fileOperation();

   public pilot(Activity activity)
   {
    this.activity=activity;

       blad = (TextView) this.activity.findViewById(R.id.textView1);
       file.read();




       blad.setText("pilot act");

       przyciski1 = new przyciski();




//przyciski1.klawisze=true;



    }

    public pilot() {

    }


    public class hostNameDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Log.d("MyThread", "bla");
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            // Get the layout inflater
            LayoutInflater inflater = activity.getLayoutInflater();

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

                           file.write(((EditText) v.findViewById(R.id.kurwa)).getText().toString());
                            Log.d("M strt", "adasd bal");
                          //  nameFile.read();
                            // servername = ((EditText) v.findViewById(R.id.kurwa)).getText().toString();



                        }
                    });
            return builder.create();
        }
    }
}
