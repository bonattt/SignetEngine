package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class PistolSkill extends Skill {

	public static final int ID = 0;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public PistolSkill() {
		super("pistol", ID, LINKED_ABILITIES, getTags());
	}
	public PistolSkill(int ranks) {
		super("pistol", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.rightHand, SkillTags.visual};
	}
}
