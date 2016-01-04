package unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import testingMothers.SampleArmor;
import testingMothers.SampleWeapons;
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

	private ItemContainer pack;
	private ArrayList<Item> items;
	private int baseWeight = 10;
	private int baseSize = 1000;
	private String name = "test_backpack";
	private Item item;
	
	@Before
	public void setup() {
		item = SampleWeapons.getSamplePistol();
		items = new ArrayList<Item>();
		items.add(SampleWeapons.getSampleCombatKnife());
		pack = new ItemContainer(baseWeight, baseSize, name, items);
	}
	@Test
	public void weightInitializesCorrectly() {
		int initialWeight = pack.getWeight();
		pack.updateWeight();
		assertEquals(initialWeight, pack.getWeight());
	}
	
	@Test
	public void testPossitiveEquals() {
		ItemContainer bag1 = pack;
		setup();
		assertEquals(pack, bag1);
	}
	
	@Test
	public void testDiffernetStatsNotEqual() {
		ItemContainer bag1 = new ItemContainer(10, 100, "backpack1");
		ItemContainer bag2 = new ItemContainer(100, 10, "backpack2");
		assertNotEquals(bag1, bag2);
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
	public void removeReturnsTrueOnSuccessfulRemove() {
		boolean result = pack.removeItem(SampleArmor.getSampleArmorJacket());
		assertFalse(result);
	}
	@Test
	public void removeReturnsFalseOnFailedRemove() {
		pack.addItem(item);
		boolean result = pack.removeItem(item);
		assertTrue(result);
	}
	@Test
	public void addReturnsTrueOnSuccess() {
		boolean result = pack.addItem(item);
		assertTrue(result);
	}
	@Test
	public void addReturnsFalseOnFail() {
		pack = new ItemContainer(10, 100, "_verySmallPack_");
		int size = 200;
		item = new Armor(size, 1, 1, 1, 1, "slot", "name", "description");
		boolean result = pack.addItem(item);
		assertFalse(result);
	}
	@Test
	public void successfulRemoveReducesWeight() {
		pack.addItem(item);
		int initialWeight = pack.getWeight();
		pack.removeItem(item);
		assertEquals(initialWeight - item.getWeight(), pack.getWeight());
	}
	@Test
	public void failedRemoveDoesNotChangeWeight() {
		int initialWeight = pack.getWeight();
		pack.removeItem(item);
		assertEquals(initialWeight, pack.getWeight());
	}
	@Test
	public void successfulAddIncreasesWeight() {
		int initialWeight = pack.getWeight();
		pack.addItem(item);
		assertEquals(initialWeight + item.getWeight(), pack.getWeight());
	}
	@Test
	public void failedAddDoesNotChangeWeight() {
		pack = new ItemContainer(10, 100, "_verySmallPack_");
		int initialWeight = pack.getWeight();
		int size = 200;
		item = new Armor(size, 1, 1, 1, 1, "slot", "name", "description");
		pack.addItem(item);
		assertEquals(initialWeight, pack.getWeight());
	}
	
	@Test
	public void addItemFailsWithNoSpace() {
		pack = new ItemContainer(10, 100, "_verySmallPack_");
		int size = 200;
		item = new Armor(size, 1, 1, 1, 1, "slot", "name", "description");
		boolean result = pack.addItem(item);
		assertFalse(result);
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