package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class FirstAidSkill extends Skill {

	public static final int ID = 23;
	private static final String[] LINKED_ABILITIES = new String[]{"anl"};
	
	public FirstAidSkill() {
		super("First Aid", ID, LINKED_ABILITIES, getTags());
	}
	public FirstAidSkill(int ranks) {
		super("First Aid", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands, SkillTags.mental};
	}
}
