package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Athletics extends Skill {

	public static final int ID = 13;
	
	public Athletics() {
		super("Athletics", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.legs, SkillTags.feet, SkillTags.body};
	}
}
