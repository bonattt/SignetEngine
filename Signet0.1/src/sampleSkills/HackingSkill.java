package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class HackingSkill extends Skill {

	public static final int ID = 22;
	private static final String[] LINKED_ABILITIES = new String[]{"anl"};
	
	public HackingSkill() {
		super("Hacking", ID, LINKED_ABILITIES, getTags());
	}
	public HackingSkill(int ranks) {
		super("Hacking", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental};
	}
}
