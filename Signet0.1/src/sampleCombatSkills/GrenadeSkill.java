package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class GrenadeSkill extends Skill {

	public static final int ID = 4;
	
	public GrenadeSkill() {
		super("Grenade", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.rightHand, SkillTags.rightArm, SkillTags.visual};
	}
	
}
