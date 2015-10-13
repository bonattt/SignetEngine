package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class DemolitionsSkill extends Skill {

	public static final int ID = 29;
	
	public DemolitionsSkill() {
		super("Demolitions", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
