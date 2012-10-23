package com.calsched.src;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
	
	protected boolean active = true;
	protected int splashTime = 5000; // time to display the splash screen in ms
	
	@Override
	public void onResume() {
		super.onResume();
		System.out.println("OnResume called.");
		
		// thread for displaying the SplashScreen
	    SplashThread splashTread = new SplashThread();
	    
	    splashTread.setContext(this);
	    splashTread.start();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);
	    
	    // thread for displaying the SplashScreen
	    SplashThread splashTread = new SplashThread();
	    
	    splashTread.setContext(this);
	    splashTread.start();
	}
	
	private class SplashThread extends Thread {
        
    	private Context mContext;
    	
    	public void setContext(Context c) {
    		mContext = c;
    	}
    	
    	@Override
        public void run() {
            try {
                int waited = 0;
                while(active && (waited < splashTime)) {
                    sleep(100);
                    if(active) {
                        waited += 100;
                    }
                }
            } catch(InterruptedException e) {
                // do nothing
            } finally {
                finish();
                startActivity(new Intent(mContext, Main.class));
                stop();
            }
        }
    }
}
