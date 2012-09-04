package com.sourcebits.weatherapp.customcomponents;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sourcebits.weatherapp.bussinessenities.LocationEntity;

public class CustomListAdapter extends BaseAdapter{

	private Context mContext; 
	private ArrayList<LocationEntity> mData = null;

	public CustomListAdapter(Context context, ArrayList<LocationEntity> data) {
		super();
		this.mContext = context;
		this.mData = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LinearLayout row = null;
		if(convertView == null)
		{
			LayoutInflater inflater = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));

			row = (LinearLayout)inflater.inflate(com.sourcebits.weatherapp.R.layout.listrow, parent, false);	
			TextView title =   (TextView)row.findViewById(com.sourcebits.weatherapp.R.id.rowText);
			title.setText(((LocationEntity)mData.get(position)).getAreaName()+","+((LocationEntity)mData.get(position)).getCountry());
		}
		else
		{
			row = (LinearLayout)convertView;
			TextView title =   (TextView)row.findViewById(com.sourcebits.weatherapp.R.id.rowText);
			title.setText(((LocationEntity)mData.get(position)).getAreaName()+","+((LocationEntity)mData.get(position)).getCountry());
		}
		return row;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position ;
	}


	protected ArrayList<LocationEntity> getmData() {
		return mData;
	}

	protected void setmData(ArrayList<LocationEntity> mData) {
		this.mData = mData;
	}

	protected void addPlaceObj(LocationEntity placeObj) {
		this.mData.add(placeObj);
		this.notifyDataSetChanged();
	}

}
