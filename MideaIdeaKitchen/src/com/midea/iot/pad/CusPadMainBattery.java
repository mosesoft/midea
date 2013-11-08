package com.midea.iot.pad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midea.iot.R;

public class CusPadMainBattery extends LinearLayout {
	
	private ImageView mBatteryBg = null;
	private ImageView mBatteryCap = null;
	private TextView mTextBatteryPercent = null;
//	private boolean mEnable = false;
	private int mBatteryPercent = 0;
	
	private static final int BATTERY_LEN = 31;

	public CusPadMainBattery(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public CusPadMainBattery(Context context, AttributeSet attrs) {
//		super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public CusPadMainBattery(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.cus_pad_main_battery, this, true);
		mBatteryBg = (ImageView) findViewById(R.id.image_battery_bg);
		mBatteryCap = (ImageView) findViewById(R.id.image_battery_cap);
		mTextBatteryPercent = (TextView) findViewById(R.id.text_battery_percent);
//		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Narrow.ttf");
//		mTextBatteryPercent.setTypeface(tf);
		
	}
	
	private void setBatteryCap(int percent) {
		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mBatteryCap.getLayoutParams();
		lp.width = BATTERY_LEN * percent / 100;
		mBatteryCap.setLayoutParams(lp);
		
		mTextBatteryPercent.setText(percent + "%");
	}
	
	public void setBatteryStatus(boolean enable) {
		/*if(mEnable != enable) {
			mEnable = enable;
		}*/
	}
	
	public void updateBatteryCap(int percent) {
		if(mBatteryPercent != percent) {
			if(percent > 100) percent = 100;
			if(percent < 0) percent = 0;
			mBatteryPercent = percent;
			setBatteryCap(percent);
			/*if(mEnable) {
				
			}*/
		}
	}
}
