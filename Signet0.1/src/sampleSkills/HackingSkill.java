package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class HackingSkill extends Skill {

	public static final int ID = 22;
	
	public HackingSkill() {
		super("Hacking", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental};
	}
}
