package com.midea.iot.pad;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.midea.iot.R;
import com.midea.iot.activities.AppConstant;
import com.midea.iot.activities.MainActivity;
import com.midea.iot.devices.Hob;
import com.midea.iot.devices.Hood;
import com.midea.iot.devices.Microwave;
import com.midea.iot.devices.Minioven;
import com.midea.iot.devices.Oven;
import com.midea.iot.devices.Robotclean;
import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.HobProtocol.HobPosition;
import com.midea.iot.protocol.HobStatus;
import com.midea.iot.protocol.HoodProtocol;
import com.midea.iot.protocol.MicrowaveProtocol;
import com.midea.iot.protocol.MiniovenProtocol;
import com.midea.iot.protocol.OvenProtocol;
import com.midea.iot.protocol.RobotcleanProtocol;
import com.midea.iot.service.DeviceEventListener;
import com.midea.iot.service.DeviceManager;

public class MainTvActivity extends Activity {

	private static final String TAG = "MainTvActivity";
	private static final boolean DEBUG = true;

	private FrameLayout mMicrowaveLayout;
	private TextView mMicrowaveTitle;
	private CusTvProgressBar mMicrowaveMainWorkingBar;
	private ImageView mMicrowaveMode;
	private CusTvTimeBar mMicrowaveTimeBar;
	private ImageView mMicrowaveSettingIconTempr;
	private CusTvInfoBox mMicrowaveInfoTempr;
	private ImageView mMicrowaveSettingIconPower;
	private CusTvInfoBox mMicrowaveInfoPower;
	private CusTvProgressBar mMicrowaveDefrostWorkingBar;
	private ImageView mMicrowaveDisconnectWarning;

	private FrameLayout mOvenLayout;
	private TextView mOvenTitle;
	private CusTvProgressBar mOvenMainWorkingBar;
	private ImageView mOvenMode;
	private CusTvTimeBar mOvenTimeBar;
	private ImageView mOvenIconTempr;
	private CusTvInfoBox mOvenInfoTempr;
	private ImageView mOvenIconPower;
	private CusTvInfoBox mOvenInfoPower;
	private ImageView mOvenDisconnectWarning;

	private FrameLayout mHoodLayout;
	private TextView mHoodTitle;
	private ImageView mHoodMode;
	private CusTvTimeBar mHoodTimeBar;
	private TextView mHoodMotorSpeed;
	private ImageView mHoodDisconnectWarning;
	ColorStateList mCslWorkingOn;
	ColorStateList mCslWorkingOff;

	private FrameLayout mHobLayout;
	private TextView mHobTitle;
	private CusTvHobInfoBar mHobInfoBar_1;
	private CusTvHobInfoBar mHobInfoBar_2;
	private CusTvHobInfoBar mHobInfoBar_3;
	private CusTvHobInfoBar mHobInfoBar_4;
	private ImageView mHobPos_1;
	private ImageView mHobPos_2;
	private ImageView mHobPos_3;
	private ImageView mHobPos_4;
	private ImageView mHobDisconnectWarning;
	private ImageView mHobDisableWarning;

	private FrameLayout mMiniovenLayout;
	private TextView mMiniovenTitle;
	private CusTvProgressBar mMiniovenMainWorkingBar;
	private ImageView mMiniovenMode;
	private CusTvTimeBar mMiniovenTimeBar;
	private ImageView mMiniovenIconTempr;
	private CusTvInfoBox mMiniovenInfoTempr;
	private ImageView mMiniovenIconPower;
	private CusTvInfoBox mMiniovenInfoPower;
	private TextView mMiniovenRotisserie;
	private CusTvProgressBar mMiniovenRotisserieWorkingBar;
	private CusTvTimeBar mMiniovenRotisserieTimeBar;
	private ImageView mMiniovenDisconnectWarning;

	private FrameLayout mRobotcleanLayout;
	private TextView mRobotcleanTitle;
	private CusTvRobotCleanBattery mRobotcleanBattery;
	private CusTvTimeBar mRobotcleanTimeBar;
	private ImageView mRobotcleanAutoMode;
	private ImageView mRobotcleanDisconnectWarning;

	private static final int MSG_DEVICE_REPLY = 101;
	private static final int MSG_DEVICE_UPDATE = 102;
	private static final int MSG_DEVICE_TIMEOUT = 103;

	private static final int MSG_MICROWAVE_REPLY = 1001;
	private static final int MSG_MICROWAVE_UPDATE = 1002;
	private static final int MSG_MICROWAVE_TIMEOUT = 1003;

	private static final int MSG_OVEN_REPLY = 1004;
	private static final int MSG_OVEN_UPDATE = 1005;
	private static final int MSG_OVEN_TIMEOUT = 1006;

	private static final int MSG_MINIOVEN_REPLY = 1007;
	private static final int MSG_MINIOVEN_UPDATE = 1008;
	private static final int MSG_MINIOVEN_TIMEOUT = 1009;

	private static final int MSG_ROBOTCLEAN_REPLY = 1011;
	private static final int MSG_ROBOTCLEAN_UPDATE = 1012;
	private static final int MSG_ROBOTCLEAN_TIMEOUT = 1013;

	private static final int MSG_HOOD_REPLY = 1014;
	private static final int MSG_HOOD_UPDATE = 1015;
	private static final int MSG_HOOD_TIMEOUT = 1016;

	private static final int MSG_HOB_REPLY = 1017;
	private static final int MSG_HOB_UPDATE = 1018;
	private static final int MSG_HOB_TIMEOUT = 1019;

	private DeviceManager mMicrowaveManager;
	private DeviceManager mMiniOvenManager;
	private DeviceManager mOvenManager;
	private DeviceManager mHoodManager;
	private DeviceManager mHobManager;
	private DeviceManager mRobotCleanManager;
	
	private Microwave mMicrowaveDevice;
	private Oven mOvenDevice;
	private Hood mHoodDevice;
	private Hob mHobDevice;
	private Minioven mMiniovenDevice;
	private Robotclean mRobotcleanDevice;
	
	private boolean mMicrowaveEnable = false;
	private boolean mOvenEnable = false;
	private boolean mHoodEnable = false;
	private boolean mHobEnable = false;
	private boolean mMiniovenEnable = false;
	private boolean mRobotcleanEnable = false;
	
	
	private ColorStateList mCslTextOn;
	private ColorStateList mCslTextOff;
	
	private static final int DEFAULT_MICROWAVE_MODE = 3;
	private static final int DEFAULT_OVEN_MODE = 3;
	private static final int DEFAULT_HOOD_MODE = 2;
	private static final int DEFAULT_MINIOVEN_MODE = 2;
	
	private static final int TIMEOUT_DISCONNECT = 10000;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_DEVICE_REPLY:
				handleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_DEVICE_UPDATE:
				handleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_DEVICE_TIMEOUT:
				handleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_MICROWAVE_REPLY:
//				microwaveHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_MICROWAVE_UPDATE:
				microwaveHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_MICROWAVE_TIMEOUT:
				microwaveHandleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_OVEN_REPLY:
//				ovenHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_OVEN_UPDATE:
				ovenHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_OVEN_TIMEOUT:
				ovenHandleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_HOOD_REPLY:
//				hoodHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_HOOD_UPDATE:
				hoodHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_HOOD_TIMEOUT:
				hoodHandleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_HOB_REPLY:
//				hobHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_HOB_UPDATE:
				hobHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_HOB_TIMEOUT:
				hobHandleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_MINIOVEN_REPLY:
//				miniovenHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_MINIOVEN_UPDATE:
				miniovenHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_MINIOVEN_TIMEOUT:
				miniovenHandleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_ROBOTCLEAN_REPLY:
//				robotcleanHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_ROBOTCLEAN_UPDATE:
				robotcleanHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_ROBOTCLEAN_TIMEOUT:
				robotcleanHandleTimeoutMessage((byte[]) msg.obj);
				break;

