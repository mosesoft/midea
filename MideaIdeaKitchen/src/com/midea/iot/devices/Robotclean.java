package com.midea.iot.devices;

import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.RobotcleanProtocol;
import com.midea.iot.socket.BaseSocketThread;

public class Robotclean extends BaseDevice {

	public Robotclean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Robotclean(BaseSocketThread bst, BaseProtocol bp) {
		super(bst, bp);
		// TODO Auto-generated constructor stub
	}

	public void autoCharge() {
		RobotcleanProtocol rcProtocol = (RobotcleanProtocol)getProtocol();
		byte[] msg = rcProtocol.autoCharge();
		command(msg);
	}

	public void autoWork() {
		RobotcleanProtocol rcProtocol = (RobotcleanProtocol)getProtocol();
		byte[] msg = rcProtocol.autoWork();
		command(msg);
	}
	
	public int batteryValue() {
		RobotcleanProtocol rcProtocol = (RobotcleanProtocol)getProtocol();
		return rcProtocol.batteryValue();
	}
	
	public void charge() {
		RobotcleanProtocol rcProtocol = (RobotcleanProtocol)getProtocol();
		byte[] msg = rcProtocol.charge();
		command(msg);
	}
	
	@Override
	public int getWorkStatus() {
		RobotcleanProtocol rcProtocol = (RobotcleanProtocol)getProtocol();
		return rcProtocol.getWorkStatus();
	}
	
	public boolean isFullCharged() {
		RobotcleanProtocol rcProtocol = (RobotcleanProtocol)getProtocol();
		return rcProtocol.isFullCharged();
	}
	
	public void power(boolean on) {
		RobotcleanProtocol rcProtocol = (RobotcleanProtocol)getProtocol();
		byte[] msg = rcProtocol.power(on);
		command(msg);
	}
	
	public int workTime() {
		RobotcleanProtocol rcProtocol = (RobotcleanProtocol)getProtocol();
		return rcProtocol.workTime();
	}
}
