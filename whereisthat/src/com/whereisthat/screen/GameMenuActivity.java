package com.whereisthat.screen;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import com.whereisthat.R;

public class GameMenuActivity extends Activity {

	private MediaPlayer backgroundSound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}

	@Override
	protected void onStart() {
		super.onStart();
		backgroundSound = MediaPlayer.create(this, R.raw.game_menu);
		backgroundSound.setLooping(true);
		backgroundSound.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		backgroundSound.stop();
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
}
