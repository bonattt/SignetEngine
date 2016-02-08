package dialogue;

import java.io.PrintWriter;
import java.util.Set;

import misc.DeathException;
import creatures.PlayerCharacter;

public abstract class DialogueNode {
	
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
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof DialogueNode)) {
			return false;
		}
		return obj.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	protected void saveNextNode(PrintWriter writer, Set<DialogueNode> nodesVisited, DialogueNode nextNode) {
		if (nextNode != null) {
			nextNode.saveNodeToFile(writer, nodesVisited);
		} else {
			writer.println("NULL");
		}
	}
}
