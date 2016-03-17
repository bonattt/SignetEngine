package test;

import inventory.InventoryException;
import misc.TextTools;
import testingMothers.CharacterMother;
import creatures.PlayerCharacter;

public class ExploritoryTestInventoryAccess {
	
	public static void main(String[] args) throws InventoryException {
		runTest();
	}
	private static void runTest() throws InventoryException {
		PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
		player.getInventory().accessInventoryDuringExplore(player);
		TextTools.display(player.getInventory().toString());
	}	
}
