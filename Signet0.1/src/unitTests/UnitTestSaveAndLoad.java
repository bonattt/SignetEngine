package unitTests;

import static org.junit.Assert.*;
import health.Body;
import health.BodyPart;
import health.Wound;
import inventory.Gear;
import inventory.Inventory;
import inventory.ItemContainer;
import items.Armor;
import items.Bandage;
import items.Item;
import items.LightSource;
import items.Ointment;
import items.Weapon;
import items.WornItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

import sampleItems.SampleHelmet;
import sampleItems.SampleShirt;
import sampleWeapons.SampleAssaultRifle;
import sampleWeapons.SampleCombatKnife;
import sampleWeapons.SampleSword;
import testingMothers.CharacterMother;
import creatures.Creature;
import creatures.PlayerCharacter;
import creatures.Skill;
import creatures.SkillTags;

public class UnitTestSaveAndLoad {

	private static final String filePath = "src/unitTests/unitTestSaveFile.txt";
	
	@Test
	public void testSingleSkillLoads() {
		
		try {
			String name = "java programing";
			int id = Integer.MAX_VALUE;
			String[] linkedAtrb = new String[]{"Str"};
			SkillTags[] tags = new SkillTags[]{SkillTags.arms, SkillTags.hands, SkillTags.combat};
			int ranks = 3;
			int exp = 100;
			
			Skill skillSaved = new Skill(name, id, linkedAtrb, tags, ranks);
			skillSaved.gainExperience(exp);
			
			PrintWriter writer = new PrintWriter(filePath, "UTF-8");
			skillSaved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			Skill skillLoaded = Skill.loadFromFile(scanner);
			scanner.close();
			
			assertEquals(name, skillLoaded.name);
			assertEquals(id, skillLoaded.id);
			assertEquals(linkedAtrb.length, skillLoaded.linkedAttributes.length);
			assertEquals(linkedAtrb[0], skillLoaded.linkedAttributes[0]);
			assertEquals(tags.length, skillLoaded.tags.length);
			assertEquals(tags[0], tags[0]);
			assertEquals(tags[1], tags[1]);
			assertEquals(tags[2], tags[2]);
			assertEquals(ranks, skillLoaded.ranks);
			
			Field f1 = Skill.class.getDeclaredField("exp");
			f1.setAccessible(true);
			int loadedExp = f1.getInt(skillLoaded);
			
			assertEquals(exp, loadedExp);
			
		} catch (FileNotFoundException e) {
			fail("threw an exception");
		} catch (UnsupportedEncodingException e) {
			fail("threw an exception");
		} catch (SecurityException e) {
			fail("threw an exception");
		} catch (IllegalArgumentException e) {
			fail("threw an exception");
		} 
		catch (NoSuchFieldException e) {
			fail("threw an exception");
		} catch (IllegalAccessException e) {
			fail("threw an exception");
		}
	}
	
