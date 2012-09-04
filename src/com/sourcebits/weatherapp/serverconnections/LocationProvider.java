package com.sourcebits.weatherapp.serverconnections;

import com.google.android.maps.GeoPoint;

public interface LocationProvider {

	public void locationFound(GeoPoint point,int index);
}
