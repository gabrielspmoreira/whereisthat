package com.whereisthat.screen.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.whereisthat.R;
import com.whereisthat.helper.ConnHelper;
import com.whereisthat.helper.FontHelper;
import com.whereisthat.helper.SoundType;
import com.whereisthat.screen.core.SoundManager;

public class GameMenuActivity extends Activity implements OnTouchListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.menu);
		setCustomFont();
		//SoundManager.start(SoundType.menu);
		((ImageView) findViewById(R.id.imgSettings)).setOnTouchListener(this);
		checkNetwork();		
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
		SoundManager.stop(SoundType.menu);
	}
	
	@Override
	protected void onResume() {
		SoundManager.Init(getApplicationContext());
		super.onResume();
		SoundManager.start(SoundType.menu);
	}

	public void btnNewGame(View view){
		if(!checkNetwork()) return;		
		SoundManager.start(SoundType.click);
		Intent action = new Intent(GameMenuActivity.this, InGameActivity.class);
		GameMenuActivity.this.startActivity(action);
		GameMenuActivity.this.finish();
	}

	public void btnClose(View view){
		finish();
	}
	
	public void btnExit(View view) {
		finish();
		System.exit(0);
	}	
	
	private void setCustomFont() {
		FontHelper.Init(getAssets());
		FontHelper.SetFont((TextView) findViewById(R.id.new_game_button));		
	}
	
	private Boolean checkNetwork(){
		if(!ConnHelper.isValid(GameMenuActivity.this)){			
			AlertDialog ad = new AlertDialog.Builder(this).create();  
			ad.setTitle(getString(R.string.app_name));
			ad.setMessage(getString(R.string.msg_network_error)); 
			ad.setButton(getString(R.string.msg_ok), new DialogInterface.OnClickListener() {  
			    public void onClick(DialogInterface dialog, int which) {  
			        dialog.dismiss();                      
			    }  
			});
			ad.show(); 
			return false;
		}
		return true;
	}
	
	private void setButtonImage(int button, int action, int imageIn, int imageOut)
	{
		ImageView iButton = (ImageView) findViewById(button);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			iButton.setImageResource(imageIn);
			break;

		case MotionEvent.ACTION_MOVE:
			iButton.setImageResource(imageOut);
			break;
		}	
	}
		
	public boolean onTouch(View v, MotionEvent event) {
	
		switch (v.getId()) {
		case R.id.imgSettings:
				setButtonImage(R.id.imgSettings, event.getAction(), R.drawable.mbt_settings_p, R.drawable.mbt_settings);
			break;
		}		
		return false;
	}
}
