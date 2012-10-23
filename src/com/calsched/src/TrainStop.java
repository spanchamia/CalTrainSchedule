package com.calsched.src;

public class TrainStop {

	int mTime;
	int mStationId;
	
	public TrainStop(int time, int stationId) {
		mTime = time;
		mStationId = stationId;
	}
	
	public void setTime(int time) {
		mTime = time;
	}
	
	public void setStationId(int id) {
		mStationId = id;
	}
	
	public int getTime() {
		return mTime;
	}
	
	public int getStationId() {
		return mStationId;
	}
}
