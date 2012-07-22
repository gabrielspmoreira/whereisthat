package com.whereisthat.game.rules;

import com.esri.android.map.MapView;
import com.whereisthat.data.Level;
import com.whereisthat.data.Location;
import com.whereisthat.data.Locations;

import android.content.res.Resources;

public class Game {
	
	private GameLevels gameLevels;
	private Locations locations;
	private IRulesSettings rulesSettings;
	
	public Game(MapView map, IRulesSettings rulesSettings){
		gameLevels = new GameLevels();
		locations = new Locations(map);
		this.rulesSettings = rulesSettings;
	}
	
	public void loadDatasets(Resources resource){
		locations.loadFromXml(resource);
		gameLevels.load(resource);
	}
	
	public void startGame(){
		nextLevel();
	}
	
	public void addRound(double distance, long answerDelay){
		gameLevels.addRound(new Round(distance, answerDelay, rulesSettings));
	}
	
	public long getLastRoundScore(){
		return gameLevels.getLastRoundScore();
	}
	
	public void nextLevel(){				
		locations.resetReturnedLocations();
		gameLevels.nextLevel();
	}
	
	public boolean IsNextLevelReached(){
		return gameLevels.IsNextLevelReached();
	}
	
	public boolean IsMaximumRoundsReached(){
		return gameLevels.IsMaximumRoundsReached();
	}
	
	public long getScore() {
		return gameLevels.getScore();
	}
	
	public Level getCurrentLevel(){
		return gameLevels.getCurrentLevel();
	}
	
	public Location getNextLocation(){
		Level level = getCurrentLevel();
		return locations.getNextLocation(level);
	}
	
	public void restart() {
		gameLevels.reset();
	}
}
