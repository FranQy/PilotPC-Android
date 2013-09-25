package com.example.socketclient;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by franqy on 24.09.13.
 *
 * Connecting to server program on PC
 */


class connecting extends AsyncTask<String, Void, Void> {

   public AsyncResponse delegate=null;

    int error= -1;

Socket socket;
    InetSocketAddress asddd;
    PrintWriter out;
    private static final int SERVERPORT = 12345;


    @Override
    protected void onPostExecute(Void result) {
        if(error == -1)
        {
            delegate.processFinish(out);
        }
        else
        {
            delegate.processFinish(1);
        }
        Log.d("async", "end");
    }

    protected Void doInBackground(String... Strings) {

        String servername = Strings[0];
        socket = new Socket();


        Log.d("async", servername);

        if(socket.isConnected())
        {
            Log.d("async", "socket test");
            try {
                socket.getOutputStream().close();
                socket.getInputStream().close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        Log.d("MyThread strt", "adasd");


        asddd = new InetSocketAddress(servername, SERVERPORT);

        if(!asddd.isUnresolved())
        {
            try {
                socket = new Socket();
                Log.e("Myad strt", "d");

                socket.connect(asddd, 1000);


                Log.e("MyThread strt", "adasd34");

                if(socket.isConnected())
                {
                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),
                            true);






                    Log.e("MyThread strt", "adasd1");
                    // pilotThread.start();

                }
                //Close connection
                // socket.close();




            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }





        }
        else
        {
            // TO DO
            //error sending, etc
            error = 1;
            // DialogFragment dialasd = new hostNameDialog();
            // dialasd.show(getFragmentManager(), "asd");
        }
        Log.d("async", "stop");
        return null;
    }


}

