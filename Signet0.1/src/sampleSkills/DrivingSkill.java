package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class DrivingSkill extends Skill {

	public static final int ID = 24;
	private static final String[] LINKED_ABILITIES = new String[]{"rec"};
	
	public DrivingSkill() {
		super("Driving", ID, LINKED_ABILITIES, getTags());
	}
	public DrivingSkill(int ranks) {
		super("Driving", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.hands};
	}
}
