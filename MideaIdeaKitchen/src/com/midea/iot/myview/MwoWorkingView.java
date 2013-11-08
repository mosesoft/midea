package com.midea.iot.myview;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Service;
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
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
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
import com.midea.iot.activities.AppStatic;
import com.midea.iot.activities.MainActivity;
import com.midea.iot.activities.MyApplication;
import com.midea.iot.devices.Microwave;
import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.MicroWaveStatus;
import com.midea.iot.wheelview.NumericWheelAdapter;
import com.midea.iot.wheelview.OnWheelChangedListener;
import com.midea.iot.wheelview.OnWheelScrollListener;
import com.midea.iot.wheelview.WheelView;

public class MwoWorkingView {
	class CookingBtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("*****BtnOnClickListener");
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.add:
				//MainActivity.getInstance().getOvenDevice().stop();
				//myApplication.remove();
				//ctx.finish();
				if(workingState != AppConstant.COOKING_PAUSE){
					settingsTime += 30;
					remainTime += 30;
					setTimeView(remainTime);
					microwave.add30Second();
				}else{
					settingsTime += 30;
					remainTime += 30;
					setTimeView(remainTime);
				}
				break;

			case R.id.ok:
				microwave.stop(getCookingDataByMode(settingsMode));
				//myApplication.remove();
				//ctx.finish();
				displaySetttingsView(false);
				hideFinishView();
				workingState = AppConstant.COOKING_WAIT;
				break;

			case R.id.stop:
				workingState = AppConstant.COOKING_WAIT;
				stateTxt.setText(AppConstant.stateArray[workingState]);
				pause.setImageResource(R.drawable.startbtn);
				microwave.stop(getCookingDataByMode(settingsMode));
				displaySetttingsView(false);
                count = 0;  
            	stepCount = 0;
            	progress = 0;
				break;

			case R.id.pause:
				if(workingState == AppConstant.COOKING_RUN){
					workingState = AppConstant.COOKING_PAUSE;
					pause.setImageResource(R.drawable.startbtn);
					microwave.pause(getCookingData());
				}else{
					workingState = AppConstant.COOKING_RUN;
					pause.setImageResource(R.drawable.pausebtn);
					mHandler.post(timerTask);
					microwave.startWork(getCookingData());
				}
				stateTxt.setText(AppConstant.stateArray[workingState]);
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
	class ImageBtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("*****ImageOnClickListener");
			ImageView powerBtn = null;
			setAllPowerBtnNormal();

			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.power1:
				power = AppConstant.POWER_1;
				powerBtn = power1;
				break;
			case R.id.power2:
				power = AppConstant.POWER_2;
				powerBtn = power2;
				break;
			case R.id.power3:
				power = AppConstant.POWER_3;
				powerBtn = power3;
				break;
			case R.id.power4:
				power = AppConstant.POWER_4;
				powerBtn = power4;
				break;
			case R.id.power5:
				power = AppConstant.POWER_5;
				powerBtn = power5;
				break;