			default:
				break;
			}
		}

	};

	private int mMicrowaveSettingTime = 0;

	private int mOvenSettingTime = 0;

	private int mOvenPreRemainTime = 0;

	private long mOvenStartTime = 0;

	private boolean mHob1IsRunning = false;

	private boolean mHob1IsModeA = false;
	private long mHob1StarTime = 0;

	private int mHob1PreRemainTime = 0;

	private boolean mHob2IsRunning = false;

	private boolean mHob2IsModeA = false;
	private long mHob2StarTime = 0;
	private int mHob2PreRemainTime = 0;
	private boolean mHob3IsRunning = false;

	private boolean mHob3IsModeA = false;

	private long mHob3StarTime = 0;

	private int mHob3PreRemainTime = 0;

	private boolean mHob4IsRunning = false;

	private boolean mHob4IsModeA = false;
	
	private long mHob4StarTime = 0;
	
	private int mHob4PreRemainTime = 0;
	private int mMiniovenSettingTime = 0;
	Runnable mMicrowaveTimeoutRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mMicrowaveDisconnectWarning.setVisibility(View.GONE);
			setMicrowaveStauts(false);
		}
	};
	Runnable mOvenTimeoutRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mOvenDisconnectWarning.setVisibility(View.GONE);
			setOvenStauts(false);
		}
	};
	
	Runnable mHoodTimeoutRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mHoodDisconnectWarning.setVisibility(View.GONE);
			setHoodStauts(false);
		}
	};
	
	Runnable mHobTimeoutRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mHobDisconnectWarning.setVisibility(View.GONE);
			setHobStauts(false);
		}
	};
	Runnable mHobCompleteWarningRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mHobDisableWarning.setVisibility(View.GONE);
		}
	};
	Runnable mMiniovenTimeoutRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mMiniovenDisconnectWarning.setVisibility(View.GONE);
			setMiniovenStauts(false);
		}
	};
	Runnable mRobotcleanTimeoutRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mRobotcleanDisconnectWarning.setVisibility(View.GONE);
			setRobotcleanStauts(false);
		}
	};
	
	private DeviceEventListener mOvenListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_OVEN_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_OVEN_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_OVEN_TIMEOUT, event).sendToTarget();
		}
	};
	
	private DeviceEventListener mMicroWaveListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_MICROWAVE_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_MICROWAVE_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_MICROWAVE_TIMEOUT, event).sendToTarget();
		}
	};
	private DeviceEventListener mHoodListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_HOOD_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_HOOD_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_HOOD_TIMEOUT, event).sendToTarget();
		}
	};
	private DeviceEventListener mHobListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_HOB_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_HOB_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_HOB_TIMEOUT, event).sendToTarget();
		}
	};
	private DeviceEventListener mMiniovenListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_MINIOVEN_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_MINIOVEN_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_MINIOVEN_TIMEOUT, event).sendToTarget();
		}
	};
	
	private DeviceEventListener mRobotCleanListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_ROBOTCLEAN_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_ROBOTCLEAN_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mHandler.obtainMessage(MSG_ROBOTCLEAN_TIMEOUT, event)
					.sendToTarget();
		}
	};
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (DEBUG)
				Log.d(TAG, "***********onReceive");
			final String action = intent.getAction();
			if (AppConstant.ACTION_DEVICE_REPLY.equals(action)) {
				// handleReplyMessage(intent.getByteArrayExtra("receive_data"));
				mHandler.obtainMessage(MSG_DEVICE_REPLY,
						intent.getByteArrayExtra("receive_data"))
						.sendToTarget();

			} else if (AppConstant.ACTION_DEVICE_UPDATE.equals(action)) {
				// handleUpdateMessage(intent.getByteArrayExtra("receive_data"));
				mHandler.obtainMessage(MSG_DEVICE_UPDATE,
						intent.getByteArrayExtra("receive_data"))
						.sendToTarget();
			} else if (AppConstant.ACTION_DEVICE_TIMEOUT.equals(action)) {
				// handleTimeoutMessage(intent.getByteArrayExtra("receive_data"));
				mHandler.obtainMessage(MSG_DEVICE_TIMEOUT,
						intent.getByteArrayExtra("receive_data"))
						.sendToTarget();
			} else if (AppConstant.ACTION_EXIT.equals(action)) {
				if (DEBUG)
					Log.d(TAG, "MainTvActivity Finished!!");
				finish();
			}
		}
	};
	private int[][] mMicrowaveModeList = { 
			{0, 0},
			{R.drawable.tv_mwo_mode_1_on, R.drawable.tv_mwo_mode_1_off},
			{R.drawable.tv_mwo_mode_2_on, R.drawable.tv_mwo_mode_2_off},
			{R.drawable.tv_mwo_mode_3_on, R.drawable.tv_mwo_mode_3_off},
			{R.drawable.tv_mwo_mode_4_on, R.drawable.tv_mwo_mode_4_off},
			{R.drawable.tv_mwo_mode_5_on, R.drawable.tv_mwo_mode_5_off},
	};
	private int[][] mOvenModeList = {
			{0, 0},
			{R.drawable.tv_oven_mode_1_sel, R.drawable.tv_oven_mode_1_off},
			{R.drawable.tv_oven_mode_2_sel, R.drawable.tv_oven_mode_2_off},
			{R.drawable.tv_oven_mode_3_sel, R.drawable.tv_oven_mode_3_off},
			{R.drawable.tv_oven_mode_4_sel, R.drawable.tv_oven_mode_4_off},
			{R.drawable.tv_oven_mode_5_sel, R.drawable.tv_oven_mode_5_off},
			{R.drawable.tv_oven_mode_6_sel, R.drawable.tv_oven_mode_6_off},
			{R.drawable.tv_oven_mode_7_sel, R.drawable.tv_oven_mode_7_off},
			{R.drawable.tv_oven_mode_8_sel, R.drawable.tv_oven_mode_8_off},
	};
	private int[][] mHoodModeList = {
			{0, 0},
			{R.drawable.tv_hood_mode_1_on, R.drawable.tv_hood_mode_1_off},
			{R.drawable.tv_hood_mode_2_on, R.drawable.tv_hood_mode_2_off},
			{R.drawable.tv_hood_mode_3_on, R.drawable.tv_hood_mode_3_off},
	};
	
	private int[][] mMiniovenModeList = {
			{0, 0},
			{R.drawable.tv_mini_mode_1_sel, R.drawable.tv_mini_mode_1_off},	
			{R.drawable.tv_mini_mode_2_sel, R.drawable.tv_mini_mode_2_off},	
			{R.drawable.tv_mini_mode_3_sel, R.drawable.tv_mini_mode_3_off},	
			{R.drawable.tv_mini_mode_4_sel, R.drawable.tv_mini_mode_4_off},	
			{R.drawable.tv_mini_mode_5_sel, R.drawable.tv_mini_mode_5_off},	
	};

	private void handleReplyMessage(byte[] msg) {
		if (DEBUG)
			Log.d(TAG, "Received Reply Message!");
		byte type = msg[BaseProtocol.PROTOCOL_TYPE];
		switch (type) {
		case BaseProtocol.TYPE_MICROWAVE:
			microwaveHandleReplyMessage(msg);
			break;
		case BaseProtocol.TYPE_OVEN:
			ovenHandleReplyMessage(msg);
			break;
		case BaseProtocol.TYPE_HOOD:
			hoodHandleReplyMessage(msg);
			break;
		case BaseProtocol.TYPE_HOB:
			hobHandleReplyMessage(msg);
			break;
		case BaseProtocol.TYPE_MINIOVEN:
			miniovenHandleReplyMessage(msg);
			break;
		case BaseProtocol.TYPE_ROBOTCLEAN:
			robotcleanHandleReplyMessage(msg);
			break;

		default:
			break;
		}
	}

	private void handleTimeoutMessage(byte[] msg) {
		if (DEBUG)
			Log.d(TAG, "Received Timeout Message!");
		byte type = msg[BaseProtocol.PROTOCOL_TYPE];
		switch (type) {
		case BaseProtocol.TYPE_MICROWAVE:
			microwaveHandleTimeoutMessage(msg);
			break;
		case BaseProtocol.TYPE_OVEN:
			ovenHandleTimeoutMessage(msg);
			break;
		case BaseProtocol.TYPE_HOOD:
			hoodHandleTimeoutMessage(msg);
			break;
		case BaseProtocol.TYPE_HOB:
			hobHandleTimeoutMessage(msg);
			break;
		case BaseProtocol.TYPE_MINIOVEN:
			miniovenHandleTimeoutMessage(msg);
			break;
		case BaseProtocol.TYPE_ROBOTCLEAN:
			robotcleanHandleTimeoutMessage(msg);
			break;

		default:
			break;
		}
	}
	
	private void handleUpdateMessage(byte[] msg) {
		if (DEBUG)
			Log.d(TAG, "Received Update Message!");
		byte type = msg[BaseProtocol.PROTOCOL_TYPE];
		switch (type) {
		case BaseProtocol.TYPE_MICROWAVE:
			microwaveHandleUpdateMessage(msg);
			break;
		case BaseProtocol.TYPE_OVEN:
			ovenHandleUpdateMessage(msg);
			break;
		case BaseProtocol.TYPE_HOOD:
			hoodHandleUpdateMessage(msg);
			break;
		case BaseProtocol.TYPE_HOB:
			hobHandleUpdateMessage(msg);
			break;
		case BaseProtocol.TYPE_MINIOVEN:
			miniovenHandleUpdateMessage(msg);
			break;
		case BaseProtocol.TYPE_ROBOTCLEAN:
			robotcleanHandleUpdateMessage(msg);
			break;

		default:
			break;
		}
	}

	private void hob1UpdateStatus(HobStatus hobStatus) {
		boolean hobRunning = (hobStatus.status == 1 ? true : false);
		if(mHob1IsRunning != hobRunning) {
			mHob1IsRunning = hobRunning;
			
			if(mHob1IsRunning) {
				mHobInfoBar_1.setHobInfoBarStatus(true);
				mHobPos_1.setImageResource(R.drawable.tv_hob_pos_1_enable);
				
				int timeRemain = hobStatus.timeLeft;
				if(timeRemain == 0) {
					//A mode
					mHob1IsModeA = true;
					mHobInfoBar_1.setHobInfo(hobStatus.powerLevel, timeRemain);
					mHob1StarTime = System.currentTimeMillis();
				} else {
					mHob1IsModeA = false;
					mHobInfoBar_1.setHobInfo(hobStatus.powerLevel, timeRemain);
					mHobInfoBar_1.updateTime(timeRemain);
					mHob1PreRemainTime = timeRemain;
					mHob1StarTime = System.currentTimeMillis();
				}
			} else {
				mHobInfoBar_1.setHobInfoBarStatus(false);
				mHobPos_1.setImageResource(R.drawable.tv_hob_pos_1_disable);
				
				mHobDisableWarning.setVisibility(View.VISIBLE);
				mHandler.removeCallbacks(mHobCompleteWarningRunnable);
				mHandler.postDelayed(mHobCompleteWarningRunnable, 5000);
			}
			
		} else {
			
			if(mHob1IsRunning) {
				int timeRemain = hobStatus.timeLeft;
				if(timeRemain == 0) {
					if(mHob1IsModeA) {
						//always A mode
						long time = (System.currentTimeMillis() - mHob1StarTime) / 1000;
						int elapseTime = time < 1 ? 1 : (int)time; 
						mHobInfoBar_1.updateTime(elapseTime);
						mHobInfoBar_1.setPowerLevel(hobStatus.powerLevel);
					} else {
						//Change to Mode A
						mHob1IsModeA = false;
						mHobInfoBar_1.setHobInfo(hobStatus.powerLevel, timeRemain);
						mHobInfoBar_1.updateTime(timeRemain);
						mHob1StarTime = System.currentTimeMillis();
					}
				} else {
					if(!mHob1IsModeA) {
						//always B Mode
						if(mHob1PreRemainTime == timeRemain) {
							//in the on minute
							long time = (System.currentTimeMillis() - mHob1StarTime) / 1000;
							int elapseTime = time < 1 ? 1 : (int)time; 
							int remainTime = timeRemain - elapseTime;
							mHobInfoBar_1.updateTime(remainTime);
							mHobInfoBar_1.setPowerLevel(hobStatus.powerLevel);
						} else {
							mHob1PreRemainTime = timeRemain;
							mHob1StarTime = System.currentTimeMillis();
							mHobInfoBar_1.updateTime(timeRemain);
							mHobInfoBar_1.setPowerLevel(hobStatus.powerLevel);
						}
					} else {
						//Change to Mode B
						mHob1IsModeA = false;
						mHobInfoBar_1.setHobInfo(hobStatus.powerLevel, timeRemain);
						mHobInfoBar_1.updateTime(timeRemain);
						mHob1PreRemainTime = timeRemain;
						mHob1StarTime = System.currentTimeMillis();
					}
				}
			}
		}
	}

	private void hob2UpdateStatus(HobStatus hobStatus) {
		boolean hobRunning = (hobStatus.status == 1 ? true : false);
		if(mHob2IsRunning != hobRunning) {
			mHob2IsRunning = hobRunning;
			
			if(mHob2IsRunning) {
				mHobInfoBar_2.setHobInfoBarStatus(true);
				mHobPos_2.setImageResource(R.drawable.tv_hob_pos_2_enable);
				
				int timeRemain = hobStatus.timeLeft;
				if(timeRemain == 0) {
					//A mode
					mHob2IsModeA = true;
					mHobInfoBar_2.setHobInfo(hobStatus.powerLevel, timeRemain);
					mHob2StarTime = System.currentTimeMillis();
				} else {
					mHob2IsModeA = false;
					mHobInfoBar_2.setHobInfo(hobStatus.powerLevel, timeRemain);
					mHobInfoBar_2.updateTime(timeRemain);
					mHob2PreRemainTime = timeRemain;
					mHob2StarTime = System.currentTimeMillis();
				}
			} else {
				mHobInfoBar_2.setHobInfoBarStatus(false);
				mHobPos_2.setImageResource(R.drawable.tv_hob_pos_2_disable);
				
				mHobDisableWarning.setVisibility(View.VISIBLE);
				mHandler.removeCallbacks(mHobCompleteWarningRunnable);
				mHandler.postDelayed(mHobCompleteWarningRunnable, 5000);
			}
			
		} else {
			
			if(mHob2IsRunning) {
				int timeRemain = hobStatus.timeLeft;
				if(timeRemain == 0) {
					if(mHob2IsModeA) {
						//always A mode
						long time = (System.currentTimeMillis() - mHob2StarTime) / 1000;
						int elapseTime = time < 1 ? 1 : (int)time; 
						mHobInfoBar_2.updateTime(elapseTime);
						mHobInfoBar_2.setPowerLevel(hobStatus.powerLevel);
					} else {
						//Change to Mode A
						mHob2IsModeA = false;
						mHobInfoBar_2.setHobInfo(hobStatus.powerLevel, timeRemain);
						mHobInfoBar_2.updateTime(timeRemain);
						mHob2StarTime = System.currentTimeMillis();
					}
				} else {
					if(!mHob2IsModeA) {
						//always B Mode
						if(mHob2PreRemainTime == timeRemain) {
							//in the on minute
							long time = (System.currentTimeMillis() - mHob2StarTime) / 1000;
							int elapseTime = time < 1 ? 1 : (int)time; 
							int remainTime = timeRemain - elapseTime;
							mHobInfoBar_2.updateTime(remainTime);
							mHobInfoBar_2.setPowerLevel(hobStatus.powerLevel);
						} else {
							mHob2PreRemainTime = timeRemain;
							mHob2StarTime = System.currentTimeMillis();
							mHobInfoBar_2.updateTime(timeRemain);
							mHobInfoBar_2.setPowerLevel(hobStatus.powerLevel);
						}
					} else {
						//Change to Mode B
						mHob2IsModeA = false;
						mHobInfoBar_2.setHobInfo(hobStatus.powerLevel, timeRemain);
						mHobInfoBar_2.updateTime(timeRemain);
						mHob2PreRemainTime = timeRemain;
						mHob2StarTime = System.currentTimeMillis();
					}
				}
			}
		}
	}

	private void hob3UpdateStatus(HobStatus hobStatus) {
		boolean hobRunning = (hobStatus.status == 1 ? true : false);
		if(mHob3IsRunning != hobRunning) {
			mHob3IsRunning = hobRunning;
			
			if(mHob3IsRunning) {
				mHobInfoBar_3.setHobInfoBarStatus(true);
				mHobPos_3.setImageResource(R.drawable.tv_hob_pos_3_enable);
				
				int timeRemain = hobStatus.timeLeft;
				if(timeRemain == 0) {
					//A mode
					mHob3IsModeA = true;
					mHobInfoBar_3.setHobInfo(hobStatus.powerLevel, timeRemain);
					mHob3StarTime = System.currentTimeMillis();
				} else {
					mHob3IsModeA = false;
					mHobInfoBar_3.setHobInfo(hobStatus.powerLevel, timeRemain);
					mHobInfoBar_3.updateTime(timeRemain);
					mHob3PreRemainTime = timeRemain;
					mHob3StarTime = System.currentTimeMillis();
				}
			} else {
				mHobInfoBar_3.setHobInfoBarStatus(false);
				mHobPos_3.setImageResource(R.drawable.tv_hob_pos_3_disable);
				
				mHobDisableWarning.setVisibility(View.VISIBLE);
				mHandler.removeCallbacks(mHobCompleteWarningRunnable);
				mHandler.postDelayed(mHobCompleteWarningRunnable, 5000);
			}
			
		} else {
			
			if(mHob3IsRunning) {
				int timeRemain = hobStatus.timeLeft;
				if(timeRemain == 0) {
					if(mHob3IsModeA) {
						//always A mode
						long time = (System.currentTimeMillis() - mHob3StarTime) / 1000;
						int elapseTime = time < 1 ? 1 : (int)time; 
						mHobInfoBar_3.updateTime(elapseTime);
						mHobInfoBar_3.setPowerLevel(hobStatus.powerLevel);
					} else {
						//Change to Mode A
						mHob3IsModeA = false;
						mHobInfoBar_3.setHobInfo(hobStatus.powerLevel, timeRemain);
						mHobInfoBar_3.updateTime(timeRemain);
						mHob3StarTime = System.currentTimeMillis();
					}
				} else {
					if(!mHob3IsModeA) {
						//always B Mode
						if(mHob3PreRemainTime == timeRemain) {
							//in the on minute
							long time = (System.currentTimeMillis() - mHob3StarTime) / 1000;
							int elapseTime = time < 1 ? 1 : (int)time; 
							int remainTime = timeRemain - elapseTime;
							mHobInfoBar_3.updateTime(remainTime);
							mHobInfoBar_3.setPowerLevel(hobStatus.powerLevel);
						} else {
							mHob3PreRemainTime = timeRemain;
							mHob3StarTime = System.currentTimeMillis();
							mHobInfoBar_3.updateTime(timeRemain);
							mHobInfoBar_3.setPowerLevel(hobStatus.powerLevel);
						}
					} else {
						//Change to Mode B
						mHob3IsModeA = false;
						mHobInfoBar_3.setHobInfo(hobStatus.powerLevel, timeRemain);
						mHobInfoBar_3.updateTime(timeRemain);
						mHob3PreRemainTime = timeRemain;
						mHob3StarTime = System.currentTimeMillis();
					}
				}
			}
		}
	}

	private void hob4UpdateStatus(HobStatus hobStatus) {
		boolean hobRunning = (hobStatus.status == 1 ? true : false);
		if(mHob4IsRunning != hobRunning) {
			mHob4IsRunning = hobRunning;
			
			if(mHob4IsRunning) {
				mHobInfoBar_4.setHobInfoBarStatus(true);
				mHobPos_4.setImageResource(R.drawable.tv_hob_pos_4_enable);
				
				int timeRemain = hobStatus.timeLeft;
				if(timeRemain == 0) {
					//A mode
					mHob4IsModeA = true;
					mHobInfoBar_4.setHobInfo(hobStatus.powerLevel, timeRemain);
					mHob4StarTime = System.currentTimeMillis();
				} else {
					mHob4IsModeA = false;
					mHobInfoBar_4.setHobInfo(hobStatus.powerLevel, timeRemain);
					mHobInfoBar_4.updateTime(timeRemain);
					mHob4PreRemainTime = timeRemain;
					mHob4StarTime = System.currentTimeMillis();
				}
			} else {
				mHobInfoBar_4.setHobInfoBarStatus(false);
				mHobPos_4.setImageResource(R.drawable.tv_hob_pos_4_disable);
				
				mHobDisableWarning.setVisibility(View.VISIBLE);
				mHandler.removeCallbacks(mHobCompleteWarningRunnable);
				mHandler.postDelayed(mHobCompleteWarningRunnable, 5000);
			}
			
		} else {
			
			if(mHob4IsRunning) {
				int timeRemain = hobStatus.timeLeft;
				if(timeRemain == 0) {
					if(mHob4IsModeA) {
						//always A mode
						long time = (System.currentTimeMillis() - mHob4StarTime) / 1000;
						int elapseTime = time < 1 ? 1 : (int)time; 
						mHobInfoBar_4.updateTime(elapseTime);
						mHobInfoBar_4.setPowerLevel(hobStatus.powerLevel);
					} else {
						//Change to Mode A
						mHob4IsModeA = false;
						mHobInfoBar_4.setHobInfo(hobStatus.powerLevel, timeRemain);
						mHobInfoBar_4.updateTime(timeRemain);
						mHob4StarTime = System.currentTimeMillis();
					}
				} else {
					if(!mHob4IsModeA) {
						//always B Mode
						if(mHob4PreRemainTime == timeRemain) {
							//in the on minute
							long time = (System.currentTimeMillis() - mHob4StarTime) / 1000;
							int elapseTime = time < 1 ? 1 : (int)time; 
							int remainTime = timeRemain - elapseTime;
							mHobInfoBar_4.updateTime(remainTime);
							mHobInfoBar_4.setPowerLevel(hobStatus.powerLevel);
						} else {
							mHob4PreRemainTime = timeRemain;
							mHob4StarTime = System.currentTimeMillis();
							mHobInfoBar_4.updateTime(timeRemain);
							mHobInfoBar_4.setPowerLevel(hobStatus.powerLevel);
						}
					} else {
						//Change to Mode B
						mHob4IsModeA = false;
						mHobInfoBar_4.setHobInfo(hobStatus.powerLevel, timeRemain);
						mHobInfoBar_4.updateTime(timeRemain);
						mHob4PreRemainTime = timeRemain;
						mHob4StarTime = System.currentTimeMillis();
					}
				}
			}
		}
	}

	private void hobHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "hobHandleReplyMessage");
		Hob hob = MainActivity.getInstance().getHobDevice();
		hobUpdateStatus(hob);
	}

	private void hobHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "hobHandleTimeoutMessage");
