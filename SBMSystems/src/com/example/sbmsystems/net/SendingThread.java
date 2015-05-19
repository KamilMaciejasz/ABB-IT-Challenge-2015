package com.example.sbmsystems.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author Kazoo
 *         <p>
 *         This class is responsible for sending object to server
 */
public class SendingThread implements Runnable {
	public Socket mySocket;
	public String login;
	public String password;
	public String token;
	
	public SendingThread(Socket s) {
		this.mySocket = s;
	}

	public void setState() {
		// TODO
	}

	@Override
	public void run() {
		try {
			PrintWriter in = new PrintWriter(mySocket.getOutputStream(), true);
			BufferedReader out = new BufferedReader(new InputStreamReader(
					mySocket.getInputStream()));
			in.println("android");
			in.println("authenticator");
			in.println("text");
			in.println(login);
			in.println(password);
		    token = out.readLine();
		    
			// TODO stany wysylania
		} catch (Exception e) {
			// TODO
		} finally {
			try {
				mySocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
