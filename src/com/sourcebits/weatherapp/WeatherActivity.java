package com.sourcebits.weatherapp;

import java.util.ArrayList;

import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;
import com.sourcebits.weatherapp.customcomponents.CirclePageIndicator;
import com.sourcebits.weatherapp.customcomponents.CustomPagerAdapter;
import com.sourcebits.weatherapp.database.WeatherDBHandler;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class WeatherActivity extends Activity {
	
	private ViewPager viewPager = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		if(viewPager==null)
		viewPager = (ViewPager)findViewById(R.id.weatherPager);
		ArrayList<WeatherAtLocation> locations = WeatherDBHandler.getAddedLocations(getApplicationContext());
		CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(locations,this,viewPager);
		viewPager.removeAllViews();
		viewPager.setAdapter(customPagerAdapter);
		viewPager.setCurrentItem(0);
		
		CirclePageIndicator mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);
        
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_weather, menu);
		return true;
	}

}
