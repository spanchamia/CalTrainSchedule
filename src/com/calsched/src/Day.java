package com.calsched.src;

public class Day {
	int mId;
	String mDay;
	
	public Day (int id, String day) {
		mId = id;
		mDay = day;
	}
	
	public int getId() {
		return mId;
	}
	
	public String getDay() {
		return mDay;
	}
}