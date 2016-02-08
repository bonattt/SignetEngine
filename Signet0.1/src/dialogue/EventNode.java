package dialogue;

import java.io.PrintWriter;
import java.util.Scanner;

import creatures.PlayerCharacter;
import misc.DeathException;
import misc.GameEvent;
import misc.TextTools;

public class EventNode implements DialogueNode {
	
	private GameEvent event;
	private DialogueNode nextNode;
	private String text;
	
	public EventNode(String text, GameEvent event, DialogueNode nextNode) {
		this.text = text;
		this.event = event;
		this.nextNode = nextNode;
	}
	
	public void setNextNode(DialogueNode node) {
		nextNode = node;
	}

	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException {
		TextTools.display(text);
		TextTools.display("press enter to continue");
		TextTools.input.nextLine();
		event.triggerEvent(player);
		return nextNode;
	}

	public void saveToFile(PrintWriter writer) {
		// TODO Auto-generated method stub
		
	}

}
