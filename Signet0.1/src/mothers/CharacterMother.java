package mothers;

import java.util.HashMap;

import creatures.PlayerCharacter;
/**
 * Jarred the impaler, object mother character.
 * @author bonattt
 */
public class CharacterMother {
	
	public static PlayerCharacter getDickDefenderOfLife(){
		// TODO implement Jarred
		String creatureName = "Jarred";
		HashMap<String, Integer> baseStats = getJarredStatMap();
		HashMap<String, Integer> damageMultipliers = getJarredDamageMultipliers();
		return new PlayerCharacter(creatureName, baseStats, damageMultipliers);
	}
	private static HashMap<String,Integer> getJarredStatMap(){
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("str", 12);	// strength
		map.put("agl", 10);	// agility
		map.put("end", 10);	// endurance
		map.put("dex", 4);	// dexterity
		map.put("cha", 1);	// Charisma
		map.put("anl", 4);	// analysis
		map.put("per", 6);	// perception
		map.put("wil", 12);	// willpower
		map.put("int", 8);	// intuition
		map.put("rec", 8);	// reaction
		return map;
	}
	private static HashMap<String, Integer> getJarredDamageMultipliers(){
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		return ret;
	}
}
