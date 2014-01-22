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
import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class FeedReader extends AsyncTask<String, Void, ArrayList<HangoutEvent>> {
	private String feedURL;

	public FeedReader(String feedURL) {
		this.feedURL = feedURL;
	}

	@SuppressLint("SimpleDateFormat") @Override
	protected ArrayList<HangoutEvent> doInBackground(String... params) {
		ArrayList<HangoutEvent> list = new ArrayList<HangoutEvent>(0);
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser pullParser = factory.newPullParser();
			URL url = new URL(this.feedURL);
			InputStream inputStream = url.openStream();
			pullParser.setInput(inputStream, null);
			int eventType = pullParser.getEventType();
			HangoutEvent hangoutEvent = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					list = new ArrayList<HangoutEvent>();
					break;
				case XmlPullParser.START_TAG:
					if ("hangout".equals(pullParser.getName())) {
						hangoutEvent = new HangoutEvent();
					} else if ("name".equals(pullParser.getName())) {
						hangoutEvent.setName(pullParser.nextText());
					} else if ("time".equals(pullParser.getName())) {
						DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy H:m z");
						Date date = null;
						try {
							date = dateFormat.parse(pullParser.nextText());
						} catch (ParseException e) {
							return list;
						}
						Calendar gregorianCalendar = GregorianCalendar.getInstance();
						gregorianCalendar.setTime(date);
						hangoutEvent.setTime((GregorianCalendar) gregorianCalendar);
					} else if ("img".equals(pullParser.getName())) {
						hangoutEvent.setImageURL(pullParser.nextText());
					} else if ("url".equals(pullParser.getName())) {
						hangoutEvent.setEventURL(pullParser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if ("hangout".equals(pullParser.getName())) {
						list.add(hangoutEvent);
						hangoutEvent = null;
					}
					break;
				}
				eventType = pullParser.next();
			}
		} catch (MalformedURLException e) {
			return new ArrayList<HangoutEvent>(0);
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