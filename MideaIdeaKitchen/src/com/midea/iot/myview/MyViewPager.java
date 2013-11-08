package com.midea.iot.myview;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midea.iot.R;
import com.midea.iot.activities.AppConstant;
import com.midea.iot.activities.MyApplication;

public class MyViewPager {
	class DisableOnTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	class EnableOnTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	class MyViewPagerOnPageChangeListener implements OnPageChangeListener{
    	@Override
    	public void onPageScrolled(int position,
    		float positionOffset, int positionOffsetPixels) {
    	}
    	@Override
    	public void onPageScrollStateChanged(int state) {

    	}
    	//ҳ��ѡ��
    	@Override
    	public void onPageSelected(int position) {
    		switch(position){
    		case 0:
    			mIndicator.setImageResource(R.drawable.page1);
    			break;
    		case 1:
    			mIndicator.setImageResource(R.drawable.page2);
    			break;
    		default:
    			mIndicator.setImageResource(R.drawable.page1);
    			break;
    		}

    	}
    }
	public MyApplication myApplication;
	public Context ctx;
	public LinearLayout parent;
	public int id;
	public int mode;
	
	public ViewPager mPager;//ҳ������
	
	public ImageView mIndicator = null;
	
	public List<View> listViews; // Tabҳ���б�

	
	public TextView modeName = null;
	
	public MyViewPager(Context ctx, LinearLayout parent, MyApplication myApplication, int id, int mode) {
		super();
		this.ctx = ctx;
		this.parent = parent;
		this.myApplication = myApplication;
		this.id = id;
		this.mode = mode;
		//initViewPager();
	}


    public void setModeClickable(boolean clickable) {
		// TODO Auto-generated method stub
/*		mode1.setClickable(clickable);
		mode2.setClickable(clickable);
		mode3.setClickable(clickable);
		mode4.setClickable(clickable);
		mode5.setClickable(clickable);
		mode6.setClickable(clickable);
		mode7.setClickable(clickable);
		mode8.setClickable(clickable);
		
		if(clickable){
	    	mPager.setOnTouchListener(new EnableOnTouchListener());
		}else{
	    	mPager.setOnTouchListener(new DisableOnTouchListener());
		}*/
	}
    
    
    public void setModeIcon(ImageView view, int mode, boolean isSelect){
		if(isSelect){
			view.setImageResource(AppConstant.mode_sel[mode]);
		}else{
			view.setImageResource(AppConstant.mode_nor[mode]);
		}
	}
    
    public void setSelModeView(TextView modeName, ImageView view, int mode){
		modeName.setText(AppConstant.oven_mode_name[mode]);
		setModeIcon(view, mode, true);
	}
}