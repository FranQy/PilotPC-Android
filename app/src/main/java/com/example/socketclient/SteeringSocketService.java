package com.example.socketclient;

/**
 * Created by franqy on 09.05.14.
 */
public interface SteeringSocketService {

    void closeSocket();

    void newSock(String servName);

    void mainActivitySleep(boolean sleep);
}
