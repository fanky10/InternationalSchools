package com.aenimastudio.nis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class SplashActivity extends BaseActivity implements Runnable {

	protected Handler delayHandler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.delayHandler = new Handler();
		setContentView();
		postDelayed();
		addClickeableContainer();
	}
	
	@Override
	public void onStop(){
		super.onStop();
		//prevents from the delayHandler thread start the following activity
		delayHandler.removeCallbacks(this);
		finish();
	}
	
	private void addClickeableContainer() {
		View container = getContainer();
		container.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	protected void postDelayed() {
		this.delayHandler.postDelayed(this, getDisplayLength());
	}

	public void run() {
		startActivity(createIntent());
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.delayHandler.removeCallbacks(this);
		this.run();
	}

	protected abstract int getDisplayLength();

	protected abstract void setContentView();

	protected abstract Intent createIntent();

	protected abstract View getContainer();

}
