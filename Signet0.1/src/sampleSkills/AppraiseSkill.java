package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class AppraiseSkill extends Skill {

	public static final int ID = 30;
	
	public AppraiseSkill() {
		super("[unnamed skill]", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental};
	}
}
