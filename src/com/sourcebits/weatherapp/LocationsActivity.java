package com.sourcebits.weatherapp;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcebits.weatherapp.bussinessenities.LocationEntity;
import com.sourcebits.weatherapp.bussinessenities.TimeZoneEnity;
import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;
import com.sourcebits.weatherapp.customcomponents.ClockAdapter;
import com.sourcebits.weatherapp.customcomponents.CustomListAdapter;
import com.sourcebits.weatherapp.database.WeatherDBHandler;
import com.sourcebits.weatherapp.serverconnections.ParserConstants;
import com.sourcebits.weatherapp.serverconnections.ParserUtilities;
import com.sourcebits.weatherapp.serverconnections.ServerConnection;
import com.sourcebits.weatherapp.utilities.UtilityFunctions;

/**
 * @author Harinadh
 * LocationsActivity to show the added locations and allow the user to add more locations.
 */
public class LocationsActivity extends Activity implements OnClickListener{

	/** Tag for class fully qualified class name.*/
	private static final String TAG =  LocationsActivity.class.getCanonicalName();


	//	private LinearLayout weatherDesription = null;
	private ProgressDialog progressDialog = null;

	private ClockAdapter clockAdapter = null;

	private TextView serachView = null;

	private TextView locationName= 	null;
	private TextView textViewTime= 	null;
	private TextView textViewTempC= null;
	private TextView textViewTempF= null;
	private TextView textViewHumidity= null;
	private ArrayList<WeatherAtLocation> locations = null;

	private RelativeLayout layOut = null;
	private LinearLayout weatherLayout = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		layOut = (RelativeLayout)getLayoutInflater().inflate(R.layout.activity_locations,null);

		setContentView(layOut);



		/* Creating Button variables by Id.*/
		Button mDonepButton =  (Button)findViewById(R.id.btnAdd);

		/* Register the buttons with OnClickListener*/
		mDonepButton.setOnClickListener(this);

		/* Creating Button variables by Id.*/
		Button mSearchButton =  (Button)findViewById(R.id.btnSearch);

		/* Register the buttons with OnClickListener*/
		mSearchButton.setOnClickListener(this);


		serachView = (TextView)findViewById(R.id.locationSearch);

		/* Creating   the clocks Gallery view */
		Gallery gallery = (Gallery) findViewById(R.id.gallery);


		locations = WeatherDBHandler.getAddedLocations(getApplicationContext());
		clockAdapter = new ClockAdapter(this,locations);

		/*  Set the adapter to our custom adapter (below) */
		gallery.setAdapter(clockAdapter);
		gallery.setSpacing(10);

