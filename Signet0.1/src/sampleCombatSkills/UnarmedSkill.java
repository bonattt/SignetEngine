package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class UnarmedSkill extends Skill {

	public static final int ID = 10;
	
	public UnarmedSkill() {
		super("Unarmed", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.legs,
				SkillTags.feet, SkillTags.visual};
	}
}
