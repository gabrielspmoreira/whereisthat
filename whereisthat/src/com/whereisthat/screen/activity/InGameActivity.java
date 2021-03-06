package com.whereisthat.screen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esri.android.map.MapView;
import com.whereisthat.R;
import com.whereisthat.game.rules.IRulesSettings;
import com.whereisthat.game.rules.RulesSettings;
import com.whereisthat.helper.FontHelper;
import com.whereisthat.screen.core.GameEngine;
import com.whereisthat.screen.core.PanelManager;
import com.whereisthat.screen.core.ScoreManager;

public class InGameActivity extends Activity {
	
	private GameEngine engine;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.ingame);
		setCustomFont();	
		IRulesSettings rulesSettings = new RulesSettings();
		
		PanelManager panelManager = new PanelManager(InGameActivity.this,
													 rulesSettings,
													 (TextView) findViewById(R.id.scoreLabel), 
                                                     (TextView) findViewById(R.id.levelLabel), 
                                                     (TextView) findViewById(R.id.AdvanceLabel), 
                                                     (TextView) findViewById(R.id.locationLabel),  
                                                     (ProgressBar) findViewById(R.id.progressBar),
                                                     (RelativeLayout) findViewById(R.id.tipContainerIni),
                                                     (RelativeLayout) findViewById(R.id.tipContainer));	
		
		ScoreManager scoreManager = new ScoreManager((RelativeLayout) findViewById(R.id.llResult),
													 (Button) findViewById(R.id.btnStopGame),
													 (Button) findViewById(R.id.btnNextRound));		
		
		engine = new GameEngine(InGameActivity.this,
							   getResources(),
							   (MapView) findViewById(R.id.gameMap),
							   panelManager,
							   scoreManager,
							   rulesSettings);		
		engine.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		engine.pause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		engine.resume();
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		engine.finish();
	}
	
	private void setCustomFont() {
		FontHelper.SetFont((TextView) findViewById(R.id.scoreLabel));	
		FontHelper.SetFont((TextView) findViewById(R.id.locationLabel));
		FontHelper.SetFont((TextView) findViewById(R.id.levelLabel));	
		FontHelper.SetFont((TextView) findViewById(R.id.AdvanceLabel));
		FontHelper.SetFont((TextView) findViewById(R.id.rt_total_points));	
		FontHelper.SetFont((TextView) findViewById(R.id.rt_distance));
		FontHelper.SetFont((Button) findViewById(R.id.btnStopGame));
		FontHelper.SetFont((Button) findViewById(R.id.btnNextRound));		
		FontHelper.SetFont((TextView) findViewById(R.id.textTip));
		FontHelper.SetFont((TextView) findViewById(R.id.textTipDesc));
	}
}