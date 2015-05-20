package com.example.sbmsystems;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.example.sbmsystems.net.SendingThread;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A login screen that offers login via login/password.
 */

public class MainActivity extends Activity implements LoaderCallbacks<Cursor>,
		OnClickListener {
	protected static final int REQUEST_VOICE = 1;
	public UserLoginTask mAuthTask;
	// private static final int CAMERA_REQUEST = 1888;

	// UI references.
	public AutoCompleteTextView mloginView;
	public EditText mPasswordView;
	public static Socket mySocket;
	public static SendingThread mySendingThread;
	private View mProgressView;
	private View mLoginFormView;
	private AuthenticationStrategy mAuthenticationStrategy;
	private String serverAdress;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findViewById(R.id.speak).setOnClickListener(this);
		findViewById(R.id.photo).setOnClickListener(this);
		prefs = getSharedPreferences("SBMSystems.PreferenceFileKey", Context.MODE_PRIVATE);		
		
		// Set up the login form.
		mloginView = (AutoCompleteTextView) findViewById(R.id.Login);
		populateAutoComplete();
		mloginView.setText(prefs.getString("LOGIN", ""));
		serverAdress = "192.168.123.123";
		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							mAuthenticationStrategy = new LogPassAuthentication();
							mAuthenticationStrategy
									.attemptLogin(MainActivity.this);
							return true;
						}
						return false;
					}
				});

		Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mAuthenticationStrategy = new LogPassAuthentication();
				mAuthenticationStrategy.attemptLogin(MainActivity.this);

			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
	}

	// It can recognize spoken Polish if the user is connected to the Internet
	// and has pushed the button

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.speak:
			mAuthenticationStrategy = new VoiceAuthentication();
			mAuthenticationStrategy.attemptLogin(this);
			break;

		case R.id.photo:
			// Intent intent = new Intent(MainActivity.this,
			// CameraActivity.class);
			// startActivity(intent);
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivity(cameraIntent);
			break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_VOICE && resultCode == RESULT_OK) {
			ArrayList<String> thingsYouSaid = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			if (!thingsYouSaid.isEmpty()) {
//				Toast.makeText(this, thingsYouSaid.get(0),						
//							     Toast.LENGTH_SHORT).show();
				mAuthTask = new UserLoginTask(mloginView.getText().toString(), thingsYouSaid.get(0), "voice");
	            mAuthTask.execute((Void) null);	
			}
		}
	}

	private void populateAutoComplete() {
		getLoaderManager().initLoader(0, null, this);
	}

	public boolean isPasswordValid(String password) {
		// TODO: Replace this with your own logic
		return password.length() > 4;
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */

	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mloginView.setAdapter(adapter);
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;
		public final String howToLogIn;
		
		
		public UserLoginTask(String email, String password, String howTo) {
			mEmail = email;
			mPassword = password;
			howToLogIn = howTo;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			mySendingThread = null;
			try {
				mySocket = new Socket(serverAdress, 1994);
				mySendingThread = new SendingThread(mySocket);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (mySendingThread == null)
				return false;
			if(howToLogIn.equals("text")){
				mySendingThread.logginIn = "text";
			}
			else{
				mySendingThread.logginIn = "voice";
			}
			mySendingThread.login = mEmail;
			mySendingThread.password = mPassword;			
			mySendingThread.run();
			String result = mySendingThread.token;
			if (result.equalsIgnoreCase("FAIL")) {
				return false;
			} else {
				return true;
			}

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);		
			if (success) {
				
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("LOGIN",mEmail);
				editor.commit();
				Intent intent = new Intent(MainActivity.this,
						NavigationActivity.class);
				startActivity(intent);
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}
				
		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
