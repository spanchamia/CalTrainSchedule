package com.calsched.src;

import java.util.ArrayList;
import java.util.List;

public class ResultTrain {
	
	private int mId;
	private int mNumber;
	private String mBound;
	private String mType;
	private String mDay;
	private int mDepTime;
	private int mArrTime;	
	
	public ResultTrain(int id, int depTime, int arrTime, int number, String bound, String type, String day) {
		mId = id;
		mNumber = number;
		mBound = bound;
		mType = type;
		mDay = day;
		mDepTime = depTime;
		mArrTime = arrTime;	
	}
	
	public ResultTrain() { }
	
	public void setId(int id) {
		mId = id;
	}
	
	public void setNumber(int number) {
		mNumber = number;
	}
	
	public void setBound(String bound) {
		mBound = bound;
	}
	
	public void setType(String type) {
		mType = type;
	}
	
	public void setDay(String day) {
		mDay = day;
	}
	
	public void setDepTime(int depTime) {
		mDepTime = depTime;
	}
	
	public void setArrTime(int arrTime) {
		mArrTime = arrTime;
	}
	
	public int getId() {
		return mId;
	}
	
	public int getNumber() {
		return mNumber;
	}
	
	public String getBound() {
		return mBound;
	}
	
	public String getType() {
		return mType;
	}
	
	public String getDay() {
		return mDay;
	}
	
	public int getDepTime() {
		return mDepTime;
	}
	
	public int getArrTime() {
		return mArrTime;
	}
	
	public String getDepTimeString() {
		return Util.getTimeString(mDepTime);
	}
	
	public String getArrTimeString() {
		return Util.getTimeString(mArrTime);
	}
}
