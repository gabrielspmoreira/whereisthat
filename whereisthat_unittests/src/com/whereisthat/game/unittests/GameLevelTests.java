package com.whereisthat.game.unittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.whereisthat.data.Level;
import com.whereisthat.game.rules.GameLevel;
import com.whereisthat.game.rules.Round;

public class GameLevelTests {

	private GameLevel gameLevel;
	
	// Distance constants
	private static final long SHORT_DISTANCE_KM = 100;
	private static final long MAX_SCORE_DISTANCE_KM = 2000;
	private static final long ABOVE_MAX_DISTANCE_KM = 5000;
	
	// Elapsed time constants (miliseconds)
	private static final long QUICK_ANSWER = 2000;
	private static final long MEDIUM_TIME_ANSWER = 5000;
	private static final long MAX_TIME_SCORE_ANSWER = 10000;
	private static final long ABOVE_MAX_TIME_ANSWER = 15000;
	
	@Before
	public void setUp() throws Exception {
		Level level = new Level("Cities", 10, 10000, false, "cities", "World Cities");
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
		gameLevel.addRound(new Round(0, ABOVE_MAX_TIME_ANSWER));
		assertEquals(MAX_SCORE_DISTANCE_KM, gameLevel.getScore());
	}
	
	@Test
	public void testRound1DistanceAlmostOnTheTarget(){
		gameLevel.addRound(new Round(SHORT_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		assertEquals(1900, gameLevel.getScore());
	}
	
	@Test
	public void testRound1DistanceInvalidNegativeValue(){
		gameLevel.addRound(new Round(-1000, ABOVE_MAX_TIME_ANSWER));
		assertEquals(0, gameLevel.getScore());
	}
	
	@Test
	public void testRound1DistanceNearRound2Far(){
		gameLevel.addRound(new Round(SHORT_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		gameLevel.addRound(new Round(ABOVE_MAX_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		assertEquals(1900, gameLevel.getScore());
	}
	
	@Test
	public void testThreeRoundsFar(){
		gameLevel.addRound(new Round(ABOVE_MAX_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		gameLevel.addRound(new Round(ABOVE_MAX_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		gameLevel.addRound(new Round(ABOVE_MAX_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		assertEquals(0, gameLevel.getScore());
	}
	
	@Test
	public void testThreeRoundsNear(){
		gameLevel.addRound(new Round(SHORT_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		gameLevel.addRound(new Round(SHORT_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		gameLevel.addRound(new Round(SHORT_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		assertEquals(5700, gameLevel.getScore());
	}
	
	@Test
	public void testRound1AnswerTimeQuick(){
		gameLevel.addRound(new Round(ABOVE_MAX_DISTANCE_KM, MEDIUM_TIME_ANSWER));
		assertEquals(100, gameLevel.getScore());
	}
	
	@Test
	public void testRound1AnswerTimeMedium(){
		gameLevel.addRound(new Round(ABOVE_MAX_DISTANCE_KM, QUICK_ANSWER));
		assertEquals(160, gameLevel.getScore());
	}
	
	@Test
	public void testRound1AnswerTimeMaximumToScore(){
		gameLevel.addRound(new Round(ABOVE_MAX_DISTANCE_KM, MAX_TIME_SCORE_ANSWER));
		assertEquals(0, gameLevel.getScore());
	}
	

	@Test
	public void testRound1DistanceandTimeAboveMaximum(){
		gameLevel.addRound(new Round(ABOVE_MAX_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		assertEquals(0, gameLevel.getScore());
	}
	
}
