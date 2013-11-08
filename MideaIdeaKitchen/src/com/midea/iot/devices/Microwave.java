package com.midea.iot.devices;

import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.MicroWaveStatus;
import com.midea.iot.protocol.MicrowaveProtocol;
import com.midea.iot.socket.BaseSocketThread;

public class Microwave extends BaseDevice {

	public Microwave() {
		// TODO Auto-generated constructor stub
		super();
	}

	public Microwave(BaseSocketThread bst, BaseProtocol bp) {
		super(bst, bp);
		// TODO Auto-generated constructor stub
	}

	public void add30Second() {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		byte[] msg = mwProtocol.add30Seconds();
		command(msg);
	}
	
	public void complete() {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		byte[] msg = mwProtocol.complete();
		command(msg);
	}
	
	public int getPowerLevel() {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		return mwProtocol.getPowerLevel();
	}
	
	public int getReallyTemperature() {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		return mwProtocol.getReallyTemperature();
	}
	
	public int getRemainTime() {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		return mwProtocol.getRemainTime();
	}
	
	public int getSettingTemperature() {
		MicrowaveProtocol moProtocol = (MicrowaveProtocol)getProtocol();
		return moProtocol.getSettingTemperature();
	}
	
	public int getTotalTime() {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		return mwProtocol.getSettingTotalTime();
	}
	
	public int getWeightNumber() {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		return mwProtocol.getWeightNumber();
	}
	public int getWorkMode() {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		return mwProtocol.getWorkMode();
	}
	@Override
	public int getWorkStatus() {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		return mwProtocol.getWorkStatus();
	}
	public void pause(MicroWaveStatus ms) {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		byte[] msg = mwProtocol.pause(ms);
		command(msg);
	}
	
	public void startWork(MicroWaveStatus ms) {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		byte[] msg = mwProtocol.startWork(ms);
		command(msg);
	}
	
	public void stop(MicroWaveStatus ms) {
		MicrowaveProtocol mwProtocol = (MicrowaveProtocol)getProtocol();
		byte[] msg = mwProtocol.stop(ms);
		command(msg);
	}
}
