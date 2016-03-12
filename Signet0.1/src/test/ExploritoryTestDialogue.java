package test;

import misc.DeathException;
import testingMothers.DialogueMother;
import dialogue.Dialogue;

public class ExploritoryTestDialogue {
	
	public static void main(String[] args) {
		Dialogue dialogue = DialogueMother.undertail();
		try {
			dialogue.startDialogue();
		} catch (DeathException e) {
			e.printStackTrace();
			System.out.println("dick seems to have died...");
		}
	}
}
