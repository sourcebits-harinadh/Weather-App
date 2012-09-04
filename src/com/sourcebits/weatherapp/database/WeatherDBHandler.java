package com.sourcebits.weatherapp.database;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sourcebits.weatherapp.bussinessenities.CurrentWeatherCondition;
import com.sourcebits.weatherapp.bussinessenities.FutureWeatherInfoEntity;
import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;
import com.sourcebits.weatherapp.bussinessenities.WeatherExtraInfoEnity;
import com.sourcebits.weatherapp.serverconnections.ParserConstants;
import com.sourcebits.weatherapp.utilities.UtilityFunctions;

/**
 * @author Harinadh
 * WeatherDBHandler class for data base utility functions for locations and weather table.
 */
public class WeatherDBHandler {

	/** Tag for class fully qualified class name.*/
	private static final String TAG =  WeatherDBHandler.class.getCanonicalName();


	/**
	 * Insert weather data into database.
	 * @param WeatherAtLocation object as data.
	 * @return boolean as a success or failure.
	 */
	public static boolean insertDataTODB(WeatherAtLocation weatherAtLocation,Context context,boolean isUpdate,int key){
		boolean result = true;
		ContentValues values = null;
		MySqliteHelper sqliteHelper = null;
		SQLiteDatabase sqlDB = null;
		try{
			sqliteHelper =MySqliteHelper.getInstance(context);
			sqlDB = sqliteHelper.getWritableDatabase();

			if(weatherAtLocation!=null){

				values = new ContentValues();
				if(weatherAtLocation.getQueryLocation()!=null){

					/* inserting data into locations table*/
					values.put(ParserConstants.WeatherConstants.QUERY, weatherAtLocation.getQueryLocation());
					values.put(ParserConstants.WeatherConstants.OFFSET, weatherAtLocation.getOffsetInMilliseconds());
					CurrentWeatherCondition currentWeatherCondition[] = weatherAtLocation.getCurrentWeatherCondition();

					if(currentWeatherCondition!=null&& currentWeatherCondition.length>0){

						values.put(ParserConstants.WeatherConstants.CLOUDCOVER,currentWeatherCondition[0].getCloudcover());
						values.put(ParserConstants.WeatherConstants.HUMIDITY, currentWeatherCondition[0].getHumidity());
						values.put(ParserConstants.WeatherConstants.OBSERVATION_TIME, currentWeatherCondition[0].getObservation_time());
						values.put(ParserConstants.WeatherConstants.PRECIPMM, currentWeatherCondition[0].getPressure());
						values.put(ParserConstants.WeatherConstants.PRESSURE, currentWeatherCondition[0].getPressure());
						values.put(ParserConstants.WeatherConstants.TEMP_C, currentWeatherCondition[0].getTemp_C());
						values.put(ParserConstants.WeatherConstants.TEMP_F, currentWeatherCondition[0].getTemp_F());
						values.put(ParserConstants.WeatherConstants.VISIBILITY, currentWeatherCondition[0].getVisibility());
						WeatherExtraInfoEnity weatherExtraInfoEnity  = currentWeatherCondition[0].getWeatherExtraInfoEnity();
						if(weatherExtraInfoEnity!=null){
							String desription = convertArrayToString(weatherExtraInfoEnity.getWeatherDesc());

							values.put(ParserConstants.WeatherConstants.WEATHER_DESC, desription);
							values.put(ParserConstants.WeatherConstants.WIND_DIR_DEGREE, weatherExtraInfoEnity.getWinddirDegree());
							values.put(ParserConstants.WeatherConstants.WIND_SPEED_KMPH, weatherExtraInfoEnity.getWindspeedKmph());
							values.put(ParserConstants.WeatherConstants.WIND_SPEED_MILES, weatherExtraInfoEnity.getWindspeedMiles());
							values.put(LocationsTable.IMG_DATA, weatherExtraInfoEnity.getImgData());

						}
					}
					long id = -1;
					if(isUpdate){
						String whereClause =  LocationsTable.COLUMN_ID + "=" +key;
						id = sqlDB.update(LocationsTable.TABLE_LOCATIONS, values,whereClause,null);	
						id = key;
					}else{
						id = sqlDB.insert(LocationsTable.TABLE_LOCATIONS, null, values);
					}
					/* end of inserting data into locations table*/


					if(id !=-1){
						Log.d(TAG, "Succceded to insert new row in "+LocationsTable.TABLE_LOCATIONS+": And row id is:"+id);

						/*inserting data into weather details  table*/


						FutureWeatherInfoEntity futureWeatherInfoEntity[] = weatherAtLocation.getFutureWeatherInfoEntity();
						if(futureWeatherInfoEntity!=null&& futureWeatherInfoEntity.length>0){

							if(isUpdate){
								String whereClause =  WeatherDetailsTable.LOCATIONS_ID + "=" +key;
								int number = sqlDB.delete(WeatherDetailsTable.TABLE_WEATHER_DETAILS, whereClause, null);
								if(number>0){
									Log.d(TAG, "Succceded to delete rows from "+WeatherDetailsTable.TABLE_WEATHER_DETAILS);
								}
							}
							for(int i=0;i<futureWeatherInfoEntity.length;i++){

								values = new ContentValues();

								values.put(WeatherDetailsTable.LOCATIONS_ID,id);
								values.put(ParserConstants.WeatherConstants.DATE,futureWeatherInfoEntity[i].getDate());
								values.put(ParserConstants.WeatherConstants.TEMP_MAX_F, futureWeatherInfoEntity[i].getTempMaxF());
								values.put(ParserConstants.WeatherConstants.TEMP_MAX_C, futureWeatherInfoEntity[i].getTempMaxC());
								values.put(ParserConstants.WeatherConstants.TEMP_MIN_F, futureWeatherInfoEntity[i].getTempMinF());
								values.put(ParserConstants.WeatherConstants.TEMP_MIN_C, futureWeatherInfoEntity[i].getTempMinC());
								values.put(ParserConstants.WeatherConstants.WIND_DIRECTION, futureWeatherInfoEntity[i].getWinddirection());

								WeatherExtraInfoEnity weatherExtraInfoEnity  = futureWeatherInfoEntity[i].getWeatherExtraInfoEnity();

								if(weatherExtraInfoEnity!=null){

									String desription = convertArrayToString(weatherExtraInfoEnity.getWeatherDesc());
									values.put(ParserConstants.WeatherConstants.WEATHER_DESC, desription);
									String urlText = convertArrayToString(weatherExtraInfoEnity.getWeatherIconUrl());
									values.put(ParserConstants.WeatherConstants.WEATHER_ICON_URL, urlText);

									values.put(ParserConstants.WeatherConstants.PRECIPMM, weatherExtraInfoEnity.getPrecipMM());
									values.put(ParserConstants.WeatherConstants.WIND_DIR_16POINT, weatherExtraInfoEnity.getWinddir16Point());
									values.put(ParserConstants.WeatherConstants.WIND_DIR_DEGREE, weatherExtraInfoEnity.getWinddirDegree());
									values.put(ParserConstants.WeatherConstants.WIND_SPEED_KMPH, weatherExtraInfoEnity.getWindspeedKmph());
									values.put(ParserConstants.WeatherConstants.WIND_SPEED_MILES, weatherExtraInfoEnity.getWindspeedMiles());
									values.put(LocationsTable.IMG_DATA, weatherExtraInfoEnity.getImgData());

								}

								long id1 = -1;

								id1 = sqlDB.insert(WeatherDetailsTable.TABLE_WEATHER_DETAILS, null, values);

								if(id1 !=-1){
									Log.d(TAG, "Succceded to insert new row in to "+WeatherDetailsTable.TABLE_WEATHER_DETAILS+": And row id is:"+id1);	
								}else{
									Log.d(TAG, "Failed to insert new row in to"+WeatherDetailsTable.TABLE_WEATHER_DETAILS+":and id is:"+id1);	
									result = false;
								}

							}

						}

						/* end of inserting data into weather details  table*/
					}else{
						result = false;
						Log.d(TAG, "Failed to insert new row in to"+LocationsTable.TABLE_LOCATIONS+":and id is:"+id);	
					}	
				}else{
					Log.d(TAG, "No data available in this enity");
				}

			}
			return result;

		}catch(Exception ex){
			Log.d(TAG, "Exception while writing the database:"+ex);
			result = false;
			return result;
		}
		finally{

			if(sqlDB!=null){
				sqlDB.close();
			}
			if(sqliteHelper!=null){
				sqliteHelper.close();	
			}
			sqlDB = null;
			sqliteHelper = null;
			values = null;

		}


	}


