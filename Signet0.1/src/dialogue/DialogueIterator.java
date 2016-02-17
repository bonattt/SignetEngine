package dialogue;

import java.util.Iterator;
import java.util.Set;

public interface DialogueIterator extends Iterator<DialogueNode> {

	void setNodesVisited(Set<DialogueNode> nodesVisited);
	DialogueIterator getNextIterator();
	
}
