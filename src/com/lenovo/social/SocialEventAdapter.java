package com.lenovo.social;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import com.lenovo.social.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SocialEventAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<SocialEvent> social_events;

	public SocialEventAdapter(Activity activity) {
		this.activity = activity;
		GoogleFeedReader reader1 = new GoogleFeedReader();
		TwitterFeedReader reader2 = new TwitterFeedReader();
		reader1.execute();
		reader2.execute();

		this.social_events = new ArrayList<SocialEvent>();
		try {
			this.social_events.addAll(reader1.get());
			this.social_events.addAll(reader2.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(social_events);
	}

	public void addItem(final SocialEvent social_event) {
		social_events.add(social_event);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return social_events.size();
	}

	@Override
	public SocialEvent getItem(int position) {
		return social_events.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		SocialEvent social_event = social_events.get(position);
		if (social_event instanceof GoogleEvent) {
			return 0;
		} else {
			return 1;
		}
	}
	
	@Override
	public int getViewTypeCount() {
	    return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int viewType = this.getItemViewType(position);
		java.text.DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(activity.getApplicationContext());
		java.text.DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(activity.getApplicationContext());
		switch (viewType) {
		case 0:
			GoogleViewHolder google_holder = null;
			if (convertView == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				convertView = inflater.inflate(R.layout.google_item, parent, false); 

				google_holder = new GoogleViewHolder (); 
				google_holder.textViewGoogleName = (TextView) convertView.findViewById(R.id.textViewGoogleName);
				google_holder.textViewGoogleTime = (TextView) convertView.findViewById(R.id.textViewGoogleTime);
				convertView.setTag(google_holder); 
			} else { 
				google_holder = (GoogleViewHolder)convertView.getTag(); 
			} 

			GoogleEvent google_event = (GoogleEvent) social_events.get(position); 
			if (google_event != null) { 
				if (google_holder.textViewGoogleName != null) { 
					google_holder.textViewGoogleName.setText(google_event.getName()); 
				}
				if (google_holder.textViewGoogleTime != null) {
					String time = dateFormat.format(google_event.getTime().getTime());
					if (System.currentTimeMillis() < google_event.getTime().getTimeInMillis()) {
						time += " " + timeFormat.format(google_event.getTime().getTime());
					}
					google_holder.textViewGoogleTime.setText(time);
				}
			}
			return convertView;
		case 1:
			TwitterViewHolder twitter_holder = null;
			if (convertView == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				convertView = inflater.inflate(R.layout.twitter_item, parent, false); 

				twitter_holder = new TwitterViewHolder (); 
				twitter_holder.textViewTwitterName = (TextView) convertView.findViewById(R.id.textViewTwitterName);
				twitter_holder.textViewTwitterHashtags = (TextView) convertView.findViewById(R.id.textViewTwitterHashtags);
				twitter_holder.textViewTwitterTime = (TextView) convertView.findViewById(R.id.textViewTwitterTime);
				convertView.setTag(twitter_holder); 
			} else { 
				twitter_holder = (TwitterViewHolder)convertView.getTag(); 
			} 

			TwitterEvent twitter_event = (TwitterEvent) social_events.get(position); 
			if (twitter_event != null) { 
				if (twitter_holder.textViewTwitterName != null) { 
					twitter_holder.textViewTwitterName.setText(twitter_event.getName()); 
				}
				if (twitter_holder.textViewTwitterTime != null) {
					String time = dateFormat.format(twitter_event.getTime().getTime());
					if (System.currentTimeMillis() < twitter_event.getTime().getTimeInMillis()) {
						time += " " + timeFormat.format(twitter_event.getTime().getTime());
					}
					twitter_holder.textViewTwitterTime.setText(time);
				}
				if (twitter_holder.textViewTwitterHashtags != null) { 
					twitter_holder.textViewTwitterHashtags.setText(twitter_event.getHashTags()); 
				}
			}
			return convertView;
		}
		return parent;


	}

	static class GoogleViewHolder {
		public TextView textViewGoogleName;
		public TextView textViewGoogleTime;
	}

	static class TwitterViewHolder {
		public TextView textViewTwitterName;
		public TextView textViewTwitterTime;
		public TextView textViewTwitterHashtags;
	}
}