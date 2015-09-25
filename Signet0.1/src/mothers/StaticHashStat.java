package mothers;

import java.util.HashMap;

public class StaticHashStat {
	
	public static final String[] STAT_LIST = {"str", "agl", "end", "res", "dex", "cha", "anl", "wil", "int", "rec"};

	public static HashMap<String,Integer> getEmptyStatMap(){
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("str", 1); // strength
		map.put("agl", 1); // agility
		map.put("end", 1); // endurance
		map.put("res", 1); // resilience
		map.put("dex", 1); // dexterity
		map.put("cha", 1); // Charisma
		map.put("anl", 1); // analysis
		map.put("wil", 1); // willpower
		map.put("int", 1); // intuition
		map.put("rec", 1); // reaction
		return map;
	}
	public static HashMap<String,Integer> getBasicStatMap(){
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("str", 10); // strength
		map.put("agl", 10); // agility
		map.put("res", 10); // resilience
		map.put("dex", 10); // dexterity
		map.put("cha", 10); // Charisma
		map.put("anl", 10); // analysis
		map.put("wil", 10); // willpower
		map.put("int", 10); // intuition
		map.put("rec", 10); // reaction
		return map;
	}
}
