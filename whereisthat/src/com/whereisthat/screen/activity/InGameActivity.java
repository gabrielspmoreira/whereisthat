package com.whereisthat.screen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.esri.android.map.MapView;
import com.whereisthat.R;
import com.whereisthat.screen.core.GameEngine;

public class InGameActivity extends Activity {
	
	private GameEngine engine;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame);
		
		GameEngine engine = new GameEngine(InGameActivity.this,
										   getResources(),
										   (MapView) findViewById(R.id.gameMap),
										   (ProgressBar) findViewById(R.id.progressBar),
										   (TextView) findViewById(R.id.locationLabel),
										   (TextView) findViewById(R.id.scoreLabel),
										   (TextView) findViewById(R.id.levelLabel),
										   (TextView) findViewById(R.id.AdvanceLabel));		
		engine.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		engine.pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		engine.finish();
	}
}