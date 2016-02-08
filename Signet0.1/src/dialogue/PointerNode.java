package dialogue;

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
public class PointerNode implements DialogueNode {

	private DialogueNode node;
	
	public PointerNode() {
		node = null;
	}
	
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException {
		return node.openNode(player, npc);
	}

	public void setNextNode(DialogueNode node) {
		this.node = node;
	}

}
