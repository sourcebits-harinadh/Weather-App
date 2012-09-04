package com.sourcebits.weatherapp.customcomponents;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import com.sourcebits.weatherapp.bussinessenities.FutureWeatherInfoEntity;
import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;

public class WeatherItem extends OverlayItem{


	private String location =null;
	private String tempMin =null;
	private String tempMax = null;
	private String desrip = null;
	private String date = null;
	private String imageDtata = null;
	
	public WeatherItem(GeoPoint point, String title, String snippet,WeatherAtLocation weatherAtLocation) {
		super(point, title, snippet);	
		location = weatherAtLocation.getQueryLocation();
		FutureWeatherInfoEntity futureWeatherInfoEntity = weatherAtLocation.getFutureWeatherInfoEntity()[0];

		tempMin = futureWeatherInfoEntity.getTempMinC();
		tempMax = futureWeatherInfoEntity.getTempMaxC();
		date = futureWeatherInfoEntity.getDate();
		desrip = futureWeatherInfoEntity.getWeatherExtraInfoEnity().getWeatherDesc()[0];
		imageDtata = futureWeatherInfoEntity.getWeatherExtraInfoEnity().getImgData();
		futureWeatherInfoEntity = null;
		weatherAtLocation = null;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getTempMin() {
		return tempMin;
	}


	public void setTempMin(String tempMin) {
		this.tempMin = tempMin;
	}


	public String getTempMax() {
		return tempMax;
	}


	public void setTempMax(String tempMax) {
		this.tempMax = tempMax;
	}


	public String getDesrip() {
		return desrip;
	}


	public void setDesrip(String desrip) {
		this.desrip = desrip;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getImageDtata() {
		return imageDtata;
	}


	public void setImageDtata(String imageDtata) {
		this.imageDtata = imageDtata;
	}

}
