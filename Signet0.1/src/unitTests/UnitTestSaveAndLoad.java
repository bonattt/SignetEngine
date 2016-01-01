package unitTests;

import static org.junit.Assert.*;
import health.Body;
import health.BodyPart;
import health.Injuries;
import health.Wound;
import inventory.Gear;
import inventory.Inventory;
import inventory.InventoryException;
import inventory.ItemContainer;
import items.Armor;
import items.Bandage;
import items.Item;
import items.LightSource;
import items.Ointment;
import items.MeleeWeapon;
import items.RangedWeapon;
import items.Weapon;
import items.WornItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.Test;

import testingMothers.CharacterMother;
import testingMothers.SampleArmor;
import testingMothers.SampleClothing;
import testingMothers.SampleWeapons;
import creatures.Creature;
import creatures.PlayerCharacter;
import creatures.Skill;
import creatures.SkillTags;

public class UnitTestSaveAndLoad {

	private static final String filePath = "src/unitTests/unitTestSaveFile.txt";
	
	static {
		System.out.println("untested classes:\n\thealth.Infection\n\tcreatures.Trait\n\t"
				+ "creatures.PlayerCharacter\n\tnpc.*\n\tenvironment.*\n");
	}
	
	@Test
	public void testSingleSkillLoads() throws FileNotFoundException {
			String name = "java programing";
			int id = Integer.MAX_VALUE;
			String[] linkedAtrb = new String[]{"Str"};
			SkillTags[] tags = new SkillTags[]{SkillTags.arms, SkillTags.hands, SkillTags.combat};
			int ranks = 3;
			int exp = 100;
			
			Skill saved = new Skill(name, id, ranks, linkedAtrb, tags);
			saved.gainExperience(exp);
			
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			Skill loaded = Skill.loadFromFile(scanner);
			scanner.close();
			
			assertEquals(saved, loaded);
	}
	
