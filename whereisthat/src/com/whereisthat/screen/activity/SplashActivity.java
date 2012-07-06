package com.whereisthat.screen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.whereisthat.R;

public class SplashActivity extends Activity {
    
	private final int SPLASH_SCREEN_DURATION = 500;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
                
        new Handler().postDelayed(new Runnable() {			
			public void run() {				
				Intent action = new Intent(SplashActivity.this, GameMenuActivity.class);				
				SplashActivity.this.startActivity(action);				
				SplashActivity.this.finish();				
			}
		}, SPLASH_SCREEN_DURATION);
    }
 
}