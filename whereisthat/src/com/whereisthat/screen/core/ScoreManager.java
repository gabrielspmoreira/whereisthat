package com.whereisthat.screen.core;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whereisthat.R;
import com.whereisthat.dialog.IScoreDialogListener;

public class ScoreManager {

	private RelativeLayout container;
	private TextView totalPoints;
	private TextView totaldistance;
	private List<IScoreDialogListener> listeners;
	
	public ScoreManager(RelativeLayout container, Button stopGame, Button nextRound){
		this.container = container;
		totalPoints = (TextView) container.findViewById(R.id.rt_total_points);
		totaldistance = (TextView) container.findViewById(R.id.rt_distance);		
		stopGame.setOnClickListener(new StopListener());
		nextRound.setOnClickListener(new NextListener());		
		listeners = new ArrayList<IScoreDialogListener>();
	}
	
	public void addListener(IScoreDialogListener listener){
		listeners.add(listener); 
	}
	
	public void show(long score, long distance){
		listeners.clear();
		totalPoints.setText(String.format("%s pts", score));
		totaldistance.setText(String.format("%d km", distance));
		container.setVisibility(View.VISIBLE);
	}
	
	private void hide()
	{
		totalPoints.setText("");
		totaldistance.setText("");
		container.setVisibility(View.GONE);
	}
	
	private class NextListener implements android.view.View.OnClickListener {
		public void onClick(View v) {				
			for (IScoreDialogListener listener : listeners) listener.nextRound();
			hide();
		}
    }
    
    private class StopListener implements android.view.View.OnClickListener {
		public void onClick(View v) {	
			for (IScoreDialogListener listener : listeners) listener.stopGame();
			hide();	
		}
    }
}
