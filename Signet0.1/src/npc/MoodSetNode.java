package npc;

import java.util.Scanner;

import misc.DeathException;
import creatures.PlayerCharacter;

public class MoodSetNode extends DialogueNode {
	
	private int moodChange;
	private DialogueNode nextNode;

	public MoodSetNode(Scanner input, DialogueNode nextNode, int moodAdjustment) {
		super(input, "");
		this.nextNode = nextNode;		
		moodChange = moodAdjustment;
	}
	@Override
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException{
		npc.mood += moodChange;
		return nextNode;
	}

}
