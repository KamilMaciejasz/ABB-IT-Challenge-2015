package com.example.sbmsystems.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 
 * @author Kazoo
 * <p>This class is responsible for sending object to server
 */
public class SendingThread implements Runnable {
    Socket mySocket;
    public SendingThread(Socket s) {
        this.mySocket = s;
    }
    public void setState(){
      //TODO  
    }
    @Override
    public void run() {
       try{
       ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream()); 
       ObjectInputStream in = new ObjectInputStream(mySocket.getInputStream()); 
       //TODO stany wysylania
        }
        catch(Exception e){
           //TODO 
        }
        finally{
           try {
            mySocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
    }

}