	/**
	 * method to read from the data base for the given table name;
	 * @return Cursor object pointing to table.
	 */
	public static Cursor readFromDatabase(String tableName,Context context,String wherelause){
		Cursor cursor = null;
		MySqliteHelper sqliteHelper = null;
		SQLiteDatabase sqlDB = null;
		try{
			sqliteHelper =  MySqliteHelper.getInstance(context);
			sqlDB = sqliteHelper.getReadableDatabase();
			cursor = sqlDB.query(tableName, null, wherelause, null, null, null, null);

		}
		catch(Exception ex){
			Log.d(TAG, "Exception in readFromDatabase():"+ex);
		}finally{
			//			if(sqlDB!=null){
			//				sqlDB.close();
			//			}
			//			if(sqliteHelper!=null){
			//				sqliteHelper.close();	
			//			}
			//			sqlDB = null;
			//			sqliteHelper = null;
			//		
			wherelause = null;
			tableName = null;
		}
		return cursor;

	}

	/**Methos to get the added locations from database.
	 * @return ArrayList<WeatherAtLocation>
	 */
	public static ArrayList<WeatherAtLocation> getAddedLocations(Context context){
		ArrayList<WeatherAtLocation> locations = null; 
		Cursor cursor = null;
		try{

			/* reading locations from the data base and preparing the WeatherAtLocation entity*/ 
			cursor = readFromDatabase(LocationsTable.TABLE_LOCATIONS,context,null);
			if(cursor!=null && cursor.getCount()>0){
				locations =  new ArrayList<WeatherAtLocation>();
				while(!cursor.isClosed()&& cursor.moveToNext()){

					WeatherAtLocation WeatherAtLocation = new WeatherAtLocation();


					WeatherAtLocation.setQueryLocation(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.QUERY)));
					WeatherAtLocation.setOffsetInMilliseconds(cursor.getLong(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.OFFSET)));
					CurrentWeatherCondition currentWeatherCondition[] = new CurrentWeatherCondition[1];
					currentWeatherCondition[0] = new CurrentWeatherCondition();

					currentWeatherCondition[0].setCloudcover(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.CLOUDCOVER)));

					currentWeatherCondition[0].setHumidity(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.HUMIDITY)));

					currentWeatherCondition[0].setObservation_time(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.OBSERVATION_TIME)));

					currentWeatherCondition[0].setPressure(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.PRESSURE)));

					currentWeatherCondition[0].setTemp_C(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.TEMP_C)));

					currentWeatherCondition[0].setTemp_F(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.TEMP_F)));

					currentWeatherCondition[0].setVisibility(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.VISIBILITY)));

					currentWeatherCondition[0].setWeatherExtraInfoEnity(prepareWeatherExtraInfo(cursor));

					WeatherAtLocation.setCurrentWeatherCondition(currentWeatherCondition);

					WeatherAtLocation.setFutureWeatherInfoEntity(prepareFutureWeatherInfo(cursor.getInt(0), context));

					locations.add(WeatherAtLocation);

				}

			}

		}catch (Exception e) {
			Log.d(TAG, "Exception in getAddedLocations():"+e);
		}finally{
			if(cursor!=null){
				cursor.close();
			}
		}

		return locations;
	}

	/**
	 * This method prepares the WeatherExtraInfoEnity from the cursor object.
	 * @param cursor object.
	 * @return WeatherExtraInfoEnity object.
	 */
	private static WeatherExtraInfoEnity prepareWeatherExtraInfo(Cursor cursor){
		WeatherExtraInfoEnity  weatherExtraInfoEnity = null; 
		String weatherDesc[] = null;
		String weatherIconUrl[] = null;
		try{

			weatherExtraInfoEnity = new WeatherExtraInfoEnity();

			if(cursor.getColumnIndex(ParserConstants.WeatherConstants.WEATHER_DESC)!=-1){
				weatherDesc = new String[1]; 
				weatherDesc[0] = cursor.getString(
						cursor.getColumnIndex(ParserConstants.WeatherConstants.WEATHER_DESC));
				weatherExtraInfoEnity.setWeatherDesc(weatherDesc);
			}

			if(cursor.getColumnIndex(ParserConstants.WeatherConstants.WEATHER_ICON_URL)!=-1){
				weatherIconUrl = new String[1]; 
				weatherIconUrl[0] = cursor.getString(
						cursor.getColumnIndex(ParserConstants.WeatherConstants.WEATHER_ICON_URL));
				weatherExtraInfoEnity.setWeatherIconUrl(weatherIconUrl);
			}
			if(cursor.getColumnIndex(ParserConstants.WeatherConstants.PRECIPMM)!=-1)
				weatherExtraInfoEnity.setPrecipMM(cursor.getString(
						cursor.getColumnIndex(ParserConstants.WeatherConstants.PRECIPMM)));

			if(cursor.getColumnIndex(ParserConstants.WeatherConstants.WEATHER_CODE)!=-1)
				weatherExtraInfoEnity.setWeatherCode(cursor.getString(
						cursor.getColumnIndex(ParserConstants.WeatherConstants.WEATHER_CODE)));

			if(cursor.getColumnIndex(ParserConstants.WeatherConstants.WIND_DIR_DEGREE)!=-1)
				weatherExtraInfoEnity.setWinddirDegree(cursor.getString(
						cursor.getColumnIndex(ParserConstants.WeatherConstants.WIND_DIR_DEGREE)));

			if(cursor.getColumnIndex(ParserConstants.WeatherConstants.WIND_DIR_16POINT)!=-1)
				weatherExtraInfoEnity.setWinddir16Point(cursor.getString(
						cursor.getColumnIndex(ParserConstants.WeatherConstants.WIND_DIR_16POINT)));

			if(cursor.getColumnIndex(ParserConstants.WeatherConstants.WIND_SPEED_KMPH)!=-1)
				weatherExtraInfoEnity.setWindspeedKmph(cursor.getString(
						cursor.getColumnIndex(ParserConstants.WeatherConstants.WIND_SPEED_KMPH)));

			if(cursor.getColumnIndex(ParserConstants.WeatherConstants.WIND_SPEED_MILES)!=-1)
				weatherExtraInfoEnity.setWindspeedMiles(cursor.getString(
						cursor.getColumnIndex(ParserConstants.WeatherConstants.WIND_SPEED_MILES)));

			if(cursor.getColumnIndex(LocationsTable.IMG_DATA)!=-1)
				weatherExtraInfoEnity.setImgData(cursor.getString(
						cursor.getColumnIndex(LocationsTable.IMG_DATA)));

		}catch (Exception e) {
			Log.d(TAG, "Exception in prepareWeatherExtraInfo():"+e);
		}finally{
			weatherIconUrl = null;
			weatherDesc = null;
		}
		return weatherExtraInfoEnity;
	}

	/**
	 * This method returns the array of FutureWeatherInfoEntity objects from database based on the location id.
	 * @param locationId
	 * @param context
	 * @return  array of FutureWeatherInfoEntity objects.
	 */
	private static FutureWeatherInfoEntity[] prepareFutureWeatherInfo(long locationId,Context context){
		FutureWeatherInfoEntity  futureWeatherInfoEntity[] = null; 
		String whereClause = null;
		Cursor cursor = null;
		try{
			whereClause =  WeatherDetailsTable.LOCATIONS_ID + "=" + locationId;

			cursor = readFromDatabase(WeatherDetailsTable.TABLE_WEATHER_DETAILS, context,whereClause);
			if(cursor!=null && cursor.getCount()>0){
				futureWeatherInfoEntity = new FutureWeatherInfoEntity[cursor.getCount()];
				int i=-1;
				while(!cursor.isClosed()&& cursor.moveToNext()){
					i++;
					futureWeatherInfoEntity[i] =  new FutureWeatherInfoEntity();

					futureWeatherInfoEntity[i].setDate(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.DATE)));

					futureWeatherInfoEntity[i].setTempMaxC(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.TEMP_MAX_C)));

					futureWeatherInfoEntity[i].setTempMaxF(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.TEMP_MAX_F)));

					futureWeatherInfoEntity[i].setTempMinC(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.TEMP_MIN_C)));

					futureWeatherInfoEntity[i].setTempMinF(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.TEMP_MIN_F)));

					futureWeatherInfoEntity[i].setWinddirection(cursor.getString(
							cursor.getColumnIndex(ParserConstants.WeatherConstants.WIND_DIRECTION)));

					futureWeatherInfoEntity[i].setWeatherExtraInfoEnity(prepareWeatherExtraInfo(cursor));

				}
			}


		}catch (Exception e) {
			Log.d(TAG, "Exception in prepareFutureWeatherInfo():"+e);
		}finally{
			if(cursor!=null){
				cursor.close();
			}
			whereClause = null; 
		}
		return futureWeatherInfoEntity;
	}

	public static String convertArrayToString(String values[]){
		String result = "";
		if(values!=null){
			for(int k=0;k<values.length;k++){
				result = result+values[k];
			}
		}
		return result;
	}

	/**
	 * Method to find the given location is already existed in the data base or not.
	 * @param locationName
	 * @param context
	 * @return
	 */
	public static int isLocationExisted(String locationName,Context context){
		boolean result = false;
		MySqliteHelper sqliteHelper = null;
		SQLiteDatabase sqlDB = null;
		int index = -1;
		int key = -1;
		Cursor cursor = null;
		try{

			sqliteHelper =MySqliteHelper.getInstance(context);
			sqlDB = sqliteHelper.getReadableDatabase();

			cursor = sqlDB.query(LocationsTable.TABLE_LOCATIONS, new String[]{LocationsTable.COLUMN_ID,ParserConstants.WeatherConstants.QUERY}, null, null, null, null, null);
			if(cursor!=null && cursor.getCount()>0){
				while(!cursor.isClosed()&& cursor.moveToNext()){
					if(cursor.getColumnIndex(ParserConstants.WeatherConstants.QUERY)!=-1){
						index = cursor.getColumnIndex(ParserConstants.WeatherConstants.QUERY);
						if(locationName.toLowerCase().equalsIgnoreCase( cursor.getString(
								index).toLowerCase())){
							result = true;	
							key =  cursor.getInt(cursor.getColumnIndex(LocationsTable.COLUMN_ID));

							return key;

						}
					}

				}

			}

		}catch (Exception e) {
			Log.d(TAG, "Exception in isLocationExisted():"+e);
		}finally{

			if(sqlDB!=null){
				sqlDB.close();
			}
			if(sqliteHelper!=null){
				sqliteHelper.close();	
			}
			if(cursor!=null){
				cursor.close();	
			}

			sqlDB = null;
			sqliteHelper = null;
			cursor = null;
		}
		return -1;
	}


	public static void updateWeatherDB(Context context){
		ArrayList<WeatherAtLocation> locations = null;
		WeatherAtLocation weatherAtLocation =null;
		try{
			locations = WeatherDBHandler.getAddedLocations(context);
			if(locations!=null && locations.size()>0){
				for(int i=0;i<locations.size();i++){
					weatherAtLocation =(WeatherAtLocation)locations.get(i);
					
				}
				
			}

		}catch (Exception e) {
			Log.d(TAG, "Exception in updateWeatherDB():"+e);
		}finally{
			locations = null;
			 weatherAtLocation =null;
		}
	}


}
