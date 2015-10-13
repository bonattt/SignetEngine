package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class DisguiseSkill extends Skill {

	public static final int ID = 19;
	
	public DisguiseSkill() {
		super("Intimidate", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
