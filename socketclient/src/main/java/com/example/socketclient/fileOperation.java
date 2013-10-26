package com.example.socketclient;



import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by franqy on 06.10.13.
 * change serv name etc
 */
public class fileOperation {
    connecting myAsync;
    public fileOperation()
    {
         myAsync= new connecting();
    }
    public fileOperation(boolean a)
    {

    }

    public void read()
    {

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




        //if(pilotThread.isAlive())
        // pilotThread.destroy();
        Log.d("reasd", "pilotThreadStop");

        // pilotThread = new Thread(new Pilot());
        Log.d("reasd", "pilotThreadMake");

        //test1();

        Log.d("reasd", "Myasync start");
lacz(servername);


    }


    public void write(String text)
    {
        File Direcotry = new File(String.valueOf(Environment.getExternalStorageDirectory() )+"/PilotTV/");
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

    lacz(text);
    }

    void lacz(String servname)
    {

        myAsync.closeSocket();
        myAsync = new connecting();
        MainActivity main;
        main = new MainActivity();

        myAsync.delegate = main;
        myAsync.execute(servname);
    }

    void rozlacz()
    {
        myAsync.closeSocket();
    }




}
