package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Foraging extends Skill {

	public static final int ID = 27;
	
	public Foraging() {
		super("Foraging", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental};
	}
}