			default:
				//mode = AppConstant.MODE_1;
				break;
			}

			setSelPowerView(powerBtn, power);
			//getDataByMode(mode);
		}

	}
	/*public void startCookingActivity(int id, int mode, int time, int temp, int power, int weight){
		Intent intent = new Intent();
		intent.setClass(ctx, CookingActivity.class);
		intent.putExtra("ID", id);
		intent.putExtra("MODE", mode);
		intent.putExtra("TIME", time);
		intent.putExtra("TEMP", temp);
		intent.putExtra("POWER", power);
		intent.putExtra("WEIGHT", weight);
		//����Activity
		ctx.startActivity(intent);
	}*/
	public class MwoViewPager extends MyViewPager{

		class ImageOnClickListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				System.out.println("*****ImageOnClickListener");
				ImageView modeIcon = null;
				setAllIconNormal();

				// TODO Auto-generated method stub
				switch(v.getId()){
				case R.id.mode1:
					mode = AppConstant.MODE_1;
					modeIcon = mode1;
					break;
				case R.id.mode2:
					mode = AppConstant.MODE_2;
					modeIcon = mode2;
					break;
				case R.id.mode3:
					mode = AppConstant.MODE_3;
					modeIcon = mode3;
					break;
				case R.id.mode4:
					mode = AppConstant.MODE_4;
					modeIcon = mode4;
					break;
				case R.id.mode5:
					mode = AppConstant.MODE_5;
					modeIcon = mode5;
					break;

				default:
					mode = AppConstant.MODE_1;
					modeIcon = mode1;
					break;
				}
				setMode(mode);
				setSelModeView(modeName, modeIcon, mode);
				getCookingDataByMode(mode);
				setWheelData(mode);

			}

		}
		private ImageView mode1 = null;
		private ImageView mode2 = null;
		private ImageView mode3 = null;
		private ImageView mode4 = null;
		
		


		private ImageView mode5 = null;

		public MwoViewPager(Context ctx, LinearLayout parent, MyApplication myApplication, int id, int mode) {
			super(ctx, parent, myApplication, id, mode);
			
			initViewPager();
		}

		public void initModeView() {
			// TODO Auto-generated method stub
			//super.initModeView();
			LayoutInflater mInflater = LayoutInflater.from(ctx);
			View view1 = mInflater.inflate(R.layout.mode_page1, null);
			View view2 = mInflater.inflate(R.layout.mode_page2, null);
			
			mode1 = (ImageView)view1.findViewById(R.id.mode1);
			mode2 = (ImageView)view1.findViewById(R.id.mode2);
			mode3 = (ImageView)view1.findViewById(R.id.mode3);
			mode4 = (ImageView)view1.findViewById(R.id.mode4);
			if(isPad) {
				mode5 = (ImageView)view1.findViewById(R.id.mode5);
			} else {
				mode5 = (ImageView)view2.findViewById(R.id.mode5);
			}
			setAllIconNormal();
			
			listViews.add(view1);
			
			if(!isPad) {
				listViews.add(view2);
			}
			
			setAllIconNormal();
			
			mPager.setAdapter(new MyPagerAdapter(listViews));
			mPager.setOnPageChangeListener(new MyViewPagerOnPageChangeListener());
			mPager.setCurrentItem(0);
			mode1.setOnClickListener(new ImageOnClickListener());
			mode2.setOnClickListener(new ImageOnClickListener());
			mode3.setOnClickListener(new ImageOnClickListener());
			mode4.setOnClickListener(new ImageOnClickListener());
			mode5.setOnClickListener(new ImageOnClickListener());
		}

		private void initViewPager() {
			mPager = (ViewPager) parent.findViewById(R.id.viewpager);
			modeName = (TextView) parent.findViewById(R.id.modeName);
			listViews = new ArrayList<View>();
			initModeView();

			mIndicator = (ImageView)parent.findViewById(R.id.indicator);
			mIndicator.setImageResource(R.drawable.page1);

			setSelModeView(modeName, mode1, AppConstant.MODE_1);

			//getDataByMode(AppConstant.MODE_1);
		}

		public void setAllIconNormal(){
			mode1.setImageResource(AppConstant.mwo_mode_nor[0]);
			mode2.setImageResource(AppConstant.mwo_mode_nor[1]);
			mode3.setImageResource(AppConstant.mwo_mode_nor[2]);
			mode4.setImageResource(AppConstant.mwo_mode_nor[3]);
			mode5.setImageResource(AppConstant.mwo_mode_nor[4]);
		}

		@Override
		public void setModeClickable(boolean clickable) {
			// TODO Auto-generated method stub
			mode1.setClickable(clickable);
			mode2.setClickable(clickable);
			mode3.setClickable(clickable);
			mode4.setClickable(clickable);
			mode5.setClickable(clickable);

			if(clickable){
				mPager.setOnTouchListener(new EnableOnTouchListener());
			}else{
				mPager.setOnTouchListener(new DisableOnTouchListener());
			}
		}
		@Override
		public void setModeIcon(ImageView view, int mode, boolean isSelect) {
			// TODO Auto-generated method stub
			//super.setModeIcon(view, mode, isSelect);
			if(isSelect){
				view.setImageResource(AppConstant.mwo_mode_sel[mode]);
			}else{
				view.setImageResource(AppConstant.mwo_mode_nor[mode]);
			}
		}
		@Override
		public void setSelModeView(TextView modeName, ImageView view, int mode) {
			// TODO Auto-generated method stub
			//super.setSelModeView(modeName, view, mode);
			modeName.setText(AppConstant.mwo_mode_name[mode]);
			setModeIcon(view, mode, true);
		}



		public void updateSettingsMode(int mode, boolean ispause) {
			// TODO Auto-generated method stub
			setAllIconNormal();
			ImageView modeIcon = null;
			setMode(mode);

			switch(mode){
			case AppConstant.MODE_1:
				//mode = AppConstant.MODE_1;
				modeIcon = mode1;
				break;
			case AppConstant.MODE_2:
				//mode = AppConstant.MODE_2;
				modeIcon = mode2;
				break;
			case AppConstant.MODE_3:
				//mode = AppConstant.MODE_3;
				modeIcon = mode3;
				break;
			case AppConstant.MODE_4:
				//mode = AppConstant.MODE_4;
				modeIcon = mode4;
				break;
			case AppConstant.MODE_5:
				//mode = AppConstant.MODE_5;
				modeIcon = mode5;
				break;

			default:
				//mode = AppConstant.MODE_1;
				modeIcon = mode1;
				break;
			}
			setSelModeView(modeName, modeIcon, mode);
			if(!ispause){
				getDefaultSettingsDataByMode(mode);
			}else{
				getSettingsDataByMode(mode);
			}

			setWheelData(mode);

		}
	}
	class SettingsBtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("*****BtnOnClickListener");
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.startbtn:
				System.out.println("**** OvenSettingsView id = " + id + ", mode = " + settingsMode + ", time = " + settingsTime + ", temp = " + settingsTemp);
				MicroWaveStatus os = null;
				if(settingsMode == AppConstant.MODE_1){
					os = new MicroWaveStatus(settingsMode+1, settingsTime, settingsTemp, power + 1, weight);
				}else{
					os = new MicroWaveStatus(settingsMode+1, settingsTime, settingsTemp, 0, weight);
				}
				Microwave microwave = (Microwave)MainActivity.getInstance().getDevice(BaseProtocol.TYPE_MICROWAVE);
				microwave.startWork(os);
				workingState = AppConstant.COOKING_RUN;
				displayCookingView();
				break;
			default:

				break;
			}
		}

	}
	MyApplication myApplication;
	Context ctx;
	FrameLayout parent;
	Handler mHandler;
	int workingState;
	int id;
	int settingsMode;

	private FrameLayout mwosettings = null;
	private FrameLayout mwocooking = null;
	public int settingsTime = 0;

	public int min_time = 0;
	public int max_time = 0;
	public int settingsTemp = 0;


	public int min_secs = 0;
	public int max_secs = 0;
	public int secs_step = 1;
	public  int min_temp = 0;
	public int max_temp = 0;


	public int temp_step = 0;
	public int min_weight = 100;
	public int max_weight = 2000;
	public int weight_step = 100;
	private MwoViewPager myViewPager = null;
	private LinearLayout myviewpager = null;

	private ImageButton startbtn = null;
	private TextView leftTitle = null;
	private TextView rightTitle = null;
	private LinearLayout timewheel = null;
	private LinearLayout tempwheel = null;

	private LinearLayout weightwheel = null;

	private ImageButton power1 = null;
	private ImageButton power2 = null;

	private ImageButton power3 = null;
	private ImageButton power4 = null;

	private ImageButton power5 = null;

	private FrameLayout timeext = null;

	private LinearLayout tempview = null;
	private LinearLayout powerview = null;
	private WheelView mins = null;
	private WheelView secs = null;
	private WheelView weightView = null;
	private WheelView tempView = null;
	public int remainTime = 0;

	public int actualTemp = 0;
	public int actualWeight = 0;

	//public int actualPower = 0;
	public int progress = 0;
	//public int temp = 0;
	public int power = 0;
	public int weight = 0;
	private int progressStep = 0;
	private TextView stateTxt = null;
	private LinearLayout cookingcontent = null;
	//private FrameLayout backview = null;
	private ImageView processcircle = null;
	private ImageView movingcircle = null;
	private LinearLayout cookingview = null;
	private ImageView modeIcon = null;
	private ImageView percent1 = null;

	private ImageView percent2 = null;
	private ImageView percent = null;
	private ImageView titleFont = null;
	private TextView timetxt = null;

	private TextView temptxt = null;
	private ImageView fickerfont = null;
	private ImageView finishcircle = null;
	private LinearLayout finishview = null;
	
	private ImageView finish = null;
	
	private ImageButton ok = null;

	private ImageButton stop = null;

	private ImageButton pause = null;
	private ImageButton add = null; 

	Microwave microwave = null;

	private boolean isPad = false;
	OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if(wheel.equals(mins)){
				settingsTime = settingsTime%60 + newValue*60 ;
			}else if(wheel.equals(secs)){
				settingsTime = (settingsTime/60)*60 + newValue*5;
			}else if(wheel.equals(tempView)){
				settingsTemp = newValue*temp_step + min_temp;
			}else if(wheel.equals(weightView)){
				weight = newValue*weight_step + min_weight;
			}

		}
	};
	OnWheelScrollListener wheelScrollListener = new OnWheelScrollListener() {

		@Override
		public void onScrollingFinished(WheelView wheel) {
			// TODO Auto-generated method stub
			System.out.println("*****OnWheelScrollListener");

			if(wheel.equals(mins)){
				AppStatic.saveSharedPreData(AppConstant.mwo_label[0], Integer.toString(settingsTime), ctx);
				System.out.println("*****OnWheelScrollListener time = " + settingsTime);
			}else if(wheel.equals(secs)){
				AppStatic.saveSharedPreData(AppConstant.mwo_label[0], Integer.toString(settingsTime), ctx);
				System.out.println("*****OnWheelScrollListener time2 = " + settingsTime);
			}else if(wheel.equals(tempView)){
				AppStatic.saveSharedPreData(AppConstant.mwo_label[1], Integer.toString(settingsTemp), ctx);
				System.out.println("*****OnWheelScrollListener temp = " + settingsTemp);
			}else if(wheel.equals(weightView)){
				AppStatic.saveSharedPreData(AppConstant.mwo_label[2], Integer.toString(weight), ctx);
				System.out.println("*****OnWheelScrollListener weight = " + weight);
			}
		}

		@Override
		public void onScrollingStarted(WheelView wheel) {
			// TODO Auto-generated method stub

		}


	};
	Bitmap bitmap = null;
	public final Runnable timerTask = new Runnable() {  

		@Override  
		public void run() {  
			// TODO Auto-generated method stub  
			if (workingState == AppConstant.COOKING_RUN) {  
				System.out.println("*****timerTask");
				mHandler.postDelayed(timerTask, AppConstant.UPDATE_TIME);  
				count++;  
				stepCount++;
				if(count >= AppConstant.MAX_UPDATE_NUM){
					count = 0; 
				}
				if(stepCount >= progressStep){
					stepCount = 0;
					//progress++;
					System.out.println("******OvenCooking progress = " + progress);
					//setPercentView(progress);
				}
				bitmap = createSector(count);
				movingcircle.setImageBitmap(bitmap);

			}  

		}  
	};
	public int count = 0;

	public int stepCount = 0;

	Bitmap mBitmap = null;
	public MwoWorkingView(Context ctx, FrameLayout parent, MyApplication myApplication, int id, int mode, Handler mHandler) {
		super();
		this.ctx = ctx;
		this.parent = parent;
		this.myApplication = myApplication;
		WindowManager wm = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		isPad = width >= 1200;
		
		microwave = MainActivity.getInstance().getMicrowaveDevice();
		this.id = id;
		this.settingsMode = mode;
		this.mHandler = mHandler;
		workingState = microwave.getWorkStatus() - 1;
		if(workingState < AppConstant.COOKING_WAIT){
			workingState = AppConstant.COOKING_WAIT;
		}
		initView();
	}
	public void calcProStep(int time){
		progressStep = time*60*10/AppConstant.UPDATE_TIME;
		System.out.println("******OvenCooking calcProStop progressStep = " + progressStep);
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
	public void displayCookingView(){

	stateTxt.setText(AppConstant.stateArray[workingState]);

	if(workingState == AppConstant.COOKING_RUN){
		pause.setImageResource(R.drawable.pausebtn);
	}else{
		pause.setImageResource(R.drawable.startbtn);
	}
	progress = 0;

	setPercentView(progress);
	setTimeView(remainTime);
	setTempView(actualTemp);
	updateMode(settingsMode);

	if(id == AppConstant.MICROWAVE){
		mHandler.post(timerTask);
		calcProStep(settingsTime);
	}
	mHandler.removeCallbacks(timerTask);
	mHandler.post(timerTask);

	mwosettings.setVisibility(View.INVISIBLE);
	mwocooking.setVisibility(View.VISIBLE);
}
	public void displayFinishView() {
		// TODO Auto-generated method stub
		processcircle.setVisibility(View.INVISIBLE);
		movingcircle.setVisibility(View.INVISIBLE);
		cookingview.setVisibility(View.INVISIBLE);
		finishcircle.setVisibility(View.VISIBLE);
//		cookingcontent.setBackgroundResource(R.drawable.finish_bg);
		finishview.setVisibility(View.VISIBLE);
		finish.setVisibility(View.VISIBLE);
		ok.setVisibility(View.VISIBLE);
		stop.setClickable(false);
		pause.setClickable(false);
		add.setClickable(false);
		stateTxt.setVisibility(View.INVISIBLE);
		mHandler.removeCallbacks(timerTask);
	}
	public void displaySetttingsView(boolean ispause){

		//getSettingsDataByMode(mode);
		myViewPager.updateSettingsMode(settingsMode, ispause);
		mwosettings.setVisibility(View.VISIBLE);
		mwocooking.setVisibility(View.INVISIBLE);
    	if(ispause){
    		myViewPager.setModeClickable(false);
    	}else{
    		myViewPager.setModeClickable(true);
    	}
	}
	private MicroWaveStatus getCookingData() {

		MicroWaveStatus os = null;
		if(settingsMode == AppConstant.MODE_1){
			os = new MicroWaveStatus(settingsMode+1, remainTime, settingsTemp, power + 1, weight);
		}else{
			os = new MicroWaveStatus(settingsMode+1, remainTime, settingsTemp, 0, weight);
		}
		return os;
	}

	private MicroWaveStatus getCookingDataByMode(int mode) {
		settingsTime = Integer.parseInt(AppConstant.microwave_default_data[mode][1]);
		settingsTemp = Integer.parseInt(AppConstant.microwave_default_data[mode][4]);
		min_temp = Integer.parseInt(AppConstant.microwave_default_data[mode][5]);
		max_temp = Integer.parseInt(AppConstant.microwave_default_data[mode][6]);
		temp_step = Integer.parseInt(AppConstant.microwave_default_data[mode][7]);
		
		weight_step = 100;
		min_weight = 100;
		max_weight = 2000;

		weight = Integer.parseInt(AppConstant.microwave_default_data[mode][11]);//setTimeTempView(time,temp);
		power = Integer.parseInt(AppConstant.microwave_default_data[mode][8]);
		MicroWaveStatus os = null;
		if(settingsMode == AppConstant.MODE_1){
			os = new MicroWaveStatus(mode+1, settingsTime, settingsTemp, power + 1, weight);
		}else{
			os = new MicroWaveStatus(mode+1, settingsTime, settingsTemp, 0, weight);
		}
		return os;
	}

	private void getDefaultSettingsDataByMode(int mode) {
		settingsTime = Integer.parseInt(AppConstant.microwave_default_data[mode][1]);
		settingsTemp = Integer.parseInt(AppConstant.microwave_default_data[mode][4]);
		min_temp = Integer.parseInt(AppConstant.microwave_default_data[mode][5]);
		max_temp = Integer.parseInt(AppConstant.microwave_default_data[mode][6]);
		temp_step = Integer.parseInt(AppConstant.microwave_default_data[mode][7]);
		
		weight_step = 100;
		min_weight = 100;
		max_weight = 2000;

		weight = Integer.parseInt(AppConstant.microwave_default_data[mode][11]);//setTimeTempView(time,temp);
		power = Integer.parseInt(AppConstant.microwave_default_data[mode][8]);
	}

	private void getSettingsDataByMode(int mode) {
		settingsTime = remainTime;
		//setTimeTempView(settingsTime, settingsTemp);
	}

	public int getWorkingState() {
		// TODO Auto-generated method stub
		return workingState;
	}

	public void hideFinishView() {
		// TODO Auto-generated method stub
		processcircle.setVisibility(View.VISIBLE);
		movingcircle.setVisibility(View.VISIBLE);
		cookingview.setVisibility(View.VISIBLE);
		finishcircle.setVisibility(View.INVISIBLE);
//		cookingcontent.setBackgroundResource(R.drawable.basicbg);
		finishview.setVisibility(View.INVISIBLE);
		finish.setVisibility(View.INVISIBLE);
		ok.setVisibility(View.INVISIBLE);
		stop.setClickable(true);
		pause.setClickable(true);
		add.setClickable(true);
		stateTxt.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(timerTask);
	}
	private void initCookingView() {
		mwocooking = (FrameLayout) parent.findViewById(R.id.mwocooking);
		cookingcontent = (LinearLayout)parent.findViewById(R.id.cookingcontent);
		//backview = (FrameLayout)parent.findViewById(R.id.backview);

		stateTxt = (TextView)parent.findViewById(R.id.state);
		stateTxt.setText(AppConstant.stateArray[workingState]);
		processcircle = (ImageView)parent.findViewById(R.id.processcircle);
		movingcircle = (ImageView)parent.findViewById(R.id.movingcircle);
		cookingview = (LinearLayout)parent.findViewById(R.id.cookingview);
		modeIcon = (ImageView)parent.findViewById(R.id.mode);
		percent1 = (ImageView)parent.findViewById(R.id.percent1);
		percent2 = (ImageView)parent.findViewById(R.id.percent2);
		percent = (ImageView)parent.findViewById(R.id.percent);
		titleFont = (ImageView)parent.findViewById(R.id.title_font);
		timetxt = (TextView)parent.findViewById(R.id.timetxt);
		temptxt = (TextView)parent.findViewById(R.id.temptxt);

		finishcircle = (ImageView)parent.findViewById(R.id.finishcircle);
		finishview = (LinearLayout)parent.findViewById(R.id.finishview);
		finish = (ImageView)parent.findViewById(R.id.finish);
		ok = (ImageButton)parent.findViewById(R.id.ok);

		stop = (ImageButton)parent.findViewById(R.id.stop);
		add = (ImageButton)parent.findViewById(R.id.add);
		pause = (ImageButton)parent.findViewById(R.id.pause);

		if(workingState == AppConstant.COOKING_RUN){
			pause.setImageResource(R.drawable.pausebtn);
			//backbtn.setVisibility(View.INVISIBLE);
		}else{
			pause.setImageResource(R.drawable.startbtn);
			//backbtn.setVisibility(View.VISIBLE);
		}
		progress = 0;

		setPercentView(progress);
		setTimeView(remainTime);
		setTempView(actualTemp);
		System.out.println("**** initView mode = " + settingsMode);
		updateMode(settingsMode);
		ok.setOnClickListener(new CookingBtnOnClickListener());
		stop.setOnClickListener(new CookingBtnOnClickListener());
		add.setOnClickListener(new CookingBtnOnClickListener());
		pause.setOnClickListener(new CookingBtnOnClickListener());

		if(id == AppConstant.MICROWAVE){
			mHandler.post(timerTask);
			calcProStep(settingsTime);
		}

	}

	private void initSettingsView() {
		mwosettings = (FrameLayout) parent.findViewById(R.id.mwosettings);
		myviewpager = (LinearLayout) parent.findViewById(R.id.myviewpager);

		myViewPager = new MwoViewPager(ctx, myviewpager, myApplication, id, settingsMode);

		leftTitle = (TextView) parent.findViewById(R.id.lefttitle);
		rightTitle = (TextView) parent.findViewById(R.id.righttitle);
		startbtn = (ImageButton) parent.findViewById(R.id.startbtn);

		timewheel = (LinearLayout) parent.findViewById(R.id.timewheel);
		//timewheel.setVisibility(View.VISIBLE);
		weightwheel = (LinearLayout) parent.findViewById(R.id.weightwheel);
		//weightwheel.setVisibility(View.INVISIBLE);
		timeext = (FrameLayout) parent.findViewById(R.id.timeext);
		//timeext.setVisibility(View.VISIBLE);
		tempview = (LinearLayout) parent.findViewById(R.id.tempview);
		//tempview.setVisibility(View.VISIBLE);
		powerview = (LinearLayout) parent.findViewById(R.id.powerview);

		power1 = (ImageButton) parent.findViewById(R.id.power1);
		power2 = (ImageButton) parent.findViewById(R.id.power2);
		power3 = (ImageButton) parent.findViewById(R.id.power3);
		power4 = (ImageButton) parent.findViewById(R.id.power4);
		power5 = (ImageButton) parent.findViewById(R.id.power5);

		power1.setOnClickListener(new ImageBtnOnClickListener());
		power2.setOnClickListener(new ImageBtnOnClickListener());
		power3.setOnClickListener(new ImageBtnOnClickListener());
		power4.setOnClickListener(new ImageBtnOnClickListener());
		power5.setOnClickListener(new ImageBtnOnClickListener());


		startbtn.setOnClickListener(new SettingsBtnOnClickListener());

		//getDataByMode(mode);
		if(workingState == AppConstant.COOKING_WAIT){
			getDefaultSettingsDataByMode(settingsMode);
		}else{

			String timeStr = AppStatic.getSharedPreData(AppConstant.time_label[AppConstant.MICROWAVE], ctx);
			settingsTime = Integer.parseInt(timeStr);

			setTimeTempView(settingsTime, settingsTemp);
		}
		//initViewPager();
		initWheelView();
	}
	private void initView() {
		initSettingsView();
		initCookingView();
		if(workingState == AppConstant.COOKING_WAIT){
			mwosettings.setVisibility(View.VISIBLE);
			mwocooking.setVisibility(View.INVISIBLE);
		}else{
			mwosettings.setVisibility(View.INVISIBLE);
			mwocooking.setVisibility(View.VISIBLE);
			if(workingState == AppConstant.COOKING_WAIT){
				stateTxt.setText(AppConstant.stateArray[workingState]);
				pause.setImageResource(R.drawable.startbtn);
				//myApplication.remove();
				displaySetttingsView(false);
			}else if(workingState == AppConstant.COOKING_RUN){
				displayCookingView();
				stateTxt.setText(AppConstant.stateArray[workingState]);
				pause.setImageResource(R.drawable.pausebtn);
				mHandler.removeCallbacks(timerTask);
				mHandler.post(timerTask);

			}else if(workingState == AppConstant.COOKING_PAUSE){
				displayCookingView();
				stateTxt.setText(AppConstant.stateArray[workingState]);
				pause.setImageResource(R.drawable.startbtn);
			}else if(workingState == AppConstant.COOKING_FINISH){
				displayCookingView();
				displayFinishView();
			}
		}
	}
	private void initWheelView() {
		mins = (WheelView) parent.findViewById(R.id.mins);
		secs = (WheelView) parent.findViewById(R.id.sec);
		tempView = (WheelView) parent.findViewById(R.id.temp);

		weightView = (WheelView) parent.findViewById(R.id.weight);

		setWheelData(settingsMode);
	}
	//mBitmap.recycle();
	//mBitmap = null;
	//bitmap.recycle();
	//bitmap = null;
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
	public void setAllPowerBtnNormal(){
		power1.setImageResource(R.drawable.num_nor);
		power2.setImageResource(R.drawable.num_nor);
		power3.setImageResource(R.drawable.num_nor);
		power4.setImageResource(R.drawable.num_nor);
		power5.setImageResource(R.drawable.num_nor);
	}

	public void setBtnClickable(boolean clickable) {
		setSettingsBtnClickable(clickable);
		setCookingBtnClickable(clickable);
	}

	public void setCookingBtnClickable(boolean clickable) {
		// TODO Auto-generated method stub
		startbtn.setClickable(clickable);
		myViewPager.setModeClickable(clickable);
		power1.setClickable(clickable);
		power2.setClickable(clickable);
		power3.setClickable(clickable);
		power4.setClickable(clickable);
		power5.setClickable(clickable);
		if(clickable){
			mins.setOnTouchListener(new EnableOnTouchListener());
			secs.setOnTouchListener(new EnableOnTouchListener());
			tempView.setOnTouchListener(new EnableOnTouchListener());
			weightView.setOnTouchListener(new EnableOnTouchListener());
		}else{
			mins.setOnTouchListener(new DisableOnTouchListener());
			secs.setOnTouchListener(new DisableOnTouchListener());
			tempView.setOnTouchListener(new DisableOnTouchListener());
			weightView.setOnTouchListener(new DisableOnTouchListener());
		}
	}
	public void setCookingState(int state) {
		// TODO Auto-generated method stub
		setCookingView(state);
		workingState = state;

	}

	public void setCookingView(int state){
		if(state != workingState){
			if(workingState == AppConstant.COOKING_FINISH){
				displayCookingView();
				processcircle.setVisibility(View.VISIBLE);
				movingcircle.setVisibility(View.VISIBLE);
				cookingview.setVisibility(View.VISIBLE);
				finishcircle.setVisibility(View.INVISIBLE);
//				cookingcontent.setBackgroundResource(R.drawable.basicbg);
				finishview.setVisibility(View.INVISIBLE);
				finish.setVisibility(View.INVISIBLE);
				ok.setVisibility(View.INVISIBLE);
				stop.setClickable(true);
				pause.setClickable(true);
				add.setClickable(true);
				stateTxt.setVisibility(View.VISIBLE);
			}
			if(state == AppConstant.COOKING_WAIT){
				stateTxt.setText(AppConstant.stateArray[state]);
				pause.setImageResource(R.drawable.startbtn);
				//myApplication.remove();
				displaySetttingsView(false);
			}else if(state == AppConstant.COOKING_RUN){
				displayCookingView();
				stateTxt.setText(AppConstant.stateArray[state]);
				pause.setImageResource(R.drawable.pausebtn);
				mHandler.removeCallbacks(timerTask);
				mHandler.post(timerTask);

			}else if(state == AppConstant.COOKING_PAUSE){
				displayCookingView();
				stateTxt.setText(AppConstant.stateArray[state]);
				pause.setImageResource(R.drawable.startbtn);
			}else if(state == AppConstant.COOKING_FINISH){
				displayCookingView();
				displayFinishView();
			}
		}

	}
	public void setDeviceId(int id){
		this.id = id;
	}
	private void setMode(int mode){
		this.settingsMode = mode;
	}	



	public void setPercentView(int percent) {
		if(percent > 99){
			percent = 99;
		}
		progress = percent;
		int num1 = percent/10;
		int num2 = percent%10;
		percent1.setImageResource(AppConstant.digital_large[num1]);
		percent2.setImageResource(AppConstant.digital_large[num2]);
	}
	public void setPowerBtnView(){


		timeext.setVisibility(View.VISIBLE);
		powerview.setVisibility(View.VISIBLE);
		tempview.setVisibility(View.GONE);

		rightTitle.setVisibility(View.VISIBLE);
		rightTitle.setText("POWER");
		setAllPowerBtnNormal();
		setSelPowerView(power5, power);

	}

	public void setPowerIcon(ImageView view, int power, boolean isSelect){
		if(isSelect){
			view.setImageResource(AppConstant.mwo_power_sel[power]);
		}
	}

	public void setRemainTime(int time) {
		remainTime = time;
		//setTimeTempView(settingsTime, settingsTemp);
	}
	public void setSelPowerView(ImageView view, int power){
		setPowerIcon(view, power, true);
	}
	
	public void setSelPowerView(int power){
		ImageView powerBtn = null;
		setAllPowerBtnNormal();
		switch(power){
		case AppConstant.POWER_1:
			powerBtn = power1;
			break;

		case AppConstant.POWER_2:
			powerBtn = power2;
			break;

		case AppConstant.POWER_3:
			powerBtn = power3;;
			break;

		case AppConstant.POWER_4:
			powerBtn = power4;
			break;

		case AppConstant.POWER_5:
			powerBtn = power5;
			break;
		}
		setSelPowerView(powerBtn, power);

	}
	public void setSettingsBtnClickable(boolean clickable) {
		// TODO Auto-generated method stub
		add.setClickable(clickable);
		ok.setClickable(clickable);
		stop.setClickable(clickable);
		pause.setClickable(clickable);
	} 

	public void setSettingsPower(int power) {
		this.power = power;
		//setTimeTempView(settingsTime, settingsTemp);
	}
	public void setSettingsTemp(int temp) {
		settingsTemp = temp;
		//setTimeTempView(settingsTime, settingsTemp);
	} 


	public void setSettingsTime(int time) {
		settingsTime = time;
		//setTimeTempView(settingsTime, settingsTemp);
	}
	public void setTempView(int temp) {
		temptxt.setText(temp + "��");
	}

	public void setTempWheelView(){
		boolean isGreenNumber = false;
		if(isPad) {
			isGreenNumber = true;
		}
		tempView.setAdapter(new NumericWheelAdapter(min_temp, max_temp, temp_step), AppConstant.NUM_WHITE);
		tempView.setLabel("C");
		tempView.setLabelIcon(R.drawable.c);
		tempView.setCyclic(true);
		int idx = 0;
		if(temp_step == 0){
			idx = 0;
		}else{
			idx = (settingsTemp - min_temp)/temp_step;
		}
		
		System.out.println("****setTempWheelView = " + idx);
		tempView.setCurrentItem(idx);
		tempView.addChangingListener(wheelListener);
		tempView.addScrollingListener(wheelScrollListener);

		timeext.setVisibility(View.VISIBLE);
		tempview.setVisibility(View.VISIBLE);
		powerview.setVisibility(View.GONE);

		rightTitle.setVisibility(View.VISIBLE);
		rightTitle.setText("TEMP.");
	}
	public void setTimeTempView(int time, int temp) {

		/*    	int num1 = time/60;
    	int num2 = time%60/10;
    	int num3 = time%60%10;

    	hour.setImageResource(AppConstant.digital_mid[num1]);
    	min1.setImageResource(AppConstant.digital_mid[num2]);
    	min2.setImageResource(AppConstant.digital_mid[num3]);

    	num1 = temp/100;
    	num2 = temp%100/10;
    	num3 = temp%10;

    	temp1.setImageResource(AppConstant.digital_mid[num1]);
    	temp2.setImageResource(AppConstant.digital_mid[num2]);
    	temp3.setImageResource(AppConstant.digital_mid[num3]);
    	System.out.println("******saveSharedPreData time_label = " + AppConstant.time_label[id] + ", time = " +  time);
    	AppStatic.saveSharedPreData(AppConstant.time_label[id], Integer.toString(time), ctx);*/
	}


	public void setTimeView(int time) {
		System.out.println("*****setTimeView time = " + time);
		int num1 = time/60;
		int num2 = time%60/10;
		int num3 = time%60%10;
		String timeStr = num1 + ":" + num2 + num3;
		timetxt.setText(timeStr);
	}

	public void setTimeWheelView(){
		mins.setAdapter(new NumericWheelAdapter(0, 94, 1, "%02d"), AppConstant.NUM_GREEN);
		mins.setLabel("mins");
		mins.setLabelIcon(R.drawable.font_min);
		mins.setCyclic(true);

		secs.setAdapter(new NumericWheelAdapter(0, 55, 5, "%02d"), AppConstant.NUM_GREEN);
		secs.setLabel("sec");
		secs.setLabelIcon(R.drawable.sec_font);
		secs.setCyclic(true);

		mins.setCurrentItem(settingsTime/60);
		secs.setCurrentItem(settingsTime%60/5);
		mins.addChangingListener(wheelListener);
		secs.addChangingListener(wheelListener);
		mins.addScrollingListener(wheelScrollListener);
		secs.addScrollingListener(wheelScrollListener);

		timewheel.setVisibility(View.VISIBLE);
		timeext.setVisibility(View.GONE);
		weightwheel.setVisibility(View.GONE);

		leftTitle.setText("TIME");
		rightTitle.setVisibility(View.GONE);
	}
	public void setWeightWheelView(){
		weightView.setAdapter(new NumericWheelAdapter(min_weight, max_weight, weight_step), AppConstant.NUM_GREEN);
		weightView.setLabel("weight");
		weightView.setLabelIcon(R.drawable.g);
		weightView.setCyclic(true);

		weightView.setCurrentItem((weight - min_weight)/weight_step);
		weightView.addChangingListener(wheelListener);
		weightView.addScrollingListener(wheelScrollListener);

		weightwheel.setVisibility(View.VISIBLE);
		timewheel.setVisibility(View.GONE);
		timeext.setVisibility(View.GONE);
		leftTitle.setText("WEIGHT");
		rightTitle.setVisibility(View.GONE);
	}
	private void setWheelData(int mode) {

		switch(mode){
		case AppConstant.MODE_1:
			setTimeWheelView();
			setPowerBtnView();
			break;
		case AppConstant.MODE_3:
			setTimeWheelView();
			setTempWheelView();
			break;
		case AppConstant.MODE_4:
		case AppConstant.MODE_2:
			setTimeWheelView();
			break;
		case AppConstant.MODE_5:
			setWeightWheelView();
			break;

		default:

			break;
		}

	}

	public void updateMode(int mode) {
		if(mwocooking.getVisibility() == View.VISIBLE){
			this.settingsMode = mode;
			System.out.println("***** updateMode settingsMode = " + settingsMode);
			modeIcon.setImageResource(AppConstant.mwo_mode_icon[mode]);
		}

	}

	public void updatePowerBtnView(){
		setAllPowerBtnNormal();
		setSelPowerView(power5, power);

	}
	public void updateWeight(int weight) {
		if(mwocooking.getVisibility() == View.VISIBLE){
			this.weight = weight;
			//System.out.println("***** updateMode settingsMode = " + settingsMode);
			//timetxt.setText(weight + "��");
		}

	}



}