package com.midea.iot.pad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.midea.iot.R;

public class PadMainRobotCleanWorkingView extends LinearLayout {
	private CusPadMainBattery mBatteryIndicator;
	private ImageView mSelectedMode;
	private CusPadMainTimeBar mWorkingTimeBar;
	private ImageView mWorkingTimeIndicator;

	public PadMainRobotCleanWorkingView(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public PadMainRobotCleanWorkingView(Context context, AttributeSet attrs) {
//		super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public PadMainRobotCleanWorkingView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.pad_main_robotclean_work_view, this, true);
		
		mBatteryIndicator = (CusPadMainBattery) findViewById(R.id.battery_indicator);
		mSelectedMode = (ImageView) findViewById(R.id.image_mode_selected);
		mWorkingTimeBar = (CusPadMainTimeBar) findViewById(R.id.working_time_bar);
		mWorkingTimeIndicator = (ImageView) findViewById(R.id.working_time_icon);
		
	}
	
	public void updateView(int percent, int time) {
		mBatteryIndicator.updateBatteryCap(percent);
		mWorkingTimeBar.updateTime(time);
	}
}
