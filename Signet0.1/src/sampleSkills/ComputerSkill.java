package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class ComputerSkill extends Skill {

	public static final int ID = 20;
	
	public ComputerSkill() {
		super("Computer", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental};
	}
}
