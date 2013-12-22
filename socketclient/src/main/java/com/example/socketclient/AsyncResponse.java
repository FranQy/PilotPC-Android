package com.example.socketclient;

import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


/**
 * Created by franqy on 24.09.13.
 *
 * Connect "conecting" async task to "MainActivity"
 */
public interface AsyncResponse {
    void processFinish(int output);
    void processFinish(ObjectOutputStream output, OutputStream os, InputStream is);
}
