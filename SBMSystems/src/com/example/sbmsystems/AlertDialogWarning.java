package com.example.sbmsystems;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AlertDialogWarning extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert_dialog_warning);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.warning)
	       .setTitle(R.string.dialog_title);
		
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   String postedBy = "512-442-493";
	        	   String uri = "tel:" + postedBy.trim() ;
	        	   Intent intent = new Intent(Intent.ACTION_CALL);
	        	   intent.setData(Uri.parse(uri));
	        	   startActivity(intent);
	        	   finish();
	           }
	       });
	builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // User cancelled the dialog
	        	   finish(); 
	           }
	       });
	
	AlertDialog dialog = builder.create();
	dialog.show();
	
}
	
}