	@Test
	public void testPlayerStatsSaveLoad(){
		try {
			PlayerCharacter dick = CharacterMother.getDickDefenderOfLife();
			PrintWriter writer = new PrintWriter(filePath, "UTF-8");
			dick.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			PlayerCharacter loadedPlayer = PlayerCharacter.loadAlpha0_1fromFile(scanner);
			scanner.close();
			
			assertEquals(dick.name, loadedPlayer.name);
			
			assertEquals(dick.stats_base.size(), loadedPlayer.stats_base.size());
			for(String key : dick.stats_base.keySet()){
				assertTrue(loadedPlayer.stats_base.containsKey(key));
				int dickStat = dick.stats_base.get(key);
				int loadedStat = loadedPlayer.stats_base.get(key);
				assertEquals(dickStat, loadedStat);
			}
			
			assertEquals(dick.damageMultipliers.size(), loadedPlayer.damageMultipliers.size());
			for(String key : dick.damageMultipliers.keySet()){
				assertTrue(loadedPlayer.damageMultipliers.containsKey(key));
				assertEquals(dick.damageMultipliers.get(key), loadedPlayer.damageMultipliers.get(key));
			}
			
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		} catch (UnsupportedEncodingException e) {
			fail("exception thrown");
		}
	}

//	@Test
	public void testBodyBasicSaveLoad(){
		try {
			Body bodSaved = getStartingBody();
			Body bodLoaded = getLoadedBody(bodSaved);
			
			Field field = Body.class.getDeclaredField("healthDamage");
			field.setAccessible(true);
			assertEquals(field.getInt(bodSaved), field.getInt(bodLoaded));

			field = Body.class.getDeclaredField("stunDamage");
			field.setAccessible(true);
			assertEquals(field.getInt(bodSaved), field.getInt(bodLoaded));

			field = Body.class.getDeclaredField("fatigueActual");
			field.setAccessible(true);
			assertEquals(field.getDouble(bodSaved), field.getDouble(bodLoaded), .001);

			field = Body.class.getDeclaredField("painActual");
			field.setAccessible(true);
			assertEquals(field.getDouble(bodSaved), field.getDouble(bodLoaded), .001);
			
			assertEquals(bodSaved.bodyparts.size(), bodLoaded.bodyparts.size());
			assertEquals(bodSaved.getStatMods().length, bodLoaded.getStatMods().length);
			
			field = Body.class.getDeclaredField("creature");
			field.setAccessible(true);
			assertEquals(((Creature) field.get(bodSaved)).name, ((Creature) field.get(bodLoaded)).name);
		
		} catch (NoSuchFieldException e) {
			fail("exception thrown.");
		} catch (SecurityException e) {
			fail("exception thrown.");
		} catch (IllegalArgumentException e) {
			fail("exception thrown");
		} catch (IllegalAccessException e) {
			fail("exception thrown");
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		}
	}
//	@Test
	public void testBodyBodypartsSaveLoad(){
		try {
			Body bodSaved = getStartingBody();
			Body bodLoaded = getLoadedBody(bodSaved);
			
			assertEquals(bodSaved.bodyparts.keySet(), bodLoaded.bodyparts.keySet());
			assertEquals(bodSaved.bodyparts.entrySet(), bodLoaded.bodyparts.entrySet());
		
		} catch (NoSuchFieldException e) {
			fail("exception thrown.");
		} catch (SecurityException e) {
			fail("exception thrown.");
		} catch (IllegalArgumentException e) {
			fail("exception thrown");
		} catch (IllegalAccessException e) {
			fail("exception thrown");
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		}
	}
	private Body getStartingBody() throws IllegalArgumentException, NoSuchFieldException, SecurityException, IllegalAccessException {
		PlayerCharacter dick = CharacterMother.getDickDefenderOfLife();
		Field field = Creature.class.getDeclaredField("body");
		field.setAccessible(true);
		return (Body) field.get(dick);
	}
	private Body getLoadedBody(Body bodSaved) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(filePath);
		bodSaved.saveToFile(writer);
		writer.close();
	
		Scanner scanner = new Scanner(new File(filePath));
		Body bodLoaded = Body.loadAlpha1_0fromFile(scanner);
		scanner.close();
		return bodLoaded;
	}

//	@Test
	public void testInventorySaveLoad(){
		try {
			Inventory saved = new Inventory();
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			Inventory loaded = Inventory.loadAlpha1_0fromFile(scanner);
			scanner.close();
			
			assertEquals(saved.getLightSource(), loaded.getLightSource());
			assertEquals(saved.getWeapon(), loaded.getWeapon());
			assertEquals(saved.getCarriedWeight(), loaded.getCarriedWeight());
			
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		}
		
	
	}
	
	@Test
	public void testItemStatsSaveLoad(){
		try {
			PlayerCharacter saved = CharacterMother.getDickDefenderOfLife();
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			Scanner scanner = new Scanner(new File(filePath));
			PlayerCharacter loaded = PlayerCharacter.loadAlpha0_1fromFile(scanner);
			scanner.close();
			assertEquals(saved.stats_base.size(), loaded.stats_base.size());
			for (String key : saved.stats_base.keySet()){
				assertTrue(loaded.stats_base.containsKey(key));
				assertEquals(saved.stats_base.get(key), loaded.stats_base.get(key));
			}
			
			
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		}
		
		
	}
	
