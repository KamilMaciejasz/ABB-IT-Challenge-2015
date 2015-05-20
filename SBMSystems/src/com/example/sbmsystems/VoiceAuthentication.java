package com.example.sbmsystems;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

public class VoiceAuthentication implements AuthenticationStrategy {

	@Override
	public void attemptLogin(MainActivity activity) {
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);		 
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "pl");
        try {
            activity.startActivityForResult(i, activity.REQUEST_VOICE);
        } catch (Exception e) {
            Toast.makeText(activity, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
        

	}

}
