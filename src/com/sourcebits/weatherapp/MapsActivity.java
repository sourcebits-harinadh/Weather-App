package com.sourcebits.weatherapp;

import java.util.ArrayList;
import java.util.List;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;
import com.sourcebits.weatherapp.customcomponents.CustomLocationManager;
import com.sourcebits.weatherapp.customcomponents.CustomMarkerPoint;
import com.sourcebits.weatherapp.customcomponents.MapItemizedOverlay;
import com.sourcebits.weatherapp.customcomponents.WeatherItem;
import com.sourcebits.weatherapp.database.WeatherDBHandler;
import com.sourcebits.weatherapp.serverconnections.LocationProvider;
import com.sourcebits.weatherapp.serverconnections.LocationsThread;

/**
 * @author Harinadh
 * MapsActivity to show the added locations and current location on the map.
 */
public class MapsActivity extends MapActivity implements LocationListener ,LocationProvider{

	/** Tag for class fully qualified class name.*/
	private static final String TAG =  MapsActivity.class.getCanonicalName();

	private com.google.android.maps.MapView  mMapView = null;
	private CustomMarkerPoint mCurrentLocationMarker = null;
	private ArrayList<WeatherAtLocation> locations = null;

	private MapItemizedOverlay<WeatherItem> balloonItemizedOverlay = null;

	private LocationsThread locationsThread = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		/* creating map view object */
		mMapView =  (MapView)findViewById(R.id.myMapView);

		/* creating zoom controls from map view object */
		View zoomContorlsView = mMapView.getZoomControls();

		/* adding zoom controls view to LinearLayout */
		LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoomLayout);		
		zoomLayout.addView(zoomContorlsView, 
				new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, 
						LayoutParams.WRAP_CONTENT)); 

		/* Enabling zoom controls */
		mMapView.displayZoomControls(true);
		mMapView.getOverlays().clear();
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ShowAllLocationsOnMap();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();		
	}
	/**
	 * This method clears the lod locations and shows all new  locations on map.
	 */
	private void ShowAllLocationsOnMap(){
	
		if(balloonItemizedOverlay==null){
			balloonItemizedOverlay = new MapItemizedOverlay<WeatherItem>(mMapView,getResources().getDrawable(R.drawable.pin_without_shadow));
		}
		else{
			balloonItemizedOverlay.clearPins();
		}
		
		mMapView.invalidate();
		locations = WeatherDBHandler.getAddedLocations(getApplicationContext());
		if(locations!=null && locations.size()>0){
			String locationNames[] = new String[locations.size()];
			for(int i=0;i<locations.size();i++)	{
				locationNames[i] = ((WeatherAtLocation)locations.get(i)).getQueryLocation();
			}
			if(locationsThread==null || !locationsThread.isAlive()){
				locationsThread = new LocationsThread(locationNames,getApplicationContext(),this);
				locationsThread.start();
			}
		}

	}
	/**
	 * Method to get current location.
	 * @return GeoPoint object.
	 */
	private GeoPoint getCurrentLocation(){
		GeoPoint point = null;
		try{
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			LocationManager locationMgr = CustomLocationManager.getInstance(this);
			locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 100, this);
			String provider = locationMgr.getBestProvider(criteria, true);
			Location location = locationMgr.getLastKnownLocation(provider);
			if(location==null){
				Log.d(TAG,"################# NO location found with network provider");
				locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 100, this);
				provider = locationMgr.getBestProvider(criteria, true);
				location = locationMgr.getLastKnownLocation(provider);
			}
			if(location!=null){
				point = new GeoPoint((int)(location.getLatitude()*1E6), (int)(location.getLongitude()*1E6));
				Log.d("#################Location lattitude", location.getLatitude() + "");
				Log.d("################Location longitude", location.getLongitude() + "");

			}else{
				Log.d(TAG,"################# NO location found with GPS provider");
				Toast.makeText(this, "Unable to find current location",
						Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception e) {
			Log.d(TAG, "Exception in getCurrentLocation:"+e);	
			Toast.makeText(this, "Exception in getCurrentLocation():"+e,
					Toast.LENGTH_LONG).show();
		}

		return point;
	}


	/**
	 * Method to draw a marker on map at specified GeoPoint.
	 * @param markerImgId
	 * @param markerPoint
	 */
	private void showCurrentLocation(int markerImgId,GeoPoint markerPoint){
		try{

			List<Overlay> listOfOverlays = mMapView.getOverlays();
			//			if(listOfOverlays!=null && listOfOverlays.size()>0){
			//				listOfOverlays.clear();	
			//				mMapView.invalidate();
			//				mCurrentLocationMarker = null;
			//			}

			/* Creating CustomMarkerPoint */
			mCurrentLocationMarker = new CustomMarkerPoint(markerImgId, markerPoint, this.getApplicationContext());
			listOfOverlays.add(mCurrentLocationMarker);   
			mMapView.invalidate();
		}
		catch (Exception e) {
			Log.d(TAG, "Exception in showCurrentLocation():"+e);	
			Toast.makeText(this, "Exception in showCurrentLocation():"+e,
					Toast.LENGTH_LONG).show();
		}
	}



	public void onLocationChanged(Location location) {

		if (location != null) {
			Log.d("LOCATION CHANGED", location.getLatitude() + "");
			Log.d("LOCATION CHANGED", location.getLongitude() + "");
			showCurrentLocation(R.drawable.pin_without_shadow,new GeoPoint((int)(location.getLatitude()*1E6), (int)(location.getLongitude()*1E6)));
		}
	}
	@Override
	public void onProviderDisabled(String provider) {
	}
	@Override
	public void onProviderEnabled(String provider) {
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void locationFound(final GeoPoint point,final int index) {
		// TODO Auto-generated method stub
		if(point!=null){
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					WeatherAtLocation weatherAtLocation = (WeatherAtLocation)locations.get(index);
					WeatherItem ovi = new WeatherItem(point, "", "",weatherAtLocation);
					balloonItemizedOverlay.addOverlay(ovi);
					
					if(index==locations.size()-1){
						final MapController mc = mMapView.getController();
						mc.animateTo(point);
						mc.setZoom(7);
						mMapView.getOverlays().add(balloonItemizedOverlay);
						mMapView.invalidate();
					}
				}
			});

		}

	}


}
