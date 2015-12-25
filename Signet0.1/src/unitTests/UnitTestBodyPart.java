package unitTests;

import java.lang.reflect.*;

import static org.junit.Assert.*;
import health.BodyPart;
import health.Injuries;

import org.junit.Before;
import org.junit.Test;

import bodyparts.Arm;

public class UnitTestBodyPart {
	
	private BodyPart testBodypart;
	
	@Before
	public void setUp(){
		testBodypart = new Arm("arm");
	}
	@Test
	public void testWoundDisappearWhenHealed(){
		testBodypart.addNewWound(Injuries.getSlashingWound(2, testBodypart));
		testBodypart.passTime(1400000, 1, false);
		assertEquals(0, testBodypart.getWoundCount());
	}
	
	@Test
	public void testCountInjuriesIncreasesWhenWoundsAdded(){
		int initialCount = testBodypart.getWoundCount();
		testBodypart.addNewWound(Injuries.getSlashingWound(4, testBodypart));
		assertEquals(initialCount+1, testBodypart.getWoundCount());
	}
	@Test
	public void testCountInjuriesReturnsZeroWithNoWounds(){
		assertEquals(0, testBodypart.getWoundCount());
	}
	@Test
	public void testPainIncreasesWithInjury(){
		double initialPain = testBodypart.getPain();
		testBodypart.addNewWound(Injuries.getSlashingWound(4, testBodypart));
		double pain1 = testBodypart.getPain();
		assertTrue(pain1 > initialPain);
	}
	@Test
	public void testMoreWoundsCreateMorePain(){
		testBodypart.addNewWound(Injuries.getSlashingWound(4, testBodypart));
		double pain1 = testBodypart.getPain();
		testBodypart.addNewWound(Injuries.getSlashingWound(4, testBodypart));
		double pain2 = testBodypart.getPain();
		assertTrue(pain2 > pain1);
	}
	@Test
	public void testWoundBleedsOverTimeOnBodyPart(){
		testBodypart.addNewWound(Injuries.getSlashingWound(6, testBodypart));
		double[] damage = testBodypart.passTime(10000, 1, false);
		assertTrue(damage[0] < 0);
	}
}