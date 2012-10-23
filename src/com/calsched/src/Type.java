package com.calsched.src;

public class Type {
	private int mId;
	private String mType;

	public Type (int id, String type) {
		mId = id;
		mType = type;
	}
	
	public int getId() {
		return mId;
	}
	
	public String getType() {
		return mType;
	}
}