//		mHobDisconnectWarning.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(mHobTimeoutRunnable);
		mHandler.postDelayed(mHobTimeoutRunnable, TIMEOUT_DISCONNECT);
	}

	private void hobHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "hobHandleUpdateMessage");
		Hob hob = MainActivity.getInstance().getHobDevice();
		hobUpdateStatus(hob);
	}

	private void hobUpdateStatus(Hob hob) {
		if(hob.getProtocol().getType() != BaseProtocol.TYPE_HOB) {
			return;
		}
		
		if(!mHobEnable) {
			setHobStauts(true);
		}
		
		HobStatus hobStatus = hob.getHobStatus(HobPosition.HP_LEFT_CORNER); // 1
		hob1UpdateStatus(hobStatus);
		
//		mHobInfoBar_1.setHobInfoBarStatus(true);
		/*if(hobStatus.status == 1) {
			mHobInfoBar_1.setHobInfo(hobStatus.powerLevel, hobStatus.timeLeft);
			mHobPos_1.setImageResource(R.drawable.tv_hob_pos_1_enable);
		} else {
			mHobInfoBar_1.setHobInfo(hobStatus.powerLevel, hobStatus.timeLeft);
			mHobPos_1.setImageResource(R.drawable.tv_hob_pos_1_enable);
		}*/

		hobStatus = hob.getHobStatus(HobPosition.HP_RIGHT_CORNER); // 2
		hob2UpdateStatus(hobStatus);
//		mHobInfoBar_2.setHobInfoBarStatus(true);
		/*mHobInfoBar_2.setHobInfo(hobStatus.powerLevel, hobStatus.timeLeft);
		mHobPos_2.setImageResource(R.drawable.tv_hob_pos_2_enable);*/

		hobStatus = hob.getHobStatus(HobPosition.HP_BOTTOM_LEFT); // 3
		hob3UpdateStatus(hobStatus);
//		mHobInfoBar_3.setHobInfoBarStatus(true);
		/*mHobInfoBar_3.setHobInfo(hobStatus.powerLevel, hobStatus.timeLeft);
		mHobPos_3.setImageResource(R.drawable.tv_hob_pos_3_enable);*/

		hobStatus = hob.getHobStatus(HobPosition.HP_BOTTOM_RIGHT); // 4
		hob4UpdateStatus(hobStatus);
//		mHobInfoBar_4.setHobInfoBarStatus(true);
		/*mHobInfoBar_4.setHobInfo(hobStatus.powerLevel, hobStatus.timeLeft);
		mHobPos_4.setImageResource(R.drawable.tv_hob_pos_4_enable);*/
	}

	private void hoodHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "hoodHandleReplyMessage");
		Hood hood = MainActivity.getInstance().getHoodDevice();
		hoodUpdateStatus(hood);
	}
	
	private void hoodHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "hoodHandleTimeoutMessage");
