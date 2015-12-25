package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class NegociateSkill extends Skill {

	public static final int ID = 15;
	private static final String[] LINKED_ABILITIES = new String[]{"cha"};
	
	public NegociateSkill() {
		super("Negociate", ID, LINKED_ABILITIES, getTags());
	}
	public NegociateSkill(int ranks) {
		super("Negociate", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.social};
	}
}
