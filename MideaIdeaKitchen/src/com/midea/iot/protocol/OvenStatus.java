package com.midea.iot.protocol;

public class OvenStatus {
	int mode;
	int hour;
	int minutes;
	int temperature;
	
	public OvenStatus(int mode, int hour, int minutes, int temperature) {
		super();
		this.mode = mode;
		this.hour = hour;
		this.minutes = minutes;
		this.temperature = temperature;
	}
}
