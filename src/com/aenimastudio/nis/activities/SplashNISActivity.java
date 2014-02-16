package com.aenimastudio.nis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aenimastudio.nis.R;

public class SplashNISActivity extends SplashActivity {

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
		return new Intent(SplashNISActivity.this, LoginActivity.class);
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