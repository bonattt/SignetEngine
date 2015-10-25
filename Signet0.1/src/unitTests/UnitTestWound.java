package unitTests;

import static org.junit.Assert.*;

import java.lang.reflect.*;

import injuries.BluntWound;
import injuries.BurnWound;
import injuries.PiercingWound;
import injuries.PiercingWound;
import injuries.SlashingWound;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import health.BodyPart;
import health.Wound;
import health.DamageType;
import bodyparts.*;

public class UnitTestWound {
	
	private BodyPart testBodypart;
	private Wound wound;
	
	@Before
	public void setUp(){
		testBodypart = new Arm("generic arm");
		wound = new SlashingWound(3, testBodypart);
	}
	
	@Test
	public void testLightWoundDealsNoDamage(){
		Wound wound = new SlashingWound(1, testBodypart);
		double[] array = wound.passTime(24000, 1, false);
		assertEquals(0, array[0], 0);
		assertEquals(0, array[1], 0);
		assertEquals(0, array[2], 0);
	}
	
	@Test
	public void testDeepWoundDealsDamage(){
		Wound wound = new SlashingWound(6, testBodypart);
		double[] array = wound.passTime(24000, 1, false);
		assertNotEquals(0.0, array[0], 0.1);
	}
	@Test
	public void testDeepWoundDealsNoStunOrFatigue(){
		Wound wound = new SlashingWound(6, testBodypart);
		double[] array = wound.passTime(24000, 1, false);
		assertEquals(0, array[1], 0);
		assertEquals(0, array[2], 0);
	}
	@Test
	public void testLightWoundDealsNoInstantDamage(){
		Wound wound = new SlashingWound(1, testBodypart);		
		int[] array = wound.getInstantDamage();
		assertEquals(0, array[0]);
		assertEquals(0, array[1]);
		assertEquals(0, array[2]);
	}
	@Test
	public void testDeepWoundDealsInstantDamage(){
		Wound wound = new PiercingWound(6, testBodypart);
		int[] array = wound.getInstantDamage();
		assertNotEquals(0, array[0]);
	}
	@Test
	public void testDeepWoundDealsNoInstantStunOrFatigue(){
		Wound wound = new PiercingWound(6, testBodypart);
		int[] array = wound.getInstantDamage();
		assertEquals(0, array[1]);
		assertEquals(0, array[2]);
	}
	@Test
	public void cannotCreateWoundWorseThan7(){
		Wound w = new BluntWound(7, testBodypart);
		w = new PiercingWound(6, testBodypart);
		w = new SlashingWound(5, testBodypart);
		w = new BurnWound(4, testBodypart);
		try {
			w= new BluntWound(8, testBodypart);
			fail();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
		
		try {
			w= new PiercingWound(9, testBodypart);
			fail();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
		
		try {
			w= new SlashingWound(10, testBodypart);
			fail();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
		
		try {
			w= new BurnWound(11, testBodypart);
			fail();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}	
	
	@Test
	public void testWoundHealsOverTimeNotResting(){
		int initialSeverity = 3;
		Wound w = new SlashingWound(initialSeverity, testBodypart);
		w.passTime(300000, 1, false);
		assertTrue(initialSeverity > w.getSeverity());
	}
	@Test
	public void testWoundHealsOverTimeResting(){
		int initialSeverity = 3;
		Wound w = new PiercingWound(initialSeverity, testBodypart);
		w.passTime(300000, 1, true);
		assertTrue(initialSeverity > w.getSeverity());
	}
	@Test
	public void testWoundHealsOverTimeReflection(){

		int initialSeverity = 6;
		Wound wound = new SlashingWound(initialSeverity, testBodypart);
		int timePassed = 300000;
		double healingFactor = 1;
		wound.passTime(timePassed, healingFactor, false);
		int healedSeverity = 0;
		String str = "severity";
		try {
			Field field = Wound.class.getDeclaredField(str);
			field.setAccessible(true);
			healedSeverity = (Integer) field.get(wound);
		} catch (IllegalArgumentException e) {
			fail("threw illegal arguments exception");
		} catch (IllegalAccessException e) {
			fail("threw illegal access exception");
		} catch (NoSuchFieldException e) {
			fail("threw no such field exception");
		} catch (SecurityException e) {
			fail("threw security exception");
		}
		assertTrue(initialSeverity > healedSeverity);
	}
	
	
	@Test
	public void testSevereWoundCreatesMorePainThanMinorWound(){
		Wound wound1 = new PiercingWound(2, testBodypart);
		Wound wound2 = new PiercingWound(6, testBodypart);
		assertTrue(wound1.getPain() < wound2.getPain());
	}
	@Test
	public void addNewWoundAddsNewWound(){
		 int initialCount = testBodypart.getWoundCount();
		 Wound.addNewWound(3, DamageType.SLASHING, testBodypart);
		 assertEquals(initialCount + 1, testBodypart.getWoundCount());
	}

	@Test
	public void testHealingTimeIsRandom(){
		int initialSeverity = 6;
		Wound wound1 = new SlashingWound(initialSeverity, testBodypart);
		Wound wound2 = new SlashingWound(initialSeverity, testBodypart);
		Wound wound3 = new SlashingWound(initialSeverity, testBodypart);
		String str = "recoveryTime";
		int recoveryTime1 = 0;
		int recoveryTime2 = 0;
		int recoveryTime3 = 0;
		try {
			Field field = Wound.class.getDeclaredField(str);
			field.setAccessible(true);
			recoveryTime1 = ((int[]) field.get(wound1))[5];
			recoveryTime2 = ((int[]) field.get(wound2))[5];
			recoveryTime3 = ((int[]) field.get(wound3))[5];
		} catch (IllegalArgumentException e) {
			fail("threw illegal arguments exception");
		} catch (IllegalAccessException e) {
			fail("threw illegal access exception");
		} catch (NoSuchFieldException e) {
			fail("threw no such field exception");
		} catch (SecurityException e) {
			fail("threw security exception");
		}
		assertTrue((recoveryTime1 != recoveryTime2)
				||(recoveryTime2 != recoveryTime3)
				||(recoveryTime1 != recoveryTime3));
	}

	@Test
	public void testWoundBleedsOverTime(){
		Wound wound = new SlashingWound(6, testBodypart);
		double[] damage = wound.passTime(10000, 6, false);
		assertTrue(damage[0] > 0);
	}
	@Test
	public void testWoundStopsBleeding(){
		Wound wound = new SlashingWound(6, testBodypart);
		wound.passTime(193200, 1, false);
		double[] damage = wound.passTime(1000, 1, false);
		assertEquals(0, damage[0], 0);
	}
	@Test
	public void testMinorWoundDoesNotBleed(){
		Wound wound = new SlashingWound(1, testBodypart);
		double[] damage = wound.passTime(1000, 1, false);
		assertEquals(0, damage[0], 0);
	}
	@Test
	public void testWoundBleedsProportionalToTimePassed(){
		Wound wound = new SlashingWound(1, testBodypart);
		double damageShort = wound.passTime(1000, 6, false)[0];
		double damageLong = wound.passTime(3000, 6, false)[0];
		assertEquals(damageShort * 3, damageLong, .00001);
	}
}
