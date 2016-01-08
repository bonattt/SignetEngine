package unitTests;

import static org.junit.Assert.*;
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
import items.StoryItem;
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
import java.util.List;
import java.util.Scanner;

import location.Location;
import location.TravelPath;
import misc.DeathException;
import misc.GameEvent;
import misc.GameLoadException;

import org.junit.Test;

import testingMothers.CharacterMother;
import testingMothers.LootGenericChest;
import testingMothers.SampleItems;
import creatures.Creature;
import creatures.PlayerCharacter;
import creatures.Skill;
import creatures.SkillTag;
import environment.Environment;

public class UnitTestSaveAndLoad {

	private static final String filePathRoot = "src/unitTests/testingData/";
	private static final String fileName = "unitTestSaveFile.txt";
	private static final String filePath = filePathRoot + fileName;
	
	static {
		System.out.println("untested classes:\n\thealth.Infection\n\tcreatures.Trait\n\t"
				+ "creatures.PlayerCharacter\n\tnpc.*\n\tenvironment.*\n");
	}
	@Test
	public void sampleEventSaveLoad() throws FileNotFoundException, GameLoadException {
		GameEvent saved = new LootGenericChest();
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		GameEvent loaded = Environment.loadGameEventAlpha0_1(scanner);
		scanner.close();
		
		assertEquals(saved, loaded);;
		System.out.println("loading from: " + filePath);
	}
	
	@Test
	public void locationSaveLoad()
			throws FileNotFoundException, GameLoadException, IllegalArgumentException,
				IllegalAccessException, NoSuchFieldException, SecurityException {
		List<TravelPath> paths = new ArrayList<TravelPath>();
		paths.add(new TravelPath("_street_", "_location_name_", 1, 1));
		paths.add(new TravelPath("_name_", "_location_name_", 2, 2));
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		explorable.add(new LootGenericChest()); // = TestEvent();
		
		Location saved = new Location("test_location", "unitTestSaveFile.txt", "_description_", paths, explorable);
		saved.saveToFile(filePathRoot);
		
		Field f = Location.class.getDeclaredField("fileName");
		f.setAccessible(true);
		String fileName = (String) f.get(saved);
		Location loaded = Location.loadLocation(filePathRoot, fileName);
		
		assertEquals(saved, loaded);
		System.out.println("loading from: " + filePathRoot + fileName);
	}
	
	
	
	@Test
	public void longStringSaveLoad() throws FileNotFoundException {
		String saved = "hello my fwend.\nI don't even speak good, spare me.";
		PrintWriter writer = new PrintWriter(filePath);
		Item.saveLongStringToFile(writer, saved);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		String loaded = Item.loadLongStringAlpha0_1(scanner);
		scanner.close();
		
		assertEquals(saved, loaded);
		System.out.println("loading from: " + filePath);
	}
	@Test
	public void twoLongStringsSaveLoad() throws FileNotFoundException {
		String saved1 = "hello my fwend.\nI don't even speak good, spare me.";
		String saved2 = "this is another string";
		PrintWriter writer = new PrintWriter(filePath);
		Item.saveLongStringToFile(writer, saved1);
		Item.saveLongStringToFile(writer, saved2);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		String loaded1 = Item.loadLongStringAlpha0_1(scanner);
		String loaded2 = Item.loadLongStringAlpha0_1(scanner);
		scanner.close();
		
		assertEquals(saved1, loaded1);
		assertEquals(saved2, loaded2);
		System.out.println("loading from: " + filePath);
	}
	@Test
	public void emptyLongStringSaveLoad() throws FileNotFoundException {
		String saved1 = "";
		String saved2 = "";
		PrintWriter writer = new PrintWriter(filePath);
		Item.saveLongStringToFile(writer, saved1);
		Item.saveLongStringToFile(writer, saved2);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		String loaded1 = Item.loadLongStringAlpha0_1(scanner);
		String loaded2 = Item.loadLongStringAlpha0_1(scanner);
		scanner.close();
		
		assertEquals(saved1, loaded1);
		assertEquals(saved2, loaded2);
		System.out.println("loading from: " + filePath);
	}
	@Test
	public void LongStringWithWhiteSpaceSaveLoad() throws FileNotFoundException {
		String saved1 = "just some stuff";
		PrintWriter writer = new PrintWriter(filePath);
		writer.println("\n");
		Item.saveLongStringToFile(writer, saved1);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		String loaded1 = Item.loadLongStringAlpha0_1(scanner);
		scanner.close();
		
		assertEquals(saved1, loaded1);
		System.out.println("loading from: " + filePath);
	}
	
