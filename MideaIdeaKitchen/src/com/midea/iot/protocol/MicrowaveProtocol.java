package com.midea.iot.protocol;

import android.content.Context;

public class MicrowaveProtocol extends BaseProtocol {
	
	public static final int POS_WORK_STATUS = 3;
	public static final int POS_WORK_MODE = 4;
	public static final int POS_TIME_MINUTES = 5;
	public static final int POS_TIME_SECOND = 6;
	public static final int POS_REALLY_TEMP = 7;
	public static final int POS_SETTING_TEMP = 8;
	public static final int POS_POWER_LEVEL = 9;
	public static final int POS_WEIGHT_NUMBER = 10;
	
	// work status information
	public static final byte STATUS_INFO_STANDBY = 1;
	public static final byte STATUS_INFO_RUNNING = 2;
	public static final byte STATUS_INFO_COMPLETE = 4;
	public static final byte STATUS_INFO_PAUSE = 3;
	
	//Work Mode Define
	public static final int WORK_MODE_MICROWAVE = 1;
	public static final int WORK_MODE_GRILL = 2;
	public static final int WORK_MODE_CONVECTION = 3;
	public static final int WORK_MODE_TIME_DEFROST = 4;
	public static final int WORK_MODE_WEIGHT_DEFROST = 5;
 
	public MicrowaveProtocol() {
		super();
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MICROWAVE;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MICROWAVE;
	}

	public MicrowaveProtocol(byte[] b) {
		// TODO Auto-generated constructor stub
		super(b);
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MICROWAVE;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MICROWAVE;
	}

	public MicrowaveProtocol(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MICROWAVE;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MICROWAVE;
	}

	public MicrowaveProtocol(int len) {
		super(len);
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MICROWAVE;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_MICROWAVE;
	}
	
	public byte[] add30Seconds() {
		int totalTime = getRemainTime() + 30;
		mSendData[POS_TIME_MINUTES] = (byte)(totalTime / 60);
		mSendData[POS_TIME_SECOND] = (byte)(totalTime % 60);
		
		makeValidData();
		return mSendData;
	}
	
	private void cleanSendData() {
		for (int i = 3; i < 11; i++) {
			mSendData[i] = 0;
		}
	}
	
	public byte[] complete() {
		mSendData[POS_WORK_STATUS] = STATUS_INFO_COMPLETE;
		
		makeValidData();
		return mSendData;
	}
	
	public int getPowerLevel() {
		return byte2int(mData[POS_POWER_LEVEL]);
	}
	
	public int getReallyTemperature() {
		return byte2int(mData[POS_REALLY_TEMP]);
	}
	
	//return remain time in seconds.
	public int getRemainTime() {
		return byte2int(mData[POS_TIME_MINUTES]) * 60 + 
				byte2int(mData[POS_TIME_SECOND]);
	}
	
	public int getSettingTemperature() {
		return byte2int(mSendData[POS_SETTING_TEMP]);
	}
	
	public int getSettingTotalTime() {
		return byte2int(mSendData[POS_TIME_MINUTES]) * 60 + 
				byte2int(mSendData[POS_TIME_SECOND]);
	}
	
	public int getWeightNumber() {
		return byte2int(mData[POS_WEIGHT_NUMBER]) * 100;
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
			for (int i = 3; i < 11; i++) {
				mSendData[i] = data[i];
			}
		}

		mSendData[POS_REALLY_TEMP] = 0;
	}
	
	public byte[] pause(MicroWaveStatus ms) {
		mSendData[POS_WORK_STATUS] = STATUS_INFO_PAUSE;
		setWorkStatus(ms);
		
		makeValidData();
		return mSendData;
	}
	private void setWorkStatus(MicroWaveStatus ms) {
		mSendData[POS_WORK_MODE] = (byte)ms.workMode;
		mSendData[POS_TIME_MINUTES] = (byte)(ms.settingTime / 60);
		mSendData[POS_TIME_SECOND] = (byte)(ms.settingTime % 60);
		mSendData[POS_REALLY_TEMP] = (byte)0;
		mSendData[POS_SETTING_TEMP] = (byte)ms.temperature;
		mSendData[POS_POWER_LEVEL] = (byte)ms.powerLevel;
		mSendData[POS_WEIGHT_NUMBER] = (byte)(ms.weightNumber/100);
	}
	public byte[] startWork(MicroWaveStatus ms) {
		cleanSendData();
		mSendData[POS_WORK_STATUS] = STATUS_INFO_RUNNING;
		setWorkStatus(ms);

		makeValidData();
		return mSendData;
	}
	
	public byte[] stop(MicroWaveStatus ms) {
		mSendData[POS_WORK_STATUS] = STATUS_INFO_STANDBY;
		setWorkStatus(ms);
		
		makeValidData();
		return mSendData;
	}

}
