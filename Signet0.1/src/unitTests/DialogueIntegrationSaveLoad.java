package unitTests;

import static org.junit.Assert.*;
import inventory.InventoryException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import misc.GameLoadException;

import org.junit.Test;

import testingMothers.CharacterMother;
import testingMothers.DialogueMother;
import dialogue.Dialogue;

public class DialogueIntegrationSaveLoad {

	private static final String filePath = "src/unitTests/testingData/unitTestSaveFile.txt";
	
	private Dialogue saved, loaded;

	private void saveAndLoad() throws FileNotFoundException, GameLoadException, InventoryException {
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveToFile(writer);
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		loaded = Dialogue.loadFromFileAlpha0_1(scanner, CharacterMother.getDickDefenderOfLife());
		scanner.close();
	}
	
	@Test
	public void testUndertailSaveLoad() throws Exception {
		saved = DialogueMother.undertail();
		saveAndLoad();
		assertEquals(saved, loaded);
	}

	@Test
	public void testSimpleChatSaveLoad() throws Exception {
		saved = DialogueMother.simpleChat();
		saveAndLoad();
		assertEquals(saved, loaded);
	}

	@Test
	public void testSeriesOfScenes() throws Exception {
		saved = DialogueMother.seriesOfScenes();
		saveAndLoad();
		assertEquals(saved, loaded);

	}
}
