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
		DialogueNode end = new DisplayNode("this is the last scene. Goodbye!", null);
		DialogueNode scene3 = new DisplayNode("this is scene 3", end);
		DialogueNode scene2 = new DisplayNode("this is scene 2", scene3);
		DialogueNode scene1 = new DisplayNode("this is scene 1", scene2);
		DialogueNode intro = new DisplayNode("this is the intor scene. Welcome.", scene1);
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
		DialogueNode end1 = new DisplayNode("you picked ending 1", null);
		DialogueNode end2 = new DisplayNode("you picked ending 2", null);
		DialogueNode end3 = new DisplayNode("you picked ending 3", null);
	
		String[] choices = new String[]{"ending 1", "ending 2", "ending 3"};
		DialogueNode[] nodes = new DialogueNode[]{end1, end2, end3};
		DialogueNode selector = new SelectionNode("which ending would you like?", choices, nodes);
		
		DialogueNode intro = new DisplayNode("Welcome to Deus Ex Machina, Humanoid Revolt!", selector);
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
		DialogueNode beginningPointer = new PointerNode();
		DialogueNode resetEvil = new DisplayNode("the game has randomly reset to the beginning. (but your evilness hasn't reset)", beginningPointer);
		DialogueNode everyoneHatesYou = new DisplayNode("Everyone hates you because you are evil and killed everyone.", resetEvil);
		
		DialogueNode neutral = new DisplayNode("You thought about defending yourself from a monster, so you're pretty bad (not quite evil)\neveryone hates you thought, b/c you suck.", resetEvil);
		DialogueNode sureEvil = Dialogue.yesNoNode("You are evil if you do that. Are you sure you wish to?", everyoneHatesYou, neutral);
		
		DialogueNode resetGood = new DisplayNode("The game randomly resets so you can replay it again, but SLIGHTLY DIFFERENLY, (mostly the same though)", beginningPointer);
		DialogueNode good = new DisplayNode("You let the monster kick you arround for a bit until it got bored.\nYou have proven your moral fiber is that of Ghandi!", resetGood);
		
		String[] answers = new String[]{"fight back and defend yourself!", "try to be it's fwend", "quit"};
		DialogueNode[] nodes = new DialogueNode[]{sureEvil, good, null};
		DialogueNode battle = new SelectionNode("a vicious monster attacks you!\nWhat will you do?", answers, nodes);
		
		DialogueNode welcome = new DisplayNode("welcome to undertail.", battle);
		beginningPointer.setNextNode(welcome);
		
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
