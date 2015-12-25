package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class DisguiseSkill extends Skill {

	public static final int ID = 19;
	private static final String[] LINKED_ABILITIES = new String[]{"int"};
	
	public DisguiseSkill() {
		super("Intimidate", ID, LINKED_ABILITIES, getTags());
	}
	public DisguiseSkill(int ranks) {
		super("Intimidate", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
