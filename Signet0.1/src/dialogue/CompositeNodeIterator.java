package dialogue;

import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

public class CompositeNodeIterator implements Iterator<DialogueNode>{

	private Set<DialogueNode> nodesVisited;
	private Stack<Iterator<DialogueNode>> iterators;
	
	public CompositeNodeIterator(DialogueNode startPoint) {
		this(startPoint, new HashSet<DialogueNode>());
	}
	
	public CompositeNodeIterator(DialogueNode startPoint, Set<DialogueNode> nodesVisited) {
		this.nodesVisited = nodesVisited;
		iterators = new Stack<Iterator<DialogueNode>>();
		for (DialogueNode node : startPoint.getEdges()) {
			if (node != null) {
				iterators.push(node.iterator());
				try {
					((CompositeNodeIterator) iterators.peek()).nodesVisited = this.nodesVisited;
				} catch (ClassCastException e) {
					// do nothing, it's a Leaf-iterator
				}
			}
		}
		iterators.push(new LeafNodeIterator(startPoint));
	}
	
	public void forEachRemaining(Consumer<? super DialogueNode> arg0) {
		throw new UnsupportedOperationException();
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
		
		return iterators.peek().next();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	private void seekNext() throws EmptyStackException {
		while(! iterators.peek().hasNext()) {
			iterators.pop();
		}
	}

}
