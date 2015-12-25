package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Intimidate extends Skill {

	public static final int ID = 16;
	private static final String[] LINKED_ABILITIES = new String[]{"cha"};
	
	public Intimidate() {
		super("Intimidate", ID, LINKED_ABILITIES, getTags());
	}
	public Intimidate(int ranks) {
		super("Intimidate", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.social};
	}
}
