package dialogue;

import items.Item;

import java.io.PrintWriter;
import java.util.Iterator;
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
	
	public void saveNodeToFile(PrintWriter writer, Set<DialogueNode> nodesVisited) {
		if (nodesVisited.contains(this)) {
			return;
		}
		nodesVisited.add(this);
		
		writer.println("SelectionNode");
		writer.println(id);
		for (String answer : answers) {
			writer.printf("%s ", answer);
		}
		Item.saveLongStringToFile(writer, text);
		for (DialogueNode node : nodes) {
			saveNextNode(writer, nodesVisited, node);
		}
	}

	public void saveEdgesToFile(PrintWriter writer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public Iterator iterator() {
		return new CompositeNodeIterator(this);
	}

}