//		mHoodDisconnectWarning.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(mHoodTimeoutRunnable);
		mHandler.postDelayed(mHoodTimeoutRunnable, TIMEOUT_DISCONNECT);
	}
	
	private void hoodHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "hoodHandleUpdateMessage");
		Hood hood = MainActivity.getInstance().getHoodDevice();
		hoodUpdateStatus(hood);
	}
	
	private void hoodSetWorkMode(int mode) {
		switch (mode) {
		case HoodProtocol.WORK_STATUS_SMALL_FAN:
			mHoodMode.setImageResource(R.drawable.tv_hood_mode_1_on);
			break;
		case HoodProtocol.WORK_STATUS_NORMAL_FAN:
			mHoodMode.setImageResource(R.drawable.tv_hood_mode_2_on);
			break;
		case HoodProtocol.WORK_STATUS_LARGE_FAN:
			mHoodMode.setImageResource(R.drawable.tv_hood_mode_3_on);
			break;
		default:
			mHoodMode.setImageResource(R.drawable.tv_hood_mode_1_on);
			break;
		}
	}
	
	private void hoodUpdateStatus(Hood hood) {
		if(hood.getProtocol().getType() != BaseProtocol.TYPE_HOOD) {
			return;
		}
		
		if(!mHoodEnable) {
			setHoodStauts(true);
		}
		
		int mode = hood.getWorkStatus();
		hoodSetWorkMode(mode);

		int runningTime = hood.getRunningTime();
		mHoodTimeBar.disableTotalTime();
//		mHoodTimeBar.setTimeBarStatus(true);
		mHoodTimeBar.updateTime(runningTime);

		int rpm = hood.getMotorSpeed();
		String textRmp = rpm + "rmp";
		mHoodMotorSpeed.setText(textRmp);
		mHoodMotorSpeed.setTextColor(mCslWorkingOn);
	}
	
	private void initDevices() {
		mMicrowaveManager = MainActivity.getInstance().getDeviceManager(
				BaseProtocol.TYPE_MICROWAVE);
		mMicrowaveManager.registerDeviceEventListener(mMicroWaveListener);
		mMicrowaveDevice = (Microwave) mMicrowaveManager.getDevice();
		setMicrowaveStauts(mMicrowaveManager.isConnected());

		mOvenManager = MainActivity.getInstance().getDeviceManager(
				BaseProtocol.TYPE_OVEN);
		mOvenManager.registerDeviceEventListener(mOvenListener);
		mOvenDevice = (Oven) mOvenManager.getDevice();
		setOvenStauts(mOvenManager.isConnected());

		mHobManager = MainActivity.getInstance().getDeviceManager(
				BaseProtocol.TYPE_HOB);
		mHobManager.registerDeviceEventListener(mHobListener);
		mHobDevice = (Hob) mHobManager.getDevice();
		setHobStauts(mHobManager.isConnected());

		mHoodManager = MainActivity.getInstance().getDeviceManager(
				BaseProtocol.TYPE_HOOD);
		mHoodManager.registerDeviceEventListener(mHoodListener);
		mHoodDevice = (Hood) mHoodManager.getDevice();
		setHoodStauts(mHoodManager.isConnected());

		mMiniOvenManager = MainActivity.getInstance().getDeviceManager(
				BaseProtocol.TYPE_MINIOVEN);
		mMiniOvenManager.registerDeviceEventListener(mMiniovenListener);
		mMiniovenDevice = (Minioven) mMiniOvenManager.getDevice();
		setMiniovenStauts(mMiniOvenManager.isConnected());

		mRobotCleanManager = MainActivity.getInstance().getDeviceManager(
				BaseProtocol.TYPE_ROBOTCLEAN);
		mRobotCleanManager.registerDeviceEventListener(mRobotCleanListener);
		mRobotcleanDevice = (Robotclean) mRobotCleanManager.getDevice();
		setRobotcleanStauts(mRobotCleanManager.isConnected());
	}
	
	private void initHobViews() {
		mHobLayout = (FrameLayout) findViewById(R.id.tv_hob_layout);
		mHobTitle = (TextView) findViewById(R.id.text_hob_title);
		mHobInfoBar_1 = (CusTvHobInfoBar) findViewById(R.id.cus_hob_info_bar_1);
		mHobInfoBar_2 = (CusTvHobInfoBar) findViewById(R.id.cus_hob_info_bar_2);
		mHobInfoBar_3 = (CusTvHobInfoBar) findViewById(R.id.cus_hob_info_bar_3);
		mHobInfoBar_4 = (CusTvHobInfoBar) findViewById(R.id.cus_hob_info_bar_4);
		mHobPos_1 = (ImageView) findViewById(R.id.image_hob_pos_1);
		mHobPos_2 = (ImageView) findViewById(R.id.image_hob_pos_2);
		mHobPos_3 = (ImageView) findViewById(R.id.image_hob_pos_3);
		mHobPos_4 = (ImageView) findViewById(R.id.image_hob_pos_4);
		mHobDisableWarning = (ImageView) findViewById(R.id.image_hob_complete_warning);
		mHobDisconnectWarning = (ImageView) findViewById(R.id.image_hob_disconnect_warning);

	}
	
	private void initHoodeViews() {
		mHoodLayout = (FrameLayout) findViewById(R.id.tv_hood_layout);
		mHoodTitle = (TextView) findViewById(R.id.text_hood_title);
		mHoodMode = (ImageView) findViewById(R.id.image_hood_mode);
		mHoodTimeBar = (CusTvTimeBar) findViewById(R.id.cus_hood_time_bar);
		mHoodTimeBar.disableTotalTime();
		mHoodMotorSpeed = (TextView) findViewById(R.id.text_hood_rpm);
		mHoodDisconnectWarning = (ImageView) findViewById(R.id.image_hood_disconnect_warning);

		Resources resource = getResources();
		mCslWorkingOn = resource
				.getColorStateList(R.color.tv_text_working_on);
		mCslWorkingOff = resource
				.getColorStateList(R.color.tv_text_working_off);
	}

	private void initMicrowaveViews() {
		mMicrowaveLayout = (FrameLayout) findViewById(R.id.tv_microwave_layout);
		mMicrowaveTitle = (TextView) findViewById(R.id.text_microwave_title);
		mMicrowaveMainWorkingBar = (CusTvProgressBar) findViewById(R.id.cus_microwave_progress_bar);
		mMicrowaveMode = (ImageView) findViewById(R.id.image_microwave_mode);
		mMicrowaveTimeBar = (CusTvTimeBar) findViewById(R.id.cus_microwave_time_bar);
		mMicrowaveSettingIconTempr = (ImageView) findViewById(R.id.image_microwave_setting_tempr);
		mMicrowaveInfoTempr = (CusTvInfoBox) findViewById(R.id.cus_microwave_info_tempr);
		mMicrowaveSettingIconPower = (ImageView) findViewById(R.id.image_microwave_setting_power);
		mMicrowaveInfoPower = (CusTvInfoBox) findViewById(R.id.cus_microwave_info_power);
		mMicrowaveDefrostWorkingBar = (CusTvProgressBar) findViewById(R.id.cus_microwave_progress_bar_defrost);
		mMicrowaveDisconnectWarning = (ImageView) findViewById(R.id.image_microwave_disconnect_warning);
	}

	private void initMiniOvenViews() {
		mMiniovenLayout = (FrameLayout) findViewById(R.id.tv_minioven_layout);
		mMiniovenTitle = (TextView) findViewById(R.id.text_minioven_title);
		mMiniovenMainWorkingBar = (CusTvProgressBar) findViewById(R.id.cus_minioven_progress_bar_main);
		mMiniovenMode = (ImageView) findViewById(R.id.image_minioven_mode);
		mMiniovenTimeBar = (CusTvTimeBar) findViewById(R.id.cus_minioven_time_bar);
		mMiniovenIconTempr = (ImageView) findViewById(R.id.image_minioven_setting_tempr);
		mMiniovenInfoTempr = (CusTvInfoBox) findViewById(R.id.cus_minioven_info_tempr);
		mMiniovenIconPower = (ImageView) findViewById(R.id.image_minioven_setting_power);
		mMiniovenInfoPower = (CusTvInfoBox) findViewById(R.id.cus_minioven_info_power);
		mMiniovenRotisserie = (TextView) findViewById(R.id.text_minioven_rotisserie);
		mMiniovenRotisserieWorkingBar = (CusTvProgressBar) findViewById(R.id.cus_minioven_progress_bar_rotisserie);
		mMiniovenRotisserieTimeBar = (CusTvTimeBar) findViewById(R.id.cus_minioven_time_bar_rotisserie);
		mMiniovenDisconnectWarning = (ImageView) findViewById(R.id.image_minioven_disconnect_warning);
	}

	private void initOvenViews() {
		mOvenLayout = (FrameLayout) findViewById(R.id.tv_oven_layout);
		mOvenTitle = (TextView) findViewById(R.id.text_oven_title);
		mOvenMainWorkingBar = (CusTvProgressBar) findViewById(R.id.cus_oven_progress_bar_main);
		mOvenMode = (ImageView) findViewById(R.id.image_oven_mode);
		mOvenTimeBar = (CusTvTimeBar) findViewById(R.id.cus_oven_time_bar);
		mOvenIconTempr = (ImageView) findViewById(R.id.image_oven_setting_tempr);
		mOvenInfoTempr = (CusTvInfoBox) findViewById(R.id.cus_oven_info_tempr);
		mOvenIconPower = (ImageView) findViewById(R.id.image_oven_setting_power);
		mOvenInfoPower = (CusTvInfoBox) findViewById(R.id.cus_oven_info_power);
		mOvenDisconnectWarning = (ImageView) findViewById(R.id.image_oven_disconnect_warning);
	}

	private void initRobotCleanViews() {
		mRobotcleanLayout = (FrameLayout) findViewById(R.id.tv_robotclean_layout);
		mRobotcleanTitle = (TextView) findViewById(R.id.text_robotclean_title);
		mRobotcleanBattery = (CusTvRobotCleanBattery) findViewById(R.id.cus_robotclean_battery_bar);
		mRobotcleanTimeBar = (CusTvTimeBar) findViewById(R.id.cus_robotclean_time_bar);
		mRobotcleanTimeBar.disableTotalTime();
		mRobotcleanAutoMode = (ImageView) findViewById(R.id.image_robotclean_auto);
		mRobotcleanDisconnectWarning = (ImageView) findViewById(R.id.image_robotclean_disconnect_warning);
	}

	private void initViews() {
		initMicrowaveViews();
		initOvenViews();
		initHoodeViews();
		initHobViews();
		initMiniOvenViews();
		initRobotCleanViews();
		
		Resources resource = getResources();  
		mCslTextOn = resource.getColorStateList(R.color.tv_text_mode_on);
		mCslTextOff = resource.getColorStateList(R.color.tv_text_mode_off);

		ImageButton padTv = (ImageButton) findViewById(R.id.btn_switch_tv_pad);
		padTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// finish();
//				moveTaskToBack(true);
				Intent intent = new Intent();
				intent.setClass(MainTvActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}

	private void microwaveHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "microwaveHandleReplyMessage");

		Microwave microwave = MainActivity.getInstance().getMicrowaveDevice();
		microwaveUpdateStatus(microwave);
	}

	private void microwaveHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "microwaveHandleTimeoutMessage");
