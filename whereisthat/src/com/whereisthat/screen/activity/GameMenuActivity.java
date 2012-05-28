package com.whereisthat.screen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.whereisthat.R;
import com.whereisthat.helper.FontHelper;
import com.whereisthat.helper.SoundType;
import com.whereisthat.screen.core.SoundManager;

public class GameMenuActivity extends Activity implements OnTouchListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		setCustomFont();
		SoundManager.Init(getApplicationContext());
		SoundManager.start(SoundType.menu);
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

	public void btnNewGame(View view) {
		Intent action = new Intent(GameMenuActivity.this, InGameActivity.class);
		GameMenuActivity.this.startActivity(action);
		GameMenuActivity.this.finish();
	}

	public void btnExit(View view) {
		finish();
		System.exit(0);
	}
	
	
	private void setCustomFont() {
		FontHelper.Init(getAssets());
		FontHelper.SetFont((TextView) findViewById(R.id.new_game_button));
		FontHelper.SetFont((TextView) findViewById(R.id.settings_button));
		FontHelper.SetFont((TextView) findViewById(R.id.ranking_button));
		FontHelper.SetFont((TextView) findViewById(R.id.exit_button));
	}
	
	@Override		
	public boolean onTouch(View v, MotionEvent event) {
	
		switch (v.getId()) {
		case R.id.ivSettings:
				setButtonImage(R.id.ivSettings, event.getAction(), R.drawable.mbt_settings_p, R.drawable.mbt_settings);
			break;
		}		
		return false;
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
}
