package unitTests.dialogue;

import static org.junit.Assert.*;

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
import dialogue.PointerNode;
import dialogue.SelectionNode;

public class DialogueNodeSaveLoadIntegrationTest {

	private static final int eventIndex = 3;
	private static final int pointerIndex = 2;
	private static final int selectionIndex = 1;
	private static final int displayIndex = 0;
	
	private static final int[] IDS = getIDs(4);
	private static int[] getIDs(int length) {
		int[] ids = new int[length];
		for (int i = 0; i < length; i++) {
			ids[i] = 101*i;
		}
		return ids;
	}

	private static final String filePath = "test/unitTests/testingData/unitTestSaveFile.txt";
	
	private DialogueNode displayNode, eventNode, pointerNode, selectionNode,
		loadedDisplay, loadedEvent, loadedPointer, loadedSelection;
	private Dialogue dialogue;
	private GameEvent event;
	
	@Before
	public void setup() throws Exception {
		event = new LootGenericChest();
		eventNode = new EventNode(IDS[eventIndex], "_event_", event, null);
		pointerNode = new PointerNode(IDS[pointerIndex]);
		String[] answers = new String[]{"answer1-repeat", "answer2-progress", "answer3-exit"};
		DialogueNode[] nodes = new DialogueNode[]{pointerNode, eventNode, null};
		selectionNode = new SelectionNode(IDS[selectionIndex], "_selection_", answers, nodes);
		displayNode = new DisplayNode(IDS[displayIndex], "_display_", selectionNode);
		pointerNode.setEdges(new DialogueNode[]{displayNode});
		
		PrintWriter writer = new PrintWriter(filePath);
		HashSet<DialogueNode> nodesVisited = new HashSet<DialogueNode>();
		eventNode.saveNodeToFile(writer, nodesVisited);
		pointerNode.saveNodeToFile(writer, nodesVisited);
		selectionNode.saveNodeToFile(writer, nodesVisited);
		displayNode.saveNodeToFile(writer, nodesVisited);
		eventNode.saveEdgesToFile(writer);
		pointerNode.saveEdgesToFile(writer);
		selectionNode.saveEdgesToFile(writer);
		displayNode.saveEdgesToFile(writer);
		
		writer.close();
		Scanner scanner = new Scanner(new File(filePath));
		
		loadedEvent = EventNode.loadNodeFromFileAlpha0_1(scanner);
		loadedPointer = PointerNode.loadNodeFromFileAlpha0_1(scanner);
		loadedSelection = SelectionNode.loadNodeFromFileAlpha0_1(scanner);
		loadedDisplay = DisplayNode.loadNodeFromFileAlpha0_1(scanner);
		
		List<DialogueNode> nodesLoaded = new ArrayList<DialogueNode>();
		nodesLoaded.add(loadedEvent);
		nodesLoaded.add(loadedPointer);
		nodesLoaded.add(loadedSelection);
		nodesLoaded.add(loadedDisplay);
		
		loadedEvent.loadEdgesAlpha0_1(nodesLoaded, scanner);
		loadedPointer.loadEdgesAlpha0_1(nodesLoaded, scanner);
		loadedSelection.loadEdgesAlpha0_1(nodesLoaded, scanner);
		loadedDisplay.loadEdgesAlpha0_1(nodesLoaded, scanner);
		
		scanner.close();
	}	

	@Test
	public void eventNodeID() {
		assertEquals(eventNode.getID(), loadedEvent.getID());
	}
	
	@Test
	public void pointerNodeID() {
		assertEquals(pointerNode.getID(), loadedPointer.getID());
	}

	@Test
	public void selectionNodeID() {
		assertEquals(selectionNode.getID(), loadedSelection.getID());
	}

	@Test
	public void displayNodeID() {
		assertEquals(displayNode.getID(), loadedDisplay.getID());
	}
	
	@Test
	public void eventNextID() throws Exception {
		Field f = EventNode.class.getDeclaredField("nextNode");
		f.setAccessible(true);
		Object next = f.get(loadedEvent);
		assertNull(next);
	}
	
	@Test
	public void nodeHasCorrectEvent() throws Exception {
		Field f = EventNode.class.getDeclaredField("event");
		f.setAccessible(true);
		assertEquals(event, f.get(loadedEvent));
	}

	@Test
	public void pointerNextID() throws Exception {
		Field f = PointerNode.class.getDeclaredField("nextNode");
		f.setAccessible(true);
		DisplayNode next = (DisplayNode) f.get(loadedPointer);
		assertEquals(next.getID(), loadedDisplay.getID());
	}

	@Test
	public void selectionNextID() throws Exception {
		Field f = SelectionNode.class.getDeclaredField("nodes");
		f.setAccessible(true);
		DialogueNode[] nodes = (DialogueNode[]) f.get(loadedSelection);
		assertEquals(loadedPointer.getID(), nodes[0].getID());
		assertEquals(loadedEvent.getID(), nodes[1].getID());
		assertNull(nodes[2]);
	}

	@Test
	public void displayNextID() throws Exception {
		Field f = DisplayNode.class.getDeclaredField("nextNode");
		f.setAccessible(true);
		SelectionNode next = (SelectionNode) f.get(loadedDisplay);
		assertEquals(next.getID(), loadedSelection.getID());
	}
	
	@Test
	public void hasCorrectAnswers() throws Exception {
		Field f = SelectionNode.class.getDeclaredField("answers");
		f.setAccessible(true);
		String[] answersSaved = (String[]) f.get(selectionNode);
		String[] answersLoaded = (String[]) f.get(loadedSelection);
		assertArrayEquals(answersSaved, answersLoaded);
	}
}
