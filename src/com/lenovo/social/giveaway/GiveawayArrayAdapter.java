package com.lenovo.social.giveaway;

import java.util.ArrayList;

import com.lenovo.social.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GiveawayArrayAdapter extends ArrayAdapter<Giveaway> {
	private final Activity activity;
	private final ArrayList<Giveaway> giveaways;

	static class ViewHolder {
		public TextView textViewName;
		
		public TextView labelRestrictions;
		public TextView textViewRestrictions;
		
		public TextView labelExpirationDate;
		public TextView textViewExpirationDate;
	}

	public GiveawayArrayAdapter(Activity activity, int resource,
			ArrayList<Giveaway> giveaways) {
		super(activity, resource, giveaways);
		this.activity = activity;
		this.giveaways = giveaways;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		java.text.DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(activity.getApplicationContext());
		java.text.DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(activity.getApplicationContext());
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.giveaway_item, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.textViewName = (TextView) rowView.findViewById(R.id.textViewName);
			viewHolder.labelRestrictions = (TextView) rowView.findViewById(R.id.labelRestrictions);
			viewHolder.textViewRestrictions = (TextView) rowView.findViewById(R.id.textViewRestrictions);
			viewHolder.textViewExpirationDate = (TextView) rowView.findViewById(R.id.textViewExpirationDate);
			viewHolder.labelExpirationDate = (TextView) rowView.findViewById(R.id.labelExpirationDate);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.textViewName.setText(giveaways.get(position).getName());
		holder.textViewRestrictions.setText(giveaways.get(position).getRestrictions());
		if (System.currentTimeMillis() < giveaways.get(position).getExpirationDate().getTimeInMillis()) {
			String giveawayExpirationDate = dateFormat.format(giveaways.get(position).getExpirationDate().getTime());
			giveawayExpirationDate += " " + timeFormat.format(giveaways.get(position).getExpirationDate().getTime());
			holder.textViewExpirationDate.setText(giveawayExpirationDate);
		}
		return rowView;
	}
}
