package sampleEvents;

import inventory.ItemContainer;

import java.util.Random;

import creatures.Creature;
import misc.DeathException;
import misc.DiceRoller;
import misc.GameEvent;
import misc.TextTools;

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

}