//		mMicrowaveDisconnectWarning.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(mMicrowaveTimeoutRunnable);
		mHandler.postDelayed(mMicrowaveTimeoutRunnable, TIMEOUT_DISCONNECT);
	}

	private void microwaveHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "microwaveHandleUpdateMessage");
		Microwave microwave = MainActivity.getInstance().getMicrowaveDevice();
		microwaveUpdateStatus(microwave);
	}

	private void microwaveSetWorkMode(int mode) {
		switch (mode) {
		case MicrowaveProtocol.WORK_MODE_MICROWAVE:
			mMicrowaveMode.setImageResource(R.drawable.tv_mwo_mode_1_on);
			break;
		case MicrowaveProtocol.WORK_MODE_GRILL:
			mMicrowaveMode.setImageResource(R.drawable.tv_mwo_mode_2_on);
			break;
		case MicrowaveProtocol.WORK_MODE_CONVECTION:
			mMicrowaveMode.setImageResource(R.drawable.tv_mwo_mode_3_on);
			break;
		case MicrowaveProtocol.WORK_MODE_TIME_DEFROST:
			mMicrowaveMode.setImageResource(R.drawable.tv_mwo_mode_4_on);
			break;
		case MicrowaveProtocol.WORK_MODE_WEIGHT_DEFROST:
			mMicrowaveMode.setImageResource(R.drawable.tv_mwo_mode_5_on);
			break;
		default:
			mMicrowaveMode.setImageResource(R.drawable.tv_mwo_mode_1_on);
			break;
		}
	}

	private void microwaveUpdateStatus(Microwave microwave) {
		if(microwave.getProtocol().getType() != BaseProtocol.TYPE_MICROWAVE) {
			return;
		}
		
		if(!mMicrowaveEnable) {
			setMicrowaveStauts(true);
		}
		
		int mode = microwave.getWorkMode();
		microwaveSetWorkMode(mode);

		int remainTime = microwave.getRemainTime();
		if(remainTime > mMicrowaveSettingTime) {
			mMicrowaveSettingTime = remainTime;
		}
		
		if(remainTime == 0) {
			mMicrowaveSettingTime = 0;
		}
//		int totalTime = microwave.getTotalTime();
//		if (totalTime == 0)
//			totalTime = 1;
		
		int progress = 0;
		if(mMicrowaveSettingTime == 0) {
			progress = 100;
		} else {
			progress = 100 - (remainTime * 100 / mMicrowaveSettingTime);
		}
//		int progress = 100 - (remainTime * 100 / totalTime);
//		mMicrowaveMainWorkingBar.setProgressBarStatus(true);
		mMicrowaveMainWorkingBar.updateProgress(progress);
//		mMicrowaveTimeBar.setTimeBarStatus(true);
		mMicrowaveTimeBar.setTotalTime(mMicrowaveSettingTime);
		if(microwave.getWorkStatus() == MicrowaveProtocol.STATUS_INFO_STANDBY) {
			mMicrowaveTimeBar.updateTime(0);
			mMicrowaveSettingTime = 0;
		} else {
			mMicrowaveTimeBar.updateTime(remainTime);
		}

		int tempr = microwave.getReallyTemperature();
		int temprSetting = microwave.getSettingTemperature();
		String setting = temprSetting + "��";
		String working = tempr + "��";
//		mMicrowaveInfoTempr.setInfoBoxStatus(true);
		mMicrowaveInfoTempr.setInfoBox(setting, working);
		// from midea ppt.
		if(microwave.getWorkStatus() == MicrowaveProtocol.STATUS_INFO_RUNNING) {
			mMicrowaveInfoPower.setInfoBox("1200W", "1200W");
		} else {
			mMicrowaveInfoPower.setInfoBox("0W", "0W");
		}

		if (mode == MicrowaveProtocol.WORK_MODE_WEIGHT_DEFROST) {
			mMicrowaveDefrostWorkingBar.setVisibility(View.VISIBLE);
		} else {
			mMicrowaveDefrostWorkingBar.setVisibility(View.GONE);
		}

	}

	private void miniovenHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "miniovenHandleReplyMessage");
		Minioven minioven = MainActivity.getInstance().getMiniovenDevice();
		miniovenUpdateStatus(minioven);
	}

	private void miniovenHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "miniovenHandleTimeoutMessage");
