package com.midea.iot.pad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.midea.iot.R;

public class PadMainHoodWorkingView extends LinearLayout {
	private CusPadMainCircleBar mWorkingProgressBar;
	private ImageView mSelectedMode;
	private CusPadMainTimeBar mWorkingTimeBar;
	private ImageView mWorkingTimeIndicator;

	public PadMainHoodWorkingView(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public PadMainHoodWorkingView(Context context, AttributeSet attrs) {
//		super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public PadMainHoodWorkingView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.pad_main_hood_work_view, this, true);
		
		mWorkingProgressBar = (CusPadMainCircleBar) findViewById(R.id.working_progress_bar);
		mSelectedMode = (ImageView) findViewById(R.id.image_mode_selected);
		mWorkingTimeBar = (CusPadMainTimeBar) findViewById(R.id.working_time_bar);
		mWorkingTimeIndicator = (ImageView) findViewById(R.id.working_time_icon);
		
	}
	
	public void setHandler(Handler handler) {
		mWorkingProgressBar.setHandler(handler);
	}
	
	public void updateView(int time, int modeId, boolean moving) {
		mWorkingProgressBar.updateCircle(moving);
		mSelectedMode.setImageResource(modeId);
		mWorkingTimeBar.updateTime(time);
	}
}
