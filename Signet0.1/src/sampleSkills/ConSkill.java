package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class ConSkill extends Skill {

	public static final int ID = 14;
	private static final String[] LINKED_ABILITIES = new String[]{"cha"};
	
	public ConSkill() {
		super("Con", ID, LINKED_ABILITIES, getTags());
	}
	public ConSkill(int ranks) {
		super("Con", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.social};
	}
}
