package com.aenimastudio.nis.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;
import com.aenimastudio.nis.entities.LoginResponse;
import com.aenimastudio.nis.forms.UserForm;
import com.aenimastudio.nis.utils.LoginResponseJsonParser;

public class LoginActivity extends BaseActivity {
	private ProgressDialog ringProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		init();
	}

	private void init() {

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		final EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
		final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
		final LinearLayout warningContainer = (LinearLayout) findViewById(R.id.commonMenuTopWarning);
		OnTouchListener txtTouchedListener = new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				warningContainer.setVisibility(View.GONE);
				return false;
			}
		};
		
		txtUsername.setOnTouchListener(txtTouchedListener);
		txtPassword.setOnTouchListener(txtTouchedListener);
		
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				logMeIn(txtUsername.getText().toString(), txtPassword.getText().toString());
			}
		});
	}

	public void logMeIn(String username, String password) {
		if(username.isEmpty() || password.isEmpty()){
			showLoginFailed();
			return ;
		}
		String progressTitle = getResources().getString(R.string.login_waiting_server_title);
		String progressMsg = getResources().getString(R.string.login_waiting_server_msg);
		ringProgressDialog = ProgressDialog.show(LoginActivity.this, progressTitle, progressMsg, true);
		ringProgressDialog.setCancelable(false);
		UserForm form = new UserForm(username, password);
		new AsyncLoginTask(form).execute();
	}

	private void showErrorMessage(String error) {
		LinearLayout warningContainer = (LinearLayout) findViewById(R.id.commonMenuTopWarning);
		TextView txtWarning = (TextView) findViewById(R.id.warningTxt);
		txtWarning.setText(error);
		warningContainer.setVisibility(View.VISIBLE);
		if(ringProgressDialog!=null){
			ringProgressDialog.dismiss();
		}
	}

	private void showLoginFailed() {
		showErrorMessage(getResources().getString(R.string.login_failed_message));
	};

	private void showErrorMessage() {
		showErrorMessage(getResources().getString(R.string.login_error_message));
	}

	private void loginSuccess(UserForm userForm, Integer userId) {

		//save data and show news!
		SharedPreferences.Editor editor = appSettings.edit();
		editor.putString(AppConstants.SHARED_SETTINGS_NAME, userForm.getUsername());
		editor.putString(AppConstants.SHARED_SETTINGS_PASSWORD, userForm.getPassword());
		editor.putInt(AppConstants.SHARED_SETTINGS_USER_ID, userId);
		editor.putLong(AppConstants.SHARED_SETTINGS_MOD_TIME, System.currentTimeMillis());
		// Commit the edits!
		editor.commit();
		
		ringProgressDialog.dismiss();
		startActivity(getLoginSuccessIntent(userId));
		finish();
	}

	class AsyncLoginTask extends AsyncTask<Void, Void, String> {
		private Exception exception = null;
		private UserForm userForm;

		public AsyncLoginTask(UserForm userForm) {
			this.userForm = userForm;
		}

		protected String doInBackground(Void... args) {
			try {
				String postUrl = getWebAppUrl() + getResources().getString(R.string.login_page);
				String fieldUsername = getResources().getString(R.string.login_field_username);
				String fieldPassword = getResources().getString(R.string.login_field_password);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair(fieldUsername, userForm.getUsername()));
				nameValuePairs.add(new BasicNameValuePair(fieldPassword, userForm.getPassword()));

				HttpPost httppost = new HttpPost(postUrl);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

				HttpResponse response = new DefaultHttpClient().execute(httppost);
				String data = new BasicResponseHandler().handleResponse(response);
				return data;
			} catch (Exception ex) {
				exception = ex;
				return null;
			}
		}

		protected void onPostExecute(String data) {
			Log.d(AsyncLoginTask.class.getName(), "data: " + data);
			if (exception != null) {
				showErrorMessage();
			}
			//check data, act.
			if (data != null && !data.isEmpty()) {
				try {
					//check status
					LoginResponse loginResponse = new LoginResponseJsonParser().parse(data);
					if (loginResponse.getUserId() < 0) {
						//bad login
						showLoginFailed();
					} else {
						loginSuccess(userForm, loginResponse.getUserId());
					}
				} catch (JSONException ex) {
					showErrorMessage();
				}
			} else {
				showErrorMessage();
			}

		}

		public Exception getException() {
			return exception;
		}

		public void setException(Exception exception) {
			this.exception = exception;
		}

		public UserForm getUserForm() {
			return userForm;
		}

		public void setUserForm(UserForm userForm) {
			this.userForm = userForm;
		}
	}
}
