package unitTests.inventory;

import static org.junit.Assert.*;
import health.Body;
import health.DamageType;
import inventory.InventoryException;
import items.MeleeWeapon;
import items.Weapon;

import java.lang.reflect.Field;
import java.util.Random;

import misc.DeathException;
import misc.DiceRoller;

import org.junit.Before;
import org.junit.Test;

import creatures.Creature;
import testingMothers.CharacterMother;

public class UnitTestWeapon {

	private static final int SEED = 1;
	private Random rand = new Random();
	
	Creature dick, dicksEvilTwin;
	
	@Before
	public void setup()
			throws NoSuchFieldException, SecurityException,
				IllegalArgumentException, IllegalAccessException, InventoryException {
		Field f = DiceRoller.class.getDeclaredField("rand");
		f.setAccessible(true);
		rand = (Random) f.get(null);
		rand.setSeed(SEED);
		
		dick = CharacterMother.getDickDefenderOfLife();
		dicksEvilTwin = CharacterMother.getDickDefenderOfLife();
	}
	
	@Test(expected=DeathException.class)
	public void powerfulWeaponKillsDick() throws DeathException {
		Weapon swordOf1000truths = new MeleeWeapon(1000, 1000, 1000, 1000, 0, "very true sword",
				"this sword looks like it is sharp enough to chop down a cherry tree", MeleeWeapon.ONE_HANDED,
				1000, 1000, 1, DamageType.SLASHING);
		swordOf1000truths.makeAttack(dick, dicksEvilTwin);
		swordOf1000truths.makeAttack(dick, dicksEvilTwin);
		swordOf1000truths.makeAttack(dick, dicksEvilTwin);
		swordOf1000truths.makeAttack(dick, dicksEvilTwin);
	}
	
	@Test
	public void eachHitWounds()
			throws DeathException, NoSuchFieldException, SecurityException,
				IllegalArgumentException, IllegalAccessException {
		Field f = Creature.class.getDeclaredField("body");
		f.setAccessible(true);
		Body bod = (Body) f.get(dicksEvilTwin);
		int intialWounds = bod.countWounds();
		Weapon veryVERYaccurateSword = new MeleeWeapon(100, 100, 100, 10, 0, "_name_", "_desc_", 1, 1,
				100, 1, DamageType.SLASHING);
		veryVERYaccurateSword.makeAttack(dick, dicksEvilTwin);
		veryVERYaccurateSword.makeAttack(dick, dicksEvilTwin);
		veryVERYaccurateSword.makeAttack(dick, dicksEvilTwin);
		assertEquals(intialWounds+3, bod.countWounds());
	}
	
	@Test
	public void printsIdetical()
			throws NoSuchFieldException, SecurityException,
				IllegalArgumentException, IllegalAccessException, InventoryException {
		int[] expected = new int[100];
		for (int i = 0; i < expected.length; i++) {
			expected[i] = rand.nextInt(6);
		}
		setup();
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], rand.nextInt(6));
		}
	}
	@Test
	public void printD6results() {
		// prints the results of the dice rolls that will occur.
		for (int i = 0; i < 10; i++) {
			printLine(rand);
		}
	}
	
	private static void printLine(Random rand) {
		for (int i = 0; i < 4; i++) {
			printChunk(rand);
			System.out.print("- ");
		}
		printChunk(rand);
		System.out.println();
	}
	
	private static void printChunk(Random rand) {
		for (int i = 0; i < 10; i++) {
			System.out.print(rand.nextInt(6) + " ");
		}
	}
}
