package com.whereisthat.screen.core;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;

import com.whereisthat.R;
import com.whereisthat.helper.SoundType;

public abstract class SoundManager {

	private static MediaPlayer clickSound;
	private static MediaPlayer mapTouch;	
	private static MediaPlayer menubackground;
	private static MediaPlayer inGamebackground;
	private static MediaPlayer winbackground;
<<<<<<< HEAD
	private static MediaPlayer losebackground;
	private static MediaPlayer openProgressBar;
	private static MediaPlayer closeProgressBar;
	
	
	public static void Init(Context context){
		
		menubackground = MediaPlayer.create(context, R.raw.game_menu);
		menubackground.setLooping(true);		
		
		inGamebackground = MediaPlayer.create(context, R.raw.game_bg);
		inGamebackground.setVolume(0.3f, 0.3f);
		inGamebackground.setLooping(true);	
		
		winbackground = MediaPlayer.create(context, R.raw.game_win);
		
		losebackground = MediaPlayer.create(context, R.raw.game_lose);
		
		click = MediaPlayer.create(context, R.raw.click);
		
		mapTouch = MediaPlayer.create(context, R.raw.touch_map);
		
		openProgressBar = MediaPlayer.create(context, R.raw.openpb);	
		
		closeProgressBar = MediaPlayer.create(context, R.raw.closerpd);
=======
	private static MediaPlayer losebackground;	
	private static boolean initialized;
	
	
	public static void Init(Context context){
		if (!initialized){
			menubackground = MediaPlayer.create(context, R.raw.game_menu);
			menubackground.setLooping(true);		
			
			inGamebackground = MediaPlayer.create(context, R.raw.game_bg);
			inGamebackground.setVolume(0.3f, 0.3f);
			inGamebackground.setLooping(true);	
			
			winbackground = MediaPlayer.create(context, R.raw.game_win);
			
			losebackground = MediaPlayer.create(context, R.raw.game_lose);
			
			clickSound = MediaPlayer.create(context, R.raw.click);
			
			mapTouch = MediaPlayer.create(context, R.raw.touch_map);
			
			initialized = true;
		}
>>>>>>> Fixing map and game sound when returning from idle state
	}
	
	public static void start(SoundType type){
		switch (type) {
			case click:
				clickSound.start();
				break;
			case touchMap:
				mapTouch.start();
				break;
			case inGame:
				inGamebackground.start();			
				break;
			case menu:	
				menubackground.start();
				break;
			case win:	
				inGamebackground.setVolume(0.08f, 0.08f);
				winbackground.start();
				break;
			case lose:	
				inGamebackground.setVolume(0.08f, 0.08f);
				losebackground.start();
				break;
			case openPb:
				openProgressBar.start();
				break;
			case closePb:
				//closeProgressBar.start();
				break;
		}
	}
	
	public static void stop(SoundType type){
		switch (type) {
			case inGame:
				inGamebackground.pause();
				break;
			case menu:	
				menubackground.pause();
				break;
			case win:
				inGamebackground.setVolume(0.3f, 0.3f);
				winbackground.stop();
				break;
			case lose:	
				inGamebackground.setVolume(0.3f, 0.3f);
				losebackground.stop();
				break;
			default:
				break;
		}
	}
	
}