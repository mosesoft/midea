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
import com.midea.iot.devices.Minioven;
import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.MiniovenStatus;
import com.midea.iot.wheelview.NumericWheelAdapter;
import com.midea.iot.wheelview.OnWheelChangedListener;
import com.midea.iot.wheelview.OnWheelScrollListener;
import com.midea.iot.wheelview.WheelView;

public class MiniovenWorkingView {
	class CookingBtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("*****BtnOnClickListener");
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.backbtn:

				displaySetttingsView(true);
				break;
				
			case R.id.ok:
				minioven.stop(getCookingDataByMode(settingsMode));
				displaySetttingsView(false);
				hideFinishView();
				workingState = AppConstant.COOKING_WAIT;
				break;
				
			case R.id.stop:
		    	workingState = AppConstant.COOKING_WAIT;
		    	//cookingState = AppConstant.COOKING_WAIT;
		    	stateTxt.setText(AppConstant.stateArray[workingState]);
		    	pause.setImageResource(R.drawable.startbtn);
		    	
		    	minioven.stop(getCookingDataByMode(settingsMode));
		    	displaySetttingsView(false);
                count = 0;  
            	stepCount = 0;
            	progress = 0;
				break;
				
			case R.id.pause:
		    	if(workingState == AppConstant.COOKING_RUN){
		    		workingState = AppConstant.COOKING_PAUSE;
		    		pause.setImageResource(R.drawable.startbtn);
		    		backbtn.setVisibility(View.VISIBLE);
		    		minioven.pause(getCookingDataByMode(settingsMode));
		    	}else{
		    		workingState = AppConstant.COOKING_RUN;
		    		pause.setImageResource(R.drawable.pausebtn);
		    		backbtn.setVisibility(View.INVISIBLE);
		    		mHandler.post(timerTask);
		    		minioven.reStart();
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
	/*	public void startCookingActivity(int id, int mode, int time, int temp){
		Intent intent = new Intent();
		intent.setClass(ctx, CookingActivity.class);
		intent.putExtra("ID", id);
		intent.putExtra("MODE", mode);
		intent.putExtra("TIME", time);
		intent.putExtra("TEMP", temp);

		//����Activity
		ctx.startActivity(intent);
	}*/
	public class MiniovenViewPager extends MyViewPager{

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
		
	    public MiniovenViewPager(Context ctx, LinearLayout parent, MyApplication myApplication, int id, int mode) {
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

			setSelModeView(modeName, mode3, AppConstant.MODE_3);
			
			//getDataByMode(AppConstant.MODE_1);
	    }

		public void setAllIconNormal(){
			mode1.setImageResource(AppConstant.minioven_mode_nor[0]);
			mode2.setImageResource(AppConstant.minioven_mode_nor[1]);
			mode3.setImageResource(AppConstant.minioven_mode_nor[2]);
			mode4.setImageResource(AppConstant.minioven_mode_nor[3]);
			mode5.setImageResource(AppConstant.minioven_mode_nor[4]);
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
				view.setImageResource(AppConstant.minioven_mode_sel[mode]);
			}else{
				view.setImageResource(AppConstant.minioven_mode_nor[mode]);
			}
		}
		@Override
		public void setSelModeView(TextView modeName, ImageView view, int mode) {
			// TODO Auto-generated method stub
			modeName.setText(AppConstant.minioven_mode_name[mode]);
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
				MiniovenStatus os = new MiniovenStatus(settingsMode+1, settingsTime/60, settingsTime%60, settingsTemp);
				Minioven minioven = (Minioven)MainActivity.getInstance().getDevice(BaseProtocol.TYPE_MINIOVEN);
				minioven.start(os);
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
	private FrameLayout miniovensettings = null;
	
	private FrameLayout miniovencooking = null;
	public int settingsTime = 0;
	public int min_time = 0;
	public int max_time = 0;
	public int settingsTemp = 0;
	public int min_secs = 0;
	public int max_secs = 0;
	
	public int secs_step = 1;
	public int temp = 0;


	public  int min_temp = 0;
	public int max_temp = 0;
	public int temp_step = 0;
	
	private MiniovenViewPager myViewPager = null;
	private LinearLayout myviewpager = null;
	
	private ImageButton startbtn = null;

	
	private TextView leftTitle = null;
	private TextView rightTitle = null;
	private WheelView mins = null;
	
	private WheelView secs = null;
	private WheelView tempView = null;
	public int remainTime = 0;
	public int actualTemp = 0;
	public int progress = 0;
	private int progressStep = 0;
	private TextView stateTxt = null;
	private LinearLayout cookingcontent = null;
	private FrameLayout backview = null;
	private ImageButton backbtn = null;
	private ImageView processcircle = null;
	private ImageView movingcircle = null;
	private LinearLayout cookingview = null;
	private ImageView modeIcon = null;
	private ImageView percent1 = null;
	private ImageView percent2 = null;
	
	
	private ImageView percent = null;
	private ImageView title_font = null;
	private TextView timetxt = null;
	private TextView temptxt = null;
	
	private ImageView fickerfont = null;
	private ImageView finishcircle = null;
	private LinearLayout finishview = null;
	private ImageView finish = null;
	private ImageButton ok = null;
	
    private ImageButton stop = null;
 
    private ImageButton pause = null;
    Minioven minioven = null;
    private boolean isPad = false;
   OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if(wheel.equals(mins)){
			settingsTime = settingsTime%60 + newValue*60 ;
		}else if(wheel.equals(secs)){
			settingsTime = (settingsTime/60)*60 + newValue;
		}else if(wheel.equals(tempView)){
			settingsTemp = newValue*temp_step + min_temp;
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
            	System.out.println("*****timerTask  212121");
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
	
	public MiniovenWorkingView(Context ctx, FrameLayout parent, MyApplication myApplication, int id, int mode, Handler mHandler) {
		super();
		this.ctx = ctx;
		this.parent = parent;
		this.myApplication = myApplication;
		
		WindowManager wm = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		isPad = width >= 1200;
		
		this.id = id;
		this.settingsMode = mode;
		this.mHandler = mHandler;
		minioven = MainActivity.getInstance().getMiniovenDevice();
		workingState = minioven.getWorkStatus() - 1;
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
    		backbtn.setVisibility(View.INVISIBLE);
    	}else{
    		pause.setImageResource(R.drawable.startbtn);
    		backbtn.setVisibility(View.VISIBLE);
    	}
    	progress = 0;
    	
    	setPercentView(progress);
    	setTimeView(remainTime);
    	setTempView(actualTemp);
    	updateMode(settingsMode);
    	
    	if(id == AppConstant.MINIOVEN){
        	mHandler.post(timerTask);
        	calcProStep(settingsTime);
    	}
		mHandler.removeCallbacks(timerTask);
		mHandler.post(timerTask);

    	miniovensettings.setVisibility(View.INVISIBLE);
    	miniovencooking.setVisibility(View.VISIBLE);
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
		stateTxt.setVisibility(View.INVISIBLE);
		backbtn.setVisibility(View.INVISIBLE);
		mHandler.removeCallbacks(timerTask);
	}
    
    public void displaySetttingsView(boolean ispause){

    	//getSettingsDataByMode(mode);
    	myViewPager.updateSettingsMode(settingsMode, ispause);
    	miniovensettings.setVisibility(View.VISIBLE);
    	miniovencooking.setVisibility(View.INVISIBLE);
    	if(ispause){
    		myViewPager.setModeClickable(false);
    	}else{
    		myViewPager.setModeClickable(true);
    	}
    }
	private MiniovenStatus getCookingDataByMode(int mode) {
		settingsTime = Integer.parseInt(AppConstant.minioven_default_data[mode][1]);
		settingsTemp = Integer.parseInt(AppConstant.minioven_default_data[mode][4]);
		MiniovenStatus miniovenStatus = new MiniovenStatus(mode + 1, settingsTime/60, settingsTime%60,settingsTemp);
		return miniovenStatus;
    }
    private void getDefaultSettingsDataByMode(int mode) {
		settingsTime = Integer.parseInt(AppConstant.minioven_default_data[mode][1]);
		settingsTemp = Integer.parseInt(AppConstant.minioven_default_data[mode][4]);
		min_temp = Integer.parseInt(AppConstant.minioven_default_data[mode][5]);
		max_temp = Integer.parseInt(AppConstant.minioven_default_data[mode][6]);
		temp_step = Integer.parseInt(AppConstant.minioven_default_data[mode][7]);

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
//    	cookingcontent.setBackgroundResource(R.drawable.basicbg);
    	finishview.setVisibility(View.INVISIBLE);
    	finish.setVisibility(View.INVISIBLE);
    	ok.setVisibility(View.INVISIBLE);
    	stop.setClickable(true);
    	pause.setClickable(true);
		stateTxt.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(timerTask);

	}
    
	private void initCookingView(){
    	miniovencooking = (FrameLayout) parent.findViewById(R.id.miniovencooking);
		cookingcontent = (LinearLayout)parent.findViewById(R.id.cookingcontent);
    	backview = (FrameLayout)parent.findViewById(R.id.backview);
    	backbtn = (ImageButton)parent.findViewById(R.id.backbtn);
    	stateTxt = (TextView)parent.findViewById(R.id.state);
    	stateTxt.setText(AppConstant.stateArray[workingState]);
    	processcircle = (ImageView)parent.findViewById(R.id.processcircle);
    	movingcircle = (ImageView)parent.findViewById(R.id.movingcircle);
    	cookingview = (LinearLayout)parent.findViewById(R.id.cookingview);
    	modeIcon = (ImageView)parent.findViewById(R.id.mode);
    	percent1 = (ImageView)parent.findViewById(R.id.percent1);
    	percent2 = (ImageView)parent.findViewById(R.id.percent2);
    	percent = (ImageView)parent.findViewById(R.id.percent);
    	title_font = (ImageView)parent.findViewById(R.id.title_font);
    	timetxt = (TextView)parent.findViewById(R.id.timetxt);
    	temptxt = (TextView)parent.findViewById(R.id.temptxt);

    	finishcircle = (ImageView)parent.findViewById(R.id.finishcircle);
    	finishview = (LinearLayout)parent.findViewById(R.id.finishview);
    	finish = (ImageView)parent.findViewById(R.id.finish);
    	ok = (ImageButton)parent.findViewById(R.id.ok);

    	stop = (ImageButton)parent.findViewById(R.id.stop);
    	pause = (ImageButton)parent.findViewById(R.id.pause);

    	if(workingState == AppConstant.COOKING_RUN){
    		pause.setImageResource(R.drawable.pausebtn);
    		backbtn.setVisibility(View.INVISIBLE);
    	}else{
    		pause.setImageResource(R.drawable.startbtn);
    		backbtn.setVisibility(View.VISIBLE);
    	}
    	progress = 0;

    	setPercentView(progress);
    	setTimeView(remainTime);
    	setTempView(actualTemp);
    	System.out.println("**** initView mode = " + settingsMode);
    	updateMode(settingsMode);
    	backbtn.setOnClickListener(new CookingBtnOnClickListener());
    	ok.setOnClickListener(new CookingBtnOnClickListener());
    	stop.setOnClickListener(new CookingBtnOnClickListener());
    	pause.setOnClickListener(new CookingBtnOnClickListener());

    	if(id == AppConstant.MINIOVEN){
    		mHandler.post(timerTask);
    		calcProStep(settingsTime);
    	}
    }
	
	private void initSettingsView() {

    	miniovensettings = (FrameLayout) parent.findViewById(R.id.miniovensettings);
    	myviewpager = (LinearLayout) parent.findViewById(R.id.myviewpager);

    	myViewPager = new MiniovenViewPager(ctx, myviewpager, myApplication, id, settingsMode);
    	leftTitle = (TextView) parent.findViewById(R.id.lefttitle);
    	rightTitle = (TextView) parent.findViewById(R.id.righttitle);
    	startbtn = (ImageButton) parent.findViewById(R.id.startbtn);

    	startbtn.setOnClickListener(new SettingsBtnOnClickListener());

		if(workingState == AppConstant.COOKING_WAIT){
			getDefaultSettingsDataByMode(settingsMode);
		}else{

			String timeStr = AppStatic.getSharedPreData(AppConstant.time_label[AppConstant.MINIOVEN], ctx);
			settingsTime = Integer.parseInt(timeStr);

			//setTimeTempView(settingsTime, settingsTemp);
		}
    	//initViewPager();
    	initWheelView();

    }

	private void initView() {
    	initSettingsView();
    	initCookingView();
    	if(workingState == AppConstant.COOKING_WAIT){
    		miniovensettings.setVisibility(View.VISIBLE);
    		miniovencooking.setVisibility(View.INVISIBLE);
    	}else{
    		miniovensettings.setVisibility(View.INVISIBLE);
    		miniovencooking.setVisibility(View.VISIBLE);
    		if(workingState == AppConstant.COOKING_WAIT){
		    	stateTxt.setText(AppConstant.stateArray[workingState]);
		    	pause.setImageResource(R.drawable.startbtn);
		    	//myApplication.remove();
		    	displaySetttingsView(false);
	    	}else if(workingState == AppConstant.COOKING_RUN){
	    		System.out.println("***** COOKING_RUN ");
	    		displayCookingView();
	    		stateTxt.setText(AppConstant.stateArray[workingState]);
	    		pause.setImageResource(R.drawable.pausebtn);
	    		backbtn.setVisibility(View.INVISIBLE);
	    		mHandler.removeCallbacks(timerTask);
	    		mHandler.post(timerTask);
	    	}else if(workingState == AppConstant.COOKING_PAUSE){
	    		displayCookingView();
	    		stateTxt.setText(AppConstant.stateArray[workingState]);
	    		pause.setImageResource(R.drawable.startbtn);
	    		backbtn.setVisibility(View.VISIBLE);
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
		
		setWheelData(settingsMode);
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
	
	public void setBtnClickable(boolean clickable) {
		setSettingsBtnClickable(clickable);
		setCookingBtnClickable(clickable);
	}
	public void setCookingBtnClickable(boolean clickable) {
		// TODO Auto-generated method stub
		startbtn.setClickable(clickable);
		myViewPager.setModeClickable(clickable);
		if(clickable){
	    	mins.setOnTouchListener(new EnableOnTouchListener());
	    	secs.setOnTouchListener(new EnableOnTouchListener());
	    	tempView.setOnTouchListener(new EnableOnTouchListener());
		}else{
	    	mins.setOnTouchListener(new DisableOnTouchListener());
	    	secs.setOnTouchListener(new DisableOnTouchListener());
	    	tempView.setOnTouchListener(new DisableOnTouchListener());
		}
	}
    
    public void setCookingState(int state) {
		// TODO Auto-generated method stub
		setCookingView(state);
		workingState = state;

	}
    
    public void setCookingView(int state){
    	System.out.println("*****setCookingView state = " + state + ", workingState = " + workingState);
    	if(state != workingState){
    		if(workingState == AppConstant.COOKING_FINISH){
    			displayCookingView();
    			hideFinishView();
    		}
    		if(state == AppConstant.COOKING_WAIT){
		    	stateTxt.setText(AppConstant.stateArray[state]);
		    	pause.setImageResource(R.drawable.startbtn);
		    	//myApplication.remove();
		    	displaySetttingsView(false);
	    	}else if(state == AppConstant.COOKING_RUN){
	    		System.out.println("***** COOKING_RUN ");
	    		displayCookingView();
	    		stateTxt.setText(AppConstant.stateArray[state]);
	    		pause.setImageResource(R.drawable.pausebtn);
	    		backbtn.setVisibility(View.INVISIBLE);
	    		mHandler.removeCallbacks(timerTask);
	    		mHandler.post(timerTask);
	    	}else if(state == AppConstant.COOKING_PAUSE){
	    		displayCookingView();
	    		stateTxt.setText(AppConstant.stateArray[state]);
	    		pause.setImageResource(R.drawable.startbtn);
	    		backbtn.setVisibility(View.VISIBLE);
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

    public void setSettingsBtnClickable(boolean clickable) {
		// TODO Auto-generated method stub
		backbtn.setClickable(clickable);
		ok.setClickable(clickable);
		stop.setClickable(clickable);
		pause.setClickable(clickable);
	}
    public void setSettingsTempView(int temp) {
    	if(miniovensettings.getVisibility() != View.VISIBLE){
        	settingsTemp = temp;
    	}
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
		tempView.setAdapter(new NumericWheelAdapter(min_temp, max_temp, temp_step), AppConstant.NUM_WHITE);
		tempView.setLabel("C");
		tempView.setLabelIcon(R.drawable.c);
		tempView.setCyclic(true);
		
		int idx = (temp - min_temp)/temp_step;
		tempView.setCurrentItem(idx);
		tempView.addChangingListener(wheelListener);
		tempView.addScrollingListener(wheelScrollListener);

		rightTitle.setText("TEMP.");
    }
	public void setTimeView(int time) {
    	int num1 = time/60;
    	int num2 = time%60/10;
    	int num3 = time%60%10;
    	String timeStr = num1 + ":" + num2 + num3;
    	timetxt.setText(timeStr);
    }
	
	public void setTimeWheelView(){
		mins.setAdapter(new NumericWheelAdapter(0, 89, 1, "%02d"), AppConstant.NUM_GREEN);
		mins.setLabel("mins");
		mins.setLabelIcon(R.drawable.font_min);
		mins.setCyclic(true);
	
		secs.setAdapter(new NumericWheelAdapter(0, 59, 1, "%02d"), AppConstant.NUM_GREEN);
		secs.setLabel("sec");
		secs.setLabelIcon(R.drawable.sec_font);
		secs.setCyclic(true);
		
		mins.setCurrentItem(settingsTime/60);
		secs.setCurrentItem(settingsTime%60);
		mins.addChangingListener(wheelListener);
		secs.addChangingListener(wheelListener);
		mins.addScrollingListener(wheelScrollListener);
		secs.addScrollingListener(wheelScrollListener);
		
		leftTitle.setText("TIME");
    }
	
	private void setWheelData(int mode) {
		setTimeWheelView();
		setTempWheelView();
    }
	
    public void updateMode(int mode) {
    	if(miniovensettings.getVisibility() != View.VISIBLE){
        	this.settingsMode = mode;
        	System.out.println("***** updateMode settingsMode = " + settingsMode);
        	modeIcon.setImageResource(AppConstant.minioven_mode_icon[mode]);
    	}

    }
}