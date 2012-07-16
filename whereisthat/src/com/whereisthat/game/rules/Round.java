package com.whereisthat.game.rules;

import com.whereisthat.helper.GameConstants;

public class Round {
	
	private double distance;
	private long answerDelay;
	private long calculatedScore;
	
	public Round(double distance, long answerDelay){
		this.distance = distance;
		this.answerDelay = answerDelay;
		this.calculatedScore = -1;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public long getAnswerDelay(){
		return answerDelay;
	}
	
	public long getScore(){
		if (calculatedScore < 0){
			calculatedScore = 0;
			if (distance >= 0 && answerDelay >= 0 &&
			    answerDelay <= GameConstants.MAXIMUM_MILISECONDS_TO_ANSWER){
				
				long distanceScore = Math.max(0, GameConstants.MAXIMUM_KM_DISTANCE_TO_SCORE - (long) Math.floor(distance));
				long answerDelayScore = 0;

				if (distanceScore > 0){
					answerDelayScore = Math.max(0, (GameConstants.MAXIMUM_MILISECONDS_TO_ANSWER - answerDelay) / 
							(GameConstants.MAXIMUM_MILISECONDS_TO_ANSWER / GameConstants.MAXIMUM_SCORE_FOR_TIME));
				}
				
				
				calculatedScore = distanceScore + answerDelayScore;
			}
		}
		
		return calculatedScore;
	}

}
