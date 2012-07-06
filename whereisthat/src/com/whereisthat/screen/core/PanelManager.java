
package com.whereisthat.screen.core;

import com.whereisthat.R;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PanelManager {

	private Context context;
	private TextView gameScoreView;
	private TextView levelView;
	private TextView minimumScoreToAvanceView;
	private TextView locationView;
	private ProgressBar progressBar;
	
	public PanelManager(Context context,
			 			TextView gameScoreView, 
			            TextView levelView, 
			            TextView minimumScoreToAvanceView, 
			            TextView locationView,
			            ProgressBar progressBar)	{
		this.context = context;
		this.gameScoreView = gameScoreView;
		this.levelView = levelView;
		this.minimumScoreToAvanceView = minimumScoreToAvanceView;
		this.locationView = locationView;
		this.progressBar = progressBar;
		this.progressBar.setMax(10000);		
	
		levelView.setText(String.format("%s 1 (cidades)", context.getString(R.string.level_label)));
		minimumScoreToAvanceView.setText(String.format("%s --", context.getString(R.string.next_score_level_label)));
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
