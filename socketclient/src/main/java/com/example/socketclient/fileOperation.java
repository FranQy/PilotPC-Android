package com.example.socketclient;

import android.util.Log;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by franqy on 06.10.13.
 * change serv name etc
 */
public class fileOperation {
    connecting myAsync= new connecting();
    public void read()
    {

        Log.d("M strt", "reading 0");
        String servername = "";

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

        //test1();

        Log.d("reasd", "Myasync start");
lacz(servername);


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




}
