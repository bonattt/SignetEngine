package dialogue;

import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

public class CompositeNodeIterator implements DialogueIterator {

	private Set<DialogueNode> nodesVisited;
	private Stack<DialogueIterator> iterators;
	private DialogueNode nextNode;

	public CompositeNodeIterator(DialogueNode startPoint) {
		this(startPoint, new HashSet<DialogueNode>());
	}

	public CompositeNodeIterator(DialogueNode startPoint,
			Set<DialogueNode> nodesVisited) {
		this.nodesVisited = nodesVisited;
		iterators = new Stack<DialogueIterator>();
		for (DialogueNode node : startPoint.getEdges()) {
			addNode(node);
		}
		iterators.push(new LeafNodeIterator(startPoint));
		nextNode = null;
	}

	private void addNode(DialogueNode node) {
		if (node != null) {
			iterators.push((DialogueIterator) node.iterator());
			try {
				// All composite iterators iterating on the same dialogue will
				// share the same NodesVisited set
				((CompositeNodeIterator) iterators.peek()).nodesVisited = this.nodesVisited;
			} catch (ClassCastException e) {
				// do nothing, it's a Leaf-iterator
			}
		}

	}

	public boolean hasNext() {
		if (this.nextNode != null) {
			return true;
		}
		try {
			this.nextNode = next();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public DialogueNode next() {
		DialogueNode nextNode;
		
		if (this.nextNode != null) {
			nextNode = this.nextNode;
			this.nextNode = null;
			return nextNode;
		}
		
		while(true) {
			nextNode = getNext();
			if (nextNode == null) {
				return nextNode;
			}
			if (!nodesVisited.contains(nextNode)) {
				nodesVisited.add(nextNode);
				return nextNode;
			}
		}
	}
	
	private DialogueNode getNext() {
		try {
			seekNext();
		} catch (EmptyStackException e) {
			throw new NoSuchElementException();
		}
		DialogueNode nextNode = iterators.peek().next();
		// System.out.printf("next (composite): %s, ID: %d\n",
		// nextNode.toString(), nextNode.getID());
		return nextNode;
	}

	private void seekNext() throws EmptyStackException {
		while (!iterators.peek().hasNext()) {
			DialogueIterator nextIterator = iterators.pop().getNextIterator();
			if (nextIterator != null) {
				iterators.push(nextIterator);
			}
		}
	}

	public void setNodesVisited(Set<DialogueNode> nodesVisited) {
		this.nodesVisited = nodesVisited;
	}

	public DialogueIterator getNextIterator() {
		return null;
	}

}
