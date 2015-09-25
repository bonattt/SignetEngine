package sampleEvents;

import java.util.Random;

import creatures.Creature;
import misc.DeathException;
import misc.GameEvent;
import misc.TextTools;

public class LootGenericChest implements GameEvent {
	
	private Random r = new Random();
	
	public LootGenericChest(){
		// do nothing
		// this event is an event for testing purposes, and only displays text that an item was found 
	}
	
	public void triggerEvent(Creature player) throws DeathException {
		
		int numb = r.nextInt(10);
		if (numb <= 2){ 
			// 30% chance to "find" a rare item.
			TextTools.display("You found a rare item!!!");
		} else {
			TextTools.display("You found an item!");
		}
	}

	public String getName() {
		return "Loot Chest Event";
	}

}
