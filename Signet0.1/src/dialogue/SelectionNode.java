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
 * selection nodes progress a dialogue purely based on the player's selection.
 * 
 * @author bonattt
 *
 */
public class SelectionNode extends DialogueNode {

	private DialogueNode[] nodes;
	private String[] answers;
	private String text;
	
	public SelectionNode(int id, String text, String[] answers, DialogueNode[] nodes){
		this.text = text;
		this.id = id;
		this.nodes = nodes;
		this.answers = answers;
	}
	
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException{
		int reply = TextTools.questionAsker(this.text, answers, TextTools.BACK_DISABLED);
		return nodes[reply - 1];
	}

	public void setEdges(DialogueNode[] edges) {
		nodes = edges;
	}

	public DialogueNode[] getEdges() {
		return nodes;
	}

	public static DialogueNode loadSelectionNodeAlpha0_1(Scanner scanner) {
		int id = scanner.nextInt();
		scanner.nextLine();
		String[] answers = scanner.nextLine().split(" ");
		String text = Item.loadLongStringAlpha0_1(scanner);
		return new SelectionNode(id, text, answers, new DialogueNode[]{});
	}
	
	@Override
	public void loadEdgesAlpha0_1(List<DialogueNode> nodesLoaded, Scanner scanner) {
		nodes = loadMultiEdgesAlpha0_1(nodesLoaded, scanner);
	}
	
	public void saveNodeToFile(PrintWriter writer, Set<DialogueNode> nodesVisited) {
		writer.println("selection");
		if (nodesVisited.contains(this)) {
			return;
		}
		nodesVisited.add(this);
		writer.println(id);
		for (String answer : answers) {
			writer.printf("%s ", answer);
		}
		writer.println();
		Item.saveLongStringToFile(writer, text);
	}

	public void saveEdgesToFile(PrintWriter writer) {
		saveMultiEdge(nodes, writer);
	}

	public Iterator iterator() {
		return new CompositeNodeIterator(this);
	}

}
