package com.sourcebits.weatherapp.serverconnections;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sourcebits.weatherapp.bussinessenities.CurrentWeatherCondition;
import com.sourcebits.weatherapp.bussinessenities.FutureWeatherInfoEntity;
import com.sourcebits.weatherapp.bussinessenities.LocationEntity;
import com.sourcebits.weatherapp.bussinessenities.TimeZoneEnity;
import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;
import com.sourcebits.weatherapp.bussinessenities.WeatherExtraInfoEnity;
import com.sourcebits.weatherapp.utilities.UtilityFunctions;

/**
 * @author Harinadh
 * ParserUtilities class provides methods to parse the weather and location information.
 */
public class ParserUtilities {

	/* Tag for class fully qualified class name.*/
	private static final String TAG =  ParserUtilities.class.getCanonicalName();

	/**
	 * parse the response and returns the  WeatherAtLocation object.
	 * @param response: server response.
	 * @return WeatherAtLocation object.
	 * @throws JSONException
	 */
	public static WeatherAtLocation getWeatherInfoEnity(String response) throws JSONException,Exception{
		WeatherAtLocation weatherAtLocation = null;
		JSONArray jsonArray = null;
		JSONObject jsonObj = null;
		String errorMessage = null;
		try{
			if(response!=null){
				jsonObj = new JSONObject(response);
				jsonObj = jsonObj.getJSONObject(ParserConstants.WeatherConstants.DATA);
				jsonArray = jsonObj.optJSONArray(ParserConstants.WeatherConstants.ERROR);
				if(jsonArray!=null&& jsonArray.length()>0){
					JSONObject jsonObjError = jsonArray.getJSONObject(0);
					errorMessage = jsonObjError.getString(ParserConstants.WeatherConstants.MSG);
					Exception e = new Exception(errorMessage);
					throw e; 
				}else{
					/* parsing the current condition array*/
					jsonArray =  jsonObj.getJSONArray(ParserConstants.WeatherConstants.CURRENT_CONDITION);
					weatherAtLocation = new WeatherAtLocation();
					if(jsonArray!=null && jsonArray.length()>0){
						CurrentWeatherCondition currentWeatherCondition[]  = new CurrentWeatherCondition[jsonArray.length()];
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObjTemp = jsonArray.getJSONObject(i);
							currentWeatherCondition[i] = new CurrentWeatherCondition();
							currentWeatherCondition[i].setObservation_time(jsonObjTemp.getString(ParserConstants.WeatherConstants.OBSERVATION_TIME));
							currentWeatherCondition[i].setCloudcover(jsonObjTemp.getString(ParserConstants.WeatherConstants.CLOUDCOVER));
							currentWeatherCondition[i].setHumidity(jsonObjTemp.getString(ParserConstants.WeatherConstants.HUMIDITY));
							currentWeatherCondition[i].setPressure(jsonObjTemp.getString(ParserConstants.WeatherConstants.PRESSURE));
							currentWeatherCondition[i].setTemp_C(jsonObjTemp.getString(ParserConstants.WeatherConstants.TEMP_C));
							currentWeatherCondition[i].setTemp_F(jsonObjTemp.getString(ParserConstants.WeatherConstants.TEMP_F));
							currentWeatherCondition[i].setVisibility(jsonObjTemp.getString(ParserConstants.WeatherConstants.VISIBILITY));
							currentWeatherCondition[i].setWeatherExtraInfoEnity(getExtraWeatherInfoEnity(jsonObjTemp));

							weatherAtLocation.setCurrentWeatherCondition(currentWeatherCondition);
						}
					}
					/* End of parsing the current condition array*/

					/* parsing the request array*/
					jsonArray =  jsonObj.getJSONArray(ParserConstants.WeatherConstants.REQUEST);
					if(jsonArray!=null && jsonArray.length()>0){

						JSONObject jsonObjTemp = jsonArray.getJSONObject(0);
						weatherAtLocation.setQueryLocation(jsonObjTemp.getString(ParserConstants.WeatherConstants.QUERY));
						weatherAtLocation.setLocationType(jsonObjTemp.getString(ParserConstants.WeatherConstants.TYPE));
					}
					/* End of parsing the request array*/

					/* parsing the weather array*/
					jsonArray =  jsonObj.getJSONArray(ParserConstants.WeatherConstants.WEATHER);
					if(jsonArray!=null && jsonArray.length()>0){
						FutureWeatherInfoEntity futureWeatherInfoEntity[]  = new FutureWeatherInfoEntity[jsonArray.length()];
						for (int i = 0; i < jsonArray.length(); i++) {

							JSONObject jsonObjTemp = jsonArray.getJSONObject(i);
							futureWeatherInfoEntity[i] = new FutureWeatherInfoEntity();
							futureWeatherInfoEntity[i].setDate(jsonObjTemp.getString(ParserConstants.WeatherConstants.DATE));
							futureWeatherInfoEntity[i].setTempMaxC(jsonObjTemp.getString(ParserConstants.WeatherConstants.TEMP_MAX_C));
							futureWeatherInfoEntity[i].setTempMaxF(jsonObjTemp.getString(ParserConstants.WeatherConstants.TEMP_MAX_F));
							futureWeatherInfoEntity[i].setTempMinC(jsonObjTemp.getString(ParserConstants.WeatherConstants.TEMP_MIN_C));
							futureWeatherInfoEntity[i].setTempMinF(jsonObjTemp.getString(ParserConstants.WeatherConstants.TEMP_MIN_F));
							futureWeatherInfoEntity[i].setWinddirection(jsonObjTemp.getString(ParserConstants.WeatherConstants.WIND_DIRECTION));

							futureWeatherInfoEntity[i].setWeatherExtraInfoEnity(getExtraWeatherInfoEnity(jsonObjTemp));

							weatherAtLocation.setFutureWeatherInfoEntity(futureWeatherInfoEntity);
						}

					}
					/* End of parsing the weather array*/
				}
			}
		}catch(JSONException e) {
			Log.d(TAG, "JSONException:"+e);
			throw e;
		}
		catch(Exception e) {
			Log.d(TAG, "Exception:"+e);
			throw e;
		}finally{
			jsonObj = null;
			jsonArray = null;
			response = null;
			errorMessage = null;
		}
		return weatherAtLocation;
	}


	/**
	 * This method parses the JSONObject which represents the Extra Weather Information and returns the WeatherExtraInfoEnity object.
	 * @param jsonObj
	 * @returns WeatherExtraInfoEnity object
	 * @throws JSONException
	 */
	private  static WeatherExtraInfoEnity getExtraWeatherInfoEnity(JSONObject jsonObj ) throws JSONException{

		WeatherExtraInfoEnity weatherExtraInfoEnity = null;
		JSONArray desriptionArray = null;
		JSONArray imgUrlsArray = null;
		try{
			weatherExtraInfoEnity = new WeatherExtraInfoEnity();

			weatherExtraInfoEnity.setWeatherCode(jsonObj.getString(ParserConstants.WeatherConstants.WEATHER_CODE));
			weatherExtraInfoEnity.setWinddir16Point(jsonObj.getString(ParserConstants.WeatherConstants.WIND_DIR_16POINT));
			weatherExtraInfoEnity.setWinddirDegree(jsonObj.getString(ParserConstants.WeatherConstants.WIND_DIR_DEGREE));
			weatherExtraInfoEnity.setWindspeedKmph(jsonObj.getString(ParserConstants.WeatherConstants.WIND_SPEED_KMPH));
			weatherExtraInfoEnity.setWindspeedMiles(jsonObj.getString(ParserConstants.WeatherConstants.WIND_SPEED_MILES));
			weatherExtraInfoEnity.setPrecipMM(jsonObj.getString(ParserConstants.WeatherConstants.PRECIPMM));

			desriptionArray = jsonObj.getJSONArray(ParserConstants.WeatherConstants.WEATHER_DESC);
			if(desriptionArray!=null && desriptionArray.length()>0){
				String desriptions[] = new String[desriptionArray.length()];
				for (int i = 0; i < desriptionArray.length(); i++) {
					JSONObject jsonObjTemp = desriptionArray.getJSONObject(i);
					desriptions[i] = jsonObjTemp.getString(ParserConstants.WeatherConstants.VALUE);
				}
				weatherExtraInfoEnity.setWeatherDesc(desriptions);
			}

			imgUrlsArray = jsonObj.getJSONArray(ParserConstants.WeatherConstants.WEATHER_ICON_URL);
			if(imgUrlsArray!=null && imgUrlsArray.length()>0){
				String imageUrls[] = new String[imgUrlsArray.length()];
				for (int i = 0; i < imgUrlsArray.length(); i++) {

					JSONObject jsonObjTemp = imgUrlsArray.getJSONObject(i);
					imageUrls[i] = jsonObjTemp.getString(ParserConstants.WeatherConstants.VALUE);

				}
				weatherExtraInfoEnity.setWeatherIconUrl(imageUrls);
				String imgData = UtilityFunctions.BitMapToString(ServerConnection.downLoadImg(imageUrls[0]));
				weatherExtraInfoEnity.setImgData(imgData);
			}

		}catch(JSONException e) {
			Log.d(TAG, "JSONException:"+e);
			new Throwable("JSONException:"+e);
		}
		catch(Exception e) {
			Log.d(TAG, "Exception:"+e);
		}finally{
			desriptionArray = null;
			imgUrlsArray = null;
		}
		return weatherExtraInfoEnity;
	}


	/**
	 * This method parses the Time zone response for a city and returns the TimeZoneEnity object.
	 * @param server response.
	 * @return
	 * @throws JSONException
	 */
	public static TimeZoneEnity getTimeZoneEnity(String response ) throws JSONException{

		TimeZoneEnity timeZoneEnity = null;
		JSONArray timeZoneArray = null;
		JSONObject jsonObj = null;
		try{
			if(response!=null){
				jsonObj = new JSONObject(response);
				jsonObj = jsonObj.getJSONObject(ParserConstants.WeatherConstants.DATA);

				timeZoneArray = jsonObj.getJSONArray(ParserConstants.TimeZoneConstants.TIME_ZONE);

				if(timeZoneArray!=null && timeZoneArray.length()>0){
					timeZoneEnity = new TimeZoneEnity();
					JSONObject jsonObjTemp = timeZoneArray.getJSONObject(0);
					timeZoneEnity.setLocalTime(jsonObjTemp.getString(ParserConstants.TimeZoneConstants.LOCALTIME));

					Date curretnTime = new Date();

					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					long remoteTime = sdf1.parse(timeZoneEnity.getLocalTime()).getTime();


					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


					long localTime = 
						new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
					.parse(sdf.format(curretnTime)).getTime() ;


					timeZoneEnity.setUTCOffSet(localTime-remoteTime);
					Log.d(TAG, "local"+localTime);
					Log.d(TAG, "remote"+remoteTime);
					Log.d(TAG, "difference"+(localTime-remoteTime));
				}
			}

		}catch(JSONException e) {
			Log.d(TAG, "JSONException:"+e);
			new Throwable("JSONException:"+e);
		}
		catch(Exception e) {
			Log.d(TAG, "Exception:"+e);
		}finally{
			timeZoneArray = null;
			jsonObj = null;
			response = null;
		}
		return timeZoneEnity;
	}

	/**
	 * This method parses the locations name and country  for a keyword and returns the Location object.
	 * @param server response.
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<LocationEntity> getLocationsList(String response ) throws JSONException{

		LocationEntity locationEnity = null;
		JSONArray locationsArray = null;
		JSONObject jsonObj = null;
		ArrayList<LocationEntity> list = null;
		try{
			if(response!=null){
				jsonObj = new JSONObject(response);
				jsonObj = jsonObj.getJSONObject(ParserConstants.WeatherConstants.SEARCH_API);
				if(jsonObj!=null && !jsonObj.equals("null")){
					locationsArray = jsonObj.getJSONArray(ParserConstants.WeatherConstants.RESULT);
					if(locationsArray!=null && locationsArray.length()>0){
						list = new ArrayList<LocationEntity>();

						for(int i=0;i<locationsArray.length();i++){

							locationEnity = new LocationEntity();
							JSONObject jsonObjTemp = locationsArray.getJSONObject(i);
							locationEnity.setAreaName(jsonObjTemp.getJSONArray(ParserConstants.WeatherConstants.AREA).getJSONObject(0).getString(ParserConstants.WeatherConstants.VALUE));
							locationEnity.setCountry(jsonObjTemp.getJSONArray(ParserConstants.WeatherConstants.COUNTRY).getJSONObject(0).getString(ParserConstants.WeatherConstants.VALUE));	

							list.add(locationEnity);
						}

					}
				}
			}

		}catch(JSONException e) {
			Log.d(TAG, "JSONException:"+e);
			new Throwable("JSONException:"+e);
		}
		catch(Exception e) {
			Log.d(TAG, "Exception:"+e);
		}finally{
			locationsArray = null;
			jsonObj = null;
			response = null;
		}
		return list;
	}
}
