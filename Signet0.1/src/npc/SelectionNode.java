package npc;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import misc.DeathException;
import misc.TextTools;
import creatures.PlayerCharacter;

/** 
 * selection nodes progress a dialogue purely based on the player's selection.
 * 
 * @author bonattt
 *
 */
public class SelectionNode extends DialogueNode {

	private DialogueNode[] nodes;
	private String[] answers;
	
	public SelectionNode(Scanner input, String text, String[] answers, DialogueNode[] nodes){
		super(input, text);
		this.nodes = nodes;
		this.answers = answers;
	}
	
	@Override
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException{
		int reply = TextTools.questionAsker(this.nodeText, answers, TextTools.BACK_DISABLED);
		return nodes[reply - 1];
	}
}
