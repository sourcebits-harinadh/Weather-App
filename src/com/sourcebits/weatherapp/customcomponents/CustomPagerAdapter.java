package com.sourcebits.weatherapp.customcomponents;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sourcebits.weatherapp.R;
import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;
import com.sourcebits.weatherapp.utilities.UtilityFunctions;

public class CustomPagerAdapter extends PagerAdapter {

	/** Tag for class fully qualified class name.*/
	private static final String TAG =  CustomPagerAdapter.class.getCanonicalName();

	private LayoutInflater inflater  = null;
	private View weatherDetailsView = null;
	private ArrayList<WeatherAtLocation> locations = null;
	private int size;

	private ViewGroup vg =null;
	private Context context = null;
	public CustomPagerAdapter(ArrayList<WeatherAtLocation> locations,Context context,ViewGroup vg){
		this.locations = locations;
		this.context = context;
		if(this.locations!=null){
			size = this.locations.size();
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

			weatherDetailsView = inflater.inflate(R.layout.weather_details_decsription_layout,null);

			final WeatherAtLocation data = (WeatherAtLocation)locations.get(position);


			TextView header = (TextView)weatherDetailsView.findViewById(R.id.locationHeader);
			header.setText(data.getQueryLocation());

			Button next = (Button)weatherDetailsView.findViewById(R.id.btnFutureWeather);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					preparePoupWindow(data);
				}
			});

			ImageView imageView = (ImageView)weatherDetailsView.findViewById(R.id.statusImg);
			imageView.setImageBitmap(UtilityFunctions.StringToBitMap(data.getFutureWeatherInfoEntity()[0].getWeatherExtraInfoEnity().getImgData()));

			TextView desrip = (TextView)weatherDetailsView.findViewById(R.id.description);
			desrip.setText(data.getFutureWeatherInfoEntity()[0].getWeatherExtraInfoEnity().getWeatherDesc()[0]);

			TextView date = (TextView)weatherDetailsView.findViewById(R.id.date);
			date.setText(data.getFutureWeatherInfoEntity()[0].getDate());

			TextView maxTemp = (TextView)weatherDetailsView.findViewById(R.id.maxTempratureValue);
			maxTemp.setText(data.getFutureWeatherInfoEntity()[0].getTempMaxC());

			TextView minTemp = (TextView)weatherDetailsView.findViewById(R.id.minTempratureValue);
			minTemp.setText(data.getFutureWeatherInfoEntity()[0].getTempMinC());

			TextView windDirection = (TextView)weatherDetailsView.findViewById(R.id.windDirectionValue);
			windDirection.setText(data.getFutureWeatherInfoEntity()[0].getWinddirection());

			TextView windDirDegreeValue = (TextView)weatherDetailsView.findViewById(R.id.windDirDegreeValue);
			windDirDegreeValue.setText(data.getFutureWeatherInfoEntity()[0].getWeatherExtraInfoEnity().getWinddirDegree());

			TextView speed = (TextView)weatherDetailsView.findViewById(R.id.windSpeedValue);
			speed.setText(data.getFutureWeatherInfoEntity()[0].getWeatherExtraInfoEnity().getWindspeedKmph());

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

	private void preparePoupWindow(WeatherAtLocation weatherAtLocation){

		View inflater = null;
		try{
			inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.detail_pager_layout,null,false);
			if (inflater != null) {
				WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
				final PopupWindow popupWindow = new PopupWindow(inflater);
				popupWindow.setFocusable(true);

				int screenWidth = wm.getDefaultDisplay().getWidth();
				int screenHeight = wm.getDefaultDisplay().getHeight();


				popupWindow.setWidth(screenWidth-80);
				popupWindow.setHeight(screenHeight-140);

				ViewPager viewPager = (ViewPager)inflater.findViewById(R.id.weatherDetailsPager);
				CustomDetailsAdapter customPagerAdapter = new CustomDetailsAdapter(weatherAtLocation,context,viewPager);
				viewPager.setAdapter(customPagerAdapter);
				viewPager.setCurrentItem(0);

				CirclePageIndicator mIndicator = (CirclePageIndicator)inflater.findViewById(R.id.circlePagerSample);
				mIndicator.setViewPager(viewPager);


				popupWindow.showAtLocation(viewPager, Gravity.CENTER, 0,0);

				Button btnDismiss = (Button)inflater.findViewById(R.id.dismiss);
				btnDismiss.setOnClickListener(new Button.OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						popupWindow.dismiss();
					}});
			}
		}
		catch(Exception ex){
			Log.d(TAG, "Exception in prepareVechileListPoup()"+ex);
		}
		finally{
			inflater = null;
		}

	}

}
