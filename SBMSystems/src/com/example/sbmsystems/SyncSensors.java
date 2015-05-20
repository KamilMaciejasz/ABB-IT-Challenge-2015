package com.example.sbmsystems;
import java.util.ArrayList;

import com.example.sbmsystems.NavigationActivity.PlaceholderFragment;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class SyncSensors extends AsyncTask<Void, Void, Void>{

	PlaceholderFragment activity;
	TextView t1, t2, t3, t4, t5, t6;
	
	SyncSensors(PlaceholderFragment f){
		activity = f;
		t1 = (TextView) activity.getActivity().findViewById(R.id.textView1);
		t2 = (TextView) activity.getActivity().findViewById(R.id.textView2);
		t3 = (TextView) activity.getActivity().findViewById(R.id.textView3);
		t4 = (TextView) activity.getActivity().findViewById(R.id.textView4);
		t5 = (TextView) activity.getActivity().findViewById(R.id.textView5);
		t6 = (TextView) activity.getActivity().findViewById(R.id.textView6);
	}
		
	
	
	@Override
	protected Void doInBackground(Void... params) {
		
	
		activity.getActivity().runOnUiThread(new Runnable() {
						@Override
			public void run() {
				{
					if (activity != null
							&& activity.getActivity() != null
							){
					t2.setText("YES");	
					t4.setText("1000 c");	
					}
				}
			}
		});		
		return null;
	}	

}
