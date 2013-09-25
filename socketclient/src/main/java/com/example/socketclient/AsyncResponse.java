package com.example.socketclient;

import java.io.PrintWriter;

/**
 * Created by franqy on 24.09.13.
 *
 * Connect "conecting" async task to "MainActivity"
 */
public interface AsyncResponse {
    void processFinish(int output);
    void processFinish(PrintWriter output);
}
