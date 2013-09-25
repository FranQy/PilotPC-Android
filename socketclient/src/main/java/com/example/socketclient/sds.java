package com.example.socketclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by franqy on 18.08.13.
 */
public class sds extends Activity {

    Context context;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        String value1 = "yt'" + extras.getString(Intent.EXTRA_TEXT) + "&hd=1'";


          context = getApplicationContext();


        Intent intent = new Intent(context, MainActivity.class);




         intent.putExtra("yt", value1);

         startActivity(intent);

        finish();


    }



}