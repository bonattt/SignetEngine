package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class StaffWeaponSkill extends Skill {

	public static final int ID = 6;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public StaffWeaponSkill() {
		super("Staff Weapon", ID, LINKED_ABILITIES, getTags());
	}
	public StaffWeaponSkill(int ranks) {
		super("Staff Weapon", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.arms, SkillTags.visual};
	}
}
