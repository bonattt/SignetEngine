package npc;

import java.util.Scanner;

import misc.DeathException;
import creatures.PlayerCharacter;

public abstract class DialogueNode {

	protected boolean passesTime = true;
	protected Scanner input;
	protected String nodeText;
	
	public DialogueNode(Scanner input, String text){
		nodeText = text;
		this.input = input;
		passesTime = true;
	}	
	
	public abstract DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException; //openNode handles the node, and returns the next node.
}
