package com.whereisthat.game.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;

import com.whereisthat.data.Level;
import com.whereisthat.data.Levels;

public class GameLevels {
	
	public Levels levels;
	private List<GameLevel> gameLevels;
	private GameLevel currentLevel;
	
	public GameLevels(){
		gameLevels = new ArrayList<GameLevel>();
		levels = new Levels();
		currentLevel = null;
	}
	
	public void load(Resources resource){
		levels.loadFromXml(resource);
	}
	
	public void addRound(Round round){
		if (currentLevel == null) return;
		
		currentLevel.addRound(round);
	}
	
	public void nextLevel(){
		if (gameLevels.size() == 0 || IsNextLevelReached()){
			Level level = levels.iterator().next();
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
	
	public Level getCurrentLevel(){
		return currentLevel.getLevel();
	}
}
