package com.sourcebits.weatherapp.serverconnections;

import android.content.Context;

import com.google.android.maps.GeoPoint;
import com.sourcebits.weatherapp.MapsActivity;
import com.sourcebits.weatherapp.utilities.UtilityFunctions;

public class LocationsThread extends Thread {

	private String locations[] = null;
	private Context context = null;
	private MapsActivity activity = null;
	public LocationsThread(String locations[],Context context,MapsActivity activity){
		this.locations = locations;
		this.context = context;
		this.activity = activity;
	}
	
	@Override
	public void run() {
		for(int i=0;i<locations.length;i++)	{
			GeoPoint point = UtilityFunctions.getLatAndLongFromAddress(context, locations[i]);
			activity.locationFound(point, i);
		}
		
	}
}
