package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class SwordSkill extends Skill {

	public static final int ID = 9;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public SwordSkill() {
		super("Sword", ID, LINKED_ABILITIES, getTags());
	}
	public SwordSkill(int ranks) {
		super("Sword", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.visual};
	}
}
