package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class SlightOfHandSkill extends Skill {

	public static final int ID = 18;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public SlightOfHandSkill() {
		super("Intimidate", ID, LINKED_ABILITIES, getTags());
	}
	public SlightOfHandSkill(int ranks) {
		super("Intimidate", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.rightHand};
	}
}
