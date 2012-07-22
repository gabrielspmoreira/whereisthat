package com.whereisthat.game.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.res.Resources;

import com.whereisthat.data.Level;
import com.whereisthat.data.Levels;

public class GameLevels {
	
	private List<GameLevel> gameLevels;
	private GameLevel currentLevel;
	private Iterator<Level> levelIterator;
	
	public GameLevels(){
		gameLevels = new ArrayList<GameLevel>();
		currentLevel = null;
	}
	
	public void load(Resources resource){
		Levels.loadFromXml(resource);
		levelIterator = Levels.getLevels().iterator();
	}
	
	public void addRound(Round round){
		if (currentLevel == null) return;
		
		currentLevel.addRound(round);
	}
	
	public void nextLevel(){
		if (gameLevels.size() == 0 || IsNextLevelReached()){
			Level level = levelIterator.next();
			currentLevel = new GameLevel(level);
			gameLevels.add(currentLevel);
		}
	}
	
	public boolean IsNextLevelReached(){
		if (currentLevel == null) return false;
		
		return currentLevel.IsNextLevelReached();
	}
	
	public boolean IsMaximumRoundsReached(){
		if (currentLevel == null) return false;
		
		return currentLevel.IsMaximumRoundsReached();
	}
	
	public long getScore(){
		long score = 0;
		if (gameLevels.size() > 0){
			for (GameLevel level : gameLevels) {
				score += level.getScore();
			}
		}		
		return score;
	}
	
	public long getLastRoundScore(){
		return gameLevels.get(gameLevels.size()-1).getLastRoundScore();
	}
	
	public Level getCurrentLevel(){
		return currentLevel.getLevel();
	}
	
	public void reset() {
		gameLevels.clear();
		levelIterator = Levels.getLevels().iterator();
		currentLevel = null;
	}
}
