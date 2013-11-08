package com.midea.iot.protocol;

import android.content.Context;

public class HoodProtocol extends BaseProtocol {
	
	public static final int WORK_STATUS_STOP = 0;
	public static final int WORK_STATUS_SMALL_FAN = 1;
	public static final int WORK_STATUS_NORMAL_FAN = 2;
	public static final int WORK_STATUS_LARGE_FAN = 3;
	
	private static final int POS_HOOD_WORK_MODE = 3;
	private static final int POS_HOOD_LIGHT = 9;

	public HoodProtocol() {
		super();
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOOD;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOOD;
	}

	public HoodProtocol(byte[] b) {
		// TODO Auto-generated constructor stub
		super(b);
		
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOOD;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOOD;
	}

	public HoodProtocol(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOOD;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOOD;
	}

	public HoodProtocol(int len) {
		super(len);
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOOD;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOOD;
	}
	
	public int getMotorSpeed() {
		return byte2int(mData[7]) * 100 + byte2int(mData[8]);
	}
	
	//return total time in second(s).
	public int getTotalRuningTime() {
		return byte2int(mData[5]) * 60 + byte2int(mData[6]);
	}
	
	@Override
	public int getWorkStatus() {
		return byte2int(mData[POS_HOOD_WORK_MODE]);
	}
	
	@Override
	public void inflater(byte[] data) {
		super.inflater(data);

		//the reply message status isn't true.
		if (data[0] == (byte) 0x5A && data[1] == (byte) 0xA5) {
			/*for (int i = 3; i < 11; i++) {
				mSendData[i] = data[i];
			}*/
			mSendData[POS_HOOD_WORK_MODE] = data[POS_HOOD_WORK_MODE];
			mSendData[POS_HOOD_LIGHT] = data[POS_HOOD_LIGHT];
		}
	}
	public boolean isLightOn() {
		return (byte2int(mData[POS_HOOD_LIGHT]) == 2 ? true : false);
	}
	public byte[] setLightOn(boolean lightOn) {
		mSendData[POS_HOOD_LIGHT] = (byte)(lightOn ? 2 : 1);
		makeValidData();
		return mSendData;
	}

	public byte[] startWork(int fan) {
		mSendData[POS_HOOD_WORK_MODE] = (byte)fan;
		mSendData[POS_HOOD_LIGHT] = (byte)0;
		makeValidData();
		return mSendData;
	}
	
	public byte[] stopWork() {
		mSendData[POS_HOOD_WORK_MODE] = (byte)0;
		makeValidData();
		return mSendData;
	}
}
