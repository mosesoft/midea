package com.midea.iot.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.midea.iot.R;
import com.midea.iot.devices.BaseDevice;
import com.midea.iot.devices.Hob;
import com.midea.iot.devices.Hood;
import com.midea.iot.devices.Microwave;
import com.midea.iot.devices.Minioven;
import com.midea.iot.devices.Oven;
import com.midea.iot.devices.Robotclean;
import com.midea.iot.myview.PointInfo;
import com.midea.iot.pad.MainTvActivity;
import com.midea.iot.pad.PadMainHobWorkingView;
import com.midea.iot.pad.PadMainHoodWorkingView;
import com.midea.iot.pad.PadMainMwoWorkingView;
import com.midea.iot.pad.PadMainRobotCleanWorkingView;
import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.HobProtocol;
import com.midea.iot.protocol.HobProtocol.HobPosition;
import com.midea.iot.protocol.HoodProtocol;
import com.midea.iot.protocol.MicrowaveProtocol;
import com.midea.iot.protocol.MiniovenProtocol;
import com.midea.iot.protocol.OvenProtocol;
import com.midea.iot.protocol.RobotcleanProtocol;
import com.midea.iot.service.DeviceEventListener;
import com.midea.iot.service.DeviceManager;
import com.midea.iot.socket.BaseSocketThread;

public class MainActivity extends Activity {

