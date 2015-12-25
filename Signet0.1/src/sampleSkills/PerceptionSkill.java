package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class PerceptionSkill extends Skill {

	public static final int ID = 11;
	private static final String[] LINKED_ABILITIES = new String[]{"per"};
	
	public PerceptionSkill() {
		super("Perception", ID, LINKED_ABILITIES, getTags());
	}
	public PerceptionSkill(int ranks) {
		super("Perception", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.auditory, SkillTags.visual, SkillTags.mental};
	}
}
