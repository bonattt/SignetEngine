package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Navigation extends Skill {

	public static final int ID = 26;
	private static final String[] LINKED_ABILITIES = new String[]{"int"};
	
	public Navigation() {
		super("Navigation", ID, LINKED_ABILITIES, getTags());
	}
	public Navigation(int ranks) {
		super("Navigation", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental};
	}
}