	@Test
	public void storyItemSaveLoad() throws FileNotFoundException {
		StoryItem saved = SampleItems.getMysticThingy();
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		StoryItem loaded = (StoryItem) Item.loadAlpha0_1(scanner);
		scanner.close();
		assertEquals(saved, loaded);
		System.out.println("loading from: " + filePath);
	}
	
	@Test
	public void testSingleSkillLoads() throws FileNotFoundException, GameLoadException {
			String name = "java programing";
			int id = Integer.MAX_VALUE;
			String[] linkedAtrb = new String[]{"Str"};
			SkillTag[] tags = new SkillTag[]{SkillTag.arms, SkillTag.hands, SkillTag.combat};
			int ranks = 3;
			int exp = 100;
			
			Skill saved = new Skill(name, id, ranks, linkedAtrb, tags);
			saved.gainExperience(exp);
			
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			Skill loaded = Skill.loadSkillAlpha0_1(scanner);
			scanner.close();
			
			assertEquals(saved, loaded);
			System.out.println("loading from: " + filePath);
	}
	
	@Test
	public void testPlayerSkillsSaveLoad() throws FileNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchFieldException, InventoryException {
		
		PlayerCharacter saved = CharacterMother.getDickDefenderOfLife();
		PrintWriter writer = new PrintWriter(filePath);;
		Method method = Creature.class.getDeclaredMethod("saveSkills", writer.getClass());
		method.setAccessible(true);
		method.invoke(saved, writer);
//		saved.saveSkills(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		method = Creature.class.getDeclaredMethod("loadAlpha0_1skills", scanner.getClass());
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
		System.out.println("loading from: " + filePath);
	}
	
