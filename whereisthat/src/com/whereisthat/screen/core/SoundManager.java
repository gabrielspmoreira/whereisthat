package com.whereisthat.screen.core;

import android.content.Context;
import android.media.MediaPlayer;

import com.whereisthat.R;
import com.whereisthat.helper.SoundType;

public abstract class SoundManager {

	private static MediaPlayer menubackground;
	private static MediaPlayer inGamebackground;
	
	public static void Init(Context context){
		menubackground = MediaPlayer.create(context, R.raw.game_menu);
		menubackground.setLooping(true);		
		
		inGamebackground = MediaPlayer.create(context, R.raw.game_bg);
		inGamebackground.setVolume(0.3f, 0.3f);
		inGamebackground.setLooping(true);		
	}
	
	public static void start(SoundType type){
		switch (type) {
			case inGame:
				inGamebackground.start();
				break;
			case menu:	
				menubackground.start();
				break;
		}
	}
	
	public static void stop(SoundType type){
		switch (type) {
			case inGame:
				inGamebackground.stop();
				break;
			case menu:	
				menubackground.stop();
				break;
		}
	}
	
}