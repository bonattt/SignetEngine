package creatures;

import health.Body;
import health.BodyPart;
import inventory.Inventory;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import environment.Environment;
import misc.DeathException;
import misc.GameLoadException;

public class PlayerCharacter extends Creature  {

	private static PlayerCharacter instance = null; 
	
	public PlayerCharacter(String creatureName, HashMap<String, Integer> baseStats,
			HashMap<String, Integer> damageMultipliers, HashMap<String, Skill> startingSkills,
			HashMap<String, BodyPart> bodyparts) {
		super(creatureName, baseStats, damageMultipliers, startingSkills, bodyparts);
	}
	public static PlayerCharacter getInstance(){
		if (instance == null){
			System.out.println("ERROR Player Instance is NULL");
			return null;
//			HashMap<String, Integer> baseStats = new HashMap<String, Integer>();
//			HashMap<String, Integer> damageMultipliers = new HashMap<String, Integer>();
//			instance = new PlayerCharacter("new character", baseStats, damageMultipliers);
		}
		return instance;
	}
	@Override
	public void saveToFile(PrintWriter writer){
		super.saveToFile(writer);
	}
	public static PlayerCharacter loadAlpha0_1(Scanner scanner) throws GameLoadException{
		String name = scanner.nextLine();
		HashMap<String, Integer> baseStats = loadAlpha0_1stats(scanner);
		HashMap<String, Integer> damageMultipliers = loadAlpha0_1damageMultipliers(scanner);
		HashMap<String, Skill> skills = loadAlpha0_1skills(scanner);
		HashMap<String, BodyPart> bodyparts = null;
		PlayerCharacter player = new PlayerCharacter(name, baseStats, damageMultipliers, skills, bodyparts);
		player.loadInvAndBodyAlpha0_1(scanner);
		return player;
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
