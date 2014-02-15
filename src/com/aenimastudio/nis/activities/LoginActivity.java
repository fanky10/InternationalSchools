package com.aenimastudio.nis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;

public class LoginActivity extends BaseActivity {

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
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				logMeIn(txtUsername.getText().toString(), txtPassword.getText().toString());
			}
		});
	}

	private void logMeIn(String username, String password) {
		Log.d(LoginActivity.class.getName(),"login me in with: "+username+" : "+password);
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.LOGIN_USERNAME_KEY, username);
		bundle.putString(AppConstants.LOGIN_PASSWORD_KEY, password);
		Intent intent = new Intent(getApplicationContext(), PostLoginBrowserActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
