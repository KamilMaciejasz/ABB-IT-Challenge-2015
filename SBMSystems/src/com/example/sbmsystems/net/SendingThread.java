package com.example.sbmsystems.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

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
	public String logginIn;
	public long session;
	public int task;

	private PrintWriter in;
	private BufferedReader out;

	public SendingThread(Socket s, int i) throws IOException {
		this.mySocket = s;
		in = new PrintWriter(mySocket.getOutputStream(), true);
		out = new BufferedReader(new InputStreamReader(
				mySocket.getInputStream()));
		task = i;
	}

	public void sessionCheck() throws IOException {
		in.println("android");
		in.println("authenticator");
		in.println(logginIn);
		in.println(login);
		in.println(session);
		token = out.readLine();		
	}

	public void logginCommunication() {
		try {
			in.println("android");
			in.println("authenticator");
			in.println(logginIn);
			in.println(login);
			in.println(password);
			token = out.readLine();
			session = Long.parseLong(out.readLine());
			// TODO stany wysylania
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				mySocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void run() {
		if (task == 0)
			logginCommunication();
		if (task == 1)
			try {
				sessionCheck();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
