package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Acrobatics extends Skill {

	public static final int ID = 12;
	
	public Acrobatics() {
		super("Acrobatics", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands, SkillTags.arms, SkillTags.legs, SkillTags.feet,
				SkillTags.body};
	}
}
