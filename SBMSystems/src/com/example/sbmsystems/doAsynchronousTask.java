package com.example.sbmsystems;

import java.util.TimerTask;

import android.os.Handler;
import android.util.Log;

import com.example.sbmsystems.NavigationActivity.PlaceholderFragment;
import com.example.sbmsystems.net.GettingSensorThread;

public class doAsynchronousTask {

	public GettingSensorThread mgGetting;
	public static PlaceholderFragment mPlaceholder;
	public Boolean noti;
	public TimerTask t;
	public Handler h;
	public void setTimerTask(){
		t = new TimerTask() {
			
			@Override
			public void run() {
				h.post(new Runnable() {
					public void run() {
						try {
							SensorsThreads mSensorThreads = new SensorsThreads(
									mgGetting, mPlaceholder,
									noti);
							mSensorThreads.execute();
							
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
	
//	doAsynchronousTask(GettingSensorThread g, PlaceholderFragment p,
//			boolean x, final Handler h) {
//		Log.w("lol", "kurwa");
//		mgGettingSensorThread = g;
//		mPlaceholderFragment = p;
//		notification = x;
//		t = new TimerTask() {
//			
//			@Override
//			public void run() {
////				h.post(new Runnable() {
////					public void run() {
////						try {
////							SensorsThreads mSensorThreads = new SensorsThreads(
////									mgGettingSensorThread, mPlaceholderFragment,
////									notification);
////							mSensorThreads.execute();
////
////						} catch (Exception e) {
////
////						}
////					}
////				});
//			}
//		};
		
//	}
	
	
}
	

	


