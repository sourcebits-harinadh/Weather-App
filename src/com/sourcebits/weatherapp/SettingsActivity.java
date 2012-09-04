package com.sourcebits.weatherapp;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.Menu;

/**
 * @author Harinadh
 * SettingsActivity to enable and disable the app settings.
 */
public class SettingsActivity extends PreferenceActivity implements OnPreferenceClickListener{

	/** Tag for class fully qualified class name.*/
	private static final String TAG =  SettingsActivity.class.getCanonicalName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPreferenceScreen(createPreferences());
	}

	/**
	 * This method creates the PreferenceScreen object with multiple options. 
	 * @return PreferenceScreen object.
	 */
	private PreferenceScreen createPreferences() {
		PreferenceScreen rootElement = null;
		try{
			rootElement = getPreferenceManager().createPreferenceScreen(this);

			// Preference attributes
			PreferenceCategory prefAttrsCat = new PreferenceCategory(this);
			prefAttrsCat.setTitle(R.string.app_settings);
			rootElement.addPreference(prefAttrsCat);

			CheckBoxPreference weatherServicesCheckBox = new CheckBoxPreference(this);
			weatherServicesCheckBox.setTitle(R.string.weather_services);
			weatherServicesCheckBox.setSummary(R.string.turn_on);
			weatherServicesCheckBox.setDefaultValue(false);

			prefAttrsCat.addPreference(weatherServicesCheckBox);

			CheckBoxPreference GPSServicesCheckBox = new CheckBoxPreference(this);

			GPSServicesCheckBox.setTitle(R.string.gps);
			GPSServicesCheckBox.setSummary(R.string.turn_on);
			GPSServicesCheckBox.setDefaultValue(false);
			prefAttrsCat.addPreference(GPSServicesCheckBox);


		}
		catch (Exception e) {
			Log.d(TAG, "Exception in createPreferences():"+e);	
		}
		return rootElement;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_settings, menu);
		return true;
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		// TODO Auto-generated method stub
		String title = preference.getTitle().toString();
		try{
			//			if(title.equalsIgnoreCase(getString(R.string.weather_services))){
			//				preference.gets
			//				preference.setSummary(R.string.turn_off);
			//			}else if(title.equalsIgnoreCase(getString(R.string.gps))){
			//
			//			}
		}finally{
			title = null;
		}
		return true;
	}
}
