package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Stealth extends Skill {

	public static final int ID = 17;
	
	public Stealth() {
		super("Intimidate", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.legs, SkillTags.feet, SkillTags.body};
	}
}
