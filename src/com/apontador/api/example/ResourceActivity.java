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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResourceActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resource);
		
		Bundle extras = getIntent().getExtras();
		String grantType = extras.getString("grantType");
		String username  = extras.getString("username");
		String password  = extras.getString("password");
		String clientId  = null;
		String clientSecret = null;
		
		//distinct credentials for distinct grantType permissions
		if (grantType.equalsIgnoreCase("password")) {
			clientId = "my-trusted-client-with-secret";
			clientSecret = "somesecret";	
		} else {
			clientId = "my-client-with-secret";
			clientSecret = "secret";
		}
		
		String oauthData = getOAuthData(grantType, clientId, clientSecret, username, password, "read", "http://ec2-75-101-246-209.compute-1.amazonaws.com:8080/api/oauth/token");
		
		TextView oauthText = (TextView) findViewById(R.id.oauth_data_details);
		oauthText.setText("oauth details:\n" + oauthData);
		
		String accessToken = getAccessToken(oauthData);
		String resourceURI = "http://ec2-75-101-246-209.compute-1.amazonaws.com:8080/api/places/nottinghill";
		
		String resourceData = getResourceData(resourceURI, accessToken);
		TextView resourceText = (TextView) findViewById(R.id.resource_details);
		resourceText.setText("resource details:\n" + resourceData);

	}
	
	public String getResourceData(String resourceURI, String accessToken) {
		
		Log.i(ResourceActivity.class.getName(), "GET: " + resourceURI);
		
        DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(resourceURI);
		getRequest.addHeader("accept", "application/json");
		getRequest.addHeader("authorization", "Bearer " + accessToken);
 
		HttpResponse response = null;
		try {
			response = httpClient.execute(getRequest);
		} catch (Exception e) {
			Log.e(ResourceActivity.class.getName(), e.getMessage());
		}
		
		return getResponseBody(response);
		
	}



	// just an example, be sure to use it inside an AsyncTask
	public String getOAuthData(String grantType, String clientId, String clientSecret, String username, String password, String scope, String endPoint) {

		Log.i(ResourceActivity.class.getName(), "Flow: " + grantType);
		
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(endPoint);
		String result = null;

		try {

			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("client_id", clientId));
			nameValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));
			nameValuePairs.add(new BasicNameValuePair("grant_type",grantType));

			if (grantType.equalsIgnoreCase("password")) {
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("password", password));
				nameValuePairs.add(new BasicNameValuePair("scope", scope));
			}
			
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
	
	private String getAccessToken(String oauthData) {
		String accessToken = null;
		try {
			JSONObject jsonObject = new JSONObject(oauthData);
			accessToken = jsonObject.getString("access_token");
			Log.i(ResourceActivity.class.getName(), "access_token: " + accessToken);
		} catch (JSONException e) {
			Log.e(ResourceActivity.class.getName(), e.getMessage());
		}
		
		return accessToken;
	}	

}
