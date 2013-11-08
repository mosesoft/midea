package com.midea.iot.pad;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.midea.iot.R;

public class PadMainHobWorkingView extends FrameLayout {
	private ImageView mHobWorkingOn_1;
	private ImageView mHobWorkingOn_2;
	private ImageView mHobWorkingOn_3;
	private ImageView mHobWorkingOn_4;
	
	public PadMainHobWorkingView(Context context) {
//		super(context);
		this(context,null);
		// TODO Auto-generated constructor stub
	}

	public PadMainHobWorkingView(Context context, AttributeSet attrs) {
//		super(context, attrs);
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public PadMainHobWorkingView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.pad_main_hob_work_view, this, true);
		
		mHobWorkingOn_1 = (ImageView) findViewById(R.id.image_hob_working_on_1);
		mHobWorkingOn_2 = (ImageView) findViewById(R.id.image_hob_working_on_2);
		mHobWorkingOn_3 = (ImageView) findViewById(R.id.image_hob_working_on_3);
		mHobWorkingOn_4 = (ImageView) findViewById(R.id.image_hob_working_on_4);
	}
	
	public void updateView(boolean b1, boolean b2, boolean b3, boolean b4) {
		if(b1) {
			mHobWorkingOn_1.setVisibility(View.VISIBLE);
		} else {
			mHobWorkingOn_1.setVisibility(View.INVISIBLE);
		}
		
		if(b2) {
			mHobWorkingOn_2.setVisibility(View.VISIBLE);
		} else {
			mHobWorkingOn_2.setVisibility(View.INVISIBLE);
		}
		
		if(b3) {
			mHobWorkingOn_3.setVisibility(View.VISIBLE);
		} else {
			mHobWorkingOn_3.setVisibility(View.INVISIBLE);
		}
		
		if(b4) {
			mHobWorkingOn_4.setVisibility(View.VISIBLE);
		} else {
			mHobWorkingOn_4.setVisibility(View.INVISIBLE);
		}
	}

}
