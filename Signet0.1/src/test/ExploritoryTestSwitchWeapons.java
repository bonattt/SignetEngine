package test;

import testingMothers.CharacterMother;
import items.Weapon;
import misc.TextTools;
import creatures.PlayerCharacter;

public class ExploritoryTestSwitchWeapons {
	
	public static void main(String[] args){
		runTest();
	}
	
	private static void runTest(){
	
		PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
		int keepTesting = 0;
		do {
			if (keepTesting == 0){
				TextTools.display("\n\n\n\n\n\n\n\n\n\n\n\n\ns----------\n");
			}
			displayTestResults(player);
			player.getInventory().switchWeapons();
			keepTesting = TextTools.questionAsker("keep testing?",
													new String[]{"yes", "no"},
													TextTools.BACK_ENABLED);
		} while(keepTesting == 1);
		displayTestResults(player);
	}
	
	private static void displayTestResults(PlayerCharacter player){

		Weapon currentWeapon = player.getInventory().getWeapon();
		if(currentWeapon == null) {
			TextTools.display("held weapon: null");
		} else {
			TextTools.display("held weapon: " + currentWeapon.name());
		}
		
		currentWeapon = player.getInventory().getEquippedWeapons().get("holster");
		if(currentWeapon == null) {
			TextTools.display("holster: null");
		} else {
			TextTools.display("holster: " + currentWeapon.name());
		}
		
		currentWeapon = player.getInventory().getEquippedWeapons().get("boot");
		if(currentWeapon == null) {
			TextTools.display("boot: null");
		} else {
			TextTools.display("boot: " + currentWeapon.name());
		}
		
		player.getInventory().displayBackpack();
		
	}
}
