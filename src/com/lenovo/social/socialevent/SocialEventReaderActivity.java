package com.lenovo.social.socialevent;

import com.lenovo.social.R;
import com.lenovo.social.Server;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;

public class SocialEventReaderActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_reader);
		SocialEventAdapter adapter = new SocialEventAdapter(this);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		SocialEvent social_event = (SocialEvent) getListAdapter().getItem(position);
		if (social_event instanceof GoogleEvent) {
			GoogleEvent google_event = (GoogleEvent) social_event;
			Intent intent = null;
			if (!google_event.getEventURL().equals("") && google_event.getEventURL() != null) {
				intent = new Intent(Intent.ACTION_VIEW, Uri.parse(google_event.getEventURL()));

			} else if (!google_event.getRecordingURL().equals("") && google_event.getRecordingURL() != null) {
				intent = new Intent(Intent.ACTION_VIEW, Uri.parse(google_event.getEventURL()));
			}
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {}
		} else if (social_event instanceof TwitterEvent) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Server.TWITTER_URL));
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {}
		}
	}
}