package mothers;

import java.util.HashMap;
import java.util.Scanner;

import npc.*;

public class DialogueMother {
	
	public static DialogueNode ViewingPannorama(){
		Scanner inputScanner = new Scanner (System.in);
		DialogueNode node3 = new DisplayNode(inputScanner, "You depart, taking nothing but memories.", null);
		DialogueNode node2 = new DisplayNode(inputScanner, "Soon it grows dark, so you begin to pack up your things.", node3);
		DialogueNode head = new DisplayNode(inputScanner, "You gaze out over the canyon, and take in it's beauty.", node2);
		return head;
	}
	
	public static DialogueNode MagicBoxes(){
		Scanner inputScanner = new Scanner (System.in);
		
		DialogueNode tail = new DisplayNode(inputScanner, "it's been fun playing! until next time, suckers!", null);
		DialogueNode node3 = new DisplayNode(inputScanner, "You opened box 3, you get my tax bill, which you are now contractually obligated to pay.\nCongradulations!", tail);
		DialogueNode node2 = new DisplayNode(inputScanner, "You opened box 2, you got a can of whoop-ass! Don't use it all in one place.", tail);
		DialogueNode node1 = new DisplayNode(inputScanner, "You opened box 1, you got nothing. You idiot!", tail);
		
		String[] answers = new String[]{
				"Box number 1", "Box number 2", "Box number 3"
		};
		DialogueNode[] nodes = new DialogueNode[]{
				node1, node2, node3
		};
		DialogueNode head = new SelectionNode(inputScanner, "You are at a game show where you are given 3 boxes.\nYou must chose which box to open to find out your prize!.\nWhich box will you open?", answers, nodes);
		return head;
	}
	
	public static DialogueNode LargeDialogue01(){
		Scanner inputScanner = new Scanner(System.in);
		
		DialogueNode buySomeDrugs = new DisplayNode(inputScanner, "You haggle over prices with the shifty bum for a while. Eventually you \n"
				+ "agree on a price, erxchange money for goods, and you move on into the night...", null);
		DialogueNode badAttitude = new DisplayNode(inputScanner, "\"Sorry chummer, you got a bad attitude. Better luck next time.\"\n"
				+ "You look at the man expectingly for a while, but he says nothing more.\n"
				+ "You realize you've rubbed him the wrong way somehow, and there's nothing you can do\n"
				+ "so you walk away, feeling disappointed.", null);
		DialogueNode goodsNotGood = new DisplayNode(inputScanner, "If you ain't here for the goods, than kindly fuck off, chummer.\n"
				+ "You decide enough is enough and move along, not wanting to start trouble with\n"
				+ "whoever this chummer's boss is.", null);
		
		int mood = -1;
//		DialogueNode nodes = buysSomeDrugs;
//		DialogueNode lowMood = badAttitude;
		DialogueNode moodSelect01 = new MoodSelectNode(inputScanner, badAttitude, mood, buySomeDrugs);
		
		String[] answers = new String[]{"\"I'm interested\"", "\"I heard you have bad goods\""};
		DialogueNode[] nextNodes = new DialogueNode[]{moodSelect01, goodsNotGood};
		String text = "Well I'm selling baking supplies if you need any.";
		DialogueNode iAmSelling = new SelectionNode(inputScanner, text, answers, nextNodes);
		
		DialogueNode rudeRequestToBuy = new MoodSetNode(inputScanner, iAmSelling, -1);
		
		DialogueNode basicLeave = new DisplayNode(inputScanner, "You just walk away", null);
		
		PointerNode tooPolitePointer = new PointerNode(); // allows tooPolite to point to a node declared later 
		DialogueNode tooPoliteText = new DisplayNode(inputScanner, "Yeah whatever. You buyin'?", tooPolitePointer);
		DialogueNode tooPolite = new MoodSetNode(inputScanner, tooPoliteText, -1); //TODO fix this so it can point to a selection node from earlier.
		
		answers = new String[]{"\"sorry, just trying to be friendly.", "just leave"};
		nextNodes = new DialogueNode[]{tooPolite, basicLeave};
		DialogueNode tryToBeNice = new SelectionNode(inputScanner, "Huh? What do you think this is? Get lost!", answers, nextNodes);
		
		DialogueNode rudeCustomer = new MoodSetNode(inputScanner, iAmSelling, -1);
		
		answers = new String[]{"\"I bake on occasion\"", "\"so what if I do?\"", "\"what?\"", "\"good evening\"", "\"get lost, creep!\"",
		                     "\"I don't want any trouble.\"", "just leave."};
		nextNodes = new DialogueNode[]{iAmSelling, rudeCustomer, tryToBeNice, tryToBeNice, basicLeave, tryToBeNice, basicLeave};
		text = "You walk down the alley and see a shady figure standing there.\n" +
				"You like to bake cookies sometimes?";

		DialogueNode firstSelection = new SelectionNode(inputScanner, "", answers, nextNodes);
		tooPolitePointer.setNode(firstSelection);
		
		DialogueNode firstDisplay = new DisplayNode(inputScanner, text, firstSelection);
		
		return firstDisplay;
	}
	
	
}
