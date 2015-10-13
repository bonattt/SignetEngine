package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class LongArmSkill extends Skill {

	public static final int ID = 1;
	
	public LongArmSkill() {
		super("Longarms", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.visual};
	}
	
}
