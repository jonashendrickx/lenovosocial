package com.lenovo.social.giveaway;

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
public class GiveawayFeedReader {
	public static ArrayList<Giveaway> get() {
		ArrayList<Giveaway> list = new ArrayList<Giveaway>(0);
		try {
			FeedReader reader = new FeedReader();
			reader.execute(Server.GIVEAWAY_FEED_URL);
			JSONObject jsonResponse;
			if (reader.get() != null) {
				jsonResponse = new JSONObject(reader.get());
				JSONArray jsonMainNode = jsonResponse.optJSONArray("giveaway");
				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
					Giveaway giveaway = new Giveaway();
					giveaway.setName(jsonChildNode.optString("name"));
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date date = null;
					try {
						date = dateFormat.parse(jsonChildNode.optString("exp_date"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Calendar gregorianCalendar = Calendar.getInstance();
					gregorianCalendar.setTime(date);
					giveaway.setExpirationDate((GregorianCalendar) gregorianCalendar);
					final String url = jsonChildNode.optString("url");
					if (!url.equals("null")) {
						giveaway.setURL(url);
					}
					list.add(giveaway);
				}
			} else {
				return new ArrayList<Giveaway>(0);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return list;
	}

}
