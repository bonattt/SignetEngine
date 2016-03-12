package unitTests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import dialogue.DialogueNode;
import dialogue.DisplayNode;
import dialogue.PointerNode;

public class DialoguePointerNodeSaveLoad {

	private static final int ID1 = 12345;
	private static final int ID2 = 54321;
	private static final String filePath = "src/unitTests/testingData/unitTestSaveFile.txt";
	
	private DialogueNode saved, loaded, next;
	
	@Before
	public void setup() throws Exception {
		next = new DisplayNode(ID1, "_testing_", null);
		
		saved = new PointerNode(ID2);
		saved.setEdges(new DialogueNode[]{next});
		
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
	public void pointerNodeID() {
		int savedID = saved.getID();
		int loadedID = loaded.getID();
		assertEquals(savedID, loadedID);
	}
	
	@Test
	public void pointerNodeClass() {
		assertEquals(saved.getClass(), loaded.getClass());
	}

	@Test
	public void pointerNodeNextID() throws Exception {
		Field f = PointerNode.class.getDeclaredField("nextNode");
		f.setAccessible(true);

		DialogueNode savedNext = (DialogueNode) f.get(saved);
		DialogueNode loadNext = (DialogueNode) f.get(loaded);
	
		assertEquals(savedNext.getID(), loadNext.getID());
	}
}
