package com.sourcebits.weatherapp.customcomponents;

import java.util.TimeZone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RemoteViews.RemoteView;

import com.sourcebits.weatherapp.R;



/**
 * This widget display an analogic clock with two hands for hours and minutes.
 */
@RemoteView
public class MyAnalogClock extends View {
	public MyAnalogClock(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private Time mCalendar;

	private Drawable mHourHand;
	private Drawable mMinuteHand;
	private Drawable mSecondHand;
	private Drawable mDial;

	private int mDialWidth;
	private int mDialHeight;

	private boolean mAttached;

	private final Handler mHandler = new Handler();
	private float mMinutes;
	private float mHour;
	private boolean mChanged;

	Context mContext;

	public MyAnalogClock(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyAnalogClock(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Resources r = context.getResources();
		//		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnalogClock, defStyle, 0);
		mContext = context;
		// mDial =
		// a.getDrawable(com.android.internal.R.styleable.AnalogClock_dial);
		// if (mDial == null) {
		mDial = r.getDrawable(R.drawable.clock_dial);
		// }

		// mHourHand =
		// a.getDrawable(com.android.internal.R.styleable.AnalogClock_hand_hour);
		// if (mHourHand == null) {
		mHourHand = r.getDrawable(R.drawable.clock_hour);
		// }

		// mMinuteHand =
		// a.getDrawable(com.android.internal.R.styleable.AnalogClock_hand_minute);
		// if (mMinuteHand == null) {
		mMinuteHand = r.getDrawable(R.drawable.clock_minute);
		mSecondHand = r.getDrawable(R.drawable.clockgoog_minute);
		// }

		mCalendar = new Time();

		mDialWidth = mDial.getIntrinsicWidth();
		mDialHeight = mDial.getIntrinsicHeight();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		if (!mAttached) {
			mAttached = true;
			IntentFilter filter = new IntentFilter();

			filter.addAction(Intent.ACTION_TIME_TICK);
			filter.addAction(Intent.ACTION_TIME_CHANGED);
			filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);

			getContext().registerReceiver(mIntentReceiver, filter, null,
					mHandler);
		}

		// NOTE: It's safe to do these after registering the receiver since the
		// receiver always runs
		// in the main thread, therefore the receiver can't run before this
		// method returns.

		// The time zone may have changed while the receiver wasn't registered,
		// so update the Time
		mCalendar = new Time();

		// Make sure we update to the current time
		onTimeChanged();
		counter.start();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mAttached) {
			counter.cancel();
			getContext().unregisterReceiver(mIntentReceiver);
			mAttached = false;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		float hScale = 1.0f;
		float vScale = 1.0f;

		if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < mDialWidth) {
			hScale = (float) widthSize / (float) mDialWidth;
		}

		if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < mDialHeight) {
			vScale = (float) heightSize / (float) mDialHeight;
		}

		float scale = Math.min(hScale, vScale);

		setMeasuredDimension(
				resolveSize((int) (mDialWidth * scale), widthMeasureSpec),
				resolveSize((int) (mDialHeight * scale), heightMeasureSpec));
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mChanged = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		boolean changed = mChanged;
		if (changed) {
			mChanged = false;
		}
		boolean seconds = mSeconds;
		if (seconds) {
			mSeconds = false;
		}
		int availableWidth = 200;
		int availableHeight = 200;

		int x = availableWidth / 2;
		int y = availableHeight / 2;

		final Drawable dial = mDial;
		int w = dial.getIntrinsicWidth();
		int h = dial.getIntrinsicHeight();

		boolean scaled = false;

		if (availableWidth < w || availableHeight < h) {
			scaled = true;
			float scale = Math.min((float) availableWidth / (float) w,
					(float) availableHeight / (float) h);
			canvas.save();
			canvas.scale(scale, scale, x, y);
		}

		if (changed) {
			dial.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
		}
		dial.draw(canvas);

		canvas.save();
		canvas.rotate(mHour / 12.0f * 360.0f, x, y);
		final Drawable hourHand = mHourHand;
		if (changed) {
			w = hourHand.getIntrinsicWidth();
			h = hourHand.getIntrinsicHeight();
			hourHand.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y
					+ (h / 2));
		}
		hourHand.draw(canvas);
		canvas.restore();

		canvas.save();
		canvas.rotate(mMinutes / 60.0f * 360.0f, x, y);
		// canvas.rotate(mSecond, x, y);
		final Drawable minuteHand = mMinuteHand;
		if (changed) {
			w = minuteHand.getIntrinsicWidth();
			h = minuteHand.getIntrinsicHeight();
			minuteHand.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y
					+ (h / 2));
		}
		minuteHand.draw(canvas);
		canvas.restore();
		canvas.save();
		canvas.rotate(mSecond, x, y);

		// minuteHand = mMinuteHand;
		if (seconds) {
			w = mSecondHand.getIntrinsicWidth();
			h = mSecondHand.getIntrinsicHeight();
			mSecondHand.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y
					+ (h / 2));
		}
		mSecondHand.draw(canvas);
		canvas.restore();
		if (scaled) {
			canvas.restore();
		}
	}

	MyCount counter = new MyCount(10000, 1000);

	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			counter.start();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mCalendar.set(System.currentTimeMillis() - 60000);

			//			int hour = mCalendar.hour;
			//			int minute = mCalendar.minute;
			int second = mCalendar.second;

			mSecond = 6.0f * second;
			mSeconds = true;
			// mChanged = true;
			MyAnalogClock.this.invalidate();
			// Toast.makeText(mContext, "text", Toast.LENGTH_LONG).show();
		}
	}

	public void setOffset(long offset){
		this.offset = offset;
		onTimeChanged();
	}

	public void setTime(long time){
		long tmp = System.currentTimeMillis() - time;
		this.offset = tmp;
		onTimeChanged();
	}

	boolean mSeconds = false;
	float mSecond = 0;
	long offset = 0;

	private void onTimeChanged() {
		mCalendar.set(System.currentTimeMillis() - offset);

		int hour = mCalendar.hour;
		int minute = mCalendar.minute;
		int second = mCalendar.second;

		mMinutes = minute + second / 60.0f;
		mHour = hour + mMinutes / 60.0f;
		mChanged = true;
	}

	private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)) {
				String tz = intent.getStringExtra("time-zone");
				mCalendar = new Time(TimeZone.getTimeZone(tz).getID());
			}

			onTimeChanged();

			invalidate();
		}
	};
}