package dialogue;

import java.io.PrintWriter;

import misc.DeathException;
import misc.GameEvent;
import misc.TextTools;
import creatures.Creature;
import creatures.PlayerCharacter;
import creatures.Skill;

public class Dialogue implements GameEvent {
	
	private String name;
	private PlayerCharacter player;
	private NPC npc;
	private DialogueNode start;
	
	public Dialogue(String name, DialogueNode start, PlayerCharacter player) {
		this(name, start, player, null);
	}
	
	public Dialogue(String name, DialogueNode start, PlayerCharacter player, NPC npc) {
		this.name = name;
		this.start = start;
		this.player = player;
		this.npc = npc;
	}
	
	public void startDialogue() throws DeathException {
		DialogueNode currentNode = start;
		while(currentNode != null) {
			currentNode = currentNode.openNode(player, npc);
		}
	}
	
	public static DialogueNode getSkillTestNode(Skill skill, String text, DialogueNode nextNode) {
		GameEvent event = null;
		new EventNode(text, event, nextNode);
		throw new UnsupportedOperationException();
	}
	
	public static DialogueNode yesNoNode(String text, DialogueNode yesNode, DialogueNode noNode) {
		String[] answers = new String[]{"yes", "no"};
		DialogueNode[] nodes = new DialogueNode[]{yesNode, noNode};
		return new SelectionNode(text, answers, nodes);
	}

	public Object triggerEvent(Creature player) throws DeathException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveToFile(PrintWriter writer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
