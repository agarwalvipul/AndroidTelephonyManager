package com.example.telephonymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);

		setContentView(R.layout.fragment_main);
	}

	public void goToNext(View view) {
		Intent i = new Intent(this, PhoneActivity.class);
		startActivity(i);
	}
}
