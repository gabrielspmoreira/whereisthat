package com.whereisthat.game.unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.whereisthat.data.Level;
import com.whereisthat.data.LocationType;
import com.whereisthat.game.rules.GameLevel;
import com.whereisthat.game.rules.IRulesSettings;
import com.whereisthat.game.rules.Round;

public class RoundTests {
	
	private IRulesSettings rulesSettings;
	
	@Before
	public void setUp() throws Exception {
		rulesSettings = new RulesSettings();
	}

	@Test
	public void testRound1DistanceRightOnTheTarget(){
		Round round = new Round(0, TestConstants.MAX_TIME_SCORE_ANSWER, rulesSettings);
		assertEquals(TestConstants.MAX_SCORE_DISTANCE_KM, round.getScore());
	}
	
	@Test
	public void testRound1DistanceAlmostOnTheTarget(){
		Round round = new Round(TestConstants.SHORT_DISTANCE_KM, TestConstants.MAX_TIME_SCORE_ANSWER, rulesSettings);
		assertEquals(1900, round.getScore());
	}
	
	@Test
	public void testRound1DistanceInvalidNegativeValue(){
		Round round = new Round(-1000, TestConstants.ABOVE_MAX_TIME_ANSWER, rulesSettings);
		assertEquals(0, round.getScore());
	}
	
	@Test
	public void testRound1AnswerTimeMedium(){
		Round round = new Round(TestConstants.SHORT_DISTANCE_KM, TestConstants.MEDIUM_TIME_ANSWER, rulesSettings);
		assertEquals(2000, round.getScore());
	}
	
	@Test
	public void testRound1AnswerTimeQuick(){
		Round round = new Round(TestConstants.SHORT_DISTANCE_KM, TestConstants.QUICK_ANSWER, rulesSettings);
		assertEquals(2060, round.getScore());
	}
	
	@Test
	public void testRound1AnswerTimeMaximumToScore(){
		Round round = new Round(TestConstants.ABOVE_MAX_DISTANCE_KM, TestConstants.MAX_TIME_SCORE_ANSWER, rulesSettings);
		assertEquals(0, round.getScore());
	}

	@Test
	public void testRound1DistanceandTimeAboveMaximum(){
		Round round = new Round(TestConstants.ABOVE_MAX_DISTANCE_KM, TestConstants.ABOVE_MAX_TIME_ANSWER, rulesSettings);
		assertEquals(0, round.getScore());
	}
	
	@Test
	public void testRound1QuickAnswerFarDistance(){
		Round round = new Round(TestConstants.ABOVE_MAX_DISTANCE_KM, TestConstants.QUICK_ANSWER, rulesSettings);
		assertEquals(0, round.getScore());
	}

}
