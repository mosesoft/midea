package com.midea.iot.protocol;

import android.content.Context;

public class HobProtocol extends BaseProtocol {

	public enum HobPosition {
		HP_BOTTOM_LEFT,				//����
		HP_LEFT_CORNER,				//����
		HP_RIGHT_CORNER,			//����
		HP_BOTTOM_RIGHT				//����
	}
	public final int HP_BOTTOM_LEFT = 0;
	public final int HP_LEFT_CORNER = 1;
	public final int HP_RIGHT_CORNER = 2;
	public final int HP_BOTTOM_RIGHT = 3;
	public static final int HOB_WORK_STATUS_RUNNING = 1;
	public static final int HOB_WORK_STATUS_PAUSE = 0;

	public HobProtocol() {
		super();
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOB;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOB;
	}

	public HobProtocol(byte[] b) {
		// TODO Auto-generated constructor stub
		super(b);
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOB;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOB;
	}

	public HobProtocol(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOB;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOB;
	}

	public HobProtocol(int len) {
		super(len);
		// TODO Auto-generated constructor stub
		mData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOB;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOB;
	}
	
	private void clearSendBuff() {
		for(int i = 3; i < mSendData.length; i ++) {
			mSendData[i] = 0; 
		}
		
		mSendData[0] = (byte)0xA5;
		mSendData[1] = (byte)0x5A;
		mSendData[BaseProtocol.PROTOCOL_TYPE] = BaseProtocol.TYPE_HOB;
	}

	
	private int getBitValue(byte b, int pos) {
		if(pos > 7) return 0;
		
		return ((b >> pos) & 0x1);
	}
	
	//pos: 1: the large 4bit, 2: the little 4bit;
	private int getHalfByteValue(byte b, int pos) {
		if(pos == 1) {
			return ((b >> 4) & 0xF);
		} else {
			return (b & 0xF);
		}
	}
	
	public HobStatus getHobStatus(HobPosition hob) {
		HobStatus hobStatus = new HobStatus();
		
		hobStatus.status = getWorkStatus(hob);
		hobStatus.powerLevel = getPowerLevel(hob);
		hobStatus.timeLeft = getLeftTime(hob) * 60;
		
		return hobStatus;
	}
	
	public int getLeftTime(HobPosition hob) {
		byte b = 0;
		
		switch(hob) {
		case HP_BOTTOM_LEFT:
			b = mData[6];
			break;
			
		case HP_LEFT_CORNER:
			b = mData[7];
			break;
			
		case HP_RIGHT_CORNER:
			b = mData[8];
			break;
			
		case HP_BOTTOM_RIGHT:
			b = mData[9];
			break;
			
		default:
			break;
		}
		
		return (b & 0xFF);
	}
	
	public boolean getMasterSwitchStatus() {
		return getBitValue(mData[3], 5) == 1;
	}
		
	public int getPowerLevel(HobPosition hob) {
		int pos = 1;
		byte b = 0;
		
		switch(hob) {
		case HP_BOTTOM_LEFT:
			pos = 1;
			b = mData[4];
			break;
			
		case HP_LEFT_CORNER:
			pos = 2;
			b = mData[4];
			break;
			
		case HP_RIGHT_CORNER:
			pos = 2;
			b = mData[5];
			break;
			
		case HP_BOTTOM_RIGHT:
			pos = 1;
			b = mData[5];
			break;
			
		default:
			break;
		}
		
		return getHalfByteValue(b, pos);
	}
	
	public int getWorkStatus(HobPosition hob) {
		byte b = mData[3];
		int pos = 1;
		
		switch(hob) {
		case HP_BOTTOM_LEFT:
			pos = 4;
			break;
			
		case HP_LEFT_CORNER:
			pos = 3;
			break;
			
		case HP_RIGHT_CORNER:
			pos = 2;
			break;
			
		case HP_BOTTOM_RIGHT:
			pos = 1;
			break;
			
		default:
			pos = 10;
			break;
		}
		
		return getBitValue(b, pos);
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
	}
	
	public boolean isChildrenLock() {
		return (mData[3] & 0x01) == 1;
	}
	
