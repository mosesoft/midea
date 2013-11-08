package com.midea.iot.pad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midea.iot.R;

public class CusPadMainTimeBar extends LinearLayout {
	private TextView mTimeHigh;
	private TextView mTimeLow;

	public CusPadMainTimeBar(Context context) {
		// super(context);
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public CusPadMainTimeBar(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public CusPadMainTimeBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.cus_pad_main_time_bar,
				this, true);
		mTimeHigh = (TextView) findViewById(R.id.text_time_high);
		mTimeLow = (TextView) findViewById(R.id.text_time_low);
	}

	private String int2String(int num) {
		if (num == 0)
			return "00";

		if (num < 10)
			return "0" + num;

		return "" + num;
	}

	public void updateTime(int totalTime) {
		int high = totalTime / 60;
		int low = totalTime % 60;

		String strHigh = int2String(high);
		String strLow = int2String(low);

		mTimeHigh.setText(strHigh);
		mTimeLow.setText(strLow);
	}

}
