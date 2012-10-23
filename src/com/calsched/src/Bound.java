package com.calsched.src;

public class Bound {
	int mId;
	String mBound;
	
	public Bound (int id, String bound) {
		mId = id;
		mBound = bound;
	}
	
	public int getId() {
		return mId;
	}
	
	public String getBound() {
		return mBound;
	}
}