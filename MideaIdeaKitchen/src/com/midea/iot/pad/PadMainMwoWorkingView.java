package com.midea.iot.pad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.midea.iot.R;

public class PadMainMwoWorkingView extends LinearLayout {
	
	private CusPadMainProgressBar mWorkingProgressBar;
	private ImageView mSelectedMode;
	private CusPadMainTimeBar mWorkingTimeBar;
	private ImageView mWorkingTimeIndicator;

	public PadMainMwoWorkingView(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public PadMainMwoWorkingView(Context context, AttributeSet attrs) {
//		super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}
	

	@SuppressLint("NewApi")
	public PadMainMwoWorkingView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.pad_main_mwo_work_view, this, true);
		
		mWorkingProgressBar = (CusPadMainProgressBar) findViewById(R.id.working_progress_bar);
		mSelectedMode = (ImageView) findViewById(R.id.image_mode_selected);
		mWorkingTimeBar = (CusPadMainTimeBar) findViewById(R.id.working_time_bar);
		mWorkingTimeIndicator = (ImageView) findViewById(R.id.working_time_icon);
		
	}
	
	public void setTimeIndicator(boolean hours) {
		if(hours) {
			mWorkingTimeIndicator.setImageResource(R.drawable.pad_main_time_icon_hour);
		} else {
			mWorkingTimeIndicator.setImageResource(R.drawable.pad_main_time_icon_min);
		}
	}
	
	public void updateView(int percent, int time, int modeId) {
		mWorkingProgressBar.updateProgress(percent);
		mSelectedMode.setImageResource(modeId);
		mWorkingTimeBar.updateTime(time);
	}
}
