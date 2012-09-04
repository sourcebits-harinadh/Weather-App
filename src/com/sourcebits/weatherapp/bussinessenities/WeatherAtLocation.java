package com.sourcebits.weatherapp.bussinessenities;

/**
 * @author Harinadh.
 * Entity for Weather At a particular Location.
 */
public class WeatherAtLocation {

	/** array of Current Weather Condition entities at given location.*/
	private CurrentWeatherCondition currentWeatherCondition[] = null;

	/** array of Future Weather information entities at given location.*/
	private FutureWeatherInfoEntity futureWeatherInfoEntity[] = null;

	/** location name.*/
	private String queryLocation = null;

	/** location type.*/
	private String locationType = null;
	
	/** time offset.*/
	private long offsetInMilliseconds ;

	public CurrentWeatherCondition[] getCurrentWeatherCondition() {
		return currentWeatherCondition;
	}

	public void setCurrentWeatherCondition(
			CurrentWeatherCondition[] currentWeatherCondition) {
		this.currentWeatherCondition = currentWeatherCondition;
	}

	public FutureWeatherInfoEntity[] getFutureWeatherInfoEntity() {
		return futureWeatherInfoEntity;
	}

	public void setFutureWeatherInfoEntity(
			FutureWeatherInfoEntity[] futureWeatherInfoEntity) {
		this.futureWeatherInfoEntity = futureWeatherInfoEntity;
	}

	public String getQueryLocation() {
		return queryLocation;
	}

	public void setQueryLocation(String queryLocation) {
		this.queryLocation = queryLocation;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public long getOffsetInMilliseconds() {
		return offsetInMilliseconds;
	}

	public void setOffsetInMilliseconds(long offsetInMilliseconds) {
		this.offsetInMilliseconds = offsetInMilliseconds;
	}
}
