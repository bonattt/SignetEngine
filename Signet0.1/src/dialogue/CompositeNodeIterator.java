package dialogue;

import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

public class CompositeNodeIterator implements DialogueIterator {

	private Set<DialogueNode> nodesVisited;
	private Stack<DialogueIterator> iterators;
	
	public CompositeNodeIterator(DialogueNode startPoint) {
		this(startPoint, new HashSet<DialogueNode>());
	}
	
	public CompositeNodeIterator(DialogueNode startPoint, Set<DialogueNode> nodesVisited) {
		this.nodesVisited = nodesVisited;
		iterators = new Stack<DialogueIterator>();
		for (DialogueNode node : startPoint.getEdges()) {
			if (node != null) {
				iterators.push((DialogueIterator) node.iterator());
				try {
					// All composite iterators iterating on the smae dialogue will share the same NodesVisited set
					((CompositeNodeIterator) iterators.peek()).nodesVisited = this.nodesVisited;
				} catch (ClassCastException e) {
					// do nothing, it's a Leaf-iterator
				}
			}
		}
		iterators.push(new LeafNodeIterator(startPoint));
	}
	
	public boolean hasNext() {
		try {
			seekNext();
		} catch (EmptyStackException e) {
			return false;
		}
		return true;
	}

	public DialogueNode next() {
		try {
			seekNext();
		} catch (EmptyStackException e) {
			throw new NoSuchElementException();
		}
		DialogueNode nextNode = iterators.peek().next();
		System.out.printf("next (composite): %s, ID: %d\n", nextNode.toString(), nextNode.getID());
		return nextNode;
	}

	private void seekNext() throws EmptyStackException {
		while(! iterators.peek().hasNext()) {
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
