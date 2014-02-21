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

@SuppressLint("SimpleDateFormat")
public class RssFeedReader extends AsyncTask<String, Void, ArrayList<RssItem>> {

	@Override
	protected ArrayList<RssItem> doInBackground(String... params) {
			ArrayList<RssItem> list = new ArrayList<RssItem>(0);
			try {
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(false);
				XmlPullParser parser = factory.newPullParser();
				URL url = new URL(params[0]);
				InputStream inputStream = url.openStream();
				parser.setInput(inputStream, null);
				boolean insideItem = false;
				RssItem rssItem = null;
				int eventType = parser.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_TAG) {
						if (parser.getName().equalsIgnoreCase("item")) {
							insideItem = true;
							rssItem = new RssItem();
						} else if (parser.getName().equalsIgnoreCase("title")) {
							if (insideItem) {
								rssItem.setTitle(parser.nextText());
							}
						} else if (parser.getName().equalsIgnoreCase("description")) {
							if (insideItem) {
								rssItem.setDescription(parser.nextText());
							}
						} else if (parser.getName().equalsIgnoreCase("link")) {
							if (insideItem) {
								rssItem.setLink(parser.nextText());
							}
						} else if (parser.getName().equalsIgnoreCase("pubDate")) {
							if (insideItem) {
								DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
								Date date = null;
								try {
									date = df.parse(parser.nextText());
								} catch (ParseException e) {
									return list;
								}
								Calendar gcal = Calendar.getInstance();
								gcal.setTime(date);
								rssItem.setPublicationDate((GregorianCalendar) gcal);
							}
						}
					} else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
		                insideItem = false;
		                list.add(rssItem);
		            }

		            eventType = parser.next();
				}
			} catch (MalformedURLException e) {
				return new ArrayList<RssItem>(0);
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
