package com.midea.iot.pad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midea.iot.R;

public class CusTvTimeBar extends LinearLayout {
	private ImageView mTimeHour0 = null; //ʱ���Сʱ��ʮλ
	private ImageView mTimeHour1 = null; //ʱ���Сʱ���λ
	private ImageView mTimeMinutes0 = null; //ʱ��ķ�����ʮ��λ
	private ImageView mTimeMinutes1 = null; //ʱ���Сʱ�����λ
	private ImageView mTimeColon = null;
	private ImageView mTimeInfoBox = null;
	private TextView mTotalTime = null;
	private int mSettingTime = 600;  //10 minutes
	private int mTime = 0;
	private boolean mEnable = false;
	ColorStateList mCslTextTimeOn;
	ColorStateList mCslTextTimeOff;
	
	int mSelDigitalImage[] = { 
			R.drawable.tv_digital_sel_0,
			R.drawable.tv_digital_sel_1,
			R.drawable.tv_digital_sel_2,
			R.drawable.tv_digital_sel_3,
			R.drawable.tv_digital_sel_4,
			R.drawable.tv_digital_sel_5,
			R.drawable.tv_digital_sel_6,
			R.drawable.tv_digital_sel_7,
			R.drawable.tv_digital_sel_8,
			R.drawable.tv_digital_sel_9,
			};
	
	int mUnSelDigitalImage[] = { 
			R.drawable.tv_digital_unsel_0,
			R.drawable.tv_digital_unsel_1,
			R.drawable.tv_digital_unsel_2,
			R.drawable.tv_digital_unsel_3,
			R.drawable.tv_digital_unsel_4,
			R.drawable.tv_digital_unsel_5,
			R.drawable.tv_digital_unsel_6,
			R.drawable.tv_digital_unsel_7,
			R.drawable.tv_digital_unsel_8,
			R.drawable.tv_digital_unsel_9,
			};

	public CusTvTimeBar(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
		
	}
	
	public CusTvTimeBar(Context context, AttributeSet attrs) {
//		super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);

	}

	@SuppressLint("NewApi")
	public CusTvTimeBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.cus_tv_time_bar, this, true);
		mTimeHour0 = (ImageView) findViewById(R.id.image_time_0);
		mTimeHour1 = (ImageView) findViewById(R.id.image_time_1);
		mTimeMinutes0 = (ImageView) findViewById(R.id.image_time_2);
		mTimeMinutes1 = (ImageView) findViewById(R.id.image_time_3);
		mTimeColon = (ImageView) findViewById(R.id.image_time_colon);
		mTimeInfoBox = (ImageView) findViewById(R.id.image_info_box);
		mTotalTime = (TextView) findViewById(R.id.text_time_total_time);
		
		/*Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Narrow.ttf");
		mTotalTime.setTypeface(tf);*/
		
		Resources resource = context.getResources();  
		mCslTextTimeOn = resource.getColorStateList(R.color.tv_text_time_on);
		mCslTextTimeOff = resource.getColorStateList(R.color.tv_text_time_off);
	}

	public void disableTotalTime() {
		mTimeInfoBox.setVisibility(View.GONE);
		mTotalTime.setVisibility(View.GONE);
	}
	
	public void setTimeBarStatus(boolean enable) {
		mEnable = enable;
		if(enable) {
			mTimeHour0.setImageResource(R.drawable.tv_digital_sel_0);
			mTimeHour1.setImageResource(R.drawable.tv_digital_sel_0);
			mTimeMinutes0.setImageResource(R.drawable.tv_digital_sel_0);
			mTimeMinutes1.setImageResource(R.drawable.tv_digital_sel_0);
			mTimeColon.setImageResource(R.drawable.tv_digital_sign_sel);
			mTimeInfoBox.setImageResource(R.drawable.tv_info_box_sel);
			mTotalTime.setTextColor(mCslTextTimeOn);
			updateTime(mTime);
		} else {
			mTimeHour0.setImageResource(R.drawable.tv_digital_unsel_0);
			mTimeHour1.setImageResource(R.drawable.tv_digital_unsel_0);
			mTimeMinutes0.setImageResource(R.drawable.tv_digital_unsel_0);
			mTimeMinutes1.setImageResource(R.drawable.tv_digital_unsel_0);
			mTimeColon.setImageResource(R.drawable.tv_digital_sign_unsel);
			mTimeInfoBox.setImageResource(R.drawable.tv_info_box_unsel);
			mTotalTime.setTextColor(mCslTextTimeOff);
		}
	}
	
	public void setTotalTime(int totalTime) {
		mSettingTime = totalTime;
		String strTime = totalTime / 60 + ":" + totalTime % 60;
		mTotalTime.setText(strTime);
		
		if(totalTime == 0) {
			mTimeInfoBox.setImageResource(R.drawable.tv_info_box_unsel);
		}
	}
	
	public void updateTime(int time) {
		if(!mEnable) return;
		
		if(mTime != time) {
			mTime = time;
			int hour0 = time / 60 / 10;
			int hour1 = time / 60 % 10;
			
			int minute0 = time % 60 / 10;
			int minute1 = time % 60 % 10;
			
			mTimeHour0.setImageResource(mSelDigitalImage[hour0]);
			mTimeHour1.setImageResource(mSelDigitalImage[hour1]);
			mTimeMinutes0.setImageResource(mSelDigitalImage[minute0]);
			mTimeMinutes1.setImageResource(mSelDigitalImage[minute1]);
		}
	}

}
