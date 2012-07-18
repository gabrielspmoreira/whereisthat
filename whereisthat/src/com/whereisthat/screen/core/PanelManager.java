
package com.whereisthat.screen.core;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whereisthat.R;
import com.whereisthat.data.HistoricEvent;
import com.whereisthat.data.Location;
import com.whereisthat.helper.SoundType;

public class PanelManager {

	private Context context;
	private TextView gameScoreView;
	private TextView levelView;
	private TextView minimumScoreToAvanceView;
	private TextView locationView;
	private ProgressBar progressBar;
	private Location location;
	private Boolean hasExpanded;
	private int oldHeight;
	
	public PanelManager(Context context,
			 			TextView gameScoreView, 
			            TextView levelView, 
			            TextView minimumScoreToAvanceView, 
			            TextView locationView,
			            ProgressBar progressBar){
		this.context = context;
		this.gameScoreView = gameScoreView;
		this.levelView = levelView;
		this.minimumScoreToAvanceView = minimumScoreToAvanceView;
		this.locationView = locationView;
		this.progressBar = progressBar;
		this.progressBar.setMax(10000);			
		
		hasExpanded = false;
		progressBar.setOnClickListener(new DetailsListener());
		levelView.setText(String.format("%s 1 (cidades)", context.getString(R.string.level_label)));
		minimumScoreToAvanceView.setText(String.format("%s --", context.getString(R.string.next_score_level_label)));
		oldHeight = progressBar.getLayoutParams().height;
	}
	
	public void updatePanel(long score, int level, long minimumScoreToAvance)	{
		gameScoreView.setText(String.format("%s pts", score));
		//levelView.setText(String.format("Level %l", level));
		//minimumScoreToAvanceView.setText(String.format("Score to advance: %s", minimumScoreToAvance));
	}
	
	public void updateView(Location location){
		this.location = location;
		locationView.setText(location.toString());		
		restartProgress();
	}
	
	public void restartProgress()
	{		
		progressBar.setProgress(0);	
	}
	
	public void setProgress(int progress) {
		progressBar.setProgress(progress);	
	}
	
	private void expandDescription()
	{
		switch(location.getType()) {
			case historicevents:		
				hasExpanded = true;
				SoundManager.start(SoundType.openPb);				
				//DropDownAnim anim = new DropDownAnim(progressBar, 150, true);
				//progressBar.setAnimation(anim);					
				progressBar.getLayoutParams().height = 200;
				progressBar.requestLayout();
				locationView.setText(((HistoricEvent)location).getDescription());	
				break;	
		}
	}
	
	private void unExpandDescription()
	{
		switch(location.getType()) {
			case historicevents:
				hasExpanded = false;
				SoundManager.start(SoundType.closePb);
				progressBar.getLayoutParams().height = oldHeight;
				progressBar.requestLayout();
				locationView.setText(location.toString());
				break;	
		}
	}
	
	private class DetailsListener implements android.view.View.OnClickListener {
		public void onClick(View v) {
			if(!hasExpanded) expandDescription();
			else unExpandDescription();			
		}
    }
}
