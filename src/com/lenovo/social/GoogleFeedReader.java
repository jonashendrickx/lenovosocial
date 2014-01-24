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
import android.util.Log;

public class GoogleFeedReader extends AsyncTask<String, Void, ArrayList<GoogleEvent>> {
	@SuppressLint("SimpleDateFormat") @Override
	protected ArrayList<GoogleEvent> doInBackground(String... params) {
		ArrayList<GoogleEvent> list = new ArrayList<GoogleEvent>(0);
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();
			URL url = new URL(Server.GOOGLE_FEED_URL);
			InputStream inputStream = url.openStream();
			parser.setInput(inputStream, null);
			int eventType = parser.getEventType();
			GoogleEvent event = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					list = new ArrayList<GoogleEvent>();
					break;
				case XmlPullParser.START_TAG:
					if ("event".equals(parser.getName())) {
						event = new GoogleEvent();
						break;
					} else if ("name".equals(parser.getName())) {
						event.setName(parser.nextText());
						break;
					} else if ("time".equals(parser.getName())) {
						DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy H:m z");
						Date date = null;
						try {
							date = dateFormat.parse(parser.nextText());
						} catch (ParseException e) {
							return list;
						}
						Calendar gregorianCalendar = GregorianCalendar.getInstance();
						gregorianCalendar.setTime(date);
						event.setTime((GregorianCalendar) gregorianCalendar);
						break;
					} else if ("event_url".equals(parser.getName())) {
						event.setEventURL(parser.nextText());
						break;
					} else if ("recording_url".equals(parser.getName())) {
						event.setRecordingURL(parser.nextText());
						break;
					}
				case XmlPullParser.END_TAG:
					if ("event".equals(parser.getName())) {
						list.add(event);
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (MalformedURLException e) {
			return new ArrayList<GoogleEvent>(0);
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