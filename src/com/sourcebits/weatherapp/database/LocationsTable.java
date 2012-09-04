package com.sourcebits.weatherapp.database;

import com.sourcebits.weatherapp.serverconnections.ParserConstants;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Harinadh
 * LocationsTable class to create the locations database table.
 */
public class LocationsTable {


	/** Locations table name. */
	public static final String TABLE_LOCATIONS = "Locations";

	/*Locations table column names.*/
	public static final String COLUMN_ID = "_id";
	
	public static final String IMG_DATA = "image";

	/* Database creation SQL statement. */
	private static final String DATABASE_CREATE = "create table " 
		+ TABLE_LOCATIONS
		+ "(" 
		+ COLUMN_ID +" integer primary key autoincrement, " 
		+ParserConstants.WeatherConstants.QUERY+" text not null, "
		+ParserConstants.WeatherConstants.OFFSET+" long, "
		+ParserConstants.WeatherConstants.CLOUDCOVER+" text not null, "
		+ParserConstants.WeatherConstants.HUMIDITY+" text not null, "
		+ParserConstants.WeatherConstants.OBSERVATION_TIME+" text not null, "
		+ParserConstants.WeatherConstants.PRECIPMM+" text not null, "
		+ParserConstants.WeatherConstants.PRESSURE+" text not null, "
		+ParserConstants.WeatherConstants.TEMP_C+" text not null, "
		+ParserConstants.WeatherConstants.TEMP_F+" text not null, "
		+ParserConstants.WeatherConstants.VISIBILITY+" text not null, "
		+ParserConstants.WeatherConstants.WEATHER_DESC+" text not null, "
		+ParserConstants.WeatherConstants.WIND_DIR_DEGREE+" text not null, "
		+ParserConstants.WeatherConstants.WIND_SPEED_KMPH+" text not null, "
		+ParserConstants.WeatherConstants.WIND_SPEED_MILES+" text not null, "
		+IMG_DATA+" text not null"
		
		+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);

	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(LocationsTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
		onCreate(database);
	}
}
