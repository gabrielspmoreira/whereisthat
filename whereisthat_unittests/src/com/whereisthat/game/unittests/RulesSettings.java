package com.whereisthat.game.unittests;

import com.whereisthat.game.rules.IRulesSettings;

public class RulesSettings implements IRulesSettings {
	
	private static final long MAXIMUM_KM_DISTANCE_TO_SCORE = 2000;
	private static final int MAXIMUM_MILISECONDS_TO_ANSWER = 10000;
	private static final long MAXIMUM_SCORE_FOR_TIME = 200;
	

	@Override
	public long getMaximumKmDistanceToScore(){
		return MAXIMUM_KM_DISTANCE_TO_SCORE;
	}
	

	@Override
	public int getMaximumMilisecondsToAnswer(){
		return MAXIMUM_MILISECONDS_TO_ANSWER;
	}
	
	@Override
	public long getMaximumScoreForTime(){
		return MAXIMUM_SCORE_FOR_TIME;
	}
}
