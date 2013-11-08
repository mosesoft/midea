package com.midea.iot.devices;

import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.OvenProtocol;
import com.midea.iot.protocol.OvenStatus;
import com.midea.iot.socket.BaseSocketThread;

public class Oven extends BaseDevice {
	public Oven() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Oven(BaseSocketThread bst, BaseProtocol bp) {
		super(bst, bp);
	}

	public void childrenLock(boolean lock) {
		OvenProtocol ovenData = (OvenProtocol)getProtocol();
		byte[] msg = ovenData.setChildrenLock(lock);
		command(msg);
	}

	public int getReallyTemperature() {
		OvenProtocol ovenData = (OvenProtocol)getProtocol();
		return ovenData.getReallyTemperature();
	}

	public int getRemainTime() {
		OvenProtocol ovenData = (OvenProtocol)getProtocol();
		return ovenData.getRemainTime();
	}
	
	public int getSettingTemperature() {
		OvenProtocol ovenData = (OvenProtocol)getProtocol();
		return ovenData.getSettingTemperature();
	}
	
	public int getSettingTime() {
		OvenProtocol ovenData = (OvenProtocol)getProtocol();
		return ovenData.getSettingTime();
	}
	
	public int getWorkMode() {
		OvenProtocol ovenData = (OvenProtocol)getProtocol();
		return ovenData.getWorkMode();
	}
	
	@Override
	public int getWorkStatus() {
		OvenProtocol ovenData = (OvenProtocol)getProtocol();
		return ovenData.getWorkStatus();
	}
	
	public boolean isChildrenLock() {
		OvenProtocol ovenData = (OvenProtocol)getProtocol();
		return ovenData.isChildrenLock();
	}
	
	public boolean isLightOn() {
		OvenProtocol ovenData = (OvenProtocol)getProtocol();
		return ovenData.isLightOn();
	}
	
	public void lightOn(boolean on) {
		OvenProtocol ovenData = (OvenProtocol)getProtocol();
		byte[] msg = ovenData.setLight(on);
		command(msg);
	}
	
	public void ok() {
		OvenProtocol ovenProtocol = (OvenProtocol)getProtocol();
		byte[] sMsg = ovenProtocol.ok();
		command(sMsg);
	}
	
	public void pause(OvenStatus os) {
		OvenProtocol ovenProtocol = (OvenProtocol)getProtocol();
		byte[] sMsg = ovenProtocol.pause(os);
		command(sMsg);
	}
	
	public void reStart() {
		OvenProtocol ovenProtocol = (OvenProtocol)getProtocol();
		byte[] sMsg = ovenProtocol.reStart();
		command(sMsg);
	}
	
	public void start(OvenStatus os) {
		OvenProtocol ovenProtocol = (OvenProtocol)getProtocol();
		byte[] sMsg = ovenProtocol.start(os);
		command(sMsg);
	}
	
	public void stop(OvenStatus os) {
		OvenProtocol ovenProtocol = (OvenProtocol)getProtocol();
		byte[] sMsg = ovenProtocol.stop(os);
		command(sMsg);
	}
}
