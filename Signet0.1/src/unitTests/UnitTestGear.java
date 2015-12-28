package unitTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import sampleItems.*;
import inventory.Gear;
import items.Armor;
import items.Weapon;
import items.WornItem;

import org.junit.Before;
import org.junit.Test;

public class UnitTestGear {

	private Gear gear;
	
	private static final String WEAPON1 = "slot-1";	
	private static final String WEAPON2 = "slot-2";
	private static final String ARMOR1 = "armor";
	private static final String CLOTHING1 = "shirt";
	private static final String CLOTHING2 = "pants";
	
	@Before
	public void setup() {
		HashMap<String, Weapon> weaponSlots = new HashMap<String, Weapon>();
		HashMap<String, WornItem> clothingSlots = new HashMap<String, WornItem>();
		HashMap<String, Armor> armorSlots = new HashMap<String, Armor>();
		weaponSlots.put("boot-holster", null);
		weaponSlots.put("hip-holster", null);
		clothingSlots.put("shirt", null);
		clothingSlots.put("pants", null);
		clothingSlots.put("hat", null);
		armorSlots.put("main armor", null);
		armorSlots.put("helmet", null);

		gear = new Gear(weaponSlots, clothingSlots, armorSlots);
		gear.addWeapon(WEAPON1, SampleWeapons.getSampleCombatKnife());
		gear.addWeapon(WEAPON2, SampleWeapons.getSamplePistol());
		gear.equipClothing(new WornItem(100, 25, 10, 0, 0, CLOTHING1, "cotton shirt", "TODO"));
		gear.equipClothing(new WornItem(125, 35, 15, 0, 0, CLOTHING2, "cotton pants", "2DO"));
		gear.equipArmor(new Armor(200, 100, 50, 5, 0, ARMOR1, "armor", "this will protect you from yourself"));
	}
	
	@Test
	public void gearEqualsItself() {
		Gear gear1 = gear;
		setup(); // sets gear field to a new, identical Gear object
		assertEquals(gear, gear1);
	}
	@Test
	public void notEqualWithDifferentArmor() {
		Gear gear1 = gear;
		setup(); // sets gear field to a new, identical Gear object
		gear1.equipArmor(new Armor(1, 1, 1, 1, 1, ARMOR1, "new armor", "doesn't look too sturdy"));
		assertNotEquals(gear, gear1);
	}
	@Test
	public void notEqualWithDifferentWeapon() {
		Gear gear1 = gear;
		setup(); // sets gear field to a new, identical Gear object
		gear1.addWeapon(WEAPON2, SampleWeapons.getSamplePistol());
		assertNotEquals(gear, gear1);
	}
	@Test
	public void notEqualWithDifferentClothing() {
		Gear gear1 = gear;
		setup(); // sets gear field to a new, identical Gear object
		gear1.equipClothing(new WornItem(1, 1, 1, 1, 1, CLOTHING2, "anything", "whatever"));
		assertNotEquals(gear, gear1);
	}
	

}
