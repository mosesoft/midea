package com.midea.iot.devices;

import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.HoodProtocol;
import com.midea.iot.socket.BaseSocketThread;

public class Hood extends BaseDevice {

	public Hood() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Hood(BaseSocketThread bst, BaseProtocol bp) {
		super(bst, bp);
		// TODO Auto-generated constructor stub
	}

	public int getMotorSpeed() {
		HoodProtocol hoodProtocol = (HoodProtocol)getProtocol();
		return hoodProtocol.getMotorSpeed();
	}
	
	public int getRunningTime() {
		HoodProtocol hoodProtocol = (HoodProtocol)getProtocol();
		return hoodProtocol.getTotalRuningTime();
	}
	
	@Override
	public int getWorkStatus() {
		HoodProtocol hoodProtocol = (HoodProtocol)getProtocol();
		return hoodProtocol.getWorkStatus();
	}
	
	public boolean isLightOn() {
		HoodProtocol hoodProtocol = (HoodProtocol)getProtocol();
		return hoodProtocol.isLightOn();
	}

	public void setLightOn(boolean lightOn) {
		HoodProtocol hoodProtocol = (HoodProtocol)getProtocol();
		byte[] msg = hoodProtocol.setLightOn(lightOn);
		command(msg);
	}
	
	public void startWork(int fan) {
		HoodProtocol hoodProtocol = (HoodProtocol)getProtocol();
		byte[] msg = hoodProtocol.startWork(fan);
		command(msg);
	}
	
	public void stopWork() {
		HoodProtocol hoodProtocol = (HoodProtocol)getProtocol();
		byte[] msg = hoodProtocol.stopWork();
		command(msg);
	}
}
