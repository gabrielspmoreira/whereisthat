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
import com.whereisthat.helper.FontHelper;
import com.whereisthat.helper.SoundType;
import com.whereisthat.screen.core.SoundManager;

public class BetaDialog extends Dialog {
		
    private long score;
    private List<IFinishDialogListener> listeners;

	public BetaDialog(Context context, long score) {
		super(context);
		this.score = score;
		listeners = new ArrayList<IFinishDialogListener>();  
        startSound();        
	}
	
	@Override	 
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);	
	    setContentView(R.layout.beta_dialog);	  
	    setCustomFont();    	    
	    setTitle(String.format("Onde é isso?"));	
    	((TextView) findViewById(R.id.rd_result)).setText("Obrigado!");
    	((TextView) findViewById(R.id.rd_nextLevel)).setText("Você participou da versão beta de testes.");	
	    ((TextView) findViewById(R.id.rd_score)).setText(String.format("%s pts", score));
	    ((Button) findViewById(R.id.btnendbeta)).setOnClickListener(new EndListener());
    }	
	
	public void addListener(IFinishDialogListener listener){
		listeners.add(listener); 
	}
	
	private void startSound(){
		SoundManager.start(SoundType.win);
	}
	
	private void stopSound(){
        SoundManager.stop(SoundType.win);
	}
	
	private void setCustomFont(){
		FontHelper.SetFont((TextView) findViewById(R.id.rd_score));	
		FontHelper.SetFont((TextView) findViewById(R.id.rd_result));
		FontHelper.SetFont((TextView) findViewById(R.id.rd_nextLevel));			
		FontHelper.SetFont((Button) findViewById(R.id.btnendbeta));		
	}
	    
    private class EndListener implements android.view.View.OnClickListener {
		public void onClick(View v) {	
			for (IFinishDialogListener listener : listeners) listener.endGame();
			stopSound();
			BetaDialog.this.dismiss();		
		}
    }     
}


