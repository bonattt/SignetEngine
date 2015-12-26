package npc;

import java.util.Scanner;

import misc.DeathException;
import creatures.PlayerCharacter;

/**
 * Selection nodes are a type of dialogue node used to print a message and progress the dialogue.
 * Display Nodes have only one possible next node, and there is no way to circumvent it.
 * Display Nodes can be used to break up a dialogue into 
 * @author bonattt
 *
 */
public class DisplayNode extends DialogueNode {

	private DialogueNode defaultNextNode;
	
	public DisplayNode(Scanner input, String text, DialogueNode nextNode ){
		super(input, text);
		defaultNextNode = nextNode;
	}

	@Override
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException{
		System.out.println(nodeText);
		System.out.print("press enter to continue");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		return defaultNextNode;
	}
	
}
