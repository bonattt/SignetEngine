package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class LockpickingSkill extends Skill {

	public static final int ID = 28;
	
	public LockpickingSkill() {
		super("Lockpicking", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
