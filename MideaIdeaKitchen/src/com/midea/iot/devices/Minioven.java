package com.midea.iot.devices;

import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.MiniovenProtocol;
import com.midea.iot.protocol.MiniovenStatus;
import com.midea.iot.socket.BaseSocketThread;

public class Minioven extends BaseDevice {

	public Minioven() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Minioven(BaseSocketThread bst, BaseProtocol bp) {
		super(bst, bp);
		// TODO Auto-generated constructor stub
	}

	public int getReallyTemperature() {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		return moProtocol.getReallyTemperature();
	}
	
	public int getRemainTime() {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		return moProtocol.getRemainTime();
	}
	
	public int getSettingTemperature() {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		return moProtocol.getSettingTemperature();
	}
	
	public int getSettingTime() {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		return moProtocol.getSettingTime();
	}
	public int getWorkMode() {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		return moProtocol.getWorkMode();
	}
	
	@Override
	public int getWorkStatus() {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		return moProtocol.getWorkStatus();
	}
	
	public boolean isLightOn() {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		return moProtocol.isLightOn();
	}
	
	public void lightOn(boolean on) {
		MiniovenProtocol miniovenData = (MiniovenProtocol)getProtocol();
		byte[] msg = miniovenData.setLight(on);
		command(msg);
	}
	
	public void ok() {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		byte[] msg = moProtocol.ok();
		command(msg);
	}
	
	public void pause(MiniovenStatus ms) {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		byte[] msg = moProtocol.pause(ms);
		command(msg);
	}
	
	public void reStart() {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		byte[] msg = moProtocol.reStart();
		command(msg);
	}
	
	public void start(MiniovenStatus ms) {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		byte[] msg = moProtocol.start(ms);
		command(msg);
	}

	public void stop(MiniovenStatus ms) {
		MiniovenProtocol moProtocol = (MiniovenProtocol)getProtocol();
		byte[] msg = moProtocol.stop(ms);
		command(msg);
	}
}