//	@Test
	public void testBodypartSaveLoad(){
		try {
			BodyPart saved = new BodyPart("limb", 1.5, .75, 1, new double[]{1,1,1,1,1,1,1,1,1,1});
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();

			Scanner scanner = new Scanner(new File(filePath));
			BodyPart loaded = BodyPart.loadAlpha1_0fromFile(scanner);
			scanner.close();
			
			assertEquals(saved.name, loaded.name);
			
			Field field = BodyPart.class.getDeclaredField("damageMultiplier");
			field.setAccessible(true);
			assertEquals(field.getDouble(saved), field.getDouble(loaded), .001);
			
			field = BodyPart.class.getDeclaredField("painMultipl");
			field.setAccessible(true);
			assertEquals(field.getDouble(saved), field.getDouble(loaded), .001);
			
			field = BodyPart.class.getDeclaredField("cripplingMultiplier");
			field.setAccessible(true);
			assertEquals(field.getDouble(saved), field.getDouble(loaded), .001);
			
			field = BodyPart.class.getDeclaredField("statMultipliers");
			field.setAccessible(true);
			assertArrayEquals(((double[]) field.get(saved)), ((double[]) field.get(loaded)), .001);
			
			field = BodyPart.class.getDeclaredField("naturalArmor");
			field.setAccessible(true);
			assertEquals(null, field.get(loaded));
			
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		} catch (NoSuchFieldException e) {
			fail("exception thrown");
		} catch (SecurityException e) {
			fail("exception thrown");
		} catch (IllegalArgumentException e) {
			fail("exception thrown");
		} catch (IllegalAccessException e) {
			fail("exception thrown");
		}
	}

