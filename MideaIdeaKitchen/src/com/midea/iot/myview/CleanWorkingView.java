package com.midea.iot.myview;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midea.iot.R;
import com.midea.iot.activities.AppConstant;
import com.midea.iot.activities.AppStatic;
import com.midea.iot.activities.MainActivity;
import com.midea.iot.activities.MyApplication;
import com.midea.iot.devices.Robotclean;

public class CleanWorkingView {
	class BtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("*****BtnOnClickListener");
			// TODO Auto-generated method stub
			setWorkStatus(v.getId());
			count = 0;
		}
		
	}
	MyApplication myApplication;
	Context ctx;
	FrameLayout parent;
	int time = 0;
	int workingState;
	boolean isChargeFull = false;
	
	Handler mHandler;
	
	private TextView stateTxt = null;
	private ImageView processcircle = null;
	private ImageView movingcircle = null;
	private ImageView min1 = null;
	private ImageView min2 = null;
	private ImageView sign = null;
	private ImageView sec1 = null;
	private ImageView sec2 = null;
	private ImageView cleanIcon = null;
	private LinearLayout chargingview = null;
	private TextView chargingstate = null;
	private ImageView chargingbg = null;
	private ImageView battery1fg = null;
	
	private TextView percent1 = null;
	
	private FrameLayout autoview = null;
	private ImageView battery2bg = null;
	private ImageView battery2fg = null;
	
	private TextView percent2 = null;
	private LinearLayout func_btn = null;
	private ImageView auto_btn = null;
	
	private ImageView battery_btn = null;
	
	private ImageButton power = null;
	private MyScrollView myScrollView = null;
	Robotclean robotclean = null;
	

	public int flag = 0; 
	private final Runnable timerTask4 = new Runnable() {  
  	  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
        	if(flag == 0){
            	myScrollView.smoothScrollBy(440, 0, 10000);
            	mHandler.postDelayed(timerTask4, 6000);
            	cleanIcon.setImageResource(R.drawable.clean_icon2);
            	flag = 1;
        	}else if(flag == 1){
            	myScrollView.smoothScrollBy(0, 220, 5000);
            	mHandler.postDelayed(timerTask4, 3000);
            	cleanIcon.setImageResource(R.drawable.clean_icon1);
            	flag = 2;
        	}else if(flag == 2){
            	myScrollView.smoothScrollBy(-440, 0, 10000);
            	mHandler.postDelayed(timerTask4, 6000);
            	cleanIcon.setImageResource(R.drawable.clean_icon4);
            	flag = 3;
        	}else if(flag == 3){
            	myScrollView.smoothScrollBy(0, -220, 5000);
            	mHandler.postDelayed(timerTask4, 3000);
            	cleanIcon.setImageResource(R.drawable.clean_icon3);
            	flag = 4;
        	}else if(flag == 4){
            	myScrollView.smoothScrollBy(440, 0, 10000);
            	mHandler.postDelayed(timerTask4, 6000);
            	cleanIcon.setImageResource(R.drawable.clean_icon2);
            	flag = 5;
        	}else if(flag == 5){
            	myScrollView.smoothScrollBy(0, -220, 5000);
            	mHandler.postDelayed(timerTask4, 3000);
            	cleanIcon.setImageResource(R.drawable.clean_icon3);
            	flag = 6;
        	}else if(flag == 6){
            	myScrollView.smoothScrollBy(-440, 0, 10000);
            	mHandler.postDelayed(timerTask4, 6000);
            	cleanIcon.setImageResource(R.drawable.clean_icon4);
            	flag = 7;
        	}else if(flag == 7){
        		myScrollView.smoothScrollBy(0, 220, 5000);
        		mHandler.postDelayed(timerTask4, 3000);
        		cleanIcon.setImageResource(R.drawable.clean_icon1);
            	flag = 0;
        	}
        	
        	

        }  
    };
    int percent; 
    
    int count2;
    
    private final Runnable timerTask2 = new Runnable() {  
  	  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
            if (workingState == AppConstant.CLEAN_CHARGING 
            		|| workingState == AppConstant.CLEAN_AUTO_CHARGING) {  
            	System.out.println("*****timerTask  fad123");
                mHandler.postDelayed(timerTask2, AppConstant.CLEAN_CHCAGING_ANIMATE_TIME);  
                count2 += 2;  
                if(count2 >= 102){
                	count2 = percent;
                }else if(count2 >= 100){
                	count2 = 100;
            	}
            	System.out.println("******CleanCooking count = " + count2);
            	bitmap2 = createRect(count2);
            	battery1fg.setImageBitmap(bitmap2);
            }else{
            	mHandler.removeCallbacks(timerTask2);  
            }
            
        }  
    };
	Bitmap bitmap2 = null;

    Bitmap mBitmap2 = null;
    
	Bitmap bitmap = null;
	private final Runnable timerTask = new Runnable() {  
    	  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
            if (workingState == AppConstant.CLEAN_WORKING) {  
            	System.out.println("*****timerTask  fddf");
                mHandler.postDelayed(timerTask, AppConstant.UPDATE_TIME);  
                count++;  
            	if(count >= AppConstant.MAX_UPDATE_NUM){
            		count = 0; 
            	}
            	System.out.println("******CleanCooking count = " + count);
            	bitmap = createSector(count);
            	movingcircle.setImageBitmap(bitmap);
            }else{
            	mHandler.removeCallbacks(timerTask);  
            }
            
        }  
    };
	private final Runnable timerTask3 = new Runnable() {  
  	  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
			setAllBtnNormal();
			auto_btn.setImageResource(R.drawable.auto_btn_sel);
			workingState = AppConstant.CLEAN_WORKING;
			initViewState(workingState);
			System.out.println("****autoWork");
			robotclean.autoWork();
        }  
    };
	public int count = 0;
	Bitmap mBitmap = null;
    public CleanWorkingView(Context ctx, FrameLayout parent, int state, Handler mHandler) {
		super();
		this.ctx = ctx;
		this.parent = parent;
		this.myApplication = myApplication;
		robotclean = MainActivity.getInstance().getCleanDevice();
		this.workingState = robotclean.getWorkStatus();
		this.isChargeFull = robotclean.isFullCharged();
		this.time = robotclean.workTime();
		this.mHandler = mHandler;
		this.percent = robotclean.batteryValue();
		this.count2 = this.percent;
		initView();
	} 
	private Bitmap createRect(int progress) {
    	if(mBitmap2 == null){
        	InputStream is = ctx.getResources().openRawResource(R.drawable.battery_fg);    
            mBitmap2 = BitmapFactory.decodeStream(is);   
    	}

        final Bitmap b = Bitmap.createBitmap(
        		mBitmap2.getWidth(), mBitmap2.getHeight(), Bitmap.Config.ARGB_8888);
    	Canvas canvas = new Canvas(b);
    	canvas.drawColor(Color.TRANSPARENT);
    	Paint p = new Paint(); // �ʴ�        
    	p.setAntiAlias(true); // �����          
 
    	p.setStyle(Paint.Style.FILL);  

    	int width = progress*mBitmap2.getWidth()/100;
    	//Path path = AppStatic.getSectorClip(canvas, mBitmap.getWidth()/2, mBitmap.getHeight()/2, mBitmap.getWidth()/2, 270, progress*AppConstant.STEP_PROGRESS + 270);
    	RectF r = new RectF(0, 0, width, mBitmap2.getHeight());
    	canvas.drawRect(r, p);

    	p.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
    	canvas.drawBitmap(mBitmap2, 0, 0, p);
    	
    	return b;
    }
    private Bitmap createRect2(int progress) {
    	
        InputStream is = ctx.getResources().openRawResource(R.drawable.battery_fg2);    
        Bitmap mBitmap = BitmapFactory.decodeStream(is);   

        final Bitmap b = Bitmap.createBitmap(
        		mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    	Canvas canvas = new Canvas(b);
    	canvas.drawColor(Color.TRANSPARENT);
    	Paint p = new Paint(); // �ʴ�        
    	p.setAntiAlias(true); // �����          
 
    	p.setStyle(Paint.Style.FILL);  

    	int width = progress*mBitmap.getWidth()/100;
    	//Path path = AppStatic.getSectorClip(canvas, mBitmap.getWidth()/2, mBitmap.getHeight()/2, mBitmap.getWidth()/2, 270, progress*AppConstant.STEP_PROGRESS + 270);
    	RectF r = new RectF(0, 0, width, mBitmap.getHeight());
    	canvas.drawRect(r, p);

    	p.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
    	canvas.drawBitmap(mBitmap, 0, 0, p);
    	
    	return b;
    }
    private Bitmap createSector(int progress) {
    	if(mBitmap == null){
        	InputStream is = ctx.getResources().openRawResource(R.drawable.cooking_moving_circle);    
            mBitmap = BitmapFactory.decodeStream(is);   
    	}

        final Bitmap b = Bitmap.createBitmap(
        		mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    	Canvas canvas = new Canvas(b);
    	canvas.drawColor(Color.TRANSPARENT);
    	Paint p = new Paint(); // �ʴ�        
    	p.setAntiAlias(true); // �����          
 
    	p.setStyle(Paint.Style.FILL);  

    	Path path = AppStatic.getSectorClip(canvas, mBitmap.getWidth()/2, mBitmap.getHeight()/2, mBitmap.getWidth()/2, 270, progress*AppConstant.STEP_PROGRESS + 270);

    	canvas.drawPath(path, p);

    	p.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
    	canvas.drawBitmap(mBitmap, 0, 0, p);
    	
    	return b;
    }
    
    public int getWorkingState() {
		// TODO Auto-generated method stub
    	return workingState;
	}
	private void initView() {
		chargingview = (LinearLayout)parent.findViewById(R.id.chargingview);
		chargingstate = (TextView)parent.findViewById(R.id.chargingstate);
		chargingbg = (ImageView)parent.findViewById(R.id.chargingbg);
		battery1fg = (ImageView)parent.findViewById(R.id.battery1fg);
		percent1 = (TextView)parent.findViewById(R.id.percent1);
		
		
		autoview = (FrameLayout)parent.findViewById(R.id.autoview);
		stateTxt = (TextView)parent.findViewById(R.id.state);
    	processcircle = (ImageView)parent.findViewById(R.id.processcircle);
    	movingcircle = (ImageView)parent.findViewById(R.id.movingcircle);
    	min1 = (ImageView)parent.findViewById(R.id.min1);
    	min2 = (ImageView)parent.findViewById(R.id.min2);
    	sign = (ImageView)parent.findViewById(R.id.sign);
    	sec1 = (ImageView)parent.findViewById(R.id.sec1);
    	sec2 = (ImageView)parent.findViewById(R.id.sec2);
    	
    	percent2 = (TextView)parent.findViewById(R.id.percent2);
    	cleanIcon = (ImageView)parent.findViewById(R.id.cleanicon);
    	battery2bg = (ImageView)parent.findViewById(R.id.battery2bg);
    	battery2fg = (ImageView)parent.findViewById(R.id.battery2fg);
    	
    	func_btn = (LinearLayout)parent.findViewById(R.id.func_btn);
    	auto_btn = (ImageView)parent.findViewById(R.id.auto_btn);
    	battery_btn = (ImageView)parent.findViewById(R.id.battery_btn);
    	
    	power = (ImageButton)parent.findViewById(R.id.power);

    	myScrollView = (MyScrollView)parent.findViewById(R.id.animicon);
    	auto_btn.setOnClickListener(new BtnOnClickListener());
    	battery_btn.setOnClickListener(new BtnOnClickListener());
    	power.setOnClickListener(new BtnOnClickListener());
    	initViewState(workingState);
    	setPercent(percent);

    }
    public void initViewState(int state){
		switch(state){
		case AppConstant.CLEAN_STOP:
			chargingview.setVisibility(View.INVISIBLE);
			autoview.setVisibility(View.VISIBLE);
			stopAnim();
			
	    	processcircle.setImageResource(R.drawable.stop_process_circle);
	    	movingcircle.setVisibility(View.INVISIBLE);
	    	auto_btn.setClickable(false);
	    	battery_btn.setClickable(false);
			func_btn.setBackgroundResource(R.drawable.func_btn_disable);
			auto_btn.setImageResource(R.drawable.auto_btn_nor);
			battery_btn.setImageResource(R.drawable.auto_btn_nor);
	    	min1.setImageResource(R.drawable.disable_0);
	    	min2.setImageResource(R.drawable.disable_0);
	    	sign.setImageResource(R.drawable.disable_sign);
	    	sec1.setImageResource(R.drawable.disable_0);
	    	sec2.setImageResource(R.drawable.disable_0);
			percent2.setText(Integer.toString(percent) + "%");
			percent2.setTextColor(0xffafafaf);
        	bitmap2 = createRect2(percent);
        	battery2fg.setImageBitmap(bitmap2);
        	battery2bg.setImageResource(R.drawable.battery_bg2);
	    	mHandler.removeCallbacks(timerTask);
	    	stateTxt.setVisibility(View.INVISIBLE);
	    	mHandler.removeCallbacks(timerTask2);
			break;
		case AppConstant.CLEAN_WORKING:
			startAnim();

			chargingview.setVisibility(View.INVISIBLE);
			autoview.setVisibility(View.VISIBLE);
			
	    	processcircle.setImageResource(R.drawable.cooking_process_circle);
	    	movingcircle.setVisibility(View.VISIBLE);
	    	
	    	auto_btn.setClickable(true);
	    	battery_btn.setClickable(true);
			func_btn.setBackgroundResource(R.drawable.func_btn_nor);
			System.out.println("*****CLEAN_WORKING percent = " + percent);
			percent2.setText(Integer.toString(percent) + "%");
			percent2.setTextColor(0xffbfbfbf);
			//setAllBtnNormal();
			auto_btn.setImageResource(R.drawable.auto_btn_sel);
			battery_btn.setImageResource(R.drawable.battery_btn_nor);
			stateTxt.setVisibility(View.VISIBLE);
			setTimeView(time);
	    	if(parent.getVisibility() == View.VISIBLE){
	    		mHandler.removeCallbacks(timerTask);
	        	mHandler.post(timerTask);
	    	}
        	bitmap2 = createRect(percent);
        	battery2fg.setImageBitmap(bitmap2);
        	battery2bg.setImageResource(R.drawable.battery_bg);
	    	mHandler.removeCallbacks(timerTask2);
			break;
		case AppConstant.CLEAN_CHARGING:
			stopAnim();
			chargingview.setVisibility(View.VISIBLE);
			autoview.setVisibility(View.INVISIBLE);

	    	auto_btn.setClickable(true);
	    	battery_btn.setClickable(true);
			func_btn.setBackgroundResource(R.drawable.func_btn_nor);
			//setAllBtnNormal();
			auto_btn.setImageResource(R.drawable.auto_btn_nor);
			battery_btn.setImageResource(R.drawable.battery_btn_sel);
			mHandler.removeCallbacks(timerTask);
			if(robotclean.isFullCharged()){
				System.out.println("****isFullCharged 4");
				chargingstate.setVisibility(View.INVISIBLE);
				chargingbg.setImageResource(R.drawable.battery_full_bg);
				percent1.setText("BATTERY FULL");
			}else{
				chargingstate.setVisibility(View.VISIBLE);
				chargingbg.setImageResource(R.drawable.battery_charging_bg);
				percent1.setText(Integer.toString(percent) + "%");
			}
	    	
	    	if(parent.getVisibility() == View.VISIBLE){
	    		mHandler.removeCallbacks(timerTask2);
	        	mHandler.post(timerTask2);
	    	}

			break;
		case AppConstant.CLEAN_AUTO_CHARGING:
			stopAnim();
			chargingview.setVisibility(View.VISIBLE);
			autoview.setVisibility(View.INVISIBLE);

	    	auto_btn.setClickable(true);
	    	battery_btn.setClickable(true);
			func_btn.setBackgroundResource(R.drawable.func_btn_nor);
			//setAllBtnNormal();
			auto_btn.setImageResource(R.drawable.auto_btn_sel);
			battery_btn.setImageResource(R.drawable.battery_btn_nor);
			mHandler.removeCallbacks(timerTask);
			if(robotclean.isFullCharged()){
				System.out.println("****isFullCharged 5");
				chargingstate.setVisibility(View.INVISIBLE);
				chargingbg.setImageResource(R.drawable.battery_full_bg);
				percent1.setText("BATTERY FULL");
			}else{
				chargingstate.setVisibility(View.VISIBLE);
				chargingbg.setImageResource(R.drawable.battery_charging_bg);
				percent1.setText(Integer.toString(percent) + "%");
			}
			if(parent.getVisibility() == View.VISIBLE){
	    		System.out.println("******mHandler . post(timerTask2)2");
	    		mHandler.removeCallbacks(timerTask2);
	        	mHandler.post(timerTask2);
	    	}
			break;
		default:
			break;
		}
		
	} 
    
    public void recyleBitmap(){
		if(mBitmap != null){
			mBitmap.recycle();
			mBitmap = null;
		}
		if(bitmap != null){
			bitmap.recycle();
			bitmap = null;
		}
		
		if(mBitmap2 != null){
			mBitmap2.recycle();
			mBitmap2 = null;
		}
		if(bitmap2 != null){
			bitmap2.recycle();
			bitmap2 = null;
		}
	} 
	public void removeTimerTask(){
		mHandler.removeCallbacks(timerTask);
		mHandler.removeCallbacks(timerTask2);
		mHandler.removeCallbacks(timerTask3);
		mHandler.removeCallbacks(timerTask4);
	}

    private void setAllBtnNormal(){
		auto_btn.setImageResource(R.drawable.auto_btn_nor);
		battery_btn.setImageResource(R.drawable.battery_btn_nor);
	}
    public void setBtnClickable(boolean clickable) {
		// TODO Auto-generated method stub
    	auto_btn.setClickable(clickable);
    	battery_btn.setClickable(clickable);
    	power.setClickable(clickable);
	}
	
	public void setCookingView(int state, boolean isChargeFull){
    	System.out.println("******setCookingView state = " + state + ", workingState = " + workingState);
    	
    	if(state != workingState){
    		switch(state){
    		case AppConstant.CLEAN_STOP:
    			stopAnim();
    			chargingview.setVisibility(View.INVISIBLE);
    			autoview.setVisibility(View.VISIBLE);
    			
    	    	processcircle.setImageResource(R.drawable.stop_process_circle);
    	    	movingcircle.setVisibility(View.INVISIBLE);
    	    	auto_btn.setClickable(false);
    	    	battery_btn.setClickable(false);
    			func_btn.setBackgroundResource(R.drawable.func_btn_disable);
    			auto_btn.setImageResource(R.drawable.auto_btn_nor);
    			battery_btn.setImageResource(R.drawable.auto_btn_nor);
    	    	min1.setImageResource(R.drawable.disable_0);
    	    	min2.setImageResource(R.drawable.disable_0);
    	    	sign.setImageResource(R.drawable.disable_sign);
    	    	sec1.setImageResource(R.drawable.disable_0);
    	    	sec2.setImageResource(R.drawable.disable_0);
    			percent2.setText(Integer.toString(percent) + "%");
    			percent2.setTextColor(0xffafafaf);
            	bitmap2 = createRect2(percent);
            	battery2fg.setImageBitmap(bitmap2);
            	battery2bg.setImageResource(R.drawable.battery_bg2);
    	    	mHandler.removeCallbacks(timerTask);
    	    	stateTxt.setVisibility(View.INVISIBLE);
    	    	mHandler.removeCallbacks(timerTask2);
    			break;
    		case AppConstant.CLEAN_WORKING:
    			startAnim();
    			chargingview.setVisibility(View.INVISIBLE);
    			autoview.setVisibility(View.VISIBLE);
    			
    	    	processcircle.setImageResource(R.drawable.cooking_process_circle);
    	    	movingcircle.setVisibility(View.VISIBLE);
    	    	
    	    	auto_btn.setClickable(true);
    	    	battery_btn.setClickable(true);
    			func_btn.setBackgroundResource(R.drawable.func_btn_nor);
    			System.out.println("*****CLEAN_WORKING percent = " + percent);
    			percent2.setText(Integer.toString(percent) + "%");
    			percent2.setTextColor(0xffbfbfbf);
    			//setAllBtnNormal();
    			auto_btn.setImageResource(R.drawable.auto_btn_sel);
    			battery_btn.setImageResource(R.drawable.battery_btn_nor);
    			stateTxt.setVisibility(View.VISIBLE);
    			setTimeView(time);
    	    	if(parent.getVisibility() == View.VISIBLE){
    	    		mHandler.removeCallbacks(timerTask);
    	        	mHandler.post(timerTask);
    	    	}
            	bitmap2 = createRect(percent);
            	battery2fg.setImageBitmap(bitmap2);
            	battery2bg.setImageResource(R.drawable.battery_bg);
    	    	mHandler.removeCallbacks(timerTask2);
    			break;
    		case AppConstant.CLEAN_CHARGING:
    			stopAnim();
    			chargingview.setVisibility(View.VISIBLE);
    			autoview.setVisibility(View.INVISIBLE);

    	    	auto_btn.setClickable(true);
    	    	battery_btn.setClickable(true);
    			func_btn.setBackgroundResource(R.drawable.func_btn_nor);
    			//setAllBtnNormal();
    			auto_btn.setImageResource(R.drawable.auto_btn_nor);
    			battery_btn.setImageResource(R.drawable.battery_btn_sel);
    			mHandler.removeCallbacks(timerTask);
    			if(robotclean.isFullCharged()){
    				System.out.println("****isFullCharged 2");
    				chargingstate.setVisibility(View.INVISIBLE);
    				chargingbg.setImageResource(R.drawable.battery_full_bg);
    				percent1.setText("BATTERY FULL");
    			}else{
    				chargingstate.setVisibility(View.VISIBLE);
    				chargingbg.setImageResource(R.drawable.battery_charging_bg);
    				percent1.setText(Integer.toString(percent) + "%");
    			}
    	    	
    	    	if(parent.getVisibility() == View.VISIBLE){
    	    		mHandler.removeCallbacks(timerTask2);
    	        	mHandler.post(timerTask2);
    	    	}

    			break;
    		case AppConstant.CLEAN_AUTO_CHARGING:
    			stopAnim();
    			chargingview.setVisibility(View.VISIBLE);
    			autoview.setVisibility(View.INVISIBLE);

    	    	auto_btn.setClickable(true);
    	    	battery_btn.setClickable(true);
    			func_btn.setBackgroundResource(R.drawable.func_btn_nor);
    			//setAllBtnNormal();
    			auto_btn.setImageResource(R.drawable.auto_btn_sel);
    			battery_btn.setImageResource(R.drawable.battery_btn_nor);
    			mHandler.removeCallbacks(timerTask);
    			if(robotclean.isFullCharged()){
    				System.out.println("****isFullCharged 3");
    				//mHandler.postDelayed(timerTask3, 5000);
    				chargingstate.setVisibility(View.INVISIBLE);
    				chargingbg.setImageResource(R.drawable.battery_full_bg);
    				percent1.setText("BATTERY FULL");
    			}else{
    				chargingstate.setVisibility(View.VISIBLE);
    				chargingbg.setImageResource(R.drawable.battery_charging_bg);
    				percent1.setText(Integer.toString(percent) + "%");
    			}
    			if(parent.getVisibility() == View.VISIBLE){
    	    		System.out.println("******mHandler . post(timerTask2)2");
    	    		mHandler.removeCallbacks(timerTask2);
    	        	mHandler.post(timerTask2);
    	    	}
    			break;
    		default:
    			break;
    		}
    	}else if((state == AppConstant.CLEAN_AUTO_CHARGING || state == AppConstant.CLEAN_CHARGING)
    			&& (this.isChargeFull != isChargeFull)){
    		switch(state){

    		case AppConstant.CLEAN_CHARGING:
    			stopAnim();
    			chargingview.setVisibility(View.VISIBLE);
    			autoview.setVisibility(View.INVISIBLE);

    	    	auto_btn.setClickable(true);
    	    	battery_btn.setClickable(true);
    			func_btn.setBackgroundResource(R.drawable.func_btn_nor);
    			//setAllBtnNormal();
    			auto_btn.setImageResource(R.drawable.auto_btn_nor);
    			battery_btn.setImageResource(R.drawable.battery_btn_sel);
    			mHandler.removeCallbacks(timerTask);
    			if(robotclean.isFullCharged()){
    				System.out.println("****isFullCharged 2");
    				chargingstate.setVisibility(View.INVISIBLE);
    				chargingbg.setImageResource(R.drawable.battery_full_bg);
    				percent1.setText("BATTERY FULL");
    			}else{
    				chargingstate.setVisibility(View.VISIBLE);
    				chargingbg.setImageResource(R.drawable.battery_charging_bg);
    				percent1.setText(Integer.toString(percent) + "%");
    			}
    	    	
    	    	if(parent.getVisibility() == View.VISIBLE){
    	    		mHandler.removeCallbacks(timerTask2);
    	        	mHandler.post(timerTask2);
    	    	}

    			break;
    		case AppConstant.CLEAN_AUTO_CHARGING:
    			stopAnim();
    			chargingview.setVisibility(View.VISIBLE);
    			autoview.setVisibility(View.INVISIBLE);

    	    	auto_btn.setClickable(true);
    	    	battery_btn.setClickable(true);
    			func_btn.setBackgroundResource(R.drawable.func_btn_nor);
    			//setAllBtnNormal();
    			auto_btn.setImageResource(R.drawable.auto_btn_sel);
    			battery_btn.setImageResource(R.drawable.battery_btn_nor);
    			mHandler.removeCallbacks(timerTask);
    			if(robotclean.isFullCharged()){
    				System.out.println("****isFullCharged 3");
    				mHandler.postDelayed(timerTask3, 5000);
    				chargingstate.setVisibility(View.INVISIBLE);
    				chargingbg.setImageResource(R.drawable.battery_full_bg);
    				percent1.setText("BATTERY FULL");
    			}else{
    				chargingstate.setVisibility(View.VISIBLE);
    				chargingbg.setImageResource(R.drawable.battery_charging_bg);
    				percent1.setText(Integer.toString(percent) + "%");
    			}
    			if(parent.getVisibility() == View.VISIBLE){
    	    		System.out.println("******mHandler . post(timerTask2)2");
    	    		mHandler.removeCallbacks(timerTask2);
    	        	mHandler.post(timerTask2);
    	    	}
    			break;
    		default:
    			break;
    		}
    	}
    }
	public void setPercent(int num){
		percent = num;
/*		if(robotclean.isFullCharged()){
			chargingbg.setImageResource(R.drawable.battery_full_bg);
			percent1.setText("BATTERY FULL");
		}else{
			percent1.setText(Integer.toString(num) + "%");
			percent2.setTextColor(0xffbfbfbf);
		}*/
		if(workingState == AppConstant.CLEAN_CHARGING){
			if(robotclean.isFullCharged()){
				System.out.println("****isFullCharged 6");
				chargingstate.setVisibility(View.INVISIBLE);
				chargingbg.setImageResource(R.drawable.battery_full_bg);
				percent1.setText("BATTERY FULL");
			}else{
				chargingstate.setVisibility(View.VISIBLE);
				chargingbg.setImageResource(R.drawable.battery_charging_bg);
				percent1.setText(Integer.toString(num) + "%");
				//percent2.setTextColor(0xffbfbfbf);
			}

		}else if(workingState == AppConstant.CLEAN_AUTO_CHARGING){
			if(robotclean.isFullCharged()){
				System.out.println("****isFullCharged 1");
				//mHandler.removeCallbacks(timerTask3);
				//mHandler.postDelayed(timerTask3, 5000);
				chargingstate.setVisibility(View.INVISIBLE);
				chargingbg.setImageResource(R.drawable.battery_full_bg);
				percent1.setText("BATTERY FULL");
			}else{
				chargingstate.setVisibility(View.VISIBLE);
				chargingbg.setImageResource(R.drawable.battery_charging_bg);
				percent1.setText(Integer.toString(num) + "%");
				//percent2.setTextColor(0xffbfbfbf);
			}

		}else if(workingState == AppConstant.CLEAN_WORKING){
			percent2.setText(Integer.toString(num) + "%");
		}else if(workingState == AppConstant.CLEAN_STOP){
			percent2.setText(Integer.toString(num) + "%");
		}
	}
	public void setTimeView(int time) {
    	int num0 = time/60/10;
    	int num1 = time/60%10;
    	int num2 = time%60/10;
    	int num3 = time%60%10;
    	if(num0 >= 10){
    		num0 = 9;
    	}
    	min1.setImageResource(AppConstant.digital_time_green[num0]);
    	min2.setImageResource(AppConstant.digital_time_green[num1]);
    	sign.setImageResource(R.drawable.time_green_sign);
    	sec1.setImageResource(AppConstant.digital_time_green[num2]);
    	sec2.setImageResource(AppConstant.digital_time_green[num3]);
    }
	public void setWorkingState(int state, boolean isChargeFull) {
		// TODO Auto-generated method stub
		setCookingView(state, isChargeFull);
    	workingState = state;
    	this.isChargeFull = isChargeFull;
    	
	}

	//int workstate = 0;
	public void setWorkStatus(int viewId) {
		
		switch (viewId) {
		case R.id.auto_btn:
			if(workingState != AppConstant.CLEAN_WORKING && workingState != AppConstant.CLEAN_AUTO_CHARGING){
				setAllBtnNormal();
				auto_btn.setImageResource(R.drawable.auto_btn_sel);
				workingState = AppConstant.CLEAN_WORKING;
				initViewState(workingState);
				robotclean.autoWork();

			}
			//state.setText(AppConstant.hoodstateArray[1]);
			break;
		case R.id.battery_btn:
			System.out.println("*****setWorkStatus workingState = " + workingState);
			if(workingState != AppConstant.CLEAN_CHARGING){
				setAllBtnNormal();
				battery_btn.setImageResource(R.drawable.battery_btn_sel);
				workingState = AppConstant.CLEAN_CHARGING;
				//state.setText(AppConstant.hoodstateArray[2]);
				initViewState(workingState);
				robotclean.charge();
			}
			break;
		case R.id.power:
			setAllBtnNormal();
			if(workingState == AppConstant.CLEAN_STOP){
				workingState = AppConstant.CLEAN_CHARGING;
				robotclean.power(true);
			}else{
				workingState = AppConstant.CLEAN_STOP;
				robotclean.power(false);
			}
			initViewState(workingState);

			break;
		default:
			//imageView.setImageResource(R.drawable.digital1);
			break;
		}

	}
	
	public void startAnim(){
    	myScrollView.setVisibility(View.VISIBLE);
    	myScrollView.scrollTo(0,0);
		myScrollView.smoothScrollTo(0, 220, 5000);
		mHandler.postDelayed(timerTask4, 3000);
		cleanIcon.setImageResource(R.drawable.clean_icon1);
		flag = 0;
    }
	
    public void stopAnim(){
    	myScrollView.setVisibility(View.INVISIBLE);
    	myScrollView.scrollTo(0,0);
    	cleanIcon.setImageResource(R.drawable.clean_icon1);
    	mHandler.removeCallbacks(timerTask4);
    }
}