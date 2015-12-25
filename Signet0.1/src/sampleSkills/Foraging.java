package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Foraging extends Skill {

	public static final int ID = 27;
	private static final String[] LINKED_ABILITIES = new String[]{"anl"};
	
	public Foraging() {
		super("Foraging", ID, LINKED_ABILITIES, getTags());
	}
	public Foraging(int ranks) {
		super("Foraging", ID, LINKED_ABILITIES, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental};
	}
}
