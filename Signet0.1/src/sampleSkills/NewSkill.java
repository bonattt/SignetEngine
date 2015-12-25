package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class NewSkill extends Skill {

	public static final int ID = 31;
	private static final String[] LINKED_ABILITIES = new String[]{"???"};
	
	public NewSkill() {
		super("[unnamed skill]", ID, LINKED_ABILITIES, getTags());
	}
	public NewSkill(int ranks) {
		super("[unnamed skill]", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