	public byte[] makeOnOffMessage(boolean on) {
		clearSendBuff();
		if(on) {
			setMasterSwitchOn();
		} else {
			setMasterSwitchOff();
		}
		if(isChildrenLock()) {
			setChildrenLock(true);
		}

		makeValidData();
		
		return mSendData;

	}
	
	public byte[] makeStartPauseMessage(HobStatus hobStatus, HobPosition hob) {
		setHobStatus(hobStatus, hob);
		if(isChildrenLock()) {
			setChildrenLock(true);
		}
		makeValidData();
		return mSendData;
	}
	
	private byte setBitValue(byte b, int value, int pos) {
		if(value == 1) {
			return (byte) (b | (byte)(1 << pos));
		} else {
			//value == 0;
			return (byte) (b & (byte)(0xFFFFFFFE << pos | 0xFFFFFFFE >>> (32 - pos)));
		}
	}
	
	public byte[] setChildrenLock(boolean lock) {
		//byte[] b = super.cloneData();
		mSendData[3] = (byte)(lock ? (mSendData[3] | 0x1) : (mSendData[3] & 0xFE));
		
		makeValidData();
		return mSendData;
	}
	//pos: 1: the large 4bit, 2: the little 4bit;
	private byte setHalfByteValue(byte b, int value, int pos) {
		if(pos == 1) {
			return (byte)((b & 0x0F) | (value << 4));
		} else {
			return (byte) ((b & 0xF0) | value);
		}
	}
	
	public void setHobStatus(HobStatus hobStatus, HobPosition hob) {
		setWorkStatus(hob, hobStatus.status);
		setPowerLevel(hob, hobStatus.powerLevel);
		setLeftTime(hob, hobStatus.timeLeft);
	}
	
	private void setLeftTime(HobPosition hob, int leftTime) {
		if(leftTime > 99) leftTime = 99;
		
		switch(hob) {
		case HP_BOTTOM_LEFT:
			mSendData[6] = (byte)(leftTime & 0xFF);
			break;
			
		case HP_LEFT_CORNER:
			mSendData[7] = (byte)(leftTime & 0xFF);
			break;
			
		case HP_RIGHT_CORNER:
			mSendData[8] = (byte)(leftTime & 0xFF);
			break;
			
		case HP_BOTTOM_RIGHT:
			mSendData[9] = (byte)(leftTime & 0xFF);
			break;
			
		default:
			break;
		}
	}
	
	private void setMasterSwitchOff() {
		mSendData[3] = setBitValue(mSendData[3], 0, 5); //set the master switch on.
	}
	
	private void setMasterSwitchOn() {
		mSendData[3] = setBitValue(mSendData[3], 1, 5); //set the master switch on.
	}
	
	private void setPowerLevel(HobPosition hob, int level) {
		int pos = 1;
		level = level > 9 ? 9 : level;
		
		switch(hob) {
		case HP_BOTTOM_LEFT:
			pos = 1;
			mSendData[4] = setHalfByteValue(mSendData[4], level, pos);
			break;
			
		case HP_LEFT_CORNER:
			pos = 2;
			mSendData[4] = setHalfByteValue(mSendData[4], level, pos);
			break;
			
		case HP_RIGHT_CORNER:
			pos = 2;
			mSendData[5] = setHalfByteValue(mSendData[5], level, pos);
			break;
			
		case HP_BOTTOM_RIGHT:
			pos = 1;
			mSendData[5] = setHalfByteValue(mSendData[5], level, pos);
			break;
			
		default:
			break;
		}
	}
	
	private void setWorkStatus(HobPosition hob, int status) {
		int pos = 1;
		
		switch(hob) {
		case HP_BOTTOM_LEFT:
			pos = 4;
			break;
			
		case HP_LEFT_CORNER:
			pos = 3;
			break;
			
		case HP_RIGHT_CORNER:
			pos = 2;
			break;
			
		case HP_BOTTOM_RIGHT:
			pos = 1;
			break;
			
		default:
			pos = 10;
			break;
		}
		
		status = status >= 1 ? 1 : 0;
		
		mSendData[3] = setBitValue(mSendData[3], status, pos);
		setMasterSwitchOn();
	}
}
