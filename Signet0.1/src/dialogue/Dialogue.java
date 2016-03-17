package dialogue;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import misc.DeathException;
import misc.GameEvent;
import misc.GameLoadException;
import creatures.Creature;
import creatures.PlayerCharacter;
import creatures.Skill;

public class Dialogue implements GameEvent, Iterable<DialogueNode> {
	
	private String name;
	private PlayerCharacter player;
	private NPC npc;
	private DialogueNode start;
	
	// FIXME this is a temporary hack for saveing until I make a dialogue iterator that
	// does not stack overflow on "complex" dialogues.
	private List<DialogueNode> iterableList;
	
	public Dialogue(String name, DialogueNode start, PlayerCharacter player) {
		this(name, start, player, null);
	}
	
	public Dialogue(String name, DialogueNode start, PlayerCharacter player, NPC npc) {
		this.name = name;
		this.start = start;
		this.player = player;
		this.npc = npc; // NPC is an unimplemented class, leave it as null.
	}
	
	public void startDialogue() throws DeathException {
		DialogueNode currentNode = start;
		while(currentNode != null) {
			currentNode = currentNode.openNode(player, npc);
		}
	}
	
	public void setIterableList(List<DialogueNode> iterableList) {
		this.iterableList = iterableList;
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
		startDialogue();
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void saveToFile(PrintWriter writer) {
		writer.println(name);
		if (npc == null) {
			writer.println("null");
		} else {
			npc.saveToFile(writer);
		}
		writer.println(start.getID());		
		Iterator<DialogueNode> nodes = iterator();
		Queue<DialogueNode> nodesIterated = new LinkedList<DialogueNode>();
		Set<DialogueNode> nodesVisited = new HashSet<DialogueNode>();
		while(nodes.hasNext()) {
			DialogueNode node = nodes.next();
			node.saveNodeToFile(writer, nodesVisited);
			nodesIterated.add(node);
		}
		writer.println("edges");
		while(! nodesIterated.isEmpty()) {
			DialogueNode node = nodesIterated.poll();
			node.saveEdgesToFile(writer);
		}
	}
	
	public static Dialogue loadFromFileAlpha0_1(Scanner scanner, PlayerCharacter player) throws GameLoadException {
		String name = scanner.nextLine();
		String npcTag = scanner.nextLine();
		NPC npc;
		if (npcTag.equals("null")) {
			npc = null;
		} else {
			// TODO implement npc load/store here.
			npc = null;
		}
		int startID = scanner.nextInt();
		scanner.nextLine();
		DialogueNode start = loadDialougeNodesAlpha0_1(scanner, startID);
		return new Dialogue(name, start, player);
	}
	
	private static DialogueNode loadDialougeNodesAlpha0_1(Scanner scanner, int startID) throws GameLoadException {
		DialogueNode current;
		List<DialogueNode> nodesLoaded = new ArrayList<DialogueNode>();
		do {
			current = DialogueNode.loadNodeFromFileAlpha0_1(scanner);
			nodesLoaded.add(current);
		} while (current != null);
		
		for (DialogueNode node : nodesLoaded) {
			if (node == null) {
				continue;
			}
			node.loadEdgesAlpha0_1(nodesLoaded, scanner);
		}
		return getStartPoint(nodesLoaded, startID);
	}
	
	private static DialogueNode getStartPoint(List<DialogueNode> nodesLoaded, int startID) throws GameLoadException {
		for (DialogueNode current : nodesLoaded) {
			if (current.getID() == startID) {
				return current;
			}
		}
		throw new GameLoadException("could not find correct starting node in Dialogue.loadDialogueNodesAlpha0_1");
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Dialogue)) {
			return false;
		}
		Dialogue arg = (Dialogue) obj;
		
		
		
		return false;
	}

	public Iterator<DialogueNode> iterator() {
		return iterableList.iterator(); // FIXME
//		return new CompositeNodeIterator(start);
	}

//	public void forEach(Consumer<? super DialogueNode> arg0) {
//		throw new UnsupportedOperationException();
//	}

	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * this method returns the "size" of the dialogue, the number of nodes.
	 * @return
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * this method returns the "length" of the dialogue, the number of edges.
	 * @return
	 */
	public int length() {
		// TODO implement this
		throw new UnsupportedOperationException();
	}
}
