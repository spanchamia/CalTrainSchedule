package com.calsched.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScheduleDb {
	private ArrayList<Station> mStations;
	private int mNextStationId;
	private ArrayList<Train> mTrains;
	
	public ScheduleDb() {
		mStations = new ArrayList<Station>();
		mNextStationId = 0;
		mTrains = new ArrayList<Train>();						
	}
	
	public List<String> getAllStationNames() {
		List<String> stationNames = new ArrayList<String>();
		
		Iterator<Station> itr = mStations.iterator();
		
		while(itr.hasNext()) {
			stationNames.add(itr.next().getStation());
		}
		return stationNames;
	}
	
	public void addStation(String station) {
		mStations.add(new Station(station, mNextStationId));
		mNextStationId += 1;
	}
	
	public ArrayList<Train> getTrains() {
		return mTrains;
	}
	
	public void addTrain(Train train) {
		mTrains.add(new Train(train));
	}
	
	public class Station {
		private String mStation;
		private int mId;
		
		public Station(String station, int id) {
			mStation = station;
			mId = id;
		}
		
		public String getStation() {
			return mStation;
		}
		
		public int getId() {
			return mId;
		}
	}
	
	public Station getStation(int id) {
		Iterator<Station> itr = mStations.iterator();
		
		while(itr.hasNext()) {
			Station station = itr.next();
			if (station.getId() == id) {
				return station;
			}
		}
		
		return null;
	}
	
	public Station getStation(String station) {
		Iterator<Station> itr = mStations.iterator();
		
		while(itr.hasNext()) {
			Station st = itr.next();
			if (st.getStation().matches(station)) {
				return st;
			}
		}
		
		return null;
	}
	
	public class Train {
		private int mNumber;
		private String mType;
		private ArrayList<TrainStop> mStops;
		
		public Train(Train train) {
			mNumber = train.getNumber();
			mType = train.getType();
			
			mStops = new ArrayList<TrainStop>();
			
			Iterator<TrainStop> itr = train.getStops().iterator();
			while(itr.hasNext()) {
				mStops.add(new TrainStop(itr.next()));
			}
		}
		
		public class TrainStop {
			String mStation;
			int mTime;
			
			public TrainStop(TrainStop stop) {
				mStation = stop.getStation();
				mTime = stop.getTime();
			}
			
			public String getStation() {
				return mStation;
			}
			
			public int getTime() {
				return mTime;
			}
			
			public void setStation(String station) {
				mStation = station;
			}
			
			public void setTime(int time) {
				mTime = time;
			}
		}		
		
		public Train() {
			mStops = new ArrayList<TrainStop>();
		}
		
		public void setNumber(int number) {
			mNumber = number;
		}
		
		public void setType(String type) {
			mType = type;
		}
		
		public String getType() {
			return mType;
		}
		
		public int getNumber() {
			return mNumber;
		}
		
		public void addStop(TrainStop stop) {
			mStops.add(new TrainStop(stop));
		}
		
		public ArrayList<TrainStop> getStops() {
			return mStops;
		}
	}
}
