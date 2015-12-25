package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class HeavyWeaponsSkill extends Skill {

	public static final int ID = 3;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public HeavyWeaponsSkill() {
		super("Heavy Weapons", ID, LINKED_ABILITIES, getTags());
	}
	public HeavyWeaponsSkill(int ranks) {
		super("Heavy Weapons", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.arms, SkillTags.visual};
	}
	
}
