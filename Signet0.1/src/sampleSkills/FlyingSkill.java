package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class FlyingSkill extends Skill {

	public static final int ID = 25;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public FlyingSkill() {
		super("Flying", ID, LINKED_ABILITIES, getTags());
	}
	public FlyingSkill(int ranks) {
		super("Flying", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
