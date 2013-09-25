package com.example.socketclient;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by franqy on 24.09.13.
 */
class connecting extends AsyncTask<String, Void, PrintWriter> {
    public AsyncResponse delegate=null;

Socket socket;
    InetSocketAddress asddd;
    PrintWriter out;
    private static final int SERVERPORT = 12345;


    @Override
    protected Integer doInBackground(String... Strings) {

        String servername = Strings[0];

        if(socket.isConnected())
        {
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

                socket.connect(asddd);

                Log.e("MyThread strt", "adasd34");

                if(socket.isConnected())
                {
                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),
                            true);






                    Log.e("MyThread strt", "adasd1");
                    // pilotThread.start();
                    return 1;
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

            // DialogFragment dialasd = new hostNameDialog();
            // dialasd.show(getFragmentManager(), "asd");
        }
        return 0;
    }

    protected void onPostExecute(Integer result) {
        delegate.processFinish(result);
    }
}