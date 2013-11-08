package com.midea.iot.protocol;

import android.content.Context;

public class OvenProtocol extends BaseProtocol {
	
	public static final int CMD_START = 1;
	public static final int CMD_STOP = 2;
	public static final int CMD_PAUSE = 3;
	public static final int CMD_OK = 4;

	// status information
	public static final int STATUS_INFO_STANDBY = 1;
	public static final int STATUS_INFO_RUNNING = 2;
	public static final int STATUS_INFO_COMPLETE = 4;
	public static final int STATUS_INFO_PAUSE = 3;
	
	//position of oven
	private static final int POS_WORK_STATUS = 3;
	private static final int POS_WORK_MODE 	= 4;
	private static final int POS_TIME_HOUR = 5;
	private static final int POS_TIME_MINUTES = 6;
	private static final int POS_REALLY_TEMP = 7;
	private static final int POS_SETTING_TEMP = 8;
	private static final int POS_LIGHT_STATUS = 9;
	private static final int POS_CHILDREN_LOCK = 10;
	
	//work mode of oven
	public static final int MODE_CONVENTIONAL = 1;
	public static final int MODE_CONVENTIONAL_FAN = 2;
	public static final int MODE_DOUBLE_GRILLING = 3;
	public static final int MODE_DOUBLE_GRILLING_FAN = 4;
	public static final int MODE_CONVECTION = 5;
	public static final int MODE_RADIANT_GRILLING = 6;
	public static final int MODE_DEFROST = 7;
	public static final int MODE_BOTTOM_HEAT = 8;
	
	private OvenStatus mOs;

	public OvenProtocol() {
		super();
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_OVEN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_OVEN;
	}

	public OvenProtocol(byte[] b) {
		super(b);
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_OVEN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_OVEN;
	}

	public OvenProtocol(Context context) {
		super(context);
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_OVEN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_OVEN;
	}
	
	private String deviceModeToString(byte mode) {
		switch (mode) {
		case 1:
			return "Conventional";
		case 2:
			return "Conventional + fan";
		case 3:
			return "Double grilling";
		case 4:
			return "Double grilling + fan";
		case 5:
			return "Convection";
		case 6:
			return "Radiant grilling";
		case 7:
			return "Defrost";
		case 8:
			return "Bottom heat";
		default:
			return "Unknow Mode";

		}
	}
	
	private String deviceStatusToString(byte status) {
		switch (status) {
		case 1:
			return "Standby";
		case 2:
			return "Running";
		case 3:
			return "Pause";
		case 4:
			return "Completed";
		default:
			return "Unknown Status";
		}
	}

	@Override
	public void dump() {
		super.dump();

		System.out.println("Status: " + deviceStatusToString(mData[3]));
		System.out.println("Mode: " + deviceModeToString(mData[4]));
		System.out.println("time: " + (int) mData[5] + ":" + (int) mData[6]);
		System.out.println("device temp: " + (0xFF & mData[7]));
		System.out.println("set temp: " + (0xFF & mData[8]));
		System.out.println("light: " + ((mData[9] == 2) ? "on" : "off"));
		System.out.println("lock: " + (mData[10] == 2 ? "on" : "off"));
	}
	
	public int getReallyTemperature() {
		return byte2int(mData[POS_REALLY_TEMP]);
	}
	
	//return total time in second
	public int getRemainTime() {
		return byte2int(mData[POS_TIME_HOUR]) * 60 +
				byte2int(mData[POS_TIME_MINUTES]);
	}
	
	public int getSettingTemperature() {
		return byte2int(mData[POS_SETTING_TEMP]);
	}
	
	public int getSettingTime() {
		if(mOs != null) {
			return mOs.hour * 60 + mOs.minutes;
		} else {
			return 600;  //default 10min
		}
	}
	
	public int getWorkMode() {
		return byte2int(mData[POS_WORK_MODE]);
	}
	
	@Override
	public int getWorkStatus() {
		return byte2int(mData[POS_WORK_STATUS]);
	}
	
	@Override
	public void inflater(byte[] data) {
		super.inflater(data);

		//the reply message status isn't true.
		if (data[0] == (byte) 0x5A && data[1] == (byte) 0xA5) {
			for (int i = 3; i < 7; i++) {
				mSendData[i] = data[i];
			}
		}

		mSendData[7] = 0;
		mSendData[9] = 0;
		mSendData[10] = 0;
	}
	
	public boolean isChildrenLock() {
		return (byte2int(mData[POS_CHILDREN_LOCK]) == 2 ? true : false);
	}
	
	public boolean isLightOn() {
		return (byte2int(mData[POS_LIGHT_STATUS]) == 2 ? true : false);
	}
	
	public byte[] ok() {
		mSendData[POS_WORK_STATUS] = (byte)STATUS_INFO_STANDBY;
		setSendMessageData(true);
		
		makeValidData();
		return mSendData;
	}
	
	public byte[] pause(OvenStatus os) {
		mSendData[POS_WORK_STATUS] = (byte)STATUS_INFO_PAUSE;
		mOs = os;
		setSendMessageData(true);
		
		makeValidData();
		return mSendData;
	}
	
	public byte[] reStart() {
		mSendData[POS_WORK_STATUS] = (byte)STATUS_INFO_RUNNING;
		setSendMessageData(false);
		
		makeValidData();
		return mSendData;
	}

	public byte[] setChildrenLock(boolean lock) {
		mSendData[POS_CHILDREN_LOCK] = (byte) (lock ? 2 : 1);

		if(getWorkStatus() == STATUS_INFO_STANDBY) {
			setSendMessageData(true);
		} else {
			setSendMessageData(false);
		}
		
		makeValidData();
		return mSendData;
	}
	
	public byte[] setLight(boolean on) {
		mSendData[POS_LIGHT_STATUS] = (byte) (on ? 2 : 1);
		
		if(getWorkStatus() == STATUS_INFO_STANDBY) {
			setSendMessageData(true);
		} else {
			setSendMessageData(false);
		}
		
		makeValidData();
		return mSendData;
	}

	private void setSendMessageData(boolean orign) {
		if(orign) {
			if(mOs != null){
				mSendData[POS_WORK_MODE] = (byte) mOs.mode;
				mSendData[POS_TIME_HOUR] = (byte) mOs.hour;
				mSendData[POS_TIME_MINUTES] = (byte) mOs.minutes;
				mSendData[POS_SETTING_TEMP] = (byte) mOs.temperature;
			}else{
				
			}

		} else {
			mSendData[POS_WORK_MODE] = mData[POS_WORK_MODE];
			mSendData[POS_TIME_HOUR] = mData[POS_TIME_HOUR];
			mSendData[POS_TIME_MINUTES] = mData[POS_TIME_MINUTES];
			mSendData[POS_SETTING_TEMP] = mData[POS_SETTING_TEMP];
		}
	}

	public byte[] start(OvenStatus os) {
		mSendData[POS_WORK_STATUS] = STATUS_INFO_RUNNING;
		mOs = os;
		setSendMessageData(true);
		
		makeValidData();
		return mSendData;
	}

	public byte[] stop(OvenStatus os) {
		mSendData[POS_WORK_STATUS] = (byte)STATUS_INFO_STANDBY;
		mOs = os;
		setSendMessageData(true);
		
		makeValidData();
		return mSendData;
	}
}
