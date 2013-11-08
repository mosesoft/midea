package com.midea.iot.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.midea.iot.R;
import com.midea.iot.devices.Hob;
import com.midea.iot.devices.Hood;
import com.midea.iot.devices.Microwave;
import com.midea.iot.devices.Minioven;
import com.midea.iot.devices.Oven;
import com.midea.iot.devices.Robotclean;
import com.midea.iot.myview.CleanWorkingView;
import com.midea.iot.myview.HobWorkingView;
import com.midea.iot.myview.HoodWorkingView;
import com.midea.iot.myview.MiniovenWorkingView;
import com.midea.iot.myview.MwoWorkingView;
import com.midea.iot.myview.MyPagerAdapter;
import com.midea.iot.myview.MyScrollView;
import com.midea.iot.myview.MySideBar;
import com.midea.iot.myview.MyTitlebar;
import com.midea.iot.myview.OvenWorkingView;
import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.HobProtocol.HobPosition;

public class SettingsActivity extends Activity implements MyTitlebar.ViewClickable, 
			MySideBar.SelectDeviceLayout, HobWorkingView.GuideOperate{
	
	class DeleteBtnOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			myviewpager.setVisibility(View.GONE);
		}
	}
	
	private FrameLayout listpage = null;

	
	private MySideBar sideBar = null;
	private OvenWorkingView ovenWorkingView = null;
	//private OvenSettingsView ovenSettingsView = null;
	//private OvenCookingView ovenCookingView = null;
	private MiniovenWorkingView miniovenWorkingView = null;
	//private MiniovenSettingsView miniovenSettingsView = null;
	//private MiniovenCookingView miniovenCookingView = null;
	//private MwoSettingsView mwoSettingsView = null;
	//private MwoCookingView mwoCookingView = null;
	private MwoWorkingView mwoWorkingView = null;
	private HoodWorkingView hoodWorkingView = null;
	private HobWorkingView hobWorkingView = null;
	
	private CleanWorkingView cleanWorkingView = null;

	private MyScrollView settingmain = null;

	private FrameLayout titlebar = null;
	private FrameLayout ovenworking = null;
	
	//private FrameLayout ovencooking = null;
	private FrameLayout mwoworking = null;
	//private FrameLayout mwocooking = null;
	private FrameLayout miniovenworking = null;
	//private FrameLayout miniovencooking = null;
	private FrameLayout hoodworking = null;
	private FrameLayout hobworking = null;
	
	private FrameLayout cleanworking = null;
	
	private MyTitlebar myTitleBar = null;
	private FrameLayout myviewpager = null;
	private ViewPager viewpager = null;
	private ImageButton deletebtn = null;
	
	public int id = AppConstant.OVEN;
	private int mode = AppConstant.MODE_1;
	
	MyApplication myApplication;
	public int workingState = 0;
	static public int ovenTime = 0;
	public int miniovenTime = 0;
	public int mwoTime = 0;
	//public int time = 0;
	public int power = 0;
	public int weight = 0;
	public int ovenRemainTime = 0;
	public int miniovenRemainTime = 0;
	public int mwoRemainTime = 0;
	public int actualTemp = 0;
	public int ovenProgress = 0;
	public int miniovenProgress = 0;
	public int mwoProgress = 0;
	public int temp = 0;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (AppConstant.ACTION_DEVICE_REPLY.equals(action)) {
				handleReplyMessage(intent.getByteArrayExtra("receive_data"));

			} else if (AppConstant.ACTION_DEVICE_UPDATE.equals(action)) {
				handleUpdateMessage(intent.getByteArrayExtra("receive_data"));
			} else if(AppConstant.ACTION_DEVICE_TIMEOUT.equals(action)) {
				handleTimeoutMessage();
			}
		}
	};
    
	private Handler mHandler = new Handler();
    /*    private void setTimeView(int time) {
    	if(ovenworking.getVisibility() == View.VISIBLE){
    		ovenWorkingView.setTimeView(time);
    	}else if(mwoworking.getVisibility() == View.VISIBLE){
    		mwoWorkingView.setTimeView(time);
    	}else if(miniovenworking.getVisibility() == View.VISIBLE){
    		miniovenWorkingView.setTimeView(time);
    	}
    }
    private void setTempView(int temp) {
    	if(ovenworking.getVisibility() == View.VISIBLE){
    		ovenWorkingView.setTempView(temp);
    	}else if(mwoworking.getVisibility() == View.VISIBLE){
    		mwoWorkingView.setTempView(temp);
    	}else if(miniovenworking.getVisibility() == View.VISIBLE){
    		miniovenWorkingView.setTempView(temp);
    	}
    }*/
    private void calcProStop(int time) {
    	if(ovenworking.getVisibility() == View.VISIBLE){
    		ovenWorkingView.calcProStep(time);
    	}else if(mwoworking.getVisibility() == View.VISIBLE){
    		mwoWorkingView.calcProStep(time);
    	}else if(miniovenworking.getVisibility() == View.VISIBLE){
    		miniovenWorkingView.calcProStep(time);
    	}
    }
    

	@Override
	public void displayGuide() {
		// TODO Auto-generated method stub
		myviewpager.setVisibility(View.VISIBLE);
	}

	public void exitWorking() {
		// TODO Auto-generated method stub
		ovenWorkingView.removeTimerTask();
    	hoodWorkingView.removeTimerTask();
    	hoodWorkingView.recyleBitmap();
    	cleanWorkingView.removeTimerTask();
    	cleanWorkingView.recyleBitmap();
    	miniovenWorkingView.removeTimerTask();
    	hobWorkingView.removeTimerTask();
    	mwoWorkingView.removeTimerTask();
        /*if(ovencooking.getVisibility() == View.VISIBLE){
    		System.out.println("*****exitOvenCooing");
    		ovenCookingView.removeTimerTask();
    		ovenCookingView.recyleBitmap();
    	}else *//*if(mwocooking.getVisibility() == View.VISIBLE){
    		mwoCookingView.removeTimerTask();
    		mwoCookingView.recyleBitmap();
    	}else if(miniovenworking.getVisibility() == View.VISIBLE){
    		miniovenWorkingView.removeTimerTask();
    		//miniovenCookingView.recyleBitmap();
    	}
    	*/
	}
	private void handleReplyMessage(byte[] b) {
		byte type = b[BaseProtocol.PROTOCOL_TYPE];
		int deviceId = 0;
		int workState = 0;
		int mode = AppConstant.MODE_1;
		int tempRemainTime = 0;
		int tempProgress = 0;
		int tempTemp = 0;
		int tempSettingsTemp = 0;
		System.out.println("*****handleReplyMessage");
		switch(type) {
		case BaseProtocol.TYPE_HOB:
			Hob hob = (Hob)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_HOB - 1;
			workState = hob.getWorkStatus();
			sideBar.setWorkStatus(deviceId, workState);
			if(hobworking.getVisibility() == View.VISIBLE){
				myTitleBar.setLockStatus(hob.isChildrenLock());
			}
			
			hobWorkingView.setWorkState(workState);
			
			hobWorkingView.setSelectState(HobPosition.HP_LEFT_CORNER, hob.getWorkStatus(HobPosition.HP_LEFT_CORNER));
			hobWorkingView.setSelectPower(HobPosition.HP_LEFT_CORNER, hob.getPowerLevel(HobPosition.HP_LEFT_CORNER));
			hobWorkingView.setRemainTimeByMode(HobPosition.HP_LEFT_CORNER, hob.getRemainTime(HobPosition.HP_LEFT_CORNER));
			hobWorkingView.setSelectMode(HobPosition.HP_LEFT_CORNER);


			hobWorkingView.setSelectPower(HobPosition.HP_RIGHT_CORNER, hob.getPowerLevel(HobPosition.HP_RIGHT_CORNER));
			hobWorkingView.setSelectState(HobPosition.HP_RIGHT_CORNER, hob.getWorkStatus(HobPosition.HP_RIGHT_CORNER));
			hobWorkingView.setRemainTimeByMode(HobPosition.HP_RIGHT_CORNER, hob.getRemainTime(HobPosition.HP_RIGHT_CORNER));
			hobWorkingView.setSelectMode(HobPosition.HP_RIGHT_CORNER);

			
			hobWorkingView.setSelectPower(HobPosition.HP_BOTTOM_LEFT, hob.getPowerLevel(HobPosition.HP_BOTTOM_LEFT));
			hobWorkingView.setSelectState(HobPosition.HP_BOTTOM_LEFT, hob.getWorkStatus(HobPosition.HP_BOTTOM_LEFT));
			hobWorkingView.setRemainTimeByMode(HobPosition.HP_BOTTOM_LEFT, hob.getRemainTime(HobPosition.HP_BOTTOM_LEFT));
			hobWorkingView.setSelectMode(HobPosition.HP_BOTTOM_LEFT);
			

			hobWorkingView.setSelectPower(HobPosition.HP_BOTTOM_RIGHT, hob.getPowerLevel(HobPosition.HP_BOTTOM_RIGHT));
			hobWorkingView.setSelectState(HobPosition.HP_BOTTOM_RIGHT, hob.getWorkStatus(HobPosition.HP_BOTTOM_RIGHT));
			hobWorkingView.setRemainTimeByMode(HobPosition.HP_BOTTOM_RIGHT, hob.getRemainTime(HobPosition.HP_BOTTOM_RIGHT));
			hobWorkingView.setSelectMode(HobPosition.HP_BOTTOM_RIGHT);
			
			
			hobWorkingView.updateAllHobView();
			//hobWorkingView.setSelectWheelData();
			hobWorkingView.setPauseState();
			break;
			
		case BaseProtocol.TYPE_OVEN:

			Oven oven = (Oven)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_OVEN - 1;
			workState = oven.getWorkStatus() - 1;
			if(workState == AppConstant.COOKING_WAIT){
				ovenTime = 0;
			}else if(workState == AppConstant.COOKING_RUN){
				if(ovenTime == 0){
					String timeStr = AppStatic.getSharedPreData(AppConstant.time_label[deviceId], SettingsActivity.this);
					System.out.println("******ovenTime 1 = " + ovenTime);
					ovenTime = 1;//Integer.parseInt(timeStr);
				}
			}
			System.out.println("****** workState = " + workState);
			if(workState < AppConstant.COOKING_WAIT){
				workState = AppConstant.COOKING_WAIT;
			}else if(workState > AppConstant.COOKING_FINISH){
				workState = AppConstant.COOKING_FINISH;
			}
			mode = oven.getWorkMode() - 1;
			if(mode < AppConstant.MODE_1){
				mode = AppConstant.MODE_1;
			}else if(mode > AppConstant.MODE_8){
				mode = AppConstant.MODE_8;
			}

			sideBar.setWorkStatus(deviceId, workState);
			if(ovenworking.getVisibility() == View.VISIBLE){
				myTitleBar.setLockStatus(oven.isChildrenLock());
				myTitleBar.setLightStatus(oven.isLightOn());
			}

			System.out.println("****** getWorkMode = " + oven.getWorkMode());
			if(workState != AppConstant.COOKING_WAIT){
				if(oven.getWorkMode() > 0){
					ovenWorkingView.updateMode(mode);
				}else{
					ovenWorkingView.updateMode(oven.getWorkMode());
				}
			}


			//titlebar.set(deviceId, oven.getWorkStatus() - 1);
	    	tempRemainTime = oven.getRemainTime();
	    	ovenWorkingView.setRemainTime(tempRemainTime);
	    	System.out.println("******tempRemainTime 5 = " + tempRemainTime + ", remainTime = " + ovenRemainTime);
	    	ovenWorkingView.setTimeView(tempRemainTime);
	    	tempTemp = oven.getReallyTemperature();
	    	ovenWorkingView.setTempView(tempTemp);
	    	tempSettingsTemp = oven.getSettingTemperature();
	    	ovenWorkingView.setSettingsTempView(tempSettingsTemp);
	    	if(tempRemainTime != ovenRemainTime){
	    		ovenRemainTime = tempRemainTime;
	        	
	        	if(ovenRemainTime > ovenTime){
	        		ovenTime = ovenRemainTime;
	        		ovenWorkingView.setSettingsTime(ovenTime);
	        		System.out.println("******saveSharedPreData 1 time_label = " + AppConstant.time_label[id] + ", tempRemainTime = " +  tempRemainTime);
	        	  	AppStatic.saveSharedPreData(AppConstant.time_label[deviceId], Integer.toString(ovenTime),SettingsActivity.this);
	        	  	calcProStop(ovenTime);
	        	}
	        	
	        	tempProgress = 0;
	        	if(ovenTime == 0){
	        		tempProgress = 0;
	        	}else{
	        		tempProgress = (ovenTime - ovenRemainTime)*100/ovenTime;
	        		System.out.println("******handleUpdateMessage remainTime = " + ovenRemainTime + ", time = " + ovenTime + ", tempProgress = " + tempProgress);
	        	}
	        	System.out.println("******handleUpdateMessage remainTime = " + ovenRemainTime + ", time = " + ovenTime + ", tempProgress = " + tempProgress);
	        	if(tempProgress != ovenProgress){
	        		ovenProgress = tempProgress;
	            	setPercentView(ovenProgress);
	        	}
	    	}
	 
	    	tempTemp = oven.getReallyTemperature();
	    	System.out.println("***** tempTemp 2= " + tempTemp);
	    	if(tempTemp != actualTemp){
	    		actualTemp = tempTemp;
	    		//setTempView(actualTemp);
	    	}

			//mode = oven.getWorkMode() - 1;
			//workState = oven.getWorkStatus() - 1;
			System.out.println("***** workState = " + workState);
			ovenWorkingView.setCookingState(workState);
			
			break;
			
			
			
		case BaseProtocol.TYPE_HOOD:
			Hood hood = (Hood)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_HOOD - 1;
			
			workState = hood.getWorkStatus();
			
			if(workState < AppConstant.COOKING_WAIT){
				workState = AppConstant.COOKING_WAIT;
			}
			hoodWorkingView.setWorkingState(workState);
			sideBar.setWorkStatus(deviceId, workState);
			if(hoodworking.getVisibility() == View.VISIBLE){
				myTitleBar.setLightStatus(hood.isLightOn());
			}

			if(hoodworking.getVisibility() == View.VISIBLE){
				hoodWorkingView.setSpeed(hood.getMotorSpeed());
				hoodWorkingView.setTimeView(hood.getRunningTime());
			}
			break;
			
		case BaseProtocol.TYPE_MICROWAVE:
			Microwave microwave = (Microwave)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_MICROWAVE - 1;
			workState = microwave.getWorkStatus() - 1;
			
			if(workState < AppConstant.COOKING_WAIT){
				workState = AppConstant.COOKING_WAIT;
			}else if(workState > AppConstant.COOKING_FINISH){
				workState = AppConstant.COOKING_FINISH;
			}
			mode = microwave.getWorkMode() - 1;
			
			if(mode < AppConstant.MODE_1){
				mode = AppConstant.MODE_1;
			}else if(mode > AppConstant.MODE_5){
				mode = AppConstant.MODE_5;
			}
			sideBar.setWorkStatus(deviceId, workState);

			System.out.println("****** getWorkMode = " + microwave.getWorkMode());
			if(workState != AppConstant.COOKING_WAIT){
				if(microwave.getWorkMode() > 0){
					mwoWorkingView.updateMode(mode);
				}else{
					mwoWorkingView.updateMode(microwave.getWorkMode());
				}
			}
			
			switch(mode){
			case AppConstant.MODE_1:
				power = microwave.getPowerLevel() - 1;
				mwoWorkingView.setSettingsPower(power);
		    	tempTemp = microwave.getReallyTemperature();
		    	mwoWorkingView.setTempView(tempTemp);
		    	tempSettingsTemp = microwave.getSettingTemperature();
		    	mwoWorkingView.setSettingsTemp(tempSettingsTemp);
		    	System.out.println("***** tempTemp 1= " + tempTemp);
		    	if(tempTemp != actualTemp){
		    		actualTemp = tempTemp;
		    		//setTempView(actualTemp);
		    	}
				//mwoWorkingView.setSelPowerView(power);
				break;
				
			case AppConstant.MODE_3:
		    	tempTemp = microwave.getReallyTemperature();
		    	mwoWorkingView.setTempView(tempTemp);
		    	tempSettingsTemp = microwave.getSettingTemperature();
		    	mwoWorkingView.setSettingsTemp(tempSettingsTemp);
		    	System.out.println("***** tempTemp 3= " + tempTemp);
		    	if(tempTemp != actualTemp){
		    		actualTemp = tempTemp;
		    		//setTempView(actualTemp);
		    	}
				break;
				
			case AppConstant.MODE_2:
			case AppConstant.MODE_4:
		    	tempTemp = microwave.getReallyTemperature();
		    	mwoWorkingView.setTempView(tempTemp);
		    	tempSettingsTemp = microwave.getSettingTemperature();
		    	mwoWorkingView.setSettingsTemp(tempSettingsTemp);
		    	System.out.println("***** tempTemp 2= " + tempTemp);
		    	if(tempTemp != actualTemp){
		    		actualTemp = tempTemp;
		    		//setTempView(actualTemp);
		    	}
				break;
				
			case AppConstant.MODE_5:
				weight = microwave.getWeightNumber();
				mwoWorkingView.updateWeight(weight);
		    	tempTemp = microwave.getReallyTemperature();
		    	mwoWorkingView.setTempView(tempTemp);
		    	tempSettingsTemp = microwave.getSettingTemperature();
		    	mwoWorkingView.setSettingsTemp(tempSettingsTemp);
		    	System.out.println("***** tempTemp 5= " + tempTemp);
		    	if(tempTemp != actualTemp){
		    		actualTemp = tempTemp;
		    		//setTempView(actualTemp);
		    	}
		    	
				//weight = microwave.getWeightNumber();
				//mwoWorkingView.updateWeight(weight);
				break;
			default:
				break;
			}
			

			if(workState != AppConstant.COOKING_PAUSE){
		    	tempRemainTime = microwave.getRemainTime();
		    	mwoWorkingView.setRemainTime(tempRemainTime);
		    	System.out.println("******tempRemainTime 5 = " + tempRemainTime + ", remainTime = " + mwoRemainTime);
		    	mwoWorkingView.setTimeView(tempRemainTime);

		    	if(tempRemainTime != mwoRemainTime){
		    		mwoRemainTime = tempRemainTime;
		        	
		        	if(mwoRemainTime > mwoTime){
		        		mwoTime = mwoRemainTime;
		        		mwoWorkingView.setSettingsTime(mwoTime);
		        		System.out.println("******saveSharedPreData 8 time_label = " + AppConstant.time_label[id] + ", time = " +  mwoTime);
		        	  	AppStatic.saveSharedPreData(AppConstant.time_label[deviceId], Integer.toString(mwoTime),SettingsActivity.this);
		        	  	calcProStop(mwoTime);
		        	}
		        	
		        	tempProgress = 0;
		        	if(mwoTime == 0){
		        		tempProgress = 0;
		        	}else{
		        		tempProgress = (mwoTime - mwoRemainTime)*100/mwoTime;
		        	}
		        	System.out.println("******handleUpdateMessage remainTime = " + mwoRemainTime + ", time = " + mwoTime + ", tempProgress = " + tempProgress);
		        	if(tempProgress != mwoProgress){
		        		mwoProgress = tempProgress;
		            	setPercentView(mwoProgress);
		        	}
		    	}
			}

			System.out.println("***** workState = " + workState);
			mwoWorkingView.setCookingState(workState);
			break;
			
		case BaseProtocol.TYPE_ROBOTCLEAN:
			Robotclean robotclean = (Robotclean)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_ROBOTCLEAN - 1;
			workState = robotclean.getWorkStatus();
			System.out.println("******handleReplyMessage workState = " + workState);
			cleanWorkingView.setWorkingState(workState, robotclean.isFullCharged());
			sideBar.setWorkStatus(deviceId, workState);
			if(cleanworking.getVisibility() == View.VISIBLE){
				if(workState == AppConstant.CLEAN_CHARGING){
					if(robotclean.isFullCharged()){
						cleanWorkingView.setPercent(100);
					}else{
						cleanWorkingView.setPercent(robotclean.batteryValue());
					}
				}else if(workState == AppConstant.CLEAN_AUTO_CHARGING){
					if(robotclean.isFullCharged()){
						cleanWorkingView.setPercent(100);
					}else{
						cleanWorkingView.setPercent(robotclean.batteryValue());
					}
				}else if(workState == AppConstant.CLEAN_WORKING){
					cleanWorkingView.setTimeView(robotclean.workTime());
					cleanWorkingView.setPercent(robotclean.batteryValue());
				}else if(workState == AppConstant.CLEAN_STOP){
					cleanWorkingView.setPercent(robotclean.batteryValue());
				}
			}
			break;
			
		case BaseProtocol.TYPE_MINIOVEN:
			Minioven minioven = (Minioven)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_MINIOVEN - 1;
			workState = minioven.getWorkStatus() - 1;
			
			if(workState < AppConstant.COOKING_WAIT){
				workState = AppConstant.COOKING_WAIT;
			}else if(workState > AppConstant.COOKING_FINISH){
				workState = AppConstant.COOKING_FINISH;
			}
			
			if(workState == AppConstant.COOKING_WAIT){
				miniovenTime = 0;
			}else if(workState == AppConstant.COOKING_RUN){
				if(miniovenTime == 0){
					String timeStr = AppStatic.getSharedPreData(AppConstant.time_label[deviceId], SettingsActivity.this);
					miniovenTime = Integer.parseInt(timeStr);
				}
			}
			mode = minioven.getWorkMode() - 1;
			
			if(mode < AppConstant.MODE_1){
				mode = AppConstant.MODE_1;
			}else if(mode > AppConstant.MODE_5){
				mode = AppConstant.MODE_5;
			}

			sideBar.setWorkStatus(deviceId, workState);
			//myTitleBar.setLockStatus(minioven.isChildrenLock());
			if(miniovenworking.getVisibility() == View.VISIBLE){
				myTitleBar.setLightStatus(minioven.isLightOn());
			}
			
			System.out.println("****** getWorkMode = " + minioven.getWorkMode());
			if(workState != AppConstant.COOKING_WAIT){
				miniovenWorkingView.updateMode(mode);
			}


			//titlebar.set(deviceId, oven.getWorkStatus() - 1);
	    	tempRemainTime = minioven.getRemainTime();
	    	miniovenWorkingView.setRemainTime(tempRemainTime);
	    	System.out.println("******tempRemainTime 5 = " + tempRemainTime + ", remainTime = " + miniovenRemainTime);
	    	miniovenWorkingView.setTimeView(tempRemainTime);
	    	tempTemp = minioven.getReallyTemperature();
	    	miniovenWorkingView.setTempView(tempTemp);
	    	tempSettingsTemp = minioven.getSettingTemperature();
	    	miniovenWorkingView.setSettingsTempView(tempSettingsTemp);
	    	if(tempRemainTime != miniovenRemainTime){
	    		miniovenRemainTime = tempRemainTime;
	        	
	        	if(miniovenRemainTime > miniovenTime){
	        		miniovenTime = miniovenRemainTime;
	        		miniovenWorkingView.setSettingsTime(miniovenTime);
	        		System.out.println("******saveSharedPreData 7 time_label = " + AppConstant.time_label[id] + ", time = " +  miniovenTime);
	        	  	AppStatic.saveSharedPreData(AppConstant.time_label[deviceId], Integer.toString(miniovenTime),SettingsActivity.this);
	        	  	calcProStop(miniovenTime);
	        	}
	        	
	        	tempProgress = 0;
	        	if(miniovenTime == 0){
	        		tempProgress = 0;
	        	}else{
	        		tempProgress = (miniovenTime - miniovenRemainTime)*100/miniovenTime;
	        	}
	        	System.out.println("******handleUpdateMessage remainTime = " + miniovenRemainTime + ", time = " + miniovenTime + ", tempProgress = " + tempProgress);
	        	if(tempProgress != miniovenProgress){
	        		miniovenProgress = tempProgress;
	            	setPercentView(miniovenProgress);
	        	}
	    	}
	 
	    	tempTemp = minioven.getReallyTemperature();
	    	System.out.println("***** tempTemp 2= " + tempTemp);
	    	if(tempTemp != actualTemp){
	    		actualTemp = tempTemp;
	    		//setTempView(actualTemp);
	    	}

			//mode = oven.getWorkMode() - 1;
			//workState = oven.getWorkStatus() - 1;
			System.out.println("***** workState = " + workState);
			miniovenWorkingView.setCookingState(workState);
			
			break;
		default:
			return;
		}
	}

	private void handleTimeoutMessage() {
		System.out.println("��������������Receive Timeout Message��������������");
		//showAlertDialog(SettingsActivity.this, 1, mHandler);
        if(myApplication.isActivityVisible(AppConstant.SETTINGS_ACTIVITY_NAME)){
        	System.out.println("############ Setting MyAlertDialog");
//        	new MyAlertDialog(SettingsActivity.this, mHandler, myApplication);
        }
	}

	private void handleUpdateMessage(byte[] b) {
		byte type = b[BaseProtocol.PROTOCOL_TYPE];
		int deviceId = 0;
		int workState = 0;
		int mode = AppConstant.MODE_1;
		int tempRemainTime = 0;
		int tempProgress = 0;
		int tempTemp = 0;
		int tempSettingsTemp = 0;
		System.out.println("*****handleUpdateMessage");
		switch(type) {
		case BaseProtocol.TYPE_HOB:
			Hob hob = (Hob)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_HOB - 1;
			workState = hob.getWorkStatus();
			sideBar.setWorkStatus(deviceId, workState);
			if(hobworking.getVisibility() == View.VISIBLE){
				myTitleBar.setLockStatus(hob.isChildrenLock());
			}
			hobWorkingView.setWorkState(workState);
			
			hobWorkingView.setSelectState(HobPosition.HP_LEFT_CORNER, hob.getWorkStatus(HobPosition.HP_LEFT_CORNER));
			hobWorkingView.setSelectPower(HobPosition.HP_LEFT_CORNER, hob.getPowerLevel(HobPosition.HP_LEFT_CORNER));
			hobWorkingView.setRemainTimeByMode(HobPosition.HP_LEFT_CORNER, hob.getRemainTime(HobPosition.HP_LEFT_CORNER));
			hobWorkingView.setSelectMode(HobPosition.HP_LEFT_CORNER);


			hobWorkingView.setSelectPower(HobPosition.HP_RIGHT_CORNER, hob.getPowerLevel(HobPosition.HP_RIGHT_CORNER));
			hobWorkingView.setSelectState(HobPosition.HP_RIGHT_CORNER, hob.getWorkStatus(HobPosition.HP_RIGHT_CORNER));
			hobWorkingView.setRemainTimeByMode(HobPosition.HP_RIGHT_CORNER, hob.getRemainTime(HobPosition.HP_RIGHT_CORNER));
			hobWorkingView.setSelectMode(HobPosition.HP_RIGHT_CORNER);
			
			
			hobWorkingView.setSelectPower(HobPosition.HP_BOTTOM_LEFT, hob.getPowerLevel(HobPosition.HP_BOTTOM_LEFT));
			hobWorkingView.setSelectState(HobPosition.HP_BOTTOM_LEFT, hob.getWorkStatus(HobPosition.HP_BOTTOM_LEFT));
			hobWorkingView.setRemainTimeByMode(HobPosition.HP_BOTTOM_LEFT, hob.getRemainTime(HobPosition.HP_BOTTOM_LEFT));
			hobWorkingView.setSelectMode(HobPosition.HP_BOTTOM_LEFT);
			

			hobWorkingView.setSelectPower(HobPosition.HP_BOTTOM_RIGHT, hob.getPowerLevel(HobPosition.HP_BOTTOM_RIGHT));
			hobWorkingView.setSelectState(HobPosition.HP_BOTTOM_RIGHT, hob.getWorkStatus(HobPosition.HP_BOTTOM_RIGHT));
			hobWorkingView.setRemainTimeByMode(HobPosition.HP_BOTTOM_RIGHT, hob.getRemainTime(HobPosition.HP_BOTTOM_RIGHT));
			hobWorkingView.setSelectMode(HobPosition.HP_BOTTOM_RIGHT);
			
			
			hobWorkingView.updateAllHobView();
			//hobWorkingView.setSelectWheelData();
			hobWorkingView.setPauseState();
			break;
			
		case BaseProtocol.TYPE_OVEN:

			Oven oven = (Oven)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_OVEN - 1;
			workState = oven.getWorkStatus() - 1;
			if(workState < AppConstant.COOKING_WAIT){
				workState = AppConstant.COOKING_WAIT;
			}
			if(workState == AppConstant.COOKING_WAIT){
				ovenTime = 0;
			}else if(workState == AppConstant.COOKING_RUN){
				if(ovenTime == 0){
					String timeStr = AppStatic.getSharedPreData(AppConstant.time_label[deviceId], SettingsActivity.this);
					System.out.println("******ovenTime 1 = " + ovenTime);
					ovenTime = 1;//Integer.parseInt(timeStr);
				}
			}
			mode = oven.getWorkMode() - 1;
			if(mode < AppConstant.MODE_1){
				mode = AppConstant.MODE_1;
			}

			sideBar.setWorkStatus(deviceId, workState);
			
			if(ovenworking.getVisibility() == View.VISIBLE){
				myTitleBar.setLockStatus(oven.isChildrenLock());
				myTitleBar.setLightStatus(oven.isLightOn());
			}

			System.out.println("****** getWorkMode = " + oven.getWorkMode());
			if(workState != AppConstant.COOKING_WAIT){
				if(oven.getWorkMode() > 0){
					ovenWorkingView.updateMode(mode);
				}else{
					ovenWorkingView.updateMode(oven.getWorkMode());
				}
			}


			//titlebar.set(deviceId, oven.getWorkStatus() - 1);
	    	tempRemainTime = oven.getRemainTime();
	    	ovenWorkingView.setRemainTime(tempRemainTime);
	    	System.out.println("******tempRemainTime 1 = " + tempRemainTime + ", remainTime = " + ovenRemainTime);
	    	ovenWorkingView.setTimeView(tempRemainTime);
	    	tempTemp = oven.getReallyTemperature();
	    	ovenWorkingView.setTempView(tempTemp);
	    	tempSettingsTemp = oven.getSettingTemperature();
	    	ovenWorkingView.setSettingsTempView(tempSettingsTemp);
	    	if(tempRemainTime != ovenRemainTime){
	    		ovenRemainTime = tempRemainTime;
	        	
	        	if(ovenRemainTime > ovenTime){
	        		ovenTime = ovenRemainTime;
	        		ovenWorkingView.setSettingsTime(ovenTime);
	        		System.out.println("******saveSharedPreData 6 time_label = " + AppConstant.time_label[id] + ", tempRemainTime = " +  tempRemainTime);
	        	  	AppStatic.saveSharedPreData(AppConstant.time_label[deviceId], Integer.toString(ovenTime),SettingsActivity.this);
	        	  	calcProStop(ovenTime);
	        	}
	        	
	        	tempProgress = 0;
	        	if(ovenTime == 0){
	        		tempProgress = 0;
	        	}else{
	        		tempProgress = (ovenTime - ovenRemainTime)*100/ovenTime;
	        	}
	        	System.out.println("******handleUpdateMessage remainTime = " + ovenRemainTime + ", time = " + ovenTime + ", tempProgress = " + tempProgress);
	        	if(tempProgress != ovenProgress){
	        		ovenProgress = tempProgress;
	            	setPercentView(ovenProgress);
	        	}
	    	}
	 
	    	tempTemp = oven.getReallyTemperature();
	    	System.out.println("***** tempTemp 2= " + tempTemp);
	    	if(tempTemp != actualTemp){
	    		actualTemp = tempTemp;
	    		//setTempView(actualTemp);
	    	}

			//mode = oven.getWorkMode() - 1;
			//workState = oven.getWorkStatus() - 1;
			System.out.println("***** workState = " + workState);
			ovenWorkingView.setCookingState(workState);
			
			break;
			
			
			
		case BaseProtocol.TYPE_HOOD:
			Hood hood = (Hood)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_HOOD - 1;
			
			workState = hood.getWorkStatus();
			
			if(workState < AppConstant.COOKING_WAIT){
				workState = AppConstant.COOKING_WAIT;
			}
			hoodWorkingView.setWorkingState(workState);
			sideBar.setWorkStatus(deviceId, workState);

			if(hoodworking.getVisibility() == View.VISIBLE){
				myTitleBar.setLightStatus(hood.isLightOn());
			}
			if(hoodworking.getVisibility() == View.VISIBLE){
				hoodWorkingView.setSpeed(hood.getMotorSpeed());
				hoodWorkingView.setTimeView(hood.getRunningTime());
			}
			break;
			
		case BaseProtocol.TYPE_MICROWAVE:
			Microwave microwave = (Microwave)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_MICROWAVE - 1;
			workState = microwave.getWorkStatus() - 1;
			
			if(workState < AppConstant.COOKING_WAIT){
				workState = AppConstant.COOKING_WAIT;
			}else if(workState > AppConstant.COOKING_FINISH){
				workState = AppConstant.COOKING_FINISH;
			}
			mode = microwave.getWorkMode() - 1;
			
			if(mode < AppConstant.MODE_1){
				mode = AppConstant.MODE_1;
			}else if(mode > AppConstant.MODE_5){
				mode = AppConstant.MODE_5;
			}
			sideBar.setWorkStatus(deviceId, workState);

			System.out.println("****** getWorkMode = " + microwave.getWorkMode());
			if(workState != AppConstant.COOKING_WAIT){
				if(microwave.getWorkMode() > 0){
					mwoWorkingView.updateMode(mode);
				}else{
					mwoWorkingView.updateMode(microwave.getWorkMode());
				}
			}
			
			switch(mode){
			case AppConstant.MODE_1:
				power = microwave.getPowerLevel() - 1;
				mwoWorkingView.setSettingsPower(power);
		    	tempTemp = microwave.getReallyTemperature();
		    	mwoWorkingView.setTempView(tempTemp);
		    	tempSettingsTemp = microwave.getSettingTemperature();
		    	mwoWorkingView.setSettingsTemp(tempSettingsTemp);
		    	System.out.println("***** tempTemp 1= " + tempTemp);
		    	if(tempTemp != actualTemp){
		    		actualTemp = tempTemp;
		    		//setTempView(actualTemp);
		    	}
				//mwoWorkingView.setSelPowerView(power);
				break;
				
			case AppConstant.MODE_3:
		    	tempTemp = microwave.getReallyTemperature();
		    	mwoWorkingView.setTempView(tempTemp);
		    	tempSettingsTemp = microwave.getSettingTemperature();
		    	mwoWorkingView.setSettingsTemp(tempSettingsTemp);
		    	System.out.println("***** tempTemp 3= " + tempTemp);
		    	if(tempTemp != actualTemp){
		    		actualTemp = tempTemp;
		    		//setTempView(actualTemp);
		    	}
				break;
				
			case AppConstant.MODE_2:
			case AppConstant.MODE_4:
		    	tempTemp = microwave.getReallyTemperature();
		    	mwoWorkingView.setTempView(tempTemp);
		    	tempSettingsTemp = microwave.getSettingTemperature();
		    	mwoWorkingView.setSettingsTemp(tempSettingsTemp);
		    	System.out.println("***** tempTemp 2= " + tempTemp);
		    	if(tempTemp != actualTemp){
		    		actualTemp = tempTemp;
		    		//setTempView(actualTemp);
		    	}
				break;
				
			case AppConstant.MODE_5:
				weight = microwave.getWeightNumber();
				mwoWorkingView.updateWeight(weight);
		    	tempTemp = microwave.getReallyTemperature();
		    	mwoWorkingView.setTempView(tempTemp);
		    	tempSettingsTemp = microwave.getSettingTemperature();
		    	mwoWorkingView.setSettingsTemp(tempSettingsTemp);
		    	System.out.println("***** tempTemp 5= " + tempTemp);
		    	if(tempTemp != actualTemp){
		    		actualTemp = tempTemp;
		    		//setTempView(actualTemp);
		    	}
		    	
				//weight = microwave.getWeightNumber();
				//mwoWorkingView.updateWeight(weight);
				break;
			default:
				break;
			}
			

			if(workState != AppConstant.COOKING_PAUSE){
		    	tempRemainTime = microwave.getRemainTime();
		    	mwoWorkingView.setRemainTime(tempRemainTime);
		    	System.out.println("******tempRemainTime 2 = " + tempRemainTime + ", remainTime = " + mwoRemainTime);
		    	mwoWorkingView.setTimeView(tempRemainTime);

		    	if(tempRemainTime != mwoRemainTime){
		    		mwoRemainTime = tempRemainTime;
		        	
		        	if(mwoRemainTime > mwoTime){
		        		mwoTime = mwoRemainTime;
		        		mwoWorkingView.setSettingsTime(mwoTime);
		        		System.out.println("******saveSharedPreData 3 time_label = " + AppConstant.time_label[id] + ", time = " +  mwoTime);
		        	  	AppStatic.saveSharedPreData(AppConstant.time_label[deviceId], Integer.toString(mwoTime),SettingsActivity.this);
		        	  	calcProStop(mwoTime);
		        	}
		        	
		        	tempProgress = 0;
		        	if(mwoTime == 0){
		        		tempProgress = 0;
		        	}else{
		        		tempProgress = (mwoTime - mwoRemainTime)*100/mwoTime;
		        	}
		        	System.out.println("******handleUpdateMessage remainTime = " + mwoRemainTime + ", time = " + mwoTime + ", tempProgress = " + tempProgress);
		        	if(tempProgress != mwoProgress){
		        		mwoProgress = tempProgress;
		            	setPercentView(mwoProgress);
		        	}
		    	}
			}

			System.out.println("***** workState = " + workState);
			mwoWorkingView.setCookingState(workState);
			break;
			
		case BaseProtocol.TYPE_ROBOTCLEAN:
			Robotclean robotclean = (Robotclean)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_ROBOTCLEAN - 1;
			workState = robotclean.getWorkStatus();
			System.out.println("******handleUpdateMessage workState = " + workState);
			cleanWorkingView.setWorkingState(workState, robotclean.isFullCharged());
			sideBar.setWorkStatus(deviceId, workState);
			if(cleanworking.getVisibility() == View.VISIBLE){
				if(workState == AppConstant.CLEAN_CHARGING){
					if(robotclean.isFullCharged()){
						cleanWorkingView.setPercent(100);
					}else{
						cleanWorkingView.setPercent(robotclean.batteryValue());
					}
				}else if(workState == AppConstant.CLEAN_AUTO_CHARGING){
					if(robotclean.isFullCharged()){
						cleanWorkingView.setPercent(100);
					}else{
						cleanWorkingView.setPercent(robotclean.batteryValue());
					}
				}else if(workState == AppConstant.CLEAN_WORKING){
					cleanWorkingView.setTimeView(robotclean.workTime());
					cleanWorkingView.setPercent(robotclean.batteryValue());
				}else if(workState == AppConstant.CLEAN_STOP){
					cleanWorkingView.setPercent(robotclean.batteryValue());
				}
			}
			break;
			
		case BaseProtocol.TYPE_MINIOVEN:
			Minioven minioven = (Minioven)MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_MINIOVEN - 1;
			workState = minioven.getWorkStatus() - 1;
			
			if(workState < AppConstant.COOKING_WAIT){
				workState = AppConstant.COOKING_WAIT;
			}else if(workState > AppConstant.COOKING_FINISH){
				workState = AppConstant.COOKING_FINISH;
			}
			
			if(workState == AppConstant.COOKING_WAIT){
				miniovenTime = 0;
			}else if(workState == AppConstant.COOKING_RUN){
				if(miniovenTime == 0){
					String timeStr = AppStatic.getSharedPreData(AppConstant.time_label[deviceId], SettingsActivity.this);
					miniovenTime = Integer.parseInt(timeStr);
				}
			}
			mode = minioven.getWorkMode() - 1;
			
			if(mode < AppConstant.MODE_1){
				mode = AppConstant.MODE_1;
			}else if(mode > AppConstant.MODE_5){
				mode = AppConstant.MODE_5;
			}

			sideBar.setWorkStatus(deviceId, workState);
			//myTitleBar.setLockStatus(minioven.isChildrenLock());
			if(miniovenworking.getVisibility() == View.VISIBLE){
				myTitleBar.setLightStatus(minioven.isLightOn());
			}
			
			System.out.println("****** getWorkMode = " + minioven.getWorkMode());
			if(workState != AppConstant.COOKING_WAIT){
				miniovenWorkingView.updateMode(mode);
			}


			//titlebar.set(deviceId, oven.getWorkStatus() - 1);
	    	tempRemainTime = minioven.getRemainTime();
	    	miniovenWorkingView.setRemainTime(tempRemainTime);
	    	System.out.println("******tempRemainTime 3 = " + tempRemainTime + ", remainTime = " + miniovenRemainTime);
	    	miniovenWorkingView.setTimeView(tempRemainTime);
	    	tempTemp = minioven.getReallyTemperature();
	    	miniovenWorkingView.setTempView(tempTemp);
	    	tempSettingsTemp = minioven.getSettingTemperature();
	    	miniovenWorkingView.setSettingsTempView(tempSettingsTemp);
	    	if(tempRemainTime != miniovenRemainTime){
	    		miniovenRemainTime = tempRemainTime;
	        	
	        	if(miniovenRemainTime > miniovenTime){
	        		miniovenTime = miniovenRemainTime;
	        		miniovenWorkingView.setSettingsTime(miniovenTime);
	        		System.out.println("******saveSharedPreData 2 time_label = " + AppConstant.time_label[id] + ", time = " +  miniovenTime);
	        	  	AppStatic.saveSharedPreData(AppConstant.time_label[deviceId], Integer.toString(miniovenTime),SettingsActivity.this);
	        	  	calcProStop(miniovenTime);
	        	}
	        	
	        	tempProgress = 0;
	        	if(miniovenTime == 0){
	        		tempProgress = 0;
	        	}else{
	        		tempProgress = (miniovenTime - miniovenRemainTime)*100/miniovenTime;
	        	}
	        	System.out.println("******handleUpdateMessage remainTime = " + miniovenRemainTime + ", time = " + miniovenTime + ", tempProgress = " + tempProgress);
	        	if(tempProgress != miniovenProgress){
	        		miniovenProgress = tempProgress;
	            	setPercentView(miniovenProgress);
	        	}
	    	}
	 
	    	tempTemp = minioven.getReallyTemperature();
	    	System.out.println("***** tempTemp 2= " + tempTemp);
	    	if(tempTemp != actualTemp){
	    		actualTemp = tempTemp;
	    		//setTempView(actualTemp);
	    	}

			//mode = oven.getWorkMode() - 1;
			//workState = oven.getWorkStatus() - 1;
			System.out.println("***** workState = " + workState);
			miniovenWorkingView.setCookingState(workState);
			break;
		default:
			return;
		}
	}

	@Override
	public void HideGuide() {
		// TODO Auto-generated method stub
		myviewpager.setVisibility(View.GONE);
	}
	public void initDevice(int id, int state) {
		// TODO Auto-generated method stub
    	mwoworking.setVisibility(View.INVISIBLE);
    	//mwocooking.setVisibility(View.INVISIBLE);
    	ovenworking.setVisibility(View.INVISIBLE);
    	//ovencooking.setVisibility(View.INVISIBLE);
    	miniovenworking.setVisibility(View.INVISIBLE);
    	//miniovencooking.setVisibility(View.INVISIBLE);
    	hoodworking.setVisibility(View.INVISIBLE);
    	hobworking.setVisibility(View.INVISIBLE);
    	cleanworking.setVisibility(View.INVISIBLE);
    	switch(id){
    	case AppConstant.MICROWAVE:
/*    		if(state == AppConstant.WAIT){
        		mwosetings.setVisibility(View.VISIBLE);
    		}else{
    			mwocooking.setVisibility(View.VISIBLE);
    		}*/
    		mwoworking.setVisibility(View.VISIBLE);
    		break;
    	case AppConstant.OVEN:
    		ovenworking.setVisibility(View.VISIBLE);
    		break;
    	case AppConstant.HOOD:
    		hoodworking.setVisibility(View.VISIBLE);
    		break;
    	case AppConstant.HOB:
    		hobworking.setVisibility(View.VISIBLE);
    		break;
    	case AppConstant.MINIOVEN:
    		miniovenworking.setVisibility(View.VISIBLE);
    		break;
    	case AppConstant.CLEAN:
    		cleanworking.setVisibility(View.VISIBLE);
    		break;
    		
    	default:
    		break;
    	}
    	
	}
	/**
     * ��ʼ��ViewPager
*/

    private void initView() {
    	ovenworking = (FrameLayout) findViewById(R.id.ovenworking);
    	//mwosetings = (FrameLayout) findViewById(R.id.mwosetings);
    	mwoworking = (FrameLayout) findViewById(R.id.mwoworking);
    	miniovenworking = (FrameLayout) findViewById(R.id.miniovenworking);
    	//miniovencooking = (FrameLayout) findViewById(R.id.miniovencooking);
    	hoodworking = (FrameLayout) findViewById(R.id.hoodworking);
    	hobworking = (FrameLayout) findViewById(R.id.hobworking);
    	cleanworking = (FrameLayout) findViewById(R.id.cleanworking);
    	
    	initDevice(id, workingState);
    	
    	ovenWorkingView = new OvenWorkingView(SettingsActivity.this, ovenworking, myApplication, id, mode, mHandler);
    	//ovenCookingView = new OvenCookingView(SettingsActivity.this, ovencooking, myApplication, id, mode, workingState, mHandler, 0, this);
    	//mwoSettingsView = new MwoSettingsView(SettingsActivity.this, mwosetings, myApplication, id, mode);
    	mwoWorkingView = new MwoWorkingView(SettingsActivity.this, mwoworking, myApplication, id, mode, mHandler);
    	miniovenWorkingView = new MiniovenWorkingView(SettingsActivity.this, miniovenworking, myApplication, id, mode, mHandler);
    	//miniovenCookingView = new MiniovenCookingView(SettingsActivity.this, miniovencooking, myApplication, id, mode, workingState, mHandler, 0);
    	hoodWorkingView = new HoodWorkingView(SettingsActivity.this, hoodworking, workingState, mHandler);
    	hobWorkingView = new HobWorkingView(SettingsActivity.this, hobworking, myApplication, id, mHandler, this);
    	cleanWorkingView = new CleanWorkingView(SettingsActivity.this, cleanworking, workingState, mHandler);
    	
    	
    	settingmain = (MyScrollView)findViewById(R.id.settingmain);
    	titlebar = (FrameLayout) findViewById(R.id.titlebar);
    	myTitleBar = new MyTitlebar(titlebar, myApplication, id, settingmain, this, true);
    	
    	listpage = (FrameLayout) findViewById(R.id.listpage);
    	sideBar = new MySideBar(listpage, myApplication, id, this, myTitleBar);

    }
	
	
	private void initViewPager() {
    	myviewpager = (FrameLayout) findViewById(R.id.myviewpager3);
    	deletebtn = (ImageButton) findViewById(R.id.deletebtn);
    	deletebtn.setOnClickListener(new DeleteBtnOnClickListener());
    	myviewpager.setVisibility(View.GONE);
    	viewpager = (ViewPager)findViewById(R.id.viewpager3);
    	List<View> listViews = new ArrayList<View>();
        LayoutInflater mInflater = LayoutInflater.from(this);
        View view1 = mInflater.inflate(R.layout.hob_page1, null);
        View view2 = mInflater.inflate(R.layout.hob_page2, null);
        listViews.add(view1);
        listViews.add(view2);
        viewpager.setAdapter(new MyPagerAdapter(listViews));
        //mPager.setOnPageChangeListener(new MyViewPagerOnPageChangeListener());
        viewpager.setCurrentItem(0);
    }
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		myApplication=(MyApplication) getApplication();

		Intent intent = getIntent();
		id = intent.getIntExtra("ID", AppConstant.OVEN);
		workingState = intent.getIntExtra("status", AppConstant.COOKING_WAIT);
		if(workingState > AppConstant.COOKING_FINISH){
			workingState = AppConstant.COOKING_FINISH;
		}
		registerBroadcastReceiver();
		initViewPager();
		initView();
		ScreenControl.init(this);
		
		myApplication.addActivity(this);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);
		exitWorking();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ScreenControl.screenDiswake();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ScreenControl.screenWake();
	}
    private void registerBroadcastReceiver() {
		final IntentFilter filter = new IntentFilter();
		filter.addAction(AppConstant.ACTION_DEVICE_REPLY);
		filter.addAction(AppConstant.ACTION_DEVICE_UPDATE);
		filter.addAction(AppConstant.ACTION_DEVICE_TIMEOUT);
		registerReceiver(mReceiver, filter);
	}
