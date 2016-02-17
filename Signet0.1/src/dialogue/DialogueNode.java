package dialogue;

import java.io.PrintWriter;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import misc.DeathException;
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
		for (int i = 0; i < this.getEdges().length; i++) {
			if(arg.getEdges()[i].getID() != this.getEdges()[i].getID()) {
				return false;
			}
		}
		return true;
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
