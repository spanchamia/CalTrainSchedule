package com.calsched.src;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class ScheduleDbAdapter {
	
	//The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.calsched.src/databases/";
    
	// Database fields
	public static final String ID = "_id";
	public static final int ID_NULL = 0;
	
	public static final String TRAIN_TYPE_TBL = "TrainType";
	public static final String TRAIN_TYPE_TBL_TYPE = "type";
	
	public static final String STATION_TBL = "Station";
	public static final String STATION_TBL_NAME = "name";
	public static final String STATION_TBL_ZONE = "zone";
	public static final String STATION_TBL_ADDRESS = "address";
	public static final String STATION_TBL_MILES = "miles";
	
	public static final String BOUND_TBL = "Bound";
	public static final String BOUND_TBL_BOUND = "bound";
	
	public static final String DAY_TBL = "Day";
	public static final String DAY_TBL_DAY = "day";
	public static final String DAY_TBL_WEEKDAY_STR = "Weekday";
	public static final String DAY_TBL_WEEKEND_STR = "Weekend";
	public static final String DAY_TBL_SATURDAY_STR = "Saturday";
	
	public static final String TRAIN_TBL = "Train";
	public static final String TRAIN_TBL_NUMBER = "number";
	public static final String TRAIN_TBL_TYPE = "type";
	public static final String TRAIN_TBL_BOUND = "bound";
	public static final String TRAIN_TBL_DAY = "day";
	public static final String TRAIN_TBL_MISC = "misc";
	
	public static final String STOP_TBL = "Stop";
	public static final String STOP_TBL_TRAIN = "train";
	public static final String STOP_TBL_STATION = "station";
	public static final String STOP_TBL_TIME = "time";
	
	private static Context mContext;
	private static SQLiteDatabase mDb;
	private static ScheduleDbHelper mDbHelper;
	
	public ScheduleDbAdapter() { 
		mContext = null;
		mDbHelper = null;
		mDb = null;
	}
	
	public static void setContext(Context context) {
		mContext = context;
		mDbHelper = new ScheduleDbHelper(mContext);
	}
	
	public static boolean isInitialized() {
		return mContext != null;
	}
	
	public static boolean isDbOpened() {
		return mDb != null;
	}
	
	/**
	 * Check if database already exists
	 * @throws IOException
	 */
	private static boolean checkDatabase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + mDbHelper.getDatabaseName();
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		} catch(SQLiteException e) {
			System.out.println("Database does not exist.");
		}
		
		if (checkDB != null) {
			checkDB.close();
		}
		
		return checkDB != null? true : false;
	}
	
	/**
	 * Creates an empty database on the system and rewrites it with my db.
	 * @throws IOException
	 */
	public static void createDatabase() throws IOException {
		boolean dbExist = checkDatabase();
		
		/*if (dbExist) {
			System.out.println("Database exists.");
			return;
		}*/
		
		copyDatabase();
		return;
	}
	
	/**
	 * Copies my database from assets folder to system.
	 * @throws IOException 
	 */
	private static void copyDatabase() throws IOException {
		//Open your local db as the input stream
    	InputStream myInput = mContext.getAssets().open(mDbHelper.getDatabaseName());
    	
    	// Path to the just created empty db
    	String outFileName = DB_PATH + mDbHelper.getDatabaseName();
    	
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	
    	//transfer bytes from the myInput to the myOutput
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}

    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    	
    	System.out.println("Copy Database Complete!");

	}
	
	public static void open() throws SQLException {
		mDb = mDbHelper.getReadableDatabase();		
	}
	
	public static void close() {
		mDbHelper.close();
	}
	
	public static Cursor fetchAllTrainTypes() {
		return mDb.query(TRAIN_TYPE_TBL, new String[] { ID, TRAIN_TYPE_TBL_TYPE }, 
				null, null, null,
				null, null);				
	}
	
	public static Cursor fetchAllTrains() {
		return mDb.query(TRAIN_TBL, new String[] { ID, TRAIN_TBL_NUMBER }, 
				null, null, null,
				null, null);				
	}
	
	public static Cursor fetchAllStations() {
		return mDb.query(STATION_TBL, new String[] { ID, STATION_TBL_NAME, STATION_TBL_MILES }, 
				null, null, null,
				null, STATION_TBL_MILES);				
	}
	
	public static int fetchStationId(String name) {
		Cursor cursor =  mDb.query(STATION_TBL, 
								   new String [] { ID }, 
								   "name='" + name + "'", 
								   null, null, 
								   null, null);
		if (cursor.getCount() == 0) {
			return ID_NULL;
		}
		
		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex(ID));
	}
	
	public static String fetchStationName(int id) {
		Cursor cursor =  mDb.query(STATION_TBL, 
								   new String [] { STATION_TBL_NAME }, 
								   "_id=" + id, 
								   null, null, 
								   null, null);
		
		if (cursor.getCount() == 0) {
			return null;
		}
		
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(STATION_TBL_NAME));
	}
	
	public static String fetchStationAddress(int id) {
		Cursor cursor =  mDb.query(STATION_TBL, 
								   new String [] { STATION_TBL_ADDRESS }, 
								   "_id=" + id, 
								   null, null, 
								   null, null);
		
		if (cursor.getCount() == 0) {
			return null;
		}
		
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(STATION_TBL_ADDRESS));
	}
	
	public static Cursor fetchAllTypes() {
		return mDb.query(TRAIN_TYPE_TBL, new String [] { ID, TRAIN_TYPE_TBL_TYPE },
				null, null, null,
				null, null);
	}
	
	public static Cursor fetchAllBounds() {
		return mDb.query(BOUND_TBL, new String [] { ID, BOUND_TBL_BOUND },
				null, null, null,
				null, null);
	}
	
	public static Cursor fetchAllDays() {
		return mDb.query(DAY_TBL, new String [] { ID, DAY_TBL_DAY },
				null, null, null,
				null, null);
	}
	
	public static ArrayList<ResultTrain> fetchAllResultTrains() {
		ArrayList<ResultTrain> resTrains = new ArrayList<ResultTrain>();
		
		ArrayList<ResultTrain> resTrainsCurDay = fetchAllResultTrainsOnThisTimeAndDay(
													State.getTime(), 
													State.getDayOfWeek());
		if (resTrainsCurDay != null) {
			resTrains.addAll(resTrainsCurDay);
		}
		
		ArrayList<ResultTrain> resTrainsPrevDay = fetchAllResultTrainsOnThisTimeAndDay(
													State.getNextDayTime(), 
													State.getPrevDayOfWeek());
		if (resTrainsPrevDay != null) {
			resTrains.addAll(resTrainsPrevDay);
		}
		
		return resTrains;
	}
	
	public static ArrayList<ResultTrain> fetchAllResultTrainsOnThisTimeAndDay(int time, int dayOfWeek) {
		ArrayList<TrainTime> depTrains = new ArrayList<TrainTime>();
		ArrayList<TrainTime> arrTrains = new ArrayList<TrainTime>();
		
		{
			Cursor cursor1;
			
			/**
			 * Get a list of all trains arriving at departure station(at the specified time, if given).
			 */
			cursor1 = mDb.query(
					STOP_TBL,
					new String [] { ID, STOP_TBL_TRAIN, STOP_TBL_TIME },
					STOP_TBL_STATION + "=" + State.getDepartureId() + " " +
					(State.getIsDepTime() ? 
							("AND " + STOP_TBL_TIME + ">" + (time - State.getTimeTolerance()) + " AND " +
							STOP_TBL_TIME + "<" + (time + State.getTimeTolerance())) : ""),
							null, null, null, null);
					
			if (cursor1.getCount() == 0) {
				return null;
			}
			
			cursor1.moveToFirst();
			
			while(!cursor1.isAfterLast()) {				
				int train = cursor1.getInt(cursor1.getColumnIndex(STOP_TBL_TRAIN));
				int depTime = cursor1.getInt(cursor1.getColumnIndex(STOP_TBL_TIME));
				depTrains.add(new TrainTime(train, depTime));
				System.out.println("[DEP TRAIN] TRAIN: " + train + 
							                   " TIME: " + depTime);
				cursor1.moveToNext();
			}
				
			cursor1.close();
			
			/**
			 * Get a list of all trains arriving at arrival station(at the specified time, if given).
			 */
			cursor1 = mDb.query(
					STOP_TBL,
					new String [] { ID, STOP_TBL_TRAIN, STOP_TBL_TIME },
					STOP_TBL_STATION + "=" + State.getArrivalId() + " " +
					(!State.getIsDepTime() ? 
							("AND " + STOP_TBL_TIME + ">" + (time - State.getTimeTolerance()) + " AND " +
							STOP_TBL_TIME + "<" + (time + State.getTimeTolerance())) : ""),
							null, null, null, null);
					
			if (cursor1.getCount() == 0) {
				return null;
			}
			
			cursor1.moveToFirst();
			
			while(!cursor1.isAfterLast()) {
				int train = cursor1.getInt(cursor1.getColumnIndex(STOP_TBL_TRAIN));
				int arrTime = cursor1.getInt(cursor1.getColumnIndex(STOP_TBL_TIME));
				arrTrains.add(new TrainTime(train, arrTime));
				System.out.println("[ARR TRAIN] TRAIN: " + train + 
		                                       " TIME: " + arrTime);
				cursor1.moveToNext();
			}
		}
		
		/**
		 * Remove the trains that are not common between depTrains and arrTrains
		 */
		{
			Iterator<TrainTime> depItr = depTrains.iterator();		
			
			while(depItr.hasNext()) {
				TrainTime t = depItr.next();
				
				Iterator<TrainTime> arrItr = arrTrains.iterator();
				boolean trainFound = false;
				while(arrItr.hasNext()) {
					TrainTime at = arrItr.next();
					if ((t.getTrain() == at.getTrain()) &&
						(t.getTime() < at.getTime())) {
						trainFound = true;
						break;
					}
				}
				
				if (!trainFound) {
					System.out.println("REMOVING: [DEP TRAIN] TRAIN: " + t.getTrain() + 
							                   " TIME: " + t.getTime());
					depItr.remove();	
				}
			}
		}				
			
		/**
		 * Remove the trains which are not running on the day selected by user.
		 */
		{			
			String selection;
			
			if (dayOfWeek == Calendar.SATURDAY) {
				System.out.println("Day = SATURDAY");
				selection = "day='Saturday' OR day='Weekend'";
			} else if (dayOfWeek == Calendar.SUNDAY) {
				System.out.println("Day = WEEKEND");
				selection = "day='Weekend'";
			} else {
				System.out.println("Day = WEEKDAY");
				selection = "day='Weekday'";
			}
			
			Cursor cursor1 = mDb.query(
					DAY_TBL, 
					new String [] { ID }, 
					selection, null, null, null, null);
			
			ArrayList<Integer> dayIds = new ArrayList<Integer>();
			ArrayList<Integer> trainsOnTargetDays = new ArrayList<Integer>();
			
			if (cursor1.getCount() == 0) {
				return null;
			}
			
			cursor1.moveToFirst();
			
			while (!cursor1.isAfterLast()) {
				dayIds.add(cursor1.getInt(cursor1.getColumnIndex(ID)));
				cursor1.moveToNext();
			}
			
			cursor1.close();
			
			selection = "";
			Iterator<Integer> dayItr = dayIds.iterator();
			while (dayItr.hasNext()) {
				if (!selection.matches("")) {
					selection += " OR ";
				}
				
				selection += "day=" + dayItr.next() + " ";
			}
			
			cursor1 = mDb.query(
					TRAIN_TBL, 
					new String [] { ID }, 
					selection, null, null, null, null);
			
			if (cursor1.getCount() == 0) {
				return null;
			}
			
			cursor1.moveToFirst();
			
			while (!cursor1.isAfterLast()) {
				trainsOnTargetDays.add(cursor1.getInt(cursor1.getColumnIndex(ID)));
				cursor1.moveToNext();
			}			
			
			cursor1.close();
			
			Iterator<TrainTime> depItr = depTrains.iterator();		
			
			while(depItr.hasNext()) {
				TrainTime t = depItr.next();
				Iterator<Integer> trainsItr = trainsOnTargetDays.iterator();
				boolean trainFound = false;
				while(trainsItr.hasNext()) {
					Integer train = trainsItr.next();
					if (t.getTrain() == train) {
						trainFound = true;
						break;
					}
				}
				
				if (!trainFound) {
					System.out.println("REMOVING: [DEP TRAIN] TRAIN: " + t.getTrain() + 
			                   " TIME: " + t.getTime());
					depItr.remove();
				}
			}
		}
		
		ArrayList<ResultTrain> resTrains = new ArrayList<ResultTrain>();
		
		Iterator<TrainTime> depItr = depTrains.iterator();
		while (depItr.hasNext()) {
			TrainTime t = depItr.next();
			Iterator<TrainTime> arrItr = arrTrains.iterator();
			while (arrItr.hasNext()) {
				TrainTime at = arrItr.next();
				if (t.getTrain() == at.getTrain()) {
					Cursor cursor1 = mDb.query(
							TRAIN_TBL, 
							new String [] { ID, TRAIN_TBL_NUMBER, TRAIN_TBL_TYPE, 
									       TRAIN_TBL_BOUND, TRAIN_TBL_DAY }, 
			                "_id=" + t.getTrain(), null, null, null, null);
					
					if (cursor1.getCount() == 0) {
						return null;
					}
					
					int number = 0;
					int itype = 0 ,ibound = 0, iday = 0;
					
					cursor1.moveToFirst();
					while (!cursor1.isAfterLast()) {
						number = cursor1.getInt(cursor1.getColumnIndex(TRAIN_TBL_NUMBER));
						itype = cursor1.getInt(cursor1.getColumnIndex(TRAIN_TBL_TYPE));
						ibound = cursor1.getInt(cursor1.getColumnIndex(TRAIN_TBL_BOUND));
						iday = cursor1.getInt(cursor1.getColumnIndex(TRAIN_TBL_DAY));
						cursor1.moveToNext();
					}
					
					cursor1.close();
					
					resTrains.add(new ResultTrain(t.getTrain(), t.getTime(), at.getTime(), 
												  number, PrepopFields.getBound(ibound),
												  PrepopFields.getType(itype), 
												  PrepopFields.getDay(iday)));
					System.out.println("[RES TRAIN] ID: " + t.getTrain() + " TRAIN: " + t.getTrain() + 
												" TIME: " + t.getTime());					
				}
			}
		}
		
		State.setResultTrains(resTrains);
		return resTrains;	
	}
	
	public static List<TrainStop> fetchAllStops(int id, int departureId,
												int arrivalId) {
		float depStationMiles = 0;
		float arrStationMiles = 0;
		String ascDesc = "";
		List<TrainStop> stops = new ArrayList<TrainStop>();
		
		{
			/* Get miles for departure station */
			Cursor cursor1;
			
			cursor1 = mDb.query(
					STATION_TBL,
					new String [] { ID, STATION_TBL_MILES },
					ID + "=" + departureId,
					null, null, null, null);
					
			if (cursor1.getCount() == 0) {
				return null;
			}
			
			cursor1.moveToFirst();
			
			if(!cursor1.isAfterLast()) {				
				depStationMiles = cursor1.getFloat(cursor1.getColumnIndex(STATION_TBL_MILES));				
				cursor1.moveToNext();
			}
				
			cursor1.close();
		}
		{
			/* Get miles for departure station */
			Cursor cursor1;
			
			cursor1 = mDb.query(
					STATION_TBL,
					new String [] { ID, STATION_TBL_MILES },
					ID + "=" + arrivalId,
					null, null, null, null);
					
			if (cursor1.getCount() == 0) {
				return null;
			}
			
			cursor1.moveToFirst();
			
			if(!cursor1.isAfterLast()) {				
				arrStationMiles = cursor1.getFloat(cursor1.getColumnIndex(STATION_TBL_MILES));				
				cursor1.moveToNext();
			}
				
			cursor1.close();
		}
		
		if (arrStationMiles < depStationMiles) {
			ascDesc = " DESC";
		}
		
		{
			Cursor cursor1;
			
			/**
			 * Get a list of all stops that the train has.
			 */
			cursor1 = mDb.query(
					STOP_TBL,
					new String [] { ID, STOP_TBL_STATION, STOP_TBL_TIME },
					STOP_TBL_TRAIN + "=" + id,
							null, null, null, STOP_TBL_TIME);
					
			if (cursor1.getCount() == 0) {
				return null;
			}
			
			cursor1.moveToFirst();
			
			while(!cursor1.isAfterLast()) {		
				stops.add(new TrainStop(
						cursor1.getInt(cursor1.getColumnIndex(STOP_TBL_TIME)),
						cursor1.getInt(cursor1.getColumnIndex(STOP_TBL_STATION))));
				cursor1.moveToNext();
			}
				
			cursor1.close();
		}
		
		/* Delete all stops other than those between departureId and arrivalId */
		Iterator<TrainStop> stopsItr = stops.iterator();
		while(stopsItr.hasNext()) {
			TrainStop stop = stopsItr.next();
			stopsItr.remove();
			if (stop.getStationId() == departureId) {
				break;
			}
		}
		
		while(stopsItr.hasNext()) {
			TrainStop stop = stopsItr.next();
			if (stop.getStationId() == arrivalId) {
				stopsItr.remove();
				break;
			}
		}

		while(stopsItr.hasNext()) {
			TrainStop stop = stopsItr.next();
			stopsItr.remove();
		}
		
		return stops;
	}
}
