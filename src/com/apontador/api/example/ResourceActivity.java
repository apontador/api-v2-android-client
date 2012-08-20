package com.apontador.api.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResourceActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_resource);

		String oauthData = getOauthDataFromClientCredentialsFlow();
		TextView oauthText = (TextView) findViewById(R.id.oauth_data_details);
		oauthText.setText("oauth details: " + oauthData);

		TextView resourceText = (TextView) findViewById(R.id.resource_details);
		resourceText.setText("resource details: ");

	}

	// just an example, be sure to use it inside an AsyncTask
	public String getOauthDataFromClientCredentialsFlow() {

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://192.168.3.89:8080/api/oauth/token");
		String result = null;

		try {

			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("client_id",
					"my-client-with-secret"));
			nameValuePairs
					.add(new BasicNameValuePair("client_secret", "secret"));
			nameValuePairs.add(new BasicNameValuePair("grant_type",
					"client_credentials"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			return getResponseBody(response);

		} catch (Exception e) {
			Log.e(ResourceActivity.class.getName(), e.getMessage());
		}

		return result;
	}

	private static String getResponseBody(HttpResponse response) {

		String responseText = null;
		HttpEntity entity = null;

		try {

			entity = response.getEntity();
			responseText = getResponseBody(entity);

		} catch (Exception e) {
			Log.e(ResourceActivity.class.getName(), e.getMessage());
		}

		return responseText;
	}

	private static String getResponseBody(final HttpEntity entity)
			throws IOException, ParseException {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		InputStream instream = entity.getContent();

		if (instream == null) {
			return "";
		}

		if (entity.getContentLength() > Integer.MAX_VALUE) { 
			throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
		}

		String charset = getContentCharSet(entity);

		if (charset == null) {
			charset = HTTP.DEFAULT_CONTENT_CHARSET;
		}

		Reader reader = new InputStreamReader(instream, charset);
		StringBuilder buffer = new StringBuilder();

		try {
			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}

		} finally {
			reader.close();
		}

		return buffer.toString();
	}

	private static String getContentCharSet(final HttpEntity entity) throws ParseException {

		String charset = null;

		if (entity.getContentType() != null) {
			HeaderElement values[] = entity.getContentType().getElements();
			if (values.length > 0) {
				NameValuePair param = values[0].getParameterByName("charset");
				if (param != null) {
					charset = param.getValue();
				}
			}
		}

		return charset;
	}

}
