package com.midea.iot.protocol;

import android.content.Context;

public class RobotcleanProtocol extends BaseProtocol {
	
	private static final int POS_WORK_STATUS = 3;
	private static final int POS_FULL_CHARGED = 4;
	private static final int POS_TIME_MINUTES = 5;
	private static final int POS_TIME_SECOND = 6;
	private static final int POS_BATTERY_VALUE = 7;
	
	
	public static final int WORK_STATUS_POWEROFF = 0;
	public static final int WORK_STATUS_CHARGE = 1;
	public static final int WORK_STATUS_AUTOWORK = 2;
	public static final int WORK_STATUS_AUTOCHARGE = 3;

	public RobotcleanProtocol() {
		super();
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_ROBOTCLEAN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_ROBOTCLEAN;
	}

	public RobotcleanProtocol(byte[] b) {
		// TODO Auto-generated constructor stub
		super(b);
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_ROBOTCLEAN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_ROBOTCLEAN;
	}

	public RobotcleanProtocol(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_ROBOTCLEAN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_ROBOTCLEAN;
	}

	public RobotcleanProtocol(int len) {
		super(len);
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_ROBOTCLEAN;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_ROBOTCLEAN;
	}
	
	public byte[] autoCharge() {
		mSendData[POS_WORK_STATUS] = (byte)WORK_STATUS_AUTOCHARGE;
		makeValidData();
		return mSendData;
	}
	
	public byte[] autoWork() {
		mSendData[POS_WORK_STATUS] = (byte)WORK_STATUS_AUTOWORK;
		makeValidData();
		return mSendData;
	}
	
	//return the % battery.
	public int batteryValue() {
		return byte2int(mData[POS_BATTERY_VALUE]);
	}

	public byte[] charge() {
		mSendData[POS_WORK_STATUS] = (byte)WORK_STATUS_CHARGE;
		makeValidData();
		return mSendData;
	}
	
	@Override
	public int getWorkStatus() {
		return byte2int(mData[POS_WORK_STATUS]);
	}
	@Override
	public void inflater(byte[] data) {
		super.inflater(data);

		//the Send Message always is zero.
		
	}
	
	public boolean isFullCharged() {
		return mData[POS_FULL_CHARGED] == 1 ? true : false;
	}
	
	public byte[] power(boolean on) {
		mSendData[POS_WORK_STATUS] = (byte)(on ? WORK_STATUS_CHARGE : WORK_STATUS_POWEROFF);
		makeValidData();
		return mSendData;
	}
	
	//return the total worktime in second.
	public int workTime() {
		return byte2int(mData[POS_TIME_MINUTES]) * 60 +
				byte2int(mData[POS_TIME_SECOND]);
	}
	
}
