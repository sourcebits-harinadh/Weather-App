package com.sourcebits.weatherapp.serverconnections;

/**
 * @author Harinadh
 * interface for parser constants.
 */
public interface ParserConstants {


	/**
	 * Weather information Constants for parsing the response.
	 */
	public interface WeatherConstants{

		/** error.*/
		String ERROR = "error";
		
		/** message*/
		String MSG = "msg";
		
		/** data.*/
		String DATA = "data";

		/** current condition.*/
		String CURRENT_CONDITION = "current_condition";

		/** weather.*/
		String WEATHER = "weather";

		/** value.*/
		String VALUE = "value";

		/** cloud cover information.*/
		String OFFSET = "offset";
		
		/** cloud cover information.*/
		String CLOUDCOVER = "cloudcover";

		/** humidity information.*/
		String HUMIDITY = "humidity";

		/** weather observation_time information.*/
		String OBSERVATION_TIME = "observation_time";

		/** pressure in air information.*/
		String PRESSURE = "pressure";

		/** temperature in C information.*/
		String TEMP_C = "temp_C";

		/** temperature in F information.*/
		String TEMP_F = "temp_F";

		/** visibility information.*/
		String VISIBILITY = "visibility";

		/** array of weather Descriptions */  
		String WEATHER_DESC = "weatherDesc";

		/** array of weather Images urls */  
		String WEATHER_ICON_URL = "weatherIconUrl";

		/** wind direction 16Point information */  
		String WIND_DIR_16POINT = "winddir16Point";

		/** wind direction Degree information */  
		String WIND_DIR_DEGREE = "winddirDegree";

		/** wind speed in Kmph information */  
		String WIND_SPEED_KMPH = "windspeedKmph";

		/** wind speed in Miles information */  
		String WIND_SPEED_MILES = "windspeedMiles";

		/** precipMM information.*/
		String PRECIPMM = "precipMM";

		/** weatherCode information.*/
		String WEATHER_CODE = "weatherCode";

		/** weather information on this date.*/
		String DATE = "date";

		/** maximum temperature in C information.*/
		String TEMP_MAX_C = "tempMaxC";

		/** maximum temperature in F information.*/
		String TEMP_MAX_F = "tempMaxF";

		/** minimum temperature in C information.*/
		String TEMP_MIN_C = "tempMinC";

		/** minimum temperature in F information.*/
		String TEMP_MIN_F = "tempMinF";

		/** wind direction information.*/
		String WIND_DIRECTION = "winddirection";

		/** query.*/
		String QUERY = "query";

		/** type.*/
		String TYPE = "type";
		
		/** request.*/
		String REQUEST = "request";
		
		String API_KEY_FOR_WEATHER_SERVICES = "9f2b2c1b7d130133122108";
		
		String AREA = "areaName";
		
		String COUNTRY = "country";
		
		String SEARCH_API = "search_api";
		
		String RESULT = "result";
		
	}

	
	/**
	 * Weather information Constants for parsing the response.
	 */
	public interface TimeZoneConstants{

		/** time_zone.*/
		String TIME_ZONE = "time_zone";

		/** local time.*/
		String LOCALTIME = "localtime";

		/** utc Offset.*/
		String UTCOFFSET = "utcOffset";
	}
}
