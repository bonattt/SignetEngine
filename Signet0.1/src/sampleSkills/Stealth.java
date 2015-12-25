package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Stealth extends Skill {

	public static final int ID = 17;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public Stealth() {
		super("Intimidate", ID, LINKED_ABILITIES, getTags());
	}
	public Stealth(int ranks) {
		super("Intimidate", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.legs, SkillTags.feet, SkillTags.body};
	}
}
