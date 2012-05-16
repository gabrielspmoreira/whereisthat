package com.whereisthat.game.rules;

import java.util.ArrayList;
import java.util.List;

public class GameScore {	
	
	private List<Round> rounds;
	
	public GameScore(){
		rounds = new ArrayList<Round>();
	}
	
	public void addRound(Round round){
		rounds.add(round);
	}
	
	public long getScore(){
		long score = 0;
		if (rounds.size() > 0){
			for (Round round : rounds) {
				score += round.getScore();
			}
		}		
		return score;
	}
	
	public long getLastRoundScore(){
		if (rounds.size() > 0){
			return rounds.get(rounds.size() -1).getScore();
		}		
		return 0;
	}
}
