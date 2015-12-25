package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class LockpickingSkill extends Skill {

	public static final int ID = 28;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public LockpickingSkill() {
		super("Lockpicking", ID, LINKED_ABILITIES, getTags());
	}
	public LockpickingSkill(int ranks) {
		super("Lockpicking", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
