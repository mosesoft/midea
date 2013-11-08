package com.midea.iot.pad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midea.iot.R;

public class CusTvInfoBox extends LinearLayout {
	
	private ImageView mInfoBoxBg = null;
	private TextView  mInfoSettingValue = null;
	private TextView  mInfoWorkingValue = null;
	private boolean mEnable = false;;
	private String mSettingValue;
	private String mWorkingValue;
	ColorStateList mCslSettingOn;
	ColorStateList mCslSettingOff;
	ColorStateList mCslWorkingOn;
	ColorStateList mCslWorkingOff;
	
	public CusTvInfoBox(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public CusTvInfoBox(Context context, AttributeSet attrs) {
//		super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public CusTvInfoBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.cus_tv_info_box, this, true);
		
		mInfoBoxBg = (ImageView) findViewById(R.id.image_tv_info_box);
		mInfoSettingValue = (TextView) findViewById(R.id.text_tv_setting_value);
		mInfoWorkingValue = (TextView) findViewById(R.id.text_tv_working_value);
		
		/*Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Narrow.ttf");
		mInfoSettingValue.setTypeface(tf);
		mInfoWorkingValue.setTypeface(tf);*/
		
		Resources resource = context.getResources();  
		mCslSettingOn = resource.getColorStateList(R.color.tv_text_setting_on);
		mCslSettingOff = resource.getColorStateList(R.color.tv_text_setting_off);
		mCslWorkingOn = resource.getColorStateList(R.color.tv_text_working_on);
		mCslWorkingOff = resource.getColorStateList(R.color.tv_text_working_off);
	}

	public void setInfoBox(String setting, String working) {
		updateInfomation(setting, working);
	}
	
	@SuppressLint("ResourceAsColor")
	public void setInfoBoxStatus(boolean enable) {
		mEnable = enable;
		
		if(enable) {
			mInfoBoxBg.setImageResource(R.drawable.tv_info_box_sel);
			  
			mInfoSettingValue.setTextColor(mCslSettingOn);
			mInfoWorkingValue.setTextColor(mCslWorkingOn);
		} else {
			mInfoBoxBg.setImageResource(R.drawable.tv_info_box_unsel);
			mInfoSettingValue.setTextColor(mCslSettingOff);
			mInfoWorkingValue.setTextColor(mCslWorkingOff);
		}
		
		updateInfomation(mSettingValue, mWorkingValue);
	}
	
	private void updateInfomation(String setting, String working) {
		if(setting != null) {
			mInfoSettingValue.setText(setting);
		}
		
		if(working != null) {
			mInfoWorkingValue.setText(working);
		}
	}
	
	public void updateWorkingInfo(String working) {
		updateInfomation(null, working);
	}
}
