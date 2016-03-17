package unitTests.dialogue;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import misc.GameEvent;

import org.junit.Before;
import org.junit.Test;

import testingMothers.LootGenericChest;
import dialogue.Dialogue;
import dialogue.DialogueNode;
import dialogue.DisplayNode;
import dialogue.EventNode;

public class DialogueEventNodeSaveLoad {

	private static final int ID1 = 12345;
	private static final int ID2 = 54321;
	private static final String filePath = "test/unitTests/testingData/unitTestSaveFile.txt";
	
	private DialogueNode saved, loaded, next;
	
	@Before
	public void setup() throws Exception {
		next = new DisplayNode(ID1, "_testing_", null);
	
		GameEvent event = new LootGenericChest();
		saved = new EventNode(ID2, "_eventNodeName_", event, next);
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveNodeToFile(writer, new HashSet<DialogueNode>());
		saved.saveEdgesToFile(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		loaded = DialogueNode.loadNodeFromFileAlpha0_1(scanner);
		List<DialogueNode> list = new ArrayList<DialogueNode>();
		list.add(loaded);
		list.add(next);
		loaded.loadEdgesAlpha0_1(list, scanner);
		scanner.close();
	}
	
	@Test
	public void eventNodeID() {
		int savedID = saved.getID();
		int loadedID = loaded.getID();
		assertEquals(savedID, loadedID);
	}
	
	@Test
	public void eventNodeClass() {
		assertEquals(saved.getClass(), loaded.getClass());
	}

	@Test
	public void eventNodesNextID() throws Exception {
		Field f = EventNode.class.getDeclaredField("nextNode");
		f.setAccessible(true);

		DialogueNode savedNext = (DialogueNode) f.get(saved);
		DialogueNode loadNext = (DialogueNode) f.get(loaded);
		
		assertEquals(savedNext.getID(), loadNext.getID());
	}
	
	@Test
	public void eventNodeHasCorrectEvent() throws Exception {
		Field f = EventNode.class.getDeclaredField("event");
		f.setAccessible(true);
		GameEvent eventSaved = (GameEvent) f.get(saved);
		GameEvent eventLoaded = (GameEvent) f.get(loaded);
		
		assertEquals(eventSaved, eventLoaded);
		
	}
}
