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

public class CusTvProgressBar extends LinearLayout {

	private ImageView mProgressBarBg = null;
	private ImageView mProgressBarLeft = null;
	private ImageView mProgressBarMid = null;
	private ImageView mProgressBarRight = null;
	private TextView mPrecent = null;
	private boolean mEnable = false;
	private int mTotalLength = 0;
	private int mLeftLength = 0;
	private int mRightLength = 0;
	
	ColorStateList mCslTextOn;
	ColorStateList mCslTextOff;

	public CusTvProgressBar(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public CusTvProgressBar(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public CusTvProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.cus_tv_progress_bar,
				this, true);

		mProgressBarBg = (ImageView) findViewById(R.id.image_bg);
		mProgressBarLeft = (ImageView) findViewById(R.id.image_left);
		mProgressBarMid = (ImageView) findViewById(R.id.image_mid);
		mProgressBarRight = (ImageView) findViewById(R.id.image_right);

		mPrecent = (TextView) findViewById(R.id.text_present);
		
		Resources resource = context.getResources();  
		mCslTextOn = resource.getColorStateList(R.color.tv_text_on);
		mCslTextOff = resource.getColorStateList(R.color.tv_text_off);

		/*
		 * Typeface tf = Typeface.createFromAsset(context.getAssets(),
		 * "fonts/Helvetica-Narrow.ttf"); mPrecent.setTypeface(tf);
		 */
	}

	public void setProgressBarStatus(boolean enable) {
		mEnable = enable;
		if (mEnable) {
			mProgressBarBg.setImageResource(R.drawable.tv_progress_bar_bg_on);
			mProgressBarLeft.setVisibility(View.VISIBLE);
			mProgressBarMid.setVisibility(View.VISIBLE);
			mProgressBarRight.setVisibility(View.VISIBLE);
			mPrecent.setTextColor(mCslTextOn);
		} else {
			mProgressBarBg.setImageResource(R.drawable.tv_progress_bar_bg_off);
			mProgressBarLeft.setVisibility(View.GONE);
			mProgressBarMid.setVisibility(View.GONE);
			mProgressBarRight.setVisibility(View.GONE);
			mPrecent.setTextColor(mCslTextOff);
		}
	}

	@SuppressLint("NewApi")
	public void updateProgress(int percent) {
		if (mEnable) {
			if (mTotalLength == 0 || mLeftLength == 0 || mRightLength == 0) {
				mTotalLength = mProgressBarBg.getWidth();
				mLeftLength = mProgressBarLeft.getWidth();
				mRightLength = mProgressBarRight.getWidth();

				int len = mProgressBarLeft.getLeft();
				mTotalLength -= len * 2;
			}
			
			
			if(percent > 100) percent = 100;
			if(percent < 0) percent = 0;

			int midLen = mTotalLength * percent / 100 - mLeftLength
					- mRightLength;
			
			if(midLen < 1) midLen = 1;
			if (midLen > 0) {
				FrameLayout.LayoutParams l = (FrameLayout.LayoutParams) mProgressBarMid
						.getLayoutParams();
				l.width = midLen;
				mProgressBarMid.setLayoutParams(l);
				int lenRight = mProgressBarMid.getRight();
				mProgressBarRight.setLeft(lenRight);
				FrameLayout.LayoutParams l2 = (FrameLayout.LayoutParams) mProgressBarRight
						.getLayoutParams();
				l2.setMargins(lenRight, 2, 0, 0);
				mProgressBarRight.setLayoutParams(l2);
				mProgressBarRight.requestLayout();
				// mProgressBarRight.setX(lenRight);
			} else {
				int lenRight = mProgressBarLeft.getRight();
				mProgressBarRight.setRight(lenRight);
			}

			mPrecent.setText(percent + "%");
		}
	}
}
