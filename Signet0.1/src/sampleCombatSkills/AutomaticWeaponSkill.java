package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class AutomaticWeaponSkill extends Skill {

	public static final int ID = 2;
	
	public AutomaticWeaponSkill() {
		super("Automatics", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.visual};
	}
	
}
