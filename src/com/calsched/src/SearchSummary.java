package com.calsched.src;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchSummary extends Activity {
	
	private class MyBackBtnClickedListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			setResult(RESULT_OK, null);
			finish();
		}		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.searchsummary);
	    
	    TextView summaryNumberView = (TextView)findViewById(R.id.summaryNumber);
	    summaryNumberView.setText(""+State.getSelectedTrain().getNumber());
	    
	    TextView summaryTypeView = (TextView)findViewById(R.id.summaryType);
	    summaryTypeView.setText(State.getSelectedTrain().getType());
	    
	    TextView summaryBoundView = (TextView)findViewById(R.id.summaryBound);
	    summaryBoundView.setText(State.getSelectedTrain().getBound());
	    
	    TextView summaryDayView = (TextView)findViewById(R.id.summaryDay);
	    summaryDayView.setText(State.getSelectedTrain().getDay());
	    
	    TextView summaryDepStationView = (TextView)findViewById(R.id.summaryDepStation);
	    summaryDepStationView.setText(ScheduleDbAdapter.fetchStationName(State.getDepartureId()));
	
	    ImageView summaryDepStationAddrView = (ImageView)findViewById(R.id.summaryDepStationAddr);	    
	    summaryDepStationAddrView.setOnClickListener(new DepStationOnClickListener());
	    
	    
	    TextView summaryArrStationView = (TextView)findViewById(R.id.summaryArrStation);
	    summaryArrStationView.setText(ScheduleDbAdapter.fetchStationName(State.getArrivalId()));
	    
	    ImageView summaryArrStationAddrView = (ImageView)findViewById(R.id.summaryArrStationAddr);	    
	    summaryArrStationAddrView.setOnClickListener(new ArrStationOnClickListener());
	    
	    Button backButton = (Button)findViewById(R.id.summaryBackButton);
	    backButton.setOnClickListener(new MyBackBtnClickedListener());
	    
	    List<TrainStop> stops = ScheduleDbAdapter.fetchAllStops(State.getSelectedTrain().getId(),
	    									  State.getDepartureId(),
	    									  State.getArrivalId());
	    
	}
	
	private class DepStationOnClickListener implements View.OnClickListener {
		
		public void onClick(View v) {
    		launchMap(ScheduleDbAdapter.fetchStationAddress(State.getDepartureId()));
    	}
	}
	
	private class ArrStationOnClickListener implements View.OnClickListener {
		
		public void onClick(View v) {
    		launchMap(ScheduleDbAdapter.fetchStationAddress(State.getArrivalId()));
    	}
	}
	
	private void launchMap(String address) {
		
		address.replace(' ', '+');
		address = "geo:0,0?q=" + address;
		
		Uri uri = Uri.parse(address);
		Intent launchMaps = new Intent(Intent.ACTION_VIEW, uri);  
		startActivity(launchMaps);
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
}
