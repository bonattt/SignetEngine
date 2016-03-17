package unitTests.dialogue;

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

public class UnitTestDialogueIteratorSimple {

	private static final int NUMB_NODES = 6;
	
	private Dialogue dialogue;
	private Iterator<DialogueNode> iter;
	
	@Before
	public void setup() throws InventoryException {

		DialogueNode end = new DisplayNode(1, "one", null);
		DialogueNode node2 = new DisplayNode(2, "two", end);
		DialogueNode node3 = new DisplayNode(3, "three", node2);
		DialogueNode node4 = new DisplayNode(4, "foud", node3);
		DialogueNode node5 = new DisplayNode(5, "five", node4);
		DialogueNode node6 = new DisplayNode(6, "six", node5);
		
		PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
		dialogue = new Dialogue("_name_", node6, player);
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
		int i = 0;
		try {
//			int i = 0;
			while(iter.hasNext()) {
				iter.next();
				i++;
			}
			assertEquals(NUMB_NODES, i);
		} catch (NoSuchElementException e) {
			System.out.printf("failed at i = %d\n", i);
			throw e;
		}
	}
	@Test
	public void hasNoDuplicateNodes() {
			Set<DialogueNode> set = new HashSet<DialogueNode>();
			List<DialogueNode> list = new ArrayList<DialogueNode>();	
			while(iter.hasNext()) {
				DialogueNode node = iter.next();
				System.out.println(node.getID());
				set.add(node);
				list.add(node);
			}
			assertEquals(set.size(), list.size());
	}
}
