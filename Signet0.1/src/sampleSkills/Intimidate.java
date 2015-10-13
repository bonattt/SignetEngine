package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Intimidate extends Skill {

	public static final int ID = 16;
	
	public Intimidate() {
		super("Intimidate", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.social};
	}
}
