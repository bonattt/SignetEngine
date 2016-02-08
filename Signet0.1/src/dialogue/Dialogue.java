package dialogue;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import misc.DeathException;
import misc.GameEvent;
import creatures.Creature;
import creatures.PlayerCharacter;
import creatures.Skill;

public class Dialogue implements GameEvent, Iterable {
	
	private String name;
	private PlayerCharacter player;
	private NPC npc;
	private DialogueNode start;
	
	public Dialogue(String name, DialogueNode start, PlayerCharacter player) {
		this(name, start, player, null);
	}
	
	public Dialogue(String name, DialogueNode start, PlayerCharacter player, NPC npc) {
		this.name = name;
		this.start = start;
		this.player = player;
		this.npc = npc;
	}
	
	public void startDialogue() throws DeathException {
		DialogueNode currentNode = start;
		while(currentNode != null) {
			currentNode = currentNode.openNode(player, npc);
		}
	}
	
	public static DialogueNode getSkillTestNode(int id, Skill skill, String text, DialogueNode nextNode) {
		GameEvent event = null;
		new EventNode(id, text, event, nextNode);
		throw new UnsupportedOperationException();
	}
	
	public static DialogueNode yesNoNode(int id, String text, DialogueNode yesNode, DialogueNode noNode) {
		String[] answers = new String[]{"yes", "no"};
		DialogueNode[] nodes = new DialogueNode[]{yesNode, noNode};
		return new SelectionNode(id, text, answers, nodes);
	}

	public Object triggerEvent(Creature player) throws DeathException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveToFile(PrintWriter writer) {
		writer.println(name);
		if (npc == null) {
			writer.println("null");
		} else {
			npc.saveToFile(writer);
		}
		
		Set<DialogueNode> nodesVisited = new HashSet<DialogueNode>();
		start.saveNodeToFile(writer, nodesVisited);
		for (DialogueNode node : nodesVisited) {
			writer.println(node.getID());
			for (DialogueNode edge : node.getEdges()) {
				writer.printf("%d ", edge.getID());
			}
			writer.println();
		}
	}
	
	public static Dialogue loadFromFileAlpha0_1(Scanner scanner) {
		String name = scanner.nextLine();
		
		
		return null;
	}
	
	public static void loadNodeAlpha0_1(Scanner scanner, Set<DialogueNode> nodesLoaded) {
		String nodeType = scanner.nextLine();
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Dialogue)) {
			return false;
		}
		Dialogue arg = (Dialogue) obj;
		
		
		return true;
	}
	
	// Iterable
	@SuppressWarnings("rawtypes")
	public void forEach(Consumer arg0) {
		throw new UnsupportedOperationException();
	}

	// Iterable
	public Iterator<DialogueNode> iterator() {
		return start.iterator();
	}

	// Iterable
	@SuppressWarnings("rawtypes")
	public Spliterator spliterator() {
		throw new UnsupportedOperationException();
	}
}
