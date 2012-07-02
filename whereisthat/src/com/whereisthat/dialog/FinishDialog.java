package com.whereisthat.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whereisthat.R;
import com.whereisthat.helper.SoundType;
import com.whereisthat.screen.core.SoundManager;

public class FinishDialog extends Dialog {
		
    private long score;
    private Boolean win;
    private String nextLevel;    
    private List<IFinishDialogListener> listeners;

	public FinishDialog(Context context, long score, Boolean win, String nextLevel) {
		super(context);
		this.score = score;
		this.win = win;
		this.nextLevel = nextLevel;		 
        listeners = new ArrayList<IFinishDialogListener>();        
        startSound();	
	}
	
	@Override	 
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.finish_dialog);	   
	    ((TextView) findViewById(R.id.rd_score)).setText(String.format("%s pts", score));
	    ((Button) findViewById(R.id.btnEnd)).setOnClickListener(new EndListener());
	    ((Button) findViewById(R.id.btnContinue)).setOnClickListener(new ContinueListener());
	    
	    if(win)
	    {	    
	    	setTitle(String.format("Finish level!"));	
	    	((TextView) findViewById(R.id.rd_result)).setText("Amazing!");
	    	((TextView) findViewById(R.id.rd_nextLevel)).setText(nextLevel);	    	
	    	return;
	    }
	    
	    setTitle(String.format("Ops..."));	  
	    ((TextView) findViewById(R.id.rd_result)).setText("So close! =(");
    	((TextView) findViewById(R.id.rd_nextLevel)).setText("Try again!");
    	((Button) findViewById(R.id.btnContinue)).setText("New");
    }	
	
	public void addListener(IFinishDialogListener listener){
		listeners.add(listener); 
	}
	
	private void startSound()
	{
		if(win)
        	SoundManager.start(SoundType.win);
        else
        	SoundManager.start(SoundType.lose);	
	}
	
	private void stopSound()
	{
		if(win)
        	SoundManager.stop(SoundType.win);
        else
        	SoundManager.stop(SoundType.lose);	
	}
	
    private class ContinueListener implements android.view.View.OnClickListener {
		public void onClick(View v) {				
			for (IFinishDialogListener listener : listeners) listener.continueGame();
			stopSound();
			FinishDialog.this.dismiss();	
		}
    }
    
    private class EndListener implements android.view.View.OnClickListener {
		public void onClick(View v) {	
			for (IFinishDialogListener listener : listeners) listener.endGame();
			stopSound();
			FinishDialog.this.dismiss();		
		}
    }   
}


