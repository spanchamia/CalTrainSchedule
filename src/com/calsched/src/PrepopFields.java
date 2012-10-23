package com.calsched.src;

import java.util.ArrayList;
import java.util.Iterator;

import android.database.Cursor;

public class PrepopFields {

	private static ArrayList<Type> mTypes = null;
	private static ArrayList<Bound> mBounds = null;
	private static ArrayList<Day> mDays = null;
	private static boolean mPopulated = false;
	
	public static void addType(int id, String type) {
		mTypes.add(new Type(id, type));
	}
	
	public static void addBound(int id, String bound) {
		mBounds.add(new Bound(id, bound));
	}
	
	public static void addDay(int id, String day) {
		mDays.add(new Day(id, day));
	}
	
	public static String getType(int id) {
		Iterator<Type> typeItr = mTypes.iterator();
		
		while (typeItr.hasNext()) {
			Type t = typeItr.next();
			
			if (t.getId() == id) {
				return t.getType();
			}
		}
		
		return null;
	}
	
	public static String getBound(int id) {
		Iterator<Bound> boundItr = mBounds.iterator();
		
		while(boundItr.hasNext()) {
			Bound b = boundItr.next();
			
			if (b.getId() == id) {
				return b.getBound();
			}
		}
		
		return null;
	}
	
	public static String getDay(int id) {
		Iterator<Day> dayItr = mDays.iterator();
		
		while(dayItr.hasNext()) {
			Day d = dayItr.next();
			
			if (d.getId() == id) {
				return d.getDay();
			}
		}
		
		return null;
	}
	public static void populate() {
		
		if (mPopulated) {
			return;
		}
		
		mTypes = new ArrayList<Type>();
		mBounds = new ArrayList<Bound>();
		mDays = new ArrayList<Day>();
		
		Cursor cursor;
		
		cursor = ScheduleDbAdapter.fetchAllTypes();
		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			
			while (!cursor.isAfterLast()) {
				int id = cursor.getInt(cursor.getColumnIndex(ScheduleDbAdapter.ID));
				String type = cursor.getString(cursor.getColumnIndex(ScheduleDbAdapter.TRAIN_TYPE_TBL_TYPE));
				mTypes.add(new Type(id, type));
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		
		cursor = ScheduleDbAdapter.fetchAllBounds();
		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			
			while (!cursor.isAfterLast()) {
				int id = cursor.getInt(cursor.getColumnIndex(ScheduleDbAdapter.ID));
				String bound = cursor.getString(cursor.getColumnIndex(ScheduleDbAdapter.BOUND_TBL_BOUND));
				mBounds.add(new Bound(id, bound));
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		
		cursor = ScheduleDbAdapter.fetchAllDays();
		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			
			while (!cursor.isAfterLast()) {
				int id = cursor.getInt(cursor.getColumnIndex(ScheduleDbAdapter.ID));
				String day = cursor.getString(cursor.getColumnIndex(ScheduleDbAdapter.DAY_TBL_DAY));
				mDays.add(new Day(id, day));
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		mPopulated = true;
	}
}
