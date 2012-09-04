package com.sourcebits.weatherapp.customcomponents;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sourcebits.weatherapp.R;
import com.sourcebits.weatherapp.bussinessenities.FutureWeatherInfoEntity;
import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;
import com.sourcebits.weatherapp.utilities.UtilityFunctions;

public class CustomDetailsAdapter extends PagerAdapter {

	/** Tag for class fully qualified class name.*/
	private static final String TAG =  CustomDetailsAdapter.class.getCanonicalName();

	private LayoutInflater inflater  = null;
	private View weatherDetailsView = null;
	private WeatherAtLocation weatherAtLocation = null;
	private int size;

	private ViewGroup vg =null;
	private Context context = null;
	public CustomDetailsAdapter(WeatherAtLocation weatherAtLocation, Context context,ViewGroup vg){
		this.weatherAtLocation = weatherAtLocation;
		this.context = context;
		if(this.weatherAtLocation!=null){
			size = this.weatherAtLocation.getFutureWeatherInfoEntity().length;
		}
		this.vg = vg;
	}
	public int getCount() {
		return size;
	}


	public Object instantiateItem(View collection, int position) {

		try{

			LayoutInflater inflater = (LayoutInflater) collection.getContext()
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			weatherDetailsView = inflater.inflate(R.layout.future_weather_details_layout,null);

			FutureWeatherInfoEntity data = (FutureWeatherInfoEntity)weatherAtLocation.getFutureWeatherInfoEntity()[position];

			TextView header = (TextView)weatherDetailsView.findViewById(R.id.locationHeader1);
			header.setText(weatherAtLocation.getQueryLocation());
			
			ImageView imageView = (ImageView)weatherDetailsView.findViewById(R.id.statusImg1);
			imageView.setImageBitmap(UtilityFunctions.StringToBitMap(data.getWeatherExtraInfoEnity().getImgData()));
		
			TextView desrip = (TextView)weatherDetailsView.findViewById(R.id.description1);
			desrip.setText(data.getWeatherExtraInfoEnity().getWeatherDesc()[0]);

			TextView date = (TextView)weatherDetailsView.findViewById(R.id.date1);
			date.setText(data.getDate());

			TextView maxTemp = (TextView)weatherDetailsView.findViewById(R.id.maxTempratureValue1);
			maxTemp.setText(data.getTempMaxC());

			TextView minTemp = (TextView)weatherDetailsView.findViewById(R.id.minTempratureValue1);
			minTemp.setText(data.getTempMinC());

			TextView windDirection = (TextView)weatherDetailsView.findViewById(R.id.windDirectionValue1);
			windDirection.setText(data.getWinddirection());
			
			TextView windDirection16Point = (TextView)weatherDetailsView.findViewById(R.id.windDir16PointValue1);
			windDirection16Point.setText(data.getWeatherExtraInfoEnity().getWinddir16Point());
			

			TextView windDirDegreeValue = (TextView)weatherDetailsView.findViewById(R.id.windDirDegreeValue1);
			windDirDegreeValue.setText(data.getWeatherExtraInfoEnity().getWinddirDegree());

			TextView speed = (TextView)weatherDetailsView.findViewById(R.id.windSpeedValue1);
			speed.setText(data.getWeatherExtraInfoEnity().getWindspeedKmph());
			
			TextView speed1 = (TextView)weatherDetailsView.findViewById(R.id.windSpeedinMiles1Value1);
			speed1.setText(data.getWeatherExtraInfoEnity().getWindspeedMiles());

			((ViewPager) collection).addView(weatherDetailsView, 0);

		}catch(Exception ex){
			Log.d(TAG, "Exception in instantiateItem():"+ex);
		}
		return weatherDetailsView;
	}


	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);

	}


	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);

	}


	
}
