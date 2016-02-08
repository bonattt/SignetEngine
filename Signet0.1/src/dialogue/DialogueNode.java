package dialogue;

import misc.DeathException;
import creatures.PlayerCharacter;

public interface DialogueNode {
	
	DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException;
	void setNextNode(DialogueNode node);
	
}
