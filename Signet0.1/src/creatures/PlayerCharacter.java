package creatures;

import health.Body;
import inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import misc.DeathException;
import misc.TextTools;

public class PlayerCharacter extends Creature {

	private static PlayerCharacter instance = null; 
	
	public PlayerCharacter(String creatureName, HashMap<String, Integer> baseStats, HashMap<String, Integer> damageMultipliers) {
		super(creatureName, baseStats, damageMultipliers);
	}
	public static PlayerCharacter getInstance(){
		if (instance == null){
			HashMap<String, Integer> baseStats = new HashMap<String, Integer>();
			HashMap<String, Integer> damageMultipliers = new HashMap<String, Integer>();
			instance = new PlayerCharacter("new character", baseStats, damageMultipliers);
		}
		return instance;
	}
	
	@Override
	public void die() throws DeathException {
		throw new DeathException("you have been killed ", true);
	}
	@Override
	public boolean isPlayer() {
		return true;
	}
	@Override
	public String handleDeath(DeathException e) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String handleKills(DeathException e) {
		// TODO Auto-generated method stub
		return null;
	}
}
