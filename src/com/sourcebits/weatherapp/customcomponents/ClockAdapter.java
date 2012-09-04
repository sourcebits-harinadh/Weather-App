package com.sourcebits.weatherapp.customcomponents;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sourcebits.weatherapp.R;
import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;
import com.sourcebits.weatherapp.utilities.UtilityFunctions;

/**
 * @author Harinadh
 * ClockAdapter for gallery view of clocks.
 */
public class ClockAdapter extends BaseAdapter{


	private ArrayList<WeatherAtLocation> mLocations = null; 
	private Context mContext;
	public ClockAdapter(Context c,ArrayList<WeatherAtLocation> locations) {
		mContext = c;
		this.mLocations = locations;
	}

	public int getCount() {
		return mLocations!=null?mLocations.size():0;
	}

	public Object getItem(int position) {

		return  mLocations!=null ? mLocations.get(position):null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout clockRow = (RelativeLayout)convertView;
		if(mLocations!=null){
			if(clockRow == null)
			{
				LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
				clockRow = (RelativeLayout)inflater.inflate(com.sourcebits.weatherapp.R.layout.clock_location_layout, parent, false);
				String locationName[] = UtilityFunctions.split(mLocations.get(position).getQueryLocation(),",");
				MyAnalogClock   clock = (MyAnalogClock)clockRow. findViewById(R.id.clock);
				clock.setOffset(mLocations.get(position).getOffsetInMilliseconds());
				TextView titleView =   (TextView)clockRow.findViewById(com.sourcebits.weatherapp.R.id.clockTitle);
				titleView.setText(locationName[0]);
				titleView.setPadding(0, 10, 0,0);
			}
			else
			{
				String locationName[] = UtilityFunctions.split(mLocations.get(position).getQueryLocation(),",");
				
				MyAnalogClock   clock = (MyAnalogClock)clockRow. findViewById(R.id.clock);
				clock.setOffset(mLocations.get(position).getOffsetInMilliseconds());
				TextView title =   (TextView)clockRow.findViewById(com.sourcebits.weatherapp.R.id.clockTitle);
				title.setText(locationName[0]);
				title.setPadding(0, 10, 0,0);
			}
			clockRow.setFocusable(true);
			clockRow.setFocusableInTouchMode(true);
		}
		return clockRow;
	}

	public void addElement(WeatherAtLocation weatherAtLocation){
		if(mLocations==null){
			mLocations = new ArrayList<WeatherAtLocation>();	
		}
		mLocations.add(weatherAtLocation);
		notifyDataSetChanged();
	}

	public void changeData(ArrayList<WeatherAtLocation> locations){
		mLocations = locations;
		notifyDataSetChanged();
	}

}


