package com.midea.iot.protocol;

public class MicroWaveStatus {
	int workMode;				//The work mode define at the top.
	int settingTime;			// seconds
	int temperature;			//
	int powerLevel;				// 1~5 for microwave work mode, 0 for others
	int weightNumber;			// 100~2000 g
	public MicroWaveStatus(int workMode, int settingTime, int temperature,
			int powerLevel, int weightNumber) {
		super();
		this.workMode = workMode;
		this.settingTime = settingTime;
		this.temperature = temperature;
		this.powerLevel = powerLevel;
		this.weightNumber = weightNumber;
	}
	
	
}
