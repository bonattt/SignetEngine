package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class PerceptionSkill extends Skill {

	public static final int ID = 11;
	
	public PerceptionSkill() {
		super("Perception", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.auditory, SkillTags.visual, SkillTags.mental};
	}
}