//		mMiniovenDisconnectWarning.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(mMiniovenTimeoutRunnable);
		mHandler.postDelayed(mMiniovenTimeoutRunnable, TIMEOUT_DISCONNECT);
	}

	private void miniovenHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "miniovenHandleUpdateMessage");
		Minioven minioven = MainActivity.getInstance().getMiniovenDevice();
		miniovenUpdateStatus(minioven);
	}

	private void miniovenSetWorkMode(int mode) {
		switch (mode) {
		case MiniovenProtocol.WORK_MODE_BAKE:
			mMiniovenMode.setImageResource(R.drawable.tv_mini_mode_1_sel);
			break;
		case MiniovenProtocol.WORK_MODE_BROIL:
			mMiniovenMode.setImageResource(R.drawable.tv_mini_mode_2_sel);
			break;
		case MiniovenProtocol.WORK_MODE_TOAST:
			mMiniovenMode.setImageResource(R.drawable.tv_mini_mode_3_sel);
			break;
		case MiniovenProtocol.WORK_MODE_CONVECTION:
			mMiniovenMode.setImageResource(R.drawable.tv_mini_mode_4_sel);
			break;
		case MiniovenProtocol.WORK_MODE_ROTISSERIE_GRILL:
			mMiniovenMode.setImageResource(R.drawable.tv_mini_mode_5_sel);
			break;
		default:
			mMiniovenMode.setImageResource(R.drawable.tv_mini_mode_1_sel);
			break;
		}
	}

	private void miniovenUpdateStatus(Minioven minioven) {
		if(minioven.getProtocol().getType() != BaseProtocol.TYPE_MINIOVEN) {
			return;
		}
		
		if(!mMiniovenEnable) {
			setMiniovenStauts(true);
		}
		
		int mode = minioven.getWorkMode();
		miniovenSetWorkMode(mode);

		int remainTime = minioven.getRemainTime();
		
		if(remainTime > mMiniovenSettingTime) {
			mMiniovenSettingTime = remainTime;
		}
		
		if(remainTime == 0) {
			mMiniovenSettingTime = 0;
		}
		
		int progress = 0;
		if(mMiniovenSettingTime == 0) {
			progress = 100;
		} else {
			progress = 100 - (remainTime * 100 / mMiniovenSettingTime);
		}
//		int totalTime = minioven.getSettingTime();
//		int progress = 100 - (remainTime * 100 / totalTime);
//		mMiniovenMainWorkingBar.setProgressBarStatus(true);
		mMiniovenMainWorkingBar.updateProgress(progress);
//		mMiniovenTimeBar.setTimeBarStatus(true);
		mMiniovenTimeBar.setTotalTime(mMiniovenSettingTime);
		if(minioven.getWorkStatus() == MiniovenProtocol.STATUS_INFO_STANDBY) {
			mMiniovenSettingTime = 0;
			mMiniovenTimeBar.updateTime(0);
		} else {
			mMiniovenTimeBar.updateTime(remainTime);
		}

		int tempr = minioven.getReallyTemperature();
		int temprSetting = minioven.getSettingTemperature();
		String setting = temprSetting + "��";
		String working = tempr + "��";
//		mMiniovenInfoTempr.setInfoBoxStatus(true);
		mMiniovenInfoTempr.setInfoBox(setting, working);
		// from midea ppt.
		if(minioven.getWorkStatus() == OvenProtocol.STATUS_INFO_RUNNING) {
			mMiniovenInfoPower.setInfoBox("1400W", "1400W");
		} else {
			mMiniovenInfoPower.setInfoBox("0W", "0W");
		}

		if (mode == MiniovenProtocol.WORK_MODE_ROTISSERIE_GRILL) {
			mMiniovenRotisserieWorkingBar.setVisibility(View.VISIBLE);
			mMiniovenRotisserieTimeBar.setVisibility(View.VISIBLE);
			mMiniovenRotisserie.setVisibility(View.VISIBLE);
		} else {
			mMiniovenRotisserieWorkingBar.setVisibility(View.GONE);
			mMiniovenRotisserieTimeBar.setVisibility(View.GONE);
			mMiniovenRotisserie.setVisibility(View.GONE);
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tv_main);

		initViews();
		initDevices();
		
		registerBroadcastReceiver();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		MainActivity.getInstance()
				.getDeviceManager(BaseProtocol.TYPE_MICROWAVE)
				.unRegisterDeviceEventListener(mMicroWaveListener);

		MainActivity.getInstance().getDeviceManager(BaseProtocol.TYPE_OVEN)
				.unRegisterDeviceEventListener(mOvenListener);

		MainActivity.getInstance().getDeviceManager(BaseProtocol.TYPE_HOB)
				.unRegisterDeviceEventListener(mHobListener);

		MainActivity.getInstance().getDeviceManager(BaseProtocol.TYPE_HOOD)
				.unRegisterDeviceEventListener(mHoodListener);

		MainActivity.getInstance().getDeviceManager(BaseProtocol.TYPE_MINIOVEN)
				.unRegisterDeviceEventListener(mMiniovenListener);

		MainActivity.getInstance()
				.getDeviceManager(BaseProtocol.TYPE_ROBOTCLEAN)
				.unRegisterDeviceEventListener(mRobotCleanListener);
		
		unregisterReceiver(mReceiver);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		// unregisterReceiver(mReceiver);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// registerBroadcastReceiver();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}
	
	private void ovenHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "ovenHandleReplyMessage");
		Oven oven = MainActivity.getInstance().getOvenDevice();
		ovenUpdateStatus(oven);
	}
	
	private void ovenHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "ovenHandleTimeoutMessage");
