package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class StaffWeaponSkill extends Skill {

	public static final int ID = 6;
	
	public StaffWeaponSkill() {
		super("Staff Weapon", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.arms, SkillTags.visual};
	}
}
