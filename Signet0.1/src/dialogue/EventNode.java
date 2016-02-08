package dialogue;

import items.Item;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import creatures.PlayerCharacter;
import misc.DeathException;
import misc.GameEvent;
import misc.TextTools;

public class EventNode extends DialogueNode {
	
	private GameEvent event;
	private DialogueNode nextNode;
	private String text;
	
	public EventNode(int id,String text, GameEvent event, DialogueNode nextNode) {
		this.text = text;
		this.event = event;
		this.nextNode = nextNode;
		this.id = id;
	}

	public void setEdges(DialogueNode[] edges) {
		nextNode = edges[0];
	}

	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException {
		TextTools.display(text);
		TextTools.display("press enter to continue");
		TextTools.input.nextLine();
		event.triggerEvent(player);
		return nextNode;
	}
	
	public DialogueNode[] getEdges() {
		return new DialogueNode[]{nextNode};
	}

	public void saveNodeToFile(PrintWriter writer, Set<DialogueNode> nodesVisited) {
		if (nodesVisited.contains(this)) {
			return;
		}
		nodesVisited.add(this);
		
		writer.println("EventNode");
		writer.println(id);
		Item.saveLongStringToFile(writer, text);
		event.saveToFile(writer);
		
		saveNextNode(writer, nodesVisited, nextNode);
	}

	public void saveEdgesToFile(PrintWriter writer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public Iterator iterator() {
		return new LeafNodeIterator(this);
	}


}
