package com.sourcebits.weatherapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Harinadh
 *
 */
public  class MySqliteHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "weather.db";
	private static final int DATABASE_VERSION = 1;

	public static MySqliteHelper mySqliteHelper = null;
	
	public static MySqliteHelper getInstance(Context context){
		if(mySqliteHelper==null){
			mySqliteHelper = new MySqliteHelper(context);
		}
		return mySqliteHelper;
	}

	private MySqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub.
		LocationsTable.onCreate(db);
		WeatherDetailsTable.onCreate(db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		LocationsTable.onUpgrade(db, oldVersion, newVersion);
		WeatherDetailsTable.onUpgrade(db, oldVersion, newVersion);

	}



}
