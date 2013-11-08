package com.midea.iot.protocol;

public class MiniovenStatus {
	int mode;
	int minutes;
	int second;
	int temperature;
	
	public MiniovenStatus(int mode, int minutes, int second, int temperature) {
		super();
		this.mode = mode;
		this.minutes = minutes;
		this.second = second;
		this.temperature = temperature;
	}
}
