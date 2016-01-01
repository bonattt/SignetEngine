package unitTests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import inventory.Inventory;
import inventory.ItemContainer;
import items.Item;

import org.junit.Before;
import org.junit.Test;

import testingMothers.SampleArmor;
import testingMothers.SampleClothing;
import testingMothers.SampleThingy;
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
		inv.store(SampleArmor.getSampleHelmet());
		assertTrue(weightInitial < inv.getCarriedWeight());
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
		Item item = new SampleThingy();
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
		Item item = new SampleThingy();
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
		Item item = new SampleThingy();
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
		Item item = new SampleThingy();
		int initialCount = itemsStored.size();
		itemsStored.add(item);
		assertEquals(initialCount + 1,itemsStored.size());
	}
	@Test
	public void discardingItemReducesWeight(){
		Item item = new SampleThingy();
		inv.store(item);
		int initialWeight = inv.getCarriedWeight();
		inv.discardItem(item);
		assertEquals(initialWeight - item.getWeight(), inv.getCarriedWeight());
	}
	
}
