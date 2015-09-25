package npc;

import java.util.Scanner;

import creatures.PlayerCharacter;
import misc.DeathException;
import misc.GameEvent;
import misc.DeathException;

public class EventNode extends DialogueNode {
	
	private GameEvent event;
	private DialogueNode nextNode;
	public EventNode(Scanner input, String text, GameEvent event, DialogueNode nextNode) {

		super(input, text);
		this.event = event;
		this.nextNode = nextNode;
	}

	@Override
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException {
		event.triggerEvent(player);
		return nextNode;
	}

}
