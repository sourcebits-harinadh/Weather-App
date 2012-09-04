package com.sourcebits.weatherapp.serverconnections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * @author Harinadh
 * ServerConnection class for making connection and getting the response from the server.
 */
public class ServerConnection {

	/* Tag for class fully qualified class name.*/
	private static final String TAG =  ServerConnection.class.getCanonicalName();


	/**
	 * Method to get server response for input url.
	 * @param url for sever connection.
	 * @returns the response from server.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getServerRespnse(String url) throws ClientProtocolException,IOException,IllegalArgumentException,Exception{
		

		StringBuffer response = null;
		HttpClient client = null;
		HttpGet getRequest = null;
		HttpResponse httpResponse = null;
		StatusLine statusLine = null;
		HttpEntity entity  = null;
		InputStream inputStream = null;
		BufferedReader reader  = null;
		String line = null;
		try{

			client = new DefaultHttpClient();
			getRequest = new HttpGet(url);
			httpResponse =  client.execute(getRequest);
			statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				entity = httpResponse.getEntity();
				inputStream = entity.getContent();

				reader = new BufferedReader(new InputStreamReader(inputStream));
				response = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
			}else{
				Log.d(TAG, "Invalid response code:"+statusCode);
				return null;
			}

		}catch(ClientProtocolException ex){
			Log.d(TAG, "ClientProtocolException"+ex);
			throw ex;

		}catch(IOException ex){
			Log.d(TAG, "IOException"+ex);
			throw ex;
		}catch (IllegalArgumentException ex) {
			Log.d(TAG, ""+ex);
			throw ex;
		}
		catch(Exception ex){
			Log.d(TAG, ""+ex);
			throw ex;
		}finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					Log.d(TAG, "Closing the input stream"+e);
				}
			}
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					Log.d(TAG, "Closing the buffer reader"+e);
				}
			}
			url = null;
			client = null;
			getRequest = null;
			httpResponse = null;
			statusLine = null;
			entity  = null;
			inputStream = null;
			reader  = null;
			line = null;
		}

		return response.toString();
	}

	/**
	 * Method to down load the image from the server
	 * @param url
	 * @return Bitmap
	 */
	public static  Bitmap  downLoadImg(String url){
		URL myFileUrl =null;     
		try {
			myFileUrl= new URL(url);

			HttpURLConnection conn;

			conn = (HttpURLConnection)myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			String line = null;

			Bitmap  bmImg = BitmapFactory.decodeStream(is);

			BufferedReader  reader = new BufferedReader(new InputStreamReader(is));
			StringBuffer response = new StringBuffer();

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			return bmImg;
		} catch (MalformedURLException e) {
			Log.d(TAG, "MalformedURLException"+e.getMessage());
		}
		catch (IOException e) {
			Log.d(TAG, "IOException"+e.getMessage());
		}

		return null;
	}

	/**
	 * Method to get the encode url
	 * @param url
	 * @param queryString
	 * @return the encoded url.
	 */
	public static String getEncodedUrlString(String url,String queryString){
		String encodedQueryString = null;
		String encodedUrl = null;
		try {  

			encodedQueryString = URLEncoder.encode(queryString,"UTF-8");
			encodedUrl = url + encodedQueryString;
		}
		catch (UnsupportedEncodingException e)
		{
			Log.d(TAG, "UnsupportedEncodingException"+e.getMessage());
		}

		return  encodedUrl;
	}
}
