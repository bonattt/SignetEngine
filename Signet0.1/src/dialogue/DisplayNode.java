package dialogue;

import items.Item;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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

	@Override
	public void setEdges(DialogueNode[] edges) {
		nextNode = edges[0];
	}

	@Override
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException{
		TextTools.display(text);
		TextTools.display("press enter to continue");
		TextTools.input.nextLine();
		return nextNode;
	}

	@Override
	public DialogueNode[] getEdges() {
		return new DialogueNode[]{nextNode};
	}

	@Override
	public void saveNodeToFile(PrintWriter writer, Set<DialogueNode> nodesVisited) {
		if (nodesVisited.contains(this)) {
			return;
		}
		writer.println("display");
		nodesVisited.add(this);
		writer.println(id);
		Item.saveLongStringToFile(writer, text);
	}
	
	public static DialogueNode loadDisplayNodeFromFileAlpha0_1(Scanner scanner) {
		int id = scanner.nextInt();
		scanner.nextLine();
		String text = Item.loadLongStringAlpha0_1(scanner);
		return new DisplayNode(id, text, null);
	}

	@Override
	public void loadEdgesAlpha0_1(List<DialogueNode> nodesLoaded, Scanner scanner) {
		nextNode = getSingleEdgeAlpha0_1(nodesLoaded, scanner);
	}

	@Override
	public void saveEdgesToFile(PrintWriter writer) {
		saveSingleNext(nextNode, writer);
	}

	public Iterator<DialogueNode> iterator() {
		return new LeafNodeIterator(this);
	}
}
