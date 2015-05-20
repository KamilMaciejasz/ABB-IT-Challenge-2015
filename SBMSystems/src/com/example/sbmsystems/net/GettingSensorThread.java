package com.example.sbmsystems.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class GettingSensorThread implements Runnable {
	public Socket mySocket;
	String id, desc, data;
	String idGas, idTemp;
	public GettingSensorThread(Socket s) {
		this.mySocket = s;
	}

	public void setState() {
	}

	@Override
	public void run() {

		try {
			PrintWriter in = new PrintWriter(mySocket.getOutputStream(), true);
			BufferedReader out = new BufferedReader(new InputStreamReader(
					mySocket.getInputStream()));
			in.println("android");
			in.println("request");
			in.println("sensors");
			String id = out.readLine();
			String desc;
			String data;
			
			if (!id.equalsIgnoreCase("begin")) {
				return;
			}
			ArrayList<Sensor> arr = new ArrayList<Sensor>();

			while (id != null && id.equalsIgnoreCase("end")) {
				id = out.readLine();
				if (id.equals("end"))
					return;
				desc = out.readLine();
				data = out.readLine();
				if (id != null && desc != null && data != null) {
					arr.add(new Sensor(id, desc, data));
				} else {
					return;
				}

			}
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
