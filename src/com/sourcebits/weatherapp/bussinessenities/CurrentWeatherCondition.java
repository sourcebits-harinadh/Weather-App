package com.sourcebits.weatherapp.bussinessenities;

/**
 * @author Harinadh.
 * Entity for Current Weather Condition Information.
 */

public class CurrentWeatherCondition {

	/** cloud cover information.*/
	private String cloudcover = null;

	/** humidity information.*/
	private String humidity = null;

	/** weather observation_time information.*/
	private String observation_time = null;

	/** pressure in air information.*/
	private String pressure = null;

	/** temperature in C information.*/
	private String temp_C = null;

	/** temperature in F information.*/
	private String temp_F = null;

	/** visibility information.*/
	private String visibility = null;

	/** extra weather information.*/
	private WeatherExtraInfoEnity weatherExtraInfoEnity = null;

	public String getCloudcover() {
		return cloudcover;
	}

	public void setCloudcover(String cloudcover) {
		this.cloudcover = cloudcover;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getObservation_time() {
		return observation_time;
	}

	public void setObservation_time(String observation_time) {
		this.observation_time = observation_time;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getTemp_C() {
		return temp_C;
	}

	public void setTemp_C(String temp_C) {
		this.temp_C = temp_C;
	}

	public String getTemp_F() {
		return temp_F;
	}

	public void setTemp_F(String temp_F) {
		this.temp_F = temp_F;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	public WeatherExtraInfoEnity getWeatherExtraInfoEnity() {
		return weatherExtraInfoEnity;
	}

	public void setWeatherExtraInfoEnity(WeatherExtraInfoEnity weatherExtraInfoEnity) {
		this.weatherExtraInfoEnity = weatherExtraInfoEnity;
	}

}
