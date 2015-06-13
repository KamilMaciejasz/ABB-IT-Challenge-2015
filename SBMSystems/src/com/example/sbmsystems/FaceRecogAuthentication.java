package com.example.sbmsystems;

import android.content.Intent;

public class FaceRecogAuthentication implements AuthenticationStrategy {

	@Override
	public void attemptLogin(MainActivity activity) {
		// TODO Auto-generated method stub
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivity(cameraIntent);

	}

}
