package com.whereisthat.game.rules;

import com.whereisthat.helper.GameConstants;

public class Round {
	
	private double distance;
	private long answerDelay;
	
	public Round(double distance, long answerDelay){
		this.distance = distance;
		this.answerDelay = answerDelay;
	}
	
	public void setDistance(double distance){
		this.distance = distance;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public void setAnswerDelay(long answerDelay){
		this.answerDelay = answerDelay;
	}
	
	public long getAnswerDelay(){
		return answerDelay;
	}
	
	public long getScore(){
		if (distance < 0){
			return 0;
		}
		long distanceScore = Math.max(0, GameConstants.MAXIMUM_KM_DISTANCE_TO_SCORE - (long) Math.floor(distance));

		long answerDelayScore = Math.max(0, (GameConstants.MAXIMUM_MILISECONDS_TO_ANSWER - answerDelay) / 
				(GameConstants.MAXIMUM_MILISECONDS_TO_ANSWER / GameConstants.MAXIMUM_SCORE_FOR_TIME));
		
		return distanceScore + answerDelayScore;
	}

}
