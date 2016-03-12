package dialogue;

import items.Item;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import misc.DeathException;
import misc.GameEvent;
import misc.GameLoadException;
import misc.TextTools;
import creatures.PlayerCharacter;
import environment.Environment;

public class EventNode extends DialogueNode {
	
	private GameEvent event;
	private DialogueNode nextNode;
	private String text;
	
	public EventNode(int id,String text, GameEvent event, DialogueNode nextNode) {
		this.text = text;
		this.event = event;
		this.nextNode = nextNode;
		this.id = id;
	}

	@Override
	public void setEdges(DialogueNode[] edges) {
		nextNode = edges[0];
	}

	@Override
	public DialogueNode openNode(PlayerCharacter player, NPC npc) throws DeathException {
		TextTools.display(text);
		TextTools.display("press enter to continue");
		TextTools.input.nextLine();
		event.triggerEvent(player);
		return nextNode;
	}
	
	public DialogueNode[] getEdges() {
		return new DialogueNode[]{nextNode};
	}
	
	public static EventNode loadEventNodeAlpha0_1(Scanner scanner) throws GameLoadException {
		int id = scanner.nextInt();
		scanner.nextLine();
		String text = Item.loadLongStringAlpha0_1(scanner);
		GameEvent event = Environment.loadGameEventAlpha0_1(scanner);
		return new EventNode(id, text, event, null);
	}

	@Override
	public void loadEdgesAlpha0_1(List<DialogueNode> nodesLoaded, Scanner scanner){
		nextNode = getSingleEdgeAlpha0_1(nodesLoaded, scanner);
	}
	
	@Override
	public void saveNodeToFile(PrintWriter writer, Set<DialogueNode> nodesVisited) {
		writer.println("event");
		if (nodesVisited.contains(this)) {
			return;
		}
		nodesVisited.add(this);
		writer.println(id);
		Item.saveLongStringToFile(writer, text);
		event.saveToFile(writer);
	}

	@Override
	public void saveEdgesToFile(PrintWriter writer) {
		saveSingleNext(nextNode, writer);
	}

	public Iterator iterator() {
		return new LeafNodeIterator(this);
	}


}
