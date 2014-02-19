package com.lenovo.social.release;

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

import com.lenovo.social.Server;

import android.os.AsyncTask;
import android.util.Log;

public class ReleaseFeedReader extends AsyncTask<String, Void, ArrayList<Release>>{

	@Override
	protected ArrayList<Release> doInBackground(String... params) {
		ArrayList<Release> list = new ArrayList<Release>(0);
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();
			URL url = new URL(Server.RELEASES_FEED_URL);
			InputStream inputStream = url.openStream();
			parser.setInput(inputStream, null);
			int eventType = parser.getEventType();
			Release release = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("release".equals(parser.getName())) {
						release = new Release();
						break;
					} else if ("date".equals(parser.getName())) {
						DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
						Date date = null;
						try {
							date = dateFormat.parse(parser.nextText());
						} catch (ParseException e) {
							return list;
						}
						Calendar gregorianCalendar = Calendar.getInstance();
						gregorianCalendar.setTime(date);
						release.setDate((GregorianCalendar) gregorianCalendar);
						break;
						} else if ("product".equals(parser.getName())) {
						Product product = new Product();
						product.setName(parser.nextText());
						//product.setURL(parser.getAttributeValue(parser.getNamespace(), "url"));
						//Log.e("", product.getURL());
						release.addProduct(product);
						break;
					} else {
						break;
					}
				case XmlPullParser.END_TAG:
					if ("release".equals(parser.getName())) {
						list.add(release);
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (MalformedURLException e) {
			return new ArrayList<Release>(0);
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
