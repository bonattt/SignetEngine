package npc;

import java.util.ArrayList;

import misc.DeathException;
import creatures.PlayerCharacter;

public class NPC {
	
	int mood;
	ArrayList<NPCstate> states;
	DialogueNode defaultDialogue;
	
	public NPC (DialogueNode dialogue){
		defaultDialogue = dialogue;
		states = new ArrayList<NPCstate>();
		mood = 0;
	}
	
	public void startDialogue(PlayerCharacter player) throws DeathException{
		startDialogue(player, defaultDialogue);
	}
	
	public void startDialogue(PlayerCharacter player, DialogueNode dialogue) throws DeathException{
		
		if (dialogue == null) {
			System.out.println("That dialogue does not exist");
			return;
		}
		int count = 0;
		while(dialogue != null) {
			if (dialogue.passesTime){
				count++; // TODO pass time for the number of dialogue nodes passed.
			}
			dialogue = dialogue.openNode(player, this);
			// handle's the dialogue node, and sets dialogue to the next node.
		}
	}
	
}
