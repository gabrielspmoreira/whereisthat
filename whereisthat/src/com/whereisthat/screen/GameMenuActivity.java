package com.whereisthat.screen;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.whereisthat.R;

public class GameMenuActivity extends Activity {

	private MediaPlayer soundbackground;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);	
		setCustomFontStyle();
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
	
	public void setCustomFontStyle()
	{
		android.graphics.Typeface font = 
				android.graphics.Typeface.createFromAsset(getAssets(), "fonts/showers.ttf");		

		((Button) findViewById(R.id.new_game_button)).setTypeface(font);
		((Button) findViewById(R.id.settings_button)).setTypeface(font);
		((Button) findViewById(R.id.about_button)).setTypeface(font);
		((Button) findViewById(R.id.exit_button)).setTypeface(font);
	}
}
