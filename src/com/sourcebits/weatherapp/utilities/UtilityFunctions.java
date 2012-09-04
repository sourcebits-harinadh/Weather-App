package com.sourcebits.weatherapp.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import com.google.android.maps.GeoPoint;

/**
 * @author Harinadh
 * UtilityFunctions class for use full methods.
 */
public class UtilityFunctions {

	/** Tag for class fully qualified class name.*/
	private static final String TAG =  UtilityFunctions.class.getCanonicalName();


	/**
	 * @param geo location point.
	 * @param context
	 * @return the address string or null;
	 */
	public static String getAddressFromLatAndLong(GeoPoint point,Context context){
		Geocoder geoCoder = null;
		String address = null;
		List<Address> addressesList = null;
		try {
			geoCoder = new Geocoder(context, Locale.getDefault());

			addressesList = geoCoder.getFromLocation(point.getLatitudeE6()/1E6,point.getLongitudeE6()/1E6,1);

			if (addressesList!=null && addressesList.size() > 0) 
			{	
				address = "";
				for (int i=0; i<addressesList.get(0).getMaxAddressLineIndex();i++)
					address += addressesList.get(0).getAddressLine(i) + "\n";
			}

			return address;
		}
		catch (IOException e) {                
			Log.d(TAG, "Exception in getAddressFromLatAndLong():"+e);
		} finally{
			geoCoder = null;
			addressesList = null;
			point = null;
			context = null;
		}
		return address;
	}

	/**
	 * @param context
	 * @param city name.
	 * @return the GeoPoint for the given location;
	 */
	public static GeoPoint getLatAndLongFromAddress(Context context,String cityName){

		GeoPoint point = null;
		Geocoder geoCoder = null;
		Address address = null;
		List<Address> addressesList = null;
		try {
			geoCoder = new Geocoder(context, Locale.getDefault());
			addressesList = geoCoder.getFromLocationName(cityName, 1);
			address = addressesList.get(0);
			if(address.hasLatitude() && address.hasLongitude()){
				point = new GeoPoint((int)(address.getLatitude()*1E6), (int)(address.getLongitude()*1E6));
			}
		}
		catch (IOException ex) {                
			Log.d(TAG, "Exception in getLatandLongFromAddress():"+ex);
		} finally{
			geoCoder = null;
			addressesList = null;
			context = null;
			address = null;
		}


		return point;
	}
	/**
	 * Method to check the WIFI and MOBILE network availability.
	 * @param context
	 * @returns the boolean value indicates the network connected or not.
	 */
	public static boolean isNetworkConnected(Context context)
	{
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo)
		{
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
			{
				if (ni.isConnected())
				{
					haveConnectedWifi = true;
					break;

				} 
			}
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
			{
				if (ni.isConnected())
				{
					haveConnectedMobile = true;
					break;
				}
			}
		}
		return haveConnectedWifi || haveConnectedMobile;
	}


	/**
	 * 
	 * @param encodedString
	 * @return bitmap (from given string)
	 */
	public static Bitmap StringToBitMap(String encodedString){
		try{
			byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
			Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
			return bitmap;
		}catch(Exception e){
			Log.d(TAG, "Exception in StringToBitMap():"+e);
			return null;
		}
	}

	/**
	 * @param bitmap
	 * @return converting bitmap and return a string
	 */
	public static String BitMapToString(Bitmap bitmap){
		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
		byte [] b=baos.toByteArray();
		String temp=Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}


	public static long getForiegnTime(String locatioName){
		long milliSeconds = -1 ;
		try{
			
			
			// Given a local time of 10am, get the time in Japan
			// Create a Calendar object with the local time zone
			Calendar local =  Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			local.setTimeInMillis(System.currentTimeMillis());
			
			// Create an instance using Japan's time zone and set it with the local UTC
			Calendar foriegnCal = Calendar.getInstance(TimeZone.getTimeZone(locatioName));
			long temp = local.getTimeInMillis();
		
//			foriegnCal.setTimeInMillis(temp);
			milliSeconds = foriegnCal.getTimeInMillis();

		}catch (Exception e) {
			Log.d(TAG, "Exception in getForiegnTime():"+e);
		}finally{


		}
		return milliSeconds;

	}
	
	/**
	 * function to split given input String using delimiter and stores it in a vector
	 * @param inputstring : contains the array of data
	 * @param delimiter : specifies the split word/character
	 * @return : returns the splited string array of characters.
	 * 
	 */
	public static String[] split(String inputstring, String delimiter) {

		Vector<String> vec = new Vector<String>();
		try {
			int i, j;
			boolean done = false;
			i = 0;
			j = 0;
			while (!done) {
				i = inputstring.indexOf(delimiter, j);
				if (i == -1) {

					if (j < inputstring.length()) {
						vec.addElement(inputstring.substring(j));
					} else {
						vec.addElement("");
					}
					done = true;
				} else {
					String x = inputstring.substring(j, i);

					vec.addElement(x);
					j = i;
					if (j < inputstring.length()) {
						j++;
					}
				}
			}

			return toStringArray(vec);

		} catch (Exception e) {
			Log.d(TAG,"Exception in ApplicationUtil split() :: [" + e.getMessage() + "]");
			return null;
		}


	}

	/**
	 * method which converts the given vector to string array
	 * @param strings : Vector
	 * @return string array 
	 */
	public static String[] toStringArray(Vector<String> strings) {

		String[] result = new String[strings.size()];

		try
		{	
			for (int i = 0; i < strings.size(); i++) {
				result[i] = strings.elementAt(i).toString();
			}
		}
		finally{
			strings =null;
		}
		return result;
	}

	
	
	
}
