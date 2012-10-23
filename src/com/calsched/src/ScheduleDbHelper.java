package com.calsched.src;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class ScheduleDbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "calsched.db";

	private static final int DATABASE_VERSION = 1;
	
	public ScheduleDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	public String getDatabaseName() {
		return DATABASE_NAME;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