//		mOvenDisconnectWarning.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(mOvenTimeoutRunnable);
		mHandler.postDelayed(mOvenTimeoutRunnable, TIMEOUT_DISCONNECT);
	}

	private void ovenHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "ovenHandleUpdateMessage");
		Oven oven = MainActivity.getInstance().getOvenDevice();
		ovenUpdateStatus(oven);
	}

	private void ovenSetWorkMode(int mode) {
		switch (mode) {
		case OvenProtocol.MODE_CONVENTIONAL:
			mOvenMode.setImageResource(R.drawable.tv_oven_mode_1_sel);
			break;
		case OvenProtocol.MODE_CONVENTIONAL_FAN:
			mOvenMode.setImageResource(R.drawable.tv_oven_mode_2_sel);
			break;
		case OvenProtocol.MODE_DOUBLE_GRILLING:
			mOvenMode.setImageResource(R.drawable.tv_oven_mode_3_sel);
			break;
		case OvenProtocol.MODE_DOUBLE_GRILLING_FAN:
			mOvenMode.setImageResource(R.drawable.tv_oven_mode_4_sel);
			break;
		case OvenProtocol.MODE_CONVECTION:
			mOvenMode.setImageResource(R.drawable.tv_oven_mode_5_sel);
			break;
		case OvenProtocol.MODE_RADIANT_GRILLING:
			mOvenMode.setImageResource(R.drawable.tv_oven_mode_6_sel);
			break;
		case OvenProtocol.MODE_DEFROST:
			mOvenMode.setImageResource(R.drawable.tv_oven_mode_7_sel);
			break;
		case OvenProtocol.MODE_BOTTOM_HEAT:
			mOvenMode.setImageResource(R.drawable.tv_oven_mode_8_sel);
			break;
		default:
			mOvenMode.setImageResource(R.drawable.tv_mwo_mode_1_on);
			break;
		}
	}
	
	private void ovenUpdateStatus(Oven oven) {
		if(oven.getProtocol().getType() != BaseProtocol.TYPE_OVEN) {
			return;
		}
		
		if(!mOvenEnable) {
			setOvenStauts(true);
		}
		
		int mode = oven.getWorkMode();
		ovenSetWorkMode(mode);

		int remainTime = oven.getRemainTime();
		if(remainTime > mOvenSettingTime) {
			mOvenSettingTime = remainTime;
		}
		
		if(remainTime == 0) {
			mOvenSettingTime = 0;
		}
		
		int remainTimeInSecond = remainTime * 60;
		if(mOvenPreRemainTime != remainTime) {
			mOvenPreRemainTime = remainTime;
			mOvenStartTime = System.currentTimeMillis();
		} else {
			long time = (System.currentTimeMillis() - mOvenStartTime) / 1000;
			int elapseTime = time < 1 ? 1 : (int)time;  //elapse the time in second
			remainTimeInSecond -= elapseTime;
		}
//		int totalTime = oven.getSettingTime();
		int progress = 0;
		if(mOvenSettingTime == 0) {
			progress = 100;
		} else {
			progress = 100 - (remainTimeInSecond * 100 / (mOvenSettingTime*60));
		}
		
//		mOvenMainWorkingBar.setProgressBarStatus(true);
		mOvenMainWorkingBar.updateProgress(progress);
//		mOvenTimeBar.setTimeBarStatus(true);
		mOvenTimeBar.setTotalTime(mOvenSettingTime);
		if(oven.getWorkStatus() == OvenProtocol.STATUS_INFO_STANDBY) {
			mOvenTimeBar.updateTime(0);
			mOvenSettingTime = 0;
		} else {
			mOvenTimeBar.updateTime(remainTime);
		}
		
		

		int tempr = oven.getReallyTemperature();
		int temprSetting = oven.getSettingTemperature();
		String setting = temprSetting + "��";
		String working = tempr + "��";
//		mOvenInfoTempr.setInfoBoxStatus(true);
		mOvenInfoTempr.setInfoBox(setting, working);
		// from midea ppt.
		if(oven.getWorkStatus() == OvenProtocol.STATUS_INFO_RUNNING) {
			mOvenInfoPower.setInfoBox("2300W", "2300W");
		} else {
			mOvenInfoPower.setInfoBox("0W", "0W");
		}
	}
	
	private void registerBroadcastReceiver() {
		final IntentFilter filter = new IntentFilter();
		/*filter.addAction(AppConstant.ACTION_DEVICE_REPLY);
		filter.addAction(AppConstant.ACTION_DEVICE_UPDATE);
		filter.addAction(AppConstant.ACTION_DEVICE_TIMEOUT);*/
		filter.addAction(AppConstant.ACTION_EXIT);
		registerReceiver(mReceiver, filter);
	}
	
	private void robotcleanHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleReplyMessage");
		Robotclean robotclean = MainActivity.getInstance().getCleanDevice();
		robotcleanUpdateStatus(robotclean);
	}
	
	private void robotcleanHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleTimeoutMessage");
