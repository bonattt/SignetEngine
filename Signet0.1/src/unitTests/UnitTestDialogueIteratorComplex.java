package unitTests;

import static org.junit.Assert.assertEquals;
import inventory.InventoryException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import testingMothers.CharacterMother;
import creatures.PlayerCharacter;
import dialogue.Dialogue;
import dialogue.DialogueNode;
import dialogue.DisplayNode;
import dialogue.PointerNode;
import dialogue.SelectionNode;

public class UnitTestDialogueIteratorComplex {

	private static final int NUMB_NODES = 13;
	
	private Dialogue dialogue;
	private Iterator<DialogueNode> iter;
	
	@Before
	public void setup() throws InventoryException {

		DialogueNode reset2 = new DisplayNode(1, "text", null);
		DialogueNode exit2 = new DisplayNode(2, "text", null);
		DialogueNode[] nodes = new DialogueNode[]{exit2, reset2};
		DialogueNode selectD = new SelectionNode(3, "select", new String[]{"", ""}, nodes);
		DialogueNode reset1 = new PointerNode(4);
		nodes = new DialogueNode[]{reset1, selectD};
		DialogueNode selectC = new SelectionNode(5, "select", new String[]{"", ""}, nodes);
		reset1.setEdges(new DialogueNode[]{null}); //TODO
		DialogueNode branch1 = new PointerNode(6);
		branch1.setEdges(new DialogueNode[]{selectC});
		DialogueNode branch2 = new DisplayNode(7, "text", selectC);
		DialogueNode branch3 = new DisplayNode(8, "text", selectC);
		nodes = new DialogueNode[]{branch1, branch2, branch3};
		DialogueNode selectB = new SelectionNode(9, "select B", new String[]{"1", "2", "3"}, nodes);
		DialogueNode exit1 = new PointerNode(10);
		DialogueNode jump = new DisplayNode(11, "jump", selectC);
		nodes = new DialogueNode[]{exit1, selectB, jump};
		DialogueNode selectA = new SelectionNode(12, "select-A", new String[]{"exit",  "select", "jump"}, nodes);
		DialogueNode intro = new DisplayNode(13, "welcome!!", selectA);
		nodes = new DialogueNode[]{intro};
		reset2.setEdges(nodes);
		reset1.setEdges(nodes);
		
		PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
		dialogue = new Dialogue("_name_", intro, player);
		iter = dialogue.iterator();
	}
	
	@Test(expected=NoSuchElementException.class)
	public void iteratorRunsoutAndThrowsException() {
		for(int i = 0; i < NUMB_NODES+1; i++) {
			iter.next();
		}
	}
	@Test
	public void iteratorHasCorrectSize() {
		@SuppressWarnings("rawtypes")
		int i = 0;
		while(iter.hasNext()) {
			iter.next();
			i++;
		}
		assertEquals(NUMB_NODES, i);
	}
	@Test
	public void hasNoDuplicateNodes() {
		Set<DialogueNode> set = new HashSet<DialogueNode>();
		List<DialogueNode> list = new ArrayList<DialogueNode>();		
		while(iter.hasNext()) {
			DialogueNode node = iter.next();
			set.add(node);
			list.add(node);
		}
		assertEquals(set.size(), list.size());
	}
}
