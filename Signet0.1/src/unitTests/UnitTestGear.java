package unitTests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashMap;

import testingMothers.SampleWeapons;
import inventory.Gear;
import inventory.InventoryException;
import items.Armor;
import items.Weapon;
import items.WornItem;

import org.junit.Before;
import org.junit.Test;

public class UnitTestGear {

	private Gear gear;
	
	private static final String WEAPON1 = "hip-holster";	
	private static final String WEAPON2 = "boot-holster";
	private static final String ARMOR1 = "main-armor";
	private static final String ARMOR2 = "helmet";
	private static final String CLOTHING1 = "shirt";
	private static final String CLOTHING2 = "pants";
	
	@Before
	public void setup() throws InventoryException {
		HashMap<String, Weapon> weaponSlots = new HashMap<String, Weapon>();
		HashMap<String, WornItem> clothingSlots = new HashMap<String, WornItem>();
		HashMap<String, Armor> armorSlots = new HashMap<String, Armor>();
		weaponSlots.put(WEAPON1, null);
		weaponSlots.put(WEAPON2, null);
		clothingSlots.put(CLOTHING1, null);
		clothingSlots.put(CLOTHING2, null);
		armorSlots.put(ARMOR1, null);
		armorSlots.put(ARMOR2, null);

		gear = new Gear(weaponSlots, clothingSlots, armorSlots);
		gear.addWeapon(WEAPON1, SampleWeapons.getSampleCombatKnife());
		gear.addWeapon(WEAPON2, SampleWeapons.getSamplePistol());
		gear.equipClothing(new WornItem(100, 25, 10, 0, 0, CLOTHING1, "cotton shirt", "TODO"));
		gear.equipClothing(new WornItem(125, 35, 15, 0, 0, CLOTHING2, "cotton pants", "2DO"));
		gear.equipArmor(new Armor(200, 100, 50, 5, 0, ARMOR1, "armor", "this will protect you from yourself"));
	}
	@Test(expected=InventoryException.class)
	public void addWeaponToNonexistantSlot() throws InventoryException {
		gear.addWeapon("aeoifnse;oinf;oiesnf", SampleWeapons.getSampleSword());
	}

	@Test(expected=InventoryException.class)
	public void addArmorToNonexistantSlot() throws InventoryException {
		gear.equipArmor(new Armor(1, 1, 1, 1, 1, "powiejfosienjfo;", "whatever", "none"));
	}
	
	@Test(expected=InventoryException.class)
	public void addClothingToNonexistantSlot() throws InventoryException {
		gear.equipClothing(new WornItem(1, 1, 1, 1, 1, "powiejfosienjfo;", "whatever", "none"));
//		assertFalse(result);
	}
	
	@Test
	public void removeWeaponReturnsEquippedWeapon() throws InventoryException  {
		gear.removeWeapon(WEAPON1);
		Weapon weapon = SampleWeapons.getSamplePistol();
		gear.addWeapon(WEAPON1, weapon);
		assertTrue(gear.removeWeapon(WEAPON1) == weapon);
	}
	@Test
	public void removeWeaponSetsToNull()  throws InventoryException {
		Weapon weapon = SampleWeapons.getSamplePistol();
		gear.addWeapon(WEAPON1, weapon);
		gear.removeWeapon(WEAPON1);
		Weapon result = gear.getWeapon(WEAPON1);
		assertTrue(result == null);
	}
	
	@Test
	public void removeClothingReturnsEquippedClothing() throws InventoryException {
		WornItem item = new WornItem(1, 1, 1, 1, 1, CLOTHING1, "_name_", "desc");
		gear.equipClothing(item);
		gear.removeClothing(CLOTHING1);
		assertTrue(gear.getClothing(CLOTHING1) == null);
	}
	
	@Test
	public void removeArmorReturnsEquippedArmor() throws InventoryException {
		gear.removeArmor(ARMOR1);
		Armor item = new Armor(1, 1, 1, 1, 1, ARMOR1, "_name_", "desc");
		gear.equipArmor(item);
		Armor result = gear.removeArmor(ARMOR1);
		assertTrue(result == item);
	}
	@Test
	public void removeArmorSetsToNull() throws InventoryException {
		Armor item = new Armor(1, 1, 1, 1, 1, ARMOR1, "_name_", "desc");
		gear.equipArmor(item);
		gear.removeArmor(ARMOR1);
		assertTrue(gear.getArmor(ARMOR1) == null);
	}
	
	@Test
	public void addWeaponEquipsWeapon() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InventoryException {
		boolean result;
		
		Field f = Gear.class.getDeclaredField("weaponsCarried");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		HashMap<String, Weapon> obj = (HashMap<String, Weapon>) f.get(gear);
		
		gear.removeWeapon(WEAPON1);
		assertTrue(obj.get(WEAPON1) == null);
		result = gear.addWeapon(WEAPON1, SampleWeapons.getSamplePistol());
		assertTrue(result);
		assertTrue(obj.get(WEAPON1) != null);
	}

	@Test
	public void addClothingEquipsClothing() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InventoryException {
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
			IllegalArgumentException, IllegalAccessException, InventoryException {
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
	public void gearEqualsItself()  throws InventoryException {
		Gear gear1 = gear;
		setup(); // sets gear field to a new, identical Gear object
		assertEquals(gear, gear1);
	}
	@Test
	public void notEqualWithDifferentArmor()  throws InventoryException {
		Gear gear1 = gear;
		gear1.removeArmor(ARMOR1);
		gear1.equipArmor(new Armor(1, 1, 1, 1, 1, ARMOR1, "new armor", "doesn't look too sturdy"));
		setup(); // sets gear field to a new, identical Gear object
		assertNotEquals(gear, gear1);
	}
	@Test
	public void notEqualWithDifferentWeapon()  throws InventoryException {
		Gear gear1 = gear;
		gear1.removeWeapon(WEAPON1);
		gear1.addWeapon(WEAPON2, SampleWeapons.getSamplePistol());
		setup(); // sets gear field to a new, identical Gear object
		assertNotEquals(gear, gear1);
	}
	@Test
	public void notEqualWithDifferentClothing()  throws InventoryException {
		Gear gear1 = gear;
		gear1.removeClothing(CLOTHING2);
		gear1.equipClothing(new WornItem(1, 1, 1, 1, 1, CLOTHING2, "anything", "whatever"));
		setup(); // sets gear field to a new, identical Gear object
		assertNotEquals(gear, gear1);
	}
	

}
