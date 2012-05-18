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

public class StartDialog extends Dialog {
	
    private List<IStartDialogListener> listeners;

	public StartDialog(Context context) {
		super(context);
        listeners = new ArrayList<IStartDialogListener>();
	}
	
	@Override	 
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.start_dialog);
	    setTitle("");		    
	    FontHelper.SetFont((TextView) findViewById(R.id.sd_start));	    
	    Button btnStart = (Button) findViewById(R.id.btnStart);	    
	    btnStart.setOnClickListener(new StartListener()); 	    
	    FontHelper.SetFont(btnStart);
    }	
	
	public void addListener(IStartDialogListener listener){
		listeners.add(listener); 
	}
		
    private class StartListener implements android.view.View.OnClickListener {
		public void onClick(View v) {				
			for (IStartDialogListener listener : listeners) listener.startGame();
			StartDialog.this.dismiss();	
		}
    }   
}


