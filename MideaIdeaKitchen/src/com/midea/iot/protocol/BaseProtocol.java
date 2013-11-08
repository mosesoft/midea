package com.midea.iot.protocol;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.midea.iot.activities.AppConstant;
import com.midea.iot.service.DeviceEventListener;

/**
 * 
 * The base protocol struct: 0 1 2 3 4 5 6 7 8 9 10 11 12 |TAG|TYPE|STAT|MOD|
 * CUSTOM |CKSUM|
 * 
 */

public class BaseProtocol {

	public byte[] mData;
	public byte[] mSendData;

	private Context mContext;

	// location
	public static final int PROTOCOL_TAG1 = 0;
	public static final int PROTOCOL_TAG2 = 1;
	public static final int PROTOCOL_TYPE = 2;

	// type information
	public static final byte TYPE_MICROWAVE = 1;
	public static final byte TYPE_OVEN = 2;
	public static final byte TYPE_HOOD = 3;
	public static final byte TYPE_HOB = 4;
	public static final byte TYPE_MINIOVEN = 5;
	public static final byte TYPE_ROBOTCLEAN = 6;

	private ArrayList<DeviceEventListener> mDeviceEventListeners = new ArrayList<DeviceEventListener>();
	
	public BaseProtocol() {
		mData = new byte[13];
		mSendData = new byte[13];
		mSendData[0] = (byte)0xA5;
		mSendData[1] = (byte)0x5A;
	}

	public BaseProtocol(byte[] b) {
		mData = new byte[b.length];
		mSendData = new byte[b.length];
		
		mSendData[0] = (byte)0xA5;
		mSendData[1] = (byte)0x5A;
		
		inflater(b);		
	}

	public BaseProtocol(Context context) {
		mContext = context;
		mData = new byte[13];
		mSendData = new byte[13];
		
		mSendData[0] = (byte)0xA5;
		mSendData[1] = (byte)0x5A;
	}
	
	
	public BaseProtocol(int len) {
		mData = new byte[len];
		mSendData = new byte[len];
		mSendData[0] = (byte)0xA5;
		mSendData[1] = (byte)0x5A;
	}

	public void addListener(DeviceEventListener l) {
		if (!mDeviceEventListeners.contains(l)) {
			mDeviceEventListeners.add(l);
		}
	}

	protected final int byte2int(byte b) {
		System.out.println("******byte2int b = " + b);
		return b & 0xFF;
	}


	public boolean checkSum() {
		/*if (!((mData[PROTOCOL_TAG1] == (byte) 0x5A && mData[PROTOCOL_TAG2] == (byte) 0xA5) || (mData[PROTOCOL_TAG1] == (byte) 0xA5 && mData[PROTOCOL_TAG2] == (byte) 0x5A))) {
			return false;
		}*/
		
		if((0x5A != getTag()) && (0xA5 != getTag())) {
			return false;
		}

		int sum = 0;
		for (int i = 0; i < mData.length - 1; i++) {
			sum += mData[i];
		}

		return (byte) sum == mData[mData.length - 1];
	}
	
	protected final byte[] cloneData() {
		byte[] b = new byte[mSendData.length];
		for (int i = 0; i < mSendData.length; i++) {
			b[i] = mSendData[i];
		}

		return b;
	}

	private String dataTypeToString(byte[] b) {
		if (b[0] == (byte) 0x5A && b[1] == (byte) 0xA5) {
			return "Update data";
		} else if (b[0] == (byte) 0xA5 && b[1] == (byte) 0x5A) {
			return "Reply data";
		} else {
			return "Unknow data";
		}
	}
	
	private String deviceTypeToString(byte type) {
		switch (type) {
		case 1:
			return "microWave";
		case 2:
			return "oven";
		case 3:
			return "hood";
		case 4:
			return "hob";
		case 5:
			return "toast";
		case 6:
			return "robot clean";
		default:
			return "unknown devcie";
		}
	}

	public void dump() {
		System.out.println("Receive: " + dataTypeToString(mData));
		System.out.println("Type: " + deviceTypeToString(mData[2]));
	}
	
	public byte[] getBuffer() {
		return mData;
	}
	
	public byte[] getSendMessage() {
		return mSendData;
	}

	public int getTag() {
		return byte2int(mData[PROTOCOL_TAG1]);
		//return (mData[PROTOCOL_TAG1] << 8 | mData[PROTOCOL_TAG2]) & 0xFFFF;
	}
	
	public int getType() {
		return byte2int(mData[PROTOCOL_TYPE]);
	}
	
	public int getWorkStatus() {
		return byte2int(mData[3]);
	}

	public void inflater(byte[] data) {
		for (int i = 0; i < data.length; i++) {
			mData[i] = data[i];
		}
	}

	protected final void makeValidData() {
		int sum = 0;
		for (int i = 0; i < mSendData.length - 1; i++) {
			sum += mSendData[i];
		}

		mSendData[mSendData.length - 1] = (byte) sum;
	}

	public void removeListener(DeviceEventListener l) {
		mDeviceEventListeners.remove(l);
	}

	private void replyDeviceEventToListener(byte[] msg) {
		if(mDeviceEventListeners != null){
			for (DeviceEventListener l : mDeviceEventListeners) {
				l.onDeviceEventReply(msg);
			}
		}
	}
	
	public void sendMessage() {
		Intent i = null;
		System.out.println("��������������sendMessage begin��������������");
		if (0x5A == getTag()) {
			i = new Intent(AppConstant.ACTION_DEVICE_UPDATE);
			updateDeviceEventToListener(mData);
		} else if (0xA5 == getTag()) {
			i = new Intent(AppConstant.ACTION_DEVICE_REPLY);
			replyDeviceEventToListener(mData);
		} else {
			return;
		}
		i.putExtra("receive_data", mData);
		mContext.sendBroadcast(i);
	}

	public void sendMessage(String action) {
		if (action != null) {
			Intent i = new Intent(action);
			i.putExtra("receive_data", mData);
			mContext.sendBroadcast(i);
		}
		
		timeOutDeviceEventToListener(mData);
	}
	
	private void timeOutDeviceEventToListener(byte[] msg) {
		if(mDeviceEventListeners != null){
			for (DeviceEventListener l : mDeviceEventListeners) {
				l.onDeviceTimeout(msg);
			}
		}
	}

	private void updateDeviceEventToListener(byte[] msg) {
		if(mDeviceEventListeners != null){
			for (DeviceEventListener l : mDeviceEventListeners) {
				l.onDeviceEventUpdate(msg);
			}
		}
	}
}
