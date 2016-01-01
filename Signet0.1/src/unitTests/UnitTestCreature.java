package unitTests;

import static org.junit.Assert.*;
import health.Body;
import inventory.InventoryException;

import java.lang.reflect.Field;

import misc.DeathException;

import org.junit.Before;
import org.junit.Test;

import creatures.Creature;
import testingMothers.CharacterMother;
import testingMothers.SampleArmor;
import testingMothers.SampleClothing;
import testingMothers.SampleThingy;

public class UnitTestCreature {

	private Creature player;
	
	@Before
	public void setup() throws InventoryException{
		player = CharacterMother.getDickDefenderOfLife();
	}
	
	@Test
	public void testTravelingCreatesMoreFatigueWhenMoreWeightIsCarried() {
		int travelTime = 3000;
		int exhaustionFactor = 1;
		double fatigue1 = 0;
		double fatigue2 = 0;
		try {
			Field field = Creature.class.getDeclaredField("body");
			field.setAccessible(true);
			Body bod = (Body) field.get(player);
			player.travel(travelTime, exhaustionFactor);
			field = Body.class.getDeclaredField("fatigueEffective");
			field.setAccessible(true);
			fatigue1 = field.getDouble(bod);
			storyManyItems(player);
			bod.healFatigue(1000);
			player.travel(travelTime, exhaustionFactor);
			fatigue2 = field.getDouble(bod);
		} catch (DeathException e) {
			fail("the player died.");
		} catch (IllegalArgumentException e) {
			fail("the player died.");
		} catch (IllegalAccessException e) {
			fail("the player died.");
		} catch (NoSuchFieldException e) {
			fail("the player died.");
		} catch (SecurityException e) {
			fail("the player died.");
		}
		assertTrue(fatigue2 > fatigue1);
	}
	@Test
	public void testTravelingCreatesMoreFatigueWhenTravelingForLonger() {
		int travelTime = 3000;
		int exhaustionFactor = 1;
		double fatigue1 = 0;
		double fatigue2 = 0;
		try {
			Field field = Creature.class.getDeclaredField("body");
			field.setAccessible(true);
			Body bod = (Body) field.get(player);
			player.travel(travelTime, exhaustionFactor);
			field = Body.class.getDeclaredField("fatigueEffective");
			field.setAccessible(true);
			fatigue1 = field.getDouble(bod);
			bod.healFatigue(1000);
			player.travel(travelTime * 3, exhaustionFactor);
			fatigue2 = field.getDouble(bod);
		} catch (DeathException e) {
			fail("the player died.");
		} catch (IllegalArgumentException e) {
			fail("the player died.");
		} catch (IllegalAccessException e) {
			fail("the player died.");
		} catch (NoSuchFieldException e) {
			fail("the player died.");
		} catch (SecurityException e) {
			fail("the player died.");
		}
		assertTrue(fatigue2 > fatigue1);
	
	
	}
	private static void storyManyItems(Creature player){
		for (int i = 0; i < 5; i++) {
			player.getInventory().store(SampleArmor.getSampleArmorJacket());
			player.getInventory().store(SampleArmor.getSampleHelmet());
			player.getInventory().store(SampleClothing.getSamplePants());
			player.getInventory().store(SampleClothing.getSampleShirt());
			player.getInventory().store(new SampleThingy());
		}
	}
}
