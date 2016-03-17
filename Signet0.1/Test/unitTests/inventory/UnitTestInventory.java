package unitTests.inventory;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import inventory.Gear;
import inventory.Inventory;
import inventory.InventoryException;
import inventory.ItemContainer;
import items.Armor;
import items.Item;
import items.Weapon;
import items.WornItem;

import org.junit.Before;
import org.junit.Test;

import testingMothers.SampleArmor;
import testingMothers.SampleClothing;
import testingMothers.SampleItems;
import testingMothers.SampleWeapons;

public class UnitTestInventory {

	private Inventory inv;
	
	@Before
	public void setup(){
		inv = new Inventory();
	}
	
	@Test
	public void invEqualsSelf() {
		Inventory clone = new Inventory();
		assertEquals(inv, clone);
	}
	
	@Test
	public void carriedWeightCalculatedCorrectly()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException,
				IllegalAccessException, InventoryException {
		int expectedWeight = 0;
		HashMap<String, Armor> armorSlots = new HashMap<String, Armor>();
		HashMap<String, Weapon> weaponSlots = new HashMap<String, Weapon>();
		HashMap<String, WornItem> clothingSlots = new HashMap<String, WornItem>();
		Gear gear = new Gear(weaponSlots, clothingSlots, armorSlots);
		
		Armor armor = SampleArmor.getSampleArmorJacket();
		armorSlots.put("main-armor", null);
		expectedWeight += armor.getWeight();
		
		Weapon weapon = SampleWeapons.getSampleAssaultRifle();
		weaponSlots.put("weapon slot", null);
		expectedWeight += weapon.getWeight();
		
		WornItem clothing = SampleClothing.getSamplePants();
		clothingSlots.put("pants", null);
		expectedWeight += clothing.getWeight();	
		
		gear.addWeapon("weapon slot", weapon);
		gear.equipArmor(armor);
		gear.equipClothing(clothing);
		
		int bagWeight = 1000;
		expectedWeight += bagWeight;
		ItemContainer pack = new ItemContainer(bagWeight, 1000, "whatever");
		Item itm = SampleItems.getMysticThingy();
		assertTrue(pack.addItem(itm));
		expectedWeight += itm.getWeight();
		
		Weapon carriedWeapon = SampleWeapons.getSampleCombatKnife();
		expectedWeight += carriedWeapon.getWeight();
		
		Inventory inv = new Inventory(null, carriedWeapon, gear, pack);
		Field f = Inventory.class.getDeclaredField("carriedWeight");
		f.setAccessible(true);
		f.setInt(inv, 0);
		
		assertEquals(expectedWeight, inv.getCarriedWeight());
	}
	
	@Test
	public void invEqualsSelfWhenPopulated() {
		inv.store(SampleWeapons.getSampleAssaultRifle());
		inv.store(SampleArmor.getSampleArmorJacket());
		inv.store(SampleClothing.getSamplePants());
		Inventory clone = new Inventory();
		clone.store(SampleWeapons.getSampleAssaultRifle());
		clone.store(SampleArmor.getSampleArmorJacket());
		clone.store(SampleClothing.getSamplePants());
		assertEquals(inv, clone);
	}
	
	@Test
	public void addingItemIncreasesWeight(){
		int weightInitial = inv.getCarriedWeight();
		Armor armor = SampleArmor.getSampleHelmet();
		inv.store(armor);
		assertEquals(weightInitial + armor.getWeight(), inv.getCarriedWeight());
	}
	
	@Test
	public void discardingItemRemovesIt(){
		ArrayList<Item> itemsStored = null;
		try {
			Field field = Inventory.class.getDeclaredField("backpack");
			field.setAccessible(true);
			ItemContainer bag = (ItemContainer) field.get(inv);
			field = ItemContainer.class.getDeclaredField("items");
			field.setAccessible(true);
			itemsStored = (ArrayList<Item>) field.get(bag);
		} catch (NoSuchFieldException e) {
			fail("threw an excaption");
		} catch (SecurityException e) {
			fail("threw an excaption");
		} catch (IllegalArgumentException e) {
			fail("threw an excaption");
		} catch (IllegalAccessException e) {
			fail("threw an excaption");
		}
		Item item = SampleItems.getMysticThingy();
		itemsStored.add(item);
		inv.discardItem(item);
		assertFalse(itemsStored.contains(item));
	}
	@Test
	public void storingItemAddsItem(){
		ArrayList<Item> itemsStored = null;
		try {
			Field field = Inventory.class.getDeclaredField("backpack");
			field.setAccessible(true);
			ItemContainer bag = (ItemContainer) field.get(inv);
			field = ItemContainer.class.getDeclaredField("items");
			field.setAccessible(true);
			itemsStored = (ArrayList<Item>) field.get(bag);
		} catch (NoSuchFieldException e) {
			fail("threw an excaption");
		} catch (SecurityException e) {
			fail("threw an excaption");
		} catch (IllegalArgumentException e) {
			fail("threw an excaption");
		} catch (IllegalAccessException e) {
			fail("threw an excaption");
		}
		Item item = SampleItems.getMysticThingy();
		itemsStored.add(item);
		assertTrue(itemsStored.contains(item));
	}
	@Test
	public void discardingItemReducesItemCount(){
		ArrayList<Item> itemsStored = null;
		try {
			Field field = Inventory.class.getDeclaredField("backpack");
			field.setAccessible(true);
			ItemContainer bag = (ItemContainer) field.get(inv);
			field = ItemContainer.class.getDeclaredField("items");
			field.setAccessible(true);
			itemsStored = (ArrayList<Item>) field.get(bag);
		} catch (NoSuchFieldException e) {
			fail("threw an excaption");
		} catch (SecurityException e) {
			fail("threw an excaption");
		} catch (IllegalArgumentException e) {
			fail("threw an excaption");
		} catch (IllegalAccessException e) {
			fail("threw an excaption");
		}
		Item item = SampleItems.getMysticThingy();
		itemsStored.add(item);
		int initialCount = itemsStored.size();
		inv.discardItem(item);
		assertEquals(initialCount - 1,itemsStored.size());
	}
	@Test
	public void addingItemIncreasesItemCount(){
		ArrayList<Item> itemsStored = null;
		try {
			Field field = Inventory.class.getDeclaredField("backpack");
			field.setAccessible(true);
			ItemContainer bag = (ItemContainer) field.get(inv);
			field = ItemContainer.class.getDeclaredField("items");
			field.setAccessible(true);
			itemsStored = (ArrayList<Item>) field.get(bag);
		} catch (NoSuchFieldException e) {
			fail("threw an excaption");
		} catch (SecurityException e) {
			fail("threw an excaption");
		} catch (IllegalArgumentException e) {
			fail("threw an excaption");
		} catch (IllegalAccessException e) {
			fail("threw an excaption");
		}
		Item item = SampleItems.getMysticThingy();
		int initialCount = itemsStored.size();
		itemsStored.add(item);
		assertEquals(initialCount + 1,itemsStored.size());
	}
	@Test
	public void discardingItemReducesWeight(){
		Item item = SampleItems.getMysticThingy();
		inv.store(item);
		int initialWeight = inv.getCarriedWeight();
		inv.discardItem(item);
		assertEquals(initialWeight - item.getWeight(), inv.getCarriedWeight());
	}
	
}
