package test;

import misc.TextTools;
import testingMothers.CharacterMother;
import creatures.PlayerCharacter;

public class ExploritoryTestInventoryAccess {
	
	public static void main(String[] args){
		runTest();
	}
	private static void runTest(){
		PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
		player.getInventory().accessInventoryDuringExplore(player);
		TextTools.display(player.getInventory().toString());
	}	
}
