package unitTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Scanner;

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
import items.LightSource;
import items.Ointment;
import items.Weapon;
import items.WornItem;
import misc.GameLoadException;

import org.junit.Test;

import creatures.Creature;
import creatures.PlayerCharacter;
import testingMothers.CharacterMother;
import testingMothers.SampleArmor;
import testingMothers.SampleClothing;
import testingMothers.SampleThingy;
import testingMothers.SampleWeapons;

public class IntegrationTestSaveLoad {

	private static final String filePath = "src/unitTests/unitTestSaveFile.txt";
	
	@Test
	public void inventoryEmptySaveLoad() throws FileNotFoundException, GameLoadException {
		Inventory saved = new Inventory();
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		Inventory loaded = Inventory.loadAlpha1_0(scanner);
		scanner.close();
		
		assertEquals(saved, loaded);
	}
	
	@Test
	public void inventoryWithItemsSaveLoad() throws FileNotFoundException, GameLoadException {
		Weapon weapon  = SampleWeapons.getSampleAssaultRifle();
		LightSource light = null;
		HashMap<String, Weapon> weaponSlots = new HashMap<String, Weapon>();
		HashMap<String, Armor> armorSlots = new HashMap<String, Armor>();
		HashMap<String, WornItem> clothingSlots = new HashMap<String, WornItem>();
		weaponSlots.put("hip-holster", null);
		weaponSlots.put("boot-holster", SampleWeapons.getSampleCombatKnife());
		armorSlots.put("main-armor", SampleArmor.getSampleArmorJacket());
		armorSlots.put("helmet", null);
		clothingSlots.put("shirt", SampleClothing.getSampleShirt());
		clothingSlots.put("pants", null);
		clothingSlots.put("aodoaihdw", null);
		
		Gear equipment = new Gear(weaponSlots, clothingSlots, armorSlots);
		ItemContainer backpack = Inventory.getStartingBackpack();
		
		
		Inventory saved = new Inventory(light, weapon, equipment, backpack);
		saved.store(new SampleThingy());
		saved.store(SampleWeapons.getSampleSword());
		
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		Inventory loaded = Inventory.loadAlpha1_0(scanner);
		scanner.close();
		
		assertEquals(saved, loaded);
	}

	@Test
	public void injuredBodyPart() throws FileNotFoundException {
		BodyPart saved = new BodyPart("_name_", 10, 1);
		saved.addNewWound(Injuries.getBluntWound(1, saved));
		saved.addNewWound(Injuries.getBurnWound(2, saved));
		saved.addNewWound(Injuries.getStabWound(3, saved));
		saved.addNewWound(Injuries.getSlashingWound(4, saved));
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		BodyPart loaded = BodyPart.loadAlpha0_1(scanner);
		scanner.close();
		
		assertEquals(saved, loaded);
	}
	
	@Test
	public void treatedWoundSaveLoad() throws FileNotFoundException {
		BodyPart bp = new BodyPart("_name_", 10, 1);
		Wound saved = Injuries.getStabWound(5, bp);
		saved.setBandage(new Bandage());
		saved.setTreatment(new Ointment());
		
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		Wound loaded = Wound.loadAlpha1_0fromFile(scanner, bp);
		scanner.close();
		
		assertEquals(saved, loaded);
	}
	
	@Test
	public void bodySaveLoad()
			throws InventoryException, NoSuchFieldException, SecurityException,
				IllegalArgumentException, IllegalAccessException, FileNotFoundException {
		Creature dick = CharacterMother.getDickDefenderOfLife();
		Field f = Creature.class.getDeclaredField("body");
		f.setAccessible(true);
		Body saved = (Body) f.get(dick);
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		Body loaded = Body.loadAlpha1_0fromFile(scanner, dick);
		scanner.close();
		
		assertEquals(saved, loaded);
	}
	
}
