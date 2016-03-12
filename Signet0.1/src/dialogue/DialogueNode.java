package dialogue;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import misc.DeathException;
import misc.GameLoadException;
import creatures.PlayerCharacter;

public abstract class DialogueNode implements Iterable<DialogueNode> {
	
	protected int id;
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public abstract DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException;
	public abstract void setEdges(DialogueNode[] edges);
	public abstract DialogueNode[] getEdges();
	public abstract void saveNodeToFile(PrintWriter writer, Set<DialogueNode> nodesVisited);
	public abstract void saveEdgesToFile(PrintWriter writer);
	public abstract void loadEdgesAlpha0_1(List<DialogueNode> nodesLoaded, Scanner scanner)  throws GameLoadException;
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof DialogueNode)) {
			return false;
		}
		DialogueNode arg = (DialogueNode) obj;
		
		if (obj.hashCode() != this.hashCode()) {
			return false;
		}
		if (arg.getEdges().length != this.getEdges().length) {
			return false;
		}
		DialogueNode[] edges1 = arg.getEdges();
		DialogueNode[] edges2 = this.getEdges();
		for (int i = 0; i < this.getEdges().length; i++) {
			if (edges1[i] == null || edges2[i] == null) {
				return false;
			}
			if(edges1[i].getID() != edges2[i].getID()) {
				return false;
			}
		}
		return true;
	}
	
	public static DialogueNode loadNodeFromFileAlpha0_1(Scanner scanner) throws GameLoadException  {
		String nodeType = scanner.nextLine();
		
		if (nodeType.equals("pointer")) {
			return PointerNode.loadPointerNodeAlpha0_1(scanner);
		} else if (nodeType.equals("selection")) {
			return SelectionNode.loadSelectionNodeAlpha0_1(scanner);
		} else if (nodeType.equals("display")) {
			return DisplayNode.loadDisplayNodeFromFileAlpha0_1(scanner);
		} else if (nodeType.equals("event")) {
			return EventNode.loadEventNodeAlpha0_1(scanner);
		} else if (nodeType.equals("edges")) {
			return null;
		}
		else {
			throw new IllegalArgumentException("unrecognized dialogue node type");
		}		
	}
	
	protected static void saveSingleNext(DialogueNode next, PrintWriter writer) {
		if (next == null) {
			writer.println("null");
		}
		else {
			writer.println(next.getID());
		}
	}
	
	protected static void saveMultiEdge(DialogueNode[] nexts, PrintWriter writer) {
		for (DialogueNode node : nexts) {
			saveSingleNext(node, writer);
		}
		writer.println("end");
	}

	protected static DialogueNode getSingleEdgeAlpha0_1(List<DialogueNode> nodesLoaded, Scanner scanner) {
		String line = scanner.nextLine();
		if (line.equals("null")) {
			return null;
		}
		int id = Integer.parseInt(line);
		return getNode(id, nodesLoaded, scanner);
	}
	
	protected static DialogueNode getNode(int id, List<DialogueNode> nodesLoaded, Scanner scanner) {
		DialogueNode nextNode = null;
		for (DialogueNode node : nodesLoaded) {
			if (node.getID() == id) {
				nextNode = node;
				break;
			}
		}
		return nextNode;
	}
	
	protected static DialogueNode[] loadMultiEdgesAlpha0_1(List<DialogueNode> nodesLoaded, Scanner scanner) {
		List<DialogueNode> list = new ArrayList<DialogueNode>();
		String line = scanner.nextLine();
		while(! line.equals("end")) {
			if (line.equals("null")) {
				list.add(null);
			} else {
				int id = Integer.parseInt(line);
				list.add(getNode(id, nodesLoaded, scanner));
			}
			line = scanner.nextLine();
		}
		return dialogueListToNodeArray(list);
	}
	
	private static DialogueNode[] dialogueListToNodeArray(List<DialogueNode> nodeList) {
		DialogueNode[] nodeArray = new DialogueNode[nodeList.size()];
		for (int i = 0; i < nodeArray.length; i++) {
			nodeArray[i] = nodeList.get(i);
		}
		return nodeArray;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}
