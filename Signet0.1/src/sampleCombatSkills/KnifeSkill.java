package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class KnifeSkill extends Skill {

	public static final int ID = 8;
	
	public KnifeSkill() {
		super("Knife", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.rightHand, SkillTags.visual};
	}
}
