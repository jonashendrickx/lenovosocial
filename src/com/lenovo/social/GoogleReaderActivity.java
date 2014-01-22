package com.lenovo.social;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;

public class GoogleReaderActivity extends Activity {
	private ListView listViewGoogleEvents;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_reader);
		setupActionBar();
		
		listViewGoogleEvents = (ListView)findViewById(R.id.listViewGoogleReader);
		FeedReader reader = new FeedReader("http://lenovojonas.podserver.info/hangouts.xml");
		reader.execute();
		ArrayList<HangoutEvent> hangoutEvents = null;
		try {
			hangoutEvents = reader.get();
		} catch (InterruptedException ee) {
			Log.w("LenovoHangoutException", "InterruptedException while retrieving XML");
		} catch (ExecutionException ee) {
			Log.w("LenovoHangoutException", "ExecutionException while retrieving XML");
		}
		HangoutsArrayAdapter hangoutsArrayAdapter = new HangoutsArrayAdapter(this, hangoutEvents);
		listViewGoogleEvents.setAdapter(hangoutsArrayAdapter);
		listViewGoogleEvents.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> l, View v, int position,
					long arg3) {
				HangoutEvent event = (HangoutEvent) l.getItemAtPosition(position);
				try {
					Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getURL()));
					startActivity(myIntent);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_reader, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.action_donate:
			try {
				Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=SM6YYQ8K4FP3E"));
				startActivity(myIntent);
			} catch (ActivityNotFoundException e) {
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
