package com.sourcebits.weatherapp.customcomponents;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.sourcebits.weatherapp.R;
import com.sourcebits.weatherapp.utilities.UtilityFunctions;


/**
 * @author Harinadh
 * CustomMarkerPoint class to draw a marker at specified location.
 */
public class CustomMarkerPoint extends Overlay {


	private GeoPoint markerPoint = null;
	private int markerImgId;
	private Context context = null;
	private Rect rect =null;
	GestureOverlayView gd = null;
	public CustomMarkerPoint(int markerImgId,GeoPoint markerPoint,Context context){
		this.markerImgId = markerImgId;
		this.markerPoint = markerPoint;
		this.context = context;


	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,long when) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);

		/* converting GeoPoint to screen coordinates */ 
		Point screenPoint = new Point();
		mapView.getProjection().toPixels(markerPoint, screenPoint);

		/* adding  the marker point */
		Bitmap markerImg = BitmapFactory.decodeResource(context.getResources(), markerImgId);       

		//		rect =  new Rect(screenPoint.x, screenPoint.y, screenPoint.x+markerImg.getWidth(), screenPoint.y+markerImg.getHeight());

		canvas.drawBitmap(markerImg, screenPoint.x, screenPoint.y, null);    

		return true;

	}


	
//	public boolean onTouchEvent(MotionEvent event, MapView mapView) 
//	{  
//
//		if (event.getAction() == 1) {   
//			//			if(rect.contains((int) event.getX(),(int) event.getY())){
//
//			//				GeoPoint point = mapView.getProjection().fromPixels((int) event.getX(),(int) event.getY());
//
//			String address = UtilityFunctions.getAddressFromLatAndLong(markerPoint, context);
//			if(address!=null && !address.equals("")){
//				Toast.makeText(context, address, Toast.LENGTH_LONG).show();
//				//					showPopUP(address);
//			}else{
//				Toast.makeText(context, "Lattitude:"+markerPoint.getLatitudeE6()/1E6 + ",Longitude:" +	markerPoint.getLongitudeE6()/1E6 ,Toast.LENGTH_LONG).show();
//			}
//			//			}
//			return true;
//
//		}else{                            
//			return false;
//		}
//	}
	//
	//	
	//
	//	private void showPopUP(String text){
	//		
	//		View inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.addresstextlayout, null, false);
	//		final PopupWindow popupWindow = new PopupWindow(inflater);
	//		popupWindow.setFocusable(true);
	//		Bitmap popupImg = BitmapFactory.decodeResource(context.getResources(),R.drawable.tool_tip);
	//		popupWindow.setWidth(200);
	//		popupWindow.setHeight(LayoutParams.FILL_PARENT);
	//
	//		TextView textView = (TextView)inflater.findViewById(R.id.addressText);
	//		textView.setText(text);
	//		
	//		
	//		popupWindow.showAtLocation(textView, Gravity.CENTER_HORIZONTAL, 0,40);
	//	}



}
