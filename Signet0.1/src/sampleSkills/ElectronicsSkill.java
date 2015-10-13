package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class ElectronicsSkill extends Skill {

	public static final int ID = 21;
	
	public ElectronicsSkill() {
		super("Electronics", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental, SkillTags.hands};
	}
}
