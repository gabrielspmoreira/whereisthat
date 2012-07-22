package com.whereisthat.game.rules;

public class Round {
	
	private double distance;
	private long answerDelay;
	private long calculatedScore;
	private IRulesSettings rulesSettings;
	
	public Round(double distance, long answerDelay, IRulesSettings rulesSettings){
		this.distance = distance;
		this.answerDelay = answerDelay;
		this.rulesSettings = rulesSettings;
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
			    answerDelay <= rulesSettings.getMaximumMilisecondsToAnswer()){
				
				long distanceScore = Math.max(0, rulesSettings.getMaximumKmDistanceToScore() - (long) Math.floor(distance));
				long answerDelayScore = 0;

				if (distanceScore > 0){
					answerDelayScore = Math.max(0, (rulesSettings.getMaximumMilisecondsToAnswer() - answerDelay) / 
							(rulesSettings.getMaximumMilisecondsToAnswer() / rulesSettings.getMaximumScoreForTime()));
				}
				
				
				calculatedScore = distanceScore + answerDelayScore;
			}
		}
		
		return calculatedScore;
	}

}
