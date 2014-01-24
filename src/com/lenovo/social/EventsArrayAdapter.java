package com.lenovo.social;

import java.util.ArrayList;
import java.util.Collections;

import com.lenovo.social.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventsArrayAdapter extends ArrayAdapter<Event> {
	private final Activity activity;
	private final ArrayList<Event> events;

	static class ViewHolder {
		public ImageView imageViewPicture;
		public TextView textViewName;
		public TextView textViewHashtags;
		public TextView textViewTime;
	}

	public EventsArrayAdapter(Activity activity, ArrayList<Event> events) {
		super(activity, R.layout.google_item, events);
		this.activity = activity;
		Collections.sort(events);
		this.events = events;


	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		java.text.DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(activity.getApplicationContext());
		java.text.DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(activity.getApplicationContext());
		if (events.get(position) instanceof GoogleEvent) {
			if (rowView == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				rowView = inflater.inflate(R.layout.google_item, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.imageViewPicture = (ImageView) rowView.findViewById(R.id.imageViewPicture);
				viewHolder.textViewName = (TextView) rowView.findViewById(R.id.textViewName);
				viewHolder.textViewTime = (TextView) rowView.findViewById(R.id.textViewTime);
				rowView.setTag(viewHolder);
			}
			ViewHolder holder = (ViewHolder) rowView.getTag();
			String hangoutEventName = events.get(position).getName();
			String hangoutEventTime = dateFormat.format(events.get(position).getTime().getTime());
			holder.textViewName.setText(hangoutEventName);
			if (System.currentTimeMillis() < events.get(position).getTime().getTimeInMillis()) {
				hangoutEventTime += " " + timeFormat.format(events.get(position).getTime().getTime());
			}
			holder.textViewTime.setText(hangoutEventTime);
		} else if (events.get(position) instanceof TwitterEvent) {
			if (rowView == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				rowView = inflater.inflate(R.layout.twitter_item, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.imageViewPicture = (ImageView) rowView.findViewById(R.id.imageViewPicture);
				viewHolder.textViewName = (TextView) rowView.findViewById(R.id.textViewName);
				viewHolder.textViewHashtags = (TextView) rowView.findViewById(R.id.textViewHashtags);
				viewHolder.textViewTime = (TextView) rowView.findViewById(R.id.textViewTime);
				rowView.setTag(viewHolder);
			}

			ViewHolder holder = (ViewHolder) rowView.getTag();
			TwitterEvent tevt = (TwitterEvent)events.get(position);
			String hangoutEventName = events.get(position).getName();
			String hangoutEventHashtag = tevt.getHashTags();
			String hangoutEventTime = dateFormat.format(events.get(position).getTime().getTime());
			holder.textViewName.setText(hangoutEventName);
			holder.textViewHashtags.setText(hangoutEventHashtag);
			if (System.currentTimeMillis() < events.get(position).getTime().getTimeInMillis()) {
				hangoutEventTime += " " + timeFormat.format(events.get(position).getTime().getTime());
			}
			holder.textViewTime.setText(hangoutEventTime);
		}
		return rowView;
	}
}