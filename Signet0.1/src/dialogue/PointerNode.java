package dialogue;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Set;

import misc.DeathException;
import creatures.PlayerCharacter;
/**
 * 
 * @author bonattt
 *
 * this class is used as a pointer to a dialogue node that can't be defined until later in the dialogue. By creating
 * this node early, you can pass it into another node as a next node, then asign the actual node that will be used later on.
 *
 */
public class PointerNode extends DialogueNode {

	private DialogueNode nextNode;
	
	public PointerNode() {
		nextNode = null;
	}
	
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException {
		return nextNode.openNode(player, npc);
	}

	public void setEdges(DialogueNode[] edges) {
		nextNode = edges[0];
	}

	public DialogueNode[] getEdges() {
		return new DialogueNode[]{nextNode};
	}
	

	public void saveNodeToFile(PrintWriter writer, Set<DialogueNode> nodesVisited) {
		if (nodesVisited.contains(this)) {
			return;
		}
		nodesVisited.add(this);
		
		writer.println("PointerNode");
		writer.println(id);
		
		saveNextNode(writer, nodesVisited, nextNode);
	}

	public void saveEdgesToFile(PrintWriter writer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	public static PointerNode loadPointerNodeAlpha0_1(Scanner scanner) {
		// TODO
		throw new UnsupportedOperationException();
	}

}
