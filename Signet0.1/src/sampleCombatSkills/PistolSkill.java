package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class PistolSkill extends Skill {

	public static final int ID = 0;
	
	public PistolSkill(String name, int id, SkillTags[] tags) {
		super("pistol", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.rightHand, SkillTags.visual};
	}
}
