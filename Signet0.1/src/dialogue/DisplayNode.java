package dialogue;

import items.Item;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import misc.DeathException;
import misc.TextTools;
import creatures.PlayerCharacter;

/**
 * Selection nodes are a type of dialogue node used to print a message and progress the dialogue.
 * Display Nodes have only one possible next node, and there is no way to circumvent it.
 * Display Nodes can be used to break up a dialogue into 
 * @author bonattt
 *
 */
public class DisplayNode extends DialogueNode {

	private DialogueNode nextNode;
	private String text;
	
	public DisplayNode(int id, String text, DialogueNode nextNode){
		this.text = text;
		this.nextNode = nextNode;
		this.id = id;
	}
	
	public void setEdges(DialogueNode[] edges) {
		nextNode = edges[0];
	}

	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException{
		TextTools.display(text);
		TextTools.display("press enter to continue");
		TextTools.input.nextLine();
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
		
		writer.println("DisplayNode");
		writer.println(id);
		Item.saveLongStringToFile(writer, text);

		saveNextNode(writer, nodesVisited, nextNode);
	}

	public void saveEdgesToFile(PrintWriter writer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public Iterator<DialogueNode> iterator() {
		return new LeafNodeIterator(this);
	}
}
