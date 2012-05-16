package com.whereisthat.game.unittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.whereisthat.game.Game;
import com.whereisthat.game.Round;

public class GameTests {

	private Game game;
	
	// Distance constants
	private static final long SHORT_DISTANCE_KM = 100;
	private static final long MEDIUM_DISTANCE_KM = 1500;
	private static final long MAX_SCORE_DISTANCE_KM = 2000;
	private static final long ABOVE_MAX_DISTANCE_KM = 5000;
	
	// Elapsed time constants (miliseconds)
	private static final long QUICK_ANSWER = 2000;
	private static final long MEDIUM_TIME_ANSWER = 5000;
	private static final long MAX_TIME_SCORE_ANSWER = 10000;
	private static final long ABOVE_MAX_TIME_ANSWER = 15000;
	
	@Before
	public void setUp() throws Exception {
		game = new Game();
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testStartsWithScoreZero(){
		assertEquals(0, game.getScore());
	}
	
	@Test
	public void testRound1DistanceRightOnTheTarget(){
		game.addRound(new Round(0, ABOVE_MAX_TIME_ANSWER));
		assertEquals(MAX_SCORE_DISTANCE_KM, game.getScore());
	}
	
	@Test
	public void testRound1DistanceAlmostOnTheTarget(){
		game.addRound(new Round(SHORT_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		assertEquals(1900, game.getScore());
	}
	
	@Test
	public void testRound1DistanceInvalidNegativeValue(){
		game.addRound(new Round(-1000, ABOVE_MAX_TIME_ANSWER));
		assertEquals(0, game.getScore());
	}
	
	@Test
	public void testRound1DistanceNearRound2Far(){
		game.addRound(new Round(SHORT_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		game.addRound(new Round(ABOVE_MAX_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		assertEquals(1900, game.getScore());
	}
	
	@Test
	public void testThreeRoundsFar(){
		game.addRound(new Round(ABOVE_MAX_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		game.addRound(new Round(ABOVE_MAX_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		game.addRound(new Round(ABOVE_MAX_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		assertEquals(0, game.getScore());
	}
	
	@Test
	public void testThreeRoundsNear(){
		game.addRound(new Round(SHORT_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		game.addRound(new Round(SHORT_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		game.addRound(new Round(SHORT_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		assertEquals(5700, game.getScore());
	}
	
	@Test
	public void testRound1AnswerTimeQuick(){
		game.addRound(new Round(ABOVE_MAX_DISTANCE_KM, MEDIUM_TIME_ANSWER));
		assertEquals(100, game.getScore());
	}
	
	@Test
	public void testRound1AnswerTimeMedium(){
		game.addRound(new Round(ABOVE_MAX_DISTANCE_KM, QUICK_ANSWER));
		assertEquals(160, game.getScore());
	}
	
	@Test
	public void testRound1AnswerTimeMaximumToScore(){
		game.addRound(new Round(ABOVE_MAX_DISTANCE_KM, MAX_TIME_SCORE_ANSWER));
		assertEquals(0, game.getScore());
	}
	

	@Test
	public void testRound1DistanceandTimeAboveMaximum(){
		game.addRound(new Round(ABOVE_MAX_DISTANCE_KM, ABOVE_MAX_TIME_ANSWER));
		assertEquals(0, game.getScore());
	}
}
