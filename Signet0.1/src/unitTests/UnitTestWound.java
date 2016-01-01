package unitTests;

import static org.junit.Assert.*;
import items.Bandage;
import items.Ointment;

import java.lang.reflect.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import testingMothers.SampleBodyparts;
import health.BodyPart;
import health.Injuries;
import health.Wound;
import health.DamageType;

public class UnitTestWound {
	
	private BodyPart testBodypart;
	private Wound wound;
	
	@Before
	public void setup(){
		testBodypart = SampleBodyparts.arm("arm");
		wound = Injuries.getSlashingWound(3, testBodypart, false);
	}
	
	@Test
	public void equalsSelf() {
		assertEquals(wound, wound);
	}
	
	@Test
	public void notEqualsWithoutBandage() {
		wound.setBandage(new Bandage());
		Wound wound1 = Injuries.getSlashingWound(3, testBodypart, false);
		assertNotEquals(wound, wound1);
	}
	
	@Test
	public void notEqualsWithoutOintment() {
		wound.setTreatment(new Ointment());
		Wound wound1 = Injuries.getSlashingWound(3, testBodypart, false);
		assertNotEquals(wound, wound1);
	}

	@Test
	public void notEquals() {
		Wound newWound = Injuries.getBluntWound(3, testBodypart);
		assertNotEquals(wound, newWound);
	}
	
	@Test
	public void testLightWoundDealsNoDamage(){
		Wound wound = Injuries.getSlashingWound(1, testBodypart);
		double[] array = wound.passTime(24000, 1, false);
		assertEquals(0, array[0], 0);
		assertEquals(0, array[1], 0);
		assertEquals(0, array[2], 0);
	}
	
	@Test
	public void testDeepWoundDealsDamage(){
		Wound wound = Injuries.getSlashingWound(6, testBodypart);
		double[] array = wound.passTime(24000, 1, false);
		assertNotEquals(0.0, array[0], 0.1);
	}
	@Test
	public void testDeepWoundDealsNoStunOrFatigue(){
		Wound wound = Injuries.getSlashingWound(6, testBodypart);
		double[] array = wound.passTime(24000, 1, false);
		assertEquals(0, array[1], 0);
		assertEquals(0, array[2], 0);
	}
	@Test
	public void testLightWoundDealsNoInstantDamage(){
		Wound wound = Injuries.getSlashingWound(1, testBodypart);		
		int[] array = wound.getInstantDamage();
		assertEquals(0, array[0]);
		assertEquals(0, array[1]);
		assertEquals(0, array[2]);
	}
	@Test
	public void testDeepWoundDealsInstantDamage(){
		Wound wound = Injuries.getStabWound(6, testBodypart);
		int[] array = wound.getInstantDamage();
		assertNotEquals(0, array[0]);
	}
	@Test
	public void testDeepWoundDealsNoInstantStunOrFatigue(){
		Wound wound = Injuries.getStabWound(6, testBodypart);
		int[] array = wound.getInstantDamage();
		assertEquals(0, array[1]);
		assertEquals(0, array[2]);
	}
	@Test
	public void cannotCreateWoundWorseThan7(){
		Wound w = Injuries.getBluntWound(7, testBodypart);
		w = Injuries.getStabWound(6, testBodypart);
		w = Injuries.getSlashingWound(5, testBodypart);
		w = Injuries.getBurnWound(4, testBodypart);
		try {
			w= Injuries.getBluntWound(8, testBodypart);
			fail();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
		
		try {
			w= Injuries.getStabWound(9, testBodypart);
			fail();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
		
		try {
			w= Injuries.getSlashingWound(10, testBodypart);
			fail();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
		
		try {
			w= Injuries.getBurnWound(11, testBodypart);
			fail();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}	
	
	@Test
	public void testWoundHealsOverTimeNotResting(){
		int initialSeverity = 3;
		Wound w = Injuries.getSlashingWound(initialSeverity, testBodypart);
		w.passTime(300000, 1, false);
		assertTrue(initialSeverity > w.getSeverity());
	}
	@Test
	public void testWoundHealsOverTimeResting(){
		int initialSeverity = 3;
		Wound w = Injuries.getStabWound(initialSeverity, testBodypart);
		w.passTime(300000, 1, true);
		assertTrue(initialSeverity > w.getSeverity());
	}
	@Test
	public void testWoundHealsOverTimeReflection(){

		int initialSeverity = 6;
		Wound wound = Injuries.getSlashingWound(initialSeverity, testBodypart);
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
		Wound wound1 = Injuries.getStabWound(2, testBodypart);
		Wound wound2 = Injuries.getStabWound(6, testBodypart);
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
		Wound wound1 = Injuries.getSlashingWound(initialSeverity, testBodypart);
		Wound wound2 = Injuries.getSlashingWound(initialSeverity, testBodypart);
		Wound wound3 = Injuries.getSlashingWound(initialSeverity, testBodypart);
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
		Wound wound = Injuries.getSlashingWound(6, testBodypart);
		double[] damage = wound.passTime(10000, 6, false);
		assertTrue(damage[0] > 0);
	}
	@Test
	public void testWoundStopsBleeding(){
		Wound wound = Injuries.getSlashingWound(6, testBodypart);
		wound.passTime(193200, 1, false);
		double[] damage = wound.passTime(1000, 1, false);
		assertEquals(0, damage[0], 0);
	}
	@Test
	public void testMinorWoundDoesNotBleed(){
		Wound wound = Injuries.getSlashingWound(1, testBodypart);
		double[] damage = wound.passTime(1000, 1, false);
		assertEquals(0, damage[0], 0);
	}
	@Test
	public void testWoundBleedsProportionalToTimePassed(){
		Wound wound = Injuries.getSlashingWound(1, testBodypart);
		double damageShort = wound.passTime(1000, 6, false)[0];
		double damageLong = wound.passTime(3000, 6, false)[0];
		assertEquals(damageShort * 3, damageLong, .00001);
	}
}