	@Test
	public void testPlayerDamageMultipliersLoad() 
			throws FileNotFoundException, NoSuchMethodException,
				SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
				NoSuchFieldException, InventoryException {
		
		PlayerCharacter saved = CharacterMother.getDickDefenderOfLife();
		PrintWriter writer = new PrintWriter(filePath);
		Method method = Creature.class.getDeclaredMethod("saveDamageMultipliers", writer.getClass());
		method.setAccessible(true);
		method.invoke(saved, writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		
		method = Creature.class.getDeclaredMethod("loadAlpha0_1damageMultipliers", scanner.getClass());
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
		System.out.println("loading from: " + filePath);
	}
	
	
	
	@Test
	public void testPlayerStatsSaveLoad() throws FileNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InventoryException {
		PlayerCharacter saved = CharacterMother.getDickDefenderOfLife();
		PrintWriter writer = new PrintWriter(filePath);
		Method method = Creature.class.getDeclaredMethod("saveStats", writer.getClass());
		method.setAccessible(true);
		method.invoke(saved, writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		method = Creature.class.getDeclaredMethod("loadAlpha0_1stats", scanner.getClass());
		method.setAccessible(true);
		Object loadedObj = method.invoke(null, scanner);
		@SuppressWarnings("unchecked")
		HashMap<String, Integer> loadedMap = (HashMap<String, Integer>) loadedObj;
		scanner.close();
		
		for (String key : loadedMap.keySet()) {
			assertEquals(saved.getStat(key), (int) loadedMap.get(key));
		}
		assertEquals(Creature.ABILITIES.length, loadedMap.size());
		System.out.println("loading from: " + filePath);
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
		System.out.println("loading from: " + filePath);
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
		System.out.println("loading from: " + filePath);
	}
	@Test
	public void testItem_WornItemSaveLoad() throws FileNotFoundException {
		WornItem saved = new WornItem(10, 10, 20, 0, 0, "_slot_", "clothing", "nice clothing");
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
	
		Scanner scanner = new Scanner(new File(filePath));
		String itemType = scanner.nextLine();
		assertEquals("worn item", itemType);
		WornItem loaded = (WornItem) Item.loadAlpha0_1(scanner, itemType);
		scanner.close();
			
		assertTrue(saved.equals(loaded));
		assertTrue(loaded.equals(saved));
		System.out.println("loading from: " + filePath);
	}

	@Test
	public void testItemContainerSaveLoad() throws FileNotFoundException {
		ItemContainer saved = Inventory.getStartingBackpack();
		MeleeWeapon wpn1 = new MeleeWeapon(1, 1, 1, 1, 1, "item_name", "item_description");
		MeleeWeapon wpn2 = new MeleeWeapon(2, 2, 2, 2, 2, "item_name", "item_description");
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
		System.out.println("loading from: " + filePath);
	}

	@Test
	public void testItemContainerContainsCorrectItemsAfterSaveLoad()
			throws IllegalArgumentException, IllegalAccessException,
				NoSuchFieldException, SecurityException, FileNotFoundException {
		ItemContainer saved = Inventory.getStartingBackpack();
		MeleeWeapon wpn1 = new MeleeWeapon(1, 1, 1, 1, 1, "item_name", "item_description");
		MeleeWeapon wpn2 = new MeleeWeapon(2, 2, 2, 2, 2, "item_name", "item_description");
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
	}
	
	@Test
	public void testItem_ArmorSaveLoad() throws FileNotFoundException {
		Armor saved = new Armor(1000, 1000, 25, 10, 0, "armor_slot", "armor_name", "armor_description");
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		Armor loaded = (Armor) Item.loadAlpha0_1(scanner);
		scanner.close();
		
		assertTrue(saved.equals(loaded));
		assertEquals(saved.description(), loaded.description());
	}
	@Test
	public void testArmor2() throws FileNotFoundException {
		Armor saved = new Armor(100, 100, 15, 5, 1, "armor_slot", "armor_name", "this armor is shiney");
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		Armor loaded = (Armor) Item.loadAlpha0_1(scanner);
		scanner.close();
		
		assertTrue(saved.equals(loaded));
		assertEquals(saved.description(), loaded.description());
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
		System.out.println("loading from: " + filePath);
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
		System.out.println("loading from: " + filePath);
	}
	@Test
	public void testItem_RangedWeaponSaveLoad() throws FileNotFoundException {
		RangedWeapon saved = new RangedWeapon("weapon","this shoots bullets",
				100, 100, 100, 10, 0, 5, 5, 20, 3, RangedWeapon.ONE_HANDED, 10, 10);
		
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		RangedWeapon loaded = (RangedWeapon) Item.loadAlpha0_1(scanner);
		scanner.close();

		assertTrue(loaded.equals(saved));
		assertTrue(saved.equals(loaded));
		System.out.println("loading from: " + filePath);
	}
	@Test
	public void testItem_WeaponSaveLoad() {
		try {
			MeleeWeapon saved = new MeleeWeapon(4, 3, 2, 1, 2, "weapon_name", "item_description");
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
		System.out.println("loading from: " + filePath);
	}
	@Test
	public void testWeaponDescriptionSaveLoad() {
		try {
			MeleeWeapon saved = new MeleeWeapon(4, 3, 2, 1, 1, "item_name", "item_description");
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
		System.out.println("loading from: " + filePath);
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
		System.out.println("loading from: " + filePath);
	}
	
	
	@Test
	public void emptyGearSaveLoad() throws FileNotFoundException, InventoryException {

		HashMap<String, Weapon> weaponSlots = new HashMap<String, Weapon>();
		HashMap<String, WornItem> clothingSlots = new HashMap<String, WornItem>();
		HashMap<String, Armor> armorSlots = new HashMap<String, Armor>();
		weaponSlots.put("test_slot", null);
		weaponSlots.put("empty_slot", null);
		clothingSlots.put("shirt", null);
		clothingSlots.put("pants", null);
		armorSlots.put("main-armor", null);
		armorSlots.put("helmet", null);
		Gear saved = new Gear(weaponSlots, clothingSlots, armorSlots);

		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		Gear loaded = Gear.loadAlpha0_1(scanner);
		scanner.close();
		
		assertEquals(saved, loaded);
		System.out.println("loading from: " + filePath);
	}
}


