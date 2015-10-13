package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class ConSkill extends Skill {

	public static final int ID = 14;
	
	public ConSkill() {
		super("Con", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.social};
	}
}
