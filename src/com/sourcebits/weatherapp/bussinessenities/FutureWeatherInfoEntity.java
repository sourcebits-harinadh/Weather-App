package com.sourcebits.weatherapp.bussinessenities;

/**
 * @author Harinadh.
 * Entity for Future Weather Information.
 */
public class FutureWeatherInfoEntity {

	/** weather information on this date.*/
	private String date = null;

	/** maximum temperature in C information.*/
	private String tempMaxC = null;

	/** maximum temperature in F information.*/
	private String tempMaxF = null;

	/** minimum temperature in C information.*/
	private String tempMinC = null;

	/** minimum temperature in F information.*/
	private String tempMinF = null;

	/** wind direction information.*/
	private String winddirection = null;

	/** extra weather information.*/
	private WeatherExtraInfoEnity weatherExtraInfoEnity = null;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTempMaxC() {
		return tempMaxC;
	}

	public void setTempMaxC(String tempMaxC) {
		this.tempMaxC = tempMaxC;
	}

	public String getTempMaxF() {
		return tempMaxF;
	}

	public void setTempMaxF(String tempMaxF) {
		this.tempMaxF = tempMaxF;
	}

	public String getTempMinC() {
		return tempMinC;
	}

	public void setTempMinC(String tempMinC) {
		this.tempMinC = tempMinC;
	}

	public String getTempMinF() {
		return tempMinF;
	}

	public void setTempMinF(String tempMinF) {
		this.tempMinF = tempMinF;
	}

	public String getWinddirection() {
		return winddirection;
	}

	public void setWinddirection(String winddirection) {
		this.winddirection = winddirection;
	}

	public WeatherExtraInfoEnity getWeatherExtraInfoEnity() {
		return weatherExtraInfoEnity;
	}

	public void setWeatherExtraInfoEnity(WeatherExtraInfoEnity weatherExtraInfoEnity) {
		this.weatherExtraInfoEnity = weatherExtraInfoEnity;
	}

}
