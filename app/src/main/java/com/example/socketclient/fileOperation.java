package com.example.socketclient;


import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by franqy on 06.10.13.
 * change serv name etc
 */
public class fileOperation {
    String wynik;

    public fileOperation() {
    }


    public String read(String name) {


        byte buf[] = new byte[512];


        FileInputStream fis = null;

        File file = MainActivity.baseKontext.getFileStreamPath(name + ".ppc");
        if (file.exists()) {
            try {


                fis = MainActivity.kontext.openFileInput(name + ".ppc");

                InputStreamReader inputreader = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(inputreader);


                // read every line of the file into the line-variable, on line at the time
                wynik = buffreader.readLine();
                // do something with the settings from the file


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            Log.d("fileOperation", wynik);
            return wynik;


        } else {
            Log.d("fileOperation", "blad");
            String blad = "brak";
            return blad;
        }
    }

/*
        Log.d("M strt", "reading 0");
        String servername = "";

        char buf[] = new char[512];

        FileReader rdr;

        try {

            rdr = new FileReader(String.valueOf(Environment.getExternalStorageDirectory() )+"/PilotTV/serverName.ptv");




            int s = rdr.read(buf);
            for(int k = 0; k < s; k++){
                servername+=buf[k];
                buf[k]='\0';
            }
        } catch (Exception e) {
            e.printStackTrace();

          //  pilot Pilot1 = new pilot();
           // Pilot1.showcos();
        }





        Log.d("FILE", servername);




        Log.d("reasd", "Myasync start");
lacz(servername);
*/


    public void write(String name, String text) {


        FileOutputStream fos = null;

        try {
            fos = MainActivity.kontext.openFileOutput(name + ".ppc", Context.MODE_PRIVATE);

            fos.write(text.getBytes());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

       /* File Direcotry = new File(String.valueOf(Environment.getExternalStorageDirectory() )+"/PilotTV/");

        if(!Direcotry.exists())
        {
            //TODO: check result of mkdirs
            Direcotry.mkdirs();
        }
        FileWriter fWriter;
        try{
            fWriter = new FileWriter(String.valueOf(Environment.getExternalStorageDirectory() )+"/PilotTV/serverName.ptv");

            fWriter.write(text);
            fWriter.flush();
            fWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    lacz(text);*/


}
