package com.example.sbmsystems;

import java.util.ArrayList;

import com.example.sbmsystems.NavigationActivity.PlaceholderFragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.content.Intent;

public class SyncSensors extends AsyncTask<Void, Void, Void> {

	static PlaceholderFragment activity;
	TextView t1, t2, t3, t4, t5, t6;
	String gasData, tempData;
	Intent intent;
	static int counter;
	static Boolean notification;
	static Boolean existance;
	
	SyncSensors(PlaceholderFragment f, String t, String g, Boolean n) {
		activity = f;
		tempData = t;
		gasData = g;
		notification = n;
		try{
		t1 = (TextView) activity.getActivity().findViewById(R.id.textView1);
		t2 = (TextView) activity.getActivity().findViewById(R.id.textView2);
		t3 = (TextView) activity.getActivity().findViewById(R.id.textView3);
		t4 = (TextView) activity.getActivity().findViewById(R.id.textView4);
		t5 = (TextView) activity.getActivity().findViewById(R.id.textView5);
		t6 = (TextView) activity.getActivity().findViewById(R.id.textView6);
		}
		catch (NullPointerException e){
			return;
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		try{
		activity.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				{
					if (activity != null && activity.getActivity() != null) {
						t2.setText(gasData);
						t4.setText(tempData);
					}
				}
			}
		});	
		if(tempData == null || gasData == null)
			return null;
		
			if 
			((existance == null || existance == false) &&
					(Integer.parseInt(tempData) > 60)
					&& (notification == null || notification == true )) 
					
			{			
				intent = new Intent(activity.getActivity(),
						AlertDialogWarning.class);				
				activity.getActivity().startActivityForResult(intent, 3);
				SyncSensors.existance = false;
				
			}
		} catch (NullPointerException n) {
			Log.w("myApp", "nullptr");
			return null;
		}
		return null;
	}

}
