package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class DemolitionsSkill extends Skill {

	public static final int ID = 29;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public DemolitionsSkill() {
		super("Demolitions", ID, LINKED_ABILITIES, getTags());
	}
	public DemolitionsSkill(int ranks) {
		super("Demolitions", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
