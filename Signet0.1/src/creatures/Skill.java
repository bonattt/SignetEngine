package creatures;

import java.util.HashMap;
import java.util.Random;

import misc.*;


public abstract class Skill {
	 
	public String name;
	public int id;
	public SkillTags[] tags;
	public String[] linkedAttributes;
	public int ranks;
	private int exp;
	
	public static final HashMap<String, Integer> skillIndex = getSkillIndex();
	
	private static HashMap<String, Integer> getSkillIndex(){
		HashMap<String, Integer> index = new HashMap<String, Integer>();
		return index;
	}
	
	public Skill(String name, int id, SkillTags[] tags){
		this(name, id, tags, 0);
	}
	
	public Skill(String name, int id, SkillTags[] tags, int startingRanks){
		this.name = name;
		this.id = id;		
		this.tags = tags;
		this.ranks = startingRanks;
	}
	public void setRanks(int newRank){
		ranks = newRank;
	}
	
	public void gainExperience(int expGained){
		exp += expGained;
		TextTools.display("you gained " + expGained + " experience in " + name);
		while(exp >= (ranks+1)*1000){
			TextTools.display("you gained a rank in " + name + "!");
			exp -= (ranks+1)*1000;
			ranks++;
		}
	}
	public int[] makeSkillTest(Creature creature, int threshold){
		return makeLimitedSkillTest(creature, threshold, 0, linkedAttributes, Integer.MAX_VALUE);
	}
	public int[] makeSkillTest(Creature creature, int threshold, int adjustment){
		return makeLimitedSkillTest(creature, threshold, adjustment, linkedAttributes, Integer.MAX_VALUE);
	}
	public int[] makeSkillTest(Creature creature, int threshold, int adjustment, String attribute){
		return makeLimitedSkillTest(creature, threshold, 0, new String[]{attribute}, Integer.MAX_VALUE);
	}
	public int[] makeSkillTest(Creature creature, int threshold, int adjustment, String[] attributes){
		return makeLimitedSkillTest(creature, threshold, adjustment, attributes, Integer.MAX_VALUE);
	}
	/////
	public int[] makeLimitedSkillTest(Creature creature, int threshold, int limit){
		return makeLimitedSkillTest(creature, threshold, 0, linkedAttributes, limit);
	}
	public int[] makeLimitedSkillTest(Creature creature, int threshold, int adjustment, int limit){
		return makeLimitedSkillTest(creature, threshold, adjustment, linkedAttributes, limit);
	}
	public int[] makeLimitedSkillTest(Creature creature, int threshold, int adjustment, String attribute, int limit){
		return makeLimitedSkillTest(creature, threshold, 0, new String[]{attribute}, limit);
	}
	public int[] makeLimitedSkillTest(Creature creature, int threshold, int adjustment, String[] attributes, int limit){

		int dicePool;
		if(ranks == 0){
			dicePool = getAttributes(creature, attributes) - 1;
		} else {
			dicePool = getAttributes(creature, attributes) + ranks;
		}
		
		int[] result = DiceRoller.makeRoll(dicePool);
		result[0] -= threshold;
		
		return result;
	}
	
	public static int[] makeOpposedSkillTest(Creature creature1, Skill skl1, Creature creature2, Skill skl2){
		return makeOpposedSkillTest(creature1, skl1, 0, creature2, skl2, 0);
	}
	public static int[] makeOpposedSkillTest(Creature creature1, Skill skl1, int adjust1, Creature creature2, Skill skl2, int adjust2){
		int[] result1 = skl1.makeSkillTest(creature1, 0, adjust1, skl1.linkedAttributes);
		int[] result2 = skl2.makeSkillTest(creature2, 0, adjust2, skl2.linkedAttributes);
		return new int[]{(result1[0] - result2[0]), result1[1], result2[1]};
	}
	
//	public static int[] makeSkillTest(Creature creature, String skill){
//		return makeSkillTest(creature, skill, 0);
//	}
//	public static int[] makeSkillTest(Creature creature, String skillStr, int threshold){
//		return creature.getSkills().get(skillStr)
//				.makeSkillTest(creature, threshold);
//	}
	
	private static int getAttributes(Creature creature, String[] attributes){
		int accumulator = 0;
		for (int i = 0; i < attributes.length; i++){
			accumulator += creature.getStats().get(attributes[i]);
		}
		return accumulator;
	}
}
