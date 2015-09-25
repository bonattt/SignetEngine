//package test;
//
//import static org.junit.Assert.*;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//
//import inventory.Bag;
//import inventory.ItemContainer;
//import items.Item;
//import testMocks.*;
//
//import org.junit.Test;
//
//public class UnitTestBag {
//
//	@Test
//	public void testBagStoresItems() {
//		ItemContainer bag = new Bag();
//		Item mock = new ItemMock();
//		bag.addItem(mock);
//		try {
//			Field f = ItemContainer.class.getDeclaredField("items");
//			f.setAccessible(true);
//			ArrayList<Item> list = (ArrayList<Item>) f.get(bag);
//			assertTrue(list.contains(mock));
//			
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//			fail("No Such Field Exception");
//		} catch (SecurityException e) {
//			e.printStackTrace();
//			fail("Security Exception");
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			fail("IllegalArgumentException");
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//			fail("IllegalAccessException");
//		}
//		
//	}
//	@Test
//	public void testBagCanRemoveStoredItems() {
//		ItemContainer bag = new Bag();
//		Item mock = new ItemMock();
//		bag.addItem(mock);
//		bag.removeItem(mock);
//		try {
//			Field f = ItemContainer.class.getDeclaredField("items");
//			f.setAccessible(true);
//			ArrayList<Item> list = (ArrayList<Item>) f.get(bag);
//			assertEquals(0, list.size());
//			
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//			fail("No Such Field Exception");
//		} catch (SecurityException e) {
//			e.printStackTrace();
//			fail("Security Exception");
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			fail("IllegalArgumentException");
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//			fail("IllegalAccessException");
//		}
//		
//	}
//
//}
