package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class NewSkill extends Skill {

	public static final int ID = 31;
	
	public NewSkill() {
		super("[unnamed skill]", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
