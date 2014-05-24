package com.example.socketclient;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Timer;


/**
 * Created by franqy on 09.05.14.
 */
public class connection extends Service implements SteeringSocketService, AsyncResponse {

    static connecting myAsync = new connecting();



    private static ObjectOutputStream oos;


    private static Socket socket;

    private static boolean connected = false;
    private static boolean sleep = false;


    private static boolean normalOn = false;
    private static boolean sleepOn = false;


    private  final AsyncResponse delegate = new MainActivity();


    private static AsyncPingNormal pingNormal;
    private static AsyncPingSleep pingSleep;




    @Override
    public void onCreate() {
        // TODO Auto-generated method stub


        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void closeSocket() {
        connected = false;

        myAsync.closeSocket();


        Log.d("Service", "close Sock");


    }

    @Override
    public void newSock(String servName) {

        Log.d("Service", servName);
        myAsync.closeSocket();
        myAsync = new connecting();


        myAsync.delegate = new MainActivity();

        myAsync.execute(servName);
        Log.d("Service", "start Sock");

    }

    @Override
    public void mainActivitySleep(boolean sleep) {
        this.sleep = sleep;

        if (connected) {


            if (sleep) {
                normalOn = false;

                pingSleep = new AsyncPingSleep();
                sleepOn = true;
                pingSleep.execute(socket);


            } else {

                sleepOn = false;
                normalOn = true;

                pingNormal = new AsyncPingNormal();
                pingNormal.execute(socket);

            }

            Log.d("Ping", "Sleep change state");
        }


    }


    @Override
    public void processFinish(int output) {
    }


    @Override
    public void processFinish(ObjectOutputStream output, OutputStream os, InputStream is, Socket sock) {
        oos = output;
        socket = sock;
        connected = true;

        normalOn = true;

        pingNormal = new AsyncPingNormal();
        pingNormal.execute(sock);




        Log.d("Ping", "start ");


    }




    class AsyncPingNormal extends AsyncTask<Socket, Void, Integer> {


        @Override
        protected Integer doInBackground(Socket... sockets) {


            Ping ping = new Ping();


            while (normalOn) {
                try {
                    ping.liczba = 2;


                    long sendTime = System.currentTimeMillis();

                    oos.writeObject(ping);
                    oos.reset();
                    oos.flush();
                    Log.d("Ping", "send ");


                    ObjectInputStream ois = new ObjectInputStream(sockets[0].getInputStream());


                     sockets[0].setSoTimeout(500);
                    ping = (Ping) ois.readObject();


                    Log.d("Ping", "Get " + String.valueOf(System.currentTimeMillis() - sendTime));


                } catch (SocketTimeoutException e) {
                    connected = false;
                    normalOn = false;


                    Log.d("Ping", "Stop ");
                    return 1;

                } catch (OptionalDataException e) {
                    e.printStackTrace();
                } catch (EOFException e) {



                    connected = false;
                    normalOn = false;


                    Log.d("Ping", "Stop ");

                    if(ping.liczba == -1)
                        return -1;
                    else
                        return 1;

                } catch (StreamCorruptedException e) {
                    e.printStackTrace();

                    connected = false;
                    normalOn = false;


                    Log.d("Ping", "Stop ");

                    if(ping.liczba == -1)
                        return -1;
                    else
                        return 1;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if (connected) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }


            return 0;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (integer == -1) {
                if (sleep) {
                    delegate.processFinish(0);
                } else {
                    delegate.processFinish(-1);
                }
            }
            else if(integer == 1)
            {
                delegate.processFinish(1);
            }

        }


    }


    class AsyncPingSleep extends AsyncTask<Socket, Void, Integer> {


        @Override
        protected Integer doInBackground(Socket... sockets) {


            Ping ping = new Ping();


            while (sleepOn) {

                try {
                    ping.liczba = 10;


                    long sendTime = System.currentTimeMillis();

                    oos.writeObject(ping);
                    oos.reset();
                    oos.flush();
                    Log.d("Ping", "send ");


                    ObjectInputStream ois = new ObjectInputStream(sockets[0].getInputStream());


                    sockets[0].setSoTimeout(1000);
                    ping = (Ping) ois.readObject();


                    Log.d("Ping", "Get " + String.valueOf(System.currentTimeMillis() - sendTime));


                } catch (OptionalDataException e) {
                    e.printStackTrace();
                } catch (EOFException e) {

                    connected = false;
                    sleepOn = false;


                    Log.d("Ping", "Stop ");



                        return -1;

                } catch (StreamCorruptedException e) {
                    e.printStackTrace();

                    connected = false;
                    sleepOn = false;


                    Log.d("Ping", "Stop ");


                        return -1;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


                if (connected) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }


            return 0;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (integer == -1) {
                if (sleep) {
                    delegate.processFinish(0);
                } else {
                    delegate.processFinish(-1);
                }
            }

        }


    }




}
