package com.aenimastudio.sis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aenimastudio.sis.R;

public class ModalLogoutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modal_logout_layout);
		init();
	}

	private void init() {
		Button btnLogout = (Button) findViewById(R.id.btnLogout);
		btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				acceptLogout();
			}
		});
	}

	@Override
	public void onBackPressed() {
		cancelLogout();
	}

	private void acceptLogout() {
		sendResult(RESULT_OK);
	}

	private void cancelLogout() {
		sendResult(RESULT_CANCELED);
	}

	private void sendResult(Integer result) {
		Intent returnIntent = new Intent();
		setResult(result, returnIntent);
		finish();
	}
}
