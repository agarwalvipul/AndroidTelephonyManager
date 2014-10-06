package com.example.telephonymanager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneActivity extends FragmentActivity implements OnClickListener {

	private BroadcastReceiver mSentNotificationReceiver = new SentNotificationReceiver();
	private PhoneStateListener mPhoneStateListener = new CallStateListener();

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);

		setContentView(R.layout.phone);
		findViewById(R.id.send_system_call).setOnClickListener(this);

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		StringBuilder sb = new StringBuilder();
		sb.append(tm.getLine1Number());
		tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

		TextView tv = (TextView) findViewById(R.id.phone_info);
		tv.setText(sb.toString());
		IntentFilter filter = new IntentFilter();
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(mSentNotificationReceiver, filter);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.send_system_call:
			Intent systemCallIntent = new Intent(Intent.ACTION_DIAL,
					Uri.parse("tel:9560539869"));
			startActivity(systemCallIntent);
			Toast toast = Toast.makeText(getApplicationContext(), "Working",
					Toast.LENGTH_SHORT);
			toast.show();
			System.out.println("Working!");
			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		unregisterReceiver(mSentNotificationReceiver);
	}

	private class SentNotificationReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			TextView tv = (TextView) findViewById(R.id.phone_info);

			if (getResultCode() == Activity.RESULT_OK) {
				tv.setText("Message Sent Ok!");
			} else {
				tv.setText("Message could not be sent!");
			}
		}
	}

	/**
	 * Listener to detect incoming calls.
	 */
	private class CallStateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_OFFHOOK:
				Toast.makeText(getApplicationContext(),
						"Incoming: " + incomingNumber, Toast.LENGTH_LONG)
						.show();
				break;
			}
		}
	}
}
