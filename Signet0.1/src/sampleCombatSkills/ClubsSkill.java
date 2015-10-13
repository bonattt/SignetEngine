package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class ClubsSkill extends Skill {

	public static final int ID = 7;
	
	public ClubsSkill() {
		super("Club", ID, getTags());
		// TODO Auto-generated constructor stub
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.rightHand, SkillTags.rightArm, SkillTags.visual};
	}
}
