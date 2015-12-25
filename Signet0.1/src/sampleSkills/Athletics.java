package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Athletics extends Skill {

	public static final int ID = 13;
	private static final String[] LINKED_ABILITIES = new String[]{"str"};
	
	public Athletics() {
		super("Athletics", ID, LINKED_ABILITIES, getTags());
	}
	public Athletics(int ranks) {
		super("Athletics", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.legs, SkillTags.feet, SkillTags.body};
	}
}
