package creatures;

import health.Body;
import inventory.Inventory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import environment.Environment;
import misc.DeathException;
import misc.TextTools;

public class PlayerCharacter extends Creature  {

	private static PlayerCharacter instance = null; 
	
	public PlayerCharacter(String creatureName, HashMap<String, Integer> baseStats, HashMap<String, Integer> damageMultipliers, HashMap<String, Skill> startingSkills) {
		super(creatureName, baseStats, damageMultipliers, startingSkills);
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
	public static PlayerCharacter loadAlpha0_1fromFile(Scanner scanner){
		String name = scanner.nextLine();
		HashMap<String, Integer> baseStats = loadAlpha0_1stats(scanner);
		HashMap<String, Integer> damageMultipliers = loadAlpha0_1damageMultipliers(scanner);
		HashMap<String, Skill> skills = loadAlpha0_1skills(scanner);
		PlayerCharacter player = new PlayerCharacter(name, baseStats, damageMultipliers, skills);
		Body body = null;
		Inventory inv = null;
		if(Environment.print_debugs){
			System.out.print("base stats: ");
			System.out.println(baseStats);
			System.out.println();
		}
		
		
		return player;
	}
	private static HashMap<String, Integer> loadAlpha0_1stats(Scanner scanner){
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		String[] statsInOrder = new String[]{"str", "agl", "end", "dex", "cha", "anl", "per", "wil", "int", "rec"};
		for (String stat : statsInOrder){
			String strVal = scanner.nextLine();
			int intVal = Integer.parseInt(strVal);
			stats.put(stat, intVal);
		}
//		stats.put("str", Integer.getInteger(scanner.nextLine()));
//		stats.put("agl", Integer.getInteger(scanner.nextLine()));
//		stats.put("end", Integer.getInteger(scanner.nextLine()));
//		stats.put("dex", Integer.getInteger(scanner.nextLine()));
//		stats.put("cha", Integer.getInteger(scanner.nextLine()));
//		stats.put("anl", Integer.getInteger(scanner.nextLine()));
//		stats.put("per", Integer.getInteger(scanner.nextLine()));
//		stats.put("wil", Integer.getInteger(scanner.nextLine()));
//		stats.put("int", Integer.getInteger(scanner.nextLine()));
//		stats.put("rec", Integer.getInteger(scanner.nextLine()));
		return stats;
	}
	private static HashMap<String, Integer> loadAlpha0_1damageMultipliers(Scanner scanner){
		HashMap<String, Integer> damageMultipliers = new HashMap<String, Integer>();
		String currentLine = scanner.nextLine();
		while(! currentLine.equals("end damage characteristics")){
			String key = currentLine;
			Integer multiplier = Integer.getInteger(scanner.nextLine());
			currentLine = scanner.nextLine();
			damageMultipliers.put(key, multiplier);
		}
		return damageMultipliers;
	}
	private static HashMap<String, Skill> loadAlpha0_1skills(Scanner scanner){
		HashMap<String, Skill> skills = new HashMap<String, Skill>();
		
		
		return skills;
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