//		mRobotcleanDisconnectWarning.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(mRobotcleanTimeoutRunnable);
		mHandler.postDelayed(mRobotcleanTimeoutRunnable, TIMEOUT_DISCONNECT);
	}

	private void robotcleanHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleUpdateMessage");
		Robotclean robotclean = MainActivity.getInstance().getCleanDevice();
		robotcleanUpdateStatus(robotclean);
	}

	private void robotcleanUpdateStatus(Robotclean robotclean) {
		if(robotclean.getProtocol().getType() != BaseProtocol.TYPE_ROBOTCLEAN) {
			return;
		}
		
		if(!mRobotcleanEnable) {
			setRobotcleanStauts(true);
		}
		
		int status = robotclean.getWorkStatus();
		int batteryValue = robotclean.batteryValue();
		int workTime = robotclean.workTime();

//		mRobotcleanBattery.setBatteryStatus(true);
		mRobotcleanBattery.updateBatteryCap(batteryValue);

//		mRobotcleanTimeBar.setTimeBarStatus(true);
		mRobotcleanTimeBar.updateTime(workTime);

		if (status == RobotcleanProtocol.WORK_STATUS_AUTOWORK
				|| status == RobotcleanProtocol.WORK_STATUS_AUTOCHARGE) {
			mRobotcleanAutoMode.setVisibility(View.VISIBLE);
		} else {
			mRobotcleanAutoMode.setVisibility(View.GONE);
		}
	}

	private void setHobStauts(boolean enable) {
		mHobEnable = enable;
		if(enable) {
			mHobLayout.setBackgroundResource(R.drawable.tv_hob_bg_on);
			mHobTitle.setTextColor(mCslTextOn);
			/*mHobInfoBar_1.setHobInfoBarStatus(enable);
			mHobInfoBar_2.setHobInfoBarStatus(enable);
			mHobInfoBar_3.setHobInfoBarStatus(enable);
			mHobInfoBar_4.setHobInfoBarStatus(enable);
			mHobPos_1.setImageResource(R.drawable.tv_hob_pos_1_enable);
			mHobPos_2.setImageResource(R.drawable.tv_hob_pos_2_enable);
			mHobPos_3.setImageResource(R.drawable.tv_hob_pos_3_enable);
			mHobPos_4.setImageResource(R.drawable.tv_hob_pos_4_enable);*/
		} else {
			mHobLayout.setBackgroundResource(R.drawable.tv_hob_bg_off);
			mHobTitle.setTextColor(mCslTextOff);
			mHobInfoBar_1.setHobInfoBarStatus(enable);
			mHobInfoBar_2.setHobInfoBarStatus(enable);
			mHobInfoBar_3.setHobInfoBarStatus(enable);
			mHobInfoBar_4.setHobInfoBarStatus(enable);
			mHobPos_1.setImageResource(R.drawable.tv_hob_pos_1_disable);
			mHobPos_2.setImageResource(R.drawable.tv_hob_pos_2_disable);
			mHobPos_3.setImageResource(R.drawable.tv_hob_pos_3_disable);
			mHobPos_4.setImageResource(R.drawable.tv_hob_pos_4_disable);
		}
	}

	private void setHoodStauts(boolean enable) {
		mHoodEnable = enable;
		if(enable) {
			mHoodLayout.setBackgroundResource(R.drawable.tv_hood_bg_on);
			mHoodTitle.setTextColor(mCslTextOn);
			int mode = mHoodDevice.getMotorSpeed();
			mode = mode == 0 ? DEFAULT_HOOD_MODE : mode;
			if(mode >= mHoodModeList.length) mode = mHoodModeList.length - 1;
			mHoodMode.setImageResource(mHoodModeList[mode][0]);
			mHoodTimeBar.setTimeBarStatus(enable);
			mHoodMotorSpeed.setTextColor(mCslWorkingOn);
		} else {
			mHoodLayout.setBackgroundResource(R.drawable.tv_hood_bg_off);
			mHoodTitle.setTextColor(mCslTextOff);
			int mode = mHoodDevice.getMotorSpeed();
			mode = mode == 0 ? DEFAULT_HOOD_MODE : mode;
			if(mode >= mHoodModeList.length) mode = mHoodModeList.length - 1;
			mHoodMode.setImageResource(mHoodModeList[mode][1]);
			mHoodTimeBar.setTimeBarStatus(enable);
			mHoodMotorSpeed.setTextColor(mCslWorkingOff);
		}
	}

	private void setMicrowaveStauts(boolean enable) {
		mMicrowaveEnable = enable;
		if(enable) {
			mMicrowaveLayout.setBackgroundResource(R.drawable.tv_mwo_bg_on);
			mMicrowaveTitle.setTextColor(mCslTextOn);
			mMicrowaveMainWorkingBar.setProgressBarStatus(enable);
			int mode = mMicrowaveDevice.getWorkMode();
			mode = mode == 0 ? DEFAULT_MICROWAVE_MODE : mode;
			if(mode >= mMicrowaveModeList.length) mode = mMicrowaveModeList.length - 1;
			mMicrowaveMode.setImageResource(mMicrowaveModeList[mode][0]);
			mMicrowaveTimeBar.setTimeBarStatus(enable);
			mMicrowaveSettingIconTempr.setImageResource(R.drawable.tv_icon_setting_sel_on);
			mMicrowaveInfoTempr.setInfoBoxStatus(enable);
			mMicrowaveInfoTempr.setInfoBox("160��", "120��");
			mMicrowaveSettingIconPower.setImageResource(R.drawable.tv_icon_setting_sel_on);
			mMicrowaveInfoPower.setInfoBoxStatus(enable);
			mMicrowaveInfoPower.setInfoBox("1000W", "1000W");
			mMicrowaveDefrostWorkingBar.setProgressBarStatus(enable);
		} else {
			mMicrowaveLayout.setBackgroundResource(R.drawable.tv_mwo_bg_off);
			mMicrowaveTitle.setTextColor(mCslTextOff);
			mMicrowaveMainWorkingBar.setProgressBarStatus(enable);
			int mode = mMicrowaveDevice.getWorkMode();
			mode = mode == 0 ? DEFAULT_MICROWAVE_MODE : mode;
			if(mode >= mMicrowaveModeList.length) mode = mMicrowaveModeList.length - 1;
			mMicrowaveMode.setImageResource(mMicrowaveModeList[mode][1]);
			mMicrowaveTimeBar.setTimeBarStatus(enable);
			mMicrowaveSettingIconTempr.setImageResource(R.drawable.tv_icon_setting_unsel_on);
			mMicrowaveInfoTempr.setInfoBoxStatus(enable);
			mMicrowaveInfoTempr.setInfoBox("160��", "120��");
			mMicrowaveSettingIconPower.setImageResource(R.drawable.tv_icon_setting_unsel_on);
			mMicrowaveInfoPower.setInfoBoxStatus(enable);
			mMicrowaveInfoPower.setInfoBox("1000W", "1000W");
			mMicrowaveDefrostWorkingBar.setProgressBarStatus(enable);
			mMicrowaveDefrostWorkingBar.setVisibility(View.INVISIBLE);
		}
	}

	private void setMiniovenStauts(boolean enable) {
		mMiniovenEnable = enable;
		if(enable) {
			mMiniovenLayout.setBackgroundResource(R.drawable.tv_mini_bg_on);
			mMiniovenTitle.setTextColor(mCslTextOn);
			mMiniovenMainWorkingBar.setProgressBarStatus(enable);
			int mode = mMiniovenDevice.getWorkMode();
			mode = mode == 0 ? DEFAULT_MINIOVEN_MODE : mode;
			if(mode >= mMiniovenModeList.length) mode = mMiniovenModeList.length - 1;
			mMiniovenMode.setImageResource(mMiniovenModeList[mode][0]);
			mMiniovenTimeBar.setTimeBarStatus(enable);
			mMiniovenIconTempr.setImageResource(R.drawable.tv_icon_setting_sel_on);
			mMiniovenInfoTempr.setInfoBoxStatus(enable);
			mMiniovenInfoTempr.setInfoBox("160��", "120��");
			mMiniovenIconPower.setImageResource(R.drawable.tv_icon_setting_sel_on);
			mMiniovenInfoPower.setInfoBoxStatus(enable);
			mMiniovenInfoPower.setInfoBox("1000W", "1000W");
			mMiniovenRotisserieWorkingBar.setProgressBarStatus(enable);
			mMiniovenRotisserieTimeBar.setTimeBarStatus(enable);
		} else {
			mMiniovenLayout.setBackgroundResource(R.drawable.tv_mini_bg_off);
			mMiniovenTitle.setTextColor(mCslTextOff);
			mMiniovenMainWorkingBar.setProgressBarStatus(enable);
			int mode = mMiniovenDevice.getWorkMode();
			mode = mode == 0 ? DEFAULT_MINIOVEN_MODE : mode;
			if(mode >= mMiniovenModeList.length) mode = mMiniovenModeList.length - 1;
			mMiniovenMode.setImageResource(mMiniovenModeList[mode][1]);
			mMiniovenTimeBar.setTimeBarStatus(enable);
			mMiniovenIconTempr.setImageResource(R.drawable.tv_icon_setting_unsel_on);
			mMiniovenInfoTempr.setInfoBoxStatus(enable);
			mMiniovenInfoTempr.setInfoBox("160��", "120��");
			mMiniovenIconPower.setImageResource(R.drawable.tv_icon_setting_unsel_on);
			mMiniovenInfoPower.setInfoBoxStatus(enable);
			mMiniovenInfoPower.setInfoBox("1000W", "1000W");
			mMiniovenRotisserieWorkingBar.setProgressBarStatus(enable);
			mMiniovenRotisserieWorkingBar.setVisibility(View.INVISIBLE);
			mMiniovenRotisserieTimeBar.setTimeBarStatus(enable);
			mMiniovenRotisserieTimeBar.setVisibility(View.INVISIBLE);
		}
	}

	private void setOvenStauts(boolean enable) {
		mOvenEnable = enable;
		if(enable) {
			mOvenLayout.setBackgroundResource(R.drawable.tv_oven_bg_on);
			mOvenTitle.setTextColor(mCslTextOn);
			mOvenMainWorkingBar.setProgressBarStatus(enable);
			int mode = mOvenDevice.getWorkMode();
			mode = mode == 0 ? DEFAULT_OVEN_MODE : mode;
			if(mode >= mOvenModeList.length) mode = mOvenModeList.length - 1;
			mOvenMode.setImageResource(mOvenModeList[mode][0]);
			mOvenTimeBar.setTimeBarStatus(enable);
			mOvenIconTempr.setImageResource(R.drawable.tv_icon_setting_sel_on);
			mOvenInfoTempr.setInfoBoxStatus(enable);
			mOvenInfoTempr.setInfoBox("160��", "120��");
			mOvenIconPower.setImageResource(R.drawable.tv_icon_setting_sel_on);
			mOvenInfoPower.setInfoBoxStatus(enable);
			mOvenInfoPower.setInfoBox("1000W", "1000W");
		} else {
			mOvenLayout.setBackgroundResource(R.drawable.tv_oven_bg_off);
			mOvenTitle.setTextColor(mCslTextOff);
			mOvenMainWorkingBar.setProgressBarStatus(enable);
			int mode = mOvenDevice.getWorkMode();
			mode = mode == 0 ? DEFAULT_OVEN_MODE : mode;
			if(mode >= mOvenModeList.length) mode = mOvenModeList.length - 1;
			mOvenMode.setImageResource(mOvenModeList[mode][1]);
			mOvenTimeBar.setTimeBarStatus(enable);
			mOvenIconTempr.setImageResource(R.drawable.tv_icon_setting_unsel_on);
			mOvenInfoTempr.setInfoBoxStatus(enable);
			mOvenInfoTempr.setInfoBox("160��", "120��");
			mOvenIconPower.setImageResource(R.drawable.tv_icon_setting_unsel_on);
			mOvenInfoPower.setInfoBoxStatus(enable);
			mOvenInfoPower.setInfoBox("1000W", "1000W");
		}
	}

	private void setRobotcleanStauts(boolean enable) {
		mRobotcleanEnable = enable;
		if(enable) {
			mRobotcleanLayout.setBackgroundResource(R.drawable.tv_clean_bg_on);
			mRobotcleanTitle.setTextColor(mCslTextOn);
			mRobotcleanBattery.setBatteryStatus(enable);
			mRobotcleanTimeBar.setTimeBarStatus(enable);
			mRobotcleanAutoMode.setImageResource(R.drawable.tv_clean_auto_on);
		} else {
			mRobotcleanLayout.setBackgroundResource(R.drawable.tv_clean_bg_off);
			mRobotcleanTitle.setTextColor(mCslTextOff);
			mRobotcleanBattery.setBatteryStatus(enable);
			mRobotcleanTimeBar.setTimeBarStatus(enable);
			mRobotcleanAutoMode.setImageResource(R.drawable.tv_clean_auto_off);
		}
	}

}
