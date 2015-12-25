package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class ElectronicsSkill extends Skill {

	public static final int ID = 21;
	private static final String[] LINKED_ABILITIES = new String[]{"anl"};
	
	public ElectronicsSkill() {
		super("Electronics", ID, LINKED_ABILITIES, getTags());
	}
	public ElectronicsSkill(int ranks) {
		super("Electronics", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental, SkillTags.hands};
	}
}
