package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class AppraiseSkill extends Skill {

	public static final int ID = 30;
	private static final String[] LINKED_ABILITIES = new String[]{"int"};
	
	public AppraiseSkill() {
		super("appraise", ID, LINKED_ABILITIES, getTags());
	}
	public AppraiseSkill(int ranks) {
		super("appraise", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.mental};
	}
}
