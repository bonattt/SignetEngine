package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class LongArmSkill extends Skill {

	public static final int ID = 1;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public LongArmSkill() {
		super("Longarms", ID, LINKED_ABILITIES, getTags());
	}
	public LongArmSkill(int ranks) {
		super("Longarms", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.visual};
	}
	
}
