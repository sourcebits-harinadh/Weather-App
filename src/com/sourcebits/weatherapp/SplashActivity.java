package com.sourcebits.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity{

	protected boolean _active = true;
	protected int _splashTime = 2000; // time to display the splash screen in ms.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		// thread for displaying the SplashScreen
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while(_active && (waited < _splashTime)) {
						sleep(100);
						if(_active) {
							waited += 100;
						}
					}
				} catch(InterruptedException e) {
					// do nothing
				} finally {
					finish();
					startActivity(new Intent(SplashActivity.this,WeatherHomeActivity.class));
					stop();
				}
			}
		};
		splashTread.start();
	}

}
