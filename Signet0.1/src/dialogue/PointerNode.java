package dialogue;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
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
	
	public PointerNode(int id) {
		nextNode = null;
		this.id = id;
	}

	@Override
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException {
		return nextNode.openNode(player, npc);
	}

	@Override
	public void setEdges(DialogueNode[] edges) {
		nextNode = edges[0];
	}

	@Override
	public DialogueNode[] getEdges() {
		return new DialogueNode[]{nextNode};
	}

	@Override
	public void saveNodeToFile(PrintWriter writer, Set<DialogueNode> nodesVisited) {
		writer.println("pointer");
		if (nodesVisited.contains(this)) {
			return;
		}
		nodesVisited.add(this);
		writer.println(id);
	}

	@Override
	public void saveEdgesToFile(PrintWriter writer) {
		saveSingleNext(nextNode, writer);
	}
	
	public static PointerNode loadPointerNodeAlpha0_1(Scanner scanner) {
		int id = scanner.nextInt();
		scanner.nextLine();
		return new PointerNode(id);
	}
	
	public void loadEdgesAlpha0_1(List<DialogueNode> nodesLoaded, Scanner scanner) {
		nextNode = getSingleEdgeAlpha0_1(nodesLoaded, scanner);
	}
	
	public Iterator iterator() {
		return new LeafNodeIterator(this);
	}

}
