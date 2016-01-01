package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import creatures.Creature;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

import inventory.Inventory;
import inventory.InventoryException;
import inventory.ItemContainer;
import items.Armor;
import items.Item;

public class UnitTestItemContainer {

	@Test
	public void testPossitiveEquals() {
		ItemContainer bag1 = Inventory.getStartingBackpack();
		ItemContainer bag2 = new ItemContainer(Inventory.STARTING_BAG_WEIGHT,
				Inventory.STARTING_BAG_SIZE, "backpack");
		assertTrue(bag1.equals(bag2));
		assertTrue(bag2.equals(bag1));
	}
	
	@Test
	public void testNegativeEquals() {
		ItemContainer bag1 = new ItemContainer(10, 100, "backpack1");
		ItemContainer bag2 = new ItemContainer(100, 10, "backpack2");
		assertTrue(! bag1.equals(bag2));
		assertTrue(! bag2.equals(bag1));
	}
	
	@Test
	public void testPossitiveContentsEqual() {
		ItemContainer bag1 = Inventory.getStartingBackpack();
		ItemContainer bag2 = Inventory.getStartingBackpack();
		Item obj1 = new Armor(2, 2, 2, 2, 0, "_slot_", "name", "description");
		bag1.addItem(obj1);
		bag2.addItem(obj1);
		assertTrue(bag1.contentsEqual(bag2));
		assertTrue(bag2.contentsEqual(bag1));
	}
	
	@Test
	public void testNegativeContentsEqual() {
		ItemContainer bag1 = Inventory.getStartingBackpack();
		ItemContainer bag2 = Inventory.getStartingBackpack();
		Item obj1 = new Armor(2, 2, 2, 2, 0, "_slot2_", "name", "description");
		bag1.addItem(obj1);
		assertTrue(! bag1.contentsEqual(bag2));
		assertTrue(! bag2.contentsEqual(bag1));
	}
	
	@Test
	public void testBagStoresItems() {
		ItemContainer bag = Inventory.getStartingBackpack();
		Item mock = new ItemMock();
		bag.addItem(mock);
		try {
			Field f = ItemContainer.class.getDeclaredField("items");
			f.setAccessible(true);
			ArrayList<Item> list = (ArrayList<Item>) f.get(bag);
			assertTrue(list.contains(mock));
			
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			fail("No Such Field Exception");
		} catch (SecurityException e) {
			e.printStackTrace();
			fail("Security Exception");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("IllegalArgumentException");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("IllegalAccessException");
		}
		
	}
	@Test
	public void testBagCanRemoveStoredItems() {
		ItemContainer bag = Inventory.getStartingBackpack();
		Item mock = new ItemMock();
		bag.addItem(mock);
		bag.removeItem(mock);
		try {
			Field f = ItemContainer.class.getDeclaredField("items");
			f.setAccessible(true);
			ArrayList<Item> list = (ArrayList<Item>) f.get(bag);
			assertEquals(0, list.size());
			
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			fail("No Such Field Exception");
		} catch (SecurityException e) {
			e.printStackTrace();
			fail("Security Exception");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("IllegalArgumentException");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("IllegalAccessException");
		}
		
	}
}
class ItemMock extends Item {

	public ItemMock() {
		super(0, 0, 0, 0, 0, "name", "description");
	}

	@Override
	public void useFromInventory(Inventory inv, Creature player)
			throws InventoryException {
		// do nothing
	}

	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player,
			int choice) {
		// do nothing
		
	}

	@Override
	public void saveToFile(PrintWriter writer) {
		// do nothing
	}
}