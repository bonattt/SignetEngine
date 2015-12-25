package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class GrenadeSkill extends Skill {

	public static final int ID = 4;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public GrenadeSkill() {
		super("Grenade", ID, LINKED_ABILITIES, getTags());
	}
	public GrenadeSkill(int ranks) {
		super("Grenade", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.rightHand, SkillTags.rightArm, SkillTags.visual};
	}
	
}
