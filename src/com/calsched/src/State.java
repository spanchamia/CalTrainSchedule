package com.calsched.src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class State {
	private static int mDepId;
	private static int mArrId;
	private static boolean mDepTime;
	private static Calendar mCal= new GregorianCalendar();
	private static ArrayList<ResultTrain> mResultTrains;
	private static ResultTrain mSelectedTrain;
	
	public State() {
		mDepId = 0;
		mArrId = 0;
		mDepTime = true;
		mCal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		mCal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		mCal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		mCal.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		mCal.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
		mResultTrains = null;
		mSelectedTrain = null;
	}
	public static void setDepartureId(int id) {
		mDepId = id;
	}
	
	public static void setArrivalId(int id) {
		mArrId = id;
	}
	
	public static void setHour(int hour) {
		mCal.set(Calendar.HOUR_OF_DAY, hour);		
	}
	
	public static void setMin(int min) {
		mCal.set(Calendar.MINUTE, min);
	}
	
	public static void setYear(int year) {
		mCal.set(Calendar.YEAR, year);
	}
	
	public static void setMonth(int month) {
		mCal.set(Calendar.MONTH, month);
	}
	
	public static void setDate(int date) {
		mCal.set(Calendar.DAY_OF_MONTH, date);
	}
	
	public static void setDepTime(boolean depTime) {
		mDepTime = depTime;
	}
	
	public static void setResultTrains(ArrayList<ResultTrain> resTrains) {
		mResultTrains = resTrains;
	}
	
	public static void setSelectedTrain(int number) {
		
		Iterator<ResultTrain> trainItr = mResultTrains.iterator();
		
		while (trainItr.hasNext()) {
			ResultTrain t = trainItr.next();
			
			if (t.getNumber() == number) {
				mSelectedTrain = t;
				break;
			}
		}
	}
	
	public static int getDepartureId() {
		return mDepId;
	}
	
	public static int getArrivalId() {
		return mArrId;
	}
		
	public static int getHour() {
		return mCal.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getMin() {
		return mCal.get(Calendar.MINUTE);
	}
	
	public static int getYear() {
		return mCal.get(Calendar.YEAR);
	}
	
	public static int getMonth() {
		return mCal.get(Calendar.MONTH);
	}
	
	public static int getDate() {
		return mCal.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getDayOfWeek() {
		return mCal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static int getPrevDayOfWeek() {
		switch(mCal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY:
			return Calendar.SATURDAY;
		case Calendar.MONDAY:
			return Calendar.SUNDAY;
		case Calendar.TUESDAY:
			return Calendar.MONDAY;
		case Calendar.WEDNESDAY:
			return Calendar.TUESDAY;
		case Calendar.THURSDAY:
			return Calendar.WEDNESDAY;
		case Calendar.FRIDAY:
			return Calendar.THURSDAY;
		case Calendar.SATURDAY:
			return Calendar.FRIDAY;
		}
		
		/* Return Sunday by default */
		return Calendar.SUNDAY;
	}
	
	public static int getTime() {
		return getHour() * 60 + getMin(); 
	}
	
	public static int getNextDayTime() {
		return 24 * 60 + getTime();
	}
	
	public static String getDateString() {
		return (getMonth() + 1) + ":" + getDate() + ":" + getYear();
	}
	
	public static String getTimeString() {
		return Util.getTimeString(getHour() * 60 + getMin());
	}
	
	public static boolean getIsDepTime() {
		return mDepTime;
	}
	
	public static int getTimeTolerance() {
		return 30;
	}
	
	public static ArrayList<ResultTrain> getResultTrains() {
		return mResultTrains;
	}
	
	public static ResultTrain getSelectedTrain() {
		
		return mSelectedTrain;
	}
}
