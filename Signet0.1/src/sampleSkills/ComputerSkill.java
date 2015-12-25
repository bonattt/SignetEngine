package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class ComputerSkill extends Skill {

	public static final int ID = 20;
	private static final String[] LINKED_ABILITIES = new String[]{"int"};
	
	public ComputerSkill() {
		super("Computer", ID, LINKED_ABILITIES, getTags());
	}
	public ComputerSkill(int ranks) {
		super("Computer", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental};
	}
}
