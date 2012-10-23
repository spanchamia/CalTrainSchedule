package com.calsched.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Main extends Activity {
	
	static final int TIME_DIALOG_ID = 0;
	static final int DATE_DIALOG_ID = 1;
	
	List<String> mDepArrArray;
	
	// the callback received when the user "sets" the time in the dialog
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	    new TimePickerDialog.OnTimeSetListener() {
	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            State.setHour(hourOfDay);
	            State.setMin(minute);	     
	            
	            TextView time_val = (TextView)findViewById(R.id.time_val);
	    	    time_val.setText(State.getTimeString());
	        }
	    };
	    
	private DatePickerDialog.OnDateSetListener mDateSetListener = 
		new DatePickerDialog.OnDateSetListener() {		
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				State.setYear(year);
				State.setMonth(monthOfYear);
				State.setDate(dayOfMonth);
				
				TextView date_val = (TextView)findViewById(R.id.date_val);
			    date_val.setText(State.getDateString());
			}
		};
	
	public Main() {				
		mDepArrArray = new ArrayList<String>();
		mDepArrArray.add("Departure");
		mDepArrArray.add("Arrival");
	}
	
	private class MyOnItemDepSelectedListener implements OnItemSelectedListener {

		@Override
	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
			Cursor cursor = (Cursor)parent.getItemAtPosition(pos);			
			State.setDepartureId(cursor.getInt(cursor.getColumnIndex(ScheduleDbAdapter.ID)));
	    }

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private class MyOnItemArrSelectedListener implements OnItemSelectedListener {

		@Override
	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
			Cursor cursor = (Cursor)parent.getItemAtPosition(pos);
			String station = cursor.getString(cursor.getColumnIndex(ScheduleDbAdapter.STATION_TBL_NAME));
			State.setArrivalId(cursor.getInt(cursor.getColumnIndex(ScheduleDbAdapter.ID)));
	    }

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private class MyOnItemDepArrSelectedListener implements OnItemSelectedListener {

		@Override
	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
			String station = parent.getItemAtPosition(pos).toString();
			State.setDepTime(station.matches("Departure"));
	    }

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    if (!ScheduleDbAdapter.isInitialized()) {
	    	ScheduleDbAdapter.setContext(this);
	    }
	    
	    if (!ScheduleDbAdapter.isDbOpened()) {
		    try {
	        	ScheduleDbAdapter.createDatabase();
	
			} catch (IOException ioe) {
				System.out.println("Could not create Db");
				System.out.println(ioe.getMessage());
			}
			try {
				ScheduleDbAdapter.open();
			}catch(SQLException sqle){
				System.out.println("Could not create Db");
				System.out.println(sqle.getMessage());
			}
			
			PrepopFields.populate();
	    }
		
	    
	    Spinner dep_spinner = (Spinner) findViewById(R.id.dep_spinner);
	    
	    Cursor cursor = ScheduleDbAdapter.fetchAllStations();
	    startManagingCursor(cursor);
	    
	    SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(this,
	    		android.R.layout.simple_spinner_item, cursor, 
	    		new String [] { ScheduleDbAdapter.STATION_TBL_NAME },
	    		new int [] { android.R.id.text1 });
	    	    
	    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    dep_spinner.setAdapter(adapter1);
	    dep_spinner.setOnItemSelectedListener(new MyOnItemDepSelectedListener());
	    dep_spinner.setSelection(State.getDepartureId());
	    
	    Spinner arr_spinner = (Spinner) findViewById(R.id.arr_spinner);
	    arr_spinner.setAdapter(adapter1);	    		
	    arr_spinner.setOnItemSelectedListener(new MyOnItemArrSelectedListener());
	    arr_spinner.setSelection(State.getArrivalId());
	    
	    Spinner dep_arr_time_spinner = (Spinner) findViewById(R.id.dep_arr_spinner);
	    ArrayAdapter<String> depArrAdapter = new ArrayAdapter<String>(
	    		this, android.R.layout.simple_spinner_item, mDepArrArray);
	    depArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    dep_arr_time_spinner.setAdapter(depArrAdapter);
	    dep_arr_time_spinner.setOnItemSelectedListener(new MyOnItemDepArrSelectedListener());
	    
	    TextView time_val = (TextView)findViewById(R.id.time_val);
	    time_val.setText(State.getTimeString());
	    
	    TextView date_val = (TextView)findViewById(R.id.date_val);
	    date_val.setText(State.getDateString());
	    
	    Button time_btn = (Button) findViewById(R.id.time_btn);
	    time_btn.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {	    		
	    		showDialog(TIME_DIALOG_ID);
	    	}
	    });
	    
	    Button date_btn = (Button) findViewById(R.id.date_btn);
	    date_btn.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {	    		
	    		showDialog(DATE_DIALOG_ID);
	    	}
	    });
	    
	    Button search_btn = (Button) findViewById(R.id.search_btn);
	    search_btn.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
	    		onSearchBtnClicked();
	    	}
	    });
	}	
	
	private void onSearchBtnClicked() {
		
		if (State.getDepartureId() == State.getArrivalId()) {
			Toast.makeText(this, "Please select distinct departure and arrival stations",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (State.getYear() < Calendar.getInstance().get(Calendar.YEAR)) {
			Toast.makeText(this, "Please select a future date",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		if ((State.getYear() == Calendar.getInstance().get(Calendar.YEAR)) &&
			(State.getMonth() < Calendar.getInstance().get(Calendar.MONTH))) {
			Toast.makeText(this, "Please select a future date",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		if ((State.getYear() == Calendar.getInstance().get(Calendar.YEAR)) &&
			(State.getMonth() == Calendar.getInstance().get(Calendar.MONTH)) &&
			(State.getDate() < Calendar.getInstance().get(Calendar.DAY_OF_MONTH))) {
			Toast.makeText(this, "Please select a future date",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		if ((State.getYear() == Calendar.getInstance().get(Calendar.YEAR)) &&
			(State.getMonth() == Calendar.getInstance().get(Calendar.MONTH)) &&
			(State.getDate() == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) &&
			((State.getHour() < Calendar.getInstance().get(Calendar.HOUR_OF_DAY)))) {
			Toast.makeText(this, "Please select a future date",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		if ((State.getYear() == Calendar.getInstance().get(Calendar.YEAR)) &&
			(State.getMonth() == Calendar.getInstance().get(Calendar.MONTH)) &&
			(State.getDate() == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) &&
			((State.getHour() == Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) &&
			(State.getMin() < Calendar.getInstance().get(Calendar.MINUTE)))) {
			Toast.makeText(this, "Please select a future time",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent i = new Intent(this, ListPossibleTrains.class);
		System.out.println("Activity starting");
		startActivityForResult(i, Global.SearchRequestCode);
		System.out.println("Activity started");
	}
	
	@Override
 	protected void onActivityResult(int requestCode, int resultCode, Intent data) {	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menuAbout:
	        	Intent i = new Intent(this, About.class);
	    		System.out.println("Activity starting");
	    		startActivityForResult(i, Global.AboutRequestCode);
	    		System.out.println("Activity started");
	            break;	        
	    }
	    return true;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case TIME_DIALOG_ID:
	        return new TimePickerDialog(this,
	                mTimeSetListener, State.getHour(), State.getMin(), false);
	    case DATE_DIALOG_ID:
	    	return new DatePickerDialog(this,
	    			mDateSetListener, State.getYear(), State.getMonth(), State.getDate());
	    }
	    return null;
	}
}