package testingMothers;

import inventory.InventoryException;
import creatures.PlayerCharacter;
import dialogue.Dialogue;
import dialogue.DialogueNode;
import dialogue.DisplayNode;
import dialogue.PointerNode;
import dialogue.SelectionNode;

public class DialogueMother {
	
	public static Dialogue seriesOfScenes() {
		DialogueNode end = new DisplayNode(5, "this is the last scene. Goodbye!", null);
		DialogueNode scene3 = new DisplayNode(4, "this is scene 3", end);
		DialogueNode scene2 = new DisplayNode(3, "this is scene 2", scene3);
		DialogueNode scene1 = new DisplayNode(2, "this is scene 1", scene2);
		DialogueNode intro = new DisplayNode(1, "this is the intor scene. Welcome.", scene1);
		try {
			PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
			Dialogue diologue = new Dialogue("sample dialogue", intro, player);
			return diologue;
		} catch (InventoryException e) {
			e.printStackTrace();
			System.out.println("dick seems to have had a wardrobe malfunction...");
		}
		return null;
	}
	
	public static Dialogue simpleChat() {
		DialogueNode end1 = new DisplayNode(5, "you picked ending 1", null);
		DialogueNode end2 = new DisplayNode(4, "you picked ending 2", null);
		DialogueNode end3 = new DisplayNode(3, "you picked ending 3", null);
	
		String[] choices = new String[]{"ending 1", "ending 2", "ending 3"};
		DialogueNode[] nodes = new DialogueNode[]{end1, end2, end3};
		DialogueNode selector = new SelectionNode(2, "which ending would you like?", choices, nodes);
		
		DialogueNode intro = new DisplayNode(1, "Welcome to Deus Ex Machina, Humanoid Revolt!", selector);
		try {
			PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
			Dialogue diologue = new Dialogue("have a simple chat", intro, player);
			return diologue;
		} catch (InventoryException e) {
			e.printStackTrace();
			System.out.println("dick seems to have had a wardrobe malfunction...");
		}
		return null;
	}
	
	public static Dialogue undertail() {
		DialogueNode beginningPointer = new PointerNode(9);
		DialogueNode resetEvil = new DisplayNode(8, "the game has randomly reset to the beginning. (but your evilness hasn't reset)", beginningPointer);
		DialogueNode everyoneHatesYou = new DisplayNode(7, "Everyone hates you because you are evil and killed everyone.", resetEvil);
		
		DialogueNode neutral = new DisplayNode(6, "You thought about defending yourself from a monster, so you're pretty bad (not quite evil)\neveryone hates you thought, b/c you suck.", resetEvil);
		DialogueNode sureEvil = Dialogue.yesNoNode(5, "You are evil if you do that. Are you sure you wish to?", everyoneHatesYou, neutral);
		
		DialogueNode resetGood = new DisplayNode(4, "The game randomly resets so you can replay it again, but SLIGHTLY DIFFERENLY, (mostly the same though)", beginningPointer);
		DialogueNode good = new DisplayNode(3, "You let the monster kick you arround for a bit until it got bored.\nYou have proven your moral fiber is that of Ghandi!", resetGood);
		
		String[] answers = new String[]{"fight back and defend yourself!", "try to be it's fwend", "quit"};
		DialogueNode[] nodes = new DialogueNode[]{sureEvil, good, null};
		DialogueNode battle = new SelectionNode(2, "a vicious monster attacks you!\nWhat will you do?", answers, nodes);
		
		DialogueNode welcome = new DisplayNode(1, "welcome to undertail.", battle);
		beginningPointer.setEdges(new DialogueNode[]{welcome});
		
		PlayerCharacter player;
		try {
			player = CharacterMother.getDickDefenderOfLife();
			return new Dialogue("play undertail", welcome, player);
		} catch (InventoryException e) {
			e.printStackTrace();
			System.out.println("dick seems to have had a wardrobe malfuction");
		}
		return null;
	}
}
