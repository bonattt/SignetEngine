package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class DrivingSkill extends Skill {

	public static final int ID = 24;
	
	public DrivingSkill() {
		super("Driving", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
