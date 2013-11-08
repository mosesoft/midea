package com.midea.iot.service;

import com.midea.iot.devices.BaseDevice;
import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.socket.BaseSocketThread;
import com.midea.iot.socket.BaseSocketThread.SocketConnectedCallBack;

public class DeviceManager {
	private BaseProtocol mProtocol = null;
	private BaseSocketThread mConnection = null;
	private BaseDevice	mDevice = null;
	private int mDeviceType = 0;
	private boolean mIsConnected  = false;
	
	private SocketConnectedCallBack mConnectionCB = new SocketConnectedCallBack() {

		@Override
		public void onSocketConnected() {
			// TODO Auto-generated method stub
			mIsConnected = true;
		}

		@Override
		public void onSocketDisconnected() {
			// TODO Auto-generated method stub
			mIsConnected = false;
		}

		@Override
		public void onSocketTimeout() {
			// TODO Auto-generated method stub
			mIsConnected = false;
		}
	};
	
	public DeviceManager(BaseProtocol protocol, BaseSocketThread thread, BaseDevice	device) {
		super();
		mProtocol = protocol;
		mConnection = thread;
		mDevice = device;
		
		if(mProtocol != null && mConnection != null && mDevice != null) {
			mConnection.setTargetDevice(mProtocol);
			mDevice.setDeviceAttribute(mConnection, mProtocol);
			
			mConnection.registerSocketConnectedCallBack(mConnectionCB);
		}
	}
	
	public void connectToDevice(String ipAddress, int portId) {
		mConnection.connect(ipAddress, portId);
//		mConnection.start();
	}
	
	public BaseSocketThread getConnection() {
		return mConnection;
	}

	public BaseProtocol getData() {
		return mProtocol;
	}
	
	public BaseDevice getDevice() {
		return mDevice;
	}
	
	public int getDeviceType() {
		return mDeviceType;
	}
	
	public boolean isConnected() {
		return mIsConnected;
	}
	
	public void registerDeviceEventListener(DeviceEventListener l) {
		if(mProtocol != null) mProtocol.addListener(l);
	}
	
	public void reset() {
		if(mConnection != null) { mConnection.disconnect(); }
		mConnection = new BaseSocketThread(mProtocol);
		mDevice.setNewConnection(mConnection);
	}
	
	/*public void setIpAddressPort(String ipAddress, int portId) {
		mConnection.setIpAddressPort(ipAddress, portId);
	}*/
	
	public void setDeviceType(int type) {
		mDeviceType = type;
	}
	
	public void unRegisterDeviceEventListener(DeviceEventListener l) {
		if(mProtocol != null) mProtocol.removeListener(l);
	}
}
