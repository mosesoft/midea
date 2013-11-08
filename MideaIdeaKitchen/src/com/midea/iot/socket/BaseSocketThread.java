package com.midea.iot.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import com.midea.iot.activities.AppConstant;
import com.midea.iot.protocol.BaseProtocol;

public class BaseSocketThread /* extends Thread */{

	private class ReadThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			boolean bIsConected = false;
			byte[] rMsg = new byte[13];
			try {
				for (; mIns != null;) {
					// wait & receive Message
					mIns.read(rMsg, 0, 13);
					mIsReading = true;
					mIsConnected = true;
					
					if(!bIsConected) {
						if(mSocketConnectedCBs != null){
							for (SocketConnectedCallBack cb : mSocketConnectedCBs) {
								cb.onSocketTimeout();
							}
						}
						bIsConected = true;
					}

					if (isValidData(rMsg)) {
						synchronized (mDeviceData) {
							// System.out.println("**������Threadd��������Received Message��������������**");
							if (!isSameMessage(rMsg, mDeviceData.getBuffer())) {
								mDeviceData.dump();
							}

							mDeviceData.inflater(rMsg);
							mDeviceData.sendMessage();
						}
					}
				}
			} catch (Exception e) {
				System.out.println("��������Threadd������Connect Failed read!��������������");
				mDeviceData.sendMessage(AppConstant.ACTION_DEVICE_TIMEOUT);
				mIsReading = false;
				mIsConnected = false;
				e.printStackTrace();
			}
		}
		
	}
	private class ReconnectThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			try {
				for (;;) {
					sleep(5000);
					if (!mIsConnected) {
						mCanTimeout = false;
						connect(mIpAddress, mPortId);
						mCanTimeout = true;
					}
				}
			} catch (Exception e) {
				mIsReading = false;
				mIsConnected = false;
			}
		}
		
	}
	public interface SocketConnectedCallBack {
		void onSocketConnected();

		void onSocketDisconnected();

		void onSocketTimeout();
	}

	private class TimeoutThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			try {
				for (;;) {
					sleep(TIMEOUTWAIT);
					if (!mIsReading && mIsConnected) {
						mIsConnected = false;
						mDeviceData.sendMessage(AppConstant.ACTION_DEVICE_TIMEOUT);
						
						if (mReconnectThread != null && mReconnectThread.isAlive()) {
							mReconnectThread.interrupt();
						}
						
						mReconnectThread = new ReconnectThread();
						mReconnectThread.start();
					}

					mIsReading = false;
				}
			} catch (Exception e) {
				mIsReading = false;
				mIsConnected = false;
			}
		}
		
	}

	private String mIpAddress = null;
	private int mPortId = 0;
	private BaseProtocol mDeviceData = null;

	SocketAddress mSocketAddress = null;
	private Socket mSocket = null;
	private InputStream mIns = null;
	
	private OutputStream mOuts = null;
	
	private boolean mIsConnecting = false;

	private boolean mIsConnected = false;

	private boolean mCanTimeout = true;

	private static final int TIMEOUTWAIT = 20000;

	ArrayList<SocketConnectedCallBack> mSocketConnectedCBs = new ArrayList<SocketConnectedCallBack>();

	private boolean mIsReading = true;

	private Thread mReconnectThread = null;
	
	private Thread mTimeoutThread = null;/*new Thread() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();

			try {
				for (;;) {
					sleep(TIMEOUTWAIT);
					if (!mIsReading && mIsConnected) {
						mIsConnected = false;
						mDeviceData.sendMessage(AppConstant.ACTION_DEVICE_TIMEOUT);
					}

					mIsReading = false;
				}
			} catch (Exception e) {
				mIsReading = false;
				mIsConnected = false;
			}
		}

	};*/
	
	private Thread mReadThread = null;/*new Thread() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();

			byte[] rMsg = new byte[13];
			try {
				for (; mIns != null;) {
					// wait & receive Message
					mIns.read(rMsg, 0, 13);
					mIsReading = true;
					mIsConnected = true;

					if (isValidData(rMsg)) {
						synchronized (mDeviceData) {
							// System.out.println("**������Threadd��������Received Message��������������**");
							if (!isSameMessage(rMsg, mDeviceData.getBuffer())) {
								mDeviceData.dump();
							}

							mDeviceData.inflater(rMsg);
							mDeviceData.sendMessage();
						}
					}
				}
			} catch (Exception e) {
				System.out.println("��������Threadd������Connect Failed read!��������������");
				mDeviceData.sendMessage(AppConstant.ACTION_DEVICE_TIMEOUT);
				mIsReading = false;
				mIsConnected = false;
				e.printStackTrace();
			}
		}
	};*/

	public BaseSocketThread() {

	}

	public BaseSocketThread(BaseProtocol device) {
		mDeviceData = device;
	}

	public BaseSocketThread(String ipAddress, int portId, BaseProtocol device) {
		mIpAddress = ipAddress;
		mPortId = portId;
		mDeviceData = device;
	}

	private int byte2int(byte b) {
		// TODO Auto-generated method stub
		return b & 0xFF;
	}

	public void connect(String ipAddress, int portId) {
		if (portId < 1024 || ipAddress == null) {
			System.out.println("��������������Wrong Ip or Port!��������������");
			return;
		}

		if (mIsConnecting) {
			return;
		}

		if (mIsConnected && isSameIpAddressPort(ipAddress, portId)) {
			return;
		}

		if (mIsConnected || mSocket != null) {
			disconnect();
		}

		setIpAddressPort(ipAddress, portId);

		System.out.println("��������Threadd������begin to connected!��������������");
		try {
			mSocket = new Socket();
			mIsConnecting = true;
			mSocket.connect(mSocketAddress, TIMEOUTWAIT);
			mIns = mSocket.getInputStream();
			mOuts = mSocket.getOutputStream();
			mIsConnecting = false;

			if (mSocket != null && mSocket.isConnected()) {
				System.out.println("��������Threadd������connected successfully !��������������");
				if (mReadThread != null && mReadThread.isAlive()) {
					mReadThread.interrupt();
				}

				mReadThread = new ReadThread();
				mReadThread.start();

				if (mTimeoutThread != null && mTimeoutThread.isAlive()) {
					mTimeoutThread.interrupt();
				}
				mTimeoutThread = new TimeoutThread();
				mTimeoutThread.start();

				/*mIsConnected = true;
				if(mSocketConnectedCBs != null){
					for (SocketConnectedCallBack cb : mSocketConnectedCBs) {
						cb.onSocketConnected();
					}
				}*/
			}

		} catch (Exception e) {
			System.out.println("��������Threadd������Connect Failed new!��������������");
			mIsConnecting = false;
			if(mSocketConnectedCBs != null && mCanTimeout){
				for (SocketConnectedCallBack cb : mSocketConnectedCBs) {
					cb.onSocketTimeout();
				}
			}
			e.printStackTrace();
		}
		
	}

	public void disconnect() {
		try {
			if (mIns != null)
				mIns.close();
			if (mOuts != null)
				mOuts.close();
			if (mSocket != null)
				mSocket.close();

			mSocket = null;
			mOuts = null;
			mIns = null;

			/*mReadThread.interrupt();
			mTimeoutThread.interrupt();*/

			mIsConnected = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();

		disconnect();
	}

	private boolean isSameIpAddressPort(String ipAddress, int portId) {
		return mIpAddress.equals(ipAddress) && (mPortId == portId);
	}
	
	private boolean isSameMessage(byte[] msg1, byte[] msg2) {
		if (msg1.length != msg2.length) {
			return false;
		}

		int len = msg1.length;
		for (int i = 0; i < len; i++) {
			if (msg1[i] != msg2[i]) {
				return false;
			}
		}

		return true;
	}
	
	private boolean isValidData(byte[] b) {
		boolean isValid = false;
		if ((b[0] == (byte) 0x5A && b[1] == (byte) 0xA5)
				|| (b[0] == (byte) 0xA5 && b[1] == (byte) 0x5A)) {
			isValid = true;
		}

		if (isValid) {
			int sum = 0;
			for (int i = 0; i < b.length - 1; i++) {
				sum += b[i];
			}

			return (byte) sum == b[b.length - 1];
		}

		return isValid;
	}
	
	void printMessage(byte[] sMsg) {
		System.out.print("dump ");
		for (int i = 0; i < sMsg.length; i++) {
			System.out.print(byte2int(sMsg[i]));
			System.out.print(" ");
		}

		System.out.println();
	}

	public void registerSocketConnectedCallBack(SocketConnectedCallBack cb) {
		if(!mSocketConnectedCBs.contains(cb)) {
			mSocketConnectedCBs.add(cb);
		}
	}
	
	
	
	private void setIpAddressPort(String ipAddress, int portId) {
		mIpAddress = ipAddress;
		mPortId = portId;
		mSocketAddress = new InetSocketAddress(mIpAddress, mPortId);
	}

	public void setTargetDevice(BaseProtocol device) {
		mDeviceData = device;
	}

	public void unRegistermSocketConnectedCallBack(SocketConnectedCallBack cb) {
		if(mSocketConnectedCBs != null) {
			mSocketConnectedCBs.remove(cb);
		}
	}

	public void writeCommand(byte[] sMsg) {
		try {
			System.out.println("����������Threadd����  writeCommand ��������������");
			// new OvenProtocol(sMsg).dump();
			printMessage(sMsg);
			if (mOuts != null)
				mOuts.write(sMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
