package com.midea.iot.protocol;

import android.content.Context;

public class MiniovenProtocol extends BaseProtocol {

	private static final int POS_WORK_STATUS = 3;
	private static final int POS_WORK_MODE 	= 4;
	private static final int POS_TIME_MINUTES = 5;
	private static final int POS_TIME_SECOND = 6;
	private static final int POS_REALLY_TEMP = 7;
	private static final int POS_SETTING_TEMP = 8;
	private static final int POS_LIGHT_STATUS = 9;
	

	// status information
	public static final int STATUS_INFO_STANDBY = 1;
	public static final int STATUS_INFO_RUNNING = 2;
	public static final int STATUS_INFO_COMPLETE = 4;
	public static final int STATUS_INFO_PAUSE = 3;
	
	// work Mode
	public static final int WORK_MODE_BAKE = 1;
	public static final int WORK_MODE_BROIL = 2;
	public static final int WORK_MODE_TOAST = 3;
	public static final int WORK_MODE_CONVECTION = 4;
	public static final int WORK_MODE_ROTISSERIE_GRILL = 5;
	
	private MiniovenStatus mMs;

	public MiniovenProtocol() {
		super();
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MINIOVEN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MINIOVEN;
	}

	public MiniovenProtocol(byte[] b) {
		// TODO Auto-generated constructor stub
		super(b);
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MINIOVEN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MINIOVEN;
	}

	public MiniovenProtocol(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MINIOVEN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MINIOVEN;
	}

	public MiniovenProtocol(int len) {
		super(len);
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MINIOVEN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MINIOVEN;
	}
	
	public int getReallyTemperature() {
		return byte2int(mData[POS_REALLY_TEMP]);
	}
	
	//return total time in second
	public int getRemainTime() {
		return byte2int(mData[POS_TIME_MINUTES]) * 60 +
				byte2int(mData[POS_TIME_SECOND]);
	}
	
	public int getSettingTemperature() {
		return byte2int(mData[POS_SETTING_TEMP]);
	}
	
	public int getSettingTime() {
		if(mMs != null) {
			return mMs.minutes * 60 + mMs.second;
		} else {
			return 600;
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
		/*if (data[0] == (byte) 0x5A && data[1] == (byte) 0xA5) {
			for (int i = 3; i < 9; i++) {
				mSendData[i] = data[i];
			}
		}*/

		mSendData[7] = 0;
		mSendData[9] = 0;
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
	
	public byte[] pause(MiniovenStatus ms) {
		mSendData[POS_WORK_STATUS] = (byte)STATUS_INFO_PAUSE;
		mMs = ms;
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
			mSendData[POS_WORK_MODE] = (byte) mMs.mode;
			mSendData[POS_TIME_MINUTES] = (byte) mMs.minutes;
			mSendData[POS_TIME_SECOND] = (byte) mMs.second;
			mSendData[POS_SETTING_TEMP] = (byte) mMs.temperature;
		} else {
			mSendData[POS_WORK_MODE] = mData[POS_WORK_MODE];
			mSendData[POS_TIME_MINUTES] = mData[POS_TIME_MINUTES];
			mSendData[POS_TIME_SECOND] = mData[POS_TIME_SECOND];
			mSendData[POS_SETTING_TEMP] = mData[POS_SETTING_TEMP];
		}
	}
	
	public byte[] start(MiniovenStatus ms) {
		mSendData[POS_WORK_STATUS] = (byte)STATUS_INFO_RUNNING;
		mMs = ms;
		setSendMessageData(true);
		
		makeValidData();
		return mSendData;
	}

	public byte[] stop(MiniovenStatus ms) {
		mSendData[POS_WORK_STATUS] = (byte)STATUS_INFO_STANDBY;
		mMs = ms;
		setSendMessageData(true);
		
		makeValidData();
		return mSendData;
	}
}
