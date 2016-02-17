package dialogue;

import java.util.NoSuchElementException;
import java.util.Set;

public class LeafNodeIterator implements DialogueIterator {
	
	private DialogueNode node;
	private boolean visited;
	
	public LeafNodeIterator(DialogueNode node) {
		this.node = node;
		visited = false;
	}

	public boolean hasNext() {
		return !visited;
	}

	public DialogueNode next() {
		System.out.printf("next (leaf): %s, ID: %d\n", node.toString(), node.getID());
		if(! hasNext())
			throw new NoSuchElementException();
		else
			visited = true;
			return node;
	}

	public void setNodesVisited(Set<DialogueNode> nodesVisited) {
		if (nodesVisited.contains(node)) {
			visited = true;
		}
	}

	public DialogueIterator getNextIterator() {
		DialogueNode next = node.getEdges()[0];
		return (DialogueIterator) next.iterator();
	}

}
