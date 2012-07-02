package com.whereisthat.screen.core;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whereisthat.R;
import com.whereisthat.dialog.IScoreDialogListener;
import com.whereisthat.helper.SoundType;

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
		totaldistance.setText(String.format("%d km", distance));
		container.setVisibility(View.VISIBLE);		
		countUp(totalPoints, score, 0);
	}
	
	private void hide()
	{
		totalPoints.setText("");
		totaldistance.setText("");
		container.setVisibility(View.GONE);
	}
	
	private void countUp(final TextView tv, final long total, final long count) {
		if (total == 0) { 
	     tv.setText("0 pts"); 
	     return;
	   } 			
	   if(count > total) return;
	   tv.setText(String.format("%s pts", count));
	   AlphaAnimation animation = new AlphaAnimation(1.0f, 0.9f);
	   animation.setDuration(1);
	   animation.setAnimationListener(new AnimationListener() {			
			public void onAnimationStart(Animation animation) {	}			
			public void onAnimationRepeat(Animation animation) { }			
			public void onAnimationEnd(Animation animation) {							
				int i = total > 1000 ? 100 : 10;
				countUp(tv, total, count + i);
			}
	   });
	   tv.startAnimation(animation);
	}	

	private class NextListener implements android.view.View.OnClickListener {		
		public void onClick(View v) {	
			SoundManager.start(SoundType.click);
			for (IScoreDialogListener listener : listeners) listener.nextRound();
			hide();
		}
    }
    
    private class StopListener implements android.view.View.OnClickListener {
		public void onClick(View v) {	
			SoundManager.start(SoundType.click);
			for (IScoreDialogListener listener : listeners) listener.stopGame();
			hide();	
		}
    }
}