@Override
	public void selectDevice(int id) {
		// TODO Auto-generated method stub
		this.id = id;
		workingState = myApplication.getWorkStatus(id);
		
		initDevice(id, workingState);
		
    	switch(id){
    	case AppConstant.MICROWAVE:
    		mwoWorkingView.setDeviceId(id);
    		break;
    	case AppConstant.OVEN:
    		//ovenSettingsView.setDeviceId(id);
    		break;
    	case AppConstant.HOOD:

    		break;
    	case AppConstant.HOB:
    		
    		break;
    	case AppConstant.MINIOVEN:

    		miniovenWorkingView.setDeviceId(id);
    		break;
    	case AppConstant.CLEAN:

    		break;
    		
    	default:
    		break;
    	}
		setViewClickable(false);
	}
	private void setPercentView(int percent) {
    	if(ovenworking.getVisibility() == View.VISIBLE){
    		System.out.println("******setPercentView progress = " + percent );
    		ovenWorkingView.setPercentView(percent);
    	}else if(mwoworking.getVisibility() == View.VISIBLE){
    		mwoWorkingView.setPercentView(percent);
    	}else if(miniovenworking.getVisibility() == View.VISIBLE){
    		miniovenWorkingView.setPercentView(percent);
    	}
    }
	@Override
	public void setViewClickable(boolean clickable) {
		// TODO Auto-generated method stub
		myTitleBar.setBtnClickable(clickable);
		//ovenSettingsView.setBtnClickable(clickable);
		//mwoSettingsView.setBtnClickable(clickable);
		if(mwoworking.getVisibility() == View.VISIBLE){
			mwoWorkingView.setBtnClickable(clickable);
		}
		if(ovenworking.getVisibility() == View.VISIBLE){
			ovenWorkingView.setBtnClickable(clickable);
		}
		if(miniovenworking.getVisibility() == View.VISIBLE){
			miniovenWorkingView.setBtnClickable(clickable);
		}
		if(hobworking.getVisibility() == View.VISIBLE){
			hobWorkingView.setBtnClickable(clickable);
		}
		if(hoodworking.getVisibility() == View.VISIBLE){
			hoodWorkingView.setBtnClickable(clickable);
		}
		
		if(cleanworking.getVisibility() == View.VISIBLE){
			cleanWorkingView.setBtnClickable(clickable);
		}
		sideBar.setBtnClickable(!clickable);
	}
}
