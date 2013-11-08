package com.midea.iot.pad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.midea.iot.R;

public class CusTvHobInfoBar extends LinearLayout {
	private ImageView mImageHobTime = null;
	private ImageView mPowerLevelNumber = null;
	private ImageView mPowerLevelIcon = null;
	private CusTvTimeBar mHobTime = null;  //id: cusTvTimeBar_hob
	
	private int mPowerLevel = 1;
	private boolean mEnable = false;
	
	private int mIconList[] = {
		R.drawable.tv_hob_power_icon_disable_off,
		R.drawable.tv_hob_power_icon_1,
		R.drawable.tv_hob_power_icon_2,
		R.drawable.tv_hob_power_icon_3,
		R.drawable.tv_hob_power_icon_4,
		R.drawable.tv_hob_power_icon_5,
		R.drawable.tv_hob_power_icon_6,
		R.drawable.tv_hob_power_icon_7,
		R.drawable.tv_hob_power_icon_8,
		R.drawable.tv_hob_power_icon_9,
	};
	
	private int mEnableNumberList[] = {
		0,
		R.drawable.tv_hob_power_number_1_enable,
		R.drawable.tv_hob_power_number_2_enable,
		R.drawable.tv_hob_power_number_3_enable,
		R.drawable.tv_hob_power_number_4_enable,
		R.drawable.tv_hob_power_number_5_enable,
		R.drawable.tv_hob_power_number_6_enable,
		R.drawable.tv_hob_power_number_7_enable,
		R.drawable.tv_hob_power_number_8_enable,
		R.drawable.tv_hob_power_number_9_enable,
	};
	
	private int mDisableNumberList[] = {
		0,
		R.drawable.tv_hob_power_number_1_disable,
		R.drawable.tv_hob_power_number_2_disable,
		R.drawable.tv_hob_power_number_3_disable,
		R.drawable.tv_hob_power_number_4_disable,
		R.drawable.tv_hob_power_number_5_disable,
		R.drawable.tv_hob_power_number_6_disable,
		R.drawable.tv_hob_power_number_7_disable,
		R.drawable.tv_hob_power_number_8_disable,
		R.drawable.tv_hob_power_number_9_disable,
	};

	public CusTvHobInfoBar(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public CusTvHobInfoBar(Context context, AttributeSet attrs) {
//		super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public CusTvHobInfoBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.cus_tv_hob_info_bar, this, true);
		mImageHobTime = (ImageView) findViewById(R.id.image_hob_time);
		mPowerLevelNumber = (ImageView) findViewById(R.id.image_hob_power_level_number);
		mPowerLevelIcon = (ImageView) findViewById(R.id.image_hob_power_level_icon);
		mHobTime = (CusTvTimeBar) findViewById(R.id.cusTvTimeBar_hob);
	}
	
	public void setHobInfo(int powerLevel, int totalTime) {
		if(mPowerLevel != powerLevel) {
			mPowerLevel = powerLevel;
			setPowerLevel(powerLevel);
		}
		mHobTime.setTotalTime(totalTime);
	}
	
	public void setHobInfoBarStatus(boolean enable) {
		mEnable = enable;
		mHobTime.setTimeBarStatus(enable);
		
		setPowerLevel(mPowerLevel);
	}
	
	public void setPowerLevel(int powerLevel) {
		if(powerLevel < 1) powerLevel = 1;
		if(powerLevel > 9) powerLevel = 9;
		
		if(mEnable) {
			mPowerLevelNumber.setImageResource(mEnableNumberList[powerLevel]);
			mPowerLevelIcon.setImageResource(mIconList[powerLevel]);
			mImageHobTime.setImageResource(R.drawable.tv_hob_time_sel_on);
		} else {
			mPowerLevelNumber.setImageResource(mDisableNumberList[powerLevel]);
			mPowerLevelIcon.setImageResource(mIconList[0]);
			mImageHobTime.setImageResource(R.drawable.tv_hob_time_unsel_on);
		}
	}
	
	public void updateTime(int time) {
		mHobTime.updateTime(time);
	}

}
