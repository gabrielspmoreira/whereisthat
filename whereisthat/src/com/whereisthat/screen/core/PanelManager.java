package com.whereisthat.screen.core;

import android.widget.ProgressBar;
import android.widget.TextView;

public class PanelManager {

	private TextView gameScoreView;
	private TextView levelView;
	private TextView minimumScoreToAvanceView;
	private TextView locationView;
	private ProgressBar progressBar;
	
	public PanelManager(TextView gameScoreView, 
			            TextView levelView, 
			            TextView minimumScoreToAvanceView, 
			            TextView locationView,
			            ProgressBar progressBar)	{
		this.gameScoreView = gameScoreView;
		this.levelView = levelView;
		this.minimumScoreToAvanceView = minimumScoreToAvanceView;
		this.locationView = locationView;
		this.progressBar = progressBar;
		this.progressBar.setMax(10000);
	}
	
	public void updatePanel(long score, int level, long minimumScoreToAvance)	{
		gameScoreView.setText(String.format("%s pts", score));
		//levelView.setText(String.format("Level %l", level));
		//minimumScoreToAvanceView.setText(String.format("Score to advance: %s", minimumScoreToAvance));
	}
	
	public void setLocationView(String location){
		locationView.setText(location);
		restartProgress();
	}
	
	public void restartProgress()
	{		
		progressBar.setProgress(0);	
	}
	
	public void setProgress(int progress)
	{		
		progressBar.setProgress(progress);	
	}
}
