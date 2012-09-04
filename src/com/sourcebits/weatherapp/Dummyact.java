package com.sourcebits.weatherapp;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sourcebits.weatherapp.serverconnections.ServerConnection;
import com.sourcebits.weatherapp.utilities.UtilityFunctions;

public class Dummyact extends Activity implements OnClickListener{
	ImageView iv = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dummy);

		/* Creating Button variables by Id.*/
		Button mDonepButton =  (Button)findViewById(R.id.btnLoad);

		/* Register the buttons with OnClickListener*/
		mDonepButton.setOnClickListener(this);
	
		iv = (ImageView)findViewById(R.id.test);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnLoad){
			
			
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					String response= null;

					try {
//						String url = "http://free.worldweatheronline.com/feed/weather.ashx?q="+locationName+"&format=json&num_of_days=5&key="+ParserConstants.WeatherConstants.API_KEY_FOR_WEATHER_SERVICES;
//						response = ServerConnection.getServerRespnse(url);
						final Bitmap temp = ServerConnection.downLoadImg("http://www.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0017_cloudy_with_light_rain.png");
						String encodedString = UtilityFunctions.BitMapToString(temp);
						final Bitmap temp1 = UtilityFunctions.StringToBitMap(encodedString);
//						if(response!=null){
//							final WeatherAtLocation weatherAtLocation = ParserUtilities.getWeatherInfoEnity(response);
//							Log.d(TAG, "*************** Server request sucess********************");
//							if(weatherAtLocation!=null){

								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										iv.setImageBitmap(temp1);
//										clockAdapter.addElement(weatherAtLocation);
									}
								});

//								boolean result = WeatherDBHandler.insertDataTODB(weatherAtLocation, getApplicationContext());
	//
//								if(result){
//									Log.d(TAG, "*************** data base request sucess********************");
//								}
//							}
//						}

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

					}

					// dismiss the progress dialog
				

				}
			}).start();
		}
		
	}
}
