package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class SlightOfHandSkill extends Skill {

	public static final int ID = 18;
	
	public SlightOfHandSkill() {
		super("Intimidate", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.rightHand};
	}
}
