package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class SwordSkill extends Skill {

	public static final int ID = 9;
	
	public SwordSkill() {
		super("Sword", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.visual};
	}
}
