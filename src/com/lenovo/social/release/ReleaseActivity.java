package com.lenovo.social.release;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import com.lenovo.social.R;
import com.lenovo.social.R.id;
import com.lenovo.social.R.layout;

import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class ReleaseActivity extends ExpandableListActivity {
	private ArrayList<Release> releases;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_release);

		registerForContextMenu(getExpandableListView());

		ReleaseFeedReader reader = new ReleaseFeedReader();
		reader.execute();
		try {
			releases = reader.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadHosts(releases);
	}

	private void loadHosts(final ArrayList<Release> releases) {
		if (releases == null)
			return;

		this.releases = releases;
		if (getExpandableListAdapter() == null) {
			final ReleaseListAdapter adapter = new ReleaseListAdapter();
			setListAdapter(adapter);
		} else {
			((ReleaseListAdapter) getExpandableListAdapter())
					.notifyDataSetChanged();
		}
		for (int i = 0; i < getExpandableListAdapter().getGroupCount(); i++) {
			getExpandableListView().expandGroup(i);
		}
	}
	
	@Override
	public boolean onChildClick (ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		return true;
	}

	private class ReleaseListAdapter extends BaseExpandableListAdapter {
		private LayoutInflater inflater;

		public ReleaseListAdapter() {
			inflater = LayoutInflater.from(ReleaseActivity.this);

		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parentView) {
			final Release release = releases.get(groupPosition);
			convertView = inflater.inflate(R.layout.release_group_item,
					parentView, false);
			
			java.text.DateFormat dateFormat = android.text.format.DateFormat
					.getMediumDateFormat(getApplicationContext());
			((TextView) convertView.findViewById(R.id.textViewReleaseDate))
					.setText(dateFormat.format(release.getDate().getTime()));
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parentView) {
			final Release parent = releases.get(groupPosition);
			final Product product = parent.getProduct(childPosition);
			convertView = inflater.inflate(R.layout.release_child_item,
					parentView, false);
			((TextView) convertView
					.findViewById(R.id.textViewReleaseChildProductName))
					.setText(product.getName());
			return convertView;
		}

		@Override
		public Product getChild(int groupPosition, int childPosition) {
			return releases.get(groupPosition).getProduct(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return releases.get(groupPosition).getProductsCount();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return releases.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return releases.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}

		@Override
		public boolean isEmpty() {
			return ((releases == null) || releases.isEmpty());
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean areAllItemsEnabled() {
			return true;
		}
	}
}