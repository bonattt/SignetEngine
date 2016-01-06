package sampleEvents;

import inventory.ItemContainer;

import java.io.PrintWriter;

import creatures.Creature;
import misc.DeathException;
import misc.GameEvent;

public class LootGenericChest implements GameEvent {
	
	private ItemContainer chest;
	
	public LootGenericChest(){
		chest = new SampleGenericChest();
	}
	
	public void triggerEvent(Creature player) throws DeathException {
		chest.lootDuringExplore(player);
	}

	public String getName() {
		return "Loot Chest Event";
	}
	
	public void saveToFile(PrintWriter writer) {
		writer.println("loot generic chest event");
		chest.saveToFile(writer);
		
	}
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof LootGenericChest)) {
			return false;
		}
		LootGenericChest event = (LootGenericChest) obj;
		return event.chest.equals(this.chest);
	}

}
