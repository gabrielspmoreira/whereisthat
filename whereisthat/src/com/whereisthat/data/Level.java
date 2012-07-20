package com.whereisthat.data;

public class Level {
	
	private String name;
	private int maxRoundsToPass;
	private long minScoreToPass;
	private boolean hasLongQuestions;
	private String description;
	private int sequence;
	private LocationType locationType;
	
	private Level nextLevel;
	
	public Level(){
		
	}
	
	public Level(int sequence, String name, int maxRoundsToPass, long minScoreToPass, boolean hasLongQuestions,
			LocationType locationType, String description){
		this.sequence = sequence;
		this.name = name;
		this.maxRoundsToPass = maxRoundsToPass;
		this.minScoreToPass = minScoreToPass;
		this.hasLongQuestions = hasLongQuestions;
		this.description = description;
		this.locationType = locationType;
	}

	public int getSequence() {
		return sequence;
	}
	
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxRoundsToPass() {
		return maxRoundsToPass;
	}

	public void setMaxRoundsToPass(int maxRoundsToPass) {
		this.maxRoundsToPass = maxRoundsToPass;
	}

	public long getMinScoreToPass() {
		return minScoreToPass;
	}

	public void setMinScoreToPass(long minScoreToPass) {
		this.minScoreToPass = minScoreToPass;
	}

	public boolean getHasLongQuestions() {
		return hasLongQuestions;
	}

	public void setHasLongQuestions(boolean hasLongQuestions) {
		this.hasLongQuestions = hasLongQuestions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean IsNextLevelReached(long levelScore){
		return (levelScore >= minScoreToPass);
	}
	
	public boolean IsMaximumRoundsReached(int rounds){
		return (rounds >= maxRoundsToPass);
	}

	public Level getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(Level nextLevel) {
		this.nextLevel = nextLevel;
	}

	public LocationType getLocationType() {
		return locationType;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}
}
