
package com.whereisthat.screen.core;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whereisthat.R;
import com.whereisthat.data.HistoricEvent;
import com.whereisthat.data.Location;
import com.whereisthat.data.LocationType;
import com.whereisthat.game.rules.IRulesSettings;
import com.whereisthat.helper.SoundType;

public class PanelManager {

	private Context context;
	private TextView gameScoreView;
	private TextView levelView;
	private TextView locationView;
	private TextView locationDescription;
	private RelativeLayout containerTipClose;
    private RelativeLayout containerTipOpen;
	private ProgressBar progressBar;
	private Location location;
	
	public PanelManager(Context context,
						IRulesSettings rulesSettings,
			 			TextView gameScoreView, 
			            TextView levelView, 
			            TextView minimumScoreToAvanceView, 
			            TextView locationView,
			            ProgressBar progressBar,
			            RelativeLayout containerTipClose,
			            RelativeLayout containerTipOpen){
		this.context = context;
		this.gameScoreView = gameScoreView;
		this.levelView = levelView;
		this.locationView = locationView;
		this.progressBar = progressBar;
		this.progressBar.setMax(rulesSettings.getMaximumMilisecondsToAnswer());
		this.containerTipClose = containerTipClose;
		this.containerTipOpen = containerTipOpen;
		
		levelView.setText(String.format("%s 1", context.getString(R.string.level_label)));
		minimumScoreToAvanceView.setText(String.format("%s --", context.getString(R.string.next_score_level_label)));	
		locationDescription = (TextView) containerTipOpen.findViewById(R.id.textTipDesc);
		containerTipClose.setOnClickListener(new DetailsListener());
		containerTipOpen.setOnClickListener(new CloseDetailsListener());
	}
	
	public void updatePanel(long score, int level, long minimumScoreToAvance)	{
		gameScoreView.setText(String.format("%s pts", score));
		//levelView.setText(String.format("Level %l", level));
		//minimumScoreToAvanceView.setText(String.format("Score to advance: %s", minimumScoreToAvance));
	}
	
	public void updateView(Location location, int sequence, String levelDescription){
		this.location = location;
		locationView.setText(location.toString());
		levelView.setText(String.format("%s %d - %s", context.getString(R.string.level_label), sequence, levelDescription));		
		if(location.getType() == LocationType.historicEvents) expandDescription();
		
		restartProgress();
	}
	
	public void restartProgress()
	{		
		progressBar.setProgress(0);	
	}
	
	public void setProgress(int progress) {		
		progressBar.setProgress(progress);	
	}
	
	public void expandDescription()
	{				
		SoundManager.start(SoundType.click);
		containerTipClose.setVisibility(View.GONE);
		containerTipOpen.setVisibility(View.VISIBLE);					
		containerTipClose.getLayoutParams().height = 0;
		containerTipClose.requestLayout();				
		containerTipOpen.getLayoutParams().height = 100;
		containerTipOpen.requestLayout();	
		locationDescription.setText(((HistoricEvent)location).getDescription());	
	}
	
	public void unExpandDescription()
	{
		SoundManager.start(SoundType.click);
		containerTipClose.setVisibility(View.VISIBLE);
		containerTipOpen.setVisibility(View.GONE);					
		containerTipClose.getLayoutParams().height = 50;
		containerTipClose.requestLayout();				
		containerTipOpen.getLayoutParams().height = 0;
		containerTipOpen.requestLayout();
		locationDescription.setText("");
	}
	
	private class DetailsListener implements android.view.View.OnClickListener {
		public void onClick(View v) {
			expandDescription();		
		}
    }
	
	private class CloseDetailsListener implements android.view.View.OnClickListener {
		public void onClick(View v) {
			unExpandDescription();			
		}
    }
}
