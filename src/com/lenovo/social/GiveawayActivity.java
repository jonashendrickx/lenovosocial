package com.lenovo.social;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;

public class GiveawayActivity extends Activity {
	private ListView listviewGiveaways;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_giveaway);
		setupActionBar();
		GiveawayFeedReader reader = new GiveawayFeedReader();
		try {
			reader.execute();
		} catch (IllegalStateException exception) {}
		ArrayList<Giveaway> giveaways = null;
		try {
			giveaways = reader.get();
		} catch (CancellationException e) {}
		catch (InterruptedException e) {}
		catch (ExecutionException e) {}
		listviewGiveaways = (ListView)findViewById(R.id.listViewGiveaways);
		GiveawayArrayAdapter adapter = new GiveawayArrayAdapter(this, R.id.listViewGiveaways, giveaways);
		listviewGiveaways.setAdapter(adapter);
		listviewGiveaways.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> l, View v, int position,
					long arg3) {
				Giveaway event = (Giveaway) l.getItemAtPosition(position);
				try {
					if (!event.getURL().equals("") && event.getURL() != null) {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getURL()));
						startActivity(intent);
					}
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
		getMenuInflater().inflate(R.menu.giveaway, menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
