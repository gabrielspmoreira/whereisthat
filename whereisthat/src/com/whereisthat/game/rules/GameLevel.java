package com.whereisthat.game.rules;

import java.util.ArrayList;
import java.util.List;

import com.whereisthat.data.Level;
import com.whereisthat.data.Location;
import com.whereisthat.data.Locations;

public class GameLevel {	
	
	private List<Round> rounds;
	private Level level;
	
	public GameLevel(Level level){
		this.level = level;
		this.rounds = new ArrayList<Round>();
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
	
	
	public boolean IsNextLevelReached(){
		return level.IsNextLevelReached(getScore());
	}
	
	public boolean IsMaximumRoundsReached(){
		return level.IsMaximumRoundsReached(rounds.size());
	}
	
	public Level getLevel(){
		return level;
	}
}
