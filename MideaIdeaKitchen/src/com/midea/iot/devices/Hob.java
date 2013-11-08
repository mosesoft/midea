package com.midea.iot.devices;

import com.midea.iot.protocol.BaseProtocol;
import com.midea.iot.protocol.HobProtocol;
import com.midea.iot.protocol.HobProtocol.HobPosition;
import com.midea.iot.protocol.HobStatus;
import com.midea.iot.socket.BaseSocketThread;

public class Hob extends BaseDevice {
	
	public Hob() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Hob(BaseSocketThread bst, BaseProtocol bp) {
		super(bst, bp);
	}

	public HobStatus getHobStatus(HobPosition hob) {
		HobProtocol hobProtocol = (HobProtocol)getProtocol();
		return hobProtocol.getHobStatus(hob);
	}
	
	public int getPowerLevel(HobPosition hob) {
		HobProtocol hobProtocol = (HobProtocol)getProtocol();
		return hobProtocol.getPowerLevel(hob);
	}
	
	public int getRemainTime(HobPosition hob) {
		HobProtocol hobProtocol = (HobProtocol)getProtocol();
		return hobProtocol.getLeftTime(hob);
	}
	
	@Override
	public int getWorkStatus() {
		HobProtocol hobProtocol = (HobProtocol)getProtocol();
		return hobProtocol.getMasterSwitchStatus() ? 1 : 0;
	}
	
	public int getWorkStatus(HobPosition hob) {
		HobProtocol hobProtocol = (HobProtocol)getProtocol();
		return hobProtocol.getWorkStatus(hob);
	}
	
	public boolean isChildrenLock() {
		HobProtocol hobProtocol = (HobProtocol)getProtocol();
		return hobProtocol.isChildrenLock();
	}
	public void onOffMasterSwitch(boolean on) {
		HobProtocol hobProtocol = (HobProtocol)getProtocol();
		byte[] msg = hobProtocol.makeOnOffMessage(on);
		command(msg);
	}
	public void setChildrenLock(boolean on) {
		HobProtocol hobProtocol = (HobProtocol)getProtocol();
		byte[] msg = hobProtocol.setChildrenLock(on);
		command(msg);
	}
	
	public void startPause(HobStatus hobStatus, HobPosition hob) {
		HobProtocol hobProtocol = (HobProtocol)getProtocol();
		byte[] msg = hobProtocol.makeStartPauseMessage(hobStatus, hob);
		command(msg);
	}
}
