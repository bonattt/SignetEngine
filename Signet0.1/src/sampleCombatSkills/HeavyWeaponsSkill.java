package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class HeavyWeaponsSkill extends Skill {

	public static final int ID = 3;
	
	public HeavyWeaponsSkill() {
		super("Heavy Weapons", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.arms, SkillTags.visual};
	}
	
}