//	@Test
	public void testInjurySaveLoad(){
		try {
			BodyPart woundLocation = new BodyPart("limb", 1, 1, 1, new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1});
			int severity = 3;
			int dt = 3;
			String[] names = new String[]{"wound1", "wound2", "wound3", "wound4", "wound5", "wound6", "wound7"};
			int[] health = new int[]{1, 2, 3, 5, 4, 6, 7};
			int[] stun = new int[]{2, 3, 4, 5, 7, 6, 8};
			int[] fatigue = new int[]{2, 0, 1, 3, 4, 5, 6};
			int[] rate = new int[]{1, 4, 5, 7, 2, 6, 3};
			int[] instHealth = new int[]{ 3, 1, 2,6, 4, 5, 7};
			int[] instStun = new int[]{1, 2, 4, 6, 5, 3, 7};
			int[] instFatigue = new int[]{1, 4, 5, 2, 3, 6, 7};
			int[] healTime = new int[]{1, 2, 3, 4, 6, 7, 5};
			int[] infection = new int[]{1, 2, 6, 4, 3, 5, 7};
			double[] pain = new double[]{1, 4, 2, 3, 5, 6, 7};
			int[] cripple = new int[]{5, 1, 4, 6, 2, 3, 7};
			Wound saved = new Wound(severity, dt, names, woundLocation, health, stun, fatigue, rate,
					instHealth,	instStun, instFatigue, healTime, infection, pain, cripple);
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			Scanner scanner = new Scanner(new File(filePath));
			Wound loaded = Wound.loadAlpha1_0fromFile(scanner);
			scanner.close();
			
			assertEquals(severity, loaded.getSeverity());
			
			Field field = Wound.class.getDeclaredField("dt");
			field.setAccessible(true);
			assertEquals(dt, field.getInt(loaded));
			
			field = Wound.class.getDeclaredField("health");
			field.setAccessible(true);
			assertArrayEquals(health, (int[]) field.get(loaded));
			
			field = Wound.class.getDeclaredField("stun");
			field.setAccessible(true);
			assertArrayEquals(stun, (int[]) field.get(loaded));
			
			field = Wound.class.getDeclaredField("fatigue");
			field.setAccessible(true);
			assertArrayEquals(fatigue, (int[]) field.get(loaded));
			
			field = Wound.class.getDeclaredField("rate");
			field.setAccessible(true);
			assertArrayEquals(rate, (int[]) field.get(loaded));
			
			field = Wound.class.getDeclaredField("instHealth");
			field.setAccessible(true);
			assertArrayEquals(instHealth, (int[]) field.get(loaded));
			
			field = Wound.class.getDeclaredField("instStun");
			field.setAccessible(true);
			assertArrayEquals(instStun, (int[]) field.get(loaded));
			
			field = Wound.class.getDeclaredField("instFatigue");
			field.setAccessible(true);
			assertArrayEquals(instFatigue, (int[]) field.get(loaded));
			
			field = Wound.class.getDeclaredField("healthTime");
			field.setAccessible(true);
			assertArrayEquals(healTime, (int[]) field.get(loaded));
			
			field = Wound.class.getDeclaredField("infection");
			field.setAccessible(true);
			assertArrayEquals(infection, (int[]) field.get(loaded));
			
			field = Wound.class.getDeclaredField("pain");
			field.setAccessible(true);
			double[] painLoaded = (double[]) field.get(loaded);
			assertEquals(pain.length, painLoaded.length);
			for (int i = 0; i < pain.length; i++){
				assertEquals(pain[i], painLoaded[i], .001);
			}
			
			field = Wound.class.getDeclaredField("cripple");
			field.setAccessible(true);
			assertArrayEquals(cripple, (int[]) field.get(loaded));
						
			
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		} catch (IllegalArgumentException e) {
			fail("exception thrown");
		} catch (IllegalAccessException e) {
			fail("exception thrown");
		} catch (NoSuchFieldException e) {
			fail("exception thrown");
		} catch (SecurityException e) {
			fail("exception thrown");
		}
	}

	@Test
	public void testItemContainerSaveLoad(){
		try {
			ItemContainer saved = Inventory.getStartingBackpack();
			Weapon wpn1 = new Weapon(1, 1, 1, 1);
			wpn1.description = "this is weapon number one";
			wpn1.name = "weapon one";
			Weapon wpn2 = new Weapon(2, 2, 2, 2);
			wpn2.name = "weapon two";
			wpn2.description = "this is weapon number two";
			WornItem clothing = new WornItem(10, 10, 10, 2, 0);
			clothing.name = "clothing";
			clothing.description = "this is clothing";
			
			saved.addItem(wpn1);
			saved.addItem(wpn2);
			saved.addItem(clothing);
			
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			scanner.nextLine(); // clears "item container" identifier.
			ItemContainer loaded = ItemContainer.loadAlpha0_1(scanner);
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			assertTrue(loaded.equals(saved));
			
//			assertEquals(saved.name, loaded.name);
//			Field field = ItemContainer.class.getDeclaredField("concealmentHits");
//			field.setAccessible(true);
//			assertEquals(field.getInt(saved), field.getInt(loaded));
//			
//			field = ItemContainer.class.getDeclaredField("concealability");
//			field.setAccessible(true);
//			assertEquals(field.getInt(saved), field.getInt(loaded));
//			
//			field = ItemContainer.class.getDeclaredField("spaceUsed");
//			field.setAccessible(true);
//			assertEquals(field.getInt(saved), field.getInt(loaded));
//			
//			field = ItemContainer.class.getDeclaredField("weightContained");
//			field.setAccessible(true);
//			assertEquals(field.getInt(saved), field.getInt(loaded));
//			
//			field = ItemContainer.class.getDeclaredField("baseSpace");
//			field.setAccessible(true);
//			assertEquals(field.getInt(saved), field.getInt(loaded));
//			
//			field = ItemContainer.class.getDeclaredField("baseWeight");
//			field.setAccessible(true);
//			assertEquals(field.getInt(saved), field.getInt(loaded));
//			
//			field = ItemContainer.class.getDeclaredField("items");
//			field.setAccessible(true);
//			assertEquals(((ArrayList<Item>) field.get(saved)).size(), ((ArrayList<Item>) field.get(loaded)).size());
			
		} catch (FileNotFoundException e) {
			fail("threw exception");
//		} catch (NoSuchFieldException e) {
//			fail("threw exception");
		} catch (SecurityException e) {
			fail("threw exception");
		} catch (IllegalArgumentException e) {
			fail("threw exception");
//		} catch (IllegalAccessException e) {
//			fail("threw exception");
		}	
	}

	@Test
	public void testItemContainerContainsCorrectItemsAfterSaveLoad(){
		try {

			ItemContainer saved = Inventory.getStartingBackpack();
			Weapon wpn1 = new Weapon(1, 1, 1, 1);
			wpn1.description = "this is weapon number one";
			wpn1.name = "weapon one";
			Weapon wpn2 = new Weapon(2, 2, 2, 2);
			wpn2.name = "weapon two";
			wpn2.description = "this is weapon number two";
			WornItem clothing = new WornItem(10, 10, 10, 2, 0);
			clothing.name = "clothing";
			clothing.description = "this is clothing";
			
			saved.addItem(wpn1);
			saved.addItem(wpn2);
			saved.addItem(clothing);
			
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			scanner.nextLine(); // clears "item container" identifier.
			ItemContainer loaded = ItemContainer.loadAlpha0_1(scanner);
			scanner.close();
			
			Field field = ItemContainer.class.getDeclaredField("items");
			field.setAccessible(true);
			ArrayList<Item> savedItems = ((ArrayList<Item>) field.get(saved));
			ArrayList<Item> loadedItems = ((ArrayList<Item>) field.get(loaded));

			for (Item item : savedItems){
				assertTrue(loadedItems.contains(item));
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
		}	
	}
//	@Test
	public void testItem_defaultSaveLoad() {
		fail();
	}
	@Test
	public void testItem_ArmorSaveLoad() {
		try {
			Armor saved = new Armor(1000, 1000, 25, 10, 0);
			saved.name = "armor";
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			Armor loaded = (Armor) Item.loadAlpha0_1(scanner);
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			assertEquals(saved.description, loaded.description);
			
		} catch (FileNotFoundException e) {
			fail("an exception was thrown.");
		}
	}
	@Test
	public void testArmor2() {
		try {
			Armor saved = new Armor(100, 100, 15, 5, 1);
			saved.name = "armor_name";
			saved.description = "This armor is shiney";
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			
			Scanner scanner = new Scanner(new File(filePath));
			Armor loaded = (Armor) Item.loadAlpha0_1(scanner);
			scanner.close();
			
			assertTrue(saved.equals(loaded));
			assertEquals(saved.description, loaded.description);
			
		} catch (FileNotFoundException e) {
			fail("an exception was thrown.");
		}
	}
	@Test
	public void testItem_BandageSaveLoad() {
		try {
			Bandage saved = new Bandage(2.5, 2.5, 2.5, "bandage");
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
	public void testItem_RangedWeaponSaveLoad() {
		fail();
	}
	@Test
	public void testItem_WeaponSaveLoad() {
		try {
			Weapon saved = new Weapon(4, 3, 2, 1);
			saved.name = "weapon_name";
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			Scanner scanner = new Scanner(new File(filePath));
			Weapon loaded = (Weapon) Item.loadAlpha0_1(scanner);
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
			Weapon saved = new Weapon(4, 3, 2, 1);
			saved.name = "weapon_name";
			saved.description = "this is the description of a fabulous sword:\n    tada!!";
			PrintWriter writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			Scanner scanner = new Scanner(new File(filePath));
			System.out.println(scanner.nextLine());
			Weapon loaded = Weapon.loadAlpha0_1(scanner);
			scanner.close();
			assertEquals(saved.description, loaded.description);
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		}
	}
	
	@Test
	public void testItem_LightSourceSaveLoad() {
		try {
			LightSource saved = new LightSource(4, 3, 2, 1, 0);
			saved.name = "light";
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
	
	
//	@Test
	public void testEquipmentSaveLoad(){
		
		try {
			Gear saved = new Gear();
			System.out.println(saved.getArmorSlots());
			System.out.println(saved.getWeaponSlots());
			System.out.println(saved.getClothingSlots());
			
			saved.addWeapon("test slot", new SampleSword());
			saved.equipArmor(new SampleHelmet());
			saved.equipClothing(new SampleShirt());
			
			PrintWriter writer;
			writer = new PrintWriter(filePath);
			saved.saveToFile(writer);
			writer.close();
			Scanner scanner = new Scanner(new File(filePath));
			Gear loaded = Gear.loadAlpha0_1fromFile(scanner);
			scanner.close();
			
			assertArrayEquals(saved.getStatMods(), loaded.getStatMods());
			
		} catch (FileNotFoundException e) {
			fail("exception thrown");
		}
		
	}
}

