package com.midea.iot.activities;

import android.content.Context;
import android.os.PowerManager;


public class ScreenControl {

	static PowerManager pm = null;
	static PowerManager.WakeLock mWakeLock = null;
	public static void init(Context context){
		pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag"); 
	}

	public static void screenDiswake(){
		// in onPause() call 
		mWakeLock.release();
	}
	public static void screenWake(){
		// in onResume() call
		mWakeLock.acquire(); 
	}
}
