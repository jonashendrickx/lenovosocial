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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;

public class FeedReaderActivity extends Activity {
	private ListView listViewEvents;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_reader);
		setupActionBar();
		
		listViewEvents = (ListView)findViewById(R.id.listViewEventReader);
		GoogleFeedReader reader1 = new GoogleFeedReader();
		TwitterFeedReader reader2 = new TwitterFeedReader();
		try {
			reader1.execute();
			reader2.execute();
		} catch (IllegalStateException exception) {
			Log.e("", exception.getMessage());
		}
		ArrayList<Event> hangoutEvents = new ArrayList<Event>();
		try {
			hangoutEvents.addAll(reader1.get());
			hangoutEvents.addAll(reader2.get());
			
		} catch (InterruptedException ee) {
		} catch (ExecutionException ee) {
		}
	
		EventsArrayAdapter adapter = new EventsArrayAdapter(this, hangoutEvents);
		listViewEvents.setAdapter(adapter);
		listViewEvents.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> l, View v, int position,
					long arg3) {
				if (l.getItemAtPosition(position) instanceof GoogleEvent) {
					GoogleEvent event = (GoogleEvent) l.getItemAtPosition(position);
					try {
						Intent intent;
						if (!event.getRecordingURL().equals("") && event.getRecordingURL() != null) {
							intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getRecordingURL()));
						} else {
							intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getEventURL()));
						}
						startActivity(intent);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					try {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Server.TWITTER_URL));
						startActivity(intent);
					} catch (ActivityNotFoundException e) {
						
					}
				}
			}
		});
		listViewEvents.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> l, View v, int position, long arg3) {
				if (l.getItemAtPosition(position) instanceof GoogleEvent) {
				GoogleEvent event = (GoogleEvent) l.getItemAtPosition(position);
				try {
					Intent intent = null;
					boolean done = false;
					if (!event.getEventURL().equals("") && event.getEventURL() != null) {
						intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getEventURL()));
						startActivity(intent);
						done = true;
					} else if (!done && !event.getRecordingURL().equals("") && event.getRecordingURL() != null) {
						intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getEventURL()));
						startActivity(intent);
					}
				} catch (ActivityNotFoundException e) {
				}
				} else {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Server.TWITTER_URL));
					startActivity(intent);
				}
				return true;
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
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
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
