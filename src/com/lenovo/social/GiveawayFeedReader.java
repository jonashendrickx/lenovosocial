package com.lenovo.social;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;

public class GiveawayFeedReader extends AsyncTask<String, Void, ArrayList<Giveaway>>{

	@Override
	protected ArrayList<Giveaway> doInBackground(String... params) {
		ArrayList<Giveaway> list = new ArrayList<Giveaway>(0);
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();
			URL url = new URL(Server.GIVEAWAY_FEED_URL);
			InputStream inputStream = url.openStream();
			parser.setInput(inputStream, null);
			int eventType = parser.getEventType();
			Giveaway giveaway = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("giveaway".equals(parser.getName())) {
						giveaway = new Giveaway();
						break;
					} else if ("name".equals(parser.getName())) {
						giveaway.setName(parser.nextText());
						break;
					} else if ("restrictions".equals(parser.getName())) {
						giveaway.setRestrictions(parser.nextText());
						break;
					} else if ("exp_date".equals(parser.getName())) {
						DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy H:m z");
						Date date = null;
						try {
							date = dateFormat.parse(parser.nextText());
						} catch (ParseException e) {
							return list;
						}
						Calendar gregorianCalendar = Calendar.getInstance();
						gregorianCalendar.setTime(date);
						giveaway.setExpirationDate((GregorianCalendar) gregorianCalendar);
						break;
					} else if ("url".equals(parser.getName())) {
						giveaway.setURL(parser.nextText());
						break;
					} else {
						break;
					}
				case XmlPullParser.END_TAG:
					if ("giveaway".equals(parser.getName())) {
						list.add(giveaway);
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (MalformedURLException e) {
			return new ArrayList<Giveaway>(0);
		} catch (XmlPullParserException e) {
			list.trimToSize();
			return list;
		} catch (IOException e) {
			list.trimToSize();
			return list;
		}
		list.trimToSize();
		return list;
	}

}
