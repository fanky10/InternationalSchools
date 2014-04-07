package org.intschools.southern.activities;

import org.intschools.southern.R;
import org.intschools.southern.constants.AppConstants;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SplashISLogoActivity extends SplashActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.intro_layout);
	}

	@Override
	protected Intent createIntent() {
		Integer userId = appSettings.getInt(AppConstants.SHARED_SETTINGS_USER_ID, -1);
		if(userId>0){
			return getLoginSuccessIntent(userId);
		}
		return new Intent(SplashISLogoActivity.this, LoginActivity.class);
	}

	@Override
	protected View getContainer() {
		return findViewById(R.id.splashIntroContainer);
	}

	@Override
	protected int getDisplayLength() {
		return getResources().getInteger(R.integer.intro_display_length);
	}

}