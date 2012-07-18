package com.whereisthat.game.unittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.whereisthat.data.Level;
import com.whereisthat.data.LocationType;
import com.whereisthat.game.rules.GameLevel;
import com.whereisthat.game.rules.Round;

public class GameLevelTests {

	private GameLevel gameLevel;
	
	@Before
	public void setUp() throws Exception {
		Level level = new Level("Cities", 5, 7000, false, LocationType.citiesEasy, "World Cities");
		gameLevel = new GameLevel(level);
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testStartsWithScoreZero(){
		assertEquals(0, gameLevel.getScore());
	}
	
	@Test
	public void testRound1DistanceRightOnTheTarget(){
		gameLevel.addRound(new Round(0, TestConstants.MAX_TIME_SCORE_ANSWER));
		assertEquals(TestConstants.MAX_SCORE_DISTANCE_KM, gameLevel.getScore());
	}
	
	@Test
	public void testRound1QuickAnswerFarDistance(){
		gameLevel.addRound(new Round(TestConstants.ABOVE_MAX_DISTANCE_KM, TestConstants.QUICK_ANSWER));
		assertEquals(0, gameLevel.getScore());
	}
	
	
	@Test
	public void testRound1DistanceNearRound2Far(){
		gameLevel.addRound(new Round(TestConstants.SHORT_DISTANCE_KM, TestConstants.MAX_TIME_SCORE_ANSWER));
		gameLevel.addRound(new Round(TestConstants.ABOVE_MAX_DISTANCE_KM, TestConstants.MAX_TIME_SCORE_ANSWER));
		assertEquals(1900, gameLevel.getScore());
	}
	
	@Test
	public void testThreeRoundsFar(){
		gameLevel.addRound(new Round(TestConstants.ABOVE_MAX_DISTANCE_KM, TestConstants.ABOVE_MAX_TIME_ANSWER));
		gameLevel.addRound(new Round(TestConstants.ABOVE_MAX_DISTANCE_KM, TestConstants.ABOVE_MAX_TIME_ANSWER));
		gameLevel.addRound(new Round(TestConstants.ABOVE_MAX_DISTANCE_KM, TestConstants.ABOVE_MAX_TIME_ANSWER));
		assertEquals(0, gameLevel.getScore());
	}
	
	@Test
	public void testThreeRoundsNearOneLate(){
		gameLevel.addRound(new Round(TestConstants.SHORT_DISTANCE_KM, TestConstants.MEDIUM_TIME_ANSWER));
		gameLevel.addRound(new Round(TestConstants.SHORT_DISTANCE_KM, TestConstants.MEDIUM_TIME_ANSWER));
		gameLevel.addRound(new Round(TestConstants.SHORT_DISTANCE_KM, TestConstants.MEDIUM_TIME_ANSWER));
		gameLevel.addRound(new Round(TestConstants.SHORT_DISTANCE_KM, TestConstants.ABOVE_MAX_TIME_ANSWER));
		assertEquals(6000, gameLevel.getScore());
	}
	
	@Test
	public void testMaximumRoundsNotReached(){
		for (int i=1; i<=4; i++){
			gameLevel.addRound(new Round(TestConstants.SHORT_DISTANCE_KM, TestConstants.MEDIUM_TIME_ANSWER));
		}
		assertEquals(false, gameLevel.IsMaximumRoundsReached());
	}
	
	@Test
	public void testMaximumRoundsReached(){
		for (int i=1; i<=5; i++){
			gameLevel.addRound(new Round(TestConstants.SHORT_DISTANCE_KM, TestConstants.MEDIUM_TIME_ANSWER));
		}
		assertEquals(true, gameLevel.IsMaximumRoundsReached());
	}
	
	@Test
	public void testIsNextLevelNotReached(){
		for (int i=1; i<=2; i++){
			gameLevel.addRound(new Round(0, TestConstants.MAX_TIME_SCORE_ANSWER));
		}
		assertEquals(false, gameLevel.IsNextLevelReached());
	}
	
	@Test
	public void testIsNextLevelNReached(){
		for (int i=1; i<=3; i++){
			gameLevel.addRound(new Round(0, TestConstants.MAX_TIME_SCORE_ANSWER));
		}
		assertEquals(false, gameLevel.IsNextLevelReached());
	}
}
