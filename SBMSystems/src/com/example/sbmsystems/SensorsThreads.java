package com.example.sbmsystems;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.sbmsystems.NavigationActivity.PlaceholderFragment;
import com.example.sbmsystems.net.GettingSensorThread;
import com.example.sbmsystems.net.SendingThread;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SensorsThreads extends AsyncTask<Void, Void, String> {

	public GettingSensorThread mThread;
	static PlaceholderFragment activity;
	public Boolean notification;
	SensorsThreads(GettingSensorThread t, PlaceholderFragment p, Boolean b){
		mThread = t;		
		activity = p;
		notification = b;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		try {
			Socket mySocket = new Socket("192.168.123.123", 1994);
			mThread = new GettingSensorThread(mySocket);
			mThread.run();
			String gasData = mThread.gasData;
			String tempData = mThread.tempdata;
			SyncSensors w = new SyncSensors(activity,tempData,gasData, notification);
			w.execute();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
