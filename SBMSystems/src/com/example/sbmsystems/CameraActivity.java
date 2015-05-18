package com.example.sbmsystems;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

// This class is not used for now, but may be in the future

public class CameraActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888; 
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        this.imageView = (ImageView) this.findViewById(R.id.imageView1);      
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
        startActivityForResult(cameraIntent, CAMERA_REQUEST); 
         
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
//            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
//            imageView.setImageBitmap(getLastImagePath());
        	File imgFile = new  File(getLastImagePath());
        	Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        	imageView.setImageBitmap(myBitmap);
        }  
    } 
    
    private String getLastImagePath() {
        final String[] imageColumns = { MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA };
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        Cursor imageCursor = managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns,
                null, null, imageOrderBy);
        if (imageCursor.moveToFirst()) {
            int id = imageCursor.getInt(imageCursor
                    .getColumnIndex(MediaStore.Images.Media._ID));
            String fullPath = imageCursor.getString(imageCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));
            return fullPath;
        } else {
            return "";
        }
    }
}