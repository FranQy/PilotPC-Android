package com.example.socketclient;


import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by franqy on 24.09.13.
 * <p/>
 * Connecting to server program on PC
 */


class connecting extends AsyncTask<String, Void, Integer> {

    public AsyncResponse delegate = null;
    AsyncResponse delegatePing = new connection();


    Socket socket;
    InetSocketAddress SocketAdrr;
    PrintWriter out;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    OutputStream os;
    InputStream is;
    private static final int SERVERPORT = 8753;


    void closeSocket() {
        if (socket != null) {
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

        String servername = Strings[0].split(":")[0];


        socket = new Socket();

        Log.d("async", servername);

        if (socket.isConnected()) {


            try {
                socket.setKeepAlive(false);
                socket.setSoTimeout(1000);
                socket.getOutputStream().close();
                socket.getInputStream().close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Log.d("async", "dalej");


        SocketAdrr = new InetSocketAddress(servername, SERVERPORT);
        Log.d("async", "dalej2");
        if (!SocketAdrr.isUnresolved()) {
            socket = new Socket();
            try {


                socket.connect(SocketAdrr, 1000);
                Log.d("async", "dalej3");
            } catch (UnknownHostException e1) {
                // e1.printStackTrace();
            } catch (IOException e1) {
                //  e1.printStackTrace();
            }


            if (socket != null)
                if (socket.isConnected()) {


                    try {

                        /**
                         sending class
                         **/


                        Log.d("async", "sendingInfoStart");

                        oos = new ObjectOutputStream(socket.getOutputStream());


                        Connect firstSend = new Connect();

                        if (Strings[0].length() > 1)
                            firstSend.haslo = Strings[0].split(":")[2];

                        firstSend.nazwa = Build.MANUFACTURER + " " + Build.MODEL;
                        oos.writeObject(firstSend);
                        oos.reset();
                        oos.flush();

                        Log.d("async", "Sent info");


                        try {
                            ois = new ObjectInputStream(socket.getInputStream());
                            firstSend = (Connect) ois.readObject();
                            menu.polaczono(firstSend.nazwa);

                            if (new fileOperation().read(firstSend.nazwa) == "brak") {
                                new fileOperation().write(firstSend.nazwa, firstSend.nazwa + ":8753:" + Strings[0].split(":")[2]);
                            }


                            Log.d("async", "Get info");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                  /* if(out.checkError())
                    {
                    error = 1;
                        Log.d("async", "error 1");
                    }*/


                } else {
                    error = 2;
                    Log.d("async", "error 2");
                }
            //Close connection
            // socket.close();


        } else {
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
        if (result == 0) {
            delegatePing.processFinish(oos, os, is, socket);
            delegate.processFinish(oos, os, is, socket);
            Log.d("async", "ok, connected, send PrintWriter");
        } else {
            delegate.processFinish(result);
            Log.d("asynca", String.valueOf(result));
            closeSocket();


        }

    }


}

