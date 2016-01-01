package test;

import creatures.Creature;
import inventory.InventoryException;
import inventory.ItemContainer;
import sampleEvents.SampleGenericChest;
import testingMothers.CharacterMother;

public class ExploritoryTestLootChest {
	
	public static void main(String[] args) throws InventoryException {
		
		Creature player = CharacterMother.getDickDefenderOfLife();
		ItemContainer chest = new SampleGenericChest();
		chest.lootDuringExplore(player);
	}
	
}
