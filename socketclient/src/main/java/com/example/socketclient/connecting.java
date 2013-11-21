package com.example.socketclient;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
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


class connecting extends AsyncTask<String, Void, Integer> {

   public AsyncResponse delegate=null;



    Socket socket;
    InetSocketAddress SocketAdrr;
    PrintWriter out;
    ObjectOutputStream oos;
    private static final int SERVERPORT = 12345;


    void closeSocket()
    {
        if(socket!=null)
        {
            try {

                socket.getOutputStream().close();
                socket.getInputStream().close();
                socket.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket = null;
        }
    }


    @Override


    protected Integer doInBackground(String... Strings) {
        int error = 0;

        String servername = Strings[0];
        socket = new Socket();


        Log.d("async", servername);

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







        SocketAdrr = new InetSocketAddress(servername, SERVERPORT);

        if(!SocketAdrr.isUnresolved())
        {
            socket = new Socket();
            try {



                socket.connect(SocketAdrr, 1000);

            } catch (UnknownHostException e1) {
                // e1.printStackTrace();
            } catch (IOException e1) {
                //  e1.printStackTrace();
            }



                if(socket.isConnected())
                {
                    try {
                        /**
                         * sending class (later)
                         */


                        oos = new ObjectOutputStream(socket.getOutputStream());
                        //Przesylanie hasla przy polaczeniu
                       Connect dat = new Connect();
                       dat.haslo="123456";//tu podasz to, co wpisze urzytkownik lub zczytasz z QR
                        dat.nazwa="Android";
                        dat.wersja="0.0.1";//wersja aplikacji, nie wiem jak chcesz numerowac

                       oos.writeObject(dat);

                        oos.flush();
                       // oos.close();


                       /* out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);*/



                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                  /* if(out.checkError())
                    {
                    error = 1;
                        Log.d("async", "error 1");
                    }
*/







                }
                else
                {
                    error = 2;
                    Log.d("async", "error 2");
                }
                //Close connection
                // socket.close();










        }
        else
        {
            // TO DO
            //error sending, etc
            error = 3;
            Log.d("async", "error 3");
            // DialogFragment dialasd = new hostNameDialog();
            // dialasd.show(getFragmentManager(), "asd");
        }
        Log.d("async", "done");

        return error;
    }

    protected void onPostExecute(Integer result) {
        if(result==0)
        {
            delegate.processFinish(oos);
            Log.d("async", "ok, connected, send PrintWriter");
        }
        else
        {
            delegate.processFinish(result);
            Log.d("asynca", String.valueOf(result));
            closeSocket();


        }

    }



}

