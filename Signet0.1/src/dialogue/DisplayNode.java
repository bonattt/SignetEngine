package dialogue;

import java.util.Scanner;

import misc.DeathException;
import misc.TextTools;
import creatures.PlayerCharacter;

/**
 * Selection nodes are a type of dialogue node used to print a message and progress the dialogue.
 * Display Nodes have only one possible next node, and there is no way to circumvent it.
 * Display Nodes can be used to break up a dialogue into 
 * @author bonattt
 *
 */
public class DisplayNode implements DialogueNode {

	private DialogueNode defaultNextNode;
	private String text;
	
	public DisplayNode(String text, DialogueNode nextNode ){
		this.text = text;
		this.defaultNextNode = nextNode;
	}
	
	public void setNextNode(DialogueNode node) {
		defaultNextNode = node;
	}

	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException{
		TextTools.display(text);
		TextTools.display("press enter to continue");
		TextTools.input.nextLine();
		return defaultNextNode;
	}
}
