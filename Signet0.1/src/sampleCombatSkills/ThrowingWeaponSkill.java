package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class ThrowingWeaponSkill extends Skill {

	public static final int ID = 5;
	
	public ThrowingWeaponSkill() {
		super("Throwing Weapon", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.rightHand, SkillTags.rightArm, SkillTags.visual};
	}
	
}
