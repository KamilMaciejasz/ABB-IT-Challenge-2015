package com.example.sbmsystems;

import java.util.TimerTask;

import android.os.Handler;
import android.util.Log;

import com.example.sbmsystems.NavigationActivity.PlaceholderFragment;
import com.example.sbmsystems.net.GettingSensorThread;

public class doAsynchronousTask {

	public GettingSensorThread mgGetting;
	public static PlaceholderFragment mPlaceholder;
	public static Boolean noti;
	public TimerTask t;
	public Handler h;
	public static SensorsThreads mSensorsThreads;
	public void setTimerTask(){
		
		t = new TimerTask() {			
			@Override
			public void run() {
				h.post(new Runnable() {
					public void run() {
						try {
							
							mSensorsThreads = new SensorsThreads(
									mgGetting, mPlaceholder,
									noti);
							mSensorsThreads.execute();
							
						} catch (Exception e) {

						}
					}
				});
			}
		};
	}
	
	public doAsynchronousTask() {
		// TODO Auto-generated constructor stub
	}
	
}
	

	


