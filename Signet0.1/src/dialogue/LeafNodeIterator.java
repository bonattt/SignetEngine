package dialogue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class LeafNodeIterator implements Iterator<DialogueNode> {
	
	private DialogueNode node;
	private boolean visited;
	
	public LeafNodeIterator(DialogueNode node) {
		this.node = node;
		visited = false;
	}

	public void forEachRemaining(Consumer<? super DialogueNode> arg0) {
		throw new UnsupportedOperationException();
	}

	public boolean hasNext() {
		return !visited;
	}

	public DialogueNode next() {
		if(visited)
			throw new NoSuchElementException();
		else
			return node;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
