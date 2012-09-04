/***
 * Copyright (c) 2010 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sourcebits.weatherapp.customcomponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.OverlayItem;
import com.sourcebits.weatherapp.R;
import com.sourcebits.weatherapp.utilities.UtilityFunctions;

/**
 * A view representing a MapView marker information balloon.
 * 
 * @author Jeff Gilfelt
 *
 */
public class BalloonOverlayView<Item extends OverlayItem> extends FrameLayout {

	private LinearLayout layout;
	private ImageView image ;
	private TextView header;
	private TextView title1;
	private TextView title2;
	private TextView title3;
	private TextView title4;

	/**
	 * Create a new BalloonOverlayView.
	 * 
	 * @param context - The activity context.
	 * @param balloonBottomOffset - The bottom padding (in pixels) to be applied
	 * when rendering this view.
	 */
	public BalloonOverlayView(Context context, int balloonBottomOffset) {

		super(context);
		////System.out.println("inside balloonoverlayView**********"+balloonBottomOffset);
		setPadding(10, 0, 10, balloonBottomOffset);

		layout = new LimitLinearLayout(context);
		layout.setVisibility(VISIBLE);

		setupView(context, layout);

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.NO_GRAVITY;

		addView(layout, params);

	}

	/**
	 * Inflate and initialize the BalloonOverlayView UI. Override this method
	 * to provide a custom view/layout for the balloon. 
	 * 
	 * @param context - The activity context.
	 * @param parent - The root layout into which you must inflate your view.
	 */
	protected void setupView(Context context, final ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.map_popup_layout, parent);
		image = (ImageView) v.findViewById(R.id.statusImgOnMAP);
		Bitmap weatherImg = UtilityFunctions.StringToBitMap("");
		if(weatherImg!=null)
			image.setImageBitmap(weatherImg);
		header =  (TextView) v.findViewById(R.id.locationHeaderOnMap);
		title1 = (TextView) v.findViewById(R.id.descripOnMap);
		title2 = (TextView) v.findViewById(R.id.dateOnMap);
		title3 = (TextView) v.findViewById(R.id.minTempOnMapValue);
		title4 = (TextView) v.findViewById(R.id.maxTempOnMapValue);

	}

	/**
	 * Sets the view data from a given overlay item.
	 * 
	 * @param item - The overlay item containing the relevant view data. 
	 */
	public void setData(WeatherItem item) {
		layout.setVisibility(VISIBLE);
		setBalloonData(item, layout);
	}

	/**
	 * Sets the view data from a given overlay item. Override this method to create
	 * your own data/view mappings.
	 * 
	 * @param item - The overlay item containing the relevant view data.
	 * @param parent - The parent layout for this BalloonOverlayView.
	 */
	protected void setBalloonData(WeatherItem item, ViewGroup parent) {
		if (item.getLocation() != null) {
			header.setVisibility(VISIBLE);
			header.setText(item.getLocation());
		} else {
			header.setText("");
			header.setVisibility(GONE);
		}

		if (item.getImageDtata() != null) {
			image.setVisibility(VISIBLE);
			image.setImageBitmap(UtilityFunctions.StringToBitMap(item.getImageDtata()));
		} else {
			image.setVisibility(GONE);
		}


		if (item.getDesrip() != null) {
			title1.setVisibility(VISIBLE);
			title1.setText(item.getDesrip());
		} else {
			title1.setText("");
			title1.setVisibility(GONE);
		}

		if (item.getDate() != null) {
			title2.setVisibility(VISIBLE);
			title2.setText(item.getDate());
		} else {
			title2.setText("");
			title2.setVisibility(GONE);
		}

		if (item.getTempMin() != null) {
			title3.setVisibility(VISIBLE);
			title3.setText(item.getTempMin());
		} else {
			title3.setText("");
			title3.setVisibility(GONE);
		}

		if (item.getTempMax() != null) {
			title4.setVisibility(VISIBLE);
			title4.setText(item.getTempMax());
		} else {
			title4.setText("");
			title4.setVisibility(GONE);
		}
	}

	private class LimitLinearLayout extends LinearLayout {

		private static final int MAX_WIDTH_DP = 280;

		final float SCALE = getContext().getResources().getDisplayMetrics().density;

		public LimitLinearLayout(Context context) {
			super(context);
		}

		public LimitLinearLayout(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			int mode = MeasureSpec.getMode(widthMeasureSpec);
			int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
			int adjustedMaxWidth = (int)(MAX_WIDTH_DP * SCALE + 0.5f);
			int adjustedWidth = Math.min(measuredWidth, adjustedMaxWidth);
			int adjustedWidthMeasureSpec = MeasureSpec.makeMeasureSpec(adjustedWidth, mode);
			super.onMeasure(adjustedWidthMeasureSpec, heightMeasureSpec);
		}
	}

}
