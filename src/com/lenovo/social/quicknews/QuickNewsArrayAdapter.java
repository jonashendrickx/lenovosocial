package com.lenovo.social.quicknews;

import java.util.ArrayList;

import com.lenovo.social.R;
import com.lenovo.social.RssItem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class QuickNewsArrayAdapter extends ArrayAdapter<RssItem> {
	private final Activity activity;
	private final ArrayList<RssItem> items;

	static class ViewHolder {
		public TextView textViewTitle;
		public TextView textViewPublicationDate;
		public TextView textViewDescription;
	}

	public QuickNewsArrayAdapter(Activity activity, int resource,
			ArrayList<RssItem> items) {
		super(activity, resource, items);
		this.activity = activity;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		java.text.DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(activity.getApplicationContext());
		java.text.DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(activity.getApplicationContext());
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.rss_item_default, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.textViewTitle = (TextView) rowView.findViewById(R.id.textViewRssTitle);
			viewHolder.textViewPublicationDate = (TextView) rowView.findViewById(R.id.textViewRssPubDate);
			viewHolder.textViewDescription = (TextView) rowView.findViewById(R.id.textViewRssDescription);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.textViewTitle.setText(items.get(position).getTitle());
		holder.textViewPublicationDate.setText(String.format("%s %s", dateFormat.format(items.get(position).getPublicationDate().getTime()), timeFormat.format(items.get(position).getPublicationDate().getTime())));
		holder.textViewDescription.setText(items.get(position).getDescription());
		return rowView;
	}
}