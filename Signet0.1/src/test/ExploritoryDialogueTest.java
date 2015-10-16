package test;

import testingMothers.CharacterMother;
import testingMothers.DialogueMother;
import creatures.PlayerCharacter;
import npc.DialogueNode;
import npc.NPC;
import misc.DeathException;

public class ExploritoryDialogueTest {

	public static void main(String[] args){
		DialogueNode head = DialogueMother.LargeDialogue01();
		PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
		NPC npc = new NPC(head);
		try {
			npc.startDialogue(player);
		} catch (DeathException e) {
			System.out.println("Player died during the test");
//			e.printStackTrace();
		}
	}
}
