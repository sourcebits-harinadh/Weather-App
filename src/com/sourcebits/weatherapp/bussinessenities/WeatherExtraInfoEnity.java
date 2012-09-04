package com.sourcebits.weatherapp.bussinessenities;

/**
 * @author Harinadh.
 * Entity for Extra Weather Information.
 */
public class WeatherExtraInfoEnity {

	/** array of weather Descriptions */  
	private String weatherDesc[] = null;

	/** array of weather Images urls */  
	private String weatherIconUrl[] = null;

	/** wind direction 16Point information */  
	private String winddir16Point = null;

	/** wind direction Degree information */  
	private String winddirDegree = null;

	/** wind speed in Kmph information */  
	private String windspeedKmph = null;

	/** wind speed in Miles information */  
	private String windspeedMiles = null;

	/** precipMM information.*/
	private String precipMM = null;
	
	/** weatherCode information.*/
	private String weatherCode = null;
	
	private String imgData = null;


	public String[] getWeatherDesc() {
		return weatherDesc;
	}
	public void setWeatherDesc(String[] weatherDesc) {
		this.weatherDesc = weatherDesc;
	}
	public String[] getWeatherIconUrl() {
		return weatherIconUrl;
	}
	public void setWeatherIconUrl(String[] weatherIconUrl) {
		this.weatherIconUrl = weatherIconUrl;
	}
	public String getWinddir16Point() {
		return winddir16Point;
	}
	public void setWinddir16Point(String winddir16Point) {
		this.winddir16Point = winddir16Point;
	}
	public String getWinddirDegree() {
		return winddirDegree;
	}
	public void setWinddirDegree(String winddirDegree) {
		this.winddirDegree = winddirDegree;
	}
	public String getWindspeedKmph() {
		return windspeedKmph;
	}
	public void setWindspeedKmph(String windspeedKmph) {
		this.windspeedKmph = windspeedKmph;
	}
	public String getWindspeedMiles() {
		return windspeedMiles;
	}
	public void setWindspeedMiles(String windspeedMiles) {
		this.windspeedMiles = windspeedMiles;
	}

	public String getPrecipMM() {
		return precipMM;
	}

	public void setPrecipMM(String precipMM) {
		this.precipMM = precipMM;
	}
	
	public String getWeatherCode() {
		return weatherCode;
	}

	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}
	
	public String getImgData() {
		return imgData;
	}
	public void setImgData(String imgData) {
		this.imgData = imgData;
	}
}