	@Test
	public void testPlayerSkillsSaveLoad() throws FileNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchFieldException, InventoryException {
		
		PlayerCharacter saved = CharacterMother.getDickDefenderOfLife();
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveSkills(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		Method method = PlayerCharacter.class.getDeclaredMethod("loadAlpha0_1skills", scanner.getClass());
		method.setAccessible(true);
		@SuppressWarnings("unchecked")
		HashMap<String, Skill> loadedMap = (HashMap<String, Skill>) method.invoke(null, scanner);
		scanner.close();
		
		Field field = Creature.class.getDeclaredField("skills");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		HashMap<String, Skill> savedMap = (HashMap<String, Skill>) field.get(saved);
		
		for(String key : loadedMap.keySet()) {
			assertEquals(savedMap.get(key), loadedMap.get(key));
		}
		assertEquals(savedMap.size(), loadedMap.size());
	}
	
	@Test
	public void testPlayerDamageMultipliersLoad() 
			throws FileNotFoundException, NoSuchMethodException,
				SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
				NoSuchFieldException, InventoryException {
		
		PlayerCharacter saved = CharacterMother.getDickDefenderOfLife();
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveDamageMultipliers(writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		
		Method method = PlayerCharacter.class.getDeclaredMethod("loadAlpha0_1damageMultipliers", scanner.getClass());
		method.setAccessible(true);
		Object loadedObj = method.invoke(null, scanner);
		@SuppressWarnings("unchecked")
		HashMap<String, Integer> loadedMap = (HashMap<String, Integer>) loadedObj;
		scanner.close();
		
		Field field = Creature.class.getDeclaredField("damageMultipliers");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		HashMap<String, Integer> savedMap = (HashMap<String, Integer>) field.get(saved);
		
		for (String key : savedMap.keySet()) {
			assertEquals(savedMap.get(key), loadedMap.get(key));
		}
		assertEquals(savedMap.size(), loadedMap.size());
	}
	
	
	
	@Test
	public void testPlayerStatsSaveLoad() throws FileNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InventoryException {
		PlayerCharacter saved = CharacterMother.getDickDefenderOfLife();
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveStats(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		Method method = PlayerCharacter.class.getDeclaredMethod("loadAlpha0_1stats", scanner.getClass());
		method.setAccessible(true);
		Object loadedObj = method.invoke(null, scanner);
		@SuppressWarnings("unchecked")
		HashMap<String, Integer> loadedMap = (HashMap<String, Integer>) loadedObj;
		scanner.close();
		
		for (String key : loadedMap.keySet()) {
			assertEquals(saved.getStat(key), (int) loadedMap.get(key));
		}
		assertEquals(Creature.ABILITIES.length, loadedMap.size());
	}

	
	@Test
	public void testBodypartUninjuredSaveLoad() throws FileNotFoundException {
		BodyPart saved = new BodyPart("limb", 1.5, .75);
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();

		Scanner scanner = new Scanner(new File(filePath));
		BodyPart loaded = BodyPart.loadAlpha0_1(scanner);
		scanner.close();
		
		assertEquals(saved, loaded);
	}	

	@Test
	public void testWoundSaveLoad() throws FileNotFoundException {
		BodyPart testBodypart = new BodyPart("_body_", 1, 1);
		Wound saved = Injuries.getSlashingWound(3, testBodypart);
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		Wound loaded = Wound.loadAlpha1_0fromFile(scanner, testBodypart);
		scanner.close();
		
		assertEquals(saved, loaded);
	}
	@Test
	public void testItem_WornItemSaveLoad() {
		WornItem saved = new WornItem(10, 10, 20, 0, 0, "_slot_", "clothing", "nice clothing");
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			String itemType = scanner.nextLine();
			assertEquals("worn item", itemType);
			WornItem loaded = (WornItem) Item.loadAlpha0_1(scanner, itemType);
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			assertTrue(loaded.equals(saved));
			
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		}
	}

	@Test
	public void testItemContainerSaveLoad() {
		try {
			ItemContainer saved = Inventory.getStartingBackpack();
			MeleeWeapon wpn1 = new MeleeWeapon(1, 1, 1, 1, "item_name", "item_description");
			MeleeWeapon wpn2 = new MeleeWeapon(2, 2, 2, 2, "item_name", "item_description");
			WornItem clothing = new WornItem(10, 10, 10, 2, 0, "_slot_", "item_name", "item_description");
			
			saved.addItem(wpn1);
			saved.addItem(wpn2);
			saved.addItem(clothing);
			
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			ItemContainer loaded = ItemContainer.loadAlpha0_1(scanner);
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			assertTrue(loaded.equals(saved));
			
		} catch (FileNotFoundException e) {
			fail("threw exception");
		} catch (SecurityException e) {
			fail("threw exception");
		} catch (IllegalArgumentException e) {
			fail("threw exception");
		}	
	}

	@Test
	public void testItemContainerContainsCorrectItemsAfterSaveLoad() {
		try {

			ItemContainer saved = Inventory.getStartingBackpack();
			MeleeWeapon wpn1 = new MeleeWeapon(1, 1, 1, 1, "item_name", "item_description");
			MeleeWeapon wpn2 = new MeleeWeapon(2, 2, 2, 2, "item_name", "item_description");
			WornItem clothing = new WornItem(10, 10, 10, 2, 0, "_slot_", "item_name", "item_description");
			
			saved.addItem(wpn1);
			saved.addItem(wpn2);
			saved.addItem(clothing);
			
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			ItemContainer loaded = ItemContainer.loadAlpha0_1(scanner);
			scanner.close();
			
			Field field = ItemContainer.class.getDeclaredField("items");
			field.setAccessible(true);
			@SuppressWarnings("unchecked")
			ArrayList<Item> savedItems = ((ArrayList<Item>) field.get(saved));
			@SuppressWarnings("unchecked")
			ArrayList<Item> loadedItems = ((ArrayList<Item>) field.get(loaded));
			
			assertEquals(savedItems.size(), loadedItems.size());
			for (int i = 0; i < savedItems.size(); i++){
				assertTrue(savedItems.get(i).equals(loadedItems.get(i)));
				assertTrue(loadedItems.get(i).equals(savedItems.get(i)));
			}
			
		} catch (FileNotFoundException e) {
			fail("threw exception");
		} catch (NoSuchFieldException e) {
			fail("threw exception");
		} catch (SecurityException e) {
			fail("threw exception");
		} catch (IllegalArgumentException e) {
			fail("threw exception");
		} catch (IllegalAccessException e) {
			fail("threw exception");
		} catch (ClassCastException e) {
			fail("threw exception");
		}
	}
	@Test
	public void testItem_ArmorSaveLoad() {
		try {
			Armor saved = new Armor(1000, 1000, 25, 10, 0, "armor_slot", "armor_name", "armor_description");
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			Armor loaded = (Armor) Item.loadAlpha0_1(scanner);
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			assertEquals(saved.description(), loaded.description());
			
		} catch (FileNotFoundException e) {
			fail("an exception was thrown.");
		}
	}
	@Test
	public void testArmor2() {
		try {
			Armor saved = new Armor(100, 100, 15, 5, 1, "armor_slot", "armor_name", "this armor is shiney");
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			Armor loaded = (Armor) Item.loadAlpha0_1(scanner);
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			assertEquals(saved.description(), loaded.description());
			
		} catch (FileNotFoundException e) {
			fail("an exception was thrown.");
		}
	}
	@Test
	public void testItem_BandageSaveLoad() {
		try {
			Bandage saved = new Bandage("bandage", "this is for when your arm gets chopped off",
					2.5, 2.5, 2.5);
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			Bandage loaded = (Bandage) Item.loadAlpha0_1(scanner);			
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			
		} catch (FileNotFoundException e) {
			fail("an exception was thrown.");
		}
	}
	@Test
	public void testItem_OintmentSaveLoad() {
		try {
			Ointment saved = new Ointment();
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			Ointment loaded = (Ointment) Item.loadAlpha0_1(scanner);			
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			assertTrue(loaded.equals(saved));
			
		} catch (FileNotFoundException e) {
			fail("an exception was thrown.");
		}
	}
	@Test
	public void testItem_RangedWeaponSaveLoad() throws FileNotFoundException {
		RangedWeapon saved = new RangedWeapon("weapon","this shoots bullets",
				100, 100, 100, 10, 0, 5, 5, 20, RangedWeapon.ONE_HANDED, 10, 10);
		
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		RangedWeapon loaded = (RangedWeapon) Item.loadAlpha0_1(scanner);
		scanner.close();

		assertTrue(loaded.equals(saved));
		assertTrue(saved.equals(loaded));
	}
	@Test
	public void testItem_WeaponSaveLoad() {
		try {
			MeleeWeapon saved = new MeleeWeapon(4, 3, 2, 1, "weapon_name", "item_description");
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			Scanner scanner = new Scanner(new File(filePath));
			MeleeWeapon loaded = (MeleeWeapon) Item.loadAlpha0_1(scanner);
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			assertTrue(loaded.equals(saved));
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		}
	}
	@Test
	public void testWeaponDescriptionSaveLoad() {
		try {
			MeleeWeapon saved = new MeleeWeapon(4, 3, 2, 1, "item_name", "item_description");
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			Scanner scanner = new Scanner(new File(filePath));
			System.out.println(scanner.nextLine());
			MeleeWeapon loaded = MeleeWeapon.loadMeleeWeaponAlpha0_1(scanner);
			scanner.close();
			assertEquals(saved.description(), loaded.description());
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		}
	}
	
	@Test
	public void testItem_LightSourceSaveLoad() {
		try {
			LightSource saved = new LightSource(4, 3, 2, 1, 0, "item_name", "item_description");
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			Scanner scanner = new Scanner(new File(filePath));
			LightSource loaded = (LightSource) Item.loadAlpha0_1(scanner);
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			assertTrue(loaded.equals(saved));
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		}
	}
	
	
	@Test
	public void testGearSaveLoad() throws FileNotFoundException, InventoryException {

		HashMap<String, Weapon> weapons = new HashMap<String, Weapon>();
		HashMap<String, WornItem> clothing = new HashMap<String, WornItem>();
		HashMap<String, Armor> armor = new HashMap<String, Armor>();
		weapons.put("test_slot", null);
		weapons.put("empty_slot", null);
		clothing.put("shirt", null);
		clothing.put("pants", null);
		armor.put("main-armor", null);
		armor.put("helmet", null);
		
		
		Gear saved = new Gear(weapons, clothing, armor);
//		System.out.println(saved.getArmorSlots());
//		System.out.println(saved.getWeaponSlots());
//		System.out.println(saved.getClothingSlots());
		saved.addWeapon("test_slot", SampleWeapons.getSampleSword());
		saved.equipArmor(SampleArmor.getSampleHelmet());
		saved.equipClothing(SampleClothing.getSampleShirt());
		
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		Gear loaded = Gear.loadAlpha0_1(scanner);
		scanner.close();
		
		assertEquals(saved, loaded);
	}
}


