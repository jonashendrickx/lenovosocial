package com.lenovo.social;

import java.util.ArrayList;

import com.lenovo.social.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HangoutsArrayAdapter extends ArrayAdapter<HangoutEvent> {
	private final Activity activity;
	private final ArrayList<HangoutEvent> hangoutEvents;

	static class ViewHolder {
		public ImageView imageViewPicture;
		public TextView textViewName;
		public TextView textViewTime;
	}

	public HangoutsArrayAdapter(Activity activity, ArrayList<HangoutEvent> hangoutEvents) {
		super(activity, R.layout.google_reader_item, hangoutEvents);
		this.activity = activity;
		this.hangoutEvents = hangoutEvents;
		
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		java.text.DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(activity.getApplicationContext());
		java.text.DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(activity.getApplicationContext());
			if (rowView == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				rowView = inflater.inflate(R.layout.google_reader_item, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.imageViewPicture = (ImageView) rowView.findViewById(R.id.imageViewPicture);
				viewHolder.textViewName = (TextView) rowView.findViewById(R.id.textViewName);
				viewHolder.textViewTime = (TextView) rowView.findViewById(R.id.textViewTime);
				rowView.setTag(viewHolder);
			}

			ViewHolder holder = (ViewHolder) rowView.getTag();
			String hangoutEventName = hangoutEvents.get(position).getName();
			String hangoutEventTime = dateFormat.format(hangoutEvents.get(position).getTime().getTime());
			holder.textViewName.setText(hangoutEventName);
			if (System.currentTimeMillis() < hangoutEvents.get(position).getTime().getTimeInMillis()) {
				hangoutEventTime += " " + timeFormat.format(hangoutEvents.get(position).getTime().getTime());
			}
			holder.textViewTime.setText(hangoutEventTime);
			Helper helper = new Helper(activity);
			if (helper.canDownload(activity)){
				if (!hangoutEvents.get(position).getImageURL().equals("") && hangoutEvents.get(position).getImageURL() != null) {
					String url = "http://lenovojonas.podserver.info/" + hangoutEvents.get(position).getImageURL();
					new BitmapDownloaderTask((ImageView)rowView.findViewById(R.id.imageViewPicture)).execute(url);
				}
			}
		return rowView;
	}
}