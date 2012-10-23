package com.calsched.src;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class About extends Activity {
	
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
	    setContentView(R.layout.about);
	    
	    Button backButton = (Button)findViewById(R.id.aboutBackButton);
	    backButton.setOnClickListener(new MyBackBtnClickedListener());
	}
}
