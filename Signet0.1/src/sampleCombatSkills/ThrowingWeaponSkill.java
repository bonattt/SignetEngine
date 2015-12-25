package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class ThrowingWeaponSkill extends Skill {

	public static final int ID = 5;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};	
	
	public ThrowingWeaponSkill() {
		super("Throwing Weapon", ID, LINKED_ABILITIES, getTags());
	}
	public ThrowingWeaponSkill(int ranks) {
		super("Throwing Weapon", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.rightHand, SkillTags.rightArm, SkillTags.visual};
	}
	
}
