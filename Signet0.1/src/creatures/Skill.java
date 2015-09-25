package creatures;

import java.util.Random;
import misc.*;


public abstract class Skill {
	 
	public String name;
	public int id;
	public SkillTags[] tags;
	public String[] attributes;
	public int ranks;
	private int exp;
	
	public Skill(String name, int id, SkillTags[] tags){
		this.name = name;
		this.id = id;		
		this.tags = tags;
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
		return makeSkillTest(creature, threshold, 0);
	}
	public int[] makeSkillTest(Creature creature, int threshold, int adjustment){

		int dicePool;
		if(ranks == 0){
			dicePool = getAttributes(creature) - 1;
		} else {
			dicePool = getAttributes(creature) + ranks;
		}
		
		int[] result = DiceRoller.makeRoll(dicePool);
		result[0] -= threshold;
		
		return result;
	}
	
	public static int[] makeOpposedSkillTest(Creature creature1, Skill skl1, Creature creature2, Skill skl2){
		return makeOpposedSkillTest(creature1, skl1, creature2, skl2, 0, 0);
	}
	public static int[] makeOpposedSkillTest(Creature creature1, Skill skl1, Creature creature2, Skill skl2, int adjust1, int adjust2){
		int[] result1 = skl1.makeSkillTest(creature1, 0, adjust1);
		int[] result2 = skl2.makeSkillTest(creature2, 0, adjust2);
		return new int[]{(result1[0] - result2[0]), result1[1], result2[1]};
	}
//	public static int[] makeSkillTest(Creature creature, String skill){
//		return makeSkillTest(creature, skill, 0);
//	}
//	public static int[] makeSkillTest(Creature creature, String skillStr, int threshold){
//		return creature.getSkills().get(skillStr)
//				.makeSkillTest(creature, threshold);
//	}
	
	private int getAttributes(Creature creature){
		int accumulator = 0;
		for (int i = 0; i < attributes.length; i++){
			accumulator += creature.getStats().get(attributes[i]);
		}
		return accumulator;
	}
}
