package com.midea.iot.devices;

import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.socket.BaseSocketThread;

public class BaseDevice {
	public BaseSocketThread mBst = null;
	public BaseProtocol mBp = null;
	
	public BaseDevice() {
		
	}
	
	public BaseDevice(BaseSocketThread bst, BaseProtocol bp) {
		mBst = bst;
		mBp = bp;
	}
	
	protected final void command(byte[] msg) {
		if(mBst != null) mBst.writeCommand(msg);
	}
	
	public final void disconnect() {
		if(mBst != null) mBst.disconnect();
	}
	
	public BaseSocketThread getConnectThread() {
		return mBst;
	}
	
	public int getDeviceType() {
		if(mBp != null) {
			return mBp.getType();
		} else {
			return 0;
		}
	}
	
	public BaseProtocol getProtocol() {
		return mBp;
	}
	
//	public final void connect() {
//		if(mBst != null) mBst.connect();
//	}
	
	public int getWorkStatus() {
		return 0;
	}
	
	public void setDeviceAttribute(BaseSocketThread bst, BaseProtocol bp) {
		mBst = bst;
		mBp = bp;
	}
	
	public void setNewConnection(BaseSocketThread newBst) {
		mBst = newBst;
	}
}
