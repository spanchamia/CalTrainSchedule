package com.calsched.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListPossibleTrains extends ListActivity {
	
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
		setContentView(R.layout.listpossibletrains);
			
		ArrayList<ResultTrain> resTrains = ScheduleDbAdapter.fetchAllResultTrains();		
		
		if (resTrains.size() > 0) {
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Iterator<ResultTrain>resTrainItr = resTrains.iterator();
			
			while(resTrainItr.hasNext()) {
				ResultTrain resTrain = resTrainItr.next();
				Map<String, String> map = new HashMap<String, String>();
				map.put("number", Integer.toString(resTrain.getNumber()));
				map.put("depTime", resTrain.getDepTimeString());
				map.put("arrTime", resTrain.getArrTimeString());				
				list.add(map);
			}
					
			String[] from = new String[] { "depTime", "arrTime", "number" };
			int[] to = new int[] { R.id.depTime, R.id.arrTime, R.id.number };
			
			SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.possibletrain, from, to);
			
			setListAdapter(adapter);
			
			Toast.makeText(this, "Tap on any row to view more info.",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "Sorry! We could not find any results\n" +
					             "Please try a different selection.",
					Toast.LENGTH_LONG).show();
		}
		
		Button bck_button = (Button)findViewById(R.id.back_button);
		bck_button.setOnClickListener(new MyBackBtnClickedListener());
	}	
	
	@Override
	 public void onPause() {  
		super.onPause();
		setResult(RESULT_OK, null);
	}	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TextView numberView = (TextView)v.findViewById(R.id.number);
		State.setSelectedTrain(Integer.parseInt(numberView.getText().toString()));
		Intent i = new Intent(this, SearchSummary.class);
		System.out.println("Activity starting");
		startActivityForResult(i, Global.SummaryRequestCode);
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
}
