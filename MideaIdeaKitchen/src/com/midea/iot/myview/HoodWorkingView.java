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
import com.midea.iot.devices.Hood;

public class HoodWorkingView {
	class BtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("*****BtnOnClickListener");
			// TODO Auto-generated method stub
			setWorkStatus(v.getId());
		}
		
	}
	MyApplication myApplication;
	Context ctx;
	FrameLayout parent;
	int workingState;
	Handler mHandler;
	int speedNum = 0;
	int time = 0;
	
	private TextView stateTxt = null;
	private ImageView processcircle = null;
	private ImageView movingcircle = null;
	private LinearLayout workingview = null;
	private ImageView min1 = null;
	private ImageView min2 = null;
	private ImageView sec1 = null;
	
	private ImageView sec2 = null;
	
	private TextView speed = null;
	private ImageView hood_f1 = null;
	private ImageView hood_f2 = null;
	
	private ImageView hood_f3 = null;
	
	private ImageView title_font = null;
	private ImageButton stop = null;
	
	Hood hood = null;
	
	Bitmap bitmap = null; 
	
    private final Runnable timerTask = new Runnable() {  
    	  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
            if (workingState != AppConstant.HOOD_STOP) {  
            	System.out.println("*****timerTask  212121");
                mHandler.postDelayed(timerTask, AppConstant.UPDATE_TIME);  
                count++;  
            	if(count >= AppConstant.MAX_UPDATE_NUM){
            		count = 0; 
            	}
            	bitmap = createSector(count);
            	movingcircle.setImageBitmap(bitmap);
            	
            }  

        }  
    };
    
	public int count = 0;
	Bitmap mBitmap = null;
	public HoodWorkingView(Context ctx, FrameLayout parent, int state, Handler mHandler) {
		super();
		this.ctx = ctx;
		this.parent = parent;
		this.myApplication = myApplication;
		hood = MainActivity.getInstance().getHoodDevice();
		this.speedNum = hood.getMotorSpeed();
		this.time = hood.getRunningTime();
		this.workingState = hood.getWorkStatus();
		if(workingState > AppConstant.HOOD_F3){
			workingState = AppConstant.HOOD_F3;
		}
		this.mHandler = mHandler;
		initView();
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
    
 
	public void initCookingView(int state){

		setAllBtnNormal();
		mHandler.removeCallbacks(timerTask);
		mHandler.post(timerTask);
		switch (state) {
		case AppConstant.HOOD_F1:
			hood_f1.setImageResource(R.drawable.hood_f1_sel);
			stateTxt.setText(AppConstant.hoodstateArray[1]);
			break;
		case AppConstant.HOOD_F2:
			hood_f2.setImageResource(R.drawable.hood_f2_sel);
			stateTxt.setText(AppConstant.hoodstateArray[2]);
			break;
		case AppConstant.HOOD_F3:
			hood_f3.setImageResource(R.drawable.hood_f3_sel);
			stateTxt.setText(AppConstant.hoodstateArray[3]);
			break;
		case AppConstant.HOOD_STOP:
			stateTxt.setText(AppConstant.hoodstateArray[0]);
			mHandler.removeCallbacks(timerTask);
			count = 0;
			bitmap = createSector(count);
			movingcircle.setImageBitmap(bitmap);
			break;
		default:
			break;
		}
	}

    private void initView() {

    	stateTxt = (TextView)parent.findViewById(R.id.state);
    	stateTxt.setText(AppConstant.hoodstateArray[workingState]);
    	processcircle = (ImageView)parent.findViewById(R.id.processcircle);
    	movingcircle = (ImageView)parent.findViewById(R.id.movingcircle);
    	title_font = (ImageView)parent.findViewById(R.id.title_font);
    	min1 = (ImageView)parent.findViewById(R.id.min1);
    	min2 = (ImageView)parent.findViewById(R.id.min2);
    	sec1 = (ImageView)parent.findViewById(R.id.sec1);
    	sec2 = (ImageView)parent.findViewById(R.id.sec2);
    	
    	speed = (TextView)parent.findViewById(R.id.speed);
    	
    	hood_f1 = (ImageView)parent.findViewById(R.id.hood_f1);
    	hood_f2 = (ImageView)parent.findViewById(R.id.hood_f2);
    	hood_f3 = (ImageView)parent.findViewById(R.id.hood_f3);
    	
    	stop = (ImageButton)parent.findViewById(R.id.stop);

    	hood_f1.setOnClickListener(new BtnOnClickListener());
    	hood_f2.setOnClickListener(new BtnOnClickListener());
    	hood_f3.setOnClickListener(new BtnOnClickListener());
    	stop.setOnClickListener(new BtnOnClickListener());
    	initCookingView(workingState);
    	if(parent.getVisibility() == View.VISIBLE){
    		if(workingState != AppConstant.HOOD_STOP){
    	       	mHandler.post(timerTask);
    		}
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
	}
	
	public void removeTimerTask(){
		mHandler.removeCallbacks(timerTask);
	}
	private void setAllBtnNormal(){
		hood_f1.setImageResource(R.drawable.hood_f1_nor);
		hood_f2.setImageResource(R.drawable.hood_f2_nor);
		hood_f3.setImageResource(R.drawable.hood_f3_nor);
	}
	public void setBtnClickable(boolean clickable) {
		// TODO Auto-generated method stub
		hood_f1.setClickable(clickable);
		hood_f2.setClickable(clickable);
		hood_f3.setClickable(clickable);
		stop.setClickable(clickable);
	}
	public void setCookingView(int state){
		System.out.println("******setCookingView state = " + state + ", workingState = " + workingState);
		if(state != workingState){
			setAllBtnNormal();
			mHandler.removeCallbacks(timerTask);
			mHandler.post(timerTask);
			switch (state) {
			case AppConstant.HOOD_F1:
				hood_f1.setImageResource(R.drawable.hood_f1_sel);
				stateTxt.setText(AppConstant.hoodstateArray[1]);
				break;
			case AppConstant.HOOD_F2:
				hood_f2.setImageResource(R.drawable.hood_f2_sel);
				stateTxt.setText(AppConstant.hoodstateArray[2]);
				break;
			case AppConstant.HOOD_F3:
				hood_f3.setImageResource(R.drawable.hood_f3_sel);
				stateTxt.setText(AppConstant.hoodstateArray[3]);
				break;
			case AppConstant.HOOD_STOP:
				stateTxt.setText(AppConstant.hoodstateArray[0]);
				mHandler.removeCallbacks(timerTask);
				count = 0;
				bitmap = createSector(count);
				movingcircle.setImageBitmap(bitmap);
				break;
			default:
				break;
			}
		}
	}
	
	public void setSpeed(int speedNum){
		speed.setText(Integer.toString(speedNum));
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
    	sec1.setImageResource(AppConstant.digital_time_green[num2]);
    	sec2.setImageResource(AppConstant.digital_time_green[num3]);
    }
	
	public void setWorkingState(int state) {
		// TODO Auto-generated method stub
		setCookingView(state);
    	workingState = state;
	}
	
	
	public void setWorkStatus(int viewId) {

		switch (viewId) {
		case R.id.hood_f1:
			if(workingState != AppConstant.HOOD_F1){
				setAllBtnNormal();
				mHandler.removeCallbacks(timerTask);
			    mHandler.post(timerTask);
				hood_f1.setImageResource(R.drawable.hood_f1_sel);
				stateTxt.setText(AppConstant.hoodstateArray[1]);
				workingState = AppConstant.HOOD_F1;
				hood.startWork(AppConstant.HOOD_F1);
			}
			break;
		case R.id.hood_f2:
			if(workingState != AppConstant.HOOD_F2){
				setAllBtnNormal();
				mHandler.removeCallbacks(timerTask);
			    mHandler.post(timerTask);
				hood_f2.setImageResource(R.drawable.hood_f2_sel);
				stateTxt.setText(AppConstant.hoodstateArray[2]);
				workingState = AppConstant.HOOD_F2;
				hood.startWork(AppConstant.HOOD_F2);
			}

			break;
		case R.id.hood_f3:
			if(workingState != AppConstant.HOOD_F3){
				setAllBtnNormal();
				mHandler.removeCallbacks(timerTask);
			    mHandler.post(timerTask);
				hood_f3.setImageResource(R.drawable.hood_f3_sel);
				stateTxt.setText(AppConstant.hoodstateArray[3]);
				workingState = AppConstant.HOOD_F3;
				hood.startWork(AppConstant.HOOD_F3);
			}

			break;
		case R.id.stop:
			if(workingState != AppConstant.HOOD_STOP){
				setAllBtnNormal();
				mHandler.removeCallbacks(timerTask);
			    mHandler.post(timerTask);
				stateTxt.setText(AppConstant.hoodstateArray[0]);
				workingState = AppConstant.HOOD_STOP;
				hood.startWork(AppConstant.HOOD_STOP);
				//imageView.setImageResource(R.drawable.digital1);
				mHandler.removeCallbacks(timerTask);
				count = 0;
	        	bitmap = createSector(count);
	        	movingcircle.setImageBitmap(bitmap);
			}

			break;
		default:
			//imageView.setImageResource(R.drawable.digital1);
			break;
		}

	}
}