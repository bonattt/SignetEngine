package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Navigation extends Skill {

	public static final int ID = 26;
	
	public Navigation() {
		super("Navigation", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental};
	}
}
