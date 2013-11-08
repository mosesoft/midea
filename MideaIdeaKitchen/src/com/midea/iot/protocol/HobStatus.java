package com.midea.iot.protocol;

public class HobStatus {
	public int status;   		//running is 1, pause is 0;
	public int powerLevel; 	//from 0~8;
	public int timeLeft;		//the Left time of running;
	public HobStatus() {
		super();
	}
	public HobStatus(int status, int powerLevel, int timeLeft) {
		super();
		this.status = status;
		this.powerLevel = powerLevel;
		this.timeLeft = timeLeft;
	}
}
