package com.example.sbmsystems.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ServerConnection implements Runnable {
    private ServerSocket ss;
    private ExecutorService threads;
    public static int LISTENING_PORT = 1994;
    private boolean serverIsAlive;
    {
        serverIsAlive = true;
        threads = Executors.newFixedThreadPool(20);
    }
    private void runSendingThread(Runnable r){
    	threads.execute(r);
    }
    public ServerConnection() {
        try {
            ss = new ServerSocket(LISTENING_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            serverIsAlive = false;
        }
    }

    public void run() {
        while (serverIsAlive) {
            Socket clientSocket = null;
            try {
                clientSocket = ss.accept();
            } catch (IOException e) {
                System.err.println("Exception has risen when clientSocket connect");
                e.printStackTrace();
            } finally {
                if (clientSocket != null) {
                    // TODO wykonac potrzebne operacje
                    // threads.execute(new ClientThread(clientSocket));
                }
            }
        }
    }

    public synchronized void stopServer() {
        serverIsAlive = false;
    }

    public synchronized boolean isAlive() {
        return serverIsAlive;
    }

}
