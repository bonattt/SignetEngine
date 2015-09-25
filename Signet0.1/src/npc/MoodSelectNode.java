package npc;

import java.util.Scanner;

import misc.DeathException;
import creatures.PlayerCharacter;

public class MoodSelectNode extends DialogueNode {
	
	int mood;
	DialogueNode lowMoodNextNode;
	DialogueNode highMoodNextNode;

	public MoodSelectNode(Scanner input, DialogueNode lowMoodNextNode, int mood, DialogueNode highMoodNextNodes) {
		super(input, "");
		this.lowMoodNextNode = lowMoodNextNode;
		this.mood = mood;
		this.highMoodNextNode = highMoodNextNodes;
	}

	@Override
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException{
		if (npc.mood >= this.mood){
			return highMoodNextNode;
		}
		return lowMoodNextNode;
	}
}
