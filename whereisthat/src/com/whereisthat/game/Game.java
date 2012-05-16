package com.whereisthat.game;

import java.util.ArrayList;
import java.util.List;

public class Game {	
	
	private List<Round> rounds;
	
	public Game(){
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
}
