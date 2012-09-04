package com.sourcebits.weatherapp.customcomponents;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.sourcebits.weatherapp.bussinessenities.WeatherAtLocation;

/**
 * Default Methods for Map Overlay.
 * 
 * @author Harinadh
 * 
 */
public class MapItemizedOverlay<Item extends OverlayItem> extends BalloonItemizedOverlay<WeatherItem> {

	/**
	 * List of Overlay on Map.
	 */
	private ArrayList<WeatherItem> m_overlays = null;

	/**
	 * Set Initial View.
	 * 
	 * @param map
	 * @param defaultMarker
	 */
	public MapItemizedOverlay(MapView map, Drawable defaultMarker) {
		super(boundCenter(defaultMarker),map);
		m_overlays = new ArrayList<WeatherItem>();
	}

	/**
	 * Populating the Pins on Map.
	 * 
	 * @param overlay
	 */
	public void addOverlay(WeatherItem overlay) {
		m_overlays.add(overlay);
		populate();
	}

	/**
	 * Clearing Overlay pins.
	 */
	public void clearPins() {
		m_overlays.clear();
		hideBalloon();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#createItem(int)
	 */
	@Override
	protected WeatherItem createItem(int i) {
		return m_overlays.get(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#size()
	 */
	@Override
	public int size() {
		return m_overlays.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.drillinginfo.beaumont.map.utils.BalloonItemizedOverlay#onBalloonTap
	 * (int)
	 */
//	@Override
//	protected boolean onBalloonTap(int index) {
//		Log.d("MyItemizedOverlay", "Ballon Tap");
//		return true;
//	}
}
