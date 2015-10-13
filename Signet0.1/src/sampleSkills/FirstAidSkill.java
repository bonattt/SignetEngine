package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class FirstAidSkill extends Skill {

	public static final int ID = 23;
	
	public FirstAidSkill() {
		super("First Aid", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands, SkillTags.mental};
	}
}
