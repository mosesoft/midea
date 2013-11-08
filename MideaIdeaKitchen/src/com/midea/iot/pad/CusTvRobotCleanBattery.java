package com.midea.iot.pad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midea.iot.R;

public class CusTvRobotCleanBattery extends LinearLayout {
	
	private ImageView mCleanBatteryBg = null;
	private ImageView mCleanBatteryCap = null;
	private TextView mCleanBatteryPercent = null;
	private boolean mEnable = false;
	private int mBatteryPercent = 0;
	ColorStateList mCslWorkingOn;
	ColorStateList mCslWorkingOff;
	
	private static final int BATTERY_LEN = 39;

	public CusTvRobotCleanBattery(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public CusTvRobotCleanBattery(Context context, AttributeSet attrs) {
//		super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public CusTvRobotCleanBattery(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.cus_tv_robot_battery, this, true);
		mCleanBatteryBg = (ImageView) findViewById(R.id.image_clean_battery_bg);
		mCleanBatteryCap = (ImageView) findViewById(R.id.image_clean_battery_cap);
		mCleanBatteryPercent = (TextView) findViewById(R.id.text_battery_percent);
		/*Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Narrow.ttf");
		mCleanBatteryPercent.setTypeface(tf);*/
		
		Resources resource = context.getResources();  
		mCslWorkingOn = resource.getColorStateList(R.color.tv_text_working_on);
		mCslWorkingOff = resource.getColorStateList(R.color.tv_text_working_off);
	}
	
	private void setBatteryCap(int percent) {
		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mCleanBatteryCap.getLayoutParams();
		lp.width = BATTERY_LEN * percent / 100;
		mCleanBatteryCap.setLayoutParams(lp);
		
		mCleanBatteryPercent.setText(percent + "%");
	}
	
	public void setBatteryStatus(boolean enable) {
		mEnable = enable;	
		if (enable) {
			mCleanBatteryBg.setImageResource(R.drawable.tv_clean_battery_bg_on);
			mCleanBatteryCap.setVisibility(View.VISIBLE);
			setBatteryCap(mBatteryPercent);
			mCleanBatteryPercent.setTextColor(mCslWorkingOn);
		} else {
			mCleanBatteryBg
					.setImageResource(R.drawable.tv_clean_battery_bg_off);
			mCleanBatteryCap.setVisibility(View.GONE);
			mCleanBatteryPercent.setVisibility(View.GONE);
		}
	}
	
	public void updateBatteryCap(int percent) {
		if(mBatteryPercent != percent) {
			if(percent > 100) percent = 100;
			if(percent < 0) percent = 0;
			mBatteryPercent = percent;
			if(mEnable) {
				setBatteryCap(percent);
			}
		}
	}

}
