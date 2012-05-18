package com.whereisthat.screen;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.whereisthat.R;
import com.whereisthat.helper.FontHelper;

public class GameMenuActivity extends Activity {

	private MediaPlayer soundbackground;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);	
		setCustomFont();	
	}

	@Override
	protected void onStart() {
		super.onStart();
		soundbackground = MediaPlayer.create(this, R.raw.game_menu);
		soundbackground.setLooping(true);
		soundbackground.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		soundbackground.stop();
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
	
	public void setCustomFont()
	{
		FontHelper.Init(getAssets());		
		FontHelper.SetFont((Button) findViewById(R.id.new_game_button));
		FontHelper.SetFont((Button) findViewById(R.id.settings_button));
		FontHelper.SetFont((Button) findViewById(R.id.about_button));
		FontHelper.SetFont((Button) findViewById(R.id.exit_button));
	}
}
