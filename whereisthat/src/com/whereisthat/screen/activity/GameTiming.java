package com.whereisthat.screen.activity;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameTiming {
	
	private ScheduledExecutorService scheduler;
	private long startTime;
	private boolean running;
	
	private static final int REFRESH_MILISECONDS = 200;
	
	public GameTiming(){
		running = false;
	}
	
	public void startRound(Runnable runnable){
		scheduler = Executors.newSingleThreadScheduledExecutor();
		
		scheduler.scheduleWithFixedDelay(runnable, 0, REFRESH_MILISECONDS, TimeUnit.MILLISECONDS);
		
		startTime = new Date().getTime();
		running = true;
	}
	
	public long getElapsetTime(){
		return new Date().getTime() - startTime;
	}
	
	public void stopRound(){
		if (running){
			scheduler.shutdown();
			running = false;
		}		
	}
}
