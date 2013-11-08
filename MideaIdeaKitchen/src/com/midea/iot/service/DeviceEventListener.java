package com.midea.iot.service;

public interface DeviceEventListener {
	
	public void onDeviceEventReply(byte[] event);
	
	public void onDeviceEventUpdate(byte[] event);
	
	public void onDeviceTimeout(byte[] event);
}
