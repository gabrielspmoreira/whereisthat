package com.whereisthat;

import java.util.List;
import java.util.TimerTask;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;

public class TimingTask extends TimerTask {
		
	private ProgressBar progressBar;
	
	public TimingTask(ProgressBar progress){
		this.progressBar = progress;
	}
	
	public void run() {
		
		progressBar.setProgress(progressBar.getProgress()+1);
	}
		
}
