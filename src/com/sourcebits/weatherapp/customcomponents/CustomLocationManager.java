package com.sourcebits.weatherapp.customcomponents;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;

public class CustomLocationManager extends Object{

	private  static LocationManager locationManager = null;
	
	public static LocationManager getInstance(Activity caller){
		if(locationManager==null){
			locationManager = (LocationManager) caller.getSystemService(Context.LOCATION_SERVICE);
		}
		return locationManager;
	}
}
