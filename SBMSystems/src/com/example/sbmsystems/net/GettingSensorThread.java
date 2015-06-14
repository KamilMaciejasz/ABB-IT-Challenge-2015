package com.example.sbmsystems.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import android.util.Log;

public class GettingSensorThread implements Runnable {
	public Socket mySocket;
	public String tempdata,gasData;

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

			while (id != null && !id.equalsIgnoreCase("end")) {
				id = out.readLine();
				Log.d("## TEST",id);
				if (id.equals("end"))
					return;
				desc = out.readLine();
				Log.d("## TEST",desc);
				data = out.readLine();
				Log.d("## TEST",data);
				if (id.equals("1")) {
					tempdata = data;
				}
				else if(id.equals("2")){
					gasData = data;
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
