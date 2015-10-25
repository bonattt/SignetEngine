package unitTests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import injuries.SlashingWound;
import items.Bandage;
import items.Ointment;
import health.BodyPart;
import health.Wound;

import org.junit.Before;
import org.junit.Test;

import bodyparts.Arm;

public class UnitTestWoundMedication {

	BodyPart genericBodypart;
	
	@Before
	public void setup(){
		genericBodypart = new Arm("arm");
	}
	
	@Test
	public void testWoundHealsFasterWithBandage() {
		Wound wound1 = new SlashingWound(6, genericBodypart);
		Wound wound2 = new SlashingWound(6, genericBodypart);
		int timePassed = 3000;
		double healingFactor = 1;
		wound1.passTime(timePassed, healingFactor, false);
		wound2.passTime(timePassed, healingFactor, false);
		wound2.setBandage(new Bandage(1.35, 1.35, wound2));
		String field = "recoveryClock";
		int recovery1 = 0;
		int recovery2 = 0;
		try {
			Field field1 = Wound.class.getDeclaredField(field);
			field1.setAccessible(true);
			recovery1 = (Integer) field1.get(wound1);
			recovery2 = (Integer) field1.get(wound2);
		} catch (IllegalArgumentException e) {
			fail("threw illegal arguments exception");
		} catch (IllegalAccessException e) {
			fail("threw illegal access exception");
		} catch (NoSuchFieldException e) {
			fail("threw no such field exception");
		} catch (SecurityException e) {
			fail("threw security exception");
		}
		assertTrue(((recovery2 > recovery1) && (wound2.getSeverity() == wound1.getSeverity())) || (wound2.getSeverity() < wound1.getSeverity()));
	
	}
	@Test
	public void testWoundHealsFasterWithOintment() {
		Wound wound1 = new SlashingWound(6, genericBodypart);
		Wound wound2 = new SlashingWound(6, genericBodypart);
		int timePassed = 3000;
		double healingFactor = 1;
		wound1.passTime(timePassed, healingFactor, false);
		wound2.passTime(timePassed, healingFactor, false);
		wound2.setTreatment(new Ointment(1.35, 1.35, wound2));
		String field = "recoveryClock";
		int recovery1 = 0;
		int recovery2 = 0;
		try {
			Field field1 = Wound.class.getDeclaredField(field);
			field1.setAccessible(true);
			recovery1 = (Integer) field1.get(wound1);
			recovery2 = (Integer) field1.get(wound2);
		} catch (IllegalArgumentException e) {
			fail("threw illegal arguments exception");
		} catch (IllegalAccessException e) {
			fail("threw illegal access exception");
		} catch (NoSuchFieldException e) {
			fail("threw no such field exception");
		} catch (SecurityException e) {
			fail("threw security exception");
		}
		assertTrue(((recovery2 > recovery1) && (wound2.getSeverity() == wound1.getSeverity())) || (wound2.getSeverity() < wound1.getSeverity()));
	}
}