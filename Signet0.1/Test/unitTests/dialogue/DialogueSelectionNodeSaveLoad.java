package unitTests.dialogue;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import dialogue.DialogueNode;
import dialogue.DisplayNode;
import dialogue.SelectionNode;

public class DialogueSelectionNodeSaveLoad {

	private static final int ID1 = 12345;
	private static final int ID2 = 54321;
	private static final int ID3 = Integer.MAX_VALUE;
	private static final String filePath = "test/unitTests/testingData/unitTestSaveFile.txt";
	
	private DialogueNode saved, loaded, next1, next2;
	
	@Before
	public void setup() throws Exception {
		next1 = new DisplayNode(ID1, "_testing_", null);
		next2 = new DisplayNode(ID3, "_next2_", null);
		
		saved = new SelectionNode(ID2, "_test_", new String[]{"answer1", "answer2"}, new DialogueNode[]{next1, next2});
		saved.setEdges(new DialogueNode[]{next1});
		
		PrintWriter writer = new PrintWriter(filePath);
		saved.saveNodeToFile(writer, new HashSet<DialogueNode>());
		saved.saveEdgesToFile(writer);
		writer.close();
		
		Scanner scanner = new Scanner(new File(filePath));
		loaded = DialogueNode.loadNodeFromFileAlpha0_1(scanner);
		List<DialogueNode> list = new ArrayList<DialogueNode>();
		list.add(loaded);
		list.add(next1);
		list.add(next2);
		loaded.loadEdgesAlpha0_1(list, scanner);
		scanner.close();
	}
	
	@Test
	public void selectionNodeID() {
		int savedID = saved.getID();
		int loadedID = loaded.getID();
		assertEquals(savedID, loadedID);
	}
	
	@Test
	public void selectionNodeClass() {
		assertEquals(saved.getClass(), loaded.getClass());
	}

	@Test
	public void selectionNodesNextID() throws Exception {
		Field f = SelectionNode.class.getDeclaredField("nodes");
		f.setAccessible(true);

		DialogueNode[] savedNext = (DialogueNode[]) f.get(saved);
		DialogueNode[] loadNext = (DialogueNode[]) f.get(loaded);
		
		assertEquals(savedNext.length, loadNext.length);
		for (int i = 0; i < savedNext.length; i++) {
			assertEquals(savedNext[i].getID(), loadNext[i].getID());
		}
	}
	
	@Test
	public void selectionNodesSaveCorrectAnswers() throws Exception {
		Field f = SelectionNode.class.getDeclaredField("answers");
		f.setAccessible(true);
		List<String> savedAnswers = Arrays.asList((String[]) f.get(saved));
		List<String> loadedAnswers = Arrays.asList((String[]) f.get(loaded));
		for (String str : savedAnswers) {
			assertTrue(loadedAnswers.contains(str));
		}
	}
	
	@Test
	public void selectionNodesSaveCorrectNumberOfAnswers() throws Exception {
		Field f = SelectionNode.class.getDeclaredField("answers");
		f.setAccessible(true);
		List<String> savedAnswers = Arrays.asList((String[]) f.get(saved));
		List<String> loadedAnswers = Arrays.asList((String[]) f.get(loaded));
		assertEquals(savedAnswers.size(), loadedAnswers.size());
	}
	
	@Test
	public void selectionNodesSaveCorrectOrderOfAnswers() throws Exception {
		Field f = SelectionNode.class.getDeclaredField("answers");
		f.setAccessible(true);
		List<String> savedAnswers = Arrays.asList((String[]) f.get(saved));
		List<String> loadedAnswers = Arrays.asList((String[]) f.get(loaded));
		assertArrayEquals(savedAnswers.toArray(), loadedAnswers.toArray());
	}
}
