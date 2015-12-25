package testingMothers;

import java.util.HashMap;
import java.util.Scanner;

import misc.DeathException;
import creatures.Creature;
import creatures.Skill;

public class TestMonster extends Creature {
	
	public TestMonster() {
		super("generic-saurace-rex", getGenericMonsterStats(), new HashMap<String, Integer>(), getStartingSkills());
	}

	@Override
	public void die() throws DeathException {
		throw new DeathException("You killed the generic-saurace-rex!!", false);
		
	}
	private static HashMap<String, Skill> getStartingSkills(){
		HashMap<String, Skill> skills = new HashMap<String, Skill>();
		return skills;
	}
	private static HashMap<String, Integer> getGenericMonsterStats(){
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("str", 6);	// strength
		map.put("agl", 6);	// agility
		map.put("end", 6);	// endurance
		map.put("dex", 6);	// dexterity
		map.put("cha", 6);	// Charisma
		map.put("anl", 6);	// analysis
		map.put("per", 6);	// perception
		map.put("wil", 6);	// willpower
		map.put("int", 6);	// intuition
		map.put("rec", 6);	// reaction
		return map;
	}

	@Override
	public boolean isPlayer() {
		return false;
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
