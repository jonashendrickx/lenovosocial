package com.lenovo.social.socialevent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lenovo.social.Server;
import com.lenovo.social.json.FeedReader;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class TwitterFeedReader {
	public static ArrayList<TwitterEvent> get() {
		ArrayList<TwitterEvent> events = new ArrayList<TwitterEvent>();
		try {
			FeedReader reader = new FeedReader();
			reader.execute(Server.TWITTER_FEED_URL);
			JSONObject jsonResponse;
			jsonResponse = new JSONObject(reader.get());
			JSONArray jsonMainNode = jsonResponse.optJSONArray("twitter_event");
			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				TwitterEvent event = new TwitterEvent();
				event.setName(jsonChildNode.optString("name"));
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date = null;
				try {
					date = dateFormat.parse(jsonChildNode.optString("date"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar gregorianCalendar = Calendar.getInstance();
				gregorianCalendar.setTime(date);
				event.setTime((GregorianCalendar) gregorianCalendar);
				event.setHashTags(jsonChildNode.optString("hashtags"));
				events.add(event);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return events;
	}
}