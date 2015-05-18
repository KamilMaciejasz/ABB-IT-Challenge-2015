package com.example.sbmsystems;

import com.example.sbmsystems.MainActivity.UserLoginTask;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

/**
 * Attempts to sign in or register the account specified by the login form.
 * If there are form errors (invalid email, missing fields, etc.), the
 * errors are presented and no actual login attempt is made.
 */   

public class LogPassAuthentication implements AuthenticationStrategy{
	
	@Override
	public void attemptLogin(MainActivity activity) {
		if (activity.mAuthTask != null) {
            return;
        }

        // Reset errors.
        activity.mloginView.setError(null);
        activity.mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String login = activity.mloginView.getText().toString();
        String password = activity.mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !activity.isPasswordValid(password)) {
            activity.mPasswordView.setError(activity.getString(R.string.error_invalid_password));
            focusView = activity.mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(login)) {
            activity.mloginView.setError(activity.getString(R.string.error_field_required));
            focusView = activity.mloginView;
            cancel = true;
        } else if (!activity.isEmailValid(login)) {
            activity.mloginView.setError(activity.getString(R.string.error_invalid_login));
            focusView = activity.mloginView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            activity.showProgress(true);
            activity.mAuthTask = activity.new UserLoginTask(login, password);
            activity.mAuthTask.execute((Void) null);
        }
		
	}
}
