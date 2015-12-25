package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class Acrobatics extends Skill {

	public static final int ID = 12;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public Acrobatics() {
		super("Acrobatics", ID, LINKED_ABILITIES, getTags());
	}
	public Acrobatics(int ranks) {
		super("Acrobatics", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands, SkillTags.arms, SkillTags.legs, SkillTags.feet,
				SkillTags.body};
	}
}
