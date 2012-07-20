package com.whereisthat.game.rules;

import com.esri.android.map.MapView;
import com.whereisthat.data.Level;
import com.whereisthat.data.Location;
import com.whereisthat.data.Locations;

import android.content.res.Resources;

public class Game {
	
	private GameLevels gameLevels;
	private Locations locations;
	private int numberOfPlays;
	
	public Game(MapView map){
		gameLevels = new GameLevels();
		locations = new Locations(map);
	}
	
	public void loadDatasets(Resources resource){
		locations.loadFromXml(resource);
		gameLevels.load(resource);
	}
	
	public void startGame(){
		nextLevel();
	}
	
	public void addRound(Round round){
		gameLevels.addRound(round);
	}
	
	public void nextLevel(){		
		//if(++numberOfPlays > gameLevels.getCurrentLevel().getMaxRoundsToPass())
		//{
			
		//}
		
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
