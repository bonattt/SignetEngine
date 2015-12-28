package unitTests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
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
	public void addWeaponToNonexistantSlot() {
		boolean result = gear.addWeapon("aeoifnse;oinf;oiesnf", SampleWeapons.getSampleSword());
		assertFalse(result);
	}

	@Test
	public void addArmorToNonexistantSlot() {
		boolean result = gear.equipArmor(new Armor(1, 1, 1, 1, 1, "powiejfosienjfo;", "whatever", "none"));
		assertFalse(result);
	}
	
	@Test
	public void addClothingToNonexistantSlot() {
		boolean result = gear.equipClothing(new WornItem(1, 1, 1, 1, 1, "powiejfosienjfo;", "whatever", "none"));
		assertFalse(result);
	}
	
	@Test
	public void removeWeaponReturnsEquippedWeapon() {
		Weapon weapon = SampleWeapons.getSamplePistol();
		gear.addWeapon(WEAPON1, weapon);
		assertTrue(gear.removeWeapon(WEAPON1) == weapon);
	}
	@Test
	public void removeWeaponSetsToNull() {
		Weapon weapon = SampleWeapons.getSamplePistol();
		gear.addWeapon(WEAPON1, weapon);
		assertTrue(gear.getWeapon(WEAPON1) == null);
	}
	
	@Test
	public void removeClothingReturnsEquippedClothing() {
		WornItem item = new WornItem(1, 1, 1, 1, 1, CLOTHING1, "_name_", "desc");
		gear.equipClothing(item);
		gear.removeClothing(CLOTHING1);
		assertTrue(gear.getClothing(CLOTHING1) == null);
	}
	
	@Test
	public void removeArmorReturnsEquippedArmor() {
		Armor item = new Armor(1, 1, 1, 1, 1, ARMOR1, "_name_", "desc");
		gear.equipArmor(item);
		assertTrue(gear.removeArmor(ARMOR1) == item);
	}
	@Test
	public void removeArmorSetsToNull() {
		Armor item = new Armor(1, 1, 1, 1, 1, ARMOR1, "_name_", "desc");
		gear.equipArmor(item);
		gear.removeArmor(ARMOR1);
		assertTrue(gear.getArmor(ARMOR1) == null);
	}
	
	@Test
	public void addWeaponEquipsWeapon() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		boolean result;
		
		Field f = Gear.class.getDeclaredField("weaponsCarried");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		HashMap<String, Weapon> obj = (HashMap<String, Weapon>) f.get(gear);
		
		result = gear.addWeapon(WEAPON1, null);
		assertTrue(result);
		assertTrue(obj.get(WEAPON1) == null);
		result = gear.addWeapon(WEAPON1, SampleWeapons.getSamplePistol());
		assertTrue(result);
		assertTrue(obj.get(WEAPON1) != null);
	}

	@Test
	public void addClothingEquipsClothing() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		boolean result;
		
		Field f = Gear.class.getDeclaredField("clothingWorn");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		HashMap<String, WornItem> obj = (HashMap<String, WornItem>) f.get(gear);
		
		gear.removeClothing(CLOTHING1);
		WornItem item = new WornItem(1, 1, 1, 1, 1, CLOTHING1, "_name_", "_desc_");
		result = gear.equipClothing(item);
		assertTrue(result);
		assertTrue(obj.get(CLOTHING1) == item);
	}
	
	@Test
	public void addArmorEquipsArmor() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		boolean result;
		
		Field f = Gear.class.getDeclaredField("armorEquipped");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		HashMap<String, Armor> obj = (HashMap<String, Armor>) f.get(gear);
		
		gear.removeArmor(ARMOR1);
		Armor armor = new Armor(1, 1, 1, 1, 1, ARMOR1, "armor", "desc");
		result = gear.equipArmor(armor);
		assertTrue(result);
		assertTrue(obj.get(ARMOR1) == armor);
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
