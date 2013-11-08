package com.midea.iot.myview;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midea.iot.R;
import com.midea.iot.activities.AppConstant;
import com.midea.iot.activities.MainActivity;
import com.midea.iot.activities.MyApplication;
import com.midea.iot.devices.Hob;
import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.HobProtocol.HobPosition;
import com.midea.iot.protocol.HobStatus;
import com.midea.iot.wheelview.NumericWheelAdapter;
import com.midea.iot.wheelview.OnWheelChangedListener;
import com.midea.iot.wheelview.OnWheelScrollListener;
import com.midea.iot.wheelview.WheelView;

public class HobWorkingView {
	class BtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("*****BtnOnClickListener");
			// TODO Auto-generated method stub
			Hob hob = (Hob)MainActivity.getInstance().getDevice(BaseProtocol.TYPE_HOB);
			switch(v.getId()){
			case R.id.pausebtn:
				if(getSelectState(selectedHob) == AppConstant.COOKING_RUN){
					pausebtn.setImageResource(R.drawable.startbtn);
					setSelectState(selectedHob,AppConstant.COOKING_WAIT);
					//setSelectPower(selectedHob, 0);
					//setSelectSettingsPower(selectedHob, 0);
					//setSelectTime(selectedHob, 0);
					//setSelectSettingsTime(selectedHob, 0);

					//setTimeWheelStateByPower();
					updateSelectSettingsData(selectedHob);
					setSelectWheelData();
					//mHandler.removeCallbacks(timerTask1);
					removeAppointTimer(selectedHob);
					hob.startPause(getSettingsHobStatus(selectedHob), selectedHob);
				}else{
					pausebtn.setImageResource(R.drawable.pausebtn);
					setSelectState(selectedHob,AppConstant.COOKING_RUN);
					timeWheel.setOnTouchListener(new DisableOnTouchListener());
					timeWheel.setNumColor(AppConstant.NUM_GRAY);
					
					if(!mIsPad) {
						timeView.setBackgroundResource(R.drawable.hob_time_wheel_bg_dis);
			    	} else {
			    		timeViewBg.setBackgroundResource(R.drawable.hob_time_wheel_bg_dis);
			    	}
					
					//mHandler.postDelayed(timerTask1, AppConstant.HOB_START_TIME);
					setMode(selectedHob);
					setSelectTimeBySettingsTime(selectedHob);
					startAppointTimer(selectedHob);
					hob.startPause(getSettingsHobStatus(selectedHob), selectedHob);
				}
				


				//workingState = AppConstant.COOKING_RUN;
				break;
			case R.id.onbtn:
				mHandler.removeCallbacks(timerTask);
				if(workingState == AppConstant.COOKING_WAIT){
					setHobStartingView();
		    		mHandler.postDelayed(timerTask, AppConstant.HOB_START_TIME); 
				}else{
		    		hob.onOffMasterSwitch(false);
					workingState = AppConstant.COOKING_WAIT;
					cleanAllHobStatus();
					removeAllCountTimer();
		    		pausebtn.setClickable(false);
		    		setAllViewClickable(false);
		    		pausebtn.setImageResource(R.drawable.start_btn_dis);

				}
				break;
			default:

				break;
			}
		}

	}
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
	public interface GuideOperate{
		public void displayGuide();
		public void HideGuide();
	}
	class LinearOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("*****BtnOnClickListener");
			// TODO Auto-generated method stub

			switch(v.getId()){
			case R.id.lefttop:
				selectedHob = HobPosition.HP_LEFT_CORNER;
				break;
			case R.id.righttop:
				selectedHob = HobPosition.HP_RIGHT_CORNER;
				break;
			case R.id.leftbottom:
				selectedHob = HobPosition.HP_BOTTOM_LEFT;
				break;
			case R.id.rightbottom:
				selectedHob = HobPosition.HP_BOTTOM_RIGHT;
				break;
			default:

				break;
			}

			updateAllHobView();
			setPauseBtnStatus(selectedHob);
			setWheelData(getSelectSettingsTime(selectedHob), getSelectSettingsPower(selectedHob));
            //boolean isDisplayGuide = AppStatic.getSharedBoolean("switchguide", ctx);
            //if(isDisplayGuide){
                guideOperate.displayGuide();
                //isDisplayGuide = false;
                //AppStatic.saveSharedBoolean("switchguide", isDisplayGuide, ctx);
            //}
		}
	}
	MyApplication myApplication;
	Context ctx;
	FrameLayout parent;
	Handler mHandler;
	
	int workingState;
	int leftTopState;
	int rightTopState;
	int leftBottomState;
	
	int rightBottomState;
	int leftTopMode;
	int rightTopMode;
	int leftBottomMode;
	int rightBottomMode;
	int id;
	private FrameLayout miniovensettings = null;
	
	private FrameLayout miniovencooking = null;
	public int settingsTime = 0;
	public int min_time = 0;
	public int max_time = 0;
	
	public int settingsTemp = 0;
	public int powerLeftTop = 0;
	public int powerRightTop = 0;
	public int powerLeftBottom = 0;
	public int powerRightBottom = 0;
	public int timeLeftTop = 0;
	public int timeRightTop = 0;
	public int timeLeftBottom = 0;
	
	public int timeRightBottom = 0;
	public int remainTimeLeftTop = 0;
	public int remainTimeRightTop = 0;
	public int remainTimeLeftBottom = 0;
	
	public int remainTimeRightBottom = 0;
	public int settingTimeLeftTop = 0;
	public int settingTimeRightTop = 0;
	public int settingTimeLeftBottom = 0;
	
	public int settingTimeRightBottom = 0;
	public int settingPowerLeftTop = 0;
	public int settingPowerRightTop = 0;
	public int settingPowerLeftBottom = 0;
	
	public int settingPowerRightBottom = 0;
	public int secLeftTop = 0;
	public int secRightTop = 0;
	
	public int secLeftBottom = 0;
	public int secRightBottom = 0;
	

	
	public  int min_power = 0;
	public int max_power = 0;
	public int power_step = 0;
	private TextView leftTitle = null;
	
	private TextView rightTitle = null;
	private LinearLayout lefttop = null;
	private LinearLayout righttop = null;
	private LinearLayout leftbottom = null;
	private LinearLayout rightbottom = null;
	private ImageView lefttopmin1 = null;
	private ImageView lefttopmin2 = null;
	
	
	private ImageView lefttopsign = null;
	private ImageView lefttopsec1 = null;
	private ImageView lefttopsec2 = null;
	private ImageView lefttoppowernum = null;
	private ImageView lefttoppowerimg = null;
	private ImageView righttopmin1 = null;
	private ImageView righttopmin2 = null;
	
	private ImageView righttopsign = null;
	private ImageView righttopsec1 = null;
	private ImageView righttopsec2 = null;
	private ImageView righttoppowernum = null;
	private ImageView righttoppowerimg = null;
	private ImageView leftbottommin1 = null;
	private ImageView leftbottommin2 = null;
	
	private ImageView leftbottomsign = null;
	private ImageView leftbottomsec1 = null;
	private ImageView leftbottomsec2 = null;
	private ImageView leftbottompowernum = null;
	private ImageView leftbottompowerimg = null;
	private ImageView rightbottommin1 = null;
	private ImageView rightbottommin2 = null;
	
	private ImageView rightbottomsign = null;
	private ImageView rightbottomsec1 = null;
	
	private ImageView rightbottomsec2 = null;
	private ImageView rightbottompowernum = null;
	
	
	private ImageView rightbottompowerimg = null;
	private LinearLayout timeView = null;
	private LinearLayout powerView = null;
	private LinearLayout timeViewBg = null;
	private LinearLayout powerViewBg = null;
	private TextView stateTxt = null;
	private ImageView modeIcon = null;
	private ImageView percent1 = null;
    private ImageView percent2 = null;
    private ImageView percent = null;
	private ImageView title_font = null;
	private TextView timetxt = null;
	
	private ImageView fickerfont = null;
	
	WheelView timeWheel = null;
	WheelView powerWheel = null;
	private ImageButton onbtn = null;
	private ImageButton pausebtn = null;
	
    GuideOperate guideOperate = null;


	
	Hob hob = null;
	boolean mIsPad = false;
    public HobPosition  selectedHob = HobPosition.HP_LEFT_CORNER;
    
    OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if(wheel.equals(timeWheel)){
				//setSelectTime(selectedHob, newValue);
				setSelectSettingsTime(selectedHob, newValue);
			}else if(wheel.equals(powerWheel)){
				setSelectSettingsPower(selectedHob, newValue);
				
		    	if(newValue == 0){
		        	powerWheel.setLabelIcon(R.drawable.off_font);
		    	}else{
		    		powerWheel.setLabelIcon(R.drawable.power_font);
		    	}
				//updateAppointHobView(selectedHob);
			}
		}
	};
    public boolean isScrolling = false;
	
	OnWheelScrollListener wheelScrollListener = new OnWheelScrollListener() {

		@Override
		public void onScrollingFinished(WheelView wheel) {
			// TODO Auto-generated method stub
			System.out.println("*****OnWheelScrollListener");
			
			if(wheel.equals(timeWheel)){
				//AppStatic.saveSharedPreData(AppConstant.mwo_label[0], Integer.toString(settingsTime), ctx);
				System.out.println("*****OnWheelScrollListener time = " + settingsTime);
				//updateAppointHobView(selectedHob);
				//setSelectTime(selectedHob, newValue);
				//updateAppointHobView(selectedHob);
			}else if(wheel.equals(powerWheel)){
				if(isScrolling){
					//AppStatic.saveSharedPreData(AppConstant.mwo_label[1], Integer.toString(settingsTemp), ctx);
					System.out.println("*****OnWheelScrollListener selectedHob = " + selectedHob);
					//updateAppointHobView(selectedHob);

					if(getSelectSettingsPower(selectedHob) == 0){
						if(getSelectState(selectedHob) == AppConstant.COOKING_RUN){
							setSelectState(selectedHob,AppConstant.COOKING_WAIT);
							//Brandt
							//hob.startPause(hobStatus, hob);
							System.out.println("***** startPause 1");
							hob.startPause(getSettingsHobStatus(selectedHob), selectedHob);
						}
						//pausebtn.setImageResource(R.drawable.startbtn);


						pausebtn.setClickable(false);
						pausebtn.setImageResource(R.drawable.start_btn_dis);
						setTimeWheelStateByPower();
					}else{
						if(getSelectState(selectedHob) == AppConstant.COOKING_RUN){
							//Brandt
							//hob.startPause(hobStatus, hob);
							System.out.println("***** startPause 2");
							hob.startPause(getSettingsHobPower(selectedHob), selectedHob);
							pausebtn.setImageResource(R.drawable.pausebtn);
						}else{
							pausebtn.setImageResource(R.drawable.startbtn);
						}
						pausebtn.setClickable(true);

						setTimeWheelStateByPower();
					}
					isScrolling = false;
				}

			}
			
			//updateAppointHobView(selectedHob);
		}

		@Override
		public void onScrollingStarted(WheelView wheel) {
			// TODO Auto-generated method stub
			System.out.println("*****onScrollingStarted");
			if(wheel.equals(powerWheel)){
				isScrolling = true;
			}

		}
		

	};
	
	Bitmap bitmap = null;
	public final Runnable timerTask = new Runnable() {  
    	  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
			hob.onOffMasterSwitch(true);
			workingState = AppConstant.COOKING_RUN;
			selectedHob = HobPosition.HP_LEFT_CORNER;

    		setAllViewClickable(true);
    		pausebtn.setImageResource(R.drawable.start_btn_dis);
            updateAllHobView();
    		pausebtn.setClickable(false);
            //boolean isDisplayGuide = AppStatic.getSharedBoolean("startguide", ctx);
            //if(isDisplayGuide){
                guideOperate.displayGuide();
                //isDisplayGuide = false;
                //AppStatic.saveSharedBoolean("startguide", isDisplayGuide, ctx);
            //}

        }  
    };
	
	public final Runnable timerTask1 = new Runnable() {  
  	  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
        	System.out.println("**** timerTask1 leftTopMode = " + leftTopMode);
        	if(leftTopMode == AppConstant.HOB_MODE_A){
               	if(secLeftTop != 59 || timeLeftTop != 99){
                	if(secLeftTop == 59){
                		secLeftTop = 0;
                		timeLeftTop++;
                	}else{
                    	secLeftTop++;
                	}
                	if(secLeftTop == 59 && timeLeftTop == 99){

        				setSelectState(HobPosition.HP_LEFT_CORNER,AppConstant.COOKING_WAIT);
        				if(selectedHob == HobPosition.HP_LEFT_CORNER){
        					pausebtn.setImageResource(R.drawable.startbtn);
        					setTimeWheelStateByPower();
        				}
        				hob.startPause(getHobStatus(HobPosition.HP_LEFT_CORNER), HobPosition.HP_LEFT_CORNER);
        				mHandler.removeCallbacks(timerTask1);
        				secLeftTop = 0;
        				timeLeftTop = 0;
                	}else{
                		mHandler.removeCallbacks(timerTask1);
                    	mHandler.postDelayed(timerTask1, AppConstant.HOB_START_TIME);
                	}
            	}
        	}else{
               	if(secLeftTop != 0 || timeLeftTop != 0){
                	if(secLeftTop == 0){
                		secLeftTop = 59;
                		timeLeftTop--;
                	}else{
                    	secLeftTop--;
                	}
                	if(secLeftTop == 0 && timeLeftTop == 0){

        				setSelectState(HobPosition.HP_LEFT_CORNER,AppConstant.COOKING_WAIT);
        				if(selectedHob == HobPosition.HP_LEFT_CORNER){
        					pausebtn.setImageResource(R.drawable.startbtn);
        					setTimeWheelStateByPower();
        				}
        				hob.startPause(getHobStatus(HobPosition.HP_LEFT_CORNER), HobPosition.HP_LEFT_CORNER);
        				mHandler.removeCallbacks(timerTask1);

                	}else{
                		mHandler.removeCallbacks(timerTask1);
                    	mHandler.postDelayed(timerTask1, AppConstant.HOB_START_TIME);
                	}
            	}
        	}
 
        	
        	updateAppointHobView(HobPosition.HP_LEFT_CORNER);
        }  
    };
	public final Runnable timerTask2 = new Runnable() {  
    	  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
        	if(rightTopMode == AppConstant.HOB_MODE_A){
               	if(secRightTop != 59 || timeRightTop!= 99){
                	if(secRightTop == 59){
                		secRightTop = 0;
                		timeRightTop++;
                	}else{
                    	secRightTop++;
                	}
                	if(secRightTop == 59 && timeRightTop == 99){

        				setSelectState(HobPosition.HP_RIGHT_CORNER,AppConstant.COOKING_WAIT);
        				if(selectedHob == HobPosition.HP_RIGHT_CORNER){
        					pausebtn.setImageResource(R.drawable.startbtn);
        					setTimeWheelStateByPower();
        				}
        				hob.startPause(getHobStatus(HobPosition.HP_RIGHT_CORNER), HobPosition.HP_RIGHT_CORNER);
        				mHandler.removeCallbacks(timerTask2);
        				secRightTop = 0;
        				timeRightTop = 0;
                	}else{
                		mHandler.removeCallbacks(timerTask2);
                    	mHandler.postDelayed(timerTask2, AppConstant.HOB_START_TIME);
                	}
            	}
        	}else{
               	if(secRightTop != 0 || timeRightTop != 0){
                	if(secRightTop == 0){
                		secRightTop = 59;
                		timeRightTop--;
                	}else{
                    	secRightTop--;
                	}
                	if(secRightTop == 0 && timeRightTop == 0){

        				setSelectState(HobPosition.HP_RIGHT_CORNER,AppConstant.COOKING_WAIT);
        				if(selectedHob == HobPosition.HP_RIGHT_CORNER){
        					pausebtn.setImageResource(R.drawable.startbtn);
        					setTimeWheelStateByPower();
        				}
        				hob.startPause(getHobStatus(HobPosition.HP_RIGHT_CORNER), HobPosition.HP_RIGHT_CORNER);
        				mHandler.removeCallbacks(timerTask2);

                	}else{
                		mHandler.removeCallbacks(timerTask2);
                    	mHandler.postDelayed(timerTask2, AppConstant.HOB_START_TIME);
                	}
            	}
        	}
 
        	updateAppointHobView(HobPosition.HP_RIGHT_CORNER);
        }  
    };
	
	public final Runnable timerTask3 = new Runnable() {  
    	  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
        	if(leftBottomMode == AppConstant.HOB_MODE_A){
               	if(secLeftBottom != 59 || timeLeftBottom != 99){
                	if(secLeftBottom == 59){
                		secLeftBottom = 0;
                		timeLeftBottom++;
                	}else{
                    	secLeftBottom++;
                	}
                	if(secLeftBottom == 59 && timeLeftBottom == 99){

        				setSelectState(HobPosition.HP_BOTTOM_LEFT,AppConstant.COOKING_WAIT);
        				if(selectedHob == HobPosition.HP_BOTTOM_LEFT){
        					pausebtn.setImageResource(R.drawable.startbtn);
        					setTimeWheelStateByPower();
        				}
        				hob.startPause(getHobStatus(HobPosition.HP_BOTTOM_LEFT), HobPosition.HP_BOTTOM_LEFT);
        				mHandler.removeCallbacks(timerTask3);
        				secLeftBottom = 0;
        				timeLeftBottom = 0;

                	}else{
                		mHandler.removeCallbacks(timerTask3);
                    	mHandler.postDelayed(timerTask3, AppConstant.HOB_START_TIME);
                	}
            	}
        	}else{
               	if(secLeftBottom != 0 || timeLeftBottom != 0){
                	if(secLeftBottom == 0){
                		secLeftBottom = 59;
                		timeLeftBottom--;
                	}else{
                    	secLeftBottom--;
                	}
                	if(secLeftBottom == 0 && timeLeftBottom == 0){

        				setSelectState(HobPosition.HP_BOTTOM_LEFT,AppConstant.COOKING_WAIT);
        				if(selectedHob == HobPosition.HP_BOTTOM_LEFT){
        					pausebtn.setImageResource(R.drawable.startbtn);
        					setTimeWheelStateByPower();
        				}
        				hob.startPause(getHobStatus(HobPosition.HP_BOTTOM_LEFT), HobPosition.HP_BOTTOM_LEFT);
        				mHandler.removeCallbacks(timerTask3);

                	}else{
                		mHandler.removeCallbacks(timerTask3);
                    	mHandler.postDelayed(timerTask3, AppConstant.HOB_START_TIME);
                	}
            	}
        	}
 
        	updateAppointHobView(HobPosition.HP_BOTTOM_LEFT);
        }  
    };
    public final Runnable timerTask4 = new Runnable() {  
    	  
        @Override  
        public void run() {  
        	if(rightBottomMode == AppConstant.HOB_MODE_A){
                // TODO Auto-generated method stub  
            	if(secRightBottom != 59 || timeRightBottom != 99){
                	if(secRightBottom == 59){
                		secRightBottom = 0;
                		timeRightBottom++;
                	}else{
                    	secRightBottom++;
                	}
                	if(secRightBottom == 59 && timeRightBottom == 99){

        				setSelectState(HobPosition.HP_BOTTOM_RIGHT,AppConstant.COOKING_WAIT);
        				if(selectedHob == HobPosition.HP_BOTTOM_RIGHT){
        					pausebtn.setImageResource(R.drawable.startbtn);
        					setTimeWheelStateByPower();
        				}
        				hob.startPause(getHobStatus(HobPosition.HP_BOTTOM_RIGHT), HobPosition.HP_BOTTOM_RIGHT);
        				mHandler.removeCallbacks(timerTask4);
        				secRightBottom = 0;
        				timeRightBottom = 0;
                	}else{
                		mHandler.removeCallbacks(timerTask4);
                    	mHandler.postDelayed(timerTask4, AppConstant.HOB_START_TIME);
                	}
            	}
        	}else{
                // TODO Auto-generated method stub  
            	if(secRightBottom != 0 || timeRightBottom != 0){
                	if(secRightBottom == 0){
                		secRightBottom = 59;
                		timeRightBottom--;
                	}else{
                    	secRightBottom--;
                	}
                	if(secRightBottom == 0 && timeRightBottom == 0){

        				setSelectState(HobPosition.HP_BOTTOM_RIGHT,AppConstant.COOKING_WAIT);
        				if(selectedHob == HobPosition.HP_BOTTOM_RIGHT){
        					pausebtn.setImageResource(R.drawable.startbtn);
        					setTimeWheelStateByPower();
        				}
        				hob.startPause(getHobStatus(HobPosition.HP_BOTTOM_RIGHT), HobPosition.HP_BOTTOM_RIGHT);
        				mHandler.removeCallbacks(timerTask4);

                	}else{
                		mHandler.removeCallbacks(timerTask4);
                    	mHandler.postDelayed(timerTask4, AppConstant.HOB_START_TIME);
                	}
            	}
        	}
 
        	updateAppointHobView(HobPosition.HP_BOTTOM_RIGHT);
        }  
    };
    
    public HobWorkingView(Context ctx, FrameLayout parent, MyApplication myApplication, int id, Handler mHandler, GuideOperate guideOperate) {
		super();
		this.ctx = ctx;
		this.parent = parent;
		this.myApplication = myApplication;
		this.id = id;
		this.mHandler = mHandler;
		this.guideOperate = guideOperate;
		
		
		WindowManager wm = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		mIsPad = width >= 1200;
		
		hob = MainActivity.getInstance().getHobDevice();
		workingState = hob.getWorkStatus();
		leftTopState = hob.getWorkStatus(HobPosition.HP_LEFT_CORNER);
		rightTopState = hob.getWorkStatus(HobPosition.HP_RIGHT_CORNER);
		leftBottomState = hob.getWorkStatus(HobPosition.HP_BOTTOM_LEFT);
		rightBottomState = hob.getWorkStatus(HobPosition.HP_BOTTOM_RIGHT);
		powerLeftTop = hob.getPowerLevel(HobPosition.HP_LEFT_CORNER);
		powerRightTop = hob.getPowerLevel(HobPosition.HP_RIGHT_CORNER);
		powerLeftBottom = hob.getPowerLevel(HobPosition.HP_BOTTOM_LEFT);
		powerRightBottom = hob.getPowerLevel(HobPosition.HP_BOTTOM_RIGHT);
		
		timeLeftTop = hob.getRemainTime(HobPosition.HP_LEFT_CORNER);
		timeRightTop = hob.getRemainTime(HobPosition.HP_RIGHT_CORNER);
		timeLeftBottom = hob.getRemainTime(HobPosition.HP_BOTTOM_LEFT);
		timeRightBottom = hob.getRemainTime(HobPosition.HP_BOTTOM_RIGHT);
		remainTimeLeftTop = timeLeftTop;
		remainTimeRightTop = timeRightTop;
		remainTimeLeftBottom = timeLeftBottom;
		remainTimeRightBottom = timeRightBottom;
		settingTimeLeftTop = timeLeftTop;
		settingTimeRightTop = timeRightTop;
		settingTimeLeftBottom = timeLeftBottom;
		settingTimeRightBottom = timeRightBottom;
		
		settingPowerLeftTop = powerLeftTop;
		settingPowerRightTop = powerRightTop;
		settingPowerLeftBottom = powerLeftBottom;
		settingPowerRightBottom = powerRightBottom;
		
		secLeftTop = 0;
		secRightTop = 0;
		secLeftBottom = 0;
		secRightBottom = 0;
		selectedHob = HobPosition.HP_LEFT_CORNER;
		if(remainTimeLeftTop == 0){
			leftTopMode = AppConstant.HOB_MODE_A;
		}else{
			leftTopMode = AppConstant.HOB_MODE_B;
		}
		if(remainTimeRightTop == 0){
			rightTopMode = AppConstant.HOB_MODE_A;
		}else{
			rightTopMode = AppConstant.HOB_MODE_B;
		}
		if(remainTimeLeftBottom == 0){
			leftBottomMode = AppConstant.HOB_MODE_A;
		}else{
			leftBottomMode = AppConstant.HOB_MODE_B;
		}
		if(remainTimeRightBottom == 0){
			rightBottomMode = AppConstant.HOB_MODE_A;
		}else{
			rightBottomMode = AppConstant.HOB_MODE_B;
		}
		initView();
	}

    public void cleanAllHobStatus(){
		leftTopState = 0;
		rightTopState = 0;
		leftBottomState = 0;
		rightBottomState = 0;
		powerLeftTop = 0;
		powerRightTop = 0;
		powerLeftBottom = 0;
		powerRightBottom = 0;
		
		timeLeftTop = 0;
		timeRightTop = 0;
		timeLeftBottom = 0;
		timeRightBottom = 0;
		remainTimeLeftTop = timeLeftTop;
		remainTimeRightTop = timeRightTop;
		remainTimeLeftBottom = timeLeftBottom;
		remainTimeRightBottom = timeRightBottom;
		settingTimeLeftTop = timeLeftTop;
		settingTimeRightTop = timeRightTop;
		settingTimeLeftBottom = timeLeftBottom;
		settingTimeRightBottom = timeRightBottom;
		
		secLeftTop = 0;
		secRightTop = 0;
		secLeftBottom = 0;
		secRightBottom = 0;
		
		selectedHob = HobPosition.HP_LEFT_CORNER;
		updateAllHobView();
		setWheelData(getSelectSettingsTime(selectedHob), getSelectPower(selectedHob));
	}





	private HobStatus getHobStatus(HobPosition selectedHob) {
		int state = 0;
		int time = 0;
		int power = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			state = leftBottomState;
			time = timeLeftBottom;
			power = powerLeftBottom;
			
			break;
			
		case HP_LEFT_CORNER:
			state = leftTopState;
			time = timeLeftTop;
			power = powerLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			state = rightTopState;
			time = timeRightTop;
			power = powerRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			state = rightBottomState;
			time = timeRightBottom;
			power = powerRightBottom;
			break;
			
		default:
			break;
		}
		System.out.println("**** getHobStatus selectedHob = " + selectedHob + ", state = " + state + ", power = " + power + ", time = " + time);
		HobStatus os = new HobStatus(state, power, time);
    	return os;
		//setTimeTempView(settingsTime, settingsTemp);
    }
	public int getSelectPower(HobPosition selectedHob) {
		int power = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			power = powerLeftBottom;
			break;
			
		case HP_LEFT_CORNER:
			power = powerLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			power = powerRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			power = powerRightBottom;
			break;
			
		default:
			break;
		}
    	return power;
    }
	public int getSelectRemainTime(HobPosition selectedHob) {
		int time = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			time = remainTimeLeftBottom;
			break;
			
		case HP_LEFT_CORNER:
			time = remainTimeLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			time = remainTimeRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			time = remainTimeRightBottom;
			break;
			
		default:
			break;
		}
    	return time;
    }
	public int getSelectSettingsPower(HobPosition selectedHob) {
		int power = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			power = settingPowerLeftBottom;
			break;
			
		case HP_LEFT_CORNER:
			power = settingPowerLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			power = settingPowerRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			power = settingPowerRightBottom;
			break;
			
		default:
			break;
		}
    	return power;
    }
	public int getSelectSettingsTime(HobPosition selectedHob) {
		int time = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			time = settingTimeLeftBottom;
			break;
			
		case HP_LEFT_CORNER:
			time = settingTimeLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			time = settingTimeRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			time = settingTimeRightBottom;
			break;
			
		default:
			break;
		}
    	return time;
    }
	public int getSelectState(HobPosition selectedHob) {
		int state = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			state = leftBottomState;
			break;
			
		case HP_LEFT_CORNER:
			state = leftTopState;
			break;
			
		case HP_RIGHT_CORNER:
			state = rightTopState;
			break;
			
		case HP_BOTTOM_RIGHT:
			state = rightBottomState;
			break;
			
		default:
			break;
		}
    	return state;
    }
	public int getSelectTime(HobPosition selectedHob) {
		int time = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			time = timeLeftBottom;
			break;
			
		case HP_LEFT_CORNER:
			time = timeLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			time = timeRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			time = timeRightBottom;
			break;
			
		default:
			break;
		}
    	return time;
    }
	private HobStatus getSettingsHobPower(HobPosition selectedHob) {
		int state = 0;
		int time = 0;
		int power = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			state = leftBottomState;
			time = timeLeftBottom;
			power = settingPowerLeftBottom;
			
			break;
			
		case HP_LEFT_CORNER:
			state = leftTopState;
			time = timeLeftTop;
			power = settingPowerLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			state = rightTopState;
			time = timeRightTop;
			power = settingPowerRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			state = rightBottomState;
			time = timeRightBottom;
			power = settingPowerRightBottom;
			break;
			
		default:
			break;
		}
		System.out.println("**** getHobStatus selectedHob = " + selectedHob + ", state = " + state + ", power = " + power + ", time = " + time);
		HobStatus os = new HobStatus(state, power, time);
    	return os;
		//setTimeTempView(settingsTime, settingsTemp);
    }
	private HobStatus getSettingsHobStatus(HobPosition selectedHob) {
		int state = 0;
		int time = 0;
		int power = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			state = leftBottomState;
			time = settingTimeLeftBottom;
			power = settingPowerLeftBottom;
			
			break;
			
		case HP_LEFT_CORNER:
			state = leftTopState;
			time = settingTimeLeftTop;
			power = settingPowerLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			state = rightTopState;
			time = settingTimeRightTop;
			power = settingPowerRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			state = rightBottomState;
			time = settingTimeRightBottom;
			power = settingPowerRightBottom;
			break;
			
		default:
			break;
		}
		System.out.println("**** getHobStatus selectedHob = " + selectedHob + ", state = " + state + ", power = " + power + ", time = " + time);
		HobStatus os = new HobStatus(state, power, time);
    	return os;
		//setTimeTempView(settingsTime, settingsTemp);
    }
	public int getWorkingState() {
		// TODO Auto-generated method stub
    	return workingState;
	}
	private void initView() {
    	lefttop = (LinearLayout) parent.findViewById(R.id.lefttop);
    	lefttopmin1 = (ImageView) parent.findViewById(R.id.lefttopmin1);
    	lefttopmin2 = (ImageView) parent.findViewById(R.id.lefttopmin2);
    	lefttopsign = (ImageView) parent.findViewById(R.id.lefttopsign);
    	lefttopsec1 = (ImageView) parent.findViewById(R.id.lefttopsec1);
    	lefttopsec2 = (ImageView) parent.findViewById(R.id.lefttopsec2);
    	lefttoppowernum = (ImageView) parent.findViewById(R.id.lefttoppowernum);
    	lefttoppowerimg = (ImageView) parent.findViewById(R.id.lefttoppowerimg);
    	
    	righttop = (LinearLayout) parent.findViewById(R.id.righttop);
    	righttopmin1 = (ImageView) parent.findViewById(R.id.righttopmin1);
    	righttopmin2 = (ImageView) parent.findViewById(R.id.righttopmin2);
    	righttopsign = (ImageView) parent.findViewById(R.id.righttopsign);
    	righttopsec1 = (ImageView) parent.findViewById(R.id.righttopsec1);
    	righttopsec2 = (ImageView) parent.findViewById(R.id.righttopsec2);
    	righttoppowernum = (ImageView) parent.findViewById(R.id.righttoppowernum);
    	righttoppowerimg = (ImageView) parent.findViewById(R.id.righttoppowerimg);
    	
    	leftbottom = (LinearLayout) parent.findViewById(R.id.leftbottom);
    	leftbottommin1 = (ImageView) parent.findViewById(R.id.leftbottommin1);
    	leftbottommin2 = (ImageView) parent.findViewById(R.id.leftbottommin2);
    	leftbottomsign = (ImageView) parent.findViewById(R.id.leftbottomsign);
    	leftbottomsec1 = (ImageView) parent.findViewById(R.id.leftbottomsec1);
    	leftbottomsec2 = (ImageView) parent.findViewById(R.id.leftbottomsec2);
    	leftbottompowernum = (ImageView) parent.findViewById(R.id.leftbottompowernum);
    	leftbottompowerimg = (ImageView) parent.findViewById(R.id.leftbottompowerimg);
    	
    	rightbottom = (LinearLayout) parent.findViewById(R.id.rightbottom);
    	rightbottommin1 = (ImageView) parent.findViewById(R.id.rightbottommin1);
    	rightbottommin2 = (ImageView) parent.findViewById(R.id.rightbottommin2);
    	rightbottomsign = (ImageView) parent.findViewById(R.id.rightbottomsign);
    	rightbottomsec1 = (ImageView) parent.findViewById(R.id.rightbottomsec1);
    	rightbottomsec2 = (ImageView) parent.findViewById(R.id.rightbottomsec2);
    	rightbottompowernum = (ImageView) parent.findViewById(R.id.rightbottompowernum);
    	rightbottompowerimg = (ImageView) parent.findViewById(R.id.rightbottompowerimg);
    	
    	lefttop.setOnClickListener(new LinearOnClickListener());
    	righttop.setOnClickListener(new LinearOnClickListener());
    	leftbottom.setOnClickListener(new LinearOnClickListener());
    	rightbottom.setOnClickListener(new LinearOnClickListener());
    	
    	leftTitle = (TextView) parent.findViewById(R.id.lefttitle);
    	rightTitle = (TextView) parent.findViewById(R.id.righttitle);
    	onbtn = (ImageButton)parent.findViewById(R.id.onbtn);
    	pausebtn = (ImageButton)parent.findViewById(R.id.pausebtn);
    	onbtn.setOnClickListener(new BtnOnClickListener());
    	pausebtn.setOnClickListener(new BtnOnClickListener());
    	initWheelView();
    	if(workingState == AppConstant.COOKING_RUN){
    		//pausebtn.setClickable(true);
    		setAllViewClickable(true);
    		startAllRunTimer();
    		
    		updateAllHobView();
    		//pausebtn.setImageResource(R.drawable.startbtn);
    	}else{
    		cleanAllHobStatus();
    		//pausebtn.setClickable(false);
    		setAllViewClickable(false);
    		//pausebtn.setImageResource(R.drawable.start_btn_dis);
    		
    	}
    	setPauseState();
    	//initViewPager();

    	updateAllHobView();
    }
	private void initWheelView() {
    	timeView = (LinearLayout) parent.findViewById(R.id.timeview);
    	powerView = (LinearLayout) parent.findViewById(R.id.powerview);
		timeWheel = (WheelView) parent.findViewById(R.id.timewheel);
		powerWheel = (WheelView) parent.findViewById(R.id.powerwheel);
		
		if(mIsPad) {
			timeViewBg = (LinearLayout) parent.findViewById(R.id.pad_hob_time_bg);
	    	powerViewBg = (LinearLayout) parent.findViewById(R.id.pad_hob_power_bg);
		}
		
		setWheelData(getSelectSettingsTime(selectedHob), getSelectSettingsPower(selectedHob));
    }
	private void removeAllCountTimer() {
		//mHandler.removeCallbacks(timerTask);
		mHandler.removeCallbacks(timerTask1);
		mHandler.removeCallbacks(timerTask2);
		mHandler.removeCallbacks(timerTask3);
		mHandler.removeCallbacks(timerTask4);
    }
	private void removeAppointTimer(HobPosition selectedHob) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			mHandler.removeCallbacks(timerTask3);
			break;
			
		case HP_LEFT_CORNER:
			mHandler.removeCallbacks(timerTask1);
			break;
			
		case HP_RIGHT_CORNER:
			mHandler.removeCallbacks(timerTask2);
			break;
			
		case HP_BOTTOM_RIGHT:
			mHandler.removeCallbacks(timerTask4);
			break;
			
		default:
			break;
		}
    }
	public void removeTimerTask(){
		mHandler.removeCallbacks(timerTask);
		removeAllCountTimer();
	}
	public void setAllViewClickable(boolean clickable) {
		//onbtn.setClickable(clickable);
		pausebtn.setClickable(clickable);
    	lefttop.setClickable(clickable);
    	righttop.setClickable(clickable);
    	leftbottom.setClickable(clickable);
    	rightbottom.setClickable(clickable);
    	powerWheel.setClickable(clickable);
    	timeWheel.setClickable(clickable);
		if(clickable){
	    	powerWheel.setOnTouchListener(new EnableOnTouchListener());
	    	//timeWheel.setOnTouchListener(new EnableOnTouchListener());
	    	powerWheel.setNumColor(AppConstant.NUM_WHITE);
	    	if(!mIsPad) {
	    		powerView.setBackgroundResource(R.drawable.hob_power_wheel_bg_nor);
	    	} else {
	    		powerViewBg.setBackgroundResource(R.drawable.hob_power_wheel_bg_nor);
	    	}
	    	//timeView.setBackgroundResource(R.drawable.hob_time_wheel_bg_nor);
		}else{
	    	powerWheel.setOnTouchListener(new DisableOnTouchListener());
	    	timeWheel.setOnTouchListener(new DisableOnTouchListener());
	    	powerWheel.setNumColor(AppConstant.NUM_GRAY);
	    	timeWheel.setNumColor(AppConstant.NUM_GRAY);
	    	
	    	if(!mIsPad) {
	    		powerView.setBackgroundResource(R.drawable.hob_power_wheel_bg_dis);
		    	timeView.setBackgroundResource(R.drawable.hob_time_wheel_bg_dis);
	    	} else {
	    		powerViewBg.setBackgroundResource(R.drawable.hob_power_wheel_bg_dis);
		    	timeViewBg.setBackgroundResource(R.drawable.hob_time_wheel_bg_dis);
	    	}
		}
		setTimeWheelStateByPower();
	}
	
	public void setBtnClickable(boolean clickable) {
		onbtn.setClickable(clickable);
		if(clickable && workingState == AppConstant.COOKING_RUN){
			pausebtn.setClickable(clickable);
			lefttop.setClickable(clickable);
			righttop.setClickable(clickable);
			leftbottom.setClickable(clickable);
			rightbottom.setClickable(clickable);
		}

		if(clickable && workingState == AppConstant.COOKING_RUN){
	    	powerWheel.setOnTouchListener(new EnableOnTouchListener());
	    	timeWheel.setOnTouchListener(new EnableOnTouchListener());
		}else{
	    	powerWheel.setOnTouchListener(new DisableOnTouchListener());
	    	timeWheel.setOnTouchListener(new DisableOnTouchListener());
		}

	}
	public void setDeviceId(int id){
		this.id = id;
	}
	
	public void setHobStartingView(){
		leftbottom.setBackgroundResource(R.drawable.display_box);
		leftbottommin1.setImageResource(AppConstant.hobGrayTimeNum[timeLeftBottom/10]);
		leftbottommin2.setImageResource(AppConstant.hobGrayTimeNum[timeLeftBottom%10]);
		leftbottomsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
		leftbottomsec1.setImageResource(AppConstant.hobGrayTimeNum[secLeftBottom/10]);
		leftbottomsec2.setImageResource(AppConstant.hobGrayTimeNum[secLeftBottom%10]);
		leftbottompowernum.setImageResource(AppConstant.hobSelGrayPowerNum[powerLeftBottom]);
		leftbottompowerimg.setImageResource(AppConstant.hobSelGrayPowerImg[powerLeftBottom]);
		
    	lefttop.setBackgroundResource(R.drawable.display_box);
    	lefttopmin1.setImageResource(AppConstant.hobGrayTimeNum[timeLeftTop/10]);
    	lefttopmin2.setImageResource(AppConstant.hobGrayTimeNum[timeLeftTop%10]);
    	lefttopsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
    	lefttopsec1.setImageResource(AppConstant.hobGrayTimeNum[secLeftTop/10]);
    	lefttopsec2.setImageResource(AppConstant.hobGrayTimeNum[secLeftTop%10]);
    	lefttoppowernum.setImageResource(AppConstant.hobSelGrayPowerNum[powerLeftTop]);
    	lefttoppowerimg.setImageResource(AppConstant.hobSelGrayPowerImg[powerLeftTop]);
    	
		righttop.setBackgroundResource(R.drawable.display_box);
		righttopmin1.setImageResource(AppConstant.hobGrayTimeNum[timeRightTop/10]);
		righttopmin2.setImageResource(AppConstant.hobGrayTimeNum[timeRightTop%10]);
		righttopsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
		righttopsec1.setImageResource(AppConstant.hobGrayTimeNum[secRightTop/10]);
		righttopsec2.setImageResource(AppConstant.hobGrayTimeNum[secRightTop%10]);
		righttoppowernum.setImageResource(AppConstant.hobSelGrayPowerNum[powerRightTop]);
		righttoppowerimg.setImageResource(AppConstant.hobSelGrayPowerImg[powerRightTop]);
		
		rightbottom.setBackgroundResource(R.drawable.display_box);
		rightbottommin1.setImageResource(AppConstant.hobGrayTimeNum[timeRightBottom/10]);
		rightbottommin2.setImageResource(AppConstant.hobGrayTimeNum[timeRightBottom%10]);
		rightbottomsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
		rightbottomsec1.setImageResource(AppConstant.hobGrayTimeNum[secRightBottom/10]);
		rightbottomsec2.setImageResource(AppConstant.hobGrayTimeNum[secRightBottom%10]);
		rightbottompowernum.setImageResource(AppConstant.hobSelGrayPowerNum[powerRightBottom]);
		rightbottompowerimg.setImageResource(AppConstant.hobSelGrayPowerImg[powerRightBottom]);
    }
	public void setMode(HobPosition selectedHob) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			System.out.println("**** timeLeftBottom = " + timeLeftBottom + ", leftBottomMode = " + leftBottomMode);
			if(settingTimeLeftBottom == 0){
				leftBottomMode = AppConstant.HOB_MODE_A;
			}else{
				leftBottomMode = AppConstant.HOB_MODE_B;
			}
			break;
			
		case HP_LEFT_CORNER:
			System.out.println("**** setMode settingTimeLeftTop = " + settingTimeLeftTop);
			if(settingTimeLeftTop == 0){
				leftTopMode = AppConstant.HOB_MODE_A;
			}else{
				leftTopMode = AppConstant.HOB_MODE_B;
			}
			break;
			
		case HP_RIGHT_CORNER:
			if(settingTimeRightTop == 0){
				rightTopMode = AppConstant.HOB_MODE_A;
			}else{
				rightTopMode = AppConstant.HOB_MODE_B;
			}
			
			break;
			
		case HP_BOTTOM_RIGHT:
			if(settingTimeRightBottom == 0){
				rightBottomMode = AppConstant.HOB_MODE_A;
			}else{
				rightBottomMode = AppConstant.HOB_MODE_B;
			}
			break;
			
		default:
			break;
		}
	}
	private void setPauseBtnStatus(HobPosition selectedHob) {
		if(getSelectState(selectedHob) == AppConstant.COOKING_RUN){
			pausebtn.setImageResource(R.drawable.pausebtn);
		}else{
			pausebtn.setImageResource(R.drawable.startbtn);
		}
    }

    public void setPauseState() {
		int state = AppConstant.COOKING_WAIT;
		int power = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			state = leftBottomState;
			power = settingPowerLeftBottom;
			break;
			
		case HP_LEFT_CORNER:
			state = leftTopState;
			power = settingPowerLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			state = rightTopState;
			power = settingPowerRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			state = rightBottomState;
			power = settingPowerRightBottom;
			break;
			
		default:
			state = leftTopState;
			power = settingPowerLeftTop;
			break;
		}
		if(workingState == AppConstant.COOKING_WAIT){
			pausebtn.setImageResource(R.drawable.start_btn_dis);
			pausebtn.setClickable(false);
		}else{
			if(state == AppConstant.COOKING_RUN){
				pausebtn.setImageResource(R.drawable.pausebtn);
				pausebtn.setClickable(true);
				//setSelectSettingsPower(selectedHob, );
			}else{
				if(power == 0){
					pausebtn.setImageResource(R.drawable.start_btn_dis);
					pausebtn.setClickable(false);
				}else{
					pausebtn.setImageResource(R.drawable.startbtn);
					pausebtn.setClickable(true);
				}
			}
		}
    }

    public void setPowerWheelView(int power){
    	powerWheel.setAdapter(new NumericWheelAdapter(0, 9, 1), AppConstant.NUM_WHITE);
    	powerWheel.setLabel("C");
    	if(power == 0){
        	powerWheel.setLabelIcon(R.drawable.off_font);
    	}else{
    		powerWheel.setLabelIcon(R.drawable.power_font);
    	}

    	powerWheel.setCyclic(true);
		
		powerWheel.setCurrentItem(power);
		powerWheel.addChangingListener(wheelListener);
		powerWheel.addScrollingListener(wheelScrollListener);

		leftTitle.setText("POWER");
    }
    
	public void setRemainTimeByMode(HobPosition selectedHob, int time) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			if(leftBottomMode == AppConstant.HOB_MODE_B){
				if(remainTimeLeftBottom != 0 && time == 0
						&& getSelectPower(HobPosition.HP_BOTTOM_LEFT) == 0){
					removeAppointTimer(HobPosition.HP_BOTTOM_LEFT);
				}
				remainTimeLeftBottom = time;
			}else{
				remainTimeLeftBottom = 0;
			}
			break;
			
		case HP_LEFT_CORNER:
			if(leftTopMode == AppConstant.HOB_MODE_B){
				if(remainTimeLeftTop != 0 && time == 0
						&& getSelectPower(HobPosition.HP_LEFT_CORNER) == 0){
					removeAppointTimer(HobPosition.HP_LEFT_CORNER);
				}
				remainTimeLeftTop = time;
			}else{
				remainTimeLeftTop = 0;
			}
			break;
			
		case HP_RIGHT_CORNER:
			if(rightTopMode == AppConstant.HOB_MODE_B){
				if(remainTimeRightTop != 0 && time == 0
						&& getSelectPower(HobPosition.HP_RIGHT_CORNER) == 0){
					removeAppointTimer(HobPosition.HP_RIGHT_CORNER);
				}
				remainTimeRightTop = time;
			}else{
				remainTimeRightTop = 0;
			}
			break;
			
		case HP_BOTTOM_RIGHT:
			if(rightBottomMode == AppConstant.HOB_MODE_B){
				if(remainTimeRightBottom != 0 && time == 0
						&& getSelectPower(HobPosition.HP_BOTTOM_RIGHT) == 0){
					removeAppointTimer(HobPosition.HP_BOTTOM_RIGHT);
				}
				remainTimeRightBottom = time;
			}else{
				remainTimeRightBottom = 0;
			}
			break;
			
		default:
			break;
		}
    }
	public void setSelectMode(HobPosition selectedHob) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			System.out.println("**** timeLeftBottom = " + timeLeftBottom + ", leftBottomMode = " + leftBottomMode);
			if(remainTimeLeftBottom == 0){
				if(leftBottomMode != AppConstant.HOB_MODE_A){
					leftBottomMode = AppConstant.HOB_MODE_A;
					secLeftBottom = 0;
				}
				
			}else{
				if(leftBottomMode != AppConstant.HOB_MODE_B){
					leftBottomMode = AppConstant.HOB_MODE_B;
					secLeftBottom = 0;
				}
				
			}
			
			break;
			
		case HP_LEFT_CORNER:
			System.out.println("**** setSelectMode timeLeftTop = " + timeLeftTop + ", leftTopMode = " + leftTopMode);
			if(remainTimeLeftTop == 0){
				if(leftTopMode != AppConstant.HOB_MODE_A){
					leftTopMode = AppConstant.HOB_MODE_A;
					secLeftTop = 0;
				}
				
			}else{
				if(leftTopMode != AppConstant.HOB_MODE_B){
					leftTopMode = AppConstant.HOB_MODE_B;
					secLeftTop = 0;
				}
				
			}
			
			break;
			
		case HP_RIGHT_CORNER:
			if(remainTimeRightTop == 0){
				if(rightTopMode != AppConstant.HOB_MODE_A){
					rightTopMode = AppConstant.HOB_MODE_A;
					secRightTop = 0;
				}
				
			}else{
				if(rightTopMode != AppConstant.HOB_MODE_B){
					rightTopMode = AppConstant.HOB_MODE_B;
					secRightTop = 0;
				}
				
			}
			
			break;
			
		case HP_BOTTOM_RIGHT:
			if(remainTimeRightBottom == 0){
				if(rightBottomMode != AppConstant.HOB_MODE_A){
					rightBottomMode = AppConstant.HOB_MODE_A;
					secRightBottom = 0;
				}
				
			}else{
				if(rightBottomMode != AppConstant.HOB_MODE_B){
					rightBottomMode = AppConstant.HOB_MODE_B;
					secRightBottom = 0;
				}
				
			}
			
			break;
			
		default:
			break;
		}
    }
	public void setSelectPower(HobPosition selectedHob, int power) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			powerLeftBottom = power;
			System.out.println("*****powerLeftBottom power = " + power);
			break;
			
		case HP_LEFT_CORNER:
			powerLeftTop = power;
			System.out.println("*****powerLeftTop power = " + power);
			break;
			
		case HP_RIGHT_CORNER:
			powerRightTop = power;
			System.out.println("*****powerRightTop power = " + power);
			break;
			
		case HP_BOTTOM_RIGHT:
			powerRightBottom = power;
			System.out.println("*****powerRightBottom power = " + power);
			break;
			
		default:
			break;
		}

    }

	public void setSelectSettingsPower(HobPosition selectedHob, int power) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			settingPowerLeftBottom = power;
			break;
			
		case HP_LEFT_CORNER:
			settingPowerLeftTop = power;
			break;
			
		case HP_RIGHT_CORNER:
			settingPowerRightTop = power;
			break;
			
		case HP_BOTTOM_RIGHT:
			settingPowerRightBottom = power;
			break;
			
		default:
			settingPowerLeftBottom = power;
			break;
		}
    }
	public void setSelectSettingsTime(HobPosition selectedHob, int time) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			settingTimeLeftBottom = time;
			break;
			
		case HP_LEFT_CORNER:
			settingTimeLeftTop = time;
			break;
			
		case HP_RIGHT_CORNER:
			settingTimeRightTop = time;
			break;
			
		case HP_BOTTOM_RIGHT:
			settingTimeRightBottom = time;
			break;
			
		default:
			settingTimeLeftBottom = time;
			break;
		}
    }

	public void setSelectState(HobPosition selectedHob, int state) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			leftBottomState = state;
			break;
			
		case HP_LEFT_CORNER:
			leftTopState = state;
			break;
			
		case HP_RIGHT_CORNER:
			rightTopState = state;
			break;
			
		case HP_BOTTOM_RIGHT:
			rightBottomState = state;
			break;
			
		default:
			break;
		}
    }

	public void setSelectTime(HobPosition selectedHob, int time) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			timeLeftBottom = time;
			break;
			
		case HP_LEFT_CORNER:
			System.out.println("**** setSelectTime timeLeftTop = " + time);
			timeLeftTop = time;
			break;
			
		case HP_RIGHT_CORNER:
			timeRightTop = time;
			break;
			
		case HP_BOTTOM_RIGHT:
			timeRightBottom = time;
			break;
			
		default:
			break;
		}
    }
    public void setSelectTimeBySettingsTime(HobPosition selectedHob) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			timeLeftBottom = settingTimeLeftBottom;
			break;
			
		case HP_LEFT_CORNER:
			timeLeftTop = settingTimeLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			timeRightTop = settingTimeRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			timeRightBottom = settingTimeRightBottom;
			break;
			
		default:
			break;
		}
    } 
    
	public void setSelectWheelData() {
		int time = 0; 
		int power = 0;
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			time = settingTimeLeftBottom;
			power = settingPowerLeftBottom;
			break;
			
		case HP_LEFT_CORNER:
			time = settingTimeLeftTop;
			power = settingPowerLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			time = settingTimeRightTop;
			power = settingPowerRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			time = settingTimeRightBottom;
			power = settingPowerRightBottom;
			break;
			
		default:
			time = settingTimeLeftBottom;
			power = settingPowerLeftBottom;
			break;
		}
		setWheelData(time, power);
    }
	public void setTimeWheelStateByPower(){
		if(getSelectSettingsPower(selectedHob) == 0){
			timeWheel.setOnTouchListener(new DisableOnTouchListener());
			timeWheel.setCurrentItem(0);
			timeWheel.setNumColor(AppConstant.NUM_GRAY);
			if(!mIsPad) {
				timeView.setBackgroundResource(R.drawable.hob_time_wheel_bg_dis);
	    	} else {
	    		timeViewBg.setBackgroundResource(R.drawable.hob_time_wheel_bg_dis);
	    	}
		}else if(getSelectState(selectedHob) == AppConstant.COOKING_RUN){
			timeWheel.setOnTouchListener(new DisableOnTouchListener());
			timeWheel.setNumColor(AppConstant.NUM_GRAY);
			if(!mIsPad) {
				timeView.setBackgroundResource(R.drawable.hob_time_wheel_bg_dis);
	    	} else {
	    		timeViewBg.setBackgroundResource(R.drawable.hob_time_wheel_bg_dis);
	    	}
		}else{
			timeWheel.setOnTouchListener(new EnableOnTouchListener());
			timeWheel.setNumColor(AppConstant.NUM_GREEN);
			if(!mIsPad) {
				timeView.setBackgroundResource(R.drawable.hob_time_wheel_bg_nor);
	    	} else {
	    		timeViewBg.setBackgroundResource(R.drawable.hob_time_wheel_bg_nor);
	    	}
		}
	}
	
	public void setTimeWheelView(int time){
    	timeWheel.setAdapter(new NumericWheelAdapter(0, 99, 1, "%02d"), AppConstant.NUM_GREEN);
    	timeWheel.setLabel("mins");
    	timeWheel.setLabelIcon(R.drawable.font_min);
    	timeWheel.setCyclic(true);
	
    	timeWheel.setCurrentItem(time);
    	timeWheel.addChangingListener(wheelListener);
    	timeWheel.addScrollingListener(wheelScrollListener);
		
		rightTitle.setText("TIME");
		setTimeWheelStateByPower();
    }
	public void setWheelData(int time, int power) {
		setPowerWheelView(power);
		setTimeWheelView(time);
    }
    public void setWorkState(int state) {
		workingState = state;
    } 
    
    private void startAllRunTimer() {
		if(getSelectState(HobPosition.HP_LEFT_CORNER) == AppConstant.COOKING_RUN){
			mHandler.removeCallbacks(timerTask1);
			mHandler.postDelayed(timerTask1, AppConstant.HOB_START_TIME);
		}
		if(getSelectState(HobPosition.HP_RIGHT_CORNER) == AppConstant.COOKING_RUN){
			mHandler.removeCallbacks(timerTask2);
			mHandler.postDelayed(timerTask2, AppConstant.HOB_START_TIME);
		}
		if(getSelectState(HobPosition.HP_BOTTOM_LEFT) == AppConstant.COOKING_RUN){
			mHandler.removeCallbacks(timerTask3);
			mHandler.postDelayed(timerTask3, AppConstant.HOB_START_TIME);
		}
		if(getSelectState(HobPosition.HP_BOTTOM_RIGHT) == AppConstant.COOKING_RUN){
			mHandler.removeCallbacks(timerTask4);
			mHandler.postDelayed(timerTask4, AppConstant.HOB_START_TIME);
		}
    } 
    
    private void startAppointTimer(HobPosition selectedHob) {
		setMode(selectedHob);
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			mHandler.removeCallbacks(timerTask3);
			mHandler.postDelayed(timerTask3, AppConstant.HOB_START_TIME);
			break;
			
		case HP_LEFT_CORNER:
			mHandler.removeCallbacks(timerTask1);
			mHandler.postDelayed(timerTask1, AppConstant.HOB_START_TIME);
			break;
			
		case HP_RIGHT_CORNER:
			mHandler.removeCallbacks(timerTask2);
			mHandler.postDelayed(timerTask2, AppConstant.HOB_START_TIME);
			break;
			
		case HP_BOTTOM_RIGHT:
			mHandler.removeCallbacks(timerTask4);
			mHandler.postDelayed(timerTask4, AppConstant.HOB_START_TIME);
			break;
			
		default:
			break;
		}
    } 
    
    public void updateAllHobView(){
    	updateAppointHobView(HobPosition.HP_BOTTOM_LEFT);
    	updateAppointHobView(HobPosition.HP_LEFT_CORNER);
    	updateAppointHobView(HobPosition.HP_RIGHT_CORNER);
    	updateAppointHobView(HobPosition.HP_BOTTOM_RIGHT);
    } 

	
	public void updateAppointHobView(HobPosition hobPosition){
		switch(hobPosition) {
		case HP_BOTTOM_LEFT:
			if(workingState == AppConstant.COOKING_RUN){
				if(leftBottomState == AppConstant.COOKING_RUN && hobPosition == selectedHob){
			    	leftbottom.setBackgroundResource(R.drawable.display_box);
			    	leftbottommin1.setImageResource(AppConstant.hobWhiteTimeNum[timeLeftBottom/10]);
			    	leftbottommin2.setImageResource(AppConstant.hobWhiteTimeNum[timeLeftBottom%10]);
			    	leftbottomsign.setImageResource(AppConstant.hobWhiteTimeNum[10]);
			    	leftbottomsec1.setImageResource(AppConstant.hobWhiteTimeNum[secLeftBottom/10]);
			    	leftbottomsec2.setImageResource(AppConstant.hobWhiteTimeNum[secLeftBottom%10]);
			    	leftbottompowernum.setImageResource(AppConstant.hobWhitePowerNum[powerLeftBottom]);
			    	leftbottompowerimg.setImageResource(AppConstant.hobWhitePowerImg[powerLeftBottom]);
				}else if(leftBottomState == AppConstant.COOKING_RUN && hobPosition != selectedHob){
					leftbottom.setBackgroundResource(0);
					leftbottommin1.setImageResource(AppConstant.hobGreenTimeNum[timeLeftBottom/10]);
					leftbottommin2.setImageResource(AppConstant.hobGreenTimeNum[timeLeftBottom%10]);
					leftbottomsign.setImageResource(AppConstant.hobGreenTimeNum[10]);
					leftbottomsec1.setImageResource(AppConstant.hobGreenTimeNum[secLeftBottom/10]);
					leftbottomsec2.setImageResource(AppConstant.hobGreenTimeNum[secLeftBottom%10]);
					leftbottompowernum.setImageResource(AppConstant.hobWhitePowerNum[powerLeftBottom]);
					leftbottompowerimg.setImageResource(AppConstant.hobWhitePowerImg[powerLeftBottom]);
				}else if(leftBottomState != AppConstant.COOKING_RUN && hobPosition == selectedHob){
					leftbottom.setBackgroundResource(R.drawable.display_box);
					leftbottommin1.setImageResource(AppConstant.hobGrayTimeNum[timeLeftBottom/10]);
					leftbottommin2.setImageResource(AppConstant.hobGrayTimeNum[timeLeftBottom%10]);
					leftbottomsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
					leftbottomsec1.setImageResource(AppConstant.hobGrayTimeNum[secLeftBottom/10]);
					leftbottomsec2.setImageResource(AppConstant.hobGrayTimeNum[secLeftBottom%10]);
					leftbottompowernum.setImageResource(AppConstant.hobSelGrayPowerNum[powerLeftBottom]);
					leftbottompowerimg.setImageResource(AppConstant.hobSelGrayPowerImg[powerLeftBottom]);
				}else if(leftBottomState != AppConstant.COOKING_RUN && hobPosition != selectedHob){
					leftbottom.setBackgroundResource(0);
					leftbottommin1.setImageResource(AppConstant.hobGrayTimeNum[timeLeftBottom/10]);
					leftbottommin2.setImageResource(AppConstant.hobGrayTimeNum[timeLeftBottom%10]);
					leftbottomsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
					leftbottomsec1.setImageResource(AppConstant.hobGrayTimeNum[secLeftBottom/10]);
					leftbottomsec2.setImageResource(AppConstant.hobGrayTimeNum[secLeftBottom%10]);
					leftbottompowernum.setImageResource(AppConstant.hobGrayPowerNum[powerLeftBottom]);
					leftbottompowerimg.setImageResource(AppConstant.hobGrayPowerImg[powerLeftBottom]);
				}
			}else{
				leftbottom.setBackgroundResource(0);
				leftbottommin1.setImageResource(AppConstant.hobGrayTimeNum[timeLeftTop/10]);
				leftbottommin2.setImageResource(AppConstant.hobGrayTimeNum[timeLeftTop%10]);
				leftbottomsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
				leftbottomsec1.setImageResource(AppConstant.hobGrayTimeNum[secLeftTop/10]);
				leftbottomsec2.setImageResource(AppConstant.hobGrayTimeNum[secLeftTop%10]);
				leftbottompowernum.setImageResource(AppConstant.hobGrayPowerNum[powerLeftTop]);
				leftbottompowerimg.setImageResource(AppConstant.hobGrayPowerImg[powerLeftTop]);
			}

			break;
			
		case HP_LEFT_CORNER:
			if(workingState == AppConstant.COOKING_RUN){
				if(leftTopState == AppConstant.COOKING_RUN && hobPosition == selectedHob){
			    	lefttop.setBackgroundResource(R.drawable.display_box);
			    	lefttopmin1.setImageResource(AppConstant.hobWhiteTimeNum[timeLeftTop/10]);
			    	lefttopmin2.setImageResource(AppConstant.hobWhiteTimeNum[timeLeftTop%10]);
			    	lefttopsign.setImageResource(AppConstant.hobWhiteTimeNum[10]);
			    	lefttopsec1.setImageResource(AppConstant.hobWhiteTimeNum[secLeftTop/10]);
			    	lefttopsec2.setImageResource(AppConstant.hobWhiteTimeNum[secLeftTop%10]);
			    	lefttoppowernum.setImageResource(AppConstant.hobWhitePowerNum[powerLeftTop]);
			    	lefttoppowerimg.setImageResource(AppConstant.hobWhitePowerImg[powerLeftTop]);
				}else if(leftTopState == AppConstant.COOKING_RUN && hobPosition != selectedHob){
			    	lefttop.setBackgroundResource(0);
			    	lefttopmin1.setImageResource(AppConstant.hobGreenTimeNum[timeLeftTop/10]);
			    	lefttopmin2.setImageResource(AppConstant.hobGreenTimeNum[timeLeftTop%10]);
			    	lefttopsign.setImageResource(AppConstant.hobGreenTimeNum[10]);
			    	lefttopsec1.setImageResource(AppConstant.hobGreenTimeNum[secLeftTop/10]);
			    	lefttopsec2.setImageResource(AppConstant.hobGreenTimeNum[secLeftTop%10]);
			    	lefttoppowernum.setImageResource(AppConstant.hobWhitePowerNum[powerLeftTop]);
			    	lefttoppowerimg.setImageResource(AppConstant.hobWhitePowerImg[powerLeftTop]);
				}else if(leftTopState != AppConstant.COOKING_RUN && hobPosition == selectedHob){
			    	lefttop.setBackgroundResource(R.drawable.display_box);
			    	lefttopmin1.setImageResource(AppConstant.hobGrayTimeNum[timeLeftTop/10]);
			    	lefttopmin2.setImageResource(AppConstant.hobGrayTimeNum[timeLeftTop%10]);
			    	lefttopsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
			    	lefttopsec1.setImageResource(AppConstant.hobGrayTimeNum[secLeftTop/10]);
			    	lefttopsec2.setImageResource(AppConstant.hobGrayTimeNum[secLeftTop%10]);
			    	lefttoppowernum.setImageResource(AppConstant.hobSelGrayPowerNum[powerLeftTop]);
			    	lefttoppowerimg.setImageResource(AppConstant.hobSelGrayPowerImg[powerLeftTop]);
				}else if(leftTopState != AppConstant.COOKING_RUN && hobPosition != selectedHob){
			    	lefttop.setBackgroundResource(0);
			    	lefttopmin1.setImageResource(AppConstant.hobGrayTimeNum[timeLeftTop/10]);
			    	lefttopmin2.setImageResource(AppConstant.hobGrayTimeNum[timeLeftTop%10]);
			    	lefttopsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
			    	lefttopsec1.setImageResource(AppConstant.hobGrayTimeNum[secLeftTop/10]);
			    	lefttopsec2.setImageResource(AppConstant.hobGrayTimeNum[secLeftTop%10]);
			    	lefttoppowernum.setImageResource(AppConstant.hobGrayPowerNum[powerLeftTop]);
			    	lefttoppowerimg.setImageResource(AppConstant.hobGrayPowerImg[powerLeftTop]);
				}
			}else{
		    	lefttop.setBackgroundResource(0);
		    	lefttopmin1.setImageResource(AppConstant.hobGrayTimeNum[timeLeftTop/10]);
		    	lefttopmin2.setImageResource(AppConstant.hobGrayTimeNum[timeLeftTop%10]);
		    	lefttopsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
		    	lefttopsec1.setImageResource(AppConstant.hobGrayTimeNum[secLeftTop/10]);
		    	lefttopsec2.setImageResource(AppConstant.hobGrayTimeNum[secLeftTop%10]);
		    	lefttoppowernum.setImageResource(AppConstant.hobGrayPowerNum[powerLeftTop]);
		    	lefttoppowerimg.setImageResource(AppConstant.hobGrayPowerImg[powerLeftTop]);
			}
			break;
			
		case HP_RIGHT_CORNER:
			
			if(workingState == AppConstant.COOKING_RUN){
				if(rightTopState == AppConstant.COOKING_RUN && hobPosition == selectedHob){
			    	righttop.setBackgroundResource(R.drawable.display_box);
			    	righttopmin1.setImageResource(AppConstant.hobWhiteTimeNum[timeRightTop/10]);
			    	righttopmin2.setImageResource(AppConstant.hobWhiteTimeNum[timeRightTop%10]);
			    	righttopsign.setImageResource(AppConstant.hobWhiteTimeNum[10]);
			    	righttopsec1.setImageResource(AppConstant.hobWhiteTimeNum[secRightTop/10]);
			    	righttopsec2.setImageResource(AppConstant.hobWhiteTimeNum[secRightTop%10]);
			    	righttoppowernum.setImageResource(AppConstant.hobWhitePowerNum[powerRightTop]);
			    	righttoppowerimg.setImageResource(AppConstant.hobWhitePowerImg[powerRightTop]);
				}else if(rightTopState == AppConstant.COOKING_RUN && hobPosition != selectedHob){
					righttop.setBackgroundResource(0);
					righttopmin1.setImageResource(AppConstant.hobGreenTimeNum[timeRightTop/10]);
					righttopmin2.setImageResource(AppConstant.hobGreenTimeNum[timeRightTop%10]);
					righttopsign.setImageResource(AppConstant.hobGreenTimeNum[10]);
					righttopsec1.setImageResource(AppConstant.hobGreenTimeNum[secRightTop/10]);
					righttopsec2.setImageResource(AppConstant.hobGreenTimeNum[secRightTop%10]);
					righttoppowernum.setImageResource(AppConstant.hobWhitePowerNum[powerRightTop]);
					righttoppowerimg.setImageResource(AppConstant.hobWhitePowerImg[powerRightTop]);
				}else if(rightTopState != AppConstant.COOKING_RUN && hobPosition == selectedHob){
					righttop.setBackgroundResource(R.drawable.display_box);
					righttopmin1.setImageResource(AppConstant.hobGrayTimeNum[timeRightTop/10%10]);
					righttopmin2.setImageResource(AppConstant.hobGrayTimeNum[timeRightTop%10]);
					righttopsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
					righttopsec1.setImageResource(AppConstant.hobGrayTimeNum[secRightTop/10%10]);
					righttopsec2.setImageResource(AppConstant.hobGrayTimeNum[secRightTop%10]);
					righttoppowernum.setImageResource(AppConstant.hobSelGrayPowerNum[powerRightTop]);
					righttoppowerimg.setImageResource(AppConstant.hobSelGrayPowerImg[powerRightTop]);
				}else if(rightTopState != AppConstant.COOKING_RUN && hobPosition != selectedHob){
					righttop.setBackgroundResource(0);
					righttopmin1.setImageResource(AppConstant.hobGrayTimeNum[timeRightTop/10%10]);
					righttopmin2.setImageResource(AppConstant.hobGrayTimeNum[timeRightTop%10]);
					righttopsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
					righttopsec1.setImageResource(AppConstant.hobGrayTimeNum[secRightTop/10]);
					righttopsec2.setImageResource(AppConstant.hobGrayTimeNum[secRightTop%10]);
					righttoppowernum.setImageResource(AppConstant.hobGrayPowerNum[powerRightTop]);
					righttoppowerimg.setImageResource(AppConstant.hobGrayPowerImg[powerRightTop]);
				}
			}else{
				righttop.setBackgroundResource(0);
				righttopmin1.setImageResource(AppConstant.hobGrayTimeNum[timeRightTop/10%10]);
				righttopmin2.setImageResource(AppConstant.hobGrayTimeNum[timeRightTop%10]);
				righttopsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
				righttopsec1.setImageResource(AppConstant.hobGrayTimeNum[secRightTop/10%10]);
				righttopsec2.setImageResource(AppConstant.hobGrayTimeNum[secRightTop%10]);
				righttoppowernum.setImageResource(AppConstant.hobGrayPowerNum[powerRightTop]);
				righttoppowerimg.setImageResource(AppConstant.hobGrayPowerImg[powerRightTop]);
			}
			
			break;
			
		case HP_BOTTOM_RIGHT:
			if(workingState == AppConstant.COOKING_RUN){
				if(rightBottomState == AppConstant.COOKING_RUN && hobPosition == selectedHob){
			    	rightbottom.setBackgroundResource(R.drawable.display_box);
			    	rightbottommin1.setImageResource(AppConstant.hobWhiteTimeNum[timeRightBottom/10]);
			    	rightbottommin2.setImageResource(AppConstant.hobWhiteTimeNum[timeRightBottom%10]);
			    	rightbottomsign.setImageResource(AppConstant.hobWhiteTimeNum[10]);
			    	rightbottomsec1.setImageResource(AppConstant.hobWhiteTimeNum[secRightBottom/10]);
			    	rightbottomsec2.setImageResource(AppConstant.hobWhiteTimeNum[secRightBottom%10]);
			    	rightbottompowernum.setImageResource(AppConstant.hobWhitePowerNum[powerRightBottom]);
			    	rightbottompowerimg.setImageResource(AppConstant.hobWhitePowerImg[powerRightBottom]);
				}else if(rightBottomState == AppConstant.COOKING_RUN && hobPosition != selectedHob){
					rightbottom.setBackgroundResource(0);
					rightbottommin1.setImageResource(AppConstant.hobGreenTimeNum[timeRightBottom/10]);
					rightbottommin2.setImageResource(AppConstant.hobGreenTimeNum[timeRightBottom%10]);
					rightbottomsign.setImageResource(AppConstant.hobGreenTimeNum[10]);
					rightbottomsec1.setImageResource(AppConstant.hobGreenTimeNum[secRightBottom/10]);
					rightbottomsec2.setImageResource(AppConstant.hobGreenTimeNum[secRightBottom%10]);
					rightbottompowernum.setImageResource(AppConstant.hobWhitePowerNum[powerRightBottom]);
					rightbottompowerimg.setImageResource(AppConstant.hobWhitePowerImg[powerRightBottom]);
				}else if(rightBottomState != AppConstant.COOKING_RUN && hobPosition == selectedHob){
					rightbottom.setBackgroundResource(R.drawable.display_box);
					rightbottommin1.setImageResource(AppConstant.hobGrayTimeNum[timeRightBottom/10]);
					rightbottommin2.setImageResource(AppConstant.hobGrayTimeNum[timeRightBottom%10]);
					rightbottomsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
					rightbottomsec1.setImageResource(AppConstant.hobGrayTimeNum[secRightBottom/10]);
					rightbottomsec2.setImageResource(AppConstant.hobGrayTimeNum[secRightBottom%10]);
					rightbottompowernum.setImageResource(AppConstant.hobSelGrayPowerNum[powerRightBottom]);
					rightbottompowerimg.setImageResource(AppConstant.hobSelGrayPowerImg[powerRightBottom]);
				}else if(rightBottomState != AppConstant.COOKING_RUN && hobPosition != selectedHob){
					rightbottom.setBackgroundResource(0);
					rightbottommin1.setImageResource(AppConstant.hobGrayTimeNum[timeRightBottom/10]);
					rightbottommin2.setImageResource(AppConstant.hobGrayTimeNum[timeRightBottom%10]);
					rightbottomsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
					rightbottomsec1.setImageResource(AppConstant.hobGrayTimeNum[secRightBottom/10]);
					rightbottomsec2.setImageResource(AppConstant.hobGrayTimeNum[secRightBottom%10]);
					rightbottompowernum.setImageResource(AppConstant.hobGrayPowerNum[powerRightBottom]);
					rightbottompowerimg.setImageResource(AppConstant.hobGrayPowerImg[powerRightBottom]);
				}
			}else{
				rightbottom.setBackgroundResource(0);
				rightbottommin1.setImageResource(AppConstant.hobGrayTimeNum[timeRightBottom/10]);
				rightbottommin2.setImageResource(AppConstant.hobGrayTimeNum[timeRightBottom%10]);
				rightbottomsign.setImageResource(AppConstant.hobGrayTimeNum[10]);
				rightbottomsec1.setImageResource(AppConstant.hobGrayTimeNum[secRightBottom/10]);
				rightbottomsec2.setImageResource(AppConstant.hobGrayTimeNum[secRightBottom%10]);
				rightbottompowernum.setImageResource(AppConstant.hobGrayPowerNum[powerRightBottom]);
				rightbottompowerimg.setImageResource(AppConstant.hobGrayPowerImg[powerRightBottom]);
			}

			break;
			
		default:
			break;
		}
    }
	
	public void updateSelectSettingsData(HobPosition selectedHob) {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			settingTimeLeftBottom = timeLeftBottom;
			settingPowerLeftBottom = powerLeftBottom;
			break;
			
		case HP_LEFT_CORNER:
			settingTimeLeftTop = timeLeftTop;
			settingPowerLeftTop = powerLeftTop;
			break;
			
		case HP_RIGHT_CORNER:
			settingTimeRightTop = timeRightTop;
			settingPowerRightTop = powerRightTop;
			break;
			
		case HP_BOTTOM_RIGHT:
			settingTimeRightBottom = timeRightBottom;
			settingPowerRightBottom = powerRightBottom;
			break;
			
		default:
			settingTimeLeftBottom = timeLeftBottom;
			settingPowerLeftBottom = powerLeftBottom;
			break;
		}
    }
	
	public void updateSelectTimeByMode() {
		switch(selectedHob) {
		case HP_BOTTOM_LEFT:
			if(leftBottomMode == AppConstant.HOB_MODE_B){
				timeLeftBottom = remainTimeLeftBottom;
			}
			
			break;
			
		case HP_LEFT_CORNER:
			if(leftTopMode == AppConstant.HOB_MODE_B){
				System.out.println("**** updateSelectTimeByMode timeLeftTop = " + remainTimeLeftTop);
				timeLeftTop = remainTimeLeftTop;
			}

			break;
			
		case HP_RIGHT_CORNER:
			if(rightTopMode == AppConstant.HOB_MODE_B){
				timeRightTop = remainTimeRightTop;
			}

			break;
			
		case HP_BOTTOM_RIGHT:
			if(rightBottomMode == AppConstant.HOB_MODE_B){
				timeRightBottom = remainTimeRightBottom;
			}

			break;
			
		default:
			break;
		}
    }
}