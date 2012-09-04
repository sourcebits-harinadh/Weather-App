package com.sourcebits.weatherapp;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.sourcebits.weatherapp.R.color;
import com.sourcebits.weatherapp.bussinessenities.TimeZoneEnity;
import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;
import com.sourcebits.weatherapp.serverconnections.ParserUtilities;
import com.sourcebits.weatherapp.serverconnections.ServerConnection;

/**
 * @author Harinadh
 * WeatherHomeActivity screen to hold the tab component.
 */
public class WeatherHomeActivity extends TabActivity implements OnClickListener{

	/** Tag for class fully qualified class name.*/
	private static final String TAG =  WeatherHomeActivity.class.getCanonicalName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_home);

		createTab();
	}

	/**
	 * This method creates the tab.
	 */
	private void createTab(){
		try{
			TabHost tabHost=getTabHost();
			
			//First Tab
			TabSpec locationsSpec=tabHost.newTabSpec(getString(R.string.locations_text)+getString(R.string.tab_text));
			locationsSpec.setIndicator(getString(R.string.locations_text),getResources().getDrawable(R.drawable.ic_launcher));
			Intent in1=new Intent(this,LocationsActivity.class);
			locationsSpec.setContent(in1);
		
			//Second Tab
			TabSpec weatherSpec=tabHost.newTabSpec(getString(R.string.weather_text)+getString(R.string.tab_text));
			weatherSpec.setIndicator(getString(R.string.weather_text),getResources().getDrawable(R.drawable.ic_launcher));
			Intent in2=new Intent(this,WeatherActivity.class);
			weatherSpec.setContent(in2);

			//Third Tab
			TabSpec mapSpec=tabHost.newTabSpec(getString(R.string.map_text)+getString(R.string.tab_text));
			mapSpec.setIndicator(getString(R.string.map_text),getResources().getDrawable(R.drawable.ic_launcher));
			Intent in3=new Intent(this,MapsActivity.class);
			mapSpec.setContent(in3);

//			//Fourth Tab
//			TabSpec settingsSpec=tabHost.newTabSpec(getString(R.string.settings_text)+getString(R.string.tab_text));
//			settingsSpec.setIndicator(getString(R.string.settings_text),getResources().getDrawable(R.drawable.ic_launcher));
//			Intent in4=new Intent(this,SettingsActivity.class);
//			settingsSpec.setContent(in4);


			tabHost.addTab(locationsSpec);
			tabHost.addTab(weatherSpec);
			tabHost.addTab(mapSpec);
//			tabHost.addTab(settingsSpec);
		}
		catch (Exception e) {
			Log.d(TAG, "Exception in createTab():"+e);	
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_weather_home, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		//		if(v.getId() == R.id.load){
		//			getWeatherInfoFromServer();
		//		}

	}

	


}
