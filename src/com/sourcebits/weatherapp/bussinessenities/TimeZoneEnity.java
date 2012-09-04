package com.sourcebits.weatherapp.bussinessenities;

/**
 * @author Harinadh
 * TimeZoneEnity for local time and utc offset.
 */
public class TimeZoneEnity {

	/** local time information for a city*/
	private String localTime = null;

	/** utc Off Set information for a city.*/
	private long UTCOffSet ;

	public String getLocalTime() {
		return localTime;
	}

	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}

	public long getUTCOffSet() {
		return UTCOffSet;
	}

	public void setUTCOffSet(long uTCOffSet) {
		UTCOffSet = uTCOffSet;
	}


}
