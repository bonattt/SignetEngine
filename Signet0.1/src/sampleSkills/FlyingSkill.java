package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class FlyingSkill extends Skill {

	public static final int ID = 25;
	
	public FlyingSkill() {
		super("Flying", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
