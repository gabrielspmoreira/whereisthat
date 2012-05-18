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

public class ScoreDialog extends Dialog {
	
	private String city;
	private int elapseTime;
	private long distance;	 
    private long roundScore;
    private List<IScoreDialogListener> listeners;

	public ScoreDialog(Context context, String city, int elapseTime, long distance, long roundScore) {
		super(context);
		this.city = city;
		this.elapseTime = elapseTime;		 
        this.distance = distance;
        this.roundScore = roundScore;
        listeners = new ArrayList<IScoreDialogListener>();
	}
	
	@Override	 
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.score_dialog);
	    setTitle(String.format("You marked the location in %ss.", elapseTime));
	    
	    ((TextView) findViewById(R.id.rd_distance)).setText(String.format("%d km", distance));    
	    ((TextView) findViewById(R.id.rd_city)).setText(city);
	    ((TextView) findViewById(R.id.rd_roundScore)).setText(String.format("%s pts", roundScore));
	    
	    ((Button) findViewById(R.id.btnNext)).setOnClickListener(new NextListener());
	    ((Button) findViewById(R.id.btnStop)).setOnClickListener(new StopListener());	   
    }	
	
	public void addListener(IScoreDialogListener listener){
		listeners.add(listener); 
	}
		
    private class NextListener implements android.view.View.OnClickListener {
		public void onClick(View v) {				
			for (IScoreDialogListener listener : listeners) listener.nextRound();
			ScoreDialog.this.dismiss();	
		}
    }
    
    private class StopListener implements android.view.View.OnClickListener {
		public void onClick(View v) {	
			for (IScoreDialogListener listener : listeners) listener.stopGame();
			ScoreDialog.this.dismiss();		
		}
    }    
}


