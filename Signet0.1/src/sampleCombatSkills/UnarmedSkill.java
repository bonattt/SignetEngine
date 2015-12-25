package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class UnarmedSkill extends Skill {

	public static final int ID = 10;
	private static final String[] LINKED_STATS = new String[]{"agl"};
	
	public UnarmedSkill() {
		super("Unarmed", ID, LINKED_STATS, getTags());
	}
	public UnarmedSkill(int ranks) {
		super("Unarmed", ID, LINKED_STATS, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.legs,
				SkillTags.feet, SkillTags.visual};
	}
}
