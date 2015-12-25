package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class ClubsSkill extends Skill {

	public static final int ID = 7;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public ClubsSkill() {
		super("Club", ID, LINKED_ABILITIES, getTags());
		// TODO Auto-generated constructor stub
	}
	public ClubsSkill(int ranks) {
		super("Club", ID, LINKED_ABILITIES, getTags(), ranks);
		// TODO Auto-generated constructor stub
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.rightHand, SkillTags.rightArm, SkillTags.visual};
	}
}
