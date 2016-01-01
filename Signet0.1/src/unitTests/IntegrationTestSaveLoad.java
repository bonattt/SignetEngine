package unitTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import health.BodyPart;
import health.Injuries;
import health.Wound;
import inventory.Inventory;
import items.Bandage;
import items.Ointment;
import misc.GameLoadException;

import org.junit.Test;

public class IntegrationTestSaveLoad {

	private static final String filePath = "src/unitTests/unitTestSaveFile.txt";
	
	@Test
	public void inventoryEmptySaveLoad() throws FileNotFoundException, GameLoadException {
		Inventory saved = new Inventory();
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		Inventory loaded = Inventory.loadAlpha1_0fromFile(scanner);
		scanner.close();
		
		assertEquals(saved, loaded);
	}

	@Test
	public void injuredBodyPart() throws FileNotFoundException {
		BodyPart saved = new BodyPart("_name_", 10, 1, 15, new double[]{1, 2, 3, 4, 5, 6});
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
		BodyPart bp = new BodyPart("_name_", 10, 1, 15, new double[]{1, 2, 3, 4, 5, 6});
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
	
}
