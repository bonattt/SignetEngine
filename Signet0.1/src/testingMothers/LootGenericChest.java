package testingMothers;

import inventory.ItemContainer;

import java.io.PrintWriter;
import java.util.Scanner;

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
	public static LootGenericChest loadAlpha0_1(Scanner scanner) {
		ItemContainer chest = ItemContainer.loadAlpha0_1(scanner);
		LootGenericChest event = new LootGenericChest();
		event.chest = chest;
		return event;
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
