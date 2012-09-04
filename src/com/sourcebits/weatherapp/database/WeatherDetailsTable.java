package com.sourcebits.weatherapp.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sourcebits.weatherapp.serverconnections.ParserConstants;

/**
 * @author Harinadh
 * WeatherDetails class to create the weather details database table.
 */
public class WeatherDetailsTable {

	/** Weather Details table name. */
	public static final String TABLE_WEATHER_DETAILS = "WeatherDetails";

	/*Weather Details table column names.*/
	public static final String COLUMN_ID = "_id";

	/*Weather Details table column names.*/
	public static final String LOCATIONS_ID = "locationsId";

	/* Database creation SQL statement. */
	private static final String DATABASE_CREATE = "create table " 
		+ TABLE_WEATHER_DETAILS
		+ "(" 
		+ COLUMN_ID +" integer primary key autoincrement, " 
		+LOCATIONS_ID+" long, "
		+ParserConstants.WeatherConstants.DATE+" text not null, "
		+ParserConstants.WeatherConstants.PRECIPMM+" text not null, "
		+ParserConstants.WeatherConstants.TEMP_MAX_F+" text not null, "
		+ParserConstants.WeatherConstants.TEMP_MAX_C+" text not null, "
		+ParserConstants.WeatherConstants.TEMP_MIN_F+" text not null, "
		+ParserConstants.WeatherConstants.TEMP_MIN_C+" text not null, "
		+ParserConstants.WeatherConstants.WEATHER_DESC+" text not null, "
		+ParserConstants.WeatherConstants.WEATHER_ICON_URL+" text not null, "
		+ParserConstants.WeatherConstants.WIND_DIR_16POINT+" text not null, "
		+ParserConstants.WeatherConstants.WIND_DIR_DEGREE+" text not null, "
		+ParserConstants.WeatherConstants.WIND_DIRECTION+" text not null, "
		+ParserConstants.WeatherConstants.WIND_SPEED_KMPH+" text not null, "
		+ParserConstants.WeatherConstants.WIND_SPEED_MILES+" text not null, "
		+LocationsTable.IMG_DATA+" text not null"
		+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);

	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(LocationsTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER_DETAILS);
		onCreate(database);
	}
}
