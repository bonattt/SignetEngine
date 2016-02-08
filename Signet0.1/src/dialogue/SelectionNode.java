package dialogue;

import java.io.PrintWriter;

import misc.DeathException;
import misc.TextTools;
import creatures.PlayerCharacter;

/** 
 * selection nodes progress a dialogue purely based on the player's selection.
 * 
 * @author bonattt
 *
 */
public class SelectionNode implements DialogueNode {

	private DialogueNode[] nodes;
	private String[] answers;
	private String text;
	
	public SelectionNode(String text, String[] answers, DialogueNode[] nodes){
		this.text = text;
		this.nodes = nodes;
		this.answers = answers;
	}
	
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException{
		int reply = TextTools.questionAsker(this.text, answers, TextTools.BACK_DISABLED);
		return nodes[reply - 1];
	}

	public void setNextNode(DialogueNode node) {
		throw new UnsupportedOperationException();
	}
	
	public void saveToFile(PrintWriter writer) {
		// TODO Auto-generated method stub
		
	}

}
