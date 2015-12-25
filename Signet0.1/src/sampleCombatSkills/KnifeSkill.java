package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class KnifeSkill extends Skill {

	public static final int ID = 8;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public KnifeSkill() {
		super("Knife", ID, LINKED_ABILITIES, getTags());
	}
	public KnifeSkill(int ranks) {
		super("Knife", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.rightHand, SkillTags.visual};
	}
}
