package com.whereisthat.game;

public class Round {
	
	public static final long MAXIMUM_KM_DISTANCE_TO_SCORE = 2000;
	public static final long MAXIMUM_MILISECONDS_TO_ANSWER = 10000;
	public static final long MAXIMUM_SCORE_FOR_TIME = 200;
	
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
		long distanceScore = Math.max(0, MAXIMUM_KM_DISTANCE_TO_SCORE - (long) Math.floor(distance));

		long answerDelayScore = Math.max(0, (MAXIMUM_MILISECONDS_TO_ANSWER - answerDelay) / (MAXIMUM_MILISECONDS_TO_ANSWER / MAXIMUM_SCORE_FOR_TIME));
		
		return distanceScore + answerDelayScore;
	}

}
