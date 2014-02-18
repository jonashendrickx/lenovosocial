package com.lenovo.social.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;

public class FeedReader extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... params) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(params[0]);
		String jsonResult = null;
		try {
			HttpResponse response = httpclient.execute(httppost);
			jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	private StringBuilder inputStreamToString(InputStream is) {
		String rLine = "";
		StringBuilder answer = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}
}