		/* Set a item click listener */
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {


			}
		});
		weatherLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.weather_small_desrip, null);
		weatherLayout.setGravity(Gravity.CENTER_HORIZONTAL);

		//		weatherLayout.setPadding(0, 10, 0, 10);
		//		weatherDesription = (LinearLayout)findViewById(R.id.weatherIncluded);
		locationName = (TextView)weatherLayout.findViewById(R.id.locNameValue);
		textViewTime= 	(TextView)weatherLayout.findViewById(R.id.obserTimeValue);
		textViewTempC= 	(TextView)weatherLayout.findViewById(R.id.tempCValue);
		textViewTempF= 	(TextView)weatherLayout.findViewById(R.id.tempFValue);
		textViewHumidity= 	(TextView)weatherLayout.findViewById(R.id.humidityValue);


		/* Set a item click listener */
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				WeatherAtLocation weatherAtLocation = (WeatherAtLocation)arg0.getAdapter().getItem(arg2);
				locationName.setText(" "+weatherAtLocation.getQueryLocation());
				textViewTime.setText(" "+weatherAtLocation.getCurrentWeatherCondition()[0].getObservation_time());
				textViewTempC.setText(" "+weatherAtLocation.getCurrentWeatherCondition()[0].getTemp_C());
				textViewTempF.setText(" "+weatherAtLocation.getCurrentWeatherCondition()[0].getTemp_F());
				textViewHumidity.setText(" "+weatherAtLocation.getCurrentWeatherCondition()[0].getHumidity());
				if(layOut.getChildCount()<=2){
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
					layOut.addView(weatherLayout,layOut.getChildCount(),params);

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {


			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_locations, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnAdd){

			//			preparePoupWindow(locations.get(1));
			doAddButtonEvent();
			//			Intent in = new Intent(this,Dummyact.class);startActivity(in);

			//			MapLocationViewer mlv =new MapLocationViewer(getApplicationContext());

		}else if(v.getId() == R.id.btnSearch){
			doSearchButtonEvent();
		}
	}

	/**
	 * Method to get the weather information for the entered location.
	 */
	private void doAddButtonEvent(){
		String locationName = serachView.getText().toString();

		try{
			if(locationName!=null && !locationName.equalsIgnoreCase("")){

				if(UtilityFunctions.isNetworkConnected(getApplicationContext())){

					InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(
							getApplicationContext().INPUT_METHOD_SERVICE);

					inputMethodManager.hideSoftInputFromWindow(serachView.getWindowToken(), 0);

					getWeatherInfoFromServer(locationName);
				}else{
					Toast.makeText(this, R.string.network_error_text,
							Toast.LENGTH_LONG).show();
				}

			}else{
				Toast.makeText(this, R.string.enter_location_text,
						Toast.LENGTH_LONG).show();
			}
		}catch(Exception ex){
			Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
		}finally{
			locationName = null;
		}
	}

	/**
	 * Method to get the weather information for the entered location.
	 */
	private void doSearchButtonEvent(){
		String keyWords = serachView.getText().toString();
		
		try{
			if(keyWords!=null && !keyWords.equalsIgnoreCase("") && keyWords.length()>2){

				if(UtilityFunctions.isNetworkConnected(getApplicationContext())){

					InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(
							getApplicationContext().INPUT_METHOD_SERVICE);

					inputMethodManager.hideSoftInputFromWindow(serachView.getWindowToken(), 0);
					getLocationsDictionaryFromServer(keyWords);

				}else{
					Toast.makeText(this, R.string.network_error_text,
							Toast.LENGTH_LONG).show();
				}

			}else{
				Toast.makeText(this, R.string.enter_three_letters_text,
						Toast.LENGTH_LONG).show();
			}
		}catch(Exception ex){
			Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
		}finally{
			keyWords = null;
		}
	}

	/**
	 * method to get the Weather Info From Server and parsing it runs in a separate thread.
	 * returns nothing.
	 */
	private void getWeatherInfoFromServer(final String locationName){

		progressDialog = ProgressDialog.show(LocationsActivity.this,"",getString(R.string.loading_text));

		new Thread(new Runnable() {

			@Override
			public void run() {
				String weatherResponse= null;
				String timeZoneResponse= null;
				String urlWeather = null;
				String urlTimeZone = null;
				//
				try {

					urlWeather = "http://free.worldweatheronline.com/feed/weather.ashx?q="+locationName+"&format=json&num_of_days=5&key="+ParserConstants.WeatherConstants.API_KEY_FOR_WEATHER_SERVICES;//ServerConnection.getEncodedUrlString("http://free.worldweatheronline.com/feed/weather.ashx", "q="+locationName+"&format=json&num_of_days=5&key="+ParserConstants.WeatherConstants.API_KEY_FOR_WEATHER_SERVICES);
					urlWeather= urlWeather.replace(" ", "%20");

					weatherResponse = ServerConnection.getServerRespnse(urlWeather);
					urlTimeZone =  "http://www.worldweatheronline.com/feed/tz.ashx?key="+ParserConstants.WeatherConstants.API_KEY_FOR_WEATHER_SERVICES+"&q="+locationName+"&format=json";//ServerConnection.getEncodedUrlString("http://www.worldweatheronline.com/feed/tz.ashx","key="+ParserConstants.WeatherConstants.API_KEY_FOR_WEATHER_SERVICES+"&q="+locationName+"&&format=json");
					urlTimeZone= urlTimeZone.replace(" ", "%20");
					timeZoneResponse =ServerConnection.getServerRespnse(urlTimeZone);
					if(weatherResponse!=null){

						final WeatherAtLocation weatherAtLocation = ParserUtilities.getWeatherInfoEnity(weatherResponse);
						final TimeZoneEnity timeZoneEnity = ParserUtilities.getTimeZoneEnity(timeZoneResponse);
						weatherAtLocation.setOffsetInMilliseconds(timeZoneEnity.getUTCOffSet());

						Log.d(TAG, "*************** Server request sucess********************");
						if(weatherAtLocation!=null){

							boolean result = false;
							int key = WeatherDBHandler.isLocationExisted(weatherAtLocation.getQueryLocation(),  getApplicationContext());
							if(key!=-1){
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										progressDialog.setMessage("This location already existed.Updating DB.");

									}
								});

								result = WeatherDBHandler.insertDataTODB(weatherAtLocation, getApplicationContext(),true,key);
								locations = WeatherDBHandler.getAddedLocations(getApplicationContext());

								runOnUiThread(new Runnable() {
									@Override
									public void run() {

										clockAdapter.changeData(locations);
									}
								});

							}else{
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										clockAdapter.addElement(weatherAtLocation);
									}
								});

								result = WeatherDBHandler.insertDataTODB(weatherAtLocation, getApplicationContext(),false,-1);
								if(result){
									Log.d(TAG, "*************** data base request sucess********************");
								}
							}
						}
					}

				} catch (final ClientProtocolException e) {

					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						}
					});
				} catch (final IOException e) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						}
					});
				}
				catch (final JSONException e) {	
					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						}
					});

				}
				catch (final IllegalArgumentException e) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						}
					});
				}
				catch (final Exception e) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						}
					});
				}
				finally{
					urlWeather = null;
					urlTimeZone = null;
					timeZoneResponse= null;
					weatherResponse= null;
				}

				// dismiss the progress dialog
				progressDialog.dismiss();

			}
		}).start();
	}


	private void prepareLocationsPopUp(final ArrayList<LocationEntity> list){

		View inflater = null;
		try{

			inflater = getLayoutInflater().inflate(R.layout.locations_popup_layout,null,false);
			if (inflater != null) {
				WindowManager wm = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
				final PopupWindow popupWindow = new PopupWindow(inflater);
				popupWindow.setFocusable(true);

				int screenWidth = wm.getDefaultDisplay().getWidth();
				int screenHeight = wm.getDefaultDisplay().getHeight();
				popupWindow.setWidth(screenWidth-40);
				popupWindow.setHeight(screenHeight-180);

				ListView listView = (ListView)inflater.findViewById(R.id.locationsList);
				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent intent = null;
						popupWindow.dismiss();
						serachView.setText(((LocationEntity)list.get(position)).getAreaName()+","+((LocationEntity)list.get(position)).getCountry());
					}
				});
				CustomListAdapter adapter= new CustomListAdapter(getApplicationContext(), list);
				listView.setAdapter(adapter);

				popupWindow.showAtLocation(listView, Gravity.CENTER, 0,0);
				Button btnDismiss = (Button)inflater.findViewById(R.id.cancel1);
				btnDismiss.setOnClickListener(new Button.OnClickListener(){
					public void onClick(View v) {
						popupWindow.dismiss();
					}});

			}
		}
		catch(Exception ex){
			Log.d(TAG, "Exception in prepareVechileListPoup()"+ex);
		}
		finally{
			inflater = null;
		}

	}
	/**
	 * method to get the Weather Info From Server and parsing it runs in a separate thread.
	 * returns nothing.
	 */
	private void getLocationsDictionaryFromServer(final String keyWords){

		progressDialog = ProgressDialog.show(LocationsActivity.this,"",getString(R.string.loading_locations_text));

		new Thread(new Runnable() {

			@Override
			public void run() {
				String response= null;
				String urlLocSearch = null;

				try {

					urlLocSearch =  "http://www.worldweatheronline.com/feed/search.ashx?key="+ParserConstants.WeatherConstants.API_KEY_FOR_WEATHER_SERVICES+"&query="+keyWords+"&num_of_results=3&format=json";//ServerConnection.getEncodedUrlString("http://www.worldweatheronline.com/feed/search.ashx?key="+ParserConstants.WeatherConstants.API_KEY_FOR_WEATHER_SERVICES,"&query="+keyWords+"&num_of_results=3&format=json");
					urlLocSearch = urlLocSearch.replace(" ", "%20");
					response = ServerConnection.getServerRespnse(urlLocSearch);

					if(response!=null){

						final ArrayList<LocationEntity> list =  ParserUtilities.getLocationsList(response);
						Log.d(TAG, "*************** Server request sucess********************");
						if(list!=null && list.size()>0){
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									prepareLocationsPopUp(list);
								}
							});

						}else{

							runOnUiThread(new Runnable() {							
								public void run() {

									Toast.makeText(getApplicationContext(),"No Locations Found",Toast.LENGTH_LONG).show();
								}
							});
						}
					}

				} catch (final ClientProtocolException e) {

					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						}
					});
				} catch (final IOException e) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						}
					});
				}
				catch (final JSONException e) {	
					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						}
					});

				}
				catch (final IllegalArgumentException e) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						}
					});
				}
				catch (final Exception e) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						}
					});
				}
				finally{

					response= null;
				}

				// dismiss the progress dialog
				progressDialog.dismiss();


			}
		}).start();
	}
}