	class ImageOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.microwave:
				clickId = AppConstant.MICROWAVE;
				System.out.println("***** mwo");
				if (isValidIPPort(clickId)
						&& myApplication.getConnStatus(clickId) == AppConstant.CONN_NORMAL) {
					// myApplication.setOperateStatus(clickId,
					// AppConstant.HAVE_OPERATE);
					startSettingActivity(clickId,
							myApplication.getWorkStatus(clickId));
				} else {
					showInputDialog();
				}
				break;
			case R.id.oven:
				clickId = AppConstant.OVEN;
				System.out.println("***** oven");
				if (isValidIPPort(clickId)
						&& myApplication.getConnStatus(clickId) == AppConstant.CONN_NORMAL) {
					// myApplication.setOperateStatus(clickId,
					// AppConstant.HAVE_OPERATE);
					startSettingActivity(clickId,
							myApplication.getWorkStatus(clickId));
				} else {
					showInputDialog();
				}
				break;
			case R.id.hood:
				clickId = AppConstant.HOOD;
				System.out.println("***** hood");
				if (isValidIPPort(clickId)
						&& myApplication.getConnStatus(clickId) == AppConstant.CONN_NORMAL) {
					// myApplication.setOperateStatus(clickId,
					// AppConstant.HAVE_OPERATE);
					startSettingActivity(clickId,
							myApplication.getWorkStatus(clickId));
				} else {
					showInputDialog();
				}
				break;
			case R.id.hob:
				clickId = AppConstant.HOB;
				System.out.println("***** hob");
				if (isValidIPPort(clickId)
						&& myApplication.getConnStatus(clickId) == AppConstant.CONN_NORMAL) {
					// myApplication.setOperateStatus(clickId,
					// AppConstant.HAVE_OPERATE);
					startSettingActivity(clickId,
							myApplication.getWorkStatus(clickId));
				} else {
					showInputDialog();
				}
				break;
			case R.id.minioven:
				clickId = AppConstant.MINIOVEN;
				System.out.println("***** minioven");
				if (isValidIPPort(clickId)
						&& myApplication.getConnStatus(clickId) == AppConstant.CONN_NORMAL) {
					// myApplication.setOperateStatus(clickId,
					// AppConstant.HAVE_OPERATE);
					startSettingActivity(clickId,
							myApplication.getWorkStatus(clickId));
				} else {
					showInputDialog();
				}
				break;
			case R.id.clean:
				clickId = AppConstant.CLEAN;
				System.out.println("***** clean");
				if (isValidIPPort(clickId)
						&& myApplication.getConnStatus(clickId) == AppConstant.CONN_NORMAL) {
					// myApplication.setOperateStatus(clickId,
					// AppConstant.HAVE_OPERATE);
					startSettingActivity(clickId,
							myApplication.getWorkStatus(clickId));
				} else {
					showInputDialog();
				}
				break;
			default:
				break;
			}
		}
	}
	class MyBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			final String action = arg1.getAction();
			if (Intent.ACTION_SCREEN_OFF.equals(action)) {

			} else if (Intent.ACTION_USER_PRESENT.equals(action)) {

			}
		}
	}

	class PopupOnDismissListener implements OnDismissListener {

		@Override
		public void onDismiss(DialogInterface dialog) {
			// TODO Auto-generated method stub
			int ip1 = 0;
			int ip2 = 0;
			int ip3 = 0;
			int ip4 = 0;
			String ip = ipedit.getText().toString();
			int port = 0;
			if (ip != null && !ip.equals("")) {
				String[] s = ip.split("\\.");
				System.out.println("**** s.length = " + s.length);
				if (s.length != 4) {
					Toast.makeText(MainActivity.this, "Please ReInput",
							Toast.LENGTH_SHORT).show();
				} else {
					try {
						ip1 = Integer.parseInt(s[0]);
						ip2 = Integer.parseInt(s[1]);
						ip3 = Integer.parseInt(s[2]);
						ip4 = Integer.parseInt(s[3]);
						if ((ip1 < 0 || ip1 > 255) || (ip2 < 0 || ip2 > 255)
								|| (ip3 < 0 || ip3 > 255)
								|| (ip4 < 0 || ip4 > 255)) {
							Toast.makeText(MainActivity.this, "Please ReInput",
									Toast.LENGTH_SHORT).show();
							return;
						}
					} catch (Exception e) {
						Toast.makeText(MainActivity.this, "Please ReInput",
								Toast.LENGTH_SHORT).show();
						return;
					}

				}
			} else {
				ip1 = 0;
				ip2 = 0;
				ip3 = 0;
				ip4 = 0;
			}

			if (portedit.getText().toString() != null
					&& !portedit.getText().toString().equals("")) {
				port = Integer.parseInt(portedit.getText().toString());
			} else {
				port = 0;
			}

			System.out.println("*****ip = " + ip
					+ ", AppConstant.label[id][0] = "
					+ AppConstant.label[id][0]);

			AppStatic.saveSharedPreData(AppConstant.label[clickId][0],
					Integer.toString(ip1), MainActivity.this);
			AppStatic.saveSharedPreData(AppConstant.label[clickId][1],
					Integer.toString(ip2), MainActivity.this);
			AppStatic.saveSharedPreData(AppConstant.label[clickId][2],
					Integer.toString(ip3), MainActivity.this);
			AppStatic.saveSharedPreData(AppConstant.label[clickId][3],
					Integer.toString(ip4), MainActivity.this);
			AppStatic.saveSharedPreData(AppConstant.label[clickId][4],
					Integer.toString(port), MainActivity.this);

			/*
			 * if(id == AppConstant.OVEN) { OvenDeamonConnect(); }
			 */

			connectToDevice(appConstantId2ProtocolType(clickId));
		}

	}
	private static final String TAG = "MainActivity";
	private static final boolean DEBUG = true;

	private ImageView microwave = null;
	private ImageView mwinfobg = null;
	private ImageView mwinfo = null;

	private ImageView oven = null;
	private ImageView oveninfobg = null;
	private ImageView oveninfo = null;

	private ImageView hood = null;
	private ImageView hoodinfobg = null;
	private ImageView hoodinfo = null;

	private ImageView hob = null;
	private ImageView hobinfobg = null;
	private ImageView hobinfo = null;

	private ImageView minioven = null;
	private ImageView minioveninfobg = null;
	private ImageView minioveninfo = null;

	private ImageView clean = null;

	/*
	 * private EditText ipedit1 = null; private EditText ipedit2 = null; private
	 * EditText ipedit3 = null; private EditText ipedit4 = null;
	 */

	private ImageView cleaninfobg = null;

	private ImageView cleaninfo = null;

	private ImageView mSwitchPadTv = null;

	private EditText ipedit = null;

	private EditText portedit = null;

	ImageView[] imageView = new ImageView[6];

	PointInfo[] points = null;

	PointInfo startPoint = null;

	int width, height;
	int moveX, moveY;

	boolean isUp = false;
	public StringBuffer lockString = new StringBuffer();
	public String password =  null;
	/*****************************************************/
	Oven mOven = null;
	Hob mHob = null;
	BaseProtocol mOvenData = null;
	BaseProtocol mHobData = null;
	BaseSocketThread mOvenThread = null;
	BaseSocketThread mHobThread = null;

	MyApplication myApplication;

	private boolean threadIsRunning = false;

	/************************ new new new *****************************/
	DeviceManager[] mDevice;

	private boolean mIsPad = false;

	private static MainActivity sInstance = null;

	public static MainActivity getInstance() {
		return sInstance;
	}

	private Handler mH = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_MICROWAVE_REPLY:
				microwaveHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_MICROWAVE_UPDATE:
				microwaveHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_MICROWAVE_TIMEOUT:
				microwaveHandleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_OVEN_REPLY:
				ovenHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_OVEN_UPDATE:
				ovenHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_OVEN_TIMEOUT:
				ovenHandleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_HOOD_REPLY:
				hoodHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_HOOD_UPDATE:
				hoodHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_HOOD_TIMEOUT:
				hoodHandleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_HOB_REPLY:
				hobHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_HOB_UPDATE:
				hobHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_HOB_TIMEOUT:
				hobHandleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_MINIOVEN_REPLY:
				miniovenHandleReplyMessage((byte[]) msg.obj);
				break;
			case MSG_MINIOVEN_UPDATE:
				miniovenHandleUpdateMessage((byte[]) msg.obj);
				break;
			case MSG_MINIOVEN_TIMEOUT:
				miniovenHandleTimeoutMessage((byte[]) msg.obj);
				break;

			case MSG_ROBOTCLEAN_REPLY:
				robotcleanHandleReplyMessage((byte[]) msg.obj);
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

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (AppConstant.ACTION_DEVICE_REPLY.equals(action)) {
				handleReplyMessage(intent.getByteArrayExtra("receive_data"));

			} else if (AppConstant.ACTION_DEVICE_UPDATE.equals(action)) {
				handleUpdateMessage(intent.getByteArrayExtra("receive_data"));
			} else if (AppConstant.ACTION_DEVICE_TIMEOUT.equals(action)) {
				handleTimeoutMessage(intent.getByteArrayExtra("receive_data"));
			}

			if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {
				int wifiState = intent.getIntExtra(
						WifiManager.EXTRA_WIFI_STATE, 0);

				switch (wifiState) {
				case WifiManager.WIFI_STATE_DISABLED:
					System.out.println("****WifiManager.WIFI_STATE_DISABLED");
					break;
				case WifiManager.WIFI_STATE_DISABLING:
					break;
				case WifiManager.WIFI_STATE_ENABLED:
					System.out.println("****WifiManager.WIFI_STATE_ENABLED");
					// mH.postDelayed(timerConnect,
					// AppConstant.TIMEOUT_TIME*2);
					break;
				case WifiManager.WIFI_STATE_ENABLING:
					break;
				case WifiManager.WIFI_STATE_UNKNOWN:
					break;
				}
			}
		}
	};

	private final Runnable timerTask = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// showAlertDialog(1);
			if (myApplication.isActivityVisible(AppConstant.MAIN_ACTIVITY_NAME)) {
				System.out.println("############ Task MyAlertDialog");
//				new MyAlertDialog(MainActivity.this, mH, myApplication);
			}
			setConnectStatus(AppConstant.OVEN, AppConstant.CONN_FAILED);
			setWorkStatus(AppConstant.OVEN, AppConstant.COOKING_WAIT);

		}
	};

	int left = 0;

	int top = 0;

	int mode = 0;

	int remainTime = 0;

	int temp = 0;

	/*
	 * public void startCookingActivity(int id){ Intent intent = new Intent();
	 * intent.setClass(MainActivity.this, CookingActivity.class);
	 * intent.putExtra("ID", id); intent.putExtra("MODE", mode);
	 * 
	 * String timeStr = AppStatic.getSharedPreData(AppConstant.time_label[id],
	 * MainActivity.this);
	 * //System.out.println("******startCookingActivity timeStr = " + timeStr);
	 * System.out.println("******getSharedPreData time_label = " +
	 * AppConstant.time_label[id] + ", time = " + timeStr); int time =
	 * Integer.parseInt(timeStr); if(time <= 0){ time = remainTime; }
	 * intent.putExtra("TIME", time); intent.putExtra("TEMP", temp);
	 * intent.putExtra("FLAG", true); startActivity(intent); }
	 */
	int clickId = AppConstant.MICROWAVE;

	private int id = 0;

	private int ipNum1 = 0;

	private int ipNum2 = 0;

	private int ipNum3 = 0;

	private int ipNum4 = 0;

	// private Handler mHandler = new Handler();

	private int portNum = 0;

	/*************************** for pad ***************************************/

	private static final int MSG_MICROWAVE_REPLY = 1101;

	private static final int MSG_MICROWAVE_UPDATE = 1102;

	private static final int MSG_MICROWAVE_TIMEOUT = 1103;

	private static final int MSG_OVEN_REPLY = 1104;

	private static final int MSG_OVEN_UPDATE = 1105;

	private static final int MSG_OVEN_TIMEOUT = 1106;

	private static final int MSG_MINIOVEN_REPLY = 1107;

	private static final int MSG_MINIOVEN_UPDATE = 1108;

	private static final int MSG_MINIOVEN_TIMEOUT = 1109;

	private static final int MSG_ROBOTCLEAN_REPLY = 1111;

	private static final int MSG_ROBOTCLEAN_UPDATE = 1112;

	private static final int MSG_ROBOTCLEAN_TIMEOUT = 1113;

	private static final int MSG_HOOD_REPLY = 1114;

	private static final int MSG_HOOD_UPDATE = 1115;

	private static final int MSG_HOOD_TIMEOUT = 1116;

	private static final int MSG_HOB_REPLY = 1117;

	private static final int MSG_HOB_UPDATE = 1118;

	/*
	 * class PopupOnDismissListener implements OnDismissListener{
	 * 
	 * @Override public void onDismiss() { // TODO Auto-generated method stub
	 * String ip = ipedit1.getText().toString() + ipedit2.getText().toString() +
	 * ipedit3.getText().toString() + ipedit4.getText().toString();
	 * System.out.println("*****ip = " + ip); }
	 * 
	 * }
	 */

	private static final int MSG_HOB_TIMEOUT = 1119;
	private PadMainMwoWorkingView mMicrowaveWorkingView = null;

	private ImageView mMicrowaveWorkingViewLine = null;

	private PadMainMwoWorkingView mOvenWorkingView = null;

	private ImageView mOvenWorkingViewLine = null;
	private PadMainHoodWorkingView mHoodWorkingView = null;
	private ImageView mHoodWorkingViewLine = null;
	private PadMainHobWorkingView mHobWorkingView = null;

	private ImageView mHobWorkingViewLine = null;

	private PadMainMwoWorkingView mMiniovenWorkingView = null;
	private ImageView mMiniovenWorkingViewLine = null;
	private PadMainRobotCleanWorkingView mRobotcleanWorkingView = null;
	private boolean b1 = false;
	private boolean b2 = false;
	private boolean b3 = false;

	private boolean b4 = false;

	private boolean b5 = false;

	private boolean b6 = false;

	Runnable mWaitDeviceWorking = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mDevice[BaseProtocol.TYPE_MICROWAVE].isConnected()) {
				mMicrowaveWorkingView.setVisibility(View.VISIBLE);
				b1 = true;
			}

			if (mDevice[BaseProtocol.TYPE_OVEN].isConnected()) {
				mOvenWorkingView.setVisibility(View.VISIBLE);
				b2 = true;
			}

			if (mDevice[BaseProtocol.TYPE_HOB].isConnected()) {
				mHobWorkingView.setVisibility(View.VISIBLE);
				b3 = true;
			}

			if (mDevice[BaseProtocol.TYPE_HOOD].isConnected()) {
				mHoodWorkingView.setVisibility(View.VISIBLE);
				b4 = true;
			}

			if (mDevice[BaseProtocol.TYPE_MINIOVEN].isConnected()) {
				mMiniovenWorkingView.setVisibility(View.VISIBLE);
				b5 = true;
			}

			if (mDevice[BaseProtocol.TYPE_ROBOTCLEAN].isConnected()) {
				mRobotcleanWorkingView.setVisibility(View.VISIBLE);
				b6 = true;
			}

		}

	};

	private DeviceEventListener mOvenListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_OVEN_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_OVEN_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_OVEN_TIMEOUT, event).sendToTarget();
		}
	};

	private DeviceEventListener mMicroWaveListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_MICROWAVE_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_MICROWAVE_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_MICROWAVE_TIMEOUT, event).sendToTarget();
		}
	};

	private DeviceEventListener mHoodListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_HOOD_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_HOOD_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_HOOD_TIMEOUT, event).sendToTarget();
		}
	};

	private DeviceEventListener mHobListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_HOB_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_HOB_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_HOB_TIMEOUT, event).sendToTarget();
		}
	};

	private DeviceEventListener mMiniovenListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_MINIOVEN_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_MINIOVEN_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_MINIOVEN_TIMEOUT, event).sendToTarget();
		}
	};

	private DeviceEventListener mRobotCleanListener = new DeviceEventListener() {

		@Override
		public void onDeviceEventReply(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_ROBOTCLEAN_REPLY, event).sendToTarget();
		}

		@Override
		public void onDeviceEventUpdate(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_ROBOTCLEAN_UPDATE, event).sendToTarget();
		}

		@Override
		public void onDeviceTimeout(byte[] event) {
			// TODO Auto-generated method stub
			mH.obtainMessage(MSG_ROBOTCLEAN_TIMEOUT, event).sendToTarget();
		}
	};

	private int[] mMicrowaveModeList = {
			0,
			R.drawable.pad_main_mwo_icon_sel_1,
			R.drawable.pad_main_mwo_icon_sel_2,
			R.drawable.pad_main_mwo_icon_sel_3,
			R.drawable.pad_main_mwo_icon_sel_4,
			R.drawable.pad_main_mwo_icon_sel_5,
	};
	private int[] mOvenModeList = {
			0,
			R.drawable.pad_main_oven_icon_sel_1,
			R.drawable.pad_main_oven_icon_sel_2,
			R.drawable.pad_main_oven_icon_sel_3,
			R.drawable.pad_main_oven_icon_sel_4,
			R.drawable.pad_main_oven_icon_sel_5,
			R.drawable.pad_main_oven_icon_sel_6,
			R.drawable.pad_main_oven_icon_sel_7,
			R.drawable.pad_main_oven_icon_sel_8,
	};
	private int[] mHoodModeList = {
			0,
			R.drawable.pad_main_hood_icon_sel_1,
			R.drawable.pad_main_hood_icon_sel_2,
			R.drawable.pad_main_hood_icon_sel_3,
	};

	private int[] mMiniovenModeList = {
			0,
			R.drawable.pad_main_minioven_icon_sel_1,
			R.drawable.pad_main_minioven_icon_sel_2,
			R.drawable.pad_main_minioven_icon_sel_3,
			R.drawable.pad_main_minioven_icon_sel_4,
			R.drawable.pad_main_minioven_icon_sel_5,
	};
	private int mMicrowaveSettingTime = 0;
	///*********test********//
	int testPregress = 0;

	Runnable mTestRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			testPregress ++ ;
			if(testPregress > 100) {
				testPregress = 0;
			}
			
			mH.postDelayed(mTestRunnable, 1000);
			
			mMicrowaveWorkingView.updateView(testPregress, 200, 2);
		}
		
	};
	////*******test end*********//
	private int mOvenSettingTime = 0;
	private int mOvenPreRemainTime = 0;

	private long mOvenStartTime = 0;
	private int mMiniovenSettingTime = 0;
	private int appConstantId2ProtocolType(int appConstantId) {
		switch (appConstantId) {
		case AppConstant.MICROWAVE:
			return BaseProtocol.TYPE_MICROWAVE;
		case AppConstant.HOB:
			return BaseProtocol.TYPE_HOB;
		case AppConstant.HOOD:
			return BaseProtocol.TYPE_HOOD;
		case AppConstant.MINIOVEN:
			return BaseProtocol.TYPE_MINIOVEN;
		case AppConstant.OVEN:
			return BaseProtocol.TYPE_OVEN;
		case AppConstant.CLEAN:
			return BaseProtocol.TYPE_ROBOTCLEAN;
		default:
			System.out.println("wrong app constant id!");
			return 0;
		}
	}

	private void connectAllDevices() {
		for (int i = 1; i < 7; i++) {
			connectToDevice(i);
		}
	}
	private void connectToDevice(int type) {
		if (type > 6 || type < 1) {
			System.out.println("wrong device type!");
			return;
		}
		/*
		 * if(mDevice[type].threadIsRunning()) { mDevice[type].reset(); }
		 */
		int id = protocolType2AppConstantId(type);
		System.out.println("*****connectToDevice id = " + id);
		if (isValidIPPort(id)) {
			final String ipAddress = getIpAddress(id);
			System.out.println("*****connectToDevice ipAddress = " + ipAddress);
			final int portId = getPortId(id);
			System.out.println("*****connectToDevice portId = " + portId);
			final int fType = type;
			// mDevice[fType].setIpAddressPort(ipAddress, portId);
			// mDevice[fType].connectToDevice(ipAddress, portId);
			new Thread() {
				@Override
				public void run() {
					mDevice[fType].connectToDevice(ipAddress, portId);
					// ִ����Ϻ��mH����һ������Ϣ
					// mH.sendEmptyMessage(0);
				}
			}.start();
		}
	}
	public Robotclean getCleanDevice() {
		return (Robotclean) getDevice(BaseProtocol.TYPE_ROBOTCLEAN);
	}

	public BaseDevice getDevice(int baseProtocolType) {
		return mDevice[baseProtocolType].getDevice();
	}
	public DeviceManager getDeviceManager(int deviceID) {
		if (deviceID < 1 || deviceID > 6)
			return null;
		return mDevice[deviceID];
	}
	public Hob getHobDevice() {
		return (Hob) getDevice(BaseProtocol.TYPE_HOB);
	}

	public Hood getHoodDevice() {
		return (Hood) getDevice(BaseProtocol.TYPE_HOOD);
	}
	private String getIpAddress(int id) {
		String ip1 = AppStatic.getSharedPreData(AppConstant.label[id][0],
				MainActivity.this);
		String ip2 = AppStatic.getSharedPreData(AppConstant.label[id][1],
				MainActivity.this);
		String ip3 = AppStatic.getSharedPreData(AppConstant.label[id][2],
				MainActivity.this);
		String ip4 = AppStatic.getSharedPreData(AppConstant.label[id][3],
				MainActivity.this);

		return ip1 + "." + ip2 + "." + ip3 + "." + ip4;
	}
	public Microwave getMicrowaveDevice() {
		return (Microwave) getDevice(BaseProtocol.TYPE_MICROWAVE);
	}
	public Minioven getMiniovenDevice() {
		return (Minioven) getDevice(BaseProtocol.TYPE_MINIOVEN);
	}
	public Oven getOvenDevice() {
		return (Oven) getDevice(BaseProtocol.TYPE_OVEN);
	}
	private int getPortId(int id) {
		String port = AppStatic.getSharedPreData(AppConstant.label[id][4],
				MainActivity.this);
		return Integer.parseInt(port);
	}
	private void handleReplyMessage(byte[] b) {
		byte type = b[BaseProtocol.PROTOCOL_TYPE];
		int deviceId = 0;
		int workState = 0;
		System.out.println("*****handleReplyMessage");
		switch (type) {
		case BaseProtocol.TYPE_HOB:
			Hob hob = (Hob) MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_HOB - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);

			setWorkStatus(deviceId, hob.getWorkStatus());
			break;

		case BaseProtocol.TYPE_OVEN:
			Oven oven = (Oven) MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_OVEN - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);
			workState = oven.getWorkStatus() - 1;
			if (workState < AppConstant.COOKING_WAIT) {
				workState = AppConstant.COOKING_WAIT;
			}
			setWorkStatus(deviceId, workState);
			break;

		case BaseProtocol.TYPE_HOOD:
			Hood hood = (Hood) MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_HOOD - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);

			setWorkStatus(deviceId, hood.getWorkStatus());
			break;

		case BaseProtocol.TYPE_MICROWAVE:
			Microwave microwave = (Microwave) MainActivity.getInstance()
					.getDevice(type);
			deviceId = BaseProtocol.TYPE_MICROWAVE - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);
			workState = microwave.getWorkStatus() - 1;
			if (workState < AppConstant.COOKING_WAIT) {
				workState = AppConstant.COOKING_WAIT;
			}
			setWorkStatus(deviceId, workState);
			break;

		case BaseProtocol.TYPE_ROBOTCLEAN:
			Robotclean robotclean = (Robotclean) MainActivity.getInstance()
					.getDevice(type);
			deviceId = BaseProtocol.TYPE_ROBOTCLEAN - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);

			setWorkStatus(deviceId, robotclean.getWorkStatus());
			break;

		case BaseProtocol.TYPE_MINIOVEN:
			Minioven minioven = (Minioven) MainActivity.getInstance()
					.getDevice(type);
			deviceId = BaseProtocol.TYPE_MINIOVEN - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);
			workState = minioven.getWorkStatus() - 1;
			if (workState < AppConstant.COOKING_WAIT) {
				workState = AppConstant.COOKING_WAIT;
			}
			setWorkStatus(deviceId, workState);
			break;

		default:
			return;
		}

		mH.removeCallbacks(timerTask);
		mH.postDelayed(timerTask, AppConstant.TIMEOUT_TIME);
	}
	private void handleTimeoutMessage(byte[] b) {
		System.out.println("============Receive Timeout Message============");
		// myApplication.showAlertDialog(MainActivity.this, 1, mH);
		if (myApplication.isActivityVisible(AppConstant.MAIN_ACTIVITY_NAME)) {
			System.out.println("############ Main MyAlertDialog");
//			new MyAlertDialog(MainActivity.this, mH, myApplication);
		}
		System.out.println("==============dialog 2===============");
		
		byte type = b[BaseProtocol.PROTOCOL_TYPE];
		int deviceId = 0;
		switch (type) {
		case BaseProtocol.TYPE_HOB:
			deviceId = BaseProtocol.TYPE_HOB - 1;
			setConnectStatus(deviceId, AppConstant.CONN_FAILED);
			break;

		case BaseProtocol.TYPE_OVEN:
			deviceId = BaseProtocol.TYPE_OVEN - 1;
			setConnectStatus(deviceId, AppConstant.CONN_FAILED);
			break;

		case BaseProtocol.TYPE_HOOD:
			deviceId = BaseProtocol.TYPE_HOOD - 1;
			setConnectStatus(deviceId, AppConstant.CONN_FAILED);
			break;

		case BaseProtocol.TYPE_MICROWAVE:
			deviceId = BaseProtocol.TYPE_MICROWAVE - 1;
			setConnectStatus(deviceId, AppConstant.CONN_FAILED);
			break;

		case BaseProtocol.TYPE_ROBOTCLEAN:
			deviceId = BaseProtocol.TYPE_ROBOTCLEAN - 1;
			setConnectStatus(deviceId, AppConstant.CONN_FAILED);
			break;

		case BaseProtocol.TYPE_MINIOVEN:
			deviceId = BaseProtocol.TYPE_MINIOVEN - 1;
			setConnectStatus(deviceId, AppConstant.CONN_FAILED);
			break;

		default:
			return;
		}
	}
	private void handleUpdateMessage(byte[] b) {
		byte type = b[BaseProtocol.PROTOCOL_TYPE];
		int deviceId = 0;
		int workState = 0;
		System.out.println("*****handleUpdateMessage");
		switch (type) {
		case BaseProtocol.TYPE_HOB:
			Hob hob = (Hob) MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_HOB - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);

			setWorkStatus(deviceId, hob.getWorkStatus());
			break;

		case BaseProtocol.TYPE_OVEN:
			Oven oven = (Oven) MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_OVEN - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);
			workState = oven.getWorkStatus() - 1;
			if (workState < AppConstant.COOKING_WAIT) {
				workState = AppConstant.COOKING_WAIT;
			}
			setWorkStatus(deviceId, workState);
			break;

		case BaseProtocol.TYPE_HOOD:
			Hood hood = (Hood) MainActivity.getInstance().getDevice(type);
			deviceId = BaseProtocol.TYPE_HOOD - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);

			setWorkStatus(deviceId, hood.getWorkStatus());
			break;

		case BaseProtocol.TYPE_MICROWAVE:
			Microwave microwave = (Microwave) MainActivity.getInstance()
					.getDevice(type);
			deviceId = BaseProtocol.TYPE_MICROWAVE - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);
			workState = microwave.getWorkStatus() - 1;
			if (workState < AppConstant.COOKING_WAIT) {
				workState = AppConstant.COOKING_WAIT;
			}
			setWorkStatus(deviceId, workState);
			break;

		case BaseProtocol.TYPE_ROBOTCLEAN:
			Robotclean robotclean = (Robotclean) MainActivity.getInstance()
					.getDevice(type);
			deviceId = BaseProtocol.TYPE_ROBOTCLEAN - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);

			setWorkStatus(deviceId, robotclean.getWorkStatus());
			break;

		case BaseProtocol.TYPE_MINIOVEN:
			Minioven minioven = (Minioven) MainActivity.getInstance()
					.getDevice(type);
			deviceId = BaseProtocol.TYPE_MINIOVEN - 1;
			setConnectStatus(deviceId, AppConstant.CONN_NORMAL);
			workState = minioven.getWorkStatus() - 1;
			if (workState < AppConstant.COOKING_WAIT) {
				workState = AppConstant.COOKING_WAIT;
			}
			setWorkStatus(deviceId, workState);
			break;

		default:
			return;
		}

		mH.removeCallbacks(timerTask);
		mH.postDelayed(timerTask, AppConstant.TIMEOUT_TIME);
	}
	private void hobHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleReplyMessage");
		hobUpdateStatus();
	}
	private void hobHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "hobHandleTimeoutMessage");
		if(b4) {
			b4 = false;
			mHobWorkingView.setVisibility(View.INVISIBLE);
			updateWorkingViewLine();
		}
	}

	private void hobHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "hobHandleUpdateMessage");
		hobUpdateStatus();
	}
	
	private void hobUpdateStatus() {
		if(mDevice[BaseProtocol.TYPE_HOB].getData().getType() == BaseProtocol.TYPE_HOB) {
			Hob device = getHobDevice();
			
			boolean hob1, hob2, hob3, hob4;
			
			int status_master = device.getWorkStatus();
			if(status_master == 1) {
				hob1 = device.getWorkStatus(HobPosition.HP_LEFT_CORNER) == 1;
				hob2 = device.getWorkStatus(HobPosition.HP_RIGHT_CORNER) == 1;
				hob3 = device.getWorkStatus(HobPosition.HP_BOTTOM_LEFT) == 1;
				hob4 = device.getWorkStatus(HobPosition.HP_BOTTOM_RIGHT) == 1;
			} else {
				hob1 = hob2 = hob3 = hob4 = false;
			}
			
			if(!b4) {
				b4 = true;
				mHobWorkingView.setVisibility(View.VISIBLE);
				updateWorkingViewLine();
			}
			
			mHobWorkingView.updateView(hob1, hob2, hob3, hob4);
		}
	}
	private void hoodHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleReplyMessage");
		hoodUpdateStatus();
	}
	private void hoodHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "hoodHandleTimeoutMessage");
		if(b3) {
			b3 = false;
			mHoodWorkingView.setVisibility(View.INVISIBLE);
			updateWorkingViewLine();
		}
	}
	private void hoodHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "hoodHandleUpdateMessage");
		hoodUpdateStatus();
	}
	private void hoodUpdateStatus() {
		if(mDevice[BaseProtocol.TYPE_HOOD].getData().getType() == BaseProtocol.TYPE_HOOD) {
			Hood device = getHoodDevice();
			
			int mode = device.getWorkStatus();
			int remainTime = device.getRunningTime();
			
			if(!b3) {
				b3 = true;
				mHoodWorkingView.setVisibility(View.VISIBLE);
				updateWorkingViewLine();
			}
			
			if(mode < 1) mode = 1;
			if(mode >= mHoodModeList.length) mode = mHoodModeList.length - 1;
			
			int modeId = mHoodModeList[mode];
			mHoodWorkingView.setHandler(mH);
			boolean moving = false;
			if(device.getWorkStatus() == HoodProtocol.WORK_STATUS_STOP) {
				moving = false;
			} else {
				moving = true;
			}
			mHoodWorkingView.updateView(remainTime, modeId, moving);
		}
	}
	/************************ new new new *****************************/
	private void initDevices() {
		mDevice = new DeviceManager[7];

		initMicrowave();
		initOven();
		initHood();
		initHob();
		initMiniOven();
		initRobotclean();

		connectAllDevices();
	}

	private void initDevices2() {
		
		if(!mIsPad) return;
		mDevice[BaseProtocol.TYPE_MICROWAVE]
				.registerDeviceEventListener(mMicroWaveListener);
		mDevice[BaseProtocol.TYPE_OVEN]
				.registerDeviceEventListener(mOvenListener);
		mDevice[BaseProtocol.TYPE_HOB]
				.registerDeviceEventListener(mHobListener);
		mDevice[BaseProtocol.TYPE_HOOD]
				.registerDeviceEventListener(mHoodListener);
		mDevice[BaseProtocol.TYPE_MINIOVEN]
				.registerDeviceEventListener(mMiniovenListener);
		mDevice[BaseProtocol.TYPE_ROBOTCLEAN]
				.registerDeviceEventListener(mRobotCleanListener);

		mH.removeCallbacks(mWaitDeviceWorking);
		mH.postDelayed(mWaitDeviceWorking, 5000);
//		mH.postDelayed(mTestRunnable, 5000);
	}
	
	private void initHob() {
		mDevice[BaseProtocol.TYPE_HOB] = new DeviceManager(new HobProtocol(
				MainActivity.this), new BaseSocketThread(), new Hob());
		mDevice[BaseProtocol.TYPE_HOB].setDeviceType(BaseProtocol.TYPE_HOB);
	}

	private void initHood() {
		mDevice[BaseProtocol.TYPE_HOOD] = new DeviceManager(new HoodProtocol(
				MainActivity.this), new BaseSocketThread(), new Hood());
		mDevice[BaseProtocol.TYPE_HOOD].setDeviceType(BaseProtocol.TYPE_HOOD);
	}

	private void initMicrowave() {
		mDevice[BaseProtocol.TYPE_MICROWAVE] = new DeviceManager(
				new MicrowaveProtocol(MainActivity.this),
				new BaseSocketThread(), new Microwave());
		mDevice[BaseProtocol.TYPE_MICROWAVE]
				.setDeviceType(BaseProtocol.TYPE_MICROWAVE);
	}

	private void initMiniOven() {
		mDevice[BaseProtocol.TYPE_MINIOVEN] = new DeviceManager(
				new MiniovenProtocol(MainActivity.this),
				new BaseSocketThread(), new Minioven());
		mDevice[BaseProtocol.TYPE_MINIOVEN]
				.setDeviceType(BaseProtocol.TYPE_MINIOVEN);
	}

	private void initOven() {
		mDevice[BaseProtocol.TYPE_OVEN] = new DeviceManager(new OvenProtocol(
				MainActivity.this), new BaseSocketThread(), new Oven());
		mDevice[BaseProtocol.TYPE_OVEN].setDeviceType(BaseProtocol.TYPE_OVEN);
	}

	private void initPoints() {
		points = new PointInfo[6];
		int len = points.length;
		int left = 0;
		int top = 0;

		for (int i = 0; i < len; i++) {
			int[] location = new int[2];
			imageView[i].getLocationOnScreen(location);
			left = location[0];
			top = location[1];
			points[i] = new PointInfo(i, left, top, left
					+ imageView[i].getWidth(), top + imageView[i].getHeight());

		}
	}

	private void initRobotclean() {
		mDevice[BaseProtocol.TYPE_ROBOTCLEAN] = new DeviceManager(
				new RobotcleanProtocol(MainActivity.this),
				new BaseSocketThread(), new Robotclean());
		mDevice[BaseProtocol.TYPE_ROBOTCLEAN]
				.setDeviceType(BaseProtocol.TYPE_ROBOTCLEAN);
	}

	private void initView() {

		microwave = (ImageView) findViewById(R.id.microwave);
		mwinfobg = (ImageView) findViewById(R.id.mwinfobg);
		mwinfo = (ImageView) findViewById(R.id.mwinfo);

		oven = (ImageView) findViewById(R.id.oven);
		oveninfobg = (ImageView) findViewById(R.id.oveninfobg);
		oveninfo = (ImageView) findViewById(R.id.oveninfo);

		hood = (ImageView) findViewById(R.id.hood);
		hoodinfobg = (ImageView) findViewById(R.id.hoodinfobg);
		hoodinfo = (ImageView) findViewById(R.id.hoodinfo);

		hob = (ImageView) findViewById(R.id.hob);
		hobinfobg = (ImageView) findViewById(R.id.hobinfobg);
		hobinfo = (ImageView) findViewById(R.id.hobinfo);

		minioven = (ImageView) findViewById(R.id.minioven);
		minioveninfobg = (ImageView) findViewById(R.id.minioveninfobg);
		minioveninfo = (ImageView) findViewById(R.id.minioveninfo);

		clean = (ImageView) findViewById(R.id.clean);
		cleaninfobg = (ImageView) findViewById(R.id.cleaninfobg);
		cleaninfo = (ImageView) findViewById(R.id.cleaninfo);

		mSwitchPadTv = (ImageView) findViewById(R.id.switch_pad_tv);
		if (mSwitchPadTv != null) {
			mSwitchPadTv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, MainTvActivity.class);
					startActivity(intent);
				}
			});
		}

		microwave.setOnClickListener(new ImageOnClickListener());
		microwave.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				System.out.println("***** lockString ****");
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_MOVE:
					moveX = (int) arg1.getX();
					moveY = (int) arg1.getY();
					System.out
							.println("***** lockString1 ACTION_MOVE2 moveX = "
									+ moveX + ", moveY = " + moveY);
					for (PointInfo temp : points) {
						if (temp.isInMyPlace(moveX + left, moveY + top)
								&& temp.isSelected() == false) {
							temp.setSelected(true);
							int len = lockString.length();
							if (len != 0) {
								int preId = lockString.charAt(len - 1) - 48;
								points[preId].setNextId(temp.getId());
							}
							lockString.append(temp.getId());
							System.out.println("***** lockString2  = "
									+ lockString);
							break;
						}
					}

					break;

				case MotionEvent.ACTION_DOWN:
					int[] location = new int[2];
					microwave.getLocationOnScreen(location);
					left = location[0];
					top = location[1];
					int downX = (int) arg1.getX();
					int downY = (int) arg1.getY();
					if (points == null) {
						initPoints();
					}
					System.out.println("***** lockString1 5 downX = " + downX
							+ ", downY = " + downY);
					if (points != null) {
						System.out.println("***** lockString1 1");
						for (PointInfo temp : points) {
							System.out.println("***** lockString1 1");
							if (temp.isInMyPlace(downX + left, downY + top)) {
								temp.setSelected(true);
								lockString.append(temp.getId());
								System.out.println("***** lockString1  = "
										+ lockString);
								break;
							}
						}
					}
					break;

				case MotionEvent.ACTION_UP:
					moveX = moveY = 0;
					if (password.equals(lockString.toString())) {
						lockString.delete(0, lockString.length());
						System.out
								.println("***** lockString3  = " + lockString);
						Intent intent = new Intent();
						intent.setAction(AppConstant.ACTION_EXIT);
						sendBroadcast(intent);
						finish();
					} else {
						System.out
								.println("***** lockString4  = " + lockString);
						lockString.delete(0, lockString.length());
					}
					for (PointInfo temp : points) {
						temp.setSelected(false);
					}

					break;
				default:
					break;
				}
				return false;
			}

		});
		
		
		oven.setOnClickListener(new ImageOnClickListener());
		hood.setOnClickListener(new ImageOnClickListener());
		hob.setOnClickListener(new ImageOnClickListener());
		minioven.setOnClickListener(new ImageOnClickListener());
		clean.setOnClickListener(new ImageOnClickListener());

		imageView[0] = microwave;
		imageView[1] = oven;
		imageView[2] = hood;
		imageView[3] = hob;
		imageView[4] = minioven;
		imageView[5] = clean;


	}

	private void initView2() {
		if (mIsPad) {
			mMicrowaveWorkingView = (PadMainMwoWorkingView) findViewById(R.id.working_device_microwave);
			mMicrowaveWorkingViewLine = (ImageView) findViewById(R.id.working_device_microwave_line);

			mOvenWorkingView = (PadMainMwoWorkingView) findViewById(R.id.working_device_oven);
			mOvenWorkingView.setTimeIndicator(true);
			mOvenWorkingViewLine = (ImageView) findViewById(R.id.working_device_oven_line);

			mHoodWorkingView = (PadMainHoodWorkingView) findViewById(R.id.working_device_hood);
			mHoodWorkingViewLine = (ImageView) findViewById(R.id.working_device_hood_line);

			mHobWorkingView = (PadMainHobWorkingView) findViewById(R.id.working_device_hob);
			mHobWorkingViewLine = (ImageView) findViewById(R.id.working_device_hob_line);

			mMiniovenWorkingView = (PadMainMwoWorkingView) findViewById(R.id.working_device_minioven);
			mMiniovenWorkingViewLine = (ImageView) findViewById(R.id.working_device_minioven_line);

			mRobotcleanWorkingView = (PadMainRobotCleanWorkingView) findViewById(R.id.working_device_robotclean);
		}
	}

	private boolean isValidIPPort(int id) {

		String ip1 = AppStatic.getSharedPreData(AppConstant.label[id][0],
				MainActivity.this);
		System.out
				.println("*******isValidIPPort id = " + id + ", ip1 = " + ip1);
		String ip2 = AppStatic.getSharedPreData(AppConstant.label[id][1],
				MainActivity.this);
		String ip3 = AppStatic.getSharedPreData(AppConstant.label[id][2],
				MainActivity.this);
		String ip4 = AppStatic.getSharedPreData(AppConstant.label[id][3],
				MainActivity.this);
		String port = AppStatic.getSharedPreData(AppConstant.label[id][4],
				MainActivity.this);
		ipNum1 = 0;
		ipNum2 = 0;
		ipNum3 = 0;
		ipNum4 = 0;
		portNum = 0;
		if (ip1 != null && !ip1.equals("")) {
			ipNum1 = Integer.parseInt(ip1);
		} else {
			ipNum1 = 0;
		}
		if (ip2 != null && !ip2.equals("")) {
			ipNum2 = Integer.parseInt(ip2);
		} else {
			ipNum2 = 0;
		}
		if (ip3 != null && !ip3.equals("")) {
			ipNum3 = Integer.parseInt(ip3);
		} else {
			ipNum3 = 0;
		}
		if (ip4 != null && !ip4.equals("")) {
			ipNum4 = Integer.parseInt(ip4);
		} else {
			ipNum4 = 0;
		}
		if (port != null && !port.equals("")) {
			portNum = Integer.parseInt(port);
		} else {
			portNum = 0;
		}
		if ((ipNum1 == 0 && ipNum2 == 0 && ipNum3 == 0 && ipNum4 == 0)
				|| portNum == 0) {
			return false;
		} else {
			return true;
		}

	}

	private void microwaveHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleReplyMessage");
		microwaveUpdateStatus();
	}

	private void microwaveHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "microwaveHandleTimeoutMessage");
		if(b1) {
			b1 = false;
			mMicrowaveWorkingView.setVisibility(View.INVISIBLE);
			updateWorkingViewLine();
		}
	}

	private void microwaveHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "microwaveHandleUpdateMessage");
		microwaveUpdateStatus();
	}

	private void microwaveUpdateStatus() {
		if(mDevice[BaseProtocol.TYPE_MICROWAVE].getData().getType() == BaseProtocol.TYPE_MICROWAVE) {
			Microwave device = getMicrowaveDevice();
			
			int mode = device.getWorkMode();
			int remainTime = device.getRemainTime();
			if(remainTime > mMicrowaveSettingTime) {
				mMicrowaveSettingTime = remainTime;
			}
			
			if(remainTime == 0) {
				mMicrowaveSettingTime = 0;
			}
//			int totalTime = microwave.getTotalTime();
//			if (totalTime == 0)
//				totalTime = 1;
			
			int progress = 0;
			if(mMicrowaveSettingTime == 0) {
				progress = 100;
			} else {
				progress = 100 - (remainTime * 100 / mMicrowaveSettingTime);
			}
			
			if(!b1) {
				b1 = true;
				mMicrowaveWorkingView.setVisibility(View.VISIBLE);
				updateWorkingViewLine();
			}
			
			if(mode < 1) mode = 1;
			if(mode >= mMicrowaveModeList.length) mode = mMicrowaveModeList.length - 1;
			
			int modeId = mMicrowaveModeList[mode];
			
			if(device.getWorkStatus() == MicrowaveProtocol.STATUS_INFO_STANDBY) {
				remainTime = 0; 
				mMicrowaveSettingTime = 0;
				progress = 100;
			}

			mMicrowaveWorkingView.updateView(progress, remainTime, modeId);
		}
	}

	private void miniovenHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleReplyMessage");
		miniovenUpdateStatus();
	}

	private void miniovenHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "miniovenHandleTimeoutMessage");
		if(b5) {
			b5 = false;
			mMiniovenWorkingView.setVisibility(View.INVISIBLE);
			updateWorkingViewLine();
		}
	}

	private void miniovenHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "miniovenHandleUpdateMessage");
		miniovenUpdateStatus();
	}

	private void miniovenUpdateStatus() {
		if(mDevice[BaseProtocol.TYPE_MINIOVEN].getData().getType() == BaseProtocol.TYPE_MINIOVEN) {
			Minioven device = getMiniovenDevice();
			
			int mode = device.getWorkMode();
			int remainTime = device.getRemainTime();
			
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
			
			if(!b5) {
				b5 = true;
				mMiniovenWorkingView.setVisibility(View.VISIBLE);
				updateWorkingViewLine();
			}
			
			if(mode < 1) mode = 1;
			if(mode >= mMiniovenModeList.length) mode = mMiniovenModeList.length - 1;
			
			int modeId = mMiniovenModeList[mode];
			
			if(device.getWorkStatus() == MiniovenProtocol.STATUS_INFO_STANDBY) {
				remainTime = 0; 
				mMiniovenSettingTime = 0;
				progress = 100;
			}
			
			mMiniovenWorkingView.updateView(progress, remainTime, modeId);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}

	/******************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myApplication = (MyApplication) getApplication();
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		mIsPad = width >= 1200;

		initView();
		initView2();
		initDevices();
		initDevices2();
		registerBroadcastReceiver();
		if(!mIsPad){
			password = AppConstant.PASSWORD1;
		}else{
			password = AppConstant.PASSWORD2;
		}
		sInstance = this;
		mH.removeCallbacks(timerTask);
		mH.postDelayed(timerTask, AppConstant.TIMEOUT_TIME);
		ScreenControl.init(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		myApplication.cleanAllStatus();
		mH.removeCallbacks(timerTask);
		unregisterReceiver(mReceiver);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ScreenControl.screenDiswake();
		mH.removeCallbacks(timerTask);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ScreenControl.screenWake();
		mH.removeCallbacks(timerTask);
		mH.postDelayed(timerTask, AppConstant.TIMEOUT_TIME);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("***** lockString2  onTouchEvent");
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			moveX = (int) event.getX();
			moveY = (int) event.getY();
			System.out.println("***** lockString1 ACTION_MOVE moveX = " + moveX
					+ ", moveY = " + moveY);
			for (PointInfo temp : points) {
				if (temp.isInMyPlace(moveX, moveY)
						&& temp.isSelected() == false) {
					temp.setSelected(true);
					int len = lockString.length();
					if (len != 0) {
						int preId = lockString.charAt(len - 1) - 48;
						points[preId].setNextId(temp.getId());
					}
					lockString.append(temp.getId());
					System.out.println("***** lockString2  = " + lockString);
					break;
				}
			}

			break;

		case MotionEvent.ACTION_DOWN:
			int downX = (int) event.getX();
			int downY = (int) event.getY();
			if (points == null) {
				initPoints();
			}
			System.out.println("***** lockString1 6 downX = " + downX
					+ ", downY = " + downY);
			if (points != null) {
				System.out.println("***** lockString1 1");
				for (PointInfo temp : points) {
					System.out.println("***** lockString1 1");
					if (temp.isInMyPlace(downX, downY)) {
						temp.setSelected(true);
						lockString.append(temp.getId());
						System.out
								.println("***** lockString1  = " + lockString);
						break;
					}
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			moveX = moveY = 0;
			if (password.equals(lockString.toString())) {
				lockString.delete(0, lockString.length());
				System.out.println("***** lockString3  = " + lockString);
				finish();
			} else {
				System.out.println("***** lockString4  = " + lockString);
				lockString.delete(0, lockString.length());
			}
			for (PointInfo temp : points) {
				temp.setSelected(false);
			}

			break;
		default:
			break;
		}
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	private void OvenDeamonConnect() {
		// connectToDevice(BaseProtocol.TYPE_OVEN);
		return;
	}
	
	private void ovenHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleReplyMessage");
		ovenUpdateStatus();
	}
	
	private void ovenHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "ovenHandleTimeoutMessage");
		if(b2) {
			b2 = false;
			mOvenWorkingView.setVisibility(View.INVISIBLE);
			updateWorkingViewLine();
		}
	}
	
	private void ovenHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "ovenHandleUpdateMessage");
		ovenUpdateStatus();
	}
	
	private void ovenUpdateStatus() {
		if(mDevice[BaseProtocol.TYPE_OVEN].getData().getType() == BaseProtocol.TYPE_OVEN) {
			Oven device = getOvenDevice();
			
			int mode = device.getWorkMode();
			int remainTime = device.getRemainTime();
			
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
			
			/*if (totalTime == 0)
				totalTime = 1;
			int progress = 100 - (remainTime * 100 / totalTime);*/
			
			int progress = 0;
			if(mOvenSettingTime == 0) {
				progress = 100;
			} else {
				progress = 100 - (remainTimeInSecond * 100 / (mOvenSettingTime*60));
			}
			
			if(!b2) {
				b2 = true;
				mOvenWorkingView.setVisibility(View.VISIBLE);
				updateWorkingViewLine();
			}
			
			if(mode < 1) mode = 1;
			if(mode >= mOvenModeList.length) mode = mOvenModeList.length - 1;
			
			int modeId = mOvenModeList[mode];
			
			if(device.getWorkStatus() == OvenProtocol.STATUS_INFO_STANDBY) {
				remainTime = 0; 
				mOvenSettingTime = 0;
				progress = 100;
			}
			
			mOvenWorkingView.updateView(progress, remainTime, modeId);
		}
	}

	private int protocolType2AppConstantId(int protocolType) {
		switch (protocolType) {
		case BaseProtocol.TYPE_MICROWAVE:
			return AppConstant.MICROWAVE;
		case BaseProtocol.TYPE_HOB:
			return AppConstant.HOB;
		case BaseProtocol.TYPE_HOOD:
			return AppConstant.HOOD;
		case BaseProtocol.TYPE_MINIOVEN:
			return AppConstant.MINIOVEN;
		case BaseProtocol.TYPE_OVEN:
			return AppConstant.OVEN;
		case BaseProtocol.TYPE_ROBOTCLEAN:
			return AppConstant.CLEAN;
		default:
			System.out.println("wrong device type!");
			return 0;
		}
	}
	/*
	 * private final Runnable timerConnect = new Runnable() {
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * OvenDeamonConnect(); } };
	 */
	private void registerBroadcastReceiver() {
		final IntentFilter filter = new IntentFilter();
		filter.addAction(AppConstant.ACTION_DEVICE_REPLY);
		filter.addAction(AppConstant.ACTION_DEVICE_UPDATE);
		filter.addAction(AppConstant.ACTION_DEVICE_TIMEOUT);
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		registerReceiver(mReceiver, filter);
		// mH.removeCallbacks(timerTask);
		// mH.postDelayed(timerTask, AppConstant.TIMEOUT_TIME);
	}
	
	
	private void robotcleanHandleReplyMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleReplyMessage");
		robotcleanUpdateStatus();
	}
	private void robotcleanHandleTimeoutMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleTimeoutMessage");
		if(b6) {
			b6 = false;
			mRobotcleanWorkingView.setVisibility(View.INVISIBLE);
			updateWorkingViewLine();
		}
	}
	
	private void robotcleanHandleUpdateMessage(byte[] b) {
		if (DEBUG)
			Log.d(TAG, "robotcleanHandleUpdateMessage");
		robotcleanUpdateStatus();
	}
	private void robotcleanUpdateStatus() {
		if(mDevice[BaseProtocol.TYPE_ROBOTCLEAN].getData().getType() == BaseProtocol.TYPE_ROBOTCLEAN) {
			Robotclean device = getCleanDevice();
			
			int batteryPercent = device.batteryValue();
			int remainTime = device.workTime();
			
			if(!b6) {
				b6 = true;
				mRobotcleanWorkingView.setVisibility(View.VISIBLE);
				updateWorkingViewLine();
			}
			
			
			mRobotcleanWorkingView.updateView(batteryPercent, remainTime);
		}
	}
	public void setConnectStatus(ImageView imageView, int id, int status) {

		switch (status) {
		case AppConstant.CONN_FAILED:
			imageView.setImageResource(AppConstant.connFailedIcon[id]);

			System.out.println("***** CONN_FAILED");
			break;
		case AppConstant.CONN_NORMAL:
			if (myApplication.getOperateStatus(id) == AppConstant.NOT_OPERATE) {
				imageView.setImageResource(AppConstant.connNormalIcon[id]);
			} else {
				imageView.setImageResource(AppConstant.connOKIcon[id]);
			}

			System.out.println("***** CONN_NORMAL");
			break;
		/*
		 * case AppConstant.CONN_SUCCESS:
		 * imageView.setImageResource(AppConstant.connOKIcon[id]);
		 * System.out.println("***** CONN_SUCCESS"); break;
		 */
		default:
			break;
		}
		myApplication.setConnStatus(id, status);
	}

	public void setConnectStatus(int id, int status) {
		switch (id) {
		case AppConstant.MICROWAVE:
			setConnectStatus(microwave, id, status);
			System.out.println("***** microwave");
			break;
		case AppConstant.OVEN:
			setConnectStatus(oven, id, status);
			System.out.println("***** oven");
			break;
		case AppConstant.HOOD:
			setConnectStatus(hood, id, status);
			System.out.println("***** hood");
			break;
		case AppConstant.HOB:
			setConnectStatus(hob, id, status);
			System.out.println("***** hob");
			break;
		case AppConstant.MINIOVEN:
			setConnectStatus(minioven, id, status);
			System.out.println("***** minioven");
			break;
		case AppConstant.CLEAN:
			setConnectStatus(clean, id, status);
			System.out.println("***** clean");
			break;
		default:
			break;
		}
	}

	public void setWorkStatus(ImageView imageView, ImageView imageViewBg,
			int id, int status) {
		if (myApplication.getConnStatus(id) == AppConstant.CONN_FAILED) {
			imageView.setImageResource(R.drawable.digital1);
			imageView.setVisibility(View.INVISIBLE);
			imageViewBg.setVisibility(View.INVISIBLE);
		} else {
			switch (status) {
			case AppConstant.WAIT:
				imageView.setImageResource(R.drawable.digital1);
				imageView.setVisibility(View.INVISIBLE);
				imageViewBg.setVisibility(View.INVISIBLE);
				System.out.println("***** WAIT");
				break;
			case AppConstant.RUN:
				imageView.setImageResource(R.drawable.digital1);
				imageView.setVisibility(View.VISIBLE);
				imageViewBg.setVisibility(View.VISIBLE);
				System.out.println("***** RUN");
				break;
			case AppConstant.PAUSE:
				imageView.setImageResource(R.drawable.digital1);
				imageView.setVisibility(View.VISIBLE);
				imageViewBg.setVisibility(View.VISIBLE);
				System.out.println("***** PAUSE");
				break;
			case AppConstant.FINISH:
				imageView.setImageResource(R.drawable.digital1);
				imageView.setVisibility(View.VISIBLE);
				imageViewBg.setVisibility(View.VISIBLE);
				System.out.println("***** FINISH");
				break;
			default:
				imageView.setImageResource(R.drawable.digital1);
				imageView.setVisibility(View.INVISIBLE);
				imageViewBg.setVisibility(View.INVISIBLE);
				break;
			}
		}

	}

	public void setWorkStatus(int id, int status) {
		switch (id) {
		case AppConstant.MICROWAVE:
			setWorkStatus(mwinfo, mwinfobg, id, status);
			System.out.println("***** microwave");
			break;
		case AppConstant.OVEN:
			setWorkStatus(oveninfo, oveninfobg, id, status);
			System.out.println("***** oven");
			break;
		case AppConstant.HOOD:
			setWorkStatus(hoodinfo, hoodinfobg, id, status);
			System.out.println("***** hood");
			break;
		case AppConstant.HOB:
			setWorkStatus(hobinfo, hobinfobg, id, status);
			System.out.println("***** hob");
			break;
		case AppConstant.MINIOVEN:
			setWorkStatus(minioveninfo, minioveninfobg, id, status);
			System.out.println("***** minioven");
			break;
		case AppConstant.CLEAN:
			setWorkStatus(cleaninfo, cleaninfobg, id, status);
			System.out.println("***** clean");
			break;
		default:
			return;
		}
		myApplication.setWorkStatus(id, status);
	}

	private void showInputDialog() {
		LayoutInflater li = LayoutInflater.from(MainActivity.this); // NOTE
		final View view = li.inflate(R.layout.input_layout, null);
		/*
		 * ipedit1 = (EditText) view.findViewById(R.id.ipedit1); ipedit2 =
		 * (EditText) view.findViewById(R.id.ipedit2); ipedit3 = (EditText)
		 * view.findViewById(R.id.ipedit3); ipedit4 = (EditText)
		 * view.findViewById(R.id.ipedit4);
		 */

		ipedit = (EditText) view.findViewById(R.id.ipedit);
		portedit = (EditText) view.findViewById(R.id.portedit);
		String ip1 = AppStatic.getSharedPreData(AppConstant.label[clickId][0],
				MainActivity.this);
		System.out.println("*******showInputDialog ip1 = " + ip1);
		String ip2 = AppStatic.getSharedPreData(AppConstant.label[clickId][1],
				MainActivity.this);
		String ip3 = AppStatic.getSharedPreData(AppConstant.label[clickId][2],
				MainActivity.this);
		String ip4 = AppStatic.getSharedPreData(AppConstant.label[clickId][3],
				MainActivity.this);
		String port = AppStatic.getSharedPreData(AppConstant.label[clickId][4],
				MainActivity.this);
		String ip = ip1 + "." + ip2 + "." + ip3 + "." + ip4;
		ipedit.setText(ip);
		portedit.setText(port);

		Dialog dialog = new Dialog(MainActivity.this, R.style.dialog);
		dialog.setContentView(view);// setContentView(view);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setOnDismissListener(new PopupOnDismissListener());

		dialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_BACK:
					return true;
				case KeyEvent.KEYCODE_MENU:
					return true;
				}
				return false;
			}
		});
		dialog.show();
		// dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
	}
	public void startSettingActivity(int id, int status) {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, SettingsActivity.class);
		intent.putExtra("ID", id);
		intent.putExtra("status", status);
		// ����Activity
		startActivity(intent);
	}

	private void updateWorkingViewLine() {
		if (b1 && b2) {
			mMicrowaveWorkingViewLine.setVisibility(View.VISIBLE);
		} else {
			mMicrowaveWorkingViewLine.setVisibility(View.INVISIBLE);
		}

		if (b2 && b3) {
			mOvenWorkingViewLine.setVisibility(View.VISIBLE);
		} else {
			mOvenWorkingViewLine.setVisibility(View.INVISIBLE);
		}

		if (b3 && b4) {
			mHoodWorkingViewLine.setVisibility(View.VISIBLE);
		} else {
			mHoodWorkingViewLine.setVisibility(View.INVISIBLE);
		}

		if (b4 && b5) {
			mHobWorkingViewLine.setVisibility(View.VISIBLE);
		} else {
			mHobWorkingViewLine.setVisibility(View.INVISIBLE);
		}

		if (b5 && b6) {
			mMiniovenWorkingViewLine.setVisibility(View.VISIBLE);
		} else {
			mMiniovenWorkingViewLine.setVisibility(View.INVISIBLE);
		}
	}
}
