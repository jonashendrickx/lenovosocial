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
public class GoogleFeedReader {
	public static ArrayList<GoogleEvent> get() {
		ArrayList<GoogleEvent> events = new ArrayList<GoogleEvent>();
		try {
			FeedReader reader = new FeedReader();
			reader.execute(Server.GOOGLE_FEED_URL);
			JSONObject jsonResponse;
			jsonResponse = new JSONObject(reader.get());
			JSONArray jsonMainNode = jsonResponse.optJSONArray("google_event");
			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				GoogleEvent event = new GoogleEvent();
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
				
				final String event_url = jsonChildNode.optString("event_url");
				if (!event_url.equals("null")) {
					event.setEventURL(event_url);
				}
				final String recording_url = jsonChildNode.optString("recording_url");
				if (!event_url.equals("null")) {
					event.setRecordingURL(recording_url);
				}
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